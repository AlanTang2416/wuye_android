package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/8 11:05
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetMyCollectionModel {
    /**
     * result : 1
     * body : [{"blog_id":185631,"blog_board_id":3,"user_id":450000633,"title":"这种情况是这样的","content":" 这种情况是这样的日子过","create_time":1470188220000,"update_time":1470626551000,"status":2,"type":2,"stick":0,"comment_count":1,"favorite_count":1,"view_count":8,"blogComments":[],"anonymity":1,"anonymityUser":{"icon":"/imageServer/9C66zhongxing.png","next_level_integral":1,"userLevel":0,"nick_name":"匿名用户","gold_coin":0,"charm":0},"replay_flag":0,"unread_flag":0,"favorite_id":9277,"board_title":"我的羞羞日记","user_name":"夜友-11784","icon":"/imageServer/9C66male.png","sex":"F","verify_status":1,"favorite_time":1470626550000,"userLevel":6,"achieve_level_pic":"/propServer/achievement/new/color_dj_3.png","goods_id":0,"wysqImglist":[],"vip_level":0,"achieve_gold_pic":"/propServer/achievement/new/color_cf_6.png"},{"blog_id":185551,"blog_board_id":3,"user_id":450214623,"title":"测试测试回复","content":" 霸哥被扣","create_time":1469513062000,"update_time":1470626549000,"status":2,"type":1,"stick":0,"comment_count":12,"favorite_count":1,"view_count":52,"blogComments":[],"anonymity":0,"replay_flag":0,"unread_flag":0,"favorite_id":9276,"board_title":"我的羞羞日记","user_name":"后规模","icon":"/imageServer/DC1Ec5c24d27e1894e16aaee5502e7d84da0.jpg","sex":"M","verify_status":0,"favorite_time":1470626549000,"userLevel":9,"achieve_level_pic":"/propServer/achievement/new/color_dj_5.png","goods_id":0,"wysqImglist":[],"vip_level":0,"achieve_charm_pic":"/propServer/achievement/new/corlo_ml_2.png","achieve_gold_pic":"/propServer/achievement/new/color_cf_6.png"},{"blog_id":185533,"blog_board_id":3,"user_id":450000616,"title":"看看通知中心有没有","content":" 有就好","create_time":1469173156000,"update_time":1469447363000,"status":2,"type":2,"stick":0,"comment_count":6,"favorite_count":1,"view_count":18,"blogComments":[],"anonymity":0,"replay_flag":0,"unread_flag":0,"favorite_id":9238,"board_title":"我的羞羞日记","user_name":"夜友-11767","icon":"/imageServer/9C66male.png","sex":"M","verify_status":0,"favorite_time":1469446143000,"userLevel":9,"achieve_level_pic":"/propServer/achievement/new/color_dj_5.png","goods_id":0,"wysqImglist":[],"vip_level":0,"achieve_gold_pic":"/propServer/achievement/new/color_cf_6.png"}]
     */

    private String result;
    /**
     * blog_id : 185631
     * blog_board_id : 3
     * user_id : 450000633
     * title : 这种情况是这样的
     * content :  这种情况是这样的日子过
     * create_time : 1470188220000
     * update_time : 1470626551000
     * status : 2
     * type : 2
     * stick : 0
     * comment_count : 1
     * favorite_count : 1
     * view_count : 8
     * blogComments : []
     * anonymity : 1
     * anonymityUser : {"icon":"/imageServer/9C66zhongxing.png","next_level_integral":1,"userLevel":0,"nick_name":"匿名用户","gold_coin":0,"charm":0}
     * replay_flag : 0
     * unread_flag : 0
     * favorite_id : 9277
     * board_title : 我的羞羞日记
     * user_name : 夜友-11784
     * icon : /imageServer/9C66male.png
     * sex : F
     * verify_status : 1
     * favorite_time : 1470626550000
     * userLevel : 6
     * achieve_level_pic : /propServer/achievement/new/color_dj_3.png
     * goods_id : 0
     * wysqImglist : []
     * vip_level : 0
     * achieve_gold_pic : /propServer/achievement/new/color_cf_6.png
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
        private int blog_id;
        private int blog_board_id;
        private long user_id;
        private String title;
        private String content;
        private long create_time;
        private long update_time;
        private int status;
        private int type;
        private int stick;
        private int comment_count;
        private int favorite_count;
        private int view_count;
        private int anonymity;
        /**
         * icon : /imageServer/9C66zhongxing.png
         * next_level_integral : 1
         * userLevel : 0
         * nick_name : 匿名用户
         * gold_coin : 0
         * charm : 0
         */

        private AnonymityUserEntity anonymityUser;
        private int replay_flag;
        private int unread_flag;
        private int favorite_id;
        private String board_title;
        private String user_name;
        private String icon;
        private String sex;
        private int verify_status;
        private long favorite_time;
        private int userLevel;
        private String achieve_level_pic;
        private int goods_id;
        private int vip_level;
        private String achieve_gold_pic;
        private List<?> blogComments;
        private List<?> wysqImglist;

        public int getBlog_id() {
            return blog_id;
        }

        public void setBlog_id(int blog_id) {
            this.blog_id = blog_id;
        }

        public int getBlog_board_id() {
            return blog_board_id;
        }

        public void setBlog_board_id(int blog_board_id) {
            this.blog_board_id = blog_board_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStick() {
            return stick;
        }

        public void setStick(int stick) {
            this.stick = stick;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(int favorite_count) {
            this.favorite_count = favorite_count;
        }

        public int getView_count() {
            return view_count;
        }

        public void setView_count(int view_count) {
            this.view_count = view_count;
        }

        public int getAnonymity() {
            return anonymity;
        }

        public void setAnonymity(int anonymity) {
            this.anonymity = anonymity;
        }

        public AnonymityUserEntity getAnonymityUser() {
            return anonymityUser;
        }

        public void setAnonymityUser(AnonymityUserEntity anonymityUser) {
            this.anonymityUser = anonymityUser;
        }

        public int getReplay_flag() {
            return replay_flag;
        }

        public void setReplay_flag(int replay_flag) {
            this.replay_flag = replay_flag;
        }

        public int getUnread_flag() {
            return unread_flag;
        }

        public void setUnread_flag(int unread_flag) {
            this.unread_flag = unread_flag;
        }

        public int getFavorite_id() {
            return favorite_id;
        }

        public void setFavorite_id(int favorite_id) {
            this.favorite_id = favorite_id;
        }

        public String getBoard_title() {
            return board_title;
        }

        public void setBoard_title(String board_title) {
            this.board_title = board_title;
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

        public long getFavorite_time() {
            return favorite_time;
        }

        public void setFavorite_time(long favorite_time) {
            this.favorite_time = favorite_time;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public String getAchieve_level_pic() {
            return achieve_level_pic;
        }

        public void setAchieve_level_pic(String achieve_level_pic) {
            this.achieve_level_pic = achieve_level_pic;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getVip_level() {
            return vip_level;
        }

        public void setVip_level(int vip_level) {
            this.vip_level = vip_level;
        }

        public String getAchieve_gold_pic() {
            return achieve_gold_pic;
        }

        public void setAchieve_gold_pic(String achieve_gold_pic) {
            this.achieve_gold_pic = achieve_gold_pic;
        }

        public List<?> getBlogComments() {
            return blogComments;
        }

        public void setBlogComments(List<?> blogComments) {
            this.blogComments = blogComments;
        }

        public List<?> getWysqImglist() {
            return wysqImglist;
        }

        public void setWysqImglist(List<?> wysqImglist) {
            this.wysqImglist = wysqImglist;
        }

        public static class AnonymityUserEntity {
            private String icon;
            private int next_level_integral;
            private int userLevel;
            private String nick_name;
            private int gold_coin;
            private int charm;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getNext_level_integral() {
                return next_level_integral;
            }

            public void setNext_level_integral(int next_level_integral) {
                this.next_level_integral = next_level_integral;
            }

            public int getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(int userLevel) {
                this.userLevel = userLevel;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public int getGold_coin() {
                return gold_coin;
            }

            public void setGold_coin(int gold_coin) {
                this.gold_coin = gold_coin;
            }

            public int getCharm() {
                return charm;
            }

            public void setCharm(int charm) {
                this.charm = charm;
            }
        }
    }
}
