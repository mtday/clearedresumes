package com.cr.db.impl;

import com.cr.common.model.Transaction;
import com.cr.common.model.TransactionCollection;
import com.cr.db.TransactionDao;
import java.math.BigDecimal;
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
 * Provides an implementation of the transaction service.
 */
@Service
public class DefaultTransactionDao implements TransactionDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final TransactionRowMapper rowMapper = new TransactionRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultTransactionDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public TransactionCollection getAll() {
        return new TransactionCollection(this.jdbcTemplate.query("SELECT * FROM transactions", this.rowMapper));
    }

    @Nullable
    @Override
    public Transaction get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM transactions WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public TransactionCollection getForCompany(@Nonnull final String companyId) {
        return new TransactionCollection(
                this.jdbcTemplate.query("SELECT * FROM transactions WHERE company_id = ?", this.rowMapper, companyId));
    }

    @Nonnull
    @Override
    public TransactionCollection getForUser(@Nonnull final String userId) {
        return new TransactionCollection(
                this.jdbcTemplate.query("SELECT * FROM transactions WHERE user_id = ?", this.rowMapper, userId));
    }

    @Override
    public void add(@Nonnull final Transaction transaction) {
        this.jdbcTemplate.update("INSERT INTO transactions (id, company_id, user_id, description, created, amount) "
                        + "VALUES (?, ?, ?, ?, ?, ?)", transaction.getId(), transaction.getCompanyId(), transaction
                .getUserId(),
                transaction.getDescription(), transaction.getCreated().format(FORMATTER), transaction.getAmount());
    }

    @Override
    public void update(@Nonnull final Transaction transaction) {
        this.jdbcTemplate.update("UPDATE transactions SET company_id = ?, user_id = ?, description = ?, "
                        + "created = ?, amount = ? WHERE id = ?", transaction.getCompanyId(), transaction.getUserId(),
                transaction.getDescription(), transaction.getCreated().format(FORMATTER), transaction.getAmount(),
                transaction.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM transactions WHERE id = ?", id);
    }

    private static final class TransactionRowMapper implements RowMapper<Transaction> {
        @Override
        @Nonnull
        public Transaction mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String companyId = resultSet.getString("company_id");
            final String userId = resultSet.getString("user_id");
            final String description = resultSet.getString("description");
            final LocalDateTime created = LocalDateTime.parse(resultSet.getString("created"), FORMATTER);
            final BigDecimal amount =
                    new BigDecimal(resultSet.getString("amount")).setScale(2, BigDecimal.ROUND_HALF_UP);
            return new Transaction(id, companyId, userId, description, created, amount);
        }
    }
}
