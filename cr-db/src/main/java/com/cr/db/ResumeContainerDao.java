package com.cr.db;

import com.cr.common.model.Filter;
import com.cr.common.model.MatchResult;
import com.cr.common.model.ResumeContainer;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Defines the interface required for retrieving fully populated resume containers.
 */
public interface ResumeContainerDao {
    /**
     * Retrieve the specified resume container from the database based on unique id.
     *
     * @param id the unique id of the resume to retrieve
     * @return the requested resume container, possibly null if not found
     */
    @Nullable
    ResumeContainer get(@Nonnull String id);

    /**
     * Retrieve the specified resume container from the database based on unique id, limiting the information returned
     * to that visible to the specified company.
     *
     * @param id the unique id of the resume to retrieve
     * @param companyId the unique id of the company that will view the resume
     * @return the requested resume container, possibly null if not found
     */
    @Nullable
    ResumeContainer get(@Nonnull String id, @Nonnull String companyId);

    /**
     * Retrieve the resume container associated with the specified user account.
     *
     * @param userId the unique id of the user that owns the resume
     * @return the requested resume container, possibly null if not found
     */
    @Nullable
    ResumeContainer getForUser(@Nonnull String userId);

    /**
     * Retrieve the filtered resume containers for the specified user account, company, and filters.
     *
     * @param userId the unique id of the user that wants to view resumes
     * @param companyId the unique id of the company that wants to view resumes
     * @param filter the filter to use when finding matching resumes
     * @return the requested resume containers along with the filter match result
     */
    @Nonnull
    SortedSet<Pair<ResumeContainer, MatchResult>> getFiltered(
            @Nonnull String userId, @Nonnull String companyId, @Nonnull Filter filter);
}
