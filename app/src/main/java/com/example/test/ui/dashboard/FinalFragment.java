package com.example.test.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FinalFragment extends Fragment {
    private String kombinacija = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_final, container, false);
        Bundle args = getArguments();
        kombinacija = args.getString("string", "");
        System.out.println(kombinacija);
        try {
            JSONObject object = new JSONObject("tipovi.json");
            JSONArray array = object.getJSONArray("tipovi");
            for (int i=0; i<array.length(); i++)
            {
                JSONObject tip = array.getJSONObject(i);
                String kombo = tip.getString("string");
                System.out.println(kombo);
                if (kombinacija==kombo)
                {
                    String vreme = tip.getString("vreme");
                    TextView view = root.findViewById(R.id.textView);
                    view.setText(kombo);
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return root;
    }
}
