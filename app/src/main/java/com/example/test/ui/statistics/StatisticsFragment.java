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
    private Button kopce1, kopce2, kopce3;
    private View view;

    @Override
   /* public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            v = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        v = null;
    }*/

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statisticsViewModel =
                ViewModelProviders.of(this).get(StatisticsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);


        try {
            adapter = new RecycledAdapter(this.getContext(), listItems);
            ListView listRecycled = (ListView) root.findViewById(R.id.listview);
            listRecycled.setAdapter(adapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
       /* final Button buttonScan = root.findViewById(R.id.outlinedButton);
        buttonScan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scanCode();
                    }
                }
        );*/




        kopce1 = root.findViewById(R.id.reciklirano);
        kopce2 = root.findViewById(R.id.statistics);
        kopce1.setOnClickListener(new ToggleButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                    System.out.println("WORKafafafaING");

                //kopce2.setChecked(false);
                kopce2.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button));

                replaceFragment(formatFragment("reciklirano", new RecikliranoTabFragment()));



            }
        });


        kopce2.setOnClickListener(new ToggleButton.OnClickListener() {
            public void onClick(View view) {
                kopce1.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button));

                replaceFragment(formatFragment("statistika", new StatistikaTabFragment()));
            }
        });










        Handler thread = new Handler(Looper.getMainLooper());
        thread.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("WORKING");
                db = AppDatabase.getInstance(getContext());
                try {
                    RecycledItems r1 = new RecycledItems(223, "staklo", "sise");
                    RecycledItems r2 = new RecycledItems(234, "plastika", "kese");
                    db.recycledItemsDAO().insertItem(r1);
                    db.recycledItemsDAO().insertItem(r2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<RecycledItems> ril = db.recycledItemsDAO().getAll();
                for (RecycledItems r : ril) {
                    System.out.println(r.item);
                    listItems.add(r);
                    adapter.notifyDataSetChanged();
                }
            }
        });


        return root;

    }


   /* public void scanCode() {
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
    }*/
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
