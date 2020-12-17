package com.example.test.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test.R;

public class PlasticsFragment extends Fragment {

    private String kombinacija = "";
    private Button kopce1, kopce2, kopce3, kopce4, kopce5, kopce6, kopce7, kopce8, kopce9, kopce10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_plastics, container, false);
        Bundle args = this.getArguments();
        kombinacija = args.getString("string");
        System.out.println(kombinacija);
        EditText otpadok = root.findViewById(R.id.otpadok_tekst);
        otpadok.setFocusable(false);
        kopce1 = root.findViewById(R.id.shishe);
        kopce1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("shishe", new FinalFragment()));
            }
        });
        kopce2 = root.findViewById(R.id.kapache);
        kopce2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("kapache", new FinalFragment()));
            }
        });
        kopce3 = root.findViewById(R.id.vrekja);
        kopce3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("vrekjazagjubre", new FinalFragment()));
            }
        });
        kopce4 = root.findViewById(R.id.sredstvo);
        kopce4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("sredstvocistenje", new FinalFragment()));
            }
        });
        kopce5 = root.findViewById(R.id.kesa);
        kopce5.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("kesa", new FinalFragment()));
            }
        });
        kopce6 = root.findViewById(R.id.coffee2go);
        kopce6.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("coffee2go", new FinalFragment()));
            }
        });
        kopce7 = root.findViewById(R.id.stiropor);
        kopce7.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("stiropor", new FinalFragment()));
            }
        });
        kopce8 = root.findViewById(R.id.telefon);
        kopce8.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("telefon", new FinalFragment()));
            }
        });
        kopce9 = root.findViewById(R.id.print3d);
        kopce9.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("3dprint", new FinalFragment()));
            }
        });
        kopce10 = root.findViewById(R.id.cetka);
        kopce10.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("cetkazazabi", new FinalFragment()));
            }
        });
        return root;
    }

    public void replaceFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
    }

    public Fragment formatFragment(String string, Fragment fragment) {
        kombinacija += string;
        Bundle args = new Bundle();
        args.putString("string", kombinacija);
        fragment.setArguments(args);
        return fragment;
    }
}