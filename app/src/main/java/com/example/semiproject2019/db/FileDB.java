package com.example.semiproject2019.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.semiproject2019.bean.MemberBean;
import com.example.semiproject2019.bean.MemoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FileDB {
    private static final String FILE_DB = "FileDB";
    private static Gson mGson = new Gson();

    //메모 리스트 원본 데이터
    private static List<MemoBean> memos = null;

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

    //기존 멤버 교체. 메모 수정 시 사용
    public static void setMember (Context context, MemberBean memberBean) {
        //전체 멤버 리스트를 취득
        List<MemberBean> memberList = getMemberList(context);
        if (memberList.size() == 0) return;

        //있을경우 for문
        for(int i = 0; i < memberList.size(); i++) {
            MemberBean bean = memberList.get(i);
            if (TextUtils.equals(bean.memID, memberBean.memID)) {
                //같은 멤버 ID를 찾았다.
                memberList.set(i, memberBean);
                break;
            }
        }

        //새롭게 업데이트된 리스트를 저장한다.
        String jsonStr = mGson.toJson(memberList);

        //멤버리스트 저장
        SharedPreferences.Editor editor = getSP(context).edit();
        editor.putString("memberList", jsonStr);
        editor.commit();
    }

    //새로운 메모 추가
    public static void addMemo (Context context, String memID, MemoBean memoBean) {
        MemberBean findMember = getFindMember(context, memID);
        if (findMember == null) return;

        List<MemoBean> memoList = findMember.memoList;
        if(memoList == null) {
            memoList = new ArrayList<>();
        }
        //고유 메모 ID를 생성해준다.
        memoBean.memoID = System.currentTimeMillis();
        memoList.add(0, memoBean);
        findMember.memoList = memoList;

        //저장
        setMember(context, findMember);
    }

    //메모 수정
    public static void setMemo(Context context, String memID, MemoBean memoBean) {
        //TODO 구현
        //전체 멤버 리스트를 취득
        MemberBean memberBean = getFindMember(context, memID);
        if (memberBean.memoList.size() == 0) return;

        //있을경우 for문
        for (int i = 0; i < memberBean.memoList.size(); i++) {
            MemoBean bean = memberBean.memoList.get(i);
            if (bean.memo == memoBean.memo) {
                //같은 멤버 ID를 찾았다.
                memberBean.memoList.set(i, memoBean);
                break;
            }
        }

    }

    //TODO 메모삭제
    public static void delMemo(Context context, String memID, int memoID) {
        MemberBean memberBean = getFindMember(context, memID); //멤버 찾아
        List<MemoBean> memoList = getMemoList(context, memID); //메모리스트 불러와

        memberBean.memoList.remove(memoID - 1);
    }

    //TODO 메모 찾기
    public static MemoBean findMemo(Context context, String memID, int memoID) {
        List<MemoBean> memoList = getMemoList(context, memID); //메모리스트 불러와

        MemoBean memoBean = null;
        for (MemoBean bean : memoList) {
            if (memoBean.memoID == memoID) {  //아이디가 같다
                memoBean = bean;
            }
        }
        return memoBean;
    }




    //메모리스트 취득
    public static List<MemoBean> getMemoList (Context context, String memID) {
        MemberBean memberBean = getFindMember(context, memID);
        if(memberBean == null) return null;

        if(memberBean.memoList == null) {
            return  new ArrayList<>();
        } else {
            return memberBean.memoList;
        }
    }

    //멤버리스트 가져오기
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



    public static MemberBean getFindMember (Context context, String memID) {
        //로그인 하나하나 다 해보신다 함........ 으악!
        // 1. 멤버 리스트를 가져온다.
        List<MemberBean> memberList = getMemberList(context);

        // 2. for문 돌면서 해당 아이디 찾기
        for (MemberBean bean : memberList) {
            if (TextUtils.equals(bean.memID, memID)){ //아이디가 같다.
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
