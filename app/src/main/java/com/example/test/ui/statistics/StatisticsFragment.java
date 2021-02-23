package com.example.test.ui.statistics;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.test.AppDatabase;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel statisticsViewModel;
    ArrayList<RecycledItems> listItems = new ArrayList<>();
    RecycledAdapter adapter;
    AppDatabase db;
    Activity v;
    private String kombinacija = "";

    private View view;

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statisticsViewModel =
                ViewModelProviders.of(this).get(StatisticsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

        replaceFragment(formatFragment("reciklirano", new RecikliranoTabFragment()));

        final ToggleButton kopce1 =  root.findViewById(R.id.reciklirano);
        final ToggleButton kopce2 = root.findViewById(R.id.statistics);
        kopce1.setChecked(true);
        kopce1.setEnabled(false);
        kopce2.setChecked(false);

        kopce1.setOnClickListener(new ToggleButton.OnClickListener() {
            public void onClick(View view) {
                if(kopce2.isChecked()==true) {
                    kopce2.setChecked(false);
                    kopce1.setEnabled(false);
                    kopce2.setEnabled(true);
                    replaceFragment(formatFragment("reciklirano", new RecikliranoTabFragment()));
                }

            }
        });

        kopce2.setOnClickListener(new ToggleButton.OnClickListener() {
            public void onClick(View view) {
                if(kopce1.isChecked()==true) {
                    kopce1.setChecked(false);
                    kopce1.setEnabled(true);
                    kopce2.setEnabled(false);
                    replaceFragment(formatFragment("statistika", new StatistikaTabFragment()));
                }
            }
        });


        return root;

    }

   public void replaceFragment(Fragment fragment) {
       getFragmentManager().beginTransaction().replace(R.id.tab_placeholder, fragment).addToBackStack(null).commit();
   }

    public Fragment formatFragment(String string, Fragment fragment) {
        kombinacija += string;
        Bundle args = new Bundle();
        args.putString("string", kombinacija);
        fragment.setArguments(args);
        return fragment;
    }
}
