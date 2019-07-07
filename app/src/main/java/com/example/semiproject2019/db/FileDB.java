package com.example.semiproject2019.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.semiproject2019.bean.MemberBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FileDB {
    private static final String FILE_DB = "FileDB";
    private static Gson mGson = new Gson();

    private static SharedPreferences getSP(Context context){
        SharedPreferences sp = context.getSharedPreferences(FILE_DB, Context.MODE_PRIVATE);
        return sp;
    }

    //새로운 멤버 추가
    public static void addMember (Context context, MemberBean memberBean) {
        // 1. 기존의 멤버 리스트 불러오기
        List<MemberBean> memberList = getMemberList(context);

        // 2. 기존의 멤버 리스트에 추가
        memberList.add(memberBean);

        // 3. 멤버 리스트를 저장한다.
        String listStr = mGson.toJson(memberList);

        // 4. 저장해라
        SharedPreferences.Editor editor = getSP(context).edit().putString("memberList", listStr);
        editor.putString("memberList", listStr);
        editor.commit();
    }

    public static List<MemberBean> getMemberList (Context context) {
        String listStr = getSP(context).getString("memberList", null);
        if (listStr == null) {
            //저장된 리스트가 없을 경우 새로운 리스트 리턴
            return new ArrayList<MemberBean>();
        }
        // 있을 경우 Gson으로 변환
        List<MemberBean> memberList =
                mGson.fromJson(listStr, new TypeToken<List<MemberBean>>(){}.getType());
        return memberList;
    }

    public static MemberBean getFindMember (Context context, String memId) {
        //로그인 하나하나 다 해보신다 함........ 으악!
        // 1. 멤버 리스트를 가져온다.
        List<MemberBean> memberList = getMemberList(context);

        // 2. for문 돌면서 해당 아이디 찾기
        for (MemberBean bean : memberList) {
            if (TextUtils.equals(bean.memID, memId)){ //아이디가 같다.
                // 3. 찾았을 경우 해당 MemberBean을 리턴
                return bean;
            }
        }

        // 3-2. 못 찾았을 경우는 null 리턴
        return null;
    }

    // 로그인한 memberBean을 저장
    public static void setLoginMember (Context context, MemberBean bean) {
        if (bean != null) {
            String str = mGson.toJson(bean);
            SharedPreferences.Editor editor = getSP(context).edit();
            editor.putString("loginMemberBean", str);
            editor.commit();
        }
    }

    //로그인한 MemberBean을 취득
    public static MemberBean getLoginMember (Context context) {
        String str = getSP(context).getString("loginMemberBean", null);

        if(str == null) return null;
        MemberBean memberBean = mGson.fromJson(str, MemberBean.class);
        return memberBean;
    }
}
