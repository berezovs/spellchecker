class Main {
    public static void main(String[] args) {
        Dictionary wordBank = new WordBank();
        wordBank.loadWordsFromFile("dictionary.txt");
        SpellChecker spellChecker = new SpellCheckComponent(wordBank);
        SpellCheckManager managerComponent = new SpellCheckManagerComponent(spellChecker);

        GUI.launchApp(managerComponent);

    }
}