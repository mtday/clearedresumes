package com.cr.app.controller.user.resume;

import com.cr.common.model.KeyWord;
import com.cr.common.model.ResumeContainer;
import com.cr.db.KeyWordDao;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to manage the user resume key words page.
 */
@Controller
public class ResumeKeyWordsController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeKeyWordsController.class);

    @Nonnull
    private final KeyWordDao keyWordDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     * @param keyWordDao the DAO used to manage resume key words in the database
     */
    @Autowired
    public ResumeKeyWordsController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeIntroductionDao resumeOverviewDao, @Nonnull final KeyWordDao keyWordDao) {
        super(resumeContainerDao, resumeDao, resumeOverviewDao);
        this.keyWordDao = keyWordDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resume = createResumeContainer();
        model.put("resume", resume);
        model.put("keyWords", resume.getKeyWords());

        final boolean complete = resume.isKeyWordsComplete();
        model.put("keyWordStatusColor", complete ? "success" : "info");
        model.put("keyWordStatus", complete ? "Complete" : "Incomplete");

        setCurrentAccount(model);
    }

    /**
     * Display the user resume key word page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/key-words")
    @Nonnull
    public String keyWord(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "user/resume/key-words";
    }

    /**
     * Add user key words.
     *
     * @param words the additional key words to add
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/key-words")
    @Nonnull
    public String keyWordSave(
            @Nonnull @RequestParam(value = "words", defaultValue = "") final String words,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(words)) {
            model.put("keyWordMessage", "Please enter one or more valid key words.");
            populateModel(model);
            return "user/resume/key-words";
        }

        final ResumeContainer resumeContainer = createResumeContainer();

        final Set<String> existingWords =
                this.keyWordDao.getForResume(resumeContainer.getResume().getId()).stream().map(KeyWord::getWord)
                        .collect(Collectors.toSet());
        final Set<KeyWord> newKeyWords =
                Arrays.stream(words.split("\\s+")).map(word -> word.toLowerCase(Locale.ENGLISH))
                        .filter(word -> !existingWords.contains(word))
                        .map(word -> new KeyWord(resumeContainer.getResume().getId(), word))
                        .collect(Collectors.toSet());
        this.keyWordDao.add(newKeyWords);

        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/key-words";
    }

    /**
     * Delete the key word with the specified unique id.
     *
     * @param word the unique id of the key word to delete
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/key-words/delete/{word}")
    @Nonnull
    public String keyWordDelete(
            @Nonnull @PathVariable("word") final String word, @Nonnull final Map<String, Object> model) {
        final ResumeContainer resumeContainer = createResumeContainer();
        this.keyWordDao.delete(new KeyWord(resumeContainer.getResume().getId(), word));
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/key-words";
    }
}
