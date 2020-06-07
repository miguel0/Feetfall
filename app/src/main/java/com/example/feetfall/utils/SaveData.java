package com.example.feetfall.utils;

import java.util.ArrayList;
import java.util.Stack;

public class SaveData {
    // Stats
    public static int lvl = 1;
    public static int maxExp = 5;
    public static int exp = 0;
    public static int maxHp = 50;
    public static int hp = 50;
    public static int str = 10;
    public static int def = 8;
    public static int statp = 0;
    public static String index = "1";
    public static ArrayList<String> checkpoints = new ArrayList<>();
    public static ArrayList<String> usedCheckpoints = new ArrayList<>();
    public static ArrayList<String> chapters = new ArrayList<>();
    public static ArrayList<Item> items = new ArrayList<>();
    public static Weapon helmet = null;
    public static Weapon weapon = null;
    public static Weapon armor = null;

    public static ItemAdapter adapter;

    public static void addExp(int n) {
        exp += n;
        while(exp >= maxExp) {
            exp -= maxExp;
            lvl++;
            maxExp *= 1.2;
            maxHp *= 1.2;
            hp = maxHp;
            statp += lvl;
        }
    }

    public static void damage(int n) {
        hp -= n;
        if(hp < 0) {
            hp = 0;
        } else if(hp > maxHp) {
            hp = maxHp;
        }
    }

    public static void recover(int n) {
        damage(-n);
    }

    public static int getStr() {
        int res = str;

        if(helmet != null) {
            res += helmet.getStr();
        }
        if(weapon != null) {
            res += weapon.getStr();
        }
        if(armor != null) {
            res += armor.getStr();
        }

        return res;
    }

    public static int getDef() {
        int res = def;

        if(helmet != null) {
            res += helmet.getDef();
        }
        if(weapon != null) {
            res += weapon.getDef();
        }
        if(armor != null) {
            res += armor.getDef();
        }

        return res;
    }
}
