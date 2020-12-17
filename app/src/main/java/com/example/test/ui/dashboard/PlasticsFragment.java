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
        Bundle args = getArguments();
        kombinacija = args.getString("string", "");
        EditText otpadok = root.findViewById(R.id.otpadok_tekst);
        otpadok.setFocusable(false);
        kopce1 = root.findViewById(R.id.shishe);
        kopce1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "shishe";
                Fragment fragment = null;
                fragment = new FinalFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        kopce2 = root.findViewById(R.id.kapache);
        kopce2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "kapache";
                Fragment fragment = null;
                fragment = new FinalFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        kopce3 = root.findViewById(R.id.vrekja);
        kopce3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "vrekjazagjubre";
                Fragment fragment = null;
                fragment = new FinalFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        kopce4 = root.findViewById(R.id.sredstvo);
        kopce4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "sredstvocistenje";
                Fragment fragment = null;
                fragment = new FinalFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        kopce5 = root.findViewById(R.id.kesa);
        kopce5.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "kesa";
                Fragment fragment = null;
                fragment = new FinalFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        kopce6 = root.findViewById(R.id.coffee2go);
        kopce6.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "coffee2go";
                Fragment fragment = null;
                fragment = new FinalFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        kopce7 = root.findViewById(R.id.stiropor);
        kopce7.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "stiropor";
                Fragment fragment = null;
                fragment = new FinalFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        kopce8 = root.findViewById(R.id.telefon);
        kopce8.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "telefon";
                Fragment fragment = null;
                fragment = new FinalFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        kopce9 = root.findViewById(R.id.print3d);
        kopce9.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "3dprint";
                Fragment fragment = null;
                fragment = new FinalFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        kopce10 = root.findViewById(R.id.cetka);
        kopce10.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "cetkazazabi";
                Fragment fragment = null;
                fragment = new FinalFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        return root;
    }
    public void replaceFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new FinalFragment()).addToBackStack(null).commit();
    }
    public static PlasticsFragment newInstance(String string) {
        PlasticsFragment f = new PlasticsFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("string", string);
        f.setArguments(args);
        return f;
    }
}