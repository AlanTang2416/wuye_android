package com.atman.wysq.model.response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/7 14:56
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetUserInfoModel {
    /**
     * result : 1
     * body : {"userId":6000001234,"nickName":"xxx2","addTime":1468571719000,"userExt":{"user_id":6000001234,"integral":"2","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1468571719000,"update_time":1468571719000,"name_change":0,"next_level_integral":48,"userLevel":1,"mobile":"13840000002","nick_name":"xxx2","status":2,"verify_status":0,"gold_coin":994605,"charm":10,"convert_coin":7,"vip_level":0,"accrue_coin":7},"follow_count":0,"followed_count":0,"favorited_count":0,"comment_count":15,"achieve_level_pic":"/propServer/achievement/new/color_dj_1.png","achieve_gold_pic":"/propServer/achievement/new/color_cf_3.png"}
     */

    private String result;
    /**
     * userId : 6000001234
     * nickName : xxx2
     * addTime : 1468571719000
     * userExt : {"user_id":6000001234,"integral":"2","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1468571719000,"update_time":1468571719000,"name_change":0,"next_level_integral":48,"userLevel":1,"mobile":"13840000002","nick_name":"xxx2","status":2,"verify_status":0,"gold_coin":994605,"charm":10,"convert_coin":7,"vip_level":0,"accrue_coin":7}
     * follow_count : 0
     * followed_count : 0
     * favorited_count : 0
     * comment_count : 15
     * achieve_level_pic : /propServer/achievement/new/color_dj_1.png
     * achieve_gold_pic : /propServer/achievement/new/color_cf_3.png
     */

    private BodyEntity body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BodyEntity getBody() {
        return body;
    }

    public void setBody(BodyEntity body) {
        this.body = body;
    }

    public static class BodyEntity {
        private long userId;
        private String nickName;
        private long addTime;
        /**
         * user_id : 6000001234
         * integral : 2
         * secret_type : 0
         * type : 1
         * icon : /imageServer/9C66male.png
         * sex : M
         * create_time : 1468571719000
         * update_time : 1468571719000
         * name_change : 0
         * next_level_integral : 48
         * userLevel : 1
         * mobile : 13840000002
         * nick_name : xxx2
         * status : 2
         * verify_status : 0
         * gold_coin : 994605
         * charm : 10
         * convert_coin : 7
         * vip_level : 0
         * accrue_coin : 7
         */

        private UserExtEntity userExt;
        private int follow_count;
        private int followed_count;
        private int favorited_count;
        private int comment_count;
        private String achieve_level_pic;
        private String achieve_gold_pic;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public UserExtEntity getUserExt() {
            return userExt;
        }

        public void setUserExt(UserExtEntity userExt) {
            this.userExt = userExt;
        }

        public int getFollow_count() {
            return follow_count;
        }

        public void setFollow_count(int follow_count) {
            this.follow_count = follow_count;
        }

        public int getFollowed_count() {
            return followed_count;
        }

        public void setFollowed_count(int followed_count) {
            this.followed_count = followed_count;
        }

        public int getFavorited_count() {
            return favorited_count;
        }

        public void setFavorited_count(int favorited_count) {
            this.favorited_count = favorited_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
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

        public static class UserExtEntity {
            private long user_id;
            private String integral;
            private int secret_type;
            private int type;
            private String icon;
            private String sex;
            private long create_time;
            private long update_time;
            private int name_change;
            private long next_level_integral;
            private int userLevel;
            private String mobile;
            private String nick_name;
            private int status;
            private int verify_status;
            private int gold_coin;
            private int charm;
            private int convert_coin;
            private int vip_level;
            private int accrue_coin;

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public int getSecret_type() {
                return secret_type;
            }

            public void setSecret_type(int secret_type) {
                this.secret_type = secret_type;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

            public int getName_change() {
                return name_change;
            }

            public void setName_change(int name_change) {
                this.name_change = name_change;
            }

            public long getNext_level_integral() {
                return next_level_integral;
            }

            public void setNext_level_integral(long next_level_integral) {
                this.next_level_integral = next_level_integral;
            }

            public int getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(int userLevel) {
                this.userLevel = userLevel;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getVerify_status() {
                return verify_status;
            }

            public void setVerify_status(int verify_status) {
                this.verify_status = verify_status;
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

            public int getConvert_coin() {
                return convert_coin;
            }

            public void setConvert_coin(int convert_coin) {
                this.convert_coin = convert_coin;
            }

            public int getVip_level() {
                return vip_level;
            }

            public void setVip_level(int vip_level) {
                this.vip_level = vip_level;
            }

            public int getAccrue_coin() {
                return accrue_coin;
            }

            public void setAccrue_coin(int accrue_coin) {
                this.accrue_coin = accrue_coin;
            }
        }
    }
}
