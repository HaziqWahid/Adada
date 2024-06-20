package asia.fourtitude.interviewq.jumble.controller;

import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import asia.fourtitude.interviewq.jumble.core.JumbleEngine;
import asia.fourtitude.interviewq.jumble.model.ExistsForm;
import asia.fourtitude.interviewq.jumble.model.PrefixForm;
import asia.fourtitude.interviewq.jumble.model.ScrambleForm;
import asia.fourtitude.interviewq.jumble.model.SearchForm;
import asia.fourtitude.interviewq.jumble.model.SubWordsForm;
import java.util.*;

@Controller
@RequestMapping(path = "/")
public class RootController {

    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    private final JumbleEngine jumbleEngine;

    @Autowired(required = true)
    public RootController(JumbleEngine jumbleEngine) {
        this.jumbleEngine = jumbleEngine;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("timeNow", ZonedDateTime.now());
        return "index";
    }

    @GetMapping("scramble")
    public String doGetScramble(Model model) {
        model.addAttribute("form", new ScrambleForm());
        return "scramble";
    }

    @PostMapping("scramble")
    public String doPostScramble(
            @ModelAttribute(name = "form") ScrambleForm form,
            BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#scramble()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */

        if (bindingResult.hasErrors() || form.getWord() == null || form.getWord().isEmpty()) {
            bindingResult.rejectValue("word", "error.form", "Word must not be empty");
            return "scramble";
        }

        String scrambledWord = jumbleEngine.scramble(form.getWord());
        model.addAttribute("scrambledWord", scrambledWord);

        return "scramble";
    }

    @GetMapping("palindrome")
    public String doGetPalindrome(Model model) {
        model.addAttribute("words", this.jumbleEngine.retrievePalindromeWords());
        return "palindrome";
    }

    @GetMapping("exists")
    public String doGetExists(Model model) {
        model.addAttribute("form", new ExistsForm());
        return "exists";
    }

    @PostMapping("exists")
    public String doPostExists(
            @ModelAttribute(name = "form") ExistsForm form,
            BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#exists()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */
        if (bindingResult.hasErrors() || form.getWord() == null || form.getWord().isEmpty()) {
            bindingResult.rejectValue("word", "error.form", "Word must not be empty");
            return "exists";
        }

        boolean exists = jumbleEngine.exists(form.getWord());
        model.addAttribute("exists", exists);

        return "exists";
    }

    @GetMapping("prefix")
    public String doGetPrefix(Model model) {
        model.addAttribute("form", new PrefixForm());
        return "prefix";
    }

    @PostMapping("prefix")
    public String doPostPrefix(
            @ModelAttribute(name = "form") PrefixForm form,
            BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#wordsMatchingPrefix()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */

        if (bindingResult.hasErrors()) {
            return "prefix";
        }

        String prefix = form.getPrefix();
        List<String> words = new ArrayList<>(jumbleEngine.wordsMatchingPrefix(prefix));
        model.addAttribute("words", words);
        return "prefix";
    }

    @GetMapping("search")
    public String doGetSearch(Model model) {
        model.addAttribute("form", new SearchForm());
        return "search";
    }

    @PostMapping("search")
    public String doPostSearch(
            @ModelAttribute(name = "form") SearchForm form,
            BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) Show the fields error accordingly: "Invalid startChar", "Invalid endChar", "Invalid length".
         * c) To call JumbleEngine#searchWords()
         * d) Presentation page to show the result
         * e) Must pass the corresponding unit tests
         */
        if (bindingResult.hasErrors()) {
            return "search";
        }

        Character startChar = form.getStartChar() != null && !form.getStartChar().isEmpty() ? form.getStartChar().charAt(0) : null;
        Character endChar = form.getEndChar() != null && !form.getEndChar().isEmpty() ? form.getEndChar().charAt(0) : null;
        Collection<String> wordCollection = jumbleEngine.searchWords(startChar, endChar, form.getLength());
        List<String> words = new ArrayList<>(wordCollection);

        model.addAttribute("words", words);

        return "search";
    }

    @GetMapping("subWords")
    public String goGetSubWords(Model model) {
        model.addAttribute("form", new SubWordsForm());
        return "subWords";
    }

    @PostMapping("subWords")
    public String doPostSubWords(
            @ModelAttribute(name = "form") SubWordsForm form,
            BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#generateSubWords()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */
        if (bindingResult.hasErrors()) {
            return "subWords"; // Return to the form page if validation fails
        }
        List<String> subWords = new ArrayList<>(jumbleEngine.generateSubWords(form.getWord(), form.getMinLength()));
        form.setWords(subWords);
        model.addAttribute("form", form);
        return "subWords";
    }

}
