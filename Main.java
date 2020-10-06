

class Main {
    public static void main(String[] args){
     GUI.launchApp();
     WordBank bank = new WordBank();
     bank.loadWordsFromFile("dictionary.txt");
    }
}