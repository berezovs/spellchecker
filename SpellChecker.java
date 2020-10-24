public interface SpellChecker {
    String getListOfSuggestions(String word);
    boolean wordExists(String toLowerCase);
}
