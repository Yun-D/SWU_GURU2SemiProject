package com.example.semiproject2019.database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.semiproject2019.model.MemberModel;
import com.example.semiproject2019.model.MyItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {
    public final static String TBL_ITEM = "ITEM"; //키워드 지정

    static Database list;
    static SharedPreferences sf = null; //지정 객체, 초기화

    private static List<MyItem> items = null; // 원본 데이터

    public Database() {} //public이면 외부에서도 생성할 수 있것지..

    public static Database getInstance(Context context){
        if (items == null)
            items = new ArrayList<>();

        if (sf == null) {
            sf = context.getSharedPreferences("MEMO", Activity.MODE_PRIVATE); //이니셜. 내가 만든 어플리케이션의 생명주기와 똑같아짐. 일반적으로 프라이빗 모드로 많이 사용. 스키마 정도의 기능을 함
        }

        if (list == null) {
            list = new Database();
        }

        return list; //자기 자신인 리스트를 반환하도록
    }
    //Item 선두에 추가
    public void addItem (MyItem item) {
        items.add(0, item);
    }

    //item 획득
    public MyItem getItem(int index){
        return items.get(index); //index에 해당하는 아이템 가져오기
    }

    //item 변경 api
    public void setItem (int index, MyItem item) {
        items.set(index, item); //변경. 바꿔 써버림!!
    }

    //item 삭제
    public void removeItem(int index) {
        items.remove(index);
    }

    //items를 SharedPreferences에 저장
    public void saveItems(){
        //객체를 문자열로(Json 포맷)
        String itemString = new GsonBuilder().serializeNulls().create().toJson(items);

        //저장
        SharedPreferences.Editor editor = sf.edit();
        editor.putString(TBL_ITEM, itemString); // key, value 형식으로 지정
        editor.commit();
    }

    //item 획득
    public List<MyItem> loadItems() {
        //SharedPreferences 의 Items 정보를 문자열로 획득
        String itemsString = sf.getString(TBL_ITEM, ""); //저장한걸 그대로 가져와~~~~ 키워드로 꺼내서 가져오고, 못꺼냈을 경우 길이가 0인 문자열이 빠져나옴.
        if (!itemsString.isEmpty()) { //데이터가 있다면 if문 안으로, 없으면 그대로 리턴
            // 문자열을 MyItem 배열 형태로 변환
            MyItem[] itemArray = new Gson().fromJson(itemsString, MyItem[].class); //문자열을 Gson이라는 객체의 fromJson이라는 메소드를 사용해 배열형태로 바꿔줌.

            //배열을 ArrayList 형태로 변환
            items = new ArrayList<>(Arrays.asList(itemArray)); //핸들링이 가능한 Array 형태를 사용할 것. 배열을 파라미터로 받으면 ArrayList로 바꿔준대
        }

        return items;
    }

    //회원 저장
    public void setMember(MemberModel memberModel){
        //Member 객체를 Json형태의 문자열로 변환
        String memberString = new GsonBuilder().serializeNulls().create().toJson(memberModel); //문자열 형태로 반환하겠다
        Log.d("Databse", "memberString : " + memberString);

        //저장
        SharedPreferences.Editor editor = sf.edit();
        editor.putString(memberModel.getId(), memberString); //key : value 형식으로 지정
        editor.commit();
    }

    //회원 조회
    public  MemberModel getMember(String id) {
        MemberModel memberModel = null;

        //ID를 이용해 회원정보 획득
        String memberString = sf.getString(id, ""); //길이가 0인 문자열(length가 0)
        if (memberString != null && memberString.length() > 0) {
            memberModel = new Gson().fromJson(memberString, MemberModel.class); //json 포맷을 역으로 만들어 객체로 만듦. 문자열을 멤버모델 클래스 형태로 변환.
        }
        else {
            Log.e("Database", "member null");
        }
        return memberModel;
    }

    //회원의 비밀번호 체크
    public boolean checkMember (String id, String pwd) {
        boolean isTrue = false;

        if (id.isEmpty() || pwd.isEmpty()){ //아이디나 패스워드가 비어있다면 체크 실패
            return isTrue; //실패
        }

        //회원 획득
        MemberModel memberModel = getMember(id); // 아이디 호출
        if (memberModel == null) { //아이디에 해당되는 멤버가 없다면
            return isTrue; //false 리턴 (실패)
        }

        if (memberModel.getPassword().equals(pwd)){ //파라미터로 받은 패스워드와 동일한지 확인
            isTrue = true; //성공!
        }

        return isTrue;
    }
}
