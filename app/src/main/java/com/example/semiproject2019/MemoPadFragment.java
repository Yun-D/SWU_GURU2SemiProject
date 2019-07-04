package com.example.semiproject2019;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MemoPadFragment extends Fragment {
    public MemoPadFragment() {}

    Button btnAddMeMo_Activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_pad, container, false);

        btnAddMeMo_Activity = view.findViewById(R.id.btnAddMeMo_Activity);
        btnAddMeMo_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(this, WritingFragment.class);
                //startActivity(intent);
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
