
import java.util.HashSet;
import java.util.Set;

class SpellCheckComponent implements SpellChecker {
    private Dictionary wordBank = null;


    SpellCheckComponent(Dictionary bank) {
        this.wordBank = bank;

    }

    public String getListOfSuggestions(String word) {
        String newWord = "";
        word = word.trim().toLowerCase();

        if(word.equals(""))
            return "";

        //will push words found in the dictionary into a hashset to avoid duplicate values
        Set<String> suggestedWords = new HashSet<>();

        // add a letter to the word
        for (char letter = 'a'; letter <= 'z'; letter++) {
            for (int i = 0; i <= word.length(); i++) {

                String leftSubStr = word.substring(0, i);
                String rightSubStr = word.substring(i);

                leftSubStr = leftSubStr.concat(String.valueOf(letter));
                newWord = leftSubStr.concat(rightSubStr);

                if (this.wordExists(newWord))
                    suggestedWords.add(newWord);
            }
        }

        // delete a letter
        for (int i = 0; i < word.length(); i++) {
            newWord = word.substring(0, i).concat(word.substring(i + 1));
            if (this.wordExists(newWord))
                suggestedWords.add(newWord);
        }

        // reverse a word
        StringBuffer strBuffer = new StringBuffer(word);
        strBuffer.reverse();
        newWord = strBuffer.toString();
        if (this.wordExists(newWord))
            suggestedWords.add(newWord);


        return this.toString(suggestedWords);
    }

    public String toString(Set<String> suggestions) {
        return String.join("\n", suggestions);
    }

    public boolean wordExists(String word) {
        return wordBank.findWord(word);
    }
}
