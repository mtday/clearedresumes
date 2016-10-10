package com.decojo.app.security;

import com.decojo.common.model.User;
import com.decojo.db.UserDao;
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

    /**
     * Parameter constructor.
     *
     * @param userDao the {@link UserDao} used to retrieve user objects from the database
     */
    @Autowired
    public DefaultUserDetailsService(@Nonnull final UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Nonnull
    public UserDetails loadUserByUsername(@Nonnull final String username) throws UsernameNotFoundException {
        LOG.debug("Looking up user: {}", username);
        final User user = this.userDao.getByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Failed to find user with name: " + username);
        }
        return new DefaultUserDetails(user, this.userDao.getAuthorities(user.getId()));
    }
}
