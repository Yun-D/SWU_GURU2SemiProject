package com.example.semiproject2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
메시지는 토스트로 나오거나 / 다이얼로그
리스트 자체를 json으로 저장하고....
카메라 찍고 - 물리적 장소에 저장. 경로 확실히 저장하고 알고있기

회원가입하고 로그인하면 그 사람의 메모가 보여야 해
새 메모작성 - 첫번째 탭 : 글쓰기. 여러줄 입력 가능해야 함
  	    - 두번째 탭 : 사진찍기. 사진을 안찍으면 저장이 안되도록.
	    - 저장 시 날짜까지 저장하도록
메모 수정 - EditText로 기존 내용 보이고, 수정할 수 있게
	  - 수정된 내용 데이터베이스에 반영되어야 함

회원정보 - 수정 아니고 뿌려만주기. ID, 이름, 비번

전체적으로 - 메모탭 / 회원정보 탭 임

         */

        Button btnJoin_Activity = findViewById(R.id.btnJoin_Activity);
        btnJoin_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
            }
        });


    }
}
