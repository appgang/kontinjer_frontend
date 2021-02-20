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

public class StatistikaTabFragment extends Fragment {

    ArrayList<RecycledItems> listItems = new ArrayList<>();
    RecycledAdapter adapter;
    AppDatabase db;
    public StatistikaTabFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_statistika_tab, container, false);


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
}