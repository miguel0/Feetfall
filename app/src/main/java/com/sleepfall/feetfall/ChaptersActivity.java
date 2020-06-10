package com.sleepfall.feetfall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.sleepfall.feetfall.utils.ChapterAdapter;
import com.sleepfall.feetfall.utils.Decision;
import com.sleepfall.feetfall.utils.SaveData;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChaptersActivity extends AppCompatActivity {

    public static List<Decision> chapters;
    public static ChapterAdapter adapter;

    @BindView(R.id.rvChapters)
    RecyclerView rvChapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        ButterKnife.bind(this);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SaveData.chapters.clear();
        for(String i : pref.getString("chapters", "").split(",")) {
            if (i.length() > 0) {
                SaveData.chapters.add(i);
            }
        }

        chapters = new ArrayList<>();
        adapter = new ChapterAdapter(this);
        rvChapters.setAdapter(adapter);
        rvChapters.setLayoutManager(new LinearLayoutManager(this));
    }
}