package com.example.feetfall;

public class SaveData {
    // Stats
    public static int lvl = 1;
    public static int maxExp = 5;
    public static int exp = 0;
    public static int maxHp = 50;
    public static int hp = 50;
    public static int str = 10;
    public static int def = 8;

    public static void addExp(int n) {
        exp += n;
        while(exp >= maxExp) {
            exp -= maxExp;
            lvl++;
            maxExp *= 1.2;
            maxHp *= 1.2;
            hp = maxHp;
            str *= 1.2;
            def *= 1.2;
        }
    }
}
