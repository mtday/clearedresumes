package com.decojo.db;

import com.decojo.common.model.Price;
import com.decojo.common.model.PriceType;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for pricing database management.
 */
public interface PriceDao {
    /**
     * Retrieve the price for the specified type from the database.
     *
     * @param type the type of price to retrieve
     * @return the requested price, possibly null if not found
     */
    @Nullable
    Price get(@Nonnull PriceType type);

    /**
     * Add a new price into the database.
     *
     * @param price the new price to insert
     */
    void add(@Nonnull Price price);

    /**
     * Update the specified price in the database.
     *
     * @param price the price to update
     */
    void update(@Nonnull Price price);

    /**
     * Remove the price from the database with the specified type.
     *
     * @param type the type of price to be deleted
     */
    void delete(@Nonnull PriceType type);
}
