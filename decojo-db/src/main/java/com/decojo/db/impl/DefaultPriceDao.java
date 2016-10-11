package com.decojo.db.impl;

import com.decojo.common.model.Price;
import com.decojo.common.model.PriceType;
import com.decojo.db.PriceDao;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the price service.
 */
@Service
public class DefaultPriceDao implements PriceDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final PriceRowMapper rowMapper = new PriceRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultPriceDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public Price get(@Nonnull final PriceType type) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM prices WHERE type = ?", this.rowMapper, type.name());
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Override
    public void add(@Nonnull final Price price) {
        this.jdbcTemplate
                .update("INSERT INTO prices (type, price) VALUES (?, ?)", price.getType().name(), price.getPrice());
    }

    @Override
    public void update(@Nonnull final Price price) {
        this.jdbcTemplate
                .update("UPDATE prices SET price = ? WHERE type = ?", price.getPrice(), price.getType().name());
    }

    @Override
    public void delete(@Nonnull final PriceType type) {
        this.jdbcTemplate.update("DELETE FROM prices WHERE type = ?", type.name());
    }

    private static final class PriceRowMapper implements RowMapper<Price> {
        @Override
        @Nonnull
        public Price mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final PriceType type = PriceType.valueOf(resultSet.getString("type"));
            final BigDecimal amount =
                    new BigDecimal(resultSet.getString("price")).setScale(2, BigDecimal.ROUND_HALF_UP);
            return new Price(type, amount);
        }
    }
}
