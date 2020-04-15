package com.example.feetfall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import com.example.feetfall.fragments.EquipmentFragment;
import com.example.feetfall.fragments.InventoryFragment;
import com.example.feetfall.fragments.StatsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        final Fragment fragmentStats = new StatsFragment();
        final Fragment fragmentInventory = new InventoryFragment();
        final Fragment fragmentEquipment = new EquipmentFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    Fragment fragment;
                    switch (item.getItemId()) {
                        case R.id.action_stats:
                            fragment = fragmentStats;
                            break;
                        case R.id.action_inventory:
                            fragment = fragmentInventory;
                            break;
                        case R.id.action_equip:
                        default:
                            fragment = fragmentEquipment;
                            break;
                    }
                    fragmentManager.beginTransaction().replace(R.id.flFragments, fragment).commit();
                    return true;
                });

        bottomNavigationView.setSelectedItemId(R.id.action_stats);
    }
}
