package com.cr.app.controller;

import static org.junit.Assert.assertEquals;

import com.cr.app.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link ContactController} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Perform testing on the contact us page.
     */
    @Test
    public void test() {
        final ResponseEntity<String> response = this.testRestTemplate.getForEntity("/contact", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
