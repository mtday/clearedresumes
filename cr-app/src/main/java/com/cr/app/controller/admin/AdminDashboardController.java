package com.cr.app.controller.admin;

import com.cr.app.controller.BaseController;
import com.cr.common.model.Authority;
import com.cr.common.model.Certification;
import com.cr.common.model.Clearance;
import com.cr.common.model.ClearanceType;
import com.cr.common.model.ContactInfo;
import com.cr.common.model.Education;
import com.cr.common.model.KeyWord;
import com.cr.common.model.LaborCategory;
import com.cr.common.model.PolygraphType;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeIntroduction;
import com.cr.common.model.ResumeLaborCategory;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.State;
import com.cr.common.model.User;
import com.cr.common.model.WorkLocation;
import com.cr.common.model.WorkSummary;
import com.cr.db.CertificationDao;
import com.cr.db.ClearanceDao;
import com.cr.db.ClearanceTypeDao;
import com.cr.db.ContactInfoDao;
import com.cr.db.EducationDao;
import com.cr.db.KeyWordDao;
import com.cr.db.LaborCategoryDao;
import com.cr.db.PolygraphTypeDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
import com.cr.db.ResumeLaborCategoryDao;
import com.cr.db.StateDao;
import com.cr.db.UserDao;
import com.cr.db.WorkLocationDao;
import com.cr.db.WorkSummaryDao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to manage the admin dashboard page.
 */
