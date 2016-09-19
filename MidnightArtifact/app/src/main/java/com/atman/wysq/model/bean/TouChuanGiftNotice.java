package com.atman.wysq.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by vavid on 2016/9/19.
 */
@Entity
public class TouChuanGiftNotice {

    @Id(autoincrement = true)
    private Long id;
    private int enter_center;
    private String push_content;
    private long grab_user_id;
    private String grab_user_avatar;
    private long target_user_id;
    private long center_time;
    private String center_user_avatar;
    private String giftContent;
    private String grab_user_sex;
    private String giftIcon;
    private String chat_content;
    private int type;
    private String grab_user_signature;
    private String sender_avatar;
    private String WYNotice;
    private String giftSendName;
    private String center_user_name;
    private long chat_time;
    private int target_user_status;
    private String notice_content;
    private String other_content;
    private int grab_user_verify_status;
    private long sender_id;
    private int giftPrice;
    private String grab_user_nickName;
    private String sender_sex;
    private String center_content;
    private long center_user_id;
    private String sender_name;
    private String notice;
    private String chat_id;
    private String center_user_sex;
    private int isRead;
    public int getIsRead() {
        return this.isRead;
    }
    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
    public String getCenter_user_sex() {
        return this.center_user_sex;
    }
    public void setCenter_user_sex(String center_user_sex) {
        this.center_user_sex = center_user_sex;
    }
    public String getChat_id() {
        return this.chat_id;
    }
    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }
    public String getNotice() {
        return this.notice;
    }
    public void setNotice(String notice) {
        this.notice = notice;
    }
    public String getSender_name() {
        return this.sender_name;
    }
    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }
    public long getCenter_user_id() {
        return this.center_user_id;
    }
    public void setCenter_user_id(long center_user_id) {
        this.center_user_id = center_user_id;
    }
    public String getCenter_content() {
        return this.center_content;
    }
    public void setCenter_content(String center_content) {
        this.center_content = center_content;
    }
    public String getSender_sex() {
        return this.sender_sex;
    }
    public void setSender_sex(String sender_sex) {
        this.sender_sex = sender_sex;
    }
    public String getGrab_user_nickName() {
        return this.grab_user_nickName;
    }
    public void setGrab_user_nickName(String grab_user_nickName) {
        this.grab_user_nickName = grab_user_nickName;
    }
    public int getGiftPrice() {
        return this.giftPrice;
    }
    public void setGiftPrice(int giftPrice) {
        this.giftPrice = giftPrice;
    }
    public long getSender_id() {
        return this.sender_id;
    }
    public void setSender_id(long sender_id) {
        this.sender_id = sender_id;
    }
    public int getGrab_user_verify_status() {
        return this.grab_user_verify_status;
    }
    public void setGrab_user_verify_status(int grab_user_verify_status) {
        this.grab_user_verify_status = grab_user_verify_status;
    }
    public String getOther_content() {
        return this.other_content;
    }
    public void setOther_content(String other_content) {
        this.other_content = other_content;
    }
    public String getNotice_content() {
        return this.notice_content;
    }
    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }
    public int getTarget_user_status() {
        return this.target_user_status;
    }
    public void setTarget_user_status(int target_user_status) {
        this.target_user_status = target_user_status;
    }
    public long getChat_time() {
        return this.chat_time;
    }
    public void setChat_time(long chat_time) {
        this.chat_time = chat_time;
    }
    public String getCenter_user_name() {
        return this.center_user_name;
    }
    public void setCenter_user_name(String center_user_name) {
        this.center_user_name = center_user_name;
    }
    public String getGiftSendName() {
        return this.giftSendName;
    }
    public void setGiftSendName(String giftSendName) {
        this.giftSendName = giftSendName;
    }
    public String getWYNotice() {
        return this.WYNotice;
    }
    public void setWYNotice(String WYNotice) {
        this.WYNotice = WYNotice;
    }
    public String getSender_avatar() {
        return this.sender_avatar;
    }
    public void setSender_avatar(String sender_avatar) {
        this.sender_avatar = sender_avatar;
    }
    public String getGrab_user_signature() {
        return this.grab_user_signature;
    }
    public void setGrab_user_signature(String grab_user_signature) {
        this.grab_user_signature = grab_user_signature;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getChat_content() {
        return this.chat_content;
    }
    public void setChat_content(String chat_content) {
        this.chat_content = chat_content;
    }
    public String getGiftIcon() {
        return this.giftIcon;
    }
    public void setGiftIcon(String giftIcon) {
        this.giftIcon = giftIcon;
    }
    public String getGrab_user_sex() {
        return this.grab_user_sex;
    }
    public void setGrab_user_sex(String grab_user_sex) {
        this.grab_user_sex = grab_user_sex;
    }
    public String getGiftContent() {
        return this.giftContent;
    }
    public void setGiftContent(String giftContent) {
        this.giftContent = giftContent;
    }
    public String getCenter_user_avatar() {
        return this.center_user_avatar;
    }
    public void setCenter_user_avatar(String center_user_avatar) {
        this.center_user_avatar = center_user_avatar;
    }
    public long getCenter_time() {
        return this.center_time;
    }
    public void setCenter_time(long center_time) {
        this.center_time = center_time;
    }
    public long getTarget_user_id() {
        return this.target_user_id;
    }
    public void setTarget_user_id(long target_user_id) {
        this.target_user_id = target_user_id;
    }
    public String getGrab_user_avatar() {
        return this.grab_user_avatar;
    }
    public void setGrab_user_avatar(String grab_user_avatar) {
        this.grab_user_avatar = grab_user_avatar;
    }
    public long getGrab_user_id() {
        return this.grab_user_id;
    }
    public void setGrab_user_id(long grab_user_id) {
        this.grab_user_id = grab_user_id;
    }
    public String getPush_content() {
        return this.push_content;
    }
    public void setPush_content(String push_content) {
        this.push_content = push_content;
    }
    public int getEnter_center() {
        return this.enter_center;
    }
    public void setEnter_center(int enter_center) {
        this.enter_center = enter_center;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 936095183)
    public TouChuanGiftNotice(Long id, int enter_center, String push_content,
            long grab_user_id, String grab_user_avatar, long target_user_id,
            long center_time, String center_user_avatar, String giftContent,
            String grab_user_sex, String giftIcon, String chat_content, int type,
            String grab_user_signature, String sender_avatar, String WYNotice,
            String giftSendName, String center_user_name, long chat_time,
            int target_user_status, String notice_content, String other_content,
            int grab_user_verify_status, long sender_id, int giftPrice,
            String grab_user_nickName, String sender_sex, String center_content,
            long center_user_id, String sender_name, String notice, String chat_id,
            String center_user_sex, int isRead) {
        this.id = id;
        this.enter_center = enter_center;
        this.push_content = push_content;
        this.grab_user_id = grab_user_id;
        this.grab_user_avatar = grab_user_avatar;
        this.target_user_id = target_user_id;
        this.center_time = center_time;
        this.center_user_avatar = center_user_avatar;
        this.giftContent = giftContent;
        this.grab_user_sex = grab_user_sex;
        this.giftIcon = giftIcon;
        this.chat_content = chat_content;
        this.type = type;
        this.grab_user_signature = grab_user_signature;
        this.sender_avatar = sender_avatar;
        this.WYNotice = WYNotice;
        this.giftSendName = giftSendName;
        this.center_user_name = center_user_name;
        this.chat_time = chat_time;
        this.target_user_status = target_user_status;
        this.notice_content = notice_content;
        this.other_content = other_content;
        this.grab_user_verify_status = grab_user_verify_status;
        this.sender_id = sender_id;
        this.giftPrice = giftPrice;
        this.grab_user_nickName = grab_user_nickName;
        this.sender_sex = sender_sex;
        this.center_content = center_content;
        this.center_user_id = center_user_id;
        this.sender_name = sender_name;
        this.notice = notice;
        this.chat_id = chat_id;
        this.center_user_sex = center_user_sex;
        this.isRead = isRead;
    }
    @Generated(hash = 351630481)
    public TouChuanGiftNotice() {
    }
}
