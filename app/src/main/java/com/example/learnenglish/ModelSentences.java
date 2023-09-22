package com.example.learnenglish;

public class ModelSentences {
    String EnglishSentence, HindiSentence;

    public ModelSentences() {
    }

    public ModelSentences(String englishSentence, String hindiSentence) {
        EnglishSentence = englishSentence;
        HindiSentence = hindiSentence;
    }

    public String getEnglishSentence() {
        return EnglishSentence;
    }

    public void setEnglishSentence(String englishSentence) {
        EnglishSentence = englishSentence;
    }

    public String getHindiSentence() {
        return HindiSentence;
    }

    public void setHindiSentence(String hindiSentence) {
        HindiSentence = hindiSentence;
    }
}
