package com.example.learnenglish;

public class ModelWordBank {
    String EnglishName, HindiName;

    public ModelWordBank() {
    }

    public ModelWordBank(String englishName, String hindiName) {
        EnglishName = englishName;
        HindiName = hindiName;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public String getHindiName() {
        return HindiName;
    }

    public void setHindiName(String hindiName) {
        HindiName = hindiName;
    }
}