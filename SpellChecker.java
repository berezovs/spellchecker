import java.util.ArrayList;
import java.util.List;

class SpellChecker {
    private WordBank wordBank = null;


    SpellChecker(WordBank bank) {
        this.wordBank = bank;
        
    }

    public String getListOfSuggestions(String word) {
        String newWord = "";
        word = word.trim().toLowerCase();
        String suggestedWords = "";

        // add a letter to the word
        for (char letter = 'a'; letter <= 'z'; letter++) {
            for (int i = 0; i < word.length(); i++) {
                
                String leftSubStr = word.substring(0, i);
                String rightSubStr = word.substring(i);

                leftSubStr = leftSubStr.concat(String.valueOf(letter));
                newWord = leftSubStr.concat(rightSubStr);

                if (this.wordExists(newWord))
                    suggestedWords+=newWord+" ";
            }
        }

        // delete a letter
        for (int i = 0; i < word.length(); i++) {
            newWord = word.substring(0, i).concat(word.substring(i + 1));
            if (this.wordExists(newWord))
                suggestedWords+=newWord+" ";
        }

        // reverse a word
        StringBuffer strBuffer = new StringBuffer(word);
        strBuffer.reverse();
        newWord = strBuffer.toString();
        if (this.wordExists(newWord))
            suggestedWords+=newWord+" ";

        suggestedWords=suggestedWords.trim();
        return suggestedWords;
    }

    public boolean wordExists(String word) {
        return wordBank.findWord(word);
    }
}
