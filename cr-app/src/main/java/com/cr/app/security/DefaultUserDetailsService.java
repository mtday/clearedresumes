package com.cr.app.security;

import com.cr.common.model.Account;
import com.cr.common.model.Authority;
import com.cr.common.model.Company;
import com.cr.common.model.ResumeContainer;
import com.cr.common.model.User;
import com.cr.db.CompanyDao;
import com.cr.db.ResumeContainerDao;
import com.cr.db.UserDao;
import java.util.Collection;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Responsible for retrieving user objects from the database.
 */
@Component
public class DefaultUserDetailsService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultUserDetailsService.class);

    @Nonnull
    private final UserDao userDao;
    @Nonnull
    private final CompanyDao companyDao;
    @Nonnull
    private final ResumeContainerDao resumeContainerDao;

    /**
     * Parameter constructor.
     *
     * @param userDao the {@link UserDao} used to retrieve user objects from the database
     * @param companyDao the {@link CompanyDao} used to retrieve company objects from the database
     * @param resumeContainerDao the {@link ResumeContainerDao} used to retrieve resume objects from the database
     */
    @Autowired
    public DefaultUserDetailsService(
            @Nonnull final UserDao userDao, @Nonnull final CompanyDao companyDao,
            @Nonnull final ResumeContainerDao resumeContainerDao) {
        this.userDao = userDao;
        this.companyDao = companyDao;
        this.resumeContainerDao = resumeContainerDao;
    }

    @Override
    @Nonnull
    public UserDetails loadUserByUsername(@Nonnull final String loginOrEmail) throws UsernameNotFoundException {
        final User user = this.userDao.getByLoginOrEmail(loginOrEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Failed to find user with name: " + loginOrEmail);
        }
        final Collection<Authority> authorities = this.userDao.getAuthorities(user.getId());
        final Collection<Company> companies = this.companyDao.getForUser(user.getId());
        final ResumeContainer resumeContainer = this.resumeContainerDao.getForUser(user.getId());
        final Account account = new Account(user, authorities, companies, resumeContainer);
        return new DefaultUserDetails(account);
    }
}
