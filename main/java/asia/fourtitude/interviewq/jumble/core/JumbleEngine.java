package asia.fourtitude.interviewq.jumble.core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JumbleEngine {

    private final Set<String> wordSet;

    public JumbleEngine() {
        this.wordSet = loadWordSet();
    }

    private Set<String> loadWordSet() {
        Set<String> set = new HashSet<>();
        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/words.txt"))) {
            lines.map(String::toLowerCase).forEach(set::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }
    /**
     * From the input `word`, produces/generates a copy which has the same
     * letters, but in different ordering.
     *
     * Example: from "elephant" to "aeehlnpt".
     *
     * Evaluation/Grading:
     * a) pass unit test: JumbleEngineTest#scramble()
     * b) scrambled letters/output must not be the same as input
     *
     * @param word  The input word to scramble the letters.
     * @return  The scrambled output/letters.
     */
    public String scramble(String word) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        if (word == null || word.length() <= 1) {
            return word;
        }
        List<Character> characters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            characters.add(c);
        }

        Collections.shuffle(characters);
        StringBuilder scrambledWord = new StringBuilder();
        for (char c : characters) {
            scrambledWord.append(c);
        }
        return scrambledWord.toString().equals(word) ? scramble(word) : scrambledWord.toString();
        //throw new UnsupportedOperationException("to be implemented");
    }

    /**
     * Retrieves the palindrome words from the internal
     * word list/dictionary ("src/main/resources/words.txt").
     *
     * Word of single letter is not considered as valid palindrome word.
     *
     * Examples: "eye", "deed", "level".
     *
     * Evaluation/Grading:
     * a) able to access/use resource from classpath
     * b) using inbuilt Collections
     * c) using "try-with-resources" functionality/statement
     * d) pass unit test: JumbleEngineTest#palindrome()
     *
     * @return  The list of palindrome words found in system/engine.
     * @see /https://www.google.com/search?q=palindrome+meaning
     */
    public Collection<String> retrievePalindromeWords() {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */

        Set<String> palindromes = new HashSet<>();
        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/words.txt"))) {
            lines.forEach(word -> {
                String lowerWord = word.toLowerCase();
                if (lowerWord.length() > 1 && lowerWord.equals(new StringBuilder(lowerWord).reverse().toString())) {
                    palindromes.add(word);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return palindromes;
       // throw new UnsupportedOperationException("to be implemented");
    }

    /**
     * Picks one word randomly from internal word list.
     *
     * Evaluation/Grading:
     * a) pass unit test: JumbleEngineTest#randomWord()
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param length  The word picked, must of length.
     * @return  One of the word (randomly) from word list.
     *          Or null if none matching.
     */
    public String pickOneRandomWord(Integer length) {

        List<String> words = null;
        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/words.txt"))) {
            if (length != null) {
                words = lines.filter(word -> word.length() == length).collect(Collectors.toList());
                System.out.println("Filtered words with length " + length + ": " + words.size());
            } else {
                words = lines.collect(Collectors.toList());
                System.out.println("All words: " + words.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (words == null || words.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    /**
     * Checks if the `word` exists in internal word list.
     * Matching is case insensitive.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param word  The input word to check.
     * @return  true if `word` exists in internal word list.
     */
    public boolean exists(String word) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */

        if (word == null) {
            return false;
        }

        Set<String> wordSet = new HashSet<>();
        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/words.txt"))) {
            lines.forEach(w -> wordSet.add(w.toLowerCase()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordSet.contains(word.toLowerCase());
        //throw new UnsupportedOperationException("to be implemented");
    }

    /**
     * Finds all the words from internal word list which begins with the
     * input `prefix`.
     * Matching is case insensitive.
     *
     * Invalid `prefix` (null, empty string, blank string, non letter) will
     * return empty list.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param prefix  The prefix to match.
     * @return  The list of words matching the prefix.
     */
    public Collection<String> wordsMatchingPrefix(String prefix) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        if (prefix == null || prefix.trim().isEmpty()) {
            return Collections.emptyList();
        }

        List<String> matchedWords = null;
        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/words.txt"))) {
            matchedWords = lines.filter(word -> word.toLowerCase().startsWith(prefix.toLowerCase())).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchedWords;
        //throw new UnsupportedOperationException("to be implemented");
    }

    /**
     * Finds all the words from internal word list that is matching
     * the searching criteria.
     *
     * `startChar` and `endChar` must be 'a' to 'z' only. And case insensitive.
     * `length`, if have value, must be positive integer (>= 1).
     *
     * Words are filtered using `startChar` and `endChar` first.
     * Then apply `length` on the result, to produce the final output.
     *
     * Must have at least one valid value out of 3 inputs
     * (`startChar`, `endChar`, `length`) to proceed with searching.
     * Otherwise, return empty list.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param startChar  The first character of the word to search for.
     * @param endChar    The last character of the word to match with.
     * @param length     The length of the word to match.
     * @return  The list of words matching the searching criteria.
     */
    public Collection<String> searchWords(Character startChar, Character endChar, Integer length) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        if (startChar == null && endChar == null && length == null) {
            return Collections.emptyList();
        }
        List<String> filteredWords = null;
        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/words.txt"))) {
            filteredWords = lines.filter(word -> {
                boolean matches = true;
                if (startChar != null) {
                    matches &= word.toLowerCase().startsWith(startChar.toString().toLowerCase());
                }
                if (endChar != null) {
                    matches &= word.toLowerCase().endsWith(endChar.toString().toLowerCase());
                }
                if (length != null) {
                    matches &= word.length() == length;
                }
                return matches;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredWords;
    }

    /**
     * Generates all possible combinations of smaller/sub words using the
     * letters from input word.
     *
     * The `minLength` set the minimum length of sub word that is considered
     * as acceptable word.
     *
     * If length of input `word` is less than `minLength`, then return empty list.
     *
     * Example: From "yellow" and `minLength` = 3, the output sub words:
     *     low, lowly, lye, ole, owe, owl, well, welly, woe, yell, yeow, yew, yowl
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param word       The input word to use as base/seed.
     * @param minLength  The minimum length (inclusive) of sub words.
     *                   Expects positive integer.
     *                   Default is 3.
     * @return  The list of sub words constructed from input `word`.
     */
    public Collection<String> generateSubWords(String word, Integer minLength) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        Set<String> subWords = new HashSet<>();
        if (minLength == null) {
            minLength = 3; // default value
        }else if(minLength <= 0)
        {
            return subWords;
        }
        if (word == null || word.trim().isEmpty() || word.length() < minLength || !word.matches("[a-zA-Z]+")) {
            return Collections.emptyList();
        }

        generateSubWordsHelper(word, "", minLength, subWords);
        subWords.remove(word);
        subWords.removeIf(subWord -> !exists(subWord));

        //System.out.println("Subwords after removing:");
        subWords.forEach(System.out::println);

        return subWords;
    }

    private void generateSubWordsHelper(String word, String prefix, int minLength, Set<String> subWords) {
        if (prefix.length() >= minLength) {
            subWords.add(prefix);
        }
        for (int i = 0; i < word.length(); i++) {
            String newWord = word.substring(0, i) + word.substring(i + 1);
            generateSubWordsHelper(newWord, prefix + word.charAt(i), minLength, subWords);
        }
       // throw new UnsupportedOperationException("to be implemented");
    }

    /**
     * Creates a game state with word to guess, scrambled letters, and
     * possible combinations of words.
     *
     * Word is of length 6 characters.
     * The minimum length of sub words is of length 3 characters.
     *
     * @param length     The length of selected word.
     *                   Expects >= 3.
     * @param minLength  The minimum length (inclusive) of sub words.
     *                   Expects positive integer.
     *                   Default is 3.
     * @return  The game state.
     */
    public GameState createGameState(Integer length, Integer minLength) {
        Objects.requireNonNull(length, "length must not be null");
        if (minLength == null) {
            minLength = 3;
        } else if (minLength <= 0) {
            throw new IllegalArgumentException("Invalid minLength=[" + minLength + "], expect positive integer");
        }
        if (length < 3) {
            throw new IllegalArgumentException("Invalid length=[" + length + "], expect greater than or equals 3");
        }
        if (minLength > length) {
            throw new IllegalArgumentException("Expect minLength=[" + minLength + "] greater than length=[" + length + "]");
        }
        String original = this.pickOneRandomWord(length);
        if (original == null) {
            throw new IllegalArgumentException("Cannot find valid word to create game state");
        }
        String scramble = this.scramble(original);
        Map<String, Boolean> subWords = new TreeMap<>();
        for (String subWord : this.generateSubWords(original, minLength)) {
            subWords.put(subWord, Boolean.FALSE);
        }
        return new GameState(original, scramble, subWords);
    }
}
