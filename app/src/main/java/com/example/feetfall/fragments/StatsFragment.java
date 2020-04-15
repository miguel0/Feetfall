package com.example.feetfall.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @OnClick(R.id.btSave)
    public void btSave(View v) {
        GameActivity.saveData();
    }

    public StatsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvLevel.setText(String.format("Level %d", SaveData.lvl));
        tvExp.setText(String.format("Exp %d / %d", SaveData.exp, SaveData.maxExp));
        tvHp.setText(String.format("HP %d / %d", SaveData.hp, SaveData.maxHp));
        tvStr.setText(String.format("STR %d", SaveData.str));
        tvDef.setText(String.format("DEF %d", SaveData.def));

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
