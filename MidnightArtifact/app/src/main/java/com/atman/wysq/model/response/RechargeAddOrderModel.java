package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/14 13:38
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class RechargeAddOrderModel {
    /**
     * result : 1
     * body : {"order_id":"2016071400000043","user_id":100003813,"type":1,"total_price":328,"comment_type":0,"order_type":51,"items":[{"goods_id":1,"goods_title":"820金币","goods_pic":"com.atman.wysq.coin.6","amount":1,"price":328,"total_price":328}]}
     */

    private String result;
    /**
     * order_id : 2016071400000043
     * user_id : 100003813
     * type : 1
     * total_price : 328
     * comment_type : 0
     * order_type : 51
     * items : [{"goods_id":1,"goods_title":"820金币","goods_pic":"com.atman.wysq.coin.6","amount":1,"price":328,"total_price":328}]
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
        private int total_price;
        private int comment_type;
        private int order_type;
        /**
         * goods_id : 1
         * goods_title : 820金币
         * goods_pic : com.atman.wysq.coin.6
         * amount : 1
         * price : 328
         * total_price : 328
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

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
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
            private int price;
            private int total_price;

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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getTotal_price() {
                return total_price;
            }

            public void setTotal_price(int total_price) {
                this.total_price = total_price;
            }
        }
    }
}
