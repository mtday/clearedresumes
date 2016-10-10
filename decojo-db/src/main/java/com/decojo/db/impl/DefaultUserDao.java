package com.decojo.db.impl;

import com.decojo.common.model.User;
import com.decojo.common.model.UserCollection;
import com.decojo.db.UserDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the user service.
 */
@Service
public class DefaultUserDao implements UserDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final UserRowMapper userMapper = new UserRowMapper();
    @Nonnull
    private final AuthorityRowMapper authMapper = new AuthorityRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultUserDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public UserCollection getAll() {
        return new UserCollection(this.jdbcTemplate.query("SELECT * FROM users", this.userMapper));
    }

    @Nullable
    @Override
    public User get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", this.userMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nullable
    @Override
    public User getByLogin(@Nonnull final String login) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE login = ?", this.userMapper, login);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Override
    public void add(@Nonnull final User user) {
        this.jdbcTemplate
                .update("INSERT INTO users (id, login, email, password, enabled) VALUES (?, ?, ?, ?, ?)", user.getId(),
                        user.getLogin(), user.getEmail(), user.getPassword(), user.isEnabled());
    }

    @Override
    public void update(@Nonnull final User user) {
        this.jdbcTemplate.update("UPDATE users SET login = ?, email = ?, enabled = ? WHERE id = ?", user.getLogin(),
                user.getEmail(), user.isEnabled(), user.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    @Nonnull
    @Override
    public SortedSet<String> getAuthorities(@Nonnull final String id) {
        return new TreeSet<>(this.jdbcTemplate.query("SELECT authorities.authority FROM users JOIN authorities "
                + "ON (users.id = authorities.user_id) WHERE users.id = ?", this.authMapper, id));
    }

    @Override
    public void addAuthority(@Nonnull final String id, @Nonnull final String authority) {
        this.jdbcTemplate.update("INSERT INTO authorities (user_id, authority) VALUES (?, ?)", id, authority);
    }

    @Override
    public void deleteAuthority(@Nonnull final String id, @Nonnull final String authority) {
        this.jdbcTemplate.update("DELETE FROM authorities WHERE user_id = ? AND authority = ?", id, authority);
    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        @Nonnull
        public User mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String login = resultSet.getString("login");
            final String email = resultSet.getString("email");
            final String password = resultSet.getString("password");
            final boolean enabled = resultSet.getBoolean("enabled");
            return new User(id, login, email, password, enabled);
        }
    }

    private static final class AuthorityRowMapper implements RowMapper<String> {
        @Override
        @Nonnull
        public String mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            return resultSet.getString("authority");
        }
    }
}
