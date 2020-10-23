import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class AppController {
    private static AppController controller = null;
    private SpellChecker spellChecker = null;
    private WordBank wordBank = null;
    private List<String> misspelledWords = null;


    private AppController(WordBank bank, SpellChecker spellChecker) {
        this.wordBank = bank;
        this.spellChecker = spellChecker;
        misspelledWords = new ArrayList<>();

    }

    public List<String> createArrayOfWordsFromText(String text) {
        List<String> allWords = null;
        return allWords = Arrays.asList(text.replaceAll("[\\,\\.]", "").split(" "));
    }

    public void startSpellCheck(String text) {
        this.createListOfMisspelledWords(this.createArrayOfWordsFromText(text));
    }

    public String getNextSuggestion() {
        if(this.misspelledWords.isEmpty())
            return "";

        String word = this.misspelledWords.remove(0);
        String returnString = "Misspelled: " + word;
        String suggestions = spellChecker.getListOfSuggestions(word);
        if (suggestions.equals("")) {
            returnString += "\nNo suggestions have been found";
        } else {
            returnString += "\n";
            returnString += suggestions;
        }
        return returnString;
    }

    private void createListOfMisspelledWords(List<String> allWords) {

        for (String word : allWords) {
            if (!this.spellChecker.wordExists(word.trim().toLowerCase()))
                this.misspelledWords.add(word.trim().toLowerCase());
        }
    }

    public static void createInstance(WordBank wBank, SpellChecker sChecker) {
        if (controller == null) {
            controller = new AppController(wBank, sChecker);
        }

    }

    public static AppController getInstance() {
        return controller;
    }
}
