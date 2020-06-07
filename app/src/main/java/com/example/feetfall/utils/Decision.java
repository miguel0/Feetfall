package com.example.feetfall.utils;

import java.util.ArrayList;

public class Decision {

    private String title, fileName, initialText, item, equipment;
    private Boolean checkpoint;
    private ArrayList<DecisionButton> decisions;
    public boolean decided;
    public int decision;

    public Decision(String title, String fileName, String initialText, String item, String equipment, Boolean checkpoint, ArrayList<DecisionButton> decisions) {
        this.title = title;
        this.fileName = fileName;
        this.initialText = initialText;
        this.item = item;
        this.equipment = equipment;
        this.checkpoint = checkpoint;
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

    public ArrayList<DecisionButton> getDecisions() { return decisions; }

    public void setDecisions(ArrayList<DecisionButton> decisions) { this.decisions = decisions; }

    public String getEquipment() { return equipment; }

    public void setEquipment(String equipment) { this.equipment = equipment; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Boolean getCheckpoint() { return checkpoint; }

    public void setCheckpoint(Boolean checkpoint) { this.checkpoint = checkpoint; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }
}
