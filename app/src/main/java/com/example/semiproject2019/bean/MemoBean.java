package com.example.semiproject2019.bean;

import java.util.List;
import java.io.Serializable;

public class MemoBean {
    public String memoPicPath;
    public String memo;
    public String memoDate;
    public long memoID; //고유 번호


    public long getMemoID() {
        return memoID;
    }

    public void setMemoID(long memoID) {
        this.memoID = memoID;
    }

    public String getMemoPicPath() {
        return memoPicPath;
    }

    public void setMemoPicPath(String memoPicPath) {
        this.memoPicPath = memoPicPath;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemoDate() {
        return memoDate;
    }

    public void setMemoDate(String memoDate) {
        this.memoDate = memoDate;
    }


}
