package com.example.feetfall.utils;

import java.util.ArrayList;

public class Decision {

    private String initialText, item, equipment;
    private ArrayList<DecisionButton> decisions;
    public boolean decided;
    public int decision;

    public Decision(String initialText, String item, String equipment, ArrayList<DecisionButton> decisions) {
        this.initialText = initialText;
        this.item = item;
        this.equipment = equipment;
        this.decisions = decisions;
        decided = false;
        decision = -1;
    }

    public String getInitialText() {
        return initialText;
    }

    public void setInitialText(String initialText) {
        this.initialText = initialText;
    }

    public String getItem() { return item; }

    public void setItem(String item) { this.item = item; }

    public ArrayList<DecisionButton> getDecisions() {
        return decisions;
    }

    public void setDecisions(ArrayList<DecisionButton> decisions) {
        this.decisions = decisions;
    }

    public String getEquipment() { return equipment; }

    public void setEquipment(String equipment) { this.equipment = equipment; }
}
