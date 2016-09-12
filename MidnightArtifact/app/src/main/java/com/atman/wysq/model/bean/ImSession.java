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
    @Id
    private String userId;
    private String loginUserId;
    private String content;
    private String nickName;
    private String icon;
    private String sex;
    private int verify_status;
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
    public int getVerify_status() {
        return this.verify_status;
    }
    public void setVerify_status(int verify_status) {
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
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getLoginUserId() {
        return this.loginUserId;
    }
    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    @Generated(hash = 1213762483)
    public ImSession(String userId, String loginUserId, String content,
            String nickName, String icon, String sex, int verify_status, long time,
            int unreadNum) {
        this.userId = userId;
        this.loginUserId = loginUserId;
        this.content = content;
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
