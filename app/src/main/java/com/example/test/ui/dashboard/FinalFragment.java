package com.example.test.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;

public class FinalFragment extends Fragment {
    private String kombinacija = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_final, container, false);
        Bundle args = getArguments();
        kombinacija = args.getString("string", "");
        return root;
    }
}
