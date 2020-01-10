package com.mertugrul.LeisurePass.service;

import com.mertugrul.LeisurePass.model.entity.Pass;
import com.mertugrul.LeisurePass.repo.PassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
class PassServiceTest {

    @TestConfiguration
    static class PassServiceImplTestContextConfiguration {

        @Bean
        public PassService passService() {
            return new PassService();
        }
    }

    @Autowired
    private PassService passService;

    @MockBean
    private PassRepository passRepository;

    String randomId = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validId_thenShouldBeFound(){
        Pass pass = new Pass();
        when(passRepository.findById(anyString()));
    }
}