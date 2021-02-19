package com.example.test.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.test.R;
import com.example.test.ui.statistics.CapatureAct;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private String kombinacija = "";
    private Button kopce1, kopce2, kopce3;
    private View view;
    Activity v;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            v = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        v = null;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.materijal_tekst);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        final Button buttonScan = root.findViewById(R.id.skeniraj);
        buttonScan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scanCode();
                    }
                }
        );
        TextView materijal = root.findViewById(R.id.materijal_tekst);
        materijal.setFocusable(false);
        //final Fragment fragment = null;
        kopce1 = root.findViewById(R.id.plastika);
        kopce1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("plastika", new PlasticsFragment()));
            }
        });
        kopce2 = root.findViewById(R.id.hartija);
        kopce2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("hartija", new PaperFragment()));
            }
        });
        kopce3 = root.findViewById(R.id.staklo);
        kopce3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                replaceFragment(formatFragment("staklo", new FinalFragment()));
            }
        });
        return root;
    }
                //scaner




    public void scanCode() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setCaptureActivity(CapatureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Product");
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                System.out.println(result.getContents());
                AlertDialog.Builder builder = new AlertDialog.Builder(v);
                builder.setMessage(result.getContents());
                builder.setTitle("Code");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();
                    }
                }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        v.finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(v, "No Result", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
