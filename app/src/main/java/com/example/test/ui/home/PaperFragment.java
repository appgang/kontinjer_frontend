package com.example.test.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;

public class PaperFragment extends Fragment {
    private String kombinacija = "";
    private Button kopce1, kopce2, kopce3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_paper, container, false);
        Bundle args = this.getArguments();
        kombinacija = args.getString("string");
        System.out.println(kombinacija);
        TextView otpadok = root.findViewById(R.id.otpadok_tekst);
        otpadok.setFocusable(false);
        kopce1 = root.findViewById(R.id.karton);
        kopce1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("karton", new FinalFragment()));
            }
        });
        kopce2 = root.findViewById(R.id.ambalaza);
        kopce2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("ambalaza", new FinalFragment()));
            }
        });
        kopce3 = root.findViewById(R.id.list);
        kopce3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("tenka", new FinalFragment()));
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
