package com.decojo.db;

import com.decojo.common.model.PolygraphType;
import com.decojo.common.model.PolygraphTypeCollection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for polygraph type database management.
 */
public interface PolygraphTypeDao {
    /**
     * Retrieve all of the available polygraph types in the database.
     *
     * @return all of the available polygraph types that were found
     */
    @Nonnull
    PolygraphTypeCollection getAll();

    /**
     * Retrieve the specified polygraph type from the database based on unique id.
     *
     * @param id the unique id of the polygraph type to retrieve
     * @return the requested polygraph type, possibly null if not found
     */
    @Nullable
    PolygraphType get(@Nonnull String id);

    /**
     * Add a new polygraph type into the database.
     *
     * @param polygraphType the new polygraph type to insert
     */
    void add(@Nonnull PolygraphType polygraphType);

    /**
     * Update the specified polygraph type in the database.
     *
     * @param polygraphType the polygraph type to update
     */
    void update(@Nonnull PolygraphType polygraphType);

    /**
     * Remove the polygraph type from the database with the specified unique id.
     *
     * @param id the unique id of the polygraph type to be deleted
     */
    void delete(@Nonnull String id);
}
