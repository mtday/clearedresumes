package com.cr.db.impl;

import com.cr.common.model.Authority;
import com.cr.common.model.Resume;
import com.cr.common.model.User;
import com.cr.db.UserDao;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
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
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
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

    @Override
    public boolean loginExists(@Nonnull final String login) {
        return getByLogin(login) != null;
    }

    @Override
    public boolean emailExists(@Nonnull final String email) {
        return getByEmail(email) != null;
    }

    @Nonnull
    @Override
    public SortedSet<User> getAll() {
        return new TreeSet<>(this.jdbcTemplate.query("SELECT * FROM users", this.userMapper));
    }

    @Nonnull
    @Override
    public Map<String, User> getForResumes(@Nonnull final Map<String, Resume> resumeMap) {
        final Map<String, User> userIdMap = new HashMap<>();
        final Map<String, User> resumeIdMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Set<String> userIdSet =
                    resumeMap.values().stream().map(Resume::getUserId).collect(Collectors.toSet());
            final Array userIds = connection.createArrayOf("VARCHAR", userIdSet.toArray());
            final PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id = ANY (?)");
            ps.setArray(1, userIds);
            return ps;
        }, this.userMapper).forEach(user -> userIdMap.put(user.getId(), user));
        resumeMap.values().forEach(resume -> resumeIdMap.put(resume.getId(), userIdMap.get(resume.getUserId())));
        return resumeIdMap;
    }

    @Nonnull
    @Override
    public SortedSet<User> get(@Nonnull final Collection<String> ids) {
        return new TreeSet<>(this.jdbcTemplate.query(connection -> {
            final Array userIds = connection.createArrayOf("VARCHAR", ids.toArray());
            final PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id = ANY (?)");
            ps.setArray(1, userIds);
            return ps;
        }, this.userMapper));
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

    @Nullable
    @Override
    public User getByEmail(@Nonnull final String email) {
        try {
            return this.jdbcTemplate
                    .queryForObject("SELECT * FROM users WHERE email = LOWER(?)", this.userMapper, email);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nullable
    @Override
    public User getByLoginOrEmail(@Nonnull final String loginOrEmail) {
        try {
            return this.jdbcTemplate
                    .queryForObject("SELECT * FROM users WHERE login = ? OR email = LOWER(?)", this.userMapper,
                            loginOrEmail, loginOrEmail);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public SortedSet<User> getForCompany(@Nonnull final String companyId) {
        return new TreeSet<>(this.jdbcTemplate
                .query("SELECT users.* FROM users JOIN company_users ON (users.id = company_users.user_id) "
                        + "WHERE company_users.company_id = ?", this.userMapper, companyId));
    }

    @Override
    public void add(@Nonnull final User user) {
        this.jdbcTemplate
                .update("INSERT INTO users (id, login, email, password, enabled) VALUES (?, ?, LOWER(?), ?, ?)",
                        user.getId(), user.getLogin(), user.getEmail(), user.getPassword(), user.isEnabled());
    }

    @Override
    public void update(@Nonnull final User user) {
        this.jdbcTemplate
                .update("UPDATE users SET login = ?, email = LOWER(?), enabled = ? WHERE id = ?", user.getLogin(),
                        user.getEmail(), user.isEnabled(), user.getId());
    }

    @Override
    public void setPassword(@Nonnull final String id, @Nonnull final String encodedPassword) {
        this.jdbcTemplate.update("UPDATE users SET password = ? WHERE id = ?", encodedPassword, id);
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    @Nonnull
    @Override
    public SortedSet<Authority> getAuthorities(@Nonnull final String id) {
        return new TreeSet<>(this.jdbcTemplate.query("SELECT authorities.authority FROM users JOIN authorities "
                + "ON (users.id = authorities.user_id) WHERE users.id = ?", this.authMapper, id));
    }

    @Override
    public void addAuthority(@Nonnull final String id, @Nonnull final Authority authority) {
        this.jdbcTemplate.update("INSERT INTO authorities (user_id, authority) VALUES (?, ?)", id, authority.name());
    }

    @Override
    public void deleteAuthority(@Nonnull final String id, @Nonnull final Authority authority) {
        this.jdbcTemplate.update("DELETE FROM authorities WHERE user_id = ? AND authority = ?", id, authority.name());
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

    private static final class AuthorityRowMapper implements RowMapper<Authority> {
        @Override
        @Nonnull
        public Authority mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            return Authority.valueOf(resultSet.getString("authority"));
        }
    }
}
