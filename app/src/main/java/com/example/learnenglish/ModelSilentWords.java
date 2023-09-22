package com.example.learnenglish;

public class ModelSilentWords {
    String EnglishWord, HindiWord, SilentLetter;

    public ModelSilentWords() {
    }

    public ModelSilentWords(String englishWord, String hindiWord, String silentLetter) {
        EnglishWord = englishWord;
        HindiWord = hindiWord;
        SilentLetter = silentLetter;
    }

    public String getEnglishWord() {
        return EnglishWord;
    }

    public void setEnglishWord(String englishWord) {
        EnglishWord = englishWord;
    }

    public String getHindiWord() {
        return HindiWord;
    }

    public void setHindiWord(String hindiWord) {
        HindiWord = hindiWord;
    }

    public String getSilentLetter() {
        return SilentLetter;
    }

    public void setSilentLetter(String silentLetter) {
        SilentLetter = silentLetter;
    }
}
