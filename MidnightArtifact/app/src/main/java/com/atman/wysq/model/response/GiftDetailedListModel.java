package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by vavid on 2016/9/21.
 */
public class GiftDetailedListModel {

    private String result;

    private List<BodyBean> body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        private int gift_id;
        private long user_id;
        private long from_user_id;
        private long create_time;
        private long update_time;
        private String user_name;
        private String icon;
        private String sex;
        private String verify_status;
        private int userLevel;
        private String pic_url;
        private int gift_count;

        public int getGift_id() {
            return gift_id;
        }

        public void setGift_id(int gift_id) {
            this.gift_id = gift_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public long getFrom_user_id() {
            return from_user_id;
        }

        public void setFrom_user_id(long from_user_id) {
            this.from_user_id = from_user_id;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(String verify_status) {
            this.verify_status = verify_status;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getGift_count() {
            return gift_count;
        }

        public void setGift_count(int gift_count) {
            this.gift_count = gift_count;
        }
    }
}
