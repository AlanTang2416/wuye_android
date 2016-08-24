package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/18 16:20
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MallGetTwoCategoryResponseModel {
    /**
     * result : 1
     * body : [{"goods_category_id":12,"name":"火热销售","parient_id":3,"sort":1,"leafCategorieList":[{"goods_category_id":96,"name":"润滑剂","parient_id":12,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":1,"ad_club_pic":"/imageServer/prop/adinfo/c1.png"},{"goods_category_id":95,"name":"女用催情","parient_id":12,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":2,"ad_club_pic":"/imageServer/prop/adinfo/c2.png"},{"goods_category_id":90,"name":"助兴套环","parient_id":12,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":3,"ad_club_pic":"/imageServer/prop/adinfo/c3.png"},{"goods_category_id":78,"name":"延时套环","parient_id":12,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":4,"ad_club_pic":"/imageServer/prop/adinfo/c4.png"},{"goods_category_id":97,"name":"男女共振","parient_id":12,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":5,"ad_club_pic":"/imageServer/prop/adinfo/c5.png"}]},{"goods_category_id":13,"name":"导购推荐","parient_id":3,"sort":2,"leafCategorieList":[{"goods_category_id":96,"name":"润滑剂","parient_id":12,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":1,"ad_club_pic":"/imageServer/prop/adinfo/c1.png"}]}]
     */

    private String result;
    /**
     * goods_category_id : 12
     * name : 火热销售
     * parient_id : 3
     * sort : 1
     * leafCategorieList : [{"goods_category_id":96,"name":"润滑剂","parient_id":12,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":1,"ad_club_pic":"/imageServer/prop/adinfo/c1.png"},{"goods_category_id":95,"name":"女用催情","parient_id":12,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":2,"ad_club_pic":"/imageServer/prop/adinfo/c2.png"},{"goods_category_id":90,"name":"助兴套环","parient_id":12,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":3,"ad_club_pic":"/imageServer/prop/adinfo/c3.png"},{"goods_category_id":78,"name":"延时套环","parient_id":12,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":4,"ad_club_pic":"/imageServer/prop/adinfo/c4.png"},{"goods_category_id":97,"name":"男女共振","parient_id":12,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":5,"ad_club_pic":"/imageServer/prop/adinfo/c5.png"}]
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
        private int parient_id;
        private int sort;
        /**
         * goods_category_id : 96
         * name : 润滑剂
         * parient_id : 12
         * ad_pic : /imageServer/prop/adinfo/b4.png
         * ad_url :
         * sort : 1
         * ad_club_pic : /imageServer/prop/adinfo/c1.png
         */

        private List<LeafCategorieListEntity> leafCategorieList;

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

        public int getParient_id() {
            return parient_id;
        }

        public void setParient_id(int parient_id) {
            this.parient_id = parient_id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public List<LeafCategorieListEntity> getLeafCategorieList() {
            return leafCategorieList;
        }

        public void setLeafCategorieList(List<LeafCategorieListEntity> leafCategorieList) {
            this.leafCategorieList = leafCategorieList;
        }

        public static class LeafCategorieListEntity {
            private int goods_category_id;
            private String name;
            private int parient_id;
            private String ad_pic;
            private String ad_url;
            private int sort;
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

            public int getParient_id() {
                return parient_id;
            }

            public void setParient_id(int parient_id) {
                this.parient_id = parient_id;
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

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getAd_club_pic() {
                return ad_club_pic;
            }

            public void setAd_club_pic(String ad_club_pic) {
                this.ad_club_pic = ad_club_pic;
            }
        }
    }
}
