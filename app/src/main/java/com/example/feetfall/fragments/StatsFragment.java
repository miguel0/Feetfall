package com.example.feetfall.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.feetfall.GameActivity;
import com.example.feetfall.R;
import com.example.feetfall.utils.SaveData;
import com.google.android.material.snackbar.Snackbar;

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
    @BindView(R.id.ivAddStr) ImageView btAddStr;
    @BindView(R.id.ivAddDef) ImageView btAddDef;

    @OnClick(R.id.ivAddStr)
    public void btAddStr(View v) {
        SaveData.str++;
        SaveData.statp --;
        updateData();
    }

    @OnClick(R.id.ivAddDef)
    public void btAddDef(View v) {
        SaveData.def++;
        SaveData.statp--;
        updateData();
    }

    @OnClick(R.id.btSave)
    public void btSave(View v) {
        GameActivity.saveData();
        Snackbar.make(getView().findViewById(R.id.crStats), "Progress Saved", Snackbar.LENGTH_SHORT).show();
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
        tvLevel.setText(String.format("Level\n%d", SaveData.lvl));

        int n = (int) ((1.0 * SaveData.exp / SaveData.maxExp) * 10);
        tvExp.setText(String.format("%s%s", new String(new char[n]).replace("\0", "*"), new String(new char[10 - n]).replace("\0", " ")));

        tvHp.setText(String.format("HP %d / %d", SaveData.hp, SaveData.maxHp));
        tvStr.setText(String.format("STR %d", SaveData.getStr()));
        tvDef.setText(String.format("DEF %d", SaveData.getDef()));
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
