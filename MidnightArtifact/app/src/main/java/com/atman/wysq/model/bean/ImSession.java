package com.atman.wysq.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/2 13:37
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
@Entity
public class ImSession {
    private String content;
    private String userId;
    private String nickName;
    private String icon;
    private String sex;
    private String verify_status;
    private long time;
    private int unreadNum;
    public int getUnreadNum() {
        return this.unreadNum;
    }
    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getVerify_status() {
        return this.verify_status;
    }
    public void setVerify_status(String verify_status) {
        this.verify_status = verify_status;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    @Generated(hash = 1805049135)
    public ImSession(String content, String userId, String nickName, String icon,
            String sex, String verify_status, long time, int unreadNum) {
        this.content = content;
        this.userId = userId;
        this.nickName = nickName;
        this.icon = icon;
        this.sex = sex;
        this.verify_status = verify_status;
        this.time = time;
        this.unreadNum = unreadNum;
    }
    @Generated(hash = 1305805177)
    public ImSession() {
    }
}
