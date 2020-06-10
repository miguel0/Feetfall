package com.sleepfall.feetfall.utils;

import android.app.AlertDialog;
import android.content.Context;

import com.sleepfall.feetfall.R;

public class Item {
    private String name;
    private String actualName;
    private int imageId;
    private String description;

    public Item() {
        this("potion");
    }

    public Item(String name) {
        this.name = name;

        switch(name) {
            case "potion":
                this.actualName = "Health Potion";
                this.imageId = R.drawable.potion;
                this.description = "A suspicious looking potion. These generally recover health in games so might as well try it.";
                break;
            case "key":
                this.actualName = "Door Key";
                this.imageId = R.drawable.key;
                this.description = "A key to use on doors. Can open any door. Why does this key open all doors? Who knows?";
                break;
        }
    }

    public void use(Context context) {
        switch(this.name) {
            case "potion":
                SaveData.recover(40);
                SaveData.items.remove(this);
                SaveData.adapter.notifyDataSetChanged();
                return;
            case "key":
                AlertDialog ad = new AlertDialog.Builder(context).create();
                ad.setCancelable(false);
                ad.setTitle("Warning!");
                ad.setMessage("You can't use that item here.");
                ad.setButton("Ok", (dialog, which) -> dialog.dismiss());
                ad.show();
                break;
            default:
                if(this instanceof Weapon) {
                    ((Weapon) this).equip();
                }
        }
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String actualName) {
        this.actualName = actualName;
    }
}
