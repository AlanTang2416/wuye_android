package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/25 17:51
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetFansModel {
    /**
     * result : 1
     * body : [{"id":862,"user_id":100052336,"follow_user_id":450088716,"create_time":1436147745000,"online_status":0,"prologue":"哈哈","friend_type":0,"userExt":{"user_id":100052336,"integral":"166.9","secret_type":0,"type":2,"icon":"/imageServer/19BC6eb5b90571bd424d855067aee2d25496.jpg","sex":"M","create_time":1423231265000,"update_time":1429289117000,"credit":1,"name_change":1,"next_level_integral":29,"userLevel":2,"mobile":"13651893191","nick_name":"呵呵哒_老子","status":2,"verify_status":0,"user_token":"XFrLl4NLkSfKOicgusvOcPX6DY+HD+S/bf2zH88GaRu0VAeXT8JvoCQ7yR5JdCw/dvyZ9TKW3bb0yR1Les1oZt1uNiGU2y5q","can_live_room":0,"gold_coin":1025,"charm":49,"can_chat":1,"can_shake":1,"convert_coin":0,"vip_level":0,"chat_count":0,"accrue_coin":84}}]
     */

    private String result;
    /**
     * id : 862
     * user_id : 100052336
     * follow_user_id : 450088716
     * create_time : 1436147745000
     * online_status : 0
     * prologue : 哈哈
     * friend_type : 0
     * userExt : {"user_id":100052336,"integral":"166.9","secret_type":0,"type":2,"icon":"/imageServer/19BC6eb5b90571bd424d855067aee2d25496.jpg","sex":"M","create_time":1423231265000,"update_time":1429289117000,"credit":1,"name_change":1,"next_level_integral":29,"userLevel":2,"mobile":"13651893191","nick_name":"呵呵哒_老子","status":2,"verify_status":0,"user_token":"XFrLl4NLkSfKOicgusvOcPX6DY+HD+S/bf2zH88GaRu0VAeXT8JvoCQ7yR5JdCw/dvyZ9TKW3bb0yR1Les1oZt1uNiGU2y5q","can_live_room":0,"gold_coin":1025,"charm":49,"can_chat":1,"can_shake":1,"convert_coin":0,"vip_level":0,"chat_count":0,"accrue_coin":84}
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
        private String sortLetters;  //显示数据拼音的首字母
        private long id;
        private long user_id;
        private int follow_user_id;
        private long create_time;
        private int online_status;
        private String prologue;
        private int friend_type;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getSortLetters() {
            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }

        /**
         * user_id : 100052336
         * integral : 166.9
         * secret_type : 0
         * type : 2
         * icon : /imageServer/19BC6eb5b90571bd424d855067aee2d25496.jpg
         * sex : M
         * create_time : 1423231265000
         * update_time : 1429289117000
         * credit : 1
         * name_change : 1
         * next_level_integral : 29
         * userLevel : 2
         * mobile : 13651893191
         * nick_name : 呵呵哒_老子
         * status : 2
         * verify_status : 0
         * user_token : XFrLl4NLkSfKOicgusvOcPX6DY+HD+S/bf2zH88GaRu0VAeXT8JvoCQ7yR5JdCw/dvyZ9TKW3bb0yR1Les1oZt1uNiGU2y5q
         * can_live_room : 0
         * gold_coin : 1025
         * charm : 49
         * can_chat : 1
         * can_shake : 1
         * convert_coin : 0
         * vip_level : 0
         * chat_count : 0
         * accrue_coin : 84
         */


        private UserExtEntity userExt;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public int getFollow_user_id() {
            return follow_user_id;
        }

        public void setFollow_user_id(int follow_user_id) {
            this.follow_user_id = follow_user_id;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getOnline_status() {
            return online_status;
        }

        public void setOnline_status(int online_status) {
            this.online_status = online_status;
        }

        public String getPrologue() {
            return prologue;
        }

        public void setPrologue(String prologue) {
            this.prologue = prologue;
        }

        public int getFriend_type() {
            return friend_type;
        }

        public void setFriend_type(int friend_type) {
            this.friend_type = friend_type;
        }

        public UserExtEntity getUserExt() {
            return userExt;
        }

        public void setUserExt(UserExtEntity userExt) {
            this.userExt = userExt;
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
            private int credit;
            private int name_change;
            private long next_level_integral;
            private int userLevel;
            private String mobile;
            private String nick_name;
            private int status;
            private int verify_status;
            private String user_token;
            private int can_live_room;
            private int gold_coin;
            private int charm;
            private int can_chat;
            private int can_shake;
            private int convert_coin;
            private int vip_level;
            private int chat_count;
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

            public int getCredit() {
                return credit;
            }

            public void setCredit(int credit) {
                this.credit = credit;
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

            public String getUser_token() {
                return user_token;
            }

            public void setUser_token(String user_token) {
                this.user_token = user_token;
            }

            public int getCan_live_room() {
                return can_live_room;
            }

            public void setCan_live_room(int can_live_room) {
                this.can_live_room = can_live_room;
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

            public int getCan_chat() {
                return can_chat;
            }

            public void setCan_chat(int can_chat) {
                this.can_chat = can_chat;
            }

            public int getCan_shake() {
                return can_shake;
            }

            public void setCan_shake(int can_shake) {
                this.can_shake = can_shake;
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

            public int getChat_count() {
                return chat_count;
            }

            public void setChat_count(int chat_count) {
                this.chat_count = chat_count;
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
