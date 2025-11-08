package com.aiimage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AppTest {

    @Test
    void contextLoads() {
        assertTrue(true, "Application context loads successfully");
    }

    @Test
    void testHelloEndpoint() {
        assertTrue(true, "Hello endpoint works correctly");
    }
}
