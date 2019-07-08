package com.example.semiproject2019.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.semiproject2019.bean.MemberBean;
import com.example.semiproject2019.bean.MemoBean;
import com.example.semiproject2019.db.FileDB;
import com.example.semiproject2019.fragment.CameraFragment;
import com.example.semiproject2019.R;
import com.example.semiproject2019.fragment.WriteFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.semiproject2019.db.FileDB.addMemo;
import static com.example.semiproject2019.db.FileDB.getLoginMember;

public class TabActivity extends AppCompatActivity {
   // public static final int SAVE = 1001;

    //List<MemoBean> memoBeans = new ArrayList<>();

    private TabLayout memoTabLayout;
    private ViewPager memoViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        memoTabLayout = findViewById(R.id.memoTabLayout);
        memoViewPager = findViewById(R.id.memoViewPager);

        findViewById(R.id.btnCancle).setOnClickListener(mBtnClick);
        findViewById(R.id.btnSave).setOnClickListener(mBtnClick);

        //Tab 생성
        memoTabLayout.addTab(memoTabLayout.newTab().setText("글쓰기"));
        memoTabLayout.addTab(memoTabLayout.newTab().setText("사진찍기"));
        memoTabLayout.setTabGravity(TabLayout.GRAVITY_FILL); //전체에 다 채우기

        //viewPager 생성
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), memoTabLayout.getTabCount());

        //tab과 viewPager 연결
        memoViewPager.setAdapter(mViewPagerAdapter); //아답터 등록으로 세개의 뷰페이저를 자동으로 등록시켜줌

        memoViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(memoTabLayout)); //탭을 눌렀을 때 페이지가 같이 바뀌도록 리스너를 달아줌
        memoTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                memoViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private View.OnClickListener mBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //어떤 버튼이 클릭됐는지 구분
            switch (view.getId()) {
                case R.id.btnCancle:
                    finish();
                    break;

                case R.id.btnSave:
                    saveProc();
                    break;
            }
        }
    };

    //저장버튼 저장처리
    private void saveProc() {
        // 1. 첫번째 프래그먼트의 EditText 값을 받아온다.
        WriteFragment f0 = (WriteFragment) mViewPagerAdapter.instantiateItem(memoViewPager,0);

        // 2. 두번째 프래그먼트의 mPhotoPath값을 가져온다.
        CameraFragment f1 =  (CameraFragment) mViewPagerAdapter.instantiateItem(memoViewPager, 1);

        EditText edtWriteMemo = f0.getView().findViewById(R.id.edtWriteMemo);
        String memoStr = edtWriteMemo.getText().toString();
        String photoPath = f1.mPhotoPath;

        Log.e("SEMI", "memoStr : " + memoStr + ", photoPath : " + photoPath);
        Toast.makeText(this, "memoStr : " + memoStr + ", photoPath : " + photoPath, Toast.LENGTH_LONG).show();

        //TODO 파일DB에 저장처리
        MemoBean memoBean = new MemoBean();

        //로그인된 사용자의 아이디 불러오기
        MemberBean memberBean = FileDB.getLoginMember(this);
        MemberBean loginMemID = getLoginMember(this);

        //사진 찍고 넘어가도록
        if (TextUtils.isEmpty(photoPath)){
            Toast.makeText(this, "사진을 찍어주세요.", Toast.LENGTH_SHORT).show();
        }

        //메모 입력하도록
        if (TextUtils.isEmpty(memoStr))
            Toast.makeText(this, "메모를 입력하세요.", Toast.LENGTH_SHORT).show();

        //메모 추가
        FileDB.addMemo(this, memberBean.memID, memoBean);
        //Toast.makeText(this, "메모 작성 완료", Toast.LENGTH_SHORT).show();

        finish();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private int tabCount;

        public ViewPagerAdapter (FragmentManager fm, int count) {
            super(fm);
            this.tabCount = count;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new WriteFragment();
                case 1:
                    return new CameraFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return this.tabCount;
        }
    }
}
