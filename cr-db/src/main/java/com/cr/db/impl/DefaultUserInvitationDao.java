package com.cr.db.impl;

import com.cr.common.model.UserInvitation;
import com.cr.db.UserInvitationDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the user invitation service.
 */
@Service
public class DefaultUserInvitationDao implements UserInvitationDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final UserInvitationMapper rowMapper = new UserInvitationMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultUserInvitationDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public UserInvitation getByEmail(@Nonnull final String email) {
        try {
            return this.jdbcTemplate
                    .queryForObject("SELECT * FROM user_invitations WHERE email = LOWER(?)", this.rowMapper, email);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Override
    public void add(@Nonnull final UserInvitation userInvitation) {
        this.jdbcTemplate
                .update("INSERT INTO user_invitations (id, email, company_id, created) VALUES (?, LOWER(?), ?, ?)",
                        userInvitation.getId(), userInvitation.getEmail(), userInvitation.getCompanyId(),
                        userInvitation.getCreated().format(FORMATTER));
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM user_invitations WHERE id = ?", id);
    }

    private static final class UserInvitationMapper implements RowMapper<UserInvitation> {
        @Override
        @Nonnull
        public UserInvitation mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String email = resultSet.getString("email");
            final String companyId = resultSet.getString("company_id");
            final LocalDateTime created = LocalDateTime.parse(resultSet.getString("created"), FORMATTER);
            return new UserInvitation(id, email, companyId, created);
        }
    }
}
