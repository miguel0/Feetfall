package com.example.feetfall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        TextView tvItemName = findViewById(R.id.tvItemName);
        ImageView ivItemImage = findViewById(R.id.ivItemImage);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvWepStats = findViewById(R.id.tvWepStats);

        tvItemName.setText(getIntent().getStringExtra("name"));
        Glide.with(this).load(getIntent().getIntExtra("image", R.drawable.sword)).into(ivItemImage);
        tvDescription.setText(getIntent().getStringExtra("description"));
        tvWepStats.setText(getIntent().getIntExtra("atk", -1) < 0 ? "\n" :
                String.format("Atk: %d\nDef: %d", getIntent().getIntExtra("atk", 0), getIntent().getIntExtra("def", 0)));
    }
}