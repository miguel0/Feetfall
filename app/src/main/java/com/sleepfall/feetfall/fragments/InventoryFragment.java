package com.sleepfall.feetfall.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sleepfall.feetfall.R;
import com.sleepfall.feetfall.utils.ItemAdapter;
import com.sleepfall.feetfall.utils.SaveData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InventoryFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.rvItems) RecyclerView rvItems;

    public InventoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        unbinder = ButterKnife.bind(this, view);

        ItemAdapter adapter = new ItemAdapter(SaveData.items, getContext());
        SaveData.adapter = adapter;
        rvItems.setAdapter(adapter);

        GridLayoutManager glm = new GridLayoutManager(getContext(), 3);
        rvItems.setLayoutManager(glm);

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
