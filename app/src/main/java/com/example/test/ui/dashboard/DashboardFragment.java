package com.example.test.ui.dashboard;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.test.R;

import java.io.Console;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private String kombinacija = "";
    private Button kopce1, kopce2, kopce3;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        /*final TextView textView = root.findViewById(R.id.materijal_tekst);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        EditText materijal = root.findViewById(R.id.materijal_tekst);
        materijal.setFocusable(false);
        //final Fragment fragment = null;
        kopce1 = root.findViewById(R.id.plastika);
        kopce1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "plastika";
                Fragment fragment = null;
                fragment = new PlasticsFragment();
                replaceFragment(fragment);

            }
        });
        kopce2 = root.findViewById(R.id.hartija);
        kopce2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "hartija";
            }
        });
        kopce3 = root.findViewById(R.id.staklo);
        kopce3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                kombinacija += "staklo";
            }
        });
        return root;
    }

    public void replaceFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new PlasticsFragment()).addToBackStack(null).commit();
    }
}
