package com.example.semiproject2019.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semiproject2019.R;
import com.example.semiproject2019.database.Database;
import com.example.semiproject2019.model.MemberModel;

public class JoinActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName(); //클래스명 획득

    //회원가입 아이디들
    EditText join_ID, join_Name, join_PWDConfirm, join_PWD;
    Button btnJoin;
    TextView txtPWDCheck;

    Database db; //db 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //DB 객체 획득
        db = Database.getInstance(getApplicationContext());

        //입력필드 객체 획득
        join_ID = findViewById(R.id.join_ID);
        join_Name = findViewById(R.id.join_Name);
        join_PWD = findViewById(R.id.join_PWD);
        join_PWDConfirm = findViewById(R.id.join_PWDConfirm);

        btnJoin = findViewById(R.id.btnJoin);

        txtPWDCheck = findViewById(R.id.txtPWDCheck);

        //회원가입 완료 버튼
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 후 DB에 넣기
                Log.d(TAG, "id : " + join_ID.getText());
                Log.d(TAG, "name : " + join_Name.getText());
                Log.d(TAG, "pwd : " + join_PWD.getText());
                Log.d(TAG, "pwd2: " + join_PWDConfirm.getText());

                MemberModel memberModel = new MemberModel(); //객체 생성
                memberModel.setId(join_ID.getText().toString());
                memberModel.setName(join_Name.getText().toString());
                memberModel.setPassword(join_PWD.getText().toString());
                //password2는 제외. 대신 맞는지 확인하기. (txtPWDCheck)

                //pwd와 pwdConfirm이 같다면
                if ((join_PWD.getText().toString()).equals(join_PWDConfirm.getText().toString())) {
                    txtPWDCheck.setText("패스워드가 동일합니다.");

                    //db 저장
                    db.setMember(memberModel);

                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    finish(); //메인화면으로
                } else {
                    txtPWDCheck.setText("패스워드를 다시 확인하세요..");
                }
            }
        });
    }
}
