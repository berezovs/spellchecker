import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpellCheckManagerComponent implements SpellCheckManager{
    private SpellChecker spellChecker = null;
    private List<String> misspelledWords = null;


    public SpellCheckManagerComponent(SpellChecker component) {
        this.spellChecker = component;
        misspelledWords = new ArrayList<>();

    }

    private List<String> createArrayOfWordsFromText(String text) {
        return Arrays.asList(text.replaceAll("[\\,\\.]", "").split(" "));
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


}
