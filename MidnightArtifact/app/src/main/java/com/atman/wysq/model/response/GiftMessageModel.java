package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by vavid on 2016/9/22.
 */
public class GiftMessageModel {
    /**
     * WYNotice : WYNotice
     * enter_center : true
     * type : 1
     * addvalue : []
     * push_content : 昵称 落霞与孤雾齐飞 给您送了 气球
     * center_content : 给您送了 气球
     * center_user_id : 450000168
     * center_user_name : 落霞与孤雾齐飞
     * center_user_avatar : /imageServer/C029174175f689b3451f9a80f8c9c28005d0.jpg
     * center_time : 1474513302777
     */

    private String WYNotice;
    private boolean enter_center;
    private int type;
    private String push_content;
    private String center_content;
    private long center_user_id;
    private String center_user_name;
    private String center_user_avatar;
    private long center_time;
    private List<?> addvalue;

    public String getWYNotice() {
        return WYNotice;
    }

    public void setWYNotice(String WYNotice) {
        this.WYNotice = WYNotice;
    }

    public boolean isEnter_center() {
        return enter_center;
    }

    public void setEnter_center(boolean enter_center) {
        this.enter_center = enter_center;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPush_content() {
        return push_content;
    }

    public void setPush_content(String push_content) {
        this.push_content = push_content;
    }

    public String getCenter_content() {
        return center_content;
    }

    public void setCenter_content(String center_content) {
        this.center_content = center_content;
    }

    public long getCenter_user_id() {
        return center_user_id;
    }

    public void setCenter_user_id(long center_user_id) {
        this.center_user_id = center_user_id;
    }

    public String getCenter_user_name() {
        return center_user_name;
    }

    public void setCenter_user_name(String center_user_name) {
        this.center_user_name = center_user_name;
    }

    public String getCenter_user_avatar() {
        return center_user_avatar;
    }

    public void setCenter_user_avatar(String center_user_avatar) {
        this.center_user_avatar = center_user_avatar;
    }

    public long getCenter_time() {
        return center_time;
    }

    public void setCenter_time(long center_time) {
        this.center_time = center_time;
    }

    public List<?> getAddvalue() {
        return addvalue;
    }

    public void setAddvalue(List<?> addvalue) {
        this.addvalue = addvalue;
    }
}
