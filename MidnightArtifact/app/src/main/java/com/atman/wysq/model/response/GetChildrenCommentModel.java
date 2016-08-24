package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/3 10:45
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetChildrenCommentModel {
    /**
     * result : 1
     * body : [{"blog_comment_id":273956,"blog_id":185594,"user_id":450000527,"parent_id":273800,"content":"jigo","status":1,"create_time":1470029462000,"user_name":"夜友-11678","icon":"/imageServer/9C66female.png","sex":"F","verify_status":0,"userLevel":1,"unread_flag":0,"achieve_level_pic":"/propServer/achievement/new/color_dj_1.png","vip_level":0},{"blog_comment_id":273957,"blog_id":185594,"user_id":450000527,"parent_id":273800,"content":"wageng","status":1,"create_time":1470029465000,"user_name":"夜友-11678","icon":"/imageServer/9C66female.png","sex":"F","verify_status":0,"userLevel":1,"unread_flag":0,"achieve_level_pic":"/propServer/achievement/new/color_dj_1.png","vip_level":0},{"blog_comment_id":274063,"blog_id":185594,"user_id":450000175,"parent_id":273800,"content":"看看看看看","status":1,"create_time":1470107802000,"user_name":"潇洒gg带你飞","icon":"/imageServer/D58B8da51cc3b221416f969b3f2cd517d500.jpg","sex":"F","verify_status":1,"userLevel":6,"unread_flag":0,"achieve_level_pic":"/propServer/achievement/new/color_dj_3.png","achieve_charm_pic":"/propServer/achievement/new/corlo_ml_2.png","achieve_gold_pic":"/propServer/achievement/new/color_cf_5.png","vip_level":0},{"blog_comment_id":274064,"blog_id":185594,"user_id":450000175,"parent_id":273800,"content":"我们是有好运气","status":1,"create_time":1470107850000,"user_name":"潇洒gg带你飞","icon":"/imageServer/D58B8da51cc3b221416f969b3f2cd517d500.jpg","sex":"F","verify_status":1,"userLevel":6,"unread_flag":0,"achieve_level_pic":"/propServer/achievement/new/color_dj_3.png","achieve_charm_pic":"/propServer/achievement/new/corlo_ml_2.png","achieve_gold_pic":"/propServer/achievement/new/color_cf_5.png","vip_level":0}]
     */

    private String result;
    /**
     * blog_comment_id : 273956
     * blog_id : 185594
     * user_id : 450000527
     * parent_id : 273800
     * content : jigo
     * status : 1
     * create_time : 1470029462000
     * user_name : 夜友-11678
     * icon : /imageServer/9C66female.png
     * sex : F
     * verify_status : 0
     * userLevel : 1
     * unread_flag : 0
     * achieve_level_pic : /propServer/achievement/new/color_dj_1.png
     * vip_level : 0
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
        private int blog_comment_id;
        private int blog_id;
        private long user_id;
        private int parent_id;
        private String content;
        private int status;
        private long create_time;
        private String user_name;
        private String icon;
        private String sex;
        private int verify_status;
        private int userLevel;
        private int unread_flag;
        private String achieve_level_pic;
        private int vip_level;

        public int getBlog_comment_id() {
            return blog_comment_id;
        }

        public void setBlog_comment_id(int blog_comment_id) {
            this.blog_comment_id = blog_comment_id;
        }

        public int getBlog_id() {
            return blog_id;
        }

        public void setBlog_id(int blog_id) {
            this.blog_id = blog_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public int getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(int verify_status) {
            this.verify_status = verify_status;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public int getUnread_flag() {
            return unread_flag;
        }

        public void setUnread_flag(int unread_flag) {
            this.unread_flag = unread_flag;
        }

        public String getAchieve_level_pic() {
            return achieve_level_pic;
        }

        public void setAchieve_level_pic(String achieve_level_pic) {
            this.achieve_level_pic = achieve_level_pic;
        }

        public int getVip_level() {
            return vip_level;
        }

        public void setVip_level(int vip_level) {
            this.vip_level = vip_level;
        }
    }
}
