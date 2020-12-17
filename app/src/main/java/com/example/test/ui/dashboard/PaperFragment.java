package com.example.test.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
        Bundle args = getArguments();
        kombinacija = args.getString("string", "");
        EditText otpadok = root.findViewById(R.id.otpadok_tekst);
        otpadok.setFocusable(false);
        kopce1 = root.findViewById(R.id.karton);
        kopce1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "karton";
                Fragment fragment = null;
                fragment = new PlasticsFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        kopce2 = root.findViewById(R.id.ambalaza);
        kopce2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "ambalaza";
                Fragment fragment = null;
                fragment = new PaperFragment();
                replaceFragment(fragment);
                newInstance(kombinacija);
            }
        });
        kopce3 = root.findViewById(R.id.list);
        kopce3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "tenka";
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
    public static PaperFragment newInstance(String string) {
        PaperFragment f = new PaperFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("string", string);
        f.setArguments(args);
        return f;
    }
}
