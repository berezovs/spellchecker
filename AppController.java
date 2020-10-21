import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class AppController {
    private static AppController controller = null;
    private List<String> wordsFromTextArea = null;
    private SpellChecker spellChecker = null;
    private int counter;

    private AppController(WordBank bank, SpellChecker spellChecker) {
        this.wordBank = bank;
        this.spellChecker = spellChecker;
        this.wordsFromTextArea = new ArrayList<>();
        this.suggestions = new Hashtable<>();
        this.counter = 0;
    }

    public void buildArrayOfWords(String text) {
        wordsFromTextArea = Arrays.asList(text.replaceAll("[^a-zA-Z ]", "").split(" "));
    }

    public List<String> runSpellCheck(String text) {
        List<String> list = null;
        
        this.buildArrayOfWords(text);
        for (String word : wordsFromTextArea) {
           
            if (!this.spellChecker.wordExists(word.trim().toLowerCase())) {
                list = new ArrayList<>();
               

                list = spellChecker.getListOfSuggestions(word); 
                list.add(0, word);
            }

        }
        return list;
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
