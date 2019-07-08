package com.example.semiproject2019.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.semiproject2019.R;
import com.example.semiproject2019.activity.DetailTabActivity;
import com.example.semiproject2019.activity.TabActivity;
import com.example.semiproject2019.bean.MemberBean;
import com.example.semiproject2019.bean.MemoBean;
import com.example.semiproject2019.db.FileDB;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemoFragment extends Fragment {
    public ListView lstMemo; //메모 리스트뷰

    List<MemberBean> memberBean = new ArrayList<>();
    ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo, container, false);

        lstMemo = view.findViewById(R.id.lstMemo);
        view.findViewById(R.id.btnNewMeMo_Activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TabActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void onResume() {
        super.onResume();
        MemberBean memberBean = FileDB.getLoginMember(getActivity()); //로그인된 멤버 받아오기
        List<MemoBean> memoList = FileDB.getMemoList(getActivity(), memberBean.memID); //메모리스트 받아오기

        adapter = new MemoAdapter (getActivity(), memoList);
        lstMemo.setAdapter(adapter);
    }

    class MemoAdapter extends BaseAdapter {
        private Context mContext;
        private List<MemoBean> mMemoList;

        public MemoAdapter(Context context, List<MemoBean> memoList) {
            mContext = context;
            mMemoList = memoList;
        }

        @Override
        public int getCount() {
            return mMemoList.size();
        }

        @Override
        public Object getItem(int i) {
            return mMemoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_memoitem, null);

            final MemoBean memoBean = mMemoList.get(position);

            //객체 획득
            ImageView memoItem_imgView = view.findViewById(R.id.memoItem_imgView);
            TextView memoitem_txtMemo = view.findViewById(R.id.memoitem_txtMemo);
            TextView memoItem_txtDate = view.findViewById(R.id.memoItem_txtDate);

            Button btnEdit = view.findViewById(R.id.btnEdit);
            Button btnDelete = view.findViewById(R.id.btnDelete);
            Button btnDetail = view.findViewById(R.id.btnDetail);

            memoItem_imgView.setImageURI( Uri.fromFile(new File(memoBean.memoPicPath)) );
            memoitem_txtMemo.setText( memoBean.memo );
            memoItem_txtDate.setText( memoBean.memoDate );

            //수정 버튼 클릭 이벤트
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), DetailTabActivity.class);
                    startActivity(i);
                }
            });

            //삭제 버튼 클릭 이벤트
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MemberBean memberBean = FileDB.getLoginMember(getActivity()); //fragment에서는 getActivity()로 받아옴...
                    //FileDB.delMemo(getActivity(), memberBean.memID, memoBean.memoID);
                }
            });
            //수정 버튼 클릭 이벤트
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), DetailTabActivity.class);
                    startActivity(i);
                }
            });

            return view;
        }
    }
}
