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

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity {

    private static SharedPreferences pref;
    public static List<Decision> decisions;
    public static DecisionAdapter adapter;
    public static Story story;

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
            SaveData.index = 0;
            SaveData.items = new ArrayList<>();
        } else {
            SaveData.lvl = pref.getInt("lvl", 1);
            SaveData.maxExp = pref.getInt("maxExp", 5);
            SaveData.exp = pref.getInt("exp", 0);
            SaveData.maxHp = pref.getInt("maxHp", 50);
            SaveData.hp = pref.getInt("hp", 50);
            SaveData.str = pref.getInt("str", 10);
            SaveData.def = pref.getInt("def", 8);
            SaveData.statp = pref.getInt("statp", 0);
            SaveData.index = pref.getInt("index", 0);

            for(String i : pref.getString("items", "").split(",")) {
                SaveData.items.add(new Item(i));
            }

        }

        decisions = new ArrayList<>();
        adapter = new DecisionAdapter(decisions);
        rvDecisions.setAdapter(adapter);
        rvDecisions.setLayoutManager(new LinearLayoutManager(this));

        story = new Story();
        decisions.add(Story.list.get(SaveData.index));
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
        edit.putInt("index", SaveData.index);

        StringBuilder sb = new StringBuilder();
        for(Item i : SaveData.items) {
            sb.append(i.getName());
            sb.append(",");
        }
        edit.putString("items", sb.toString());

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
