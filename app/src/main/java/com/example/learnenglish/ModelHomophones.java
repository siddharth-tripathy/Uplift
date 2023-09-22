package com.example.learnenglish;

public class ModelHomophones {
    String EngWord1, EngWord2, Meaning1, Meaning2;

    public ModelHomophones() {
    }

    public ModelHomophones(String engWord1, String engWord2, String meaning1, String meaning2) {
        EngWord1 = engWord1;
        EngWord2 = engWord2;
        Meaning1 = meaning1;
        Meaning2 = meaning2;
    }

    public String getEngWord1() {
        return EngWord1;
    }

    public void setEngWord1(String engWord1) {
        EngWord1 = engWord1;
    }

    public String getEngWord2() {
        return EngWord2;
    }

    public void setEngWord2(String engWord2) {
        EngWord2 = engWord2;
    }

    public String getMeaning1() {
        return Meaning1;
    }

    public void setMeaning1(String meaning1) {
        Meaning1 = meaning1;
    }

    public String getMeaning2() {
        return Meaning2;
    }

    public void setMeaning2(String meaning2) {
        Meaning2 = meaning2;
    }
}
