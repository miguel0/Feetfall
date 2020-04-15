package com.example.feetfall.utils;

public class Decision {

    private String initialText;
    private DecisionButton dec1, dec2;

    public Decision(String initialText, DecisionButton dec1, DecisionButton dec2) {
        this.initialText = initialText;
        this.dec1 = dec1;
        this.dec2 = dec2;
    }

    public String getInitialText() {
        return initialText;
    }

    public void setInitialText(String initialText) {
        this.initialText = initialText;
    }

    public DecisionButton getDec1() {
        return dec1;
    }

    public void setDec1(DecisionButton dec1) {
        this.dec1 = dec1;
    }

    public DecisionButton getDec2() {
        return dec2;
    }

    public void setDec2(DecisionButton dec2) {
        this.dec2 = dec2;
    }

}
