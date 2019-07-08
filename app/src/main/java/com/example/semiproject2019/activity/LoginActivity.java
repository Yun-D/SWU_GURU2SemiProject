package com.example.semiproject2019.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semiproject2019.R;
import com.example.semiproject2019.bean.MemberBean;
import com.example.semiproject2019.db.FileDB;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName(); //클래스명 획득

    EditText login_ID, login_PWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_ID = findViewById(R.id.login_ID);
        login_PWD = findViewById(R.id.login_PWD);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnJoin_Activiy = findViewById(R.id.btnJoin_Activity);

        btnLogin.setOnClickListener(mBtnLoginClick);
        btnJoin_Activiy.setOnClickListener(mBtnJoinClick);
    }

    private View.OnClickListener mBtnLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String memID = login_ID.getText().toString();
            String memPW = login_PWD.getText().toString();

            MemberBean memberBean = FileDB.getFindMember(LoginActivity.this, memID);
            if(memberBean == null) {
                Toast.makeText(LoginActivity.this, "해당 아이디는 가입이 되어있지 않습니다. ", Toast.LENGTH_SHORT).show();
                return;
            }
            //패스워드 비교
            if (TextUtils.equals(memberBean.memPWD, memPW)) {
                FileDB.setLoginMember(LoginActivity.this, memberBean); //저장

                //비밀번호 일치
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };

    private View.OnClickListener mBtnJoinClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
            startActivity(intent);
        }
    };
}
