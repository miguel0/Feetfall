package com.sleepfall.feetfall.utils;

public class DecisionButton {
    public String text, result, requires, success, failure;
    public int exp, hp, str, def;

    public DecisionButton(String text, String result, int exp, int hp, int str, int def, String requires, String success, String failure) {
        this.text = text;
        this.result = result;
        this.exp = exp;
        this.hp = hp;
        this.str = str;
        this.def = def;
        this.requires = requires;
        this.success = success;
        this.failure = failure;
    }
}
