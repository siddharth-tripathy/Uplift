package com.example.learnenglish;

public class ModelSentences {
    String English, Hindi;

    public ModelSentences(String english, String hindi) {
        English = english;
        Hindi = hindi;
    }

    public ModelSentences() {
    }

    public String getEnglish() {
        return English;
    }

    public void setEnglish(String english) {
        English = english;
    }

    public String getHindi() {
        return Hindi;
    }

    public void setHindi(String hindi) {
        Hindi = hindi;
    }
}