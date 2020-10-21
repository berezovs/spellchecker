import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        WordBank wordBank = new WordBank();
        wordBank.loadWordsFromFile("dictionary.txt");
        SpellChecker spellChecker = new SpellChecker(wordBank);
      // List <String> list = new ArrayList<>();
        // list = spellChecker.getListOfSuggestions("hous");
        // list = spellChecker.getListOfSuggestions("grea");
        AppController.createInstance(wordBank, spellChecker);
        // for(String w:list){
        //     System.out.println(w);
        // }
        
    
         GUI.launchApp();
       
    }
}