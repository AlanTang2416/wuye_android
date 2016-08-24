package com.atman.wysq.model.response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/15 16:52
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetAddressByOrderIdModel {
    /**
     * result : 1
     * body : {"receiver_name":"哈弗","receiver_address":" 干哈呀","receiver_area":"110105","receiver_phone":"13948538348","receiver_area_name":"北京 北京市 朝阳区 "}
     */

    private String result;
    /**
     * receiver_name : 哈弗
     * receiver_address :  干哈呀
     * receiver_area : 110105
     * receiver_phone : 13948538348
     * receiver_area_name : 北京 北京市 朝阳区
     */

    private BodyEntity body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BodyEntity getBody() {
        return body;
    }

    public void setBody(BodyEntity body) {
        this.body = body;
    }

    public static class BodyEntity {
        private String receiver_name;
        private String receiver_address;
        private String receiver_area;
        private String receiver_phone;
        private String receiver_area_name;

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

        public String getReceiver_area_name() {
            return receiver_area_name;
        }

        public void setReceiver_area_name(String receiver_area_name) {
            this.receiver_area_name = receiver_area_name;
        }
    }
}
