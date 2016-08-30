package com.atman.wysq.model.response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/25 15:48
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetOrderDetailModel {
    /**
     * result : 1
     * body : {"order_id":"2016062700000007","user_id":450088716,"type":1,"express_fee":0,"total_price":1,"create_time":1467030665000,"update_time":1467030665000,"comment_type":0,"order_type":1,"address_id":81}
     */

    private String result;
    /**
     * order_id : 2016062700000007
     * user_id : 450088716
     * type : 1
     * express_fee : 0
     * total_price : 1
     * create_time : 1467030665000
     * update_time : 1467030665000
     * comment_type : 0
     * order_type : 1
     * address_id : 81
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
        private String order_id;
        private long user_id;
        private int type;
        private float express_fee;
        private float total_price;
        private long create_time;
        private long update_time;
        private int comment_type;
        private int order_type;
        private int address_id;
        private String transport_company;

        public String getTransport_id() {
            return transport_id;
        }

        public void setTransport_id(String transport_id) {
            this.transport_id = transport_id;
        }

        public String getTransport_company() {
            return transport_company;
        }

        public void setTransport_company(String transport_company) {
            this.transport_company = transport_company;
        }

        private String transport_id;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public float getExpress_fee() {
            return express_fee;
        }

        public void setExpress_fee(float express_fee) {
            this.express_fee = express_fee;
        }

        public float getTotal_price() {
            return total_price;
        }

        public void setTotal_price(float total_price) {
            this.total_price = total_price;
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

        public int getComment_type() {
            return comment_type;
        }

        public void setComment_type(int comment_type) {
            this.comment_type = comment_type;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }
    }
}
