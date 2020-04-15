package com.example.feetfall.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feetfall.GameActivity;
import com.example.feetfall.R;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DecisionAdapter extends RecyclerView.Adapter<DecisionAdapter.ViewHolder> {

    private List<Decision> mDecisions;

    public DecisionAdapter(List<Decision> decisions) {
        mDecisions = decisions;
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
                if((SaveData.str < decision.getDec1().str) || (SaveData.def < decision.getDec1().def)) {
                    if(decision.getDec1().failure != null) {
                        GameActivity.decisions.add(decision.getDec1().failure);
                        SaveData.index = Story.list.indexOf(decision.getDec1().failure);
                        GameActivity.adapter.notifyDataSetChanged();
                    }
                } else {
                    if(decision.getDec1().success != null) {
                        GameActivity.decisions.add(decision.getDec1().success);
                        SaveData.index = Story.list.indexOf(decision.getDec1().success);
                        GameActivity.adapter.notifyDataSetChanged();
                    }
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
                if((SaveData.str < decision.getDec2().str) || (SaveData.def < decision.getDec2().def)) {
                    if(decision.getDec2().failure != null) {
                        GameActivity.decisions.add(decision.getDec2().failure);
                        SaveData.index = Story.list.indexOf(decision.getDec2().failure);
                        GameActivity.adapter.notifyDataSetChanged();
                    }
                } else {
                    if(decision.getDec2().success != null) {
                        GameActivity.decisions.add(decision.getDec2().success);
                        SaveData.index = Story.list.indexOf(decision.getDec2().success);
                        GameActivity.adapter.notifyDataSetChanged();
                    }
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
}
