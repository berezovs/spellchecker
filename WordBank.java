import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class WordBank implements Dictionary{

    private Hashtable<String, String> wordBank;

    WordBank() {
        this.wordBank = new Hashtable<>();
    }

    public void loadWordsFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                word = word.trim();
                word = word.toLowerCase();
                this.addWordToBank(word);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private void addWordToBank(String word) {
        this.wordBank.put(word, word);
    }

    public boolean findWord(String word) {
        return wordBank.contains(word);
    }
}
