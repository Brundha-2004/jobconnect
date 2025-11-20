package com.example.jobconnect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // This tells Spring to use the test profile
public class JobconnectApplicationTests {

    @Test
    void contextLoads() {
        // This test will verify that the Spring application context loads successfully
    }
}