package com.example.feetfall.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.feetfall.GameActivity;
import com.example.feetfall.R;
import com.example.feetfall.utils.SaveData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StatsFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.tvLevel) TextView tvLevel;
    @BindView(R.id.tvExp) TextView tvExp;
    @BindView(R.id.tvHp) TextView tvHp;
    @BindView(R.id.tvStr) TextView tvStr;
    @BindView(R.id.tvDef) TextView tvDef;
    @BindView(R.id.tvStatp) TextView tvStatp;
    @BindView(R.id.btAddStr) Button btAddStr;
    @BindView(R.id.btAddDef) Button btAddDef;

    @OnClick(R.id.btAddStr)
    public void btAddStr(View v) {
        SaveData.str++;
        SaveData.statp --;
        updateData();
    }

    @OnClick(R.id.btAddDef)
    public void btAddDef(View v) {
        SaveData.def++;
        SaveData.statp--;
        updateData();
    }

    @OnClick(R.id.btSave)
    public void btSave(View v) {
        GameActivity.saveData();
    }

    public StatsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        unbinder = ButterKnife.bind(this, view);

        updateData();

        return view;
    }

    public void updateData() {
        tvLevel.setText(String.format("Level %d", SaveData.lvl));
        tvExp.setText(String.format("Exp %d / %d", SaveData.exp, SaveData.maxExp));
        tvHp.setText(String.format("HP %d / %d", SaveData.hp, SaveData.maxHp));
        tvStr.setText(String.format("STR %d", SaveData.str));
        tvDef.setText(String.format("DEF %d", SaveData.def));
        tvStatp.setText(String.format("Stat points: %d", SaveData.statp));
        if(SaveData.statp < 1) {
            btAddStr.setEnabled(false);
            btAddDef.setEnabled(false);
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
