package com.example.semiproject2019.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semiproject2019.R;
import com.example.semiproject2019.bean.MemberBean;
import com.example.semiproject2019.db.FileDB;

import java.io.File;

public class MemberInfoFragment extends Fragment {
    //회원 정보

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_info, container, false);

        ImageView memberInfo_imgView = view.findViewById(R.id.memberInfo_imgView);
        TextView memberInfo_userID = view.findViewById(R.id.memberInfo_userID);
        TextView memberInfo_userName = view.findViewById(R.id.memberInfo_userName);
        TextView memberInfo_userPWD = view.findViewById(R.id.memberInfo_userPWD);
        TextView memberInfo_date = view.findViewById(R.id.memberInfo_date);

        //파일DB에서 가져오기
        MemberBean memberBean = FileDB.getLoginMember(getActivity());

        memberInfo_imgView.setImageURI(Uri.fromFile(new File(memberBean.photoPath))); //이미지 파일
        memberInfo_userID.setText(memberBean.memID); //아디
        memberInfo_userName.setText(memberBean.memName); //이름
        memberInfo_userPWD.setText(memberBean.memPWD);//비번
        memberInfo_date.setText(memberBean.memRegDate); //날짜

        return view;
    }
}
