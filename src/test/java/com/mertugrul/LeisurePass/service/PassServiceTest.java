package com.mertugrul.LeisurePass.service;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Java6BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.google.common.collect.ImmutableList;
import com.mertugrul.LeisurePass.exception.ApiException;
import com.mertugrul.LeisurePass.exception.ApiExceptionHandler;
import com.mertugrul.LeisurePass.exception.ApiRequestException;
import com.mertugrul.LeisurePass.model.PassActivationRespond;
import com.mertugrul.LeisurePass.model.PassDatasource;
import com.mertugrul.LeisurePass.model.UpdatePassRequest;
import com.mertugrul.LeisurePass.model.VerificationRespond;
import com.mertugrul.LeisurePass.model.entity.Customer;
import com.mertugrul.LeisurePass.model.entity.Pass;
import com.mertugrul.LeisurePass.model.entity.Vendor;
import com.mertugrul.LeisurePass.repo.CustomerRepository;
import com.mertugrul.LeisurePass.repo.PassRepository;
import com.mertugrul.LeisurePass.repo.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Test for service class of the application.
 */
class PassServiceTest {

    @InjectMocks
    private PassService passService;

    @Mock
    private PassRepository passRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private VendorRepository vendorRepository;

    private UUID passId, passId2, passId3;
    private Customer customer1, customer2, customer3;
    private Vendor vendor1, vendor2, vendor3;
    private PassDatasource passDatasource, passDatasource2, passDatasource3;
    private Pass passGiven, passGiven2, passGiven3;
    private Instant activationDate;

    /**
     * Before test starts, it creates 3 dummy customers, 3 vendors, 3 dummy passDatasources and 3 passes to use for
     * other methods.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        passId = UUID.randomUUID();
        passId2 = UUID.randomUUID();
        passId3 = UUID.randomUUID();
        activationDate = Instant.now();

        // Given
            // Create Dummy Customers
        customer1 = new Customer(1L, "Charles Smith", "Boston, USA");
        customer2 = new Customer(2L, "Sam Cook", "York, UK");
        customer3 = new Customer(3L, "Cengiz Uyruk", "Istanbul, Turkey");
            // Create Dummy Vendors
        vendor1 = new Vendor(1L, "Tower Bridge", "London, UK");
        vendor2 = new Vendor(2L, "Statue of Liberty", "New York, USA");
        vendor3 = new Vendor(3L, "Hagia Sophia Mosque", "Istanbul, Turkey");
            // Create Dummy PassDatasources
        passDatasource = new PassDatasource("London, UK",
                customer1.getCustomerId().toString(),
                vendor1.getVendorId().toString(),
                4);
        passDatasource2 = new PassDatasource("New York, USA",
                customer3.getCustomerId().toString(),
                vendor2.getVendorId().toString(),
                6);
        passDatasource3 = new PassDatasource("Istanbul, Turkey",
                customer2.getCustomerId().toString(),
                vendor3.getVendorId().toString(),
                12);
            // Create Dummy Passes
        passGiven = new Pass(passId.toString(), passDatasource.getCity() ,
                customer1.getCustomerId(),customer1.getFullName(),
                customer1.getAddress(), vendor1.getVendorId(),
                vendor1.getVendorName(), vendor1.getAddress(),
                activationDate, passDatasource.getPassLength(), true);
        passGiven2 = new Pass(passId2.toString(), passDatasource2.getCity() ,
                customer3.getCustomerId(),customer3.getFullName(),
                customer3.getAddress(), vendor2.getVendorId(),
                vendor2.getVendorName(), vendor2.getAddress(),
                activationDate, passDatasource.getPassLength(), true);
        passGiven3 = new Pass(passId3.toString(), passDatasource.getCity() ,
                customer2.getCustomerId(),customer2.getFullName(),
                customer2.getAddress(), vendor3.getVendorId(),
                vendor3.getVendorName(), vendor3.getAddress(),
                activationDate, passDatasource.getPassLength(), true);


    }


    @Test
    void createPass() {
        // Given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer1));
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor1));
        when(passRepository.save(any())).thenReturn(passGiven);

        // When
        Pass passResult = passService.createPass(passDatasource);

        // Then
        assertThat(passResult).isNotNull();
        assertThat(passResult.getId()).isEqualTo(passId.toString());
        assertThat(passResult.getCityName()).isEqualTo("London, UK");
        assertThat(passResult.getVendorName()).isEqualTo("Tower Bridge");
        assertThat(passResult.getPassLength()).isEqualTo(4);
        assertThat(passResult.getCustomerId()).isEqualTo(1L);
        assertThat(passResult.getVendorId()).isEqualTo(1L);
        assertThat(passResult.getCustomerAddress()).isEqualTo("Boston, USA");
        assertThat(passResult.getVendorAddress()).isEqualTo("London, UK");
        assertThat(passResult.getActivationDate()).isEqualTo(activationDate);


    }


    @Test
    void getAllPasses() {
        // Given
        ImmutableList<Pass> passes = new ImmutableList.Builder<Pass>()
                .add(passGiven)
                .add(passGiven2)
                .add(passGiven3)
                .build();
        when(passRepository.findAll()).thenReturn(passes);

        // When
        List<Pass> allPasses = passService.getAllPasses();

        // Then
        assertThat(allPasses).isNotNull();
        assertThat(allPasses.get(0).getId()).isEqualTo(passId.toString());
        assertThat(allPasses.get(1).getId()).isEqualTo(passId2.toString());
        assertThat(allPasses.get(2).getId()).isEqualTo(passId3.toString());
        assertThat(allPasses.get(0).getCustomerName()).isEqualTo(customer1.getFullName());
        assertThat(allPasses.get(1).getCustomerName()).isEqualTo(customer3.getFullName());
        assertThat(allPasses.get(2).getCustomerName()).isEqualTo(customer2.getFullName());
        assertThat(allPasses.get(0).getVendorName()).isEqualTo(vendor1.getVendorName());
        assertThat(allPasses.get(1).getVendorName()).isEqualTo(vendor2.getVendorName());
        assertThat(allPasses.get(2).getVendorName()).isEqualTo(vendor3.getVendorName());

    }

    @Test
    void getPassById() {
        // Given
        when(passRepository.findById(anyString())).thenReturn(Optional.ofNullable(passGiven));

        // When
        Pass resultPass = passService.getPassById(passId.toString());

        //Then
        assertThat(resultPass).isNotNull();
        assertThat(resultPass.getId()).isEqualTo(passId.toString());
        assertThat(resultPass.getCityName()).isEqualTo("London, UK");
        assertThat(resultPass.getVendorName()).isEqualTo("Tower Bridge");
        assertThat(resultPass.getPassLength()).isEqualTo(4);
        assertThat(resultPass.getCustomerId()).isEqualTo(1L);
        assertThat(resultPass.getVendorId()).isEqualTo(1L);
        assertThat(resultPass.getCustomerAddress()).isEqualTo("Boston, USA");
        assertThat(resultPass.getVendorAddress()).isEqualTo("London, UK");
        assertThat(resultPass.getActivationDate()).isEqualTo(activationDate);
    }

    /**
     * Test for checking inactive passes. One Pass object given and it is expected checkPassActive method to return
     * true.
     */
    @Test
    void checkPassActiveForActivePass() {
        // Given
        when(passRepository.findById(anyString())).thenReturn(Optional.ofNullable(passGiven));


        // When
        PassActivationRespond passActivationRespond = passService.checkPassActive(passGiven.getId());


        // Then
        assertThat(passActivationRespond).isNotNull();
        assertThat(passActivationRespond.getIsActive()).isTrue();
    }

