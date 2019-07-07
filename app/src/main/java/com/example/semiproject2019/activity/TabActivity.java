package com.example.semiproject2019.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.semiproject2019.fragment.CameraFragment;
import com.example.semiproject2019.R;
import com.example.semiproject2019.fragment.WriteFragment;
import com.google.android.material.tabs.TabLayout;

public class TabActivity extends AppCompatActivity {

//    private TabLayout memoTabLayout; //탭영역
//    private ViewPager memoViewPager; //탭별 표시할 영역
//    Button btnSave; //메모 저장 버튼
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tab);
//
//        memoTabLayout = findViewById(R.id.memoTabLayout);
//        memoViewPager = findViewById(R.id.memoViewPager);
//
//        btnSave = findViewById(R.id.btnSave);
//
//        //Tab 생성
//        memoTabLayout.addTab(memoTabLayout.newTab().setText("글쓰기"));
//        memoTabLayout.addTab(memoTabLayout.newTab().setText("사진찍기"));
//
//        memoTabLayout.setTabGravity(TabLayout.GRAVITY_FILL); //전체에 다 채우기
//
//        //ViewPager 생성
//        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), memoTabLayout.getTabCount()); //프래그먼트매니저, 탭의 수를 받음
//        memoViewPager.setAdapter(adapter); //아답터 등록으로 세개의 뷰페이저를 자동으로 등록시켜줌
//        memoViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(memoTabLayout)); //탭을 눌렀을 때 페이지가 같이 바뀌도록 리스너를 달아줌
//        memoTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) { // 탭이 선택되었을 때
//                memoViewPager.setCurrentItem(tab.getPosition()); //화면 세팅(탭의 포지션에 해당되는 번호를 연동..)
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //DB 저장하기 TODO
//
//                // if 처리하기
//                Toast.makeText(getApplicationContext(), "메모가 저장 되었습니다.", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//    }
//
//    //아답터를 필수적으로 구현해 화면 전체에 뿌려주게 해야 함
//    class MyPagerAdapter extends FragmentPagerAdapter {
//        int tabSize; //tab 수
//
//        public MyPagerAdapter(FragmentManager fm, int count) {
//            super(fm);
//            this.tabSize = count; //탭의 수
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            switch (position) {
//                case 0:
//                    return new WriteFragment();
//                case 1:
//                    return new CameraFragment();
//            }
//            return null;
//        }
//
//        @Override
//        public int getCount() {
//            return this.tabSize;
//        }
//    }
}
