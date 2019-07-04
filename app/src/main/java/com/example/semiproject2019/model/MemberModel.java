package com.example.semiproject2019.model;

import java.io.Serializable;

public class MemberModel implements Serializable { //회원 정보를 담을 그릇. serializable :: 통신 프로그램 작업 시 데이터 직렬화를 만들어줌.
    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + password2 + '\'' +
                '}';
    } //toString을 통해 들어있는 값 볼 수 있음

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    private String id;
    private String name;
    private String password;
    private String password2; //PWDConfirm
}
