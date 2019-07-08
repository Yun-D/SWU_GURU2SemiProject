package com.example.semiproject2019.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.semiproject2019.fragment.MemberInfoFragment;
import com.example.semiproject2019.fragment.MemoFragment;
import com.example.semiproject2019.R;
import com.google.android.material.tabs.TabLayout;

public class DetailTabActivity extends AppCompatActivity {

    private TabLayout detailTabLayout; //탭영역
    private ViewPager detailViewPager; //탭별 표시할 영역

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tab);

        detailTabLayout = findViewById(R.id.detailTabLayout);
        detailViewPager = findViewById(R.id.detailViewPager);

        //Tab 생성
        detailTabLayout.addTab(detailTabLayout.newTab().setText("글쓰기"));
        detailTabLayout.addTab(detailTabLayout.newTab().setText("사진찍기"));
        detailTabLayout.addTab(detailTabLayout.newTab().setText("위치정보"));

        detailTabLayout.setTabGravity(TabLayout.GRAVITY_FILL); //전체에 다 채우기

        //ViewPager 생성
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), detailTabLayout.getTabCount()); //프래그먼트매니저, 탭의 수를 받음
        detailViewPager.setAdapter(adapter); //아답터 등록으로 세개의 뷰페이저를 자동으로 등록시켜줌
        detailViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(detailTabLayout)); //탭을 눌렀을 때 페이지가 같이 바뀌도록 리스너를 달아줌
        detailTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { // 탭이 선택되었을 때
                detailViewPager.setCurrentItem(tab.getPosition()); //화면 세팅(탭의 포지션에 해당되는 번호를 연동..)
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //아답터를 필수적으로 구현해 화면 전체에 뿌려주게 해야 함
    class MyPagerAdapter extends FragmentPagerAdapter {
        int tabSize; //tab 수

        public MyPagerAdapter(FragmentManager fm, int count) {
            super(fm);
            this.tabSize = count; //탭의 수
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MemoFragment();
                case 1:
                    return new MemberInfoFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return this.tabSize;
        }
    }

    //TODO TabActivity 참고
}
