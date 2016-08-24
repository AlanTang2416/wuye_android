package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/5 10:53
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetRewardListModel {
    /**
     * result : 1
     * body : [{"user_id":1000003,"integral":"0","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1468833289000,"name_change":0,"next_level_integral":1,"userLevel":0,"mobile":"13101000003","nick_name":"test_1000003","status":2,"verify_status":0,"can_live_room":1,"gold_coin":0,"charm":1000,"convert_coin":0,"user_award_gold_num":5,"chat_count":0},{"user_id":450000348,"integral":"188","secret_type":0,"type":1,"icon":"/imageServer/B9ECfd4de06b03a345fa8a7909f004b26d5f.jpg","sex":"F","create_time":1466756803000,"name_change":0,"next_level_integral":582,"userLevel":3,"mobile":"13850000002","nick_name":"我是睡","status":2,"verify_status":1,"user_token":"u+P8QbTisImxlT+LU5vkAJ9QMcE8zfmW/LjFsMQgC1UfNf/CoMB++UU1YNb1DCgJs05S2Q9d2NYgqB3Rep8Iv9mF4MAhylgY","can_live_room":1,"gold_coin":19070,"charm":30,"can_chat":1,"can_shake":1,"convert_coin":27,"user_award_gold_num":5,"chat_count":3,"accrue_coin":21029},{"user_id":450000347,"integral":"4455","secret_type":1,"type":1,"icon":"/imageServer/568D0c24242cc75c43b2afd07daf75ddf183.jpg","sex":"M","create_time":1466646653000,"credit":18,"name_change":0,"next_level_integral":3544,"userLevel":5,"mobile":"13850000001","nick_name":"dfdf","status":2,"verify_status":0,"user_token":"D19bWeS081TIsNdye6nY+PYwdZezAm/uq8h7ymv1P0XOIk8FaEapl4+5xBXkPz/hnT+bKp3OEeEgJM28c5JkXmvMBlxeKFIO","can_live_room":1,"gold_coin":18589,"charm":30000,"can_chat":1,"can_shake":1,"convert_coin":50,"user_award_gold_num":5,"around_site":"Ms 你这是我婆婆一哦婆婆哦你哦破随心所欲周旭婆婆婆婆哦婆婆我坡跟单鞋女","chat_count":0,"accrue_coin":20179},{"user_id":450000393,"integral":"36801","secret_type":0,"type":9,"icon":"/imageServer/6027e86a3d8cacef4edd908abeebb2365117.jpg","sex":"F","create_time":1468833240000,"name_change":0,"next_level_integral":13048,"userLevel":7,"mobile":"13820000002","nick_name":"干妹妹","status":2,"verify_status":1,"user_token":"ZaecluvJa8WcnWWIPIMLfZ9QMcE8zfmW/LjFsMQgC1X16dCt9c4Rx5dBv/tWqL+xGWguSWsvXXNNTO+Kp96Dw9mF4MAhylgY","can_live_room":1,"gold_coin":42648,"charm":31959,"can_chat":1,"can_shake":1,"convert_coin":69,"user_award_gold_num":3,"hotel_id":2,"chat_count":23,"accrue_coin":176114},{"user_id":450000397,"integral":"189320","secret_type":0,"type":1,"icon":"/imageServer/D7B379ffdb62fbb3458ea093db3d3fc52564.jpg","sex":"F","create_time":1468997849000,"name_change":0,"next_level_integral":10649,"userLevel":9,"mobile":"13860000001","nick_name":"夜友-11549","status":2,"verify_status":0,"user_token":"ewN32hYZtivwKiiK0CGQe/YwdZezAm/uq8h7ymv1P0W1h3H9ks7++TcRTSE27hr7QCeIWkoC8kT9Im5US0Wm9GvMBlxeKFIO","can_live_room":1,"gold_coin":4,"charm":1596,"can_chat":1,"can_shake":1,"convert_coin":4,"user_award_gold_num":1,"chat_count":24,"accrue_coin":233687},{"user_id":1000004,"integral":"0","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1468833301000,"name_change":0,"next_level_integral":1,"userLevel":0,"mobile":"13101000004","nick_name":"夜友-500002","status":2,"verify_status":0,"can_live_room":1,"gold_coin":80,"charm":0,"convert_coin":0,"user_award_gold_num":1,"chat_count":0},{"user_id":450214613,"integral":"97048","secret_type":0,"type":1,"icon":"/imageServer/08DD1db869885111405987987d7d928e4aaa.jpg","sex":"F","create_time":1468833248000,"name_change":0,"next_level_integral":2938,"userLevel":8,"mobile":"13860000011","nick_name":"夜友-515824","status":2,"verify_status":1,"can_live_room":1,"gold_coin":11,"charm":71145,"can_chat":1,"can_shake":1,"convert_coin":10,"user_award_gold_num":1,"chat_count":4,"accrue_coin":207202},{"user_id":100000778,"integral":"35120","secret_type":0,"type":1,"icon":"/imageServer/1372c1f0c67c3e254c9b9c5d28b4f2e714b1.jpg","sex":"M","create_time":1468833193000,"name_change":0,"next_level_integral":14875,"userLevel":7,"mobile":"18721667830","nick_name":"lala","status":2,"verify_status":0,"user_token":"z657b5cxtEUirbf30f+D6PYwdZezAm/uq8h7ymv1P0XOIk8FaEapl6NjzdQjaYIq6jJJH00njuRdybkVhp4w/sWeHJFskxMt","can_live_room":1,"gold_coin":111169,"charm":2875,"can_chat":0,"can_shake":1,"convert_coin":394,"user_award_gold_num":1,"chat_count":28,"accrue_coin":116932},{"user_id":368404,"integral":"445350","secret_type":0,"type":2,"icon":"/imageServer/4816a8d68ad6c56c4fe4829bb0d43149d36d.jpg","sex":"F","create_time":1467185141000,"name_change":1,"next_level_integral":9999999999999999,"userLevel":10,"mobile":"13000000000","nick_name":"老子是：攻城狮","status":2,"verify_status":1,"user_token":"vFNdxgHi7nE7dtqDAvW/7dSL/Rea/7BHZSezjWO8jI7V7S97vEUEhmTq0TMdbYyZuS10sbnouAKXFRReIekVgw==","can_live_room":1,"gold_coin":1613132,"charm":1110,"can_chat":1,"can_shake":1,"convert_coin":797,"user_award_gold_num":1,"chat_count":15,"accrue_coin":796323}]
     */

    private String result;
    /**
     * user_id : 1000003
     * integral : 0
     * secret_type : 0
     * type : 1
     * icon : /imageServer/9C66male.png
     * sex : M
     * create_time : 1468833289000
     * name_change : 0
     * next_level_integral : 1
     * userLevel : 0
     * mobile : 13101000003
     * nick_name : test_1000003
     * status : 2
     * verify_status : 0
     * can_live_room : 1
     * gold_coin : 0
     * charm : 1000
     * convert_coin : 0
     * user_award_gold_num : 5
     * chat_count : 0
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
        private long user_id;
        private String integral;
        private int secret_type;
        private int type;
        private String icon;
        private String sex;
        private long create_time;
        private int name_change;
        private long next_level_integral;
        private int userLevel;
        private String mobile;
        private String nick_name;
        private int status;
        private int verify_status;
        private int can_live_room;
        private int gold_coin;
        private int charm;
        private int convert_coin;
        private int user_award_gold_num;
        private int chat_count;

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

        public int getConvert_coin() {
            return convert_coin;
        }

        public void setConvert_coin(int convert_coin) {
            this.convert_coin = convert_coin;
        }

        public int getUser_award_gold_num() {
            return user_award_gold_num;
        }

        public void setUser_award_gold_num(int user_award_gold_num) {
            this.user_award_gold_num = user_award_gold_num;
        }

        public int getChat_count() {
            return chat_count;
        }

        public void setChat_count(int chat_count) {
            this.chat_count = chat_count;
        }
    }
}
