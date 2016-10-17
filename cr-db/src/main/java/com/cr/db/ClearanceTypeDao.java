package com.cr.db;

import com.cr.common.model.ClearanceType;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for clearance type database management.
 */
public interface ClearanceTypeDao {
    /**
     * Retrieve all of the available clearance types in the database.
     *
     * @return all of the available clearance types that were found
     */
    @Nonnull
    SortedSet<ClearanceType> getAll();

    /**
     * Retrieve the specified clearance type from the database based on unique id.
     *
     * @param id the unique id of the clearance type to retrieve
     * @return the requested clearance type, possibly null if not found
     */
    @Nullable
    ClearanceType get(@Nonnull String id);

    /**
     * Add a new clearance type into the database.
     *
     * @param clearanceType the new clearance type to insert
     */
    void add(@Nonnull ClearanceType clearanceType);

    /**
     * Update the specified clearance type in the database.
     *
     * @param clearanceType the clearance type to update
     */
    void update(@Nonnull ClearanceType clearanceType);

    /**
     * Remove the clearance type from the database with the specified unique id.
     *
     * @param id the unique id of the clearance type to be deleted
     */
    void delete(@Nonnull String id);
}
