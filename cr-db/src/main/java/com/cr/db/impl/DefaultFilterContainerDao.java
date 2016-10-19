package com.cr.db.impl;

import com.cr.common.model.Filter;
import com.cr.common.model.FilterContainer;
import com.cr.common.model.FilterContent;
import com.cr.common.model.FilterLaborCategory;
import com.cr.common.model.FilterLocation;
import com.cr.db.FilterContainerDao;
import com.cr.db.FilterContentDao;
import com.cr.db.FilterDao;
import com.cr.db.FilterLaborCategoryDao;
import com.cr.db.FilterLocationDao;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the filter container service.
 */
@Service
public class DefaultFilterContainerDao implements FilterContainerDao {
    @Nonnull
    private final FilterDao filterDao;
    @Nonnull
    private final FilterLocationDao filterLocationDao;
    @Nonnull
    private final FilterLaborCategoryDao filterLaborCategoryDao;
    @Nonnull
    private final FilterContentDao filterContentDao;

    /**
     * Create an instance of this service.
     *
     * @param filterDao the {@link FilterDao} used to retrieve filters
     * @param filterLocationDao the {@link FilterLocationDao} used to retrieve filter reviews
     * @param filterLaborCategoryDao the {@link FilterLaborCategoryDao} used to retrieve filter labor categories
     * @param filterContentDao the {@link FilterContentDao} used to retrieve filter contact info
     */
    @Autowired
    public DefaultFilterContainerDao(
            @Nonnull final FilterDao filterDao, @Nonnull final FilterLocationDao filterLocationDao,
            @Nonnull final FilterLaborCategoryDao filterLaborCategoryDao,
            @Nonnull final FilterContentDao filterContentDao) {
        this.filterDao = filterDao;
        this.filterLocationDao = filterLocationDao;
        this.filterLaborCategoryDao = filterLaborCategoryDao;
        this.filterContentDao = filterContentDao;
    }

    @Nullable
    @Override
    public FilterContainer get(@Nonnull final String id) {
        final Filter filter = this.filterDao.get(id);
        if (filter != null) {
            final SortedSet<FilterLocation> locations = this.filterLocationDao.getForFilter(id);
            final SortedSet<FilterLaborCategory> lcats = this.filterLaborCategoryDao.getForFilter(id);
            final SortedSet<FilterContent> contents = this.filterContentDao.getForFilter(id);
            return new FilterContainer(filter, locations, lcats, contents);
        }
        return null;
    }

    @Nonnull
    @Override
    public SortedSet<FilterContainer> getForCompany(@Nonnull final String companyId) {
        return build(this.filterDao.getForCompany(companyId));
    }

    @Nonnull
    private SortedSet<FilterContainer> build(@Nonnull final SortedSet<Filter> filters) {
        final SortedSet<FilterContainer> filterContainers = new TreeSet<>();
        if (filters.isEmpty()) {
            return filterContainers;
        }

        final Map<String, Filter> filterMap = new HashMap<>();
        filters.forEach(filter -> filterMap.put(filter.getId(), filter));

        final Map<String, Collection<FilterLocation>> locationMap = this.filterLocationDao.getForFilters(filterMap);
        final Map<String, Collection<FilterLaborCategory>> lcatMap =
                this.filterLaborCategoryDao.getForFilters(filterMap);
        final Map<String, Collection<FilterContent>> contentMap = this.filterContentDao.getForFilters(filterMap);

        filterMap.values().forEach(filter -> {
            final Collection<FilterLocation> locations =
                    Optional.ofNullable(locationMap.get(filter.getId())).orElse(Collections.emptyList());
            final Collection<FilterLaborCategory> lcats =
                    Optional.ofNullable(lcatMap.get(filter.getId())).orElse(Collections.emptyList());
            final Collection<FilterContent> contents =
                    Optional.ofNullable(contentMap.get(filter.getId())).orElse(Collections.emptyList());

            filterContainers.add(new FilterContainer(filter, locations, lcats, contents));
        });

        return filterContainers;
    }
}
