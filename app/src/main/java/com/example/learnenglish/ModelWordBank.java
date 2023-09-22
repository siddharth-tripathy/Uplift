package com.example.learnenglish;

public class ModelWordBank {
    String EnglishName, HindiName, url;

    public ModelWordBank() {
    }

    public ModelWordBank(String englishName, String hindiName, String url) {
        EnglishName = englishName;
        HindiName = hindiName;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}