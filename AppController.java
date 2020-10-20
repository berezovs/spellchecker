public class AppController {
    GUI window = null;
    SpellChecker spellChecker = null;
    WordBank wordBank = null;
    private final String WORD_BANK_FILENAME = "dictionary.txt";

    AppController() {
        this.wordBank = new WordBank();
        this.wordBank.loadWordsFromFile(WORD_BANK_FILENAME);
        spellChecker = new SpellChecker(wordBank);
        window = new GUI();
        window.launchApp();
    }
}
