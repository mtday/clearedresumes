package com.decojo.app.controller.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.decojo.app.TestApplication;
import com.decojo.common.model.LaborCategory;
import com.decojo.common.model.LaborCategoryCollection;
import com.decojo.db.LaborCategoryDao;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link LaborCategoryAdminController} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LaborCategoryAdminControllerIT {
    @Autowired
    private LaborCategoryDao laborCategoryDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Perform testing on the {@link LaborCategoryAdminController} class.
     */
    @Test
    public void test() {
        final LaborCategoryCollection beforeAdd = this.laborCategoryDao.getAll();
        assertEquals(25, beforeAdd.getLaborCategories().size()); // account for the flyway-inserted data

        final LaborCategory toAdd = new LaborCategory("ignored", "Labor Category");

        final ResponseEntity<LaborCategory> addResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .postForEntity("/api/admin/lcat", toAdd, LaborCategory.class);
        assertEquals(HttpStatus.OK, addResponse.getStatusCode());
        final LaborCategory added = addResponse.getBody();
        assertNotNull(added);
        assertNotEquals("ignored", added.getId()); // The labor category was given a unique id
        assertNotNull(UUID.fromString(added.getId()));
        assertEquals(toAdd.getName(), added.getName());

        final LaborCategoryCollection afterAdd = this.laborCategoryDao.getAll();
        assertEquals(26, afterAdd.getLaborCategories().size());
        assertTrue(afterAdd.getLaborCategories().contains(added));

        final LaborCategory toUpdate = new LaborCategory("ignored", "New Name");
        final ResponseEntity<LaborCategory> updateResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .exchange("/api/admin/lcat/{id}", HttpMethod.PUT, new HttpEntity<>(toUpdate), LaborCategory.class,
                        added.getId());
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        final LaborCategory updated = updateResponse.getBody();
        assertNotNull(updated);
        assertEquals(added.getId(), updated.getId());
        assertEquals(toUpdate.getName(), updated.getName());

        final LaborCategoryCollection afterUpdate = this.laborCategoryDao.getAll();
        assertEquals(26, afterUpdate.getLaborCategories().size());
        assertTrue(afterUpdate.getLaborCategories().contains(updated));

        final ResponseEntity<String> deleteResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .exchange("/api/admin/lcat/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, String.class, updated.getId());
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertNull(deleteResponse.getBody());

        final LaborCategoryCollection afterDelete = this.laborCategoryDao.getAll();
        assertEquals(25, afterDelete.getLaborCategories().size());
    }
}
