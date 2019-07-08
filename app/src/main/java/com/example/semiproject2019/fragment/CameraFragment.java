package com.example.semiproject2019.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.semiproject2019.R;

import java.io.File;

public class CameraFragment extends Fragment {
    //사진 찍기

    public String mPhotoPath = "/sdcard/hello/world.jpg"; //dummy data

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        ImageView newMemo_ImgView = view.findViewById(R.id.newMemo_ImgView);

        //newMemo_ImgView.setImageURI(Uri.fromFile(new File(memberBean.photoPath))); //이미지 파일 -> modify에서 써먹기...

        return view;
    }
}
