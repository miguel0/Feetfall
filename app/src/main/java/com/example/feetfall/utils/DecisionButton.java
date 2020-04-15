package com.example.feetfall.utils;

public class DecisionButton {
    public String text, result;
    public int exp, hp, str, def;

    public DecisionButton(String text, String result, int exp, int hp, int str, int def) {
        this.text = text;
        this.result = result;
        this.exp = exp;
        this.hp = hp;
        this.str = str;
        this.def = def;
    }
}
