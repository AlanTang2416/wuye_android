package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/13 15:29
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ConfigModel  {
    /**
     * api : http://api.5ys7.com
     * wuye : https://itunes.apple.com/us/app/wu-ye-shen-qi/id948764399?l=zh&ls=1&mt=8
     * img : http://www.5ys7.com
     * shop : [{"description":"","title":"12金币","price":"¥6.00","name":"com.atman.wysq.coin.1"},{"description":"+送5%","title":"25金币","price":"¥12.00","name":"com.atman.wysq.coin.2"},{"description":"+送10%","title":"66金币","price":"¥30.00","name":"com.atman.wysq.coin.3"},{"description":"+送15%","title":"156金币","price":"¥68.00","name":"com.atman.wysq.coin.4"},{"description":"+送20%","title":"403金币","price":"¥168.00","name":"com.atman.wysq.coin.5"},{"description":"+送25%","title":"820金币","price":"¥328.00","name":"com.atman.wysq.coin.6"},{"description":"+送30%","title":"1685金币","price":"¥648.00","name":"com.atman.wysq.coin.7"}]
     * kUrlSchemeStatus : 1
     * kShopBackImgUrl : http://api.5ys7.com:8001/imageServer/prop/temp/shop_back_img.png
     * kStartUpAdUrl : http://api.5ys7.com:8001/imageServer/prop/temp/640-960-texttqyb.png
     * kStartUPAdBgUrl : http://api.5ys7.com:8001/imageServer/prop/temp/640-960-bgtqyb.png
     * kLiveRoomMainUrl : http://api.5ys7.com:8001/imageServer/prop/temp/live_main_imgdoor3.png
     * kLiveRoomFlag : 0
     * kUserSexSwich : 0
     * kStartUpAdGoodsId : 275119
     * kStartUpAdType : 1
     * kStartUpAdWebUrl : http://t.cn/RqkTYqQ
     * kGoldenOrderShow : 1
     */

    private String api;
    private String wuye;
    private String wuyeandroid;
    private String img;
    private String kUrlSchemeStatus;
    private String kShopBackImgUrl;
    private String kStartUpAdUrl;
    private String kStartUPAdBgUrl;
    private String kLiveRoomMainUrl;
    private String kLiveRoomFlag;
    private String kUserSexSwich;
    private String kStartUpAdGoodsId;
    private String kStartUpAdType;
    private String kStartUpAdWebUrl;
    private String kGoldenOrderShow;
    private String kPrivateChatCost;

    public String getkPrivateChatCost() {
        return kPrivateChatCost;
    }

    public void setkPrivateChatCost(String kPrivateChatCost) {
        this.kPrivateChatCost = kPrivateChatCost;
    }

    public String getWuyeandroid() {
        return wuyeandroid;
    }

    public void setWuyeandroid(String wuyeandroid) {
        this.wuyeandroid = wuyeandroid;
    }

    /**
     * description :
     * title : 12金币
     * price : ¥6.00
     * name : com.atman.wysq.coin.1
     */

    private List<ShopEntity> shop;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getWuye() {
        return wuye;
    }

    public void setWuye(String wuye) {
        this.wuye = wuye;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKUrlSchemeStatus() {
        return kUrlSchemeStatus;
    }

    public void setKUrlSchemeStatus(String kUrlSchemeStatus) {
        this.kUrlSchemeStatus = kUrlSchemeStatus;
    }

    public String getKShopBackImgUrl() {
        return kShopBackImgUrl;
    }

    public void setKShopBackImgUrl(String kShopBackImgUrl) {
        this.kShopBackImgUrl = kShopBackImgUrl;
    }

    public String getKStartUpAdUrl() {
        return kStartUpAdUrl;
    }

    public void setKStartUpAdUrl(String kStartUpAdUrl) {
        this.kStartUpAdUrl = kStartUpAdUrl;
    }

    public String getKStartUPAdBgUrl() {
        return kStartUPAdBgUrl;
    }

    public void setKStartUPAdBgUrl(String kStartUPAdBgUrl) {
        this.kStartUPAdBgUrl = kStartUPAdBgUrl;
    }

    public String getKLiveRoomMainUrl() {
        return kLiveRoomMainUrl;
    }

    public void setKLiveRoomMainUrl(String kLiveRoomMainUrl) {
        this.kLiveRoomMainUrl = kLiveRoomMainUrl;
    }

    public String getKLiveRoomFlag() {
        return kLiveRoomFlag;
    }

    public void setKLiveRoomFlag(String kLiveRoomFlag) {
        this.kLiveRoomFlag = kLiveRoomFlag;
    }

    public String getKUserSexSwich() {
        return kUserSexSwich;
    }

    public void setKUserSexSwich(String kUserSexSwich) {
        this.kUserSexSwich = kUserSexSwich;
    }

    public String getKStartUpAdGoodsId() {
        return kStartUpAdGoodsId;
    }

    public void setKStartUpAdGoodsId(String kStartUpAdGoodsId) {
        this.kStartUpAdGoodsId = kStartUpAdGoodsId;
    }

    public String getKStartUpAdType() {
        return kStartUpAdType;
    }

    public void setKStartUpAdType(String kStartUpAdType) {
        this.kStartUpAdType = kStartUpAdType;
    }

    public String getKStartUpAdWebUrl() {
        return kStartUpAdWebUrl;
    }

    public void setKStartUpAdWebUrl(String kStartUpAdWebUrl) {
        this.kStartUpAdWebUrl = kStartUpAdWebUrl;
    }

    public String getKGoldenOrderShow() {
        return kGoldenOrderShow;
    }

    public void setKGoldenOrderShow(String kGoldenOrderShow) {
        this.kGoldenOrderShow = kGoldenOrderShow;
    }

    public List<ShopEntity> getShop() {
        return shop;
    }

    public void setShop(List<ShopEntity> shop) {
        this.shop = shop;
    }

    public static class ShopEntity {
        private String description;
        private String title;
        private String price;
        private String name;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
