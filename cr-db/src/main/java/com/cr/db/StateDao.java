package com.cr.db;

import com.cr.common.model.State;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for state database management.
 */
public interface StateDao {
    /**
     * Retrieve all of the available states in the database.
     *
     * @return all of the available states that were found
     */
    @Nonnull
    SortedSet<State> getAll();

    /**
     * Retrieve the specified state from the database based on unique id.
     *
     * @param id the unique id of the state to retrieve
     * @return the requested state, possibly null if not found
     */
    @Nullable
    State get(@Nonnull String id);

    /**
     * Add a new state into the database.
     *
     * @param state the new state to insert
     */
    void add(@Nonnull State state);

    /**
     * Update the specified state in the database.
     *
     * @param state the state to update
     */
    void update(@Nonnull State state);

    /**
     * Remove the state from the database with the specified unique id.
     *
     * @param id the unique id of the state to be deleted
     */
    void delete(@Nonnull String id);
}
