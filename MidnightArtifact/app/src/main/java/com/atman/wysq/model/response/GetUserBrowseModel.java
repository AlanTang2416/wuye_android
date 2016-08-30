package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/29 15:01
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetUserBrowseModel {
    /**
     * result : 1
     * body : {"dataList":[{"user_id":450000632,"integral":"15216","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1470892572000,"credit":1,"name_change":0,"next_level_integral":4767,"userLevel":6,"mobile":"13920000021","nick_name":"夜友-11783","status":2,"verify_status":1,"gold_coin":91956,"charm":8044,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":33,"accrue_coin":36833},{"user_id":450000633,"integral":"10149","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1470881317000,"credit":6,"name_change":0,"next_level_integral":9800,"userLevel":6,"mobile":"13920000022","nick_name":"夜友-11784","status":2,"verify_status":1,"gold_coin":89115,"charm":61108,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":26,"accrue_coin":65623},{"user_id":450000621,"integral":"212","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1470879926000,"name_change":1,"next_level_integral":566,"userLevel":3,"mobile":"13920000010","nick_name":"测试VIP功能","status":2,"verify_status":1,"gold_coin":23680,"charm":32,"can_chat":1,"can_shake":1,"vip_level":1,"chat_count":27,"accrue_coin":31456},{"user_id":450000397,"integral":"199418","secret_type":0,"type":1,"icon":"/imageServer/D7B379ffdb62fbb3458ea093db3d3fc52564.jpg","sex":"F","create_time":1470879769000,"name_change":1,"next_level_integral":549,"userLevel":9,"mobile":"13860000001","nick_name":"乖乖隆地洞，老鼠儿子会","status":2,"verify_status":0,"user_token":"ewN32hYZtivwKiiK0CGQe/YwdZezAm/uq8h7ymv1P0W1h3H9ks7++TcRTSE27hr7QCeIWkoC8kT9Im5US0Wm9GvMBlxeKFIO","gold_coin":19889,"charm":1596,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":24,"accrue_coin":253689},{"user_id":450214658,"integral":"10049","secret_type":0,"type":1,"icon":"/imageServer/9C66female.png","sex":"F","create_time":1470801976000,"name_change":1,"next_level_integral":9950,"userLevel":6,"mobile":"18888888880","nick_name":"uiii","status":2,"verify_status":0,"gold_coin":26471,"charm":1048440,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":5,"accrue_coin":755989},{"user_id":450000395,"integral":"5001","secret_type":0,"type":9,"icon":"/imageServer/8179c133ee00789a44d0ac84b5270bb5ccd2.jpg","sex":"F","create_time":1470201889000,"name_change":1,"next_level_integral":2988,"userLevel":5,"mobile":"13820000004","nick_name":"午夜奇葩1","status":2,"verify_status":1,"user_token":"1rHvvclqXq60GCPWWQpk0NSL/Rea/7BHZSezjWO8jI68Ou5ssVgiPol6GFf7pMmhzAI7S1gDAof3uEdDUXpgpg==","gold_coin":17678,"charm":11,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":1,"accrue_coin":20225},{"user_id":450000168,"integral":"205330","secret_type":0,"type":2,"icon":"/imageServer/C029174175f689b3451f9a80f8c9c28005d0.jpg","sex":"F","create_time":1470047171000,"credit":191,"name_change":0,"next_level_integral":9999999999999999,"userLevel":10,"mobile":"13817673794","nick_name":"落霞与孤雾齐飞","status":2,"verify_status":1,"user_token":"dPzBXpL4J9aQ3cySaQESEJ9QMcE8zfmW/LjFsMQgC1X16dCt9c4Rx/ql8KRrfIuDklLNgcU/ZLIlbRjLGqXXwtmF4MAhylgY","gold_coin":27366,"charm":125899,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":161,"accrue_coin":496978},{"user_id":450000181,"integral":"17156","secret_type":0,"type":1,"icon":"/imageServer/A102b5c5352b2ec24836914baad372c79e54.jpg","sex":"F","create_time":1469624704000,"credit":2,"name_change":1,"next_level_integral":2816,"userLevel":6,"mobile":"13811111119","nick_name":"66666-","status":2,"verify_status":1,"user_token":"ozDfLvPxJWLDQz1V3vDYx59QMcE8zfmW/LjFsMQgC1UBWADSN74XwllhVQHV+W0i9rLM0qsXdrpPpreJj1N7rdmF4MAhylgY","gold_coin":2022378,"charm":3300045,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":2,"accrue_coin":2342312},{"user_id":450000626,"integral":"125297","secret_type":0,"type":1,"icon":"/imageServer/B0E20eecff8519f54ed59f3b9afe24d286bb.jpg","sex":"F","create_time":1469496056000,"credit":19,"name_change":1,"next_level_integral":74669,"userLevel":9,"mobile":"13920000015","nick_name":"西红柿","status":2,"verify_status":1,"gold_coin":45217,"charm":110827,"can_chat":1,"can_shake":1,"vip_level":3,"chat_count":20,"accrue_coin":216881},{"user_id":6000001234,"integral":"5106","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1469449127000,"name_change":1,"next_level_integral":2886,"userLevel":5,"mobile":"13840000002","nick_name":"就是觉得","status":2,"verify_status":0,"gold_coin":994261,"charm":10,"vip_level":0,"accrue_coin":83},{"user_id":450214548,"integral":"110319","secret_type":0,"type":1,"icon":"/imageServer/39C80cc5221a2c5d4a9bbcf40cc3330db5f7.jpg","sex":"M","create_time":1469426381000,"name_change":1,"next_level_integral":89635,"userLevel":9,"mobile":"17701426903","nick_name":"狼心","status":2,"verify_status":0,"user_token":"Jd7pP2umEmkmuG98BCcxQvYwdZezAm/uq8h7ymv1P0XP7VI30k2NfL9GBe+BLyM9C7tOToDXzZogJM28c5JkXh58iL3uj5HE","gold_coin":22,"charm":198,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":13,"accrue_coin":85541},{"user_id":450000620,"integral":"105660","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1469086472000,"credit":1,"name_change":0,"next_level_integral":94333,"userLevel":9,"mobile":"13920000009","nick_name":"夜友-11771","status":2,"verify_status":1,"gold_coin":33315,"charm":70008,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":8,"accrue_coin":46324},{"user_id":450000619,"integral":"112154","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1468985727000,"name_change":0,"next_level_integral":87841,"userLevel":9,"mobile":"13920000008","nick_name":"夜友-11770","status":2,"verify_status":0,"gold_coin":4949,"charm":1005,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":28,"accrue_coin":6271},{"user_id":450000624,"integral":"100050","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1468926264000,"name_change":0,"next_level_integral":99950,"userLevel":9,"mobile":"13920000013","nick_name":"夜友-11775","status":2,"verify_status":0,"gold_coin":14893,"charm":300,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":3,"accrue_coin":15401},{"user_id":450000623,"integral":"2250","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1468925692000,"name_change":0,"next_level_integral":750,"userLevel":4,"mobile":"13920000012","nick_name":"夜友-11774","status":2,"verify_status":0,"gold_coin":1978,"charm":0,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":1,"accrue_coin":1002}],"dataSize":15}
     */

    private String result;
    /**
     * dataList : [{"user_id":450000632,"integral":"15216","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1470892572000,"credit":1,"name_change":0,"next_level_integral":4767,"userLevel":6,"mobile":"13920000021","nick_name":"夜友-11783","status":2,"verify_status":1,"gold_coin":91956,"charm":8044,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":33,"accrue_coin":36833},{"user_id":450000633,"integral":"10149","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1470881317000,"credit":6,"name_change":0,"next_level_integral":9800,"userLevel":6,"mobile":"13920000022","nick_name":"夜友-11784","status":2,"verify_status":1,"gold_coin":89115,"charm":61108,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":26,"accrue_coin":65623},{"user_id":450000621,"integral":"212","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1470879926000,"name_change":1,"next_level_integral":566,"userLevel":3,"mobile":"13920000010","nick_name":"测试VIP功能","status":2,"verify_status":1,"gold_coin":23680,"charm":32,"can_chat":1,"can_shake":1,"vip_level":1,"chat_count":27,"accrue_coin":31456},{"user_id":450000397,"integral":"199418","secret_type":0,"type":1,"icon":"/imageServer/D7B379ffdb62fbb3458ea093db3d3fc52564.jpg","sex":"F","create_time":1470879769000,"name_change":1,"next_level_integral":549,"userLevel":9,"mobile":"13860000001","nick_name":"乖乖隆地洞，老鼠儿子会","status":2,"verify_status":0,"user_token":"ewN32hYZtivwKiiK0CGQe/YwdZezAm/uq8h7ymv1P0W1h3H9ks7++TcRTSE27hr7QCeIWkoC8kT9Im5US0Wm9GvMBlxeKFIO","gold_coin":19889,"charm":1596,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":24,"accrue_coin":253689},{"user_id":450214658,"integral":"10049","secret_type":0,"type":1,"icon":"/imageServer/9C66female.png","sex":"F","create_time":1470801976000,"name_change":1,"next_level_integral":9950,"userLevel":6,"mobile":"18888888880","nick_name":"uiii","status":2,"verify_status":0,"gold_coin":26471,"charm":1048440,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":5,"accrue_coin":755989},{"user_id":450000395,"integral":"5001","secret_type":0,"type":9,"icon":"/imageServer/8179c133ee00789a44d0ac84b5270bb5ccd2.jpg","sex":"F","create_time":1470201889000,"name_change":1,"next_level_integral":2988,"userLevel":5,"mobile":"13820000004","nick_name":"午夜奇葩1","status":2,"verify_status":1,"user_token":"1rHvvclqXq60GCPWWQpk0NSL/Rea/7BHZSezjWO8jI68Ou5ssVgiPol6GFf7pMmhzAI7S1gDAof3uEdDUXpgpg==","gold_coin":17678,"charm":11,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":1,"accrue_coin":20225},{"user_id":450000168,"integral":"205330","secret_type":0,"type":2,"icon":"/imageServer/C029174175f689b3451f9a80f8c9c28005d0.jpg","sex":"F","create_time":1470047171000,"credit":191,"name_change":0,"next_level_integral":9999999999999999,"userLevel":10,"mobile":"13817673794","nick_name":"落霞与孤雾齐飞","status":2,"verify_status":1,"user_token":"dPzBXpL4J9aQ3cySaQESEJ9QMcE8zfmW/LjFsMQgC1X16dCt9c4Rx/ql8KRrfIuDklLNgcU/ZLIlbRjLGqXXwtmF4MAhylgY","gold_coin":27366,"charm":125899,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":161,"accrue_coin":496978},{"user_id":450000181,"integral":"17156","secret_type":0,"type":1,"icon":"/imageServer/A102b5c5352b2ec24836914baad372c79e54.jpg","sex":"F","create_time":1469624704000,"credit":2,"name_change":1,"next_level_integral":2816,"userLevel":6,"mobile":"13811111119","nick_name":"66666-","status":2,"verify_status":1,"user_token":"ozDfLvPxJWLDQz1V3vDYx59QMcE8zfmW/LjFsMQgC1UBWADSN74XwllhVQHV+W0i9rLM0qsXdrpPpreJj1N7rdmF4MAhylgY","gold_coin":2022378,"charm":3300045,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":2,"accrue_coin":2342312},{"user_id":450000626,"integral":"125297","secret_type":0,"type":1,"icon":"/imageServer/B0E20eecff8519f54ed59f3b9afe24d286bb.jpg","sex":"F","create_time":1469496056000,"credit":19,"name_change":1,"next_level_integral":74669,"userLevel":9,"mobile":"13920000015","nick_name":"西红柿","status":2,"verify_status":1,"gold_coin":45217,"charm":110827,"can_chat":1,"can_shake":1,"vip_level":3,"chat_count":20,"accrue_coin":216881},{"user_id":6000001234,"integral":"5106","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1469449127000,"name_change":1,"next_level_integral":2886,"userLevel":5,"mobile":"13840000002","nick_name":"就是觉得","status":2,"verify_status":0,"gold_coin":994261,"charm":10,"vip_level":0,"accrue_coin":83},{"user_id":450214548,"integral":"110319","secret_type":0,"type":1,"icon":"/imageServer/39C80cc5221a2c5d4a9bbcf40cc3330db5f7.jpg","sex":"M","create_time":1469426381000,"name_change":1,"next_level_integral":89635,"userLevel":9,"mobile":"17701426903","nick_name":"狼心","status":2,"verify_status":0,"user_token":"Jd7pP2umEmkmuG98BCcxQvYwdZezAm/uq8h7ymv1P0XP7VI30k2NfL9GBe+BLyM9C7tOToDXzZogJM28c5JkXh58iL3uj5HE","gold_coin":22,"charm":198,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":13,"accrue_coin":85541},{"user_id":450000620,"integral":"105660","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1469086472000,"credit":1,"name_change":0,"next_level_integral":94333,"userLevel":9,"mobile":"13920000009","nick_name":"夜友-11771","status":2,"verify_status":1,"gold_coin":33315,"charm":70008,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":8,"accrue_coin":46324},{"user_id":450000619,"integral":"112154","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1468985727000,"name_change":0,"next_level_integral":87841,"userLevel":9,"mobile":"13920000008","nick_name":"夜友-11770","status":2,"verify_status":0,"gold_coin":4949,"charm":1005,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":28,"accrue_coin":6271},{"user_id":450000624,"integral":"100050","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1468926264000,"name_change":0,"next_level_integral":99950,"userLevel":9,"mobile":"13920000013","nick_name":"夜友-11775","status":2,"verify_status":0,"gold_coin":14893,"charm":300,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":3,"accrue_coin":15401},{"user_id":450000623,"integral":"2250","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1468925692000,"name_change":0,"next_level_integral":750,"userLevel":4,"mobile":"13920000012","nick_name":"夜友-11774","status":2,"verify_status":0,"gold_coin":1978,"charm":0,"can_chat":1,"can_shake":1,"vip_level":0,"chat_count":1,"accrue_coin":1002}]
     * dataSize : 15
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
        private int dataSize;
        /**
         * user_id : 450000632
         * integral : 15216
         * secret_type : 0
         * type : 1
         * icon : /imageServer/9C66male.png
         * sex : F
         * create_time : 1470892572000
         * credit : 1
         * name_change : 0
         * next_level_integral : 4767
         * userLevel : 6
         * mobile : 13920000021
         * nick_name : 夜友-11783
         * status : 2
         * verify_status : 1
         * gold_coin : 91956
         * charm : 8044
         * can_chat : 1
         * can_shake : 1
         * vip_level : 0
         * chat_count : 33
         * accrue_coin : 36833
         */

        private List<DataListEntity> dataList;

        public int getDataSize() {
            return dataSize;
        }

        public void setDataSize(int dataSize) {
            this.dataSize = dataSize;
        }

        public List<DataListEntity> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListEntity> dataList) {
            this.dataList = dataList;
        }

        public static class DataListEntity {
            private long user_id;
            private String integral;
            private int secret_type;
            private int type;
            private String icon;
            private String sex;
            private long create_time;
            private int credit;
            private int name_change;
            private long next_level_integral;
            private int userLevel;
            private String mobile;
            private String nick_name;
            private int status;
            private int verify_status;
            private int gold_coin;
            private int charm;
            private int can_chat;
            private int can_shake;
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
