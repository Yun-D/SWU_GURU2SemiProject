package com.example.semiproject2019.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.semiproject2019.fragment.MemberInfoFragment;
import com.example.semiproject2019.fragment.MemoFragment;
import com.example.semiproject2019.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout; //탭영역
    private ViewPager viewPager; //탭별 표시할 영역

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        //Tab 생성
        tabLayout.addTab(tabLayout.newTab().setText("메모"));
        tabLayout.addTab(tabLayout.newTab().setText("회원정보"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL); //전체에 다 채우기

        //ViewPager 생성
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //ViewPager와 tab 연결
        viewPager.setAdapter(adapter); //아답터 등록으로 세개의 뷰페이저를 자동으로 등록시켜줌

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout)); //탭을 눌렀을 때 페이지가 같이 바뀌도록 리스너를 달아줌
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { // 탭이 선택되었을 때
                viewPager.setCurrentItem(tab.getPosition()); //화면 세팅(탭의 포지션에 해당되는 번호를 연동..)
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
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private int tabCount; //tab 수

        public ViewPagerAdapter(FragmentManager fm, int count){
            super(fm);
            this.tabCount = count; //탭의 수
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MemoFragment(); //메모 프레그먼트
                case 1:
                    return new MemberInfoFragment(); //회원정보 프레그먼트
            }
            return null;
        }

        @Override
        public int getCount(){
            return this.tabCount;
        }
    }
}
