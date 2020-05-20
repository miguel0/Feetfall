package com.example.feetfall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import com.example.feetfall.utils.Decision;
import com.example.feetfall.utils.DecisionAdapter;
import com.example.feetfall.utils.Item;
import com.example.feetfall.utils.SaveData;
import com.example.feetfall.utils.Story;
import com.example.feetfall.utils.Weapon;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity {

    private static SharedPreferences pref;
    public static List<Decision> decisions;
    public static DecisionAdapter adapter;

    @BindView(R.id.rvDecisions) RecyclerView rvDecisions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

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
            SaveData.statp = 0;
            SaveData.index = "1";
            SaveData.items = new ArrayList<>();
            SaveData.helmet = null;
            SaveData.weapon = null;
            SaveData.armor = null;
        } else {
            SaveData.lvl = pref.getInt("lvl", 1);
            SaveData.maxExp = pref.getInt("maxExp", 5);
            SaveData.exp = pref.getInt("exp", 0);
            SaveData.maxHp = pref.getInt("maxHp", 50);
            SaveData.hp = pref.getInt("hp", 50);
            SaveData.str = pref.getInt("str", 10);
            SaveData.def = pref.getInt("def", 8);
            SaveData.statp = pref.getInt("statp", 0);
            SaveData.index = pref.getString("index", "1");

            for(String i : pref.getString("items", "").split(",")) {
                if(i.charAt(0) == 'w') {
                    SaveData.items.add(new Weapon(i.substring(1)));
                } else {
                    SaveData.items.add(new Item(i.substring(1)));
                }
            }

            String temp;
            if(!(temp = pref.getString("helmet", "")).equals("")) {
                SaveData.helmet = new Weapon(temp);
            } else {
                SaveData.helmet = null;
            }
            if(!(temp = pref.getString("weapon", "")).equals("")) {
                SaveData.weapon = new Weapon(temp);
            } else {
                SaveData.weapon = null;
            }
            if(!(temp = pref.getString("armor", "")).equals("")) {
                SaveData.armor = new Weapon(temp);
            } else {
                SaveData.armor = null;
            }
        }

        decisions = new ArrayList<>();
        adapter = new DecisionAdapter(this, decisions);
        rvDecisions.setAdapter(adapter);
        rvDecisions.setLayoutManager(new LinearLayoutManager(this));

        decisions.add(adapter.mapDecision(this,SaveData.index));
/*
        SaveData.items.add(new Item("potion"));
        SaveData.items.add(new Item("key"));
        SaveData.items.add(new Item("potion"));
        SaveData.items.add(new Weapon("sword"));
        SaveData.items.add(new Weapon("axe"));
        SaveData.items.add(new Weapon("paper hat"));
        SaveData.items.add(new Weapon("crusader helmet"));
        SaveData.items.add(new Weapon("dress"));
        SaveData.items.add(new Weapon("god dress"));
        */
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
        edit.putInt("statp", SaveData.statp);
        edit.putString("index", SaveData.index);

        StringBuilder sb = new StringBuilder();
        for(Item i : SaveData.items) {
            if(i instanceof Weapon) {
                sb.append("w" + i.getName());
            } else {
                sb.append("i" + i.getName());
            }

            sb.append(",");
        }
        edit.putString("items", sb.toString());

        if(SaveData.helmet != null) {
            edit.putString("helmet", SaveData.helmet.getName());
        } else {
            edit.putString("helmet", "");
        }

        if(SaveData.weapon != null) {
            edit.putString("weapon", SaveData.weapon.getName());
        } else {
            edit.putString("weapon", "");
        }

        if(SaveData.armor != null) {
            edit.putString("armor", SaveData.armor.getName());
        } else {
            edit.putString("armor", "");
        }

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
