package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/18 13:43
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MallGetCategoryResponseModel {
    /**
     * result : 1
     * body : [{"goods_category_id":1,"name":"男性专区","ad_pic":"/imageServer/prop/temp/1.jpg","ad_url":"http://www.meipai.com/?f=MzYwbmF2aWZz","ad_goods_id":"0","sort":1,"type":1,"ad_club_pic":"/imageServer/prop/temp/1.jpg"},{"goods_category_id":2,"name":"女性专区","ad_pic":"/imageServer/prop/temp/1.jpg","ad_url":"","ad_goods_id":"13","sort":2,"type":2,"ad_club_pic":"/imageServer/prop/temp/1.jpg"},{"goods_category_id":3,"name":"调情助兴","ad_pic":"/imageServer/prop/temp/400.jpg","ad_url":"","ad_goods_id":"5","sort":3,"type":3,"ad_club_pic":"/imageServer/prop/temp/400.jpg"},{"goods_category_id":4,"name":"延时保健","ad_pic":"/imageServer/prop/temp/400.jpg","ad_url":"","ad_goods_id":"25","sort":4,"type":4,"ad_club_pic":"/imageServer/prop/temp/400.jpg"},{"goods_category_id":5,"name":"扩展+1","ad_pic":"/imageServer/prop/temp/3.jpg","ad_url":"","ad_goods_id":"42","sort":5,"type":5,"ad_club_pic":"/imageServer/prop/temp/3.jpg"},{"goods_category_id":6,"name":"扩展+2","ad_pic":"","ad_url":"","sort":6,"ad_club_pic":""},{"goods_category_id":69,"name":"酒店速递","ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","ad_goods_id":"0","sort":99,"type":1,"category_type":1,"description":"十分钟必达","img_bg_url":"/imageServer/prop/mall/hotel_mall_bg.jpg","img_url":"/imageServer/prop/mall/hotel_mall.png","web_url":"http://192.168.1.134:7000/page/#/home/hotel/","ad_club_pic":"/imageServer/prop/temp/400.jpg"},{"goods_category_id":71,"name":"双倍金币","ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","ad_goods_id":"0","sort":100,"type":1,"category_type":0,"description":"双倍金币","ad_club_pic":"/imageServer/prop/temp/401.jpg"}]
     */

    private String result;
    /**
     * goods_category_id : 1
     * name : 男性专区
     * ad_pic : /imageServer/prop/temp/1.jpg
     * ad_url : http://www.meipai.com/?f=MzYwbmF2aWZz
     * ad_goods_id : 0
     * sort : 1
     * type : 1
     * ad_club_pic : /imageServer/prop/temp/1.jpg
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
        private int goods_category_id;
        private String name;
        private String ad_pic;
        private String ad_url;
        private String ad_goods_id;
        private int sort;
        private int type;
        private String ad_club_pic;

        public int getGoods_category_id() {
            return goods_category_id;
        }

        public void setGoods_category_id(int goods_category_id) {
            this.goods_category_id = goods_category_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAd_pic() {
            return ad_pic;
        }

        public void setAd_pic(String ad_pic) {
            this.ad_pic = ad_pic;
        }

        public String getAd_url() {
            return ad_url;
        }

        public void setAd_url(String ad_url) {
            this.ad_url = ad_url;
        }

        public String getAd_goods_id() {
            return ad_goods_id;
        }

        public void setAd_goods_id(String ad_goods_id) {
            this.ad_goods_id = ad_goods_id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAd_club_pic() {
            return ad_club_pic;
        }

        public void setAd_club_pic(String ad_club_pic) {
            this.ad_club_pic = ad_club_pic;
        }
    }
}
