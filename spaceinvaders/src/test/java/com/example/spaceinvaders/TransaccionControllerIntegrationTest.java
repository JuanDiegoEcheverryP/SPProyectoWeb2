package com.example.spaceinvaders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.spaceinvaders.repository.JugadorRepository;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@ActiveProfiles("integration-testing")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TransaccionControllerIntegrationTest {
    
    private static final String SERVER_URL = "http://localhost:8081";
	
	@Autowired
    private TestRestTemplate rest;
	
    @BeforeEach
    void init() {

        
    }


	@Test
	void contextLoads() {
	}
}
