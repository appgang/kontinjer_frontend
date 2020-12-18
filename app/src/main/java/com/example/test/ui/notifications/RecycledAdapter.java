package com.example.test.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class RecycledAdapter extends ArrayAdapter<RecycledItems> {
    private Context c;
    private List<RecycledItems> rl = new ArrayList<RecycledItems>();

    public RecycledAdapter(@NonNull Context c, ArrayList<RecycledItems> list){
        super(c,0,list);
        this.c=c;
        rl=list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem==null)
            listItem = LayoutInflater.from(c).inflate(R.layout.listview_recycled,parent,false);

        RecycledItems rc = rl.get(position);
        ImageView iv = (ImageView)listItem.findViewById(R.id.icon_list);
        iv.setImageResource(R.drawable.ic_plastic_category);

        TextView tv = (TextView)listItem.findViewById(R.id.listview);
        tv.setText(rc.item);

        return listItem;
    }


}
