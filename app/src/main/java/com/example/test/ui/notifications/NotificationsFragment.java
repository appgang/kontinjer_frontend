package com.example.test.ui.notifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.example.test.AppDatabase;
import com.example.test.R;
import com.example.test.ui.home.HomeFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    ArrayList<RecycledItems> listItems = new ArrayList<>();
    RecycledAdapter adapter;
    AppDatabase db;
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

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        final Button buttonScan = root.findViewById(R.id.outlinedButton);
        try {
            adapter = new RecycledAdapter(this.getContext(), listItems);
            ListView listRecycled = (ListView) root.findViewById(R.id.listview);
            listRecycled.setAdapter(adapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        buttonScan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scanCode();
                    }
                }
        );

        Handler thread = new Handler(Looper.getMainLooper());
        thread.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("WORKING");
                db = AppDatabase.getInstance(getContext());
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

}
