package com.decojo.app.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.decojo.app.TestApplication;
import com.decojo.common.model.LaborCategory;
import com.decojo.common.model.LaborCategoryCollection;
import com.decojo.db.LaborCategoryDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link LaborCategoryController} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LaborCategoryControllerIT {
    @Autowired
    private LaborCategoryDao laborCategoryDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Perform testing on the {@link LaborCategoryController} class.
     */
    @Test
    public void test() {
        final ResponseEntity<LaborCategoryCollection> beforeAddColl =
                this.testRestTemplate.withBasicAuth("test", "test")
                        .getForEntity("/api/lcat", LaborCategoryCollection.class);
        assertEquals(HttpStatus.OK, beforeAddColl.getStatusCode());
        assertNotNull(beforeAddColl.getBody());
        assertEquals(0, beforeAddColl.getBody().getLaborCategories().size());

        final ResponseEntity<LaborCategory> beforeAdd =
                this.testRestTemplate.withBasicAuth("test", "test").getForEntity("/api/lcat/id", LaborCategory.class);
        assertEquals(HttpStatus.NOT_FOUND, beforeAdd.getStatusCode());
        assertNull(beforeAdd.getBody());

        final LaborCategory lcat = new LaborCategory("id", "Software Engineer");
        this.laborCategoryDao.add(lcat);

        final ResponseEntity<LaborCategoryCollection> afterAddColl = this.testRestTemplate.withBasicAuth("test", "test")
                .getForEntity("/api/lcat", LaborCategoryCollection.class);
        assertEquals(HttpStatus.OK, afterAddColl.getStatusCode());
        assertNotNull(afterAddColl.getBody());
        assertEquals(1, afterAddColl.getBody().getLaborCategories().size());
        assertTrue(afterAddColl.getBody().getLaborCategories().contains(lcat));

        final ResponseEntity<LaborCategory> afterAdd = this.testRestTemplate.withBasicAuth("test", "test")
                .getForEntity(String.format("/api/lcat/%s", lcat.getId()), LaborCategory.class);
        assertEquals(HttpStatus.OK, afterAdd.getStatusCode());
        assertNotNull(afterAdd.getBody());
        assertEquals(lcat, afterAdd.getBody());
    }
}
