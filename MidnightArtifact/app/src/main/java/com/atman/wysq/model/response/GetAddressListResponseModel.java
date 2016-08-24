package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/21 14:23
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetAddressListResponseModel {
    /**
     * result : 1
     * body : [{"address_id":2037,"receiver_name":"唐炳良","receiver_address":"张江路","receiver_area":"310115","receiver_phone":"18578909061","status":1,"use_count":0,"update_time":1469072472000,"type":1,"receiver_area_name":"上海 上海市 浦东新区 "}]
     */

    private String result;
    /**
     * address_id : 2037
     * receiver_name : 唐炳良
     * receiver_address : 张江路
     * receiver_area : 310115
     * receiver_phone : 18578909061
     * status : 1
     * use_count : 0
     * update_time : 1469072472000
     * type : 1
     * receiver_area_name : 上海 上海市 浦东新区
     */

    private List<BodyEntity> body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BodyEntity> getBody() {
        return body;
    }

    public void setBody(List<BodyEntity> body) {
        this.body = body;
    }

    public static class BodyEntity {
        private int address_id;
        private String receiver_name;
        private String receiver_address;
        private String receiver_area;
        private String receiver_phone;
        private int status;
        private int use_count;
        private long update_time;
        private int type;
        private String receiver_area_name;

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public String getReceiver_address() {
            return receiver_address;
        }

        public void setReceiver_address(String receiver_address) {
            this.receiver_address = receiver_address;
        }

        public String getReceiver_area() {
            return receiver_area;
        }

        public void setReceiver_area(String receiver_area) {
            this.receiver_area = receiver_area;
        }

        public String getReceiver_phone() {
            return receiver_phone;
        }

        public void setReceiver_phone(String receiver_phone) {
            this.receiver_phone = receiver_phone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUse_count() {
            return use_count;
        }

        public void setUse_count(int use_count) {
            this.use_count = use_count;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getReceiver_area_name() {
            return receiver_area_name;
        }

        public void setReceiver_area_name(String receiver_area_name) {
            this.receiver_area_name = receiver_area_name;
        }
    }
}
