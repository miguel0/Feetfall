package com.example.feetfall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @OnClick(R.id.btStart)
    public void btStart(View v) {
        Intent i = new Intent(MainActivity.this, GameActivity.class);
        i.putExtra("new", true);
        startActivity(i);
    }

    @OnClick(R.id.btContinue)
    public void btContinue(View v) {
        Intent i = new Intent(MainActivity.this, GameActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.btChapters)
    public void btChapters(View v) {
        Intent i = new Intent(MainActivity.this, ChaptersActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.btExtras)
    public void btExtras(View v) {
        Intent i = new Intent(MainActivity.this, ApiActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
