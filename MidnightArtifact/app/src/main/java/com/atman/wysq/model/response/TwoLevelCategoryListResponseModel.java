package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/19 10:40
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class TwoLevelCategoryListResponseModel {
    /**
     * result : 1
     * body : [{"goods_id":145,"title":"牛掰男","pic_img":"/imageServer/12A7201325c606be47db8dce7c95b218e80e.jpg","store":"50","price":"9.90","discount_price":"99.00","sales":121,"special_edition_id":14,"status":2,"key_words":"牛掰","gold_coin":11,"integral":22,"chat_prop_id":13,"update_time":1467596135000,"source":1,"postage":0,"icon":"/imageServer/9C19af62f2a7c46944ebbb1306c31b58bb9b.jpg"},{"goods_id":85,"title":"金币*2商品4","pic_img":"/imageServer/12A7201325c606be47db8dce7c95b218e80e.jpg","store":"222","price":"1.00","discount_price":"10.00","sales":123,"status":2,"key_words":"诱惑","gold_coin":123,"integral":13,"chat_prop_id":12,"update_time":1445313321000,"source":1,"postage":0,"gold_coin_price":100,"charm_price":200,"icon":"/imageServer/35BC020468d05a534bfba399d5ea56c81f83.jpg"},{"goods_id":84,"title":"金币*2商品3","pic_img":"/imageServer/DA7083d44bef0148402c94ee136bb90cd494.jpg","store":"12","price":"131.00","discount_price":"12.00","sales":1,"status":2,"key_words":"诱惑","gold_coin":100,"integral":100,"chat_prop_id":14,"update_time":1445313238000,"source":1,"postage":0,"gold_coin_price":1000,"charm_price":1000,"icon":"/imageServer/5D0622bfbfd4d96a461191e5d3a268add795.jpg"},{"goods_id":52,"title":"金币X2商品2","pic_img":"/imageServer/A0424c50f437b7f44070859f0eb564276531.jpg","store":"100","basic_info":"公分速度","price":"0.1","discount_price":"1.2","sales":100,"goods_category_id":73,"special_edition_id":14,"status":2,"goods_index":2,"key_words":"诱惑","gold_coin":5,"integral":5,"chat_prop_id":13,"update_time":1443170803000,"source":1,"postage":0,"gold_coin_price":1,"charm_price":1,"icon":"/imageServer/9C19af62f2a7c46944ebbb1306c31b58bb9b.jpg"},{"goods_id":51,"title":"金币X2商品1","pic_img":"/imageServer/A0424c50f437b7f44070859f0eb564276531.jpg","store":"73","basic_info":"哇哇哇阿瓦啊","price":"1","discount_price":"2","sales":100,"goods_category_id":73,"special_edition_id":13,"status":2,"goods_index":1,"key_words":"诱惑","gold_coin":50,"integral":50,"chat_prop_id":13,"update_time":1443170824000,"source":1,"postage":0,"gold_coin_price":100,"charm_price":100,"icon":"/imageServer/9C19af62f2a7c46944ebbb1306c31b58bb9b.jpg"}]
     */

    private String result;
    /**
     * goods_id : 145
     * title : 牛掰男
     * pic_img : /imageServer/12A7201325c606be47db8dce7c95b218e80e.jpg
     * store : 50
     * price : 9.90
     * discount_price : 99.00
     * sales : 121
     * special_edition_id : 14
     * status : 2
     * key_words : 牛掰
     * gold_coin : 11
     * integral : 22
     * chat_prop_id : 13
     * update_time : 1467596135000
     * source : 1
     * postage : 0
     * icon : /imageServer/9C19af62f2a7c46944ebbb1306c31b58bb9b.jpg
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
        private int goods_id;
        private String title;
        private String pic_img;
        private String store;
        private String price;
        private String discount_price;
        private int sales;
        private int special_edition_id;
        private int status;
        private String key_words;
        private int gold_coin;
        private int integral;
        private int chat_prop_id;
        private long update_time;
        private int source;
        private int postage;
        private String icon;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic_img() {
            return pic_img;
        }

        public void setPic_img(String pic_img) {
            this.pic_img = pic_img;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscount_price() {
            return discount_price;
        }

        public void setDiscount_price(String discount_price) {
            this.discount_price = discount_price;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public int getSpecial_edition_id() {
            return special_edition_id;
        }

        public void setSpecial_edition_id(int special_edition_id) {
            this.special_edition_id = special_edition_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getKey_words() {
            return key_words;
        }

        public void setKey_words(String key_words) {
            this.key_words = key_words;
        }

        public int getGold_coin() {
            return gold_coin;
        }

        public void setGold_coin(int gold_coin) {
            this.gold_coin = gold_coin;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getChat_prop_id() {
            return chat_prop_id;
        }

        public void setChat_prop_id(int chat_prop_id) {
            this.chat_prop_id = chat_prop_id;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getPostage() {
            return postage;
        }

        public void setPostage(int postage) {
            this.postage = postage;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
