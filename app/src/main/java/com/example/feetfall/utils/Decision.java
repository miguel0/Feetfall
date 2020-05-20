package com.example.feetfall.utils;

public class Decision {

    private String initialText, item, equipment;
    private DecisionButton dec1, dec2;
    public boolean decided;
    public int decision;

    public Decision(String initialText, String item, String equipment, DecisionButton dec1, DecisionButton dec2) {
        this.initialText = initialText;
        this.item = item;
        this.equipment = equipment;
        this.dec1 = dec1;
        this.dec2 = dec2;
        decided = false;
        decision = 0;
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

    public String getItem() { return item; }

    public void setItem(String item) { this.item = item; }

    public String getEquipment() { return equipment; }

    public void setEquipment(String equipment) { this.equipment = equipment; }
}
