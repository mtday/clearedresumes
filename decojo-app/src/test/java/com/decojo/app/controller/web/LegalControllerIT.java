package com.decojo.app.controller.web;

import static org.junit.Assert.assertEquals;

import com.decojo.app.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link LegalController} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LegalControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Perform testing on the privacy policy page.
     */
    @Test
    public void testPrivacyPolicy() {
        final ResponseEntity<String> response = this.testRestTemplate.getForEntity("/privacy", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Perform testing on the terms of service page.
     */
    @Test
    public void testTermsOfService() {
        final ResponseEntity<String> response = this.testRestTemplate.getForEntity("/terms", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
