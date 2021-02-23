package com.example.test.ui.statistics;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.test.AppDatabase;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class RecikliranoTabFragment extends Fragment {

    ArrayList<RecycledItems> listItems = new ArrayList<>();
    RecycledAdapter adapter;
    AppDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_reciklirano_tab, container, false);


        try {
            adapter = new RecycledAdapter(this.getContext(), listItems);
            ListView listRecycled = (ListView) root.findViewById(R.id.listview);
            listRecycled.setAdapter(adapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Handler thread = new Handler(Looper.getMainLooper());
        thread.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("WORKING");
                db = AppDatabase.getInstance(getContext());
                try {
                    RecycledItems r1 = new RecycledItems(223, "staklo", "sise");
                    RecycledItems r2 = new RecycledItems(234, "plastika", "kese");
                    RecycledItems r3 = new RecycledItems(223, "staklo", "rr");
                    RecycledItems r4 = new RecycledItems(236, "plastika", "kerrse");
                    RecycledItems r5 = new RecycledItems(227, "staklo", "r3");
                    RecycledItems r6 = new RecycledItems(238, "plastika", "kesre");
                    RecycledItems r7 = new RecycledItems(229, "staklo", "sisre");
                    RecycledItems r8 = new RecycledItems(231, "plastika", "kerse");
                    db.recycledItemsDAO().insertItem(r1);
                    db.recycledItemsDAO().insertItem(r2);
                    db.recycledItemsDAO().insertItem(r2);
                    db.recycledItemsDAO().insertItem(r4);
                    db.recycledItemsDAO().insertItem(r5);
                    db.recycledItemsDAO().insertItem(r6);
                    db.recycledItemsDAO().insertItem(r7);
                    db.recycledItemsDAO().insertItem(r8);



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
}