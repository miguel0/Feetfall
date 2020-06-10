package com.sleepfall.feetfall.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sleepfall.feetfall.R;
import com.sleepfall.feetfall.utils.SaveData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EquipmentFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.ivHelmet) ImageView ivHelmet;
    @BindView(R.id.ivWeapon) ImageView ivWeapon;
    @BindView(R.id.ivArmor) ImageView ivArmor;

    public EquipmentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipment, container, false);
        unbinder = ButterKnife.bind(this, view);

        if(SaveData.helmet != null) {
            Glide.with(getContext()).load(SaveData.helmet.getImageId()).into(ivHelmet);

            ivHelmet.setOnClickListener(v -> {
                SaveData.items.add(SaveData.helmet);
                SaveData.helmet = null;
                ivHelmet.setImageDrawable(null);
            });
        }
        if(SaveData.weapon != null) {
            Glide.with(getContext()).load(SaveData.weapon.getImageId()).into(ivWeapon);

            ivWeapon.setOnClickListener(v -> {
                SaveData.items.add(SaveData.weapon);
                SaveData.weapon = null;
                ivWeapon.setImageDrawable(null);
            });
        }
        if(SaveData.armor != null) {
            Glide.with(getContext()).load(SaveData.armor.getImageId()).into(ivArmor);

            ivArmor.setOnClickListener(v -> {
                SaveData.items.add(SaveData.armor);
                SaveData.armor = null;
                ivArmor.setImageDrawable(null);
            });
        }

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
