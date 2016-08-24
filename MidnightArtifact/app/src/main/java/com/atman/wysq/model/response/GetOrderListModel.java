package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/25 10:36
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetOrderListModel {
    /**
     * result : 1
     * body : [{"order_id":"2016072100000012","user_id":450383738,"type":1,"express_fee":0,"total_price":85,"create_time":1469099227000,"update_time":1469099227000,"comment_type":0,"order_type":0,"address_id":2037,"items":[{"goods_id":670,"goods_title":"INS最终幻想2 男用电动自慰器飞机杯","goods_pic":"/imageServer/DE5Cc3efcace833d49e4887ab2b3c2c67fea.jpg","amount":1,"price":85,"total_price":85}]},{"order_id":"2016072100000011","user_id":450383738,"type":1,"express_fee":0,"total_price":138,"create_time":1469099172000,"update_time":1469099172000,"comment_type":0,"order_type":0,"address_id":2037,"items":[{"goods_id":669,"goods_title":"英国YouCups优客仕浪语吟声二代免提电动 发声飞机杯男用自慰器","goods_pic":"/imageServer/8CA56213deba359b4753aeeb10ad7e07099e.jpg","amount":1,"price":138,"total_price":138}]},{"order_id":"2016072100000010","user_id":450383738,"type":1,"express_fee":0,"total_price":138,"create_time":1469098808000,"update_time":1469098808000,"comment_type":0,"order_type":0,"address_id":2037,"items":[{"goods_id":669,"goods_title":"英国YouCups优客仕浪语吟声二代免提电动 发声飞机杯男用自慰器","goods_pic":"/imageServer/8CA56213deba359b4753aeeb10ad7e07099e.jpg","amount":1,"price":138,"total_price":138}]},{"order_id":"2016072100000009","user_id":450383738,"type":1,"express_fee":0,"total_price":138,"create_time":1469098800000,"update_time":1469098800000,"comment_type":0,"order_type":0,"address_id":2037,"items":[{"goods_id":669,"goods_title":"英国YouCups优客仕浪语吟声二代免提电动 发声飞机杯男用自慰器","goods_pic":"/imageServer/8CA56213deba359b4753aeeb10ad7e07099e.jpg","amount":1,"price":138,"total_price":138}]},{"order_id":"2016072100000008","user_id":450383738,"type":1,"express_fee":0,"total_price":138,"create_time":1469098679000,"update_time":1469098679000,"comment_type":0,"order_type":0,"address_id":2037,"items":[{"goods_id":669,"goods_title":"英国YouCups优客仕浪语吟声二代免提电动 发声飞机杯男用自慰器","goods_pic":"/imageServer/8CA56213deba359b4753aeeb10ad7e07099e.jpg","amount":1,"price":138,"total_price":138}]},{"order_id":"2016072100000007","user_id":450383738,"type":1,"express_fee":0,"total_price":138,"create_time":1469097933000,"update_time":1469097933000,"comment_type":0,"order_type":0,"address_id":2037,"items":[{"goods_id":669,"goods_title":"英国YouCups优客仕浪语吟声二代免提电动 发声飞机杯男用自慰器","goods_pic":"/imageServer/8CA56213deba359b4753aeeb10ad7e07099e.jpg","amount":1,"price":138,"total_price":138}]}]
     */

    private String result;
    /**
     * order_id : 2016072100000012
     * user_id : 450383738
     * type : 1
     * express_fee : 0
     * total_price : 85
     * create_time : 1469099227000
     * update_time : 1469099227000
     * comment_type : 0
     * order_type : 0
     * address_id : 2037
     * items : [{"goods_id":670,"goods_title":"INS最终幻想2 男用电动自慰器飞机杯","goods_pic":"/imageServer/DE5Cc3efcace833d49e4887ab2b3c2c67fea.jpg","amount":1,"price":85,"total_price":85}]
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
        /**
         * goods_id : 670
         * goods_title : INS最终幻想2 男用电动自慰器飞机杯
         * goods_pic : /imageServer/DE5Cc3efcace833d49e4887ab2b3c2c67fea.jpg
         * amount : 1
         * price : 85
         * total_price : 85
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

        public List<ItemsEntity> getItems() {
            return items;
        }

        public void setItems(List<ItemsEntity> items) {
            this.items = items;
        }

        public static class ItemsEntity {
            private int goods_id;
            private String goods_title;
            private String goods_pic;
            private int amount;
            private float price;
            private float total_price;

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

            public float getPrice() {
                return price;
            }

            public void setPrice(float price) {
                this.price = price;
            }

            public float getTotal_price() {
                return total_price;
            }

            public void setTotal_price(float total_price) {
                this.total_price = total_price;
            }
        }
    }
}
