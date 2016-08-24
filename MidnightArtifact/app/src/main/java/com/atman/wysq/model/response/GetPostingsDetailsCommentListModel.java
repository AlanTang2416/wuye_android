package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/29 14:32
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetPostingsDetailsCommentListModel {

    private String result;
    /**
     * blog_comment_id : 273717
     * blog_id : 185573
     * user_id : 450000181
     * content : 突击
     * status : 1
     * create_time : 1469625772000
     * user_name : 夜友——11328
     * icon : /imageServer/A102b5c5352b2ec24836914baad372c79e54.jpg
     * sex : F
     * verify_status : 1
     * userLevel : 6
     * unread_flag : 1
     * achieve_level_pic : /propServer/achievement/new/color_dj_3.png
     * achieve_gold_pic : /propServer/achievement/new/color_cf_5.png
     * blogCommentList : [{"blog_comment_id":273718,"blog_id":185573,"user_id":450000626,"parent_id":273717,"content":"u 咯哦","status":1,"create_time":1469625788000,"user_name":"西红柿","icon":"/imageServer/9C66male.png","sex":"M","verify_status":0,"unread_flag":0,"vip_level":0}]
     * vip_level : 0
     * like_count : 0
     * isLike : 0
     * total : 6
     * sub_count : 1
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
        private String achieve_gold_pic;
        private int vip_level;
        private int like_count;
        private int isLike;
        private int total;
        private int sub_count;
        /**
         * blog_comment_id : 273718
         * blog_id : 185573
         * user_id : 450000626
         * parent_id : 273717
         * content : u 咯哦
         * status : 1
         * create_time : 1469625788000
         * user_name : 西红柿
         * icon : /imageServer/9C66male.png
         * sex : M
         * verify_status : 0
         * unread_flag : 0
         * vip_level : 0
         */

        private List<BlogCommentListEntity> blogCommentList;

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

        public String getAchieve_gold_pic() {
            return achieve_gold_pic;
        }

        public void setAchieve_gold_pic(String achieve_gold_pic) {
            this.achieve_gold_pic = achieve_gold_pic;
        }

        public int getVip_level() {
            return vip_level;
        }

        public void setVip_level(int vip_level) {
            this.vip_level = vip_level;
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

        public int getSub_count() {
            return sub_count;
        }

        public void setSub_count(int sub_count) {
            this.sub_count = sub_count;
        }

        public List<BlogCommentListEntity> getBlogCommentList() {
            return blogCommentList;
        }

        public void setBlogCommentList(List<BlogCommentListEntity> blogCommentList) {
            this.blogCommentList = blogCommentList;
        }

        public static class BlogCommentListEntity {
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
            private int unread_flag;
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

            public int getUnread_flag() {
                return unread_flag;
            }

            public void setUnread_flag(int unread_flag) {
                this.unread_flag = unread_flag;
            }

            public int getVip_level() {
                return vip_level;
            }

            public void setVip_level(int vip_level) {
                this.vip_level = vip_level;
            }
        }
    }
}
