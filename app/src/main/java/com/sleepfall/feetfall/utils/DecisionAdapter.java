package com.sleepfall.feetfall.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sleepfall.feetfall.GameActivity;
import com.sleepfall.feetfall.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DecisionAdapter extends RecyclerView.Adapter<DecisionAdapter.ViewHolder> {

    private Context context;
    private List<Decision> mDecisions;
    private ArrayList<Button> buttons;

    private static boolean checkpointSelected = false;

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
        if(checkpointSelected && !decision.getTitle().equals("Goodbye") && !decision.getTitle().equals("End of journey")) {setUserData(this.context, decision.getFileName());}
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
                    if (fulfills && (SaveData.getStr() >= temp.str) && (SaveData.getDef() >= temp.def) && (SaveData.hp > 0)) {
                        SaveData.items.remove(usedItem);
                        checkpointSelected = false;
                        if(decision.getTitle().equals("End of journey")) {
                            if(temp.success.equals("helloagain") && SaveData.checkpoints.size() > 0) {
                                checkpointSelected = true;
                                nextDecision = mapDecision(context, SaveData.checkpoints.get(SaveData.checkpoints.size() - 1));
                                SaveData.checkpoints.remove(SaveData.checkpoints.size() - 1);
                            } else {
                                nextDecision = mapDecision(context, "goodbye");
                            }
                        } else{
                            nextDecision = mapDecision(context, temp.success);
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
                        checkpointSelected = false;
                        if(decision.getTitle().equals("End of journey")) {
                            if(temp.success.equals("helloagain") && SaveData.checkpoints.size() > 0) {
                                checkpointSelected = true;
                                nextDecision = mapDecision(context, SaveData.checkpoints.get(SaveData.checkpoints.size() - 1));
                                SaveData.checkpoints.remove(SaveData.checkpoints.size() - 1);
                            } else {
                                nextDecision = mapDecision(context, "goodbye");
                            }
                        } else{
                            nextDecision = mapDecision(context, temp.failure);
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
                    if (!SaveData.chapters.contains(nextDecision.getFileName()) && !nextDecision.getTitle().equals("Goodbye") && !nextDecision.getTitle().equals("End of journey")) {
                        SaveData.chapters.add(nextDecision.getFileName());
                        backupJSON(this.context, nextDecision.getFileName());
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

    public void backupJSON(Context context, String file) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(file + "ch.json", Context.MODE_PRIVATE));
            JSONObject content = new JSONObject();
            content.put("lvl", SaveData.lvl);
            content.put("maxExp", SaveData.maxExp);
            content.put("exp", SaveData.exp);
            content.put("maxHp", SaveData.maxHp);
            content.put("hp", SaveData.hp);
            content.put("str", SaveData.str);
            content.put("def", SaveData.def);
            content.put("statp", SaveData.statp);
            content.put("index", SaveData.index);
            /*
            StringBuilder che = new StringBuilder();
            for(String i : SaveData.checkpoints){
                che.append(i + ",");
            }
            content.put("checkpoints", che.toString());

            StringBuilder usche = new StringBuilder();
            for(String i : SaveData.usedCheckpoints){
                usche.append(i + ",");
            }
            content.put("usedCheckpoints", usche.toString());

            StringBuilder ch = new StringBuilder();
            for(String i : SaveData.chapters){
                ch.append(i + ",");
            }

            content.put("chapters", ch.toString());
            */
            StringBuilder sb = new StringBuilder();
            for(Item i : SaveData.items) {
                if(i instanceof Weapon) {
                    sb.append("w" + i.getName());
                } else {
                    sb.append("i" + i.getName());
                }

                sb.append(",");
            }
            content.put("items", sb.toString());

            if(SaveData.helmet != null) {
                content.put("helmet", SaveData.helmet.getName());
            } else {
                content.put("helmet", "");
            }

            if(SaveData.weapon != null) {
                content.put("weapon", SaveData.weapon.getName());
            } else {
                content.put("weapon", "");
            }

            if(SaveData.armor != null) {
                content.put("armor", SaveData.armor.getName());
            } else {
                content.put("armor", "");
            }

            osw.write(content.toString());
            osw.close();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void setUserData(Context context, String file) {
        SaveData.items.clear();
        String rawdata = "";
        try {
            InputStream inputStream = context.openFileInput(file + "ch.json");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiver = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiver = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiver);
                }

                inputStream.close();
                rawdata = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        try{
            //JSONObject data = new JSONObject(loadJSONFromAsset(context, file));
            JSONObject data = new JSONObject(rawdata);

            SaveData.lvl = data.getInt("lvl");
            SaveData.maxExp = data.getInt("maxExp");
            SaveData.exp = data.getInt("exp");
            SaveData.maxHp = data.getInt("maxHp");
            SaveData.hp = data.getInt("hp");
            SaveData.str = data.getInt("str");
            SaveData.def = data.getInt("def");
            SaveData.statp = data.getInt("statp");
            SaveData.index = data.getString("index");
            /*
            for(String i : data.getString("checkpoints").split(",")) {
                if(i.length() > 0) {
                    SaveData.checkpoints.add(i);
                }
            }

            for(String i : data.getString("usedCheckpoints").split(",")) {
                if(i.length() > 0) {
                    SaveData.usedCheckpoints.add(i);
                }
            }

            for(String i : data.getString("chapters").split(",")) {
                if(i.length() > 0) {
                    SaveData.chapters.add(i);
                }
            }
            */
            for(String i : data.getString("items").split(",")) {
                if(i.length() > 0) {
                    if (i.charAt(0) == 'w') {
                        SaveData.items.add(new Weapon(i.substring(1)));
                    } else {
                        SaveData.items.add(new Item(i.substring(1)));
                    }
                }
            }

            String temp;
            if(!(temp = data.getString("helmet")).equals("")) {
                SaveData.helmet = new Weapon(temp);
            } else {
                SaveData.helmet = null;
            }
            if(!(temp = data.getString("weapon")).equals("")) {
                SaveData.weapon = new Weapon(temp);
            } else {
                SaveData.weapon = null;
            }
            if(!(temp = data.getString("armor")).equals("")) {
                SaveData.armor = new Weapon(temp);
            } else {
                SaveData.armor = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
