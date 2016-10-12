package com.decojo.app.controller.web.employer;

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
 * Perform testing on the {@link EmployerDashboardController} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployerDashboardControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Perform testing on the employer dashboard page.
     */
    @Test
    public void test() {
        final ResponseEntity<String> response =
                this.testRestTemplate.withBasicAuth("test", "test").getForEntity("/employer/dashboard", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
