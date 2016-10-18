package com.cr.db.impl;

import com.cr.common.model.Transaction;
import com.cr.common.model.TransactionContainer;
import com.cr.common.model.User;
import com.cr.db.TransactionContainerDao;
import com.cr.db.TransactionDao;
import com.cr.db.UserDao;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the resume container service.
 */
@Service
public class DefaultTransactionContainerDao implements TransactionContainerDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final UserDao userDao;
    @Nonnull
    private final TransactionDao transactionDao;

    /**
     * Create an instance of this service.
     *
     * @param userDao the {@link UserDao} used to retrieve user accounts
     * @param transactionDao the {@link TransactionDao} used to retrieve transactions
     */
    @Autowired
    public DefaultTransactionContainerDao(
            @Nonnull final UserDao userDao, @Nonnull final TransactionDao transactionDao) {
        this.userDao = userDao;
        this.transactionDao = transactionDao;
    }

    @Nullable
    @Override
    public TransactionContainer get(@Nonnull final String id) {
        final Transaction transaction = this.transactionDao.get(id);
        if (transaction != null) {
            final User user = this.userDao.get(transaction.getUserId());
            if (user != null) {
                return new TransactionContainer(user, transaction);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public SortedSet<TransactionContainer> getForCompany(@Nonnull final String companyId) {
        return build(this.transactionDao.getForCompany(companyId));
    }

    @Nullable
    private SortedSet<TransactionContainer> build(@Nonnull final SortedSet<Transaction> transactions) {
        final SortedSet<TransactionContainer> transactionContainers = new TreeSet<>();
        if (transactions.isEmpty()) {
            return transactionContainers;
        }
        final Set<String> userIds = transactions.stream().map(Transaction::getUserId).collect(Collectors.toSet());
        final Map<String, User> userMap = new HashMap<>();
        this.userDao.get(userIds).forEach(user -> userMap.put(user.getId(), user));
        transactions.forEach(transaction -> transactionContainers
                .add(new TransactionContainer(userMap.get(transaction.getUserId()), transaction)));
        return transactionContainers;
    }
}
