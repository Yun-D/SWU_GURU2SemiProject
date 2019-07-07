package com.example.semiproject2019.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.semiproject2019.R;
import com.example.semiproject2019.activity.MainActivity;

public class MemoFragment extends Fragment {
    public MemoFragment() {}

    Button btnAddMeMo_Activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo, container, false);

        btnAddMeMo_Activity = view.findViewById(R.id.btnAddMeMo_Activity);
        btnAddMeMo_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
