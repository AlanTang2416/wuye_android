package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/18 11:48
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MallTopResponseModel {
    /**
     * result : 1
     * body : [{"ad_info_id":10,"name":"帖子：飘啊飘","description":"tiezi","ad_pic":"/imageServer/D38B9f9fd1d4cb3b4a4bb78a50ae0308cb60.jpg","ad_url":"http://weibo.com/topgirls8","ad_goods_id":184901,"sort":1,"series":1,"type":6,"create_time":1434007527000,"update_time":1441937162000},{"ad_info_id":8,"name":"专辑：性爱吉尼斯","description":"456","ad_pic":"/imageServer/prop/temp/3.jpg","ad_url":"http://www.baidu.com","ad_goods_id":13,"sort":2,"series":1,"type":4,"create_time":1433468905000,"update_time":1443585032000},{"ad_info_id":3,"name":"商品分类：雅霜","description":"1","ad_pic":"/imageServer/prop/temp/3.jpg","ad_url":"","ad_goods_id":23,"sort":3,"series":1,"type":5,"create_time":1410427489000,"update_time":1441937174000},{"ad_info_id":4,"name":"任务","description":"谁发的","ad_pic":"/imageServer/5D0622bfbfd4d96a461191e5d3a268add795.jpg","ad_url":"http://web.4399.com/stat/game.php?target=qs&server_id=S3057","ad_goods_id":5,"sort":4,"series":1,"type":3,"create_time":1410427489000,"update_time":1441937178000},{"ad_info_id":18,"name":"URL","description":"链接","ad_pic":"/imageServer/prop/temp/a7.jpg","ad_url":"http://www.baidu.com","sort":5,"series":1,"type":1,"create_time":1441936502000,"update_time":1441937745000}]
     */

    private String result;
    /**
     * ad_info_id : 10
     * name : 帖子：飘啊飘
     * description : tiezi
     * ad_pic : /imageServer/D38B9f9fd1d4cb3b4a4bb78a50ae0308cb60.jpg
     * ad_url : http://weibo.com/topgirls8
     * ad_goods_id : 184901
     * sort : 1
     * series : 1
     * type : 6
     * create_time : 1434007527000
     * update_time : 1441937162000
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
        private int ad_info_id;
        private String name;
        private String description;
        private String ad_pic;
        private String ad_url;
        private int ad_goods_id;
        private int sort;
        private int series;
        private int type;
        private long create_time;
        private long update_time;

        public int getAd_info_id() {
            return ad_info_id;
        }

        public void setAd_info_id(int ad_info_id) {
            this.ad_info_id = ad_info_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public int getAd_goods_id() {
            return ad_goods_id;
        }

        public void setAd_goods_id(int ad_goods_id) {
            this.ad_goods_id = ad_goods_id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getSeries() {
            return series;
        }

        public void setSeries(int series) {
            this.series = series;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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
    }
}
