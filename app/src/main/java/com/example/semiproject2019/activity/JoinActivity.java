package com.example.semiproject2019.activity;

import android.Manifest;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.semiproject2019.R;
import com.example.semiproject2019.bean.MemberBean;
import com.example.semiproject2019.db.FileDB;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinActivity extends AppCompatActivity {
    //private final String TAG = this.getClass().getSimpleName(); //클래스명 획득

    private ImageView mImgProfile;
    //사진이 저장되는 경로 - onActivityResult()로부터 받는 데이터
    private Uri mCaptureUri;
    //사진이 저장된 단말기상의 실제 경로
    public String mPhotoPath;
    //startActivityForResult() 에 넘겨주는 값, 이 값이 나중에 onActivityResult()로 돌아와서
    //내가 던진값인지를 구별할 때 사용하는 상수이다.
    public static final int REQUEST_IMAGE_CAPTURE = 200;

    //멤버변수
    private ImageView join_ImgView;
    private EditText join_ID, join_Name, join_PWD, join_PWDConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //카메라를 사용하기 위한 퍼미션을 요청한다.
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        }, 0);

        join_ID = findViewById(R.id.join_ID);
        join_Name = findViewById(R.id.join_Name);
        join_PWD = findViewById(R.id.join_PWD);
        join_PWDConfirm = findViewById(R.id.join_PWDConfirm);

        mImgProfile = findViewById(R.id.join_ImgView);
        Button btnJoin_Camera = findViewById(R.id.btnJoin_Camera);

        //카메라 버튼
        findViewById(R.id.btnJoin_Camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        //회원가입 버튼
        findViewById(R.id.btnJoin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinProcess();
            }
        });

        /*
         //회원가입 완료 버튼 (이전 버전)
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
        }); */
    }//end onCreate()

    //회원가입 작업 시작
    private void joinProcess() {
        MemberBean memberBean = new MemberBean();
        memberBean.memID = join_ID.getText().toString();
        memberBean.memName = join_Name.getText().toString();
        memberBean.photoPath = mPhotoPath;

        //아이디 칸에 공백이 있는지 확인
        if (TextUtils.isEmpty(memberBean.memID)) {
            Toast.makeText(this, "회원 아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        //이미 존재하는 회원 아이디를 찾는다.
        MemberBean findMemberBean = FileDB.getFindMember(this, memberBean.memID);
        if (findMemberBean != null) {
            //해당되는 아이디를 찾았다.
            Toast.makeText(this, "입력하신 회원 아이디가 이미 존재 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        //서로 패스워드가 일치하는지 확인
        String pw1 = join_PWD.getText().toString();
        String pw2 = join_PWDConfirm.getText().toString();

        if(!TextUtils.equals(pw1, pw2)) { //null값을 잡지 못하는 equals 대신 TextUtils의 equals를 씀.)
            // 일치하지 않는다면
            Toast.makeText(this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        memberBean.memPWD = pw1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //날짜, 시간 포맷 정해줌
        memberBean.memRegDate = sdf.format(new Date());

        //memberBean을 파일로 저장한다 -> JSON 변환 후
        FileDB.addMember(this, memberBean);

        //회원가입 완료
        Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
        finish();
    }

    //사진 찍기
    private void takePicture() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mCaptureUri = Uri.fromFile(getOutPutMediaFile());
        } else {
            mCaptureUri = FileProvider.getUriForFile(this,
                    "com.example.semiproject2019", getOutPutMediaFile());
        }

        i.putExtra(MediaStore.EXTRA_OUTPUT, mCaptureUri);

        //내가 원하는 액티비티로 이동하고, 그 액티비티가 종료되면 (finish되면)
        //다시금 나의 액티비티의 onActivityResult() 메서드가 호출되는 구조이다.
        //내가 어떤 데이터를 받고 싶을때 상대 액티비티를 호출해주고 그 액티비티에서
        //호출한 나의 액티비티로 데이터를 넘겨주는 구조이다. 이때 호출되는 메서드가
        //onActivityResult() 메서드 이다.
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);

    }

    private File getOutPutMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "cameraDemo");
        if(!mediaStorageDir.exists()) {
            if(!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File file = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        mPhotoPath = file.getAbsolutePath();

        return file;
    }

    private void sendPicture() {
        Bitmap bitmap = BitmapFactory.decodeFile(mPhotoPath);
        Bitmap resizedBmp = getResizedBitmap(bitmap, 4, 100, 100); //이미지 사진 크기 줄이기.

        bitmap.recycle();

        //사진이 캡쳐되서 들어오면 뒤집어져 있다. 이애를 다시 원상복구 시킨다.
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(mPhotoPath);
        } catch(Exception e) {
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;
        if(exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientToDegree(exifOrientation);
        } else {
            exifDegree = 0;
        }
        Bitmap rotatedBmp = roate(resizedBmp, exifDegree);
        mImgProfile.setImageBitmap( rotatedBmp );

        Toast.makeText(this, "사진 경로 : " + mPhotoPath, Toast.LENGTH_LONG).show(); //실제로 사진이 저장되는 경로
    }

    private int exifOrientToDegree(int exifOrientation) {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap roate(Bitmap bmp, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),
                matrix, true);
    }

    //비트맵의 사이즈를 줄여준다.
    public static Bitmap getResizedBitmap(Bitmap srcBmp, int size, int width, int height){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        Bitmap resized = Bitmap.createScaledBitmap(srcBmp, width, height, true);
        return resized;
    }

    public static Bitmap getResizedBitmap(Resources resources, int id, int size, int width, int height){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        Bitmap src = BitmapFactory.decodeResource(resources, id, options);
        Bitmap resized = Bitmap.createScaledBitmap(src, width, height, true);
        return resized;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //카메라로부터 오는 데이터를 취득한다.
        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_IMAGE_CAPTURE) {
                sendPicture();
            }
        }
    }
}
