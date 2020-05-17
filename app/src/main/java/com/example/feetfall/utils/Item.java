package com.example.feetfall.utils;

import android.app.AlertDialog;
import android.content.Context;

import com.example.feetfall.R;

public class Item {
    private String name;
    private int imageId;

    public Item(String name) {
        this.name = name;

        switch(name) {
            case "potion":
                this.imageId = R.drawable.potion;
                break;
            case "key":
                this.imageId = R.drawable.key;
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
        }
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
