package com.atman.wysq.model.request;

/**
 * Created by vavid on 2016/9/13.
 */
public class SeedMessageModel {
    private String sex;
    private String icon;
    private int verify_status;
    private String nickName;

    public SeedMessageModel(String sex, String icon, int verify_status, String nickName) {
        this.sex = sex;
        this.icon = icon;
        this.verify_status = verify_status;
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getVerify_status() {
        return verify_status;
    }

    public void setVerify_status(int verify_status) {
        this.verify_status = verify_status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "{" +
                "sex='" + sex + '\'' +
                ", icon='" + icon + '\'' +
                ", verify_status=" + verify_status +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
