package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.cr.common.model.Price;
import com.cr.common.model.PriceCollection;
import com.cr.common.model.PriceType;
import com.cr.db.PriceDao;
import com.cr.db.TestApplication;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultPriceDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultPriceDaoIT {
    @Autowired
    private PriceDao priceDao;

    /**
     * Perform testing on the auto-wired {@link DefaultPriceDao} instance.
     */
    @Test
    public void test() {
        final PriceCollection prices = this.priceDao.getAll();
        assertNotNull(prices);
        assertEquals(3, prices.getPrices().size());

        final Price price = this.priceDao.get(PriceType.ENTERPRISE_PACKAGE);
        assertNotNull(price);
        assertEquals(PriceType.ENTERPRISE_PACKAGE, price.getType());

        final Price updated = new Price(price.getType(), new BigDecimal("4.56").setScale(2, BigDecimal.ROUND_HALF_UP));
        this.priceDao.update(updated);

        final Price afterUpdate = this.priceDao.get(price.getType());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.priceDao.delete(price.getType());

        final Price afterDelete = this.priceDao.get(price.getType());
        assertNull(afterDelete);

        this.priceDao.add(price);

        final Price afterAdd = this.priceDao.get(PriceType.ENTERPRISE_PACKAGE);
        assertNotNull(afterAdd);
        assertEquals(price, afterAdd);
    }
}
