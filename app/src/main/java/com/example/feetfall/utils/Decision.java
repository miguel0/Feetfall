package com.example.feetfall.utils;

public class Decision {

    private String title, fileName, initialText, item, equipment;
    private Boolean checkpoint;
    private DecisionButton dec1, dec2;
    public boolean decided;
    public int decision;

    public Decision(String title, String fileName, String initialText, String item, String equipment, Boolean checkpoint, DecisionButton dec1, DecisionButton dec2) {
        this.title = title;
        this.fileName = fileName;
        this.initialText = initialText;
        this.item = item;
        this.equipment = equipment;
        this.checkpoint = checkpoint;
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

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Boolean getCheckpoint() { return checkpoint; }

    public void setCheckpoint(Boolean checkpoint) { this.checkpoint = checkpoint; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }
}
