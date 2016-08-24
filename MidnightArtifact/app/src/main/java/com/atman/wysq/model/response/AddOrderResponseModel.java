package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/21 18:43
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class AddOrderResponseModel {
    /**
     * result : 1
     * body : {"order_id":"2016072100000026","user_id":450000392,"type":1,"express_fee":1,"total_price":1.05,"comment_type":0,"order_type":1,"address_id":743,"items":[{"goods_id":71,"goods_title":"打飞机4","goods_pic":"/imageServer/prop/adinfo/a2.png","amount":1,"price":0.05,"total_price":0.05}],"orderLogistical":{"receiver_name":"PS","receiver_address":"可口可乐咯哦","receiver_area":"310115","receiver_phone":"13952546521","receiver_area_name":"上海 上海市 浦东新区 "}}
     */

    private String result;
    /**
     * order_id : 2016072100000026
     * user_id : 450000392
     * type : 1
     * express_fee : 1
     * total_price : 1.05
     * comment_type : 0
     * order_type : 1
     * address_id : 743
     * items : [{"goods_id":71,"goods_title":"打飞机4","goods_pic":"/imageServer/prop/adinfo/a2.png","amount":1,"price":0.05,"total_price":0.05}]
     * orderLogistical : {"receiver_name":"PS","receiver_address":"可口可乐咯哦","receiver_area":"310115","receiver_phone":"13952546521","receiver_area_name":"上海 上海市 浦东新区 "}
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
        private int express_fee;
        private double total_price;
        private int comment_type;
        private int order_type;
        private int address_id;
        /**
         * receiver_name : PS
         * receiver_address : 可口可乐咯哦
         * receiver_area : 310115
         * receiver_phone : 13952546521
         * receiver_area_name : 上海 上海市 浦东新区
         */

        private OrderLogisticalEntity orderLogistical;
        /**
         * goods_id : 71
         * goods_title : 打飞机4
         * goods_pic : /imageServer/prop/adinfo/a2.png
         * amount : 1
         * price : 0.05
         * total_price : 0.05
         */

        private List<ItemsEntity> items;

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

        public int getExpress_fee() {
            return express_fee;
        }

        public void setExpress_fee(int express_fee) {
            this.express_fee = express_fee;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
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

        public OrderLogisticalEntity getOrderLogistical() {
            return orderLogistical;
        }

        public void setOrderLogistical(OrderLogisticalEntity orderLogistical) {
            this.orderLogistical = orderLogistical;
        }

        public List<ItemsEntity> getItems() {
            return items;
        }

        public void setItems(List<ItemsEntity> items) {
            this.items = items;
        }

        public static class OrderLogisticalEntity {
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

        public static class ItemsEntity {
            private int goods_id;
            private String goods_title;
            private String goods_pic;
            private int amount;
            private double price;
            private double total_price;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_title() {
                return goods_title;
            }

            public void setGoods_title(String goods_title) {
                this.goods_title = goods_title;
            }

            public String getGoods_pic() {
                return goods_pic;
            }

            public void setGoods_pic(String goods_pic) {
                this.goods_pic = goods_pic;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getTotal_price() {
                return total_price;
            }

            public void setTotal_price(double total_price) {
                this.total_price = total_price;
            }
        }
    }
}
