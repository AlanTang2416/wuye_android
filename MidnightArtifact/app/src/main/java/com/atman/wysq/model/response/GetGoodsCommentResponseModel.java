package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/21 09:58
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetGoodsCommentResponseModel {
    /**
     * result : 1
     * body : [{"goods_comment_id":6,"goods_id":44,"user_id":450052082,"content":"还可以","create_time":1429627108000,"user_name":"夜友\u2014\u201454595","icon":"/imageServer/9C66male.png","sex":"M","userLevel":2,"like_count":9,"isLike":0,"total":1}]
     */

    private String result;
    /**
     * goods_comment_id : 6
     * goods_id : 44
     * user_id : 450052082
     * content : 还可以
     * create_time : 1429627108000
     * user_name : 夜友——54595
     * icon : /imageServer/9C66male.png
     * sex : M
     * userLevel : 2
     * like_count : 9
     * isLike : 0
     * total : 1
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
        private int goods_comment_id;
        private int goods_id;
        private int user_id;
        private String content;
        private long create_time;
        private String user_name;
        private String icon;
        private String sex;
        private int userLevel;
        private int like_count;
        private int isLike;
        private int total;

        public int getGoods_comment_id() {
            return goods_comment_id;
        }

        public void setGoods_comment_id(int goods_comment_id) {
            this.goods_comment_id = goods_comment_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
