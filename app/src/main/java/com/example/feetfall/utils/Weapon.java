package com.example.feetfall.utils;

import com.example.feetfall.R;

public class Weapon extends Item {

    private int str;
    private int def;
    private int type; // 0 = helmet, 1 = weapon, 2 = armor

    public Weapon(String name) {
        setName(name);

        switch (name) {
            case "paper hat":
                str = 0;
                def = 3;
                type = 0;
                setImageId(R.drawable.paperhat);
                setActualName("Paper Hat");
                setDescription("This doesn't look like it offers any protection. At least it looks interesting.");
                break;
            case "crusader helmet":
                str = 2;
                def = 7;
                type = 0;
                setImageId(R.drawable.crusaderhelmet);
                setActualName("Crusader Helmet");
                setDescription("Helmet worn by crusaders. Perfect for praising the sun \\[T]/");
                break;
            case "sword":
                str = 5;
                def = 0;
                type = 1;
                setImageId(R.drawable.sword);
                setActualName("Sword");
                setDescription("Just a regular old sword.");
                break;
            case "axe":
                str = 8;
                def = 1;
                type = 1;
                setImageId(R.drawable.axe);
                setActualName("Axe");
                setDescription("Stronger than a sword. Perfect for getting rid of your enemies.");
                break;
            case "dress":
                str = 0;
                def = 5;
                type = 2;
                setImageId(R.drawable.dress);
                setActualName("Dress");
                setDescription("Nothing too special going on with this dress.");
                break;
            case "god dress":
                str = 3;
                def = 10;
                type = 2;
                setImageId(R.drawable.goddress);
                setActualName("GOD DRESS");
                setDescription("With the power of god you will be able to bring justice upon those who dare defy you!!");
                break;
        }
    }

    public void equip() {
        switch(this.type) {
            case 0:
                if(SaveData.helmet != null) {
                    SaveData.items.add(SaveData.helmet);
                }

                SaveData.helmet = this;
                SaveData.items.remove(this);
                SaveData.adapter.notifyDataSetChanged();

                break;
            case 1:
                if(SaveData.weapon != null) {
                    SaveData.items.add(SaveData.weapon);
                }

                SaveData.weapon = this;
                SaveData.items.remove(this);
                SaveData.adapter.notifyDataSetChanged();

                break;
            case 2:
                if(SaveData.armor != null) {
                    SaveData.items.add(SaveData.armor);
                }

                SaveData.armor = this;
                SaveData.items.remove(this);
                SaveData.adapter.notifyDataSetChanged();

                break;
        }
    }

    public int getStr() {
        return str;
    }

    public int getDef() {
        return def;
    }
}
