public interface SpellCheckManager {
    void startSpellCheck(String text);
    String getNextSuggestion();
}
