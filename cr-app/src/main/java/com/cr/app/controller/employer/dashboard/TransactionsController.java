package com.cr.app.controller.employer.dashboard;

import com.cr.common.model.Company;
import com.cr.common.model.TransactionContainer;
import com.cr.db.CompanyDao;
import com.cr.db.ResumeDao;
import com.cr.db.TransactionContainerDao;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the employer dashboard page showing the employer company transactions.
 */
@Controller
public class TransactionsController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionsController.class);

    @Nonnull
    private final TransactionContainerDao transactionContainerDao;

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param companyDao the {@link CompanyDao} used to retrieve companies from the database
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     * @param transactionContainerDao the {@link TransactionContainerDao} used to retrieve transactions from the
     * database
     */
    @Autowired
    public TransactionsController(
            @Nonnull final HttpSession httpSession, @Nonnull final CompanyDao companyDao,
            @Nonnull final ResumeDao resumeDao, @Nonnull final TransactionContainerDao transactionContainerDao) {
        super(httpSession, companyDao, resumeDao);
        this.transactionContainerDao = transactionContainerDao;
    }

    /**
     * Display the employer dashboard page that shows the company transactions.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/transactions")
    @Nonnull
    public String transactions(@Nonnull final Map<String, Object> model) {
        final Company company = getCurrentCompany();
        if (company != null) {
            final SortedSet<TransactionContainer> transactions =
                    this.transactionContainerDao.getForCompany(company.getId());
            model.put("transactions", transactions);
        }

        setCurrentCompany(model);
        setCurrentAccount(model);
        return "employer/dashboard/transactions";
    }
}
