package com.example.feetfall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends AppCompatActivity {

    private static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbGame);
        setSupportActionBar(toolbar);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        if(getIntent().getBooleanExtra("new", false)) {
            SaveData.lvl = 1;
            SaveData.maxExp = 5;
            SaveData.exp = 0;
            SaveData.maxHp = 50;
            SaveData.hp = 50;
            SaveData.str = 10;
            SaveData.def = 8;
        } else {
            SaveData.lvl = pref.getInt("lvl", 1);
            SaveData.maxExp = pref.getInt("maxExp", 5);
            SaveData.exp = pref.getInt("exp", 0);
            SaveData.maxHp = pref.getInt("maxHp", 50);
            SaveData.hp = pref.getInt("hp", 50);
            SaveData.str = pref.getInt("str", 10);
            SaveData.def = pref.getInt("def", 8);
        }
    }

    public static void saveData() {
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("lvl", SaveData.lvl);
        edit.putInt("maxExp", SaveData.maxExp);
        edit.putInt("exp", SaveData.exp);
        edit.putInt("maxHp", SaveData.maxHp);
        edit.putInt("hp", SaveData.hp);
        edit.putInt("str", SaveData.str);
        edit.putInt("def", SaveData.def);
        edit.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.miStats) {
            Intent i = new Intent(GameActivity.this, StatsActivity.class);
            startActivity(i);
        }

        return true;
    }
}
