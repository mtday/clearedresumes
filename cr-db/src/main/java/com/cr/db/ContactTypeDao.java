package com.cr.db;

import com.cr.common.model.ContactType;
import com.cr.common.model.ContactTypeCollection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for contact type database management.
 */
public interface ContactTypeDao {
    /**
     * Retrieve all of the available contact types in the database.
     *
     * @return all of the available contact types that were found
     */
    @Nonnull
    ContactTypeCollection getAll();

    /**
     * Retrieve the specified contact type from the database based on unique id.
     *
     * @param id the unique id of the contact type to retrieve
     * @return the requested contact type, possibly null if not found
     */
    @Nullable
    ContactType get(@Nonnull String id);

    /**
     * Add a new contact type into the database.
     *
     * @param contactType the new contact type to insert
     */
    void add(@Nonnull ContactType contactType);

    /**
     * Update the specified contact type in the database.
     *
     * @param contactType the contact type to update
     */
    void update(@Nonnull ContactType contactType);

    /**
     * Remove the contact type from the database with the specified unique id.
     *
     * @param id the unique id of the contact type to be deleted
     */
    void delete(@Nonnull String id);
}
