package com.example.feetfall.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feetfall.ChaptersActivity;
import com.example.feetfall.GameActivity;
import com.example.feetfall.MainActivity;
import com.example.feetfall.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    private Context context;

    public ChapterAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ChapterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View decisionView = inflater.inflate(R.layout.chapter, parent, false);

        ChapterAdapter.ViewHolder viewHolder = new ChapterAdapter.ViewHolder(decisionView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChapterAdapter.ViewHolder holder, int position) {
        String fileName = SaveData.chapters.get(position);

        Decision decision = this.mapDecision(context, fileName);
        holder.bChapter.setText(decision.getTitle());

        holder.bChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GameActivity.class);
                i.putExtra("chapterSelected", true);
                i.putExtra("chapter", fileName);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SaveData.chapters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bChapter)
        TextView bChapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Item searchItem(String name) {
        for(Item item : SaveData.items) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public Decision mapDecision(Context context, String file) {
        try{
            JSONObject raw = new JSONObject(loadJSONFromAsset(context, file));
            String title = raw.getString("title");
            String initialText = raw.getString("initialText");
            String item = raw.getString("item");
            String equipment = raw.getString("equipment");
            Boolean checkpoint = raw.getBoolean("checkpoint");
            JSONArray decisions = raw.getJSONArray("decisions");

            JSONObject db1json = decisions.getJSONObject(0);
            DecisionButton db1 = new DecisionButton(
                    db1json.getString("text"),
                    db1json.getString("result"),
                    db1json.getInt("exp"),
                    db1json.getInt("hp"),
                    db1json.getInt("str"),
                    db1json.getInt("def"),
                    db1json.getString("requires"),
                    db1json.getString("success"),
                    db1json.getString("failure")
            );

            JSONObject db2json = decisions.getJSONObject(1);
            DecisionButton db2 = new DecisionButton(
                    db2json.getString("text"),
                    db2json.getString("result"),
                    db2json.getInt("exp"),
                    db2json.getInt("hp"),
                    db2json.getInt("str"),
                    db2json.getInt("def"),
                    db2json.getString("requires"),
                    db2json.getString("success"),
                    db2json.getString("failure")
            );

            Decision decision = new Decision(title, file, initialText, item, equipment, checkpoint, db1, db2);
            return decision;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String loadJSONFromAsset(Context context, String file) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(file + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