    /**
     * Test for checking inactive passes. One Pass object given and it is expected checkPassActive method to return
     * false.
     */
    @Test
    void checkPassActiveForInactivePass() {
        // Given
        passGiven.setActivationDate(Instant.now().minus(12, ChronoUnit.HOURS));
        when(passRepository.findById(anyString())).thenReturn(Optional.ofNullable(passGiven));

        // When
        PassActivationRespond passActivationRespond = passService.checkPassActive(passGiven.getId());

        // Then
        assertThat(passActivationRespond).isNotNull();
        assertThat(passActivationRespond.getIsActive()).isFalse();
    }

    /**
     * verifyPass method test with valid vendor
     */
    @Test
    void verifyPassValidVendor() {
        // Given
        when(passRepository.findById(anyString())).thenReturn(Optional.ofNullable(passGiven));

        // When
        VerificationRespond verificationRespond = passService.verifyPass(passGiven.getId(),
                vendor1.getVendorId());

        // Then
        assertThat(verificationRespond).isNotNull();
        assertThat(verificationRespond.getIsValid()).isTrue();
    }

    /**
     * verifyPass method test with invalid vendor
     */
    @Test
    void verifyPassInvalidVendor() {
        // Given
        when(passRepository.findById(anyString())).thenReturn(Optional.ofNullable(passGiven));

        // When
        VerificationRespond verificationRespond = passService.verifyPass(passGiven.getId(),
                vendor2.getVendorId());

        // Then
        assertThat(verificationRespond).isNotNull();
        assertThat(verificationRespond.getIsValid()).isFalse();
    }

    /**
     * updatePassLength method to update pass duration, but not update start date.
     */
    @Test
    void updatePassLengthNotStartDate() {
        // Given
        Instant checkDate = passGiven.getActivationDate();
        when(passRepository.findById(anyString())).thenReturn(Optional.ofNullable(passGiven));
        when(passRepository.save(any())).thenReturn(passGiven);
        UpdatePassRequest updatePassRequest = new UpdatePassRequest(passGiven.getId(),
                24, false);

        // When
        Pass resultPass = passService.updatePassLength(updatePassRequest);

        // Then
        assertThat(resultPass).isNotNull();
        assertThat(resultPass.getPassLength()).isEqualTo(24);
        assertThat(resultPass.getActivationDate().toString()).isEqualTo(checkDate.toString());
    }


    /**
     * updatePassLength method to update pass duration, also update start date.
     */
    @Test
    void updatePassLengthChangeStartDate() {
        // Given
        String  checkDate = passGiven.getActivationDate().toString();
        when(passRepository.findById(anyString())).thenReturn(Optional.ofNullable(passGiven));
        when(passRepository.save(any())).thenReturn(passGiven);
        UpdatePassRequest updatePassRequest = new UpdatePassRequest(passGiven.getId(),
                24, true);

        // When
        Pass resultPass = passService.updatePassLength(updatePassRequest);

        // Then
        assertThat(resultPass).isNotNull();
        assertThat(resultPass.getPassLength()).isEqualTo(24);
        assertThat(resultPass.getActivationDate().toString()).isNotEqualTo(checkDate);
    }

    @Test
    void cancelPass() {
        // Given
        when(passRepository.findById(anyString())).thenReturn(Optional.ofNullable(passGiven));
        when(passRepository.save(any())).thenReturn(passGiven);
        // When
        Pass resultPass = passService.cancelPass(passGiven.getId());

        // Then
        assertThat(resultPass).isNotNull();
        assertThat(resultPass.getIsActive()).isFalse();
    }
}