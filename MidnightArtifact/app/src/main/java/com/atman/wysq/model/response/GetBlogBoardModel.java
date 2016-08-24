package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/27 11:14
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetBlogBoardModel {
    /**
     * result : 1
     * body : [{"id":1,"title":"午夜活动专区","description":"海量金币,精美情趣用品送不停","pic_url":"IMAGE/88/1A/1.png","back_pic":"http://api.5ys7.com:8001/imageServer/prop/temp/mms0.png","forum_integral":"5","can_post":"001","order_index":1,"status":2,"blogCount":1021,"commentCount":2241},{"id":2,"title":"谈谈情说说爱","description":"聊妹撩汉自助区,这里什么都可以聊","pic_url":"IMAGE/88/1A/2.png","back_pic":"http://api.5ys7.com:8001/imageServer/prop/temp/mms1.png","forum_integral":"2","can_post":"010","order_index":2,"status":2,"blogCount":105,"commentCount":352},{"id":3,"title":"我的羞羞日记","description":"记录害羞的事,我们真的不要小黄文","pic_url":"IMAGE/88/1A/3.png","back_pic":"http://api.5ys7.com:8001/imageServer/prop/temp/mms2.png","forum_integral":"3","can_post":"100","order_index":3,"status":2,"blogCount":29,"commentCount":213},{"id":4,"title":"啪啪啪完全指南","description":"人人都是老司机,解答啪啪啪的困惑","pic_url":"IMAGE/88/1A/4.png","back_pic":"http://api.5ys7.com:8001/imageServer/prop/temp/mms3.png","forum_integral":"1","can_post":"101","order_index":4,"status":2,"blogCount":57,"commentCount":222},{"id":5,"title":"树洞里的小秘密","description":"每个人都需要一个树洞,倾吐心事,烦恼","pic_url":"IMAGE/88/1A/5.png","back_pic":"http://api.5ys7.com:8001/imageServer/prop/temp/mms4.png","forum_integral":"1","can_post":"110","order_index":5,"status":2,"blogCount":29,"commentCount":73},{"id":6,"title":"晒单分享专区","description":"一元购,心愿墙,购物,活动奖励晒单","pic_url":"IMAGE/88/1A/6.png","back_pic":"http://api.5ys7.com:8001/imageServer/prop/temp/mms0.png","forum_integral":"1","can_post":"011","order_index":6,"status":2,"blogCount":6,"commentCount":9},{"id":7,"title":"嘿嘿腿-111","description":"。。。。。。。。。。。。。","pic_url":"IMAGE/88/1A/qwyq.png","back_pic":"http://api.5ys7.com:8001/imageServer/prop/temp/mms1.png","forum_integral":"1","can_post":"111","order_index":7,"status":2,"blogCount":8,"commentCount":24},{"id":8,"title":"孤单单-000","description":"孤单单的人，孤单单的鬼，孤单单的菇凉，孤单单的嘴，大把的小费，大把的草票；","pic_url":"IMAGE/88/1A/zszp.png","back_pic":"http://api.5ys7.com:8001/imageServer/prop/temp/mms2.png","forum_integral":"1","can_post":"000","order_index":8,"status":2,"blogCount":4,"commentCount":13},{"id":9,"title":"IMAGE/88/1A/sdfx.png-001","description":"IMAGE/88/1A/sdfx.png","pic_url":"IMAGE/88/1A/sdfx.png","back_pic":"http://api.5ys7.com:8001/imageServer/prop/temp/mms3.png","forum_integral":"1","can_post":"001","order_index":9,"status":2,"blogCount":4,"commentCount":1},{"id":10,"title":"标题党10-100","description":"IMAGE/88/1A/zszp.png","pic_url":"IMAGE/88/1A/zszp.png","back_pic":"http://api.5ys7.com:8001/imageServer/prop/temp/mms4.png","forum_integral":"1","can_post":"100","order_index":10,"status":2,"blogCount":6,"commentCount":21}]
     */

    private String result;
    /**
     * id : 1
     * title : 午夜活动专区
     * description : 海量金币,精美情趣用品送不停
     * pic_url : IMAGE/88/1A/1.png
     * back_pic : http://api.5ys7.com:8001/imageServer/prop/temp/mms0.png
     * forum_integral : 5
     * can_post : 001
     * order_index : 1
     * status : 2
     * blogCount : 1021
     * commentCount : 2241
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
        private int id;
        private String title;
        private String description;
        private String pic_url;
        private String back_pic;
        private String forum_integral;
        private String can_post;
        private int order_index;
        private int status;
        private int blogCount;
        private int commentCount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getBack_pic() {
            return back_pic;
        }

        public void setBack_pic(String back_pic) {
            this.back_pic = back_pic;
        }

        public String getForum_integral() {
            return forum_integral;
        }

        public void setForum_integral(String forum_integral) {
            this.forum_integral = forum_integral;
        }

        public String getCan_post() {
            return can_post;
        }

        public void setCan_post(String can_post) {
            this.can_post = can_post;
        }

        public int getOrder_index() {
            return order_index;
        }

        public void setOrder_index(int order_index) {
            this.order_index = order_index;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getBlogCount() {
            return blogCount;
        }

        public void setBlogCount(int blogCount) {
            this.blogCount = blogCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }
    }
}
