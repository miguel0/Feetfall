package com.example.feetfall.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        holder.btDec1.setText(decision.getDec1().text);
        holder.btDec2.setText(decision.getDec2().text);

        holder.btDec1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvDecSec.setText(decision.getDec1().result);
                holder.btDec1.setEnabled(false);
                holder.btDec2.setEnabled(false);

                SaveData.addExp(decision.getDec1().exp);
                SaveData.damage(decision.getDec1().hp);
                boolean fulfills = false;
                Item usedItem = searchItem(decision.getDec1().requires);
                if(decision.getDec1().requires.equals("") || usedItem != null) {
                    fulfills = true;
                }
                if(fulfills && (SaveData.str >= decision.getDec1().str) && (SaveData.def >= decision.getDec1().def)) {
                    SaveData.items.remove(usedItem);
                    Decision nextDecision = mapDecision(context, decision.getDec1().success);
                    GameActivity.decisions.add(nextDecision);
                    SaveData.index = decision.getDec1().success;
                    if(nextDecision.getItem() != "") {
                        SaveData.items.add(new Item(nextDecision.getItem()));
                    }
                    if(nextDecision.getEquipment() != "") {
                        SaveData.items.add(new Weapon(nextDecision.getItem()));
                    }
                    GameActivity.adapter.notifyDataSetChanged();
                } else {
                    Decision nextDecision = mapDecision(context, decision.getDec1().failure);
                    GameActivity.decisions.add(nextDecision);
                    SaveData.index = decision.getDec1().failure;
                    if(nextDecision.getItem() != "") {
                        SaveData.items.add(new Item(nextDecision.getItem()));
                    }
                    if(nextDecision.getEquipment() != "") {
                        SaveData.items.add(new Weapon(nextDecision.getItem()));
                    }
                    GameActivity.adapter.notifyDataSetChanged();
                }
            }
        });

        holder.btDec2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvDecSec.setText(decision.getDec2().result);
                holder.btDec1.setEnabled(false);
                holder.btDec2.setEnabled(false);

                SaveData.addExp(decision.getDec2().exp);
                SaveData.damage(decision.getDec2().hp);
                boolean fulfills = false;
                Item usedItem = searchItem(decision.getDec2().requires);
                if(decision.getDec2().requires.equals("") || usedItem != null) {
                    fulfills = true;
                }
                if(fulfills && (SaveData.str >= decision.getDec2().str) && (SaveData.def >= decision.getDec2().def)) {
                    SaveData.items.remove(usedItem);
                    Decision nextDecision = mapDecision(context, decision.getDec2().success);
                    GameActivity.decisions.add(nextDecision);
                    SaveData.index = decision.getDec2().success;
                    if(nextDecision.getItem() != "") {
                        SaveData.items.add(new Item(nextDecision.getItem()));
                    }
                    if(nextDecision.getEquipment() != "") {
                        SaveData.items.add(new Weapon(nextDecision.getItem()));
                    }
                    GameActivity.adapter.notifyDataSetChanged();
                } else {
                    Decision nextDecision = mapDecision(context, decision.getDec2().failure);
                    GameActivity.decisions.add(nextDecision);
                    SaveData.index = decision.getDec2().failure;
                    if(nextDecision.getItem() != "") {
                        SaveData.items.add(new Item(nextDecision.getItem()));
                    }
                    if(nextDecision.getEquipment() != "") {
                        SaveData.items.add(new Weapon(nextDecision.getItem()));
                    }
                    GameActivity.adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDecisions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDecFirst) TextView tvDecFirst;
        @BindView(R.id.tvDecSec) TextView tvDecSec;
        @BindView(R.id.btDec1) Button btDec1;
        @BindView(R.id.btDec2) Button btDec2;

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
            String initialText = raw.getString("initialText");
            String item = raw.getString("item");
            String equipment = raw.getString("equipment");
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

            Decision decision = new Decision(initialText, item, equipment, db1, db2);
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