@Controller
public class AdminDashboardController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminDashboardController.class);

    @Nonnull
    private final PasswordEncoder passwordEncoder;
    @Nonnull
    private final UserDao userDao;
    @Nonnull
    private final ResumeDao resumeDao;
    @Nonnull
    private final ResumeIntroductionDao resumeIntroductionDao;
    @Nonnull
    private final ResumeLaborCategoryDao resumeLaborCategoryDao;
    @Nonnull
    private final ContactInfoDao contactInfoDao;
    @Nonnull
    private final WorkLocationDao workLocationDao;
    @Nonnull
    private final ClearanceDao clearanceDao;
    @Nonnull
    private final EducationDao educationDao;
    @Nonnull
    private final CertificationDao certificationDao;
    @Nonnull
    private final WorkSummaryDao workSummaryDao;
    @Nonnull
    private final KeyWordDao keyWordDao;
    @Nonnull
    private final LaborCategoryDao laborCategoryDao;
    @Nonnull
    private final StateDao stateDao;
    @Nonnull
    private final ClearanceTypeDao clearanceTypeDao;
    @Nonnull
    private final PolygraphTypeDao polygraphTypeDao;

    /**
     * Create an instance of this controller.
     *
     * @param passwordEncoder used to encode user passwords in the database
     * @param userDao the DAO used to manage user accounts in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeIntroductionDao the DAO used to manage resume introductions in the database
     * @param resumeLaborCategoryDao the DAO used to manage resume labor categories in the database
     * @param contactInfoDao the DAO used to manage resume contact info in the database
     * @param workLocationDao the DAO used to manage work locations in the database
     * @param clearanceDao the DAO used to manage clearances in the database
     * @param educationDao the DAO used to manage education in the database
     * @param certificationDao the DAO used to manage certifications in the database
     * @param workSummaryDao the DAO used to manage work summaries in the database
     * @param keyWordDao the DAO used to manage key words in the database
     * @param laborCategoryDao the DAO used to manage labor categories in the database
     * @param stateDao the DAO used to manage states in the database
     * @param clearanceTypeDao the DAO used to manage clearance types in the database
     * @param polygraphTypeDao the DAO used to manage polygraph types in the database
     */
    @Autowired
    public AdminDashboardController(
            @Nonnull final PasswordEncoder passwordEncoder, @Nonnull final UserDao userDao,
            @Nonnull final ResumeDao resumeDao, @Nonnull final ResumeIntroductionDao resumeIntroductionDao,
            @Nonnull final ResumeLaborCategoryDao resumeLaborCategoryDao, @Nonnull final ContactInfoDao contactInfoDao,
            @Nonnull final WorkLocationDao workLocationDao, @Nonnull final ClearanceDao clearanceDao,
            @Nonnull final EducationDao educationDao, @Nonnull final CertificationDao certificationDao,
            @Nonnull final WorkSummaryDao workSummaryDao, @Nonnull final KeyWordDao keyWordDao,
            @Nonnull final LaborCategoryDao laborCategoryDao, @Nonnull final StateDao stateDao,
            @Nonnull final ClearanceTypeDao clearanceTypeDao, @Nonnull final PolygraphTypeDao polygraphTypeDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.resumeDao = resumeDao;
        this.resumeIntroductionDao = resumeIntroductionDao;
        this.resumeLaborCategoryDao = resumeLaborCategoryDao;
        this.contactInfoDao = contactInfoDao;
        this.workLocationDao = workLocationDao;
        this.clearanceDao = clearanceDao;
        this.educationDao = educationDao;
        this.certificationDao = certificationDao;
        this.workSummaryDao = workSummaryDao;
        this.keyWordDao = keyWordDao;
        this.laborCategoryDao = laborCategoryDao;
        this.stateDao = stateDao;
        this.clearanceTypeDao = clearanceTypeDao;
        this.polygraphTypeDao = polygraphTypeDao;
    }

    /**
     * Display the admin dashboard page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/admin/dashboard")
    @Nonnull
    public String dashboard(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "admin/dashboard";
    }

    /**
     * Generate some random resumes.
     *
     * @param count the number of random resumes to generate
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/admin/generate/resumes")
    @Nonnull
    public String generateResumes(
            @RequestParam(value = "count", defaultValue = "50") final int count,
            @Nonnull final Map<String, Object> model) {
        final List<LaborCategory> laborCategories =
                new ArrayList<>(this.laborCategoryDao.getAll().getLaborCategories());
        final List<State> states = new ArrayList<>(this.stateDao.getAll().getStates());
        final List<ClearanceType> clearanceTypes = new ArrayList<>(this.clearanceTypeDao.getAll().getClearanceTypes());
        final List<PolygraphType> polygraphTypes = new ArrayList<>(this.polygraphTypeDao.getAll().getPolygraphTypes());
        final List<String> words =
                Arrays.stream(getRandomText().split("[,\\s]+")).map(word -> word.toLowerCase(Locale.ENGLISH))
                        .map(word -> word.replaceAll("\\.", "")).collect(Collectors.toList());
        final List<String> organizations =
                Arrays.asList("National Security Agency", "Central Intelligence Agency", "NSA", "CIA", "NGA", "NRO",
                        "U.S. Army", "Army", "U.S. State Department");
        final List<String> institutions =
                Arrays.asList("WVU", "UMBC", "West Virginia University", "University of Maryland",
                        "University of Alabama");
        final List<String> degrees = Arrays.asList("B.S.", "M.S.", "BS", "Bachelor of Science", "None");
        final List<String> fields =
                Arrays.asList("Computer Science", "Computer Engineering", "Electrical Engineering", "General Studies");
        final List<String> certifications = Arrays.asList("Security+", "Network+", "Project Management Professional",
                "Microsoft Certified Professional", "CCIE");
        final List<String> jobTitles =
                Arrays.asList("Junior Software Developer", "Software Engineer", "Application Engineer",
                        "Development Engineer", "Senior Software Engineer", "Lead Engineer");
        final List<String> employers = Arrays.asList("Prior Employer, LLC", "Previous Company, Inc.", "Another Company",
                "Awesome Company, LLC", "Workaholics Anonymous");

        final Random random = new Random();
        for (int resumeNum = 0; resumeNum < count; resumeNum++) {
            final int userNum = random.nextInt(Integer.MAX_VALUE); // specifying max here to get a positive integer
            final String login = String.format("login-%d", userNum);
            final String email = String.format("email-%d@clearedresumes.com", userNum);
            final String password = this.passwordEncoder.encode("password");
            final User user = new User(UUID.randomUUID().toString(), login, email, password, true);
            this.userDao.add(user);
            this.userDao.addAuthority(user.getId(), Authority.USER);

            final LocalDateTime created = LocalDateTime.now().minusDays(-random.nextInt(20));
            final LocalDateTime expiration = created.plusDays(14);
            final Resume resume =
                    new Resume(UUID.randomUUID().toString(), user.getId(), ResumeStatus.PUBLISHED, created, expiration);
            this.resumeDao.add(resume);

            final String fullName = String.format("Full Name %d", userNum);
            final String objective = getRandomText();
            final ResumeIntroduction resumeIntroduction = new ResumeIntroduction(resume.getId(), fullName, objective);
            this.resumeIntroductionDao.add(resumeIntroduction);

            final int lcatCount = random.nextFloat() < 0.05f ? 2 : 1; // 5% of the time, a user will have two lcats.
            final Collection<String> lcats = new TreeSet<>();
            for (int lcatNum = 0; lcatNum < lcatCount; lcatNum++) {
                lcats.add(laborCategories.get(random.nextInt(laborCategories.size())).getName());
            }
            for (final String lcat : lcats) {
                final ResumeLaborCategory resumeLcat =
                        new ResumeLaborCategory(UUID.randomUUID().toString(), resume.getId(), lcat, random.nextInt(20));
                this.resumeLaborCategoryDao.add(resumeLcat);
            }

            final int contactCount = random.nextInt(3);
            for (int contactNum = 0; contactNum < contactCount; contactNum++) {
                final String phoneNum = String.format("%03d-%03d-%04d", random.nextInt(1000), random.nextInt(1000),
                        random.nextInt(10000));
                final ContactInfo contactInfo = new ContactInfo(UUID.randomUUID().toString(), resume.getId(), phoneNum);
                this.contactInfoDao.add(contactInfo);
            }

            final int locationCount = random.nextInt(2);
            for (int locationNum = 0; locationNum < locationCount; locationNum++) {
                final String state = states.get(random.nextInt(states.size())).getName();
                final String region = String.format("Region %d", random.nextInt());
                final WorkLocation workLocation =
                        new WorkLocation(UUID.randomUUID().toString(), resume.getId(), state, region);
                this.workLocationDao.add(workLocation);
            }

            final int wordCount = random.nextInt(40);
            final Collection<String> keyWords = new TreeSet<>();
            for (int wordNum = 0; wordNum < wordCount; wordNum++) {
                keyWords.add(words.get(random.nextInt(words.size())));
            }
            if (!keyWords.isEmpty()) {
                this.keyWordDao.add(keyWords.stream().map(word -> new KeyWord(resume.getId(), word))
                        .collect(Collectors.toSet()));
            }

            final Clearance clearance = new Clearance(UUID.randomUUID().toString(), resume.getId(),
                    clearanceTypes.get(random.nextInt(clearanceTypes.size())).getName(),
                    organizations.get(random.nextInt(organizations.size())),
                    polygraphTypes.get(random.nextInt(polygraphTypes.size())).getName());
            this.clearanceDao.add(clearance);

            final int educationCount = random.nextInt(3);
            for (int educationNum = 0; educationNum < educationCount; educationNum++) {
                final Education education = new Education(UUID.randomUUID().toString(), resume.getId(),
                        institutions.get(random.nextInt(institutions.size())),
                        fields.get(random.nextInt(fields.size())), degrees.get(random.nextInt(degrees.size())),
                        2016 - random.nextInt(20));
                this.educationDao.add(education);
            }

            final int certificationCount = random.nextInt(3);
            for (int certificationNum = 0; certificationNum < certificationCount; certificationNum++) {
                final Certification certification = new Certification(UUID.randomUUID().toString(), resume.getId(),
                        certifications.get(random.nextInt(certifications.size())), 2016 - random.nextInt(20));
                this.certificationDao.add(certification);
            }

            final int workSummaryCount = 2 + random.nextInt(5);
            for (int workSummaryNum = 0; workSummaryNum < workSummaryCount; workSummaryNum++) {
                final String jobTitle = jobTitles.get(random.nextInt(jobTitles.size()));
                final boolean includeEmployer = random.nextBoolean();
                final String employer = includeEmployer ? employers.get(random.nextInt(employers.size())) : "";
                final LocalDate begin = LocalDate.now().minusDays(random.nextInt(10000));
                final boolean includeEnd = random.nextBoolean();
                final LocalDate end = includeEnd ? begin.plusDays(random.nextInt(1000)) : null;
                final WorkSummary workSummary =
                        new WorkSummary(UUID.randomUUID().toString(), resume.getId(), jobTitle, employer, begin, end,
                                getRandomText());
                this.workSummaryDao.add(workSummary);
            }

            LOG.info("Created random user resume: {}", login);
        }

        setCurrentAccount(model);
        return "redirect:/employer/dashboard";
    }

    @Nonnull
    private String getRandomText() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vehicula nulla lacus, et molestie "
                + "nunc dictum quis. Praesent tellus turpis, suscipit et mauris ac, pulvinar auctor tellus. Donec "
                + "lectus neque, rhoncus nec posuere sollicitudin, pharetra vel quam. Duis nec nunc nisl. Fusce "
                + "ultrices ut orci a mattis. Cras eleifend magna risus, id scelerisque enim tempor id. Quisque "
                + "venenatis congue magna, quis fringilla orci ultricies eget. Sed pharetra libero in sem facilisis, "
                + "a semper nisi egestas.";
    }
}
