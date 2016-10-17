package com.cr.db;

import com.cr.common.model.ResumeSummary;
import java.util.SortedSet;
import javax.annotation.Nonnull;

/**
 * Defines the interface required for retrieving resume summary information.
 */
public interface ResumeSummaryDao {
    /**
     * Retrieve the summary information for all resumes for the specified user account and company.
     *
     * @param userId the unique id of the user that wants to view resumes
     * @param companyId the unique id of the company that wants to view resumes
     * @return the requested resume summaries
     */
    @Nonnull
    SortedSet<ResumeSummary> getAll(@Nonnull String userId, @Nonnull String companyId);

    /**
     * Retrieve the summary information for liked resumes for the specified user account and company.
     *
     * @param userId the unique id of the user that wants to view resumes
     * @param companyId the unique id of the company that wants to view resumes
     * @return the requested resume summaries
     */
    @Nonnull
    SortedSet<ResumeSummary> getLiked(@Nonnull String userId, @Nonnull String companyId);

    /**
     * Retrieve the summary information for purchased resumes for the specified user account and company.
     *
     * @param userId the unique id of the user that wants to view resumes
     * @param companyId the unique id of the company that wants to view resumes
     * @return the requested resume summaries
     */
    @Nonnull
    SortedSet<ResumeSummary> getPurchased(@Nonnull String userId, @Nonnull String companyId);

    /**
     * Retrieve the summary information for ignored resumes for the specified user account and company.
     *
     * @param userId the unique id of the user that wants to view resumes
     * @param companyId the unique id of the company that wants to view resumes
     * @return the requested resume summaries
     */
    @Nonnull
    SortedSet<ResumeSummary> getIgnored(@Nonnull String userId, @Nonnull String companyId);
}
