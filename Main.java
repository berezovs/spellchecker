import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        WordBank wordBank = new WordBank();
        wordBank.loadWordsFromFile("dictionary.txt");
        SpellChecker spellChecker = new SpellChecker(wordBank);
        AppController.createInstance(wordBank, spellChecker);
        
    
         GUI.launchApp();
       
    }
}