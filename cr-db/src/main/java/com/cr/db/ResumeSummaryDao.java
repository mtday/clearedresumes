package com.cr.db;

import com.cr.common.model.ResumeSummary;
import java.util.SortedSet;
import javax.annotation.Nonnull;

/**
 * Defines the interface required for retrieving resume summary information.
 */
public interface ResumeSummaryDao {
    /**
     * Retrieve the resume summary information for the specified user account and company.
     *
     * @param userId the unique id of the user that wants to view resumes
     * @param companyId the unique id of the company that wants to view resumes
     * @return the requested resume summaries
     */
    @Nonnull
    SortedSet<ResumeSummary> getAll(@Nonnull String userId, @Nonnull String companyId);
}
