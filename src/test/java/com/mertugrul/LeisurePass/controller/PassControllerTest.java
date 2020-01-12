package com.mertugrul.LeisurePass.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mertugrul.LeisurePass.exception.ApiRequestException;
import com.mertugrul.LeisurePass.model.PassActivationRespond;
import com.mertugrul.LeisurePass.model.PassDatasource;
import com.mertugrul.LeisurePass.model.UpdatePassRequest;
import com.mertugrul.LeisurePass.model.VerificationRespond;
import com.mertugrul.LeisurePass.model.entity.Pass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.security.PrivateKey;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class PassControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    String URL = "/leisure-pass";

    /**
     * Create Pass method with dummy variables. This is not a Test method.
     * @return
     * @throws Exception
     */
    private Pass addPass() throws Exception{
        PassDatasource passDatasource = new PassDatasource();
        passDatasource.setCity("London");
        passDatasource.setCustomerId("3");
        passDatasource.setPassLength(4);
        passDatasource.setVendorId("3");


        MvcResult result = mockMvc.perform(post(URL)
                .contentType("application/json")
                .content(jsonMapper.writeValueAsString(passDatasource)))
                .andExpect(status().isOk())
                .andReturn();

        return jsonMapper.readValue(result.getResponse().getContentAsByteArray(), Pass.class);
    }

    /**
     * Create pass and checking for correct responses.
     * @throws Exception
     */
    @Test
    public void createPass_test() throws Exception{
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Pass pass = addPass();

        assertNotNull(pass);
        assertEquals("London", pass.getCityName());
        assertEquals("3", pass.getVendorId().toString());
        assertEquals("3", pass.getCustomerId().toString());
    }

    /**
     * Create pass, request for a update with UpdatePassRequest object, without changing the start date.
     * Check for correct response.
     * @throws Exception
     */
    @Test
    public void updatePass_test() throws Exception{
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Pass pass = addPass();
        assertNotNull(pass);
        Instant startDate = pass.getActivationDate();

        UpdatePassRequest updatePassRequest = new UpdatePassRequest();
        updatePassRequest.setPassId(pass.getId());
        updatePassRequest.setIsUpdateActivationDate(false);
        updatePassRequest.setUpdateLength(12);

        MvcResult result = mockMvc.perform(put(URL+"/update")
                .contentType("application/json")
                .content(jsonMapper.writeValueAsString(updatePassRequest)))
                .andExpect(status().isOk())
                .andReturn();

        pass = jsonMapper.readValue(result.getResponse().getContentAsByteArray(), Pass.class);

        assertEquals("12", Integer.toString(pass.getPassLength()));
        assertEquals(startDate.toString(), pass.getActivationDate().toString());
    }

    /**
     * Create pass, request for a update with UpdatePassRequest object, while changing the start date.
     * Check for correct response.
     * @throws Exception
     */
    @Test
    public void updatePassAndRenewDate_test() throws Exception{
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Pass pass = addPass();
        assertNotNull(pass);
        Instant startDate = pass.getActivationDate();

        UpdatePassRequest updatePassRequest = new UpdatePassRequest();
        updatePassRequest.setPassId(pass.getId());
        updatePassRequest.setIsUpdateActivationDate(true);
        updatePassRequest.setUpdateLength(12);

        MvcResult result = mockMvc.perform(put(URL+"/update")
                .contentType("application/json")
                .content(jsonMapper.writeValueAsString(updatePassRequest)))
                .andExpect(status().isOk())
                .andReturn();

        pass = jsonMapper.readValue(result.getResponse().getContentAsByteArray(), Pass.class);

        assertEquals("12", Integer.toString(pass.getPassLength()));
        assertNotEquals(startDate.toString(), pass.getActivationDate().toString());
    }

    /**
     * Creates pass then deletes it. Expecting status 200 OK.
     * @throws Exception
     */
    @Test
    public void cancelPass_test() throws Exception{
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Pass pass = addPass();
        assertNotNull(pass);

        MvcResult result = mockMvc.perform(delete(URL+"/"+pass.getId()+"/deletion"))
                .andExpect(status().isOk())
                .andReturn();

        pass = jsonMapper.readValue(result.getResponse().getContentAsByteArray(), Pass.class);
        assertFalse(pass.getIsActive());
    }

    /**
     * Creates pass then tries to deletes it with invalid ID but expecting status Bad Request.
     * @throws Exception
     */
    @Test
    public void cancelPassInvalidId_test() throws Exception{
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Pass pass = addPass();
        assertNotNull(pass);

        MvcResult result = mockMvc.perform(delete(URL+"/"+"222"+"/deletion"))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    /**
     * Create pass, try to get by ID and expecting the correct respond.
     * @throws Exception
     */
    @Test
    public void getById_test() throws Exception{
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Pass pass = addPass();

        MvcResult result = mockMvc.perform(get(URL+"/"+pass.getId()))
                .andExpect(status().isOk())
                .andReturn();

        pass = jsonMapper.readValue(result.getResponse().getContentAsByteArray(), Pass.class);
        assertNotNull(pass);
        assertEquals("London", pass.getCityName());
        assertEquals("3", pass.getVendorId().toString());
        assertEquals("3", pass.getCustomerId().toString());
    }

    /**
     * Create pass, try to get by ID with invalid ID and expecting Bad Request.
     * @throws Exception
     */
    @Test
    public void getByIdWitınvalidId_test() throws Exception{
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Pass pass = addPass();

        mockMvc.perform(get(URL+"/"+"222"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    /**
     * Create pass, check if the pass is valid, expecting the correct respond.
     * @throws Exception
     */
    @Test
    public void isPassActive_test() throws Exception{
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        PassActivationRespond passActivationRespond;

        Pass pass = addPass();
        assertNotNull(pass);

        MvcResult result = mockMvc.perform(get(URL+"/"+pass.getId()+"/activation"))
                .andExpect(status().isOk())
                .andReturn();

        passActivationRespond = jsonMapper.readValue(result.getResponse().getContentAsByteArray(),
                PassActivationRespond.class);
        assertNotNull(passActivationRespond);
        assertTrue(passActivationRespond.getIsActive());

    }

    /**
     * Create pass, verify with given Vendor ID, expecting correct respond.
     * @throws Exception
     */
    @Test
    public void verifyPass_test() throws Exception{
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        VerificationRespond verificationRespond;

        Pass pass = addPass();
        assertNotNull(pass);

        MvcResult result = mockMvc.perform(get(URL+"/"+pass.getId()+"/vendor/"
                + pass.getVendorId()))
                .andExpect(status().isOk())
                .andReturn();

        verificationRespond = jsonMapper.readValue(result.getResponse().getContentAsByteArray(),
                VerificationRespond.class);
        assertNotNull(verificationRespond);
        assertTrue(verificationRespond.getIsValid());
    }

    /**
     * Create pass, verify with given invalid Vendor ID, expecting status Bad Request.
     * @throws Exception
     */
    @Test
    public void verifyPass_testForUnknownVendorId() throws Exception{
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        VerificationRespond verificationRespond;

        Pass pass = addPass();
        assertNotNull(pass);

        MvcResult result = mockMvc.perform(get(URL+"/"+pass.getId()+"/vendor/"
                + 2))
                .andExpect(status().isOk())
                .andReturn();

        verificationRespond = jsonMapper.readValue(result.getResponse().getContentAsByteArray(),
                VerificationRespond.class);
        assertNotNull(verificationRespond);
        assertFalse(verificationRespond.getIsValid());
    }



}