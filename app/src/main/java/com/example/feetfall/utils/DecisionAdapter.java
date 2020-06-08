package com.example.feetfall.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feetfall.GameActivity;
import com.example.feetfall.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DecisionAdapter extends RecyclerView.Adapter<DecisionAdapter.ViewHolder> {

    private Context context;
    private List<Decision> mDecisions;
    private ArrayList<Button> buttons;

    public DecisionAdapter(Context context, List<Decision> decisions) {
        this.context = context;
        this.mDecisions = decisions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View decisionView = inflater.inflate(R.layout.item_decision, parent, false);

        ViewHolder viewHolder = new ViewHolder(decisionView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DecisionAdapter.ViewHolder holder, int position) {
        Decision decision = mDecisions.get(position);

        holder.tvDecFirst.setText(decision.getInitialText());

        buttons = new ArrayList<>();
        if(holder.llDecision.getChildCount() > 0)
            holder.llDecision.removeAllViews();
        holder.tvDecSec.setText("");

        for(DecisionButton i : decision.getDecisions()) {
            DecisionButton temp = i;

            Button button = new Button(context);

            button.setEnabled(false);
            button.setText(temp.text);
            button.setTextSize(20);
            button.setBackgroundResource(R.drawable.decision_shape);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            holder.llDecision.addView(button, params);
            buttons.add(button);

            if (!decision.decided) {
                button.setEnabled(true);

                button.setOnClickListener(v -> {
                    decision.decided = true;
                    decision.decision = decision.getDecisions().indexOf(temp);

                    holder.tvDecSec.setText(temp.result);
                    for(Button j : buttons) {
                        j.setEnabled(false);
                    }

                    SaveData.addExp(temp.exp);
                    SaveData.damage(temp.hp);
                    boolean fulfills = false;
                    Item usedItem = searchItem(temp.requires);
                    if (temp.requires.length() == 0 || usedItem != null) {
                        fulfills = true;
                    }
                    Decision nextDecision;
                    if (fulfills && (SaveData.getStr() >= temp.str) && (SaveData.getDef() >= temp.def)) {
                        SaveData.items.remove(usedItem);
                        if(decision.getTitle().equals("End of journey")) {
                            nextDecision = mapDecision(context, SaveData.checkpoints.get(SaveData.checkpoints.size() - 1));
                            SaveData.checkpoints.remove(SaveData.checkpoints.size() - 1);
                        } else{
                            if(temp.success.equals("end") && SaveData.checkpoints.size() == 0) {
                                nextDecision = mapDecision(context, "goodbye");
                            } else {
                                nextDecision = mapDecision(context, temp.success);
                            }
                        }
                        GameActivity.decisions.add(nextDecision);
                        SaveData.index = temp.success;
                        if (nextDecision.getItem().length() > 0) {
                            SaveData.items.add(new Item(nextDecision.getItem()));
                        }
                        if (nextDecision.getEquipment().length() > 0) {
                            SaveData.items.add(new Weapon(nextDecision.getEquipment()));
                        }
                        GameActivity.adapter.notifyDataSetChanged();
                    } else {
                        if(decision.getTitle().equals("End of journey")) {
                            nextDecision = mapDecision(context, SaveData.checkpoints.get(SaveData.checkpoints.size() - 1));
                            SaveData.checkpoints.remove(SaveData.checkpoints.size() - 1);
                        } else{
                            if(temp.failure.equals("end") && SaveData.checkpoints.size() == 0) {
                                nextDecision = mapDecision(context, "goodbye");
                            } else {
                                nextDecision = mapDecision(context, temp.failure);
                            }
                        }
                        GameActivity.decisions.add(nextDecision);
                        SaveData.index = temp.failure;
                        if (nextDecision.getItem().length() > 0) {
                            SaveData.items.add(new Item(nextDecision.getItem()));
                        }
                        if (nextDecision.getEquipment().length() > 0) {
                            SaveData.items.add(new Weapon(nextDecision.getEquipment()));
                        }
                        GameActivity.adapter.notifyDataSetChanged();
                    }
                    if (!SaveData.chapters.contains(nextDecision.getFileName())) {
                        SaveData.chapters.add(nextDecision.getFileName());
                    }
                    if (nextDecision.getCheckpoint()) {
                        if (!SaveData.usedCheckpoints.contains(nextDecision.getFileName())) {
                            SaveData.usedCheckpoints.add(nextDecision.getFileName());
                            if (!SaveData.checkpoints.contains(nextDecision.getFileName())) {
                                SaveData.checkpoints.add(nextDecision.getFileName());
                            }
                        }
                    }
                });
            } else {
                holder.tvDecSec.setText(decision.getDecisions().get(decision.decision).result);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDecisions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDecFirst) TextView tvDecFirst;
        @BindView(R.id.tvDecSec) TextView tvDecSec;
        @BindView(R.id.llDecision) LinearLayout llDecision;

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

            ArrayList<DecisionButton> decButtons = new ArrayList<>();

            for(int i = 0; i < decisions.length(); i++) {
                JSONObject dbjson = decisions.getJSONObject(i);
                DecisionButton db = new DecisionButton(
                        dbjson.getString("text"),
                        dbjson.getString("result"),
                        dbjson.getInt("exp"),
                        dbjson.getInt("hp"),
                        dbjson.getInt("str"),
                        dbjson.getInt("def"),
                        dbjson.getString("requires"),
                        dbjson.getString("success"),
                        dbjson.getString("failure")
                );

                decButtons.add(db);
            }

            Decision decision = new Decision(title, file, initialText, item, equipment, checkpoint, decButtons);
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
