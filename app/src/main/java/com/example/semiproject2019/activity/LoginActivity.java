package com.example.semiproject2019.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.semiproject2019.R;
import com.example.semiproject2019.database.Database;
import com.example.semiproject2019.model.MemberModel;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName(); //클래스명 획득

    EditText login_ID, login_PWD;
    Button btnLogin, btnJoin_Activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_ID = findViewById(R.id.login_ID);
        login_PWD = findViewById(R.id.login_PWD);

        btnLogin = findViewById(R.id.btnLogin); //로그인 버튼


        btnLogin.setOnClickListener(new View.OnClickListener() {
            Database db = Database.getInstance(getApplicationContext());

            @Override
            public void onClick(View view) {
                MemberModel savedMember = db.getMember(login_ID.getText().toString());
                if (savedMember != null) {
                    Log.d(TAG, "savedMember = " + savedMember.toString());
                }
                else {
                    Log.d(TAG, "회원정보 없음.");
                }

            }
        });

        btnJoin_Activity = findViewById(R.id.btnJoin_Activity); //회원가입 버튼
        btnJoin_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });


    }
}
