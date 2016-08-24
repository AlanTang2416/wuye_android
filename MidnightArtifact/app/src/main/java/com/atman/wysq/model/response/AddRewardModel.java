package com.atman.wysq.model.response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/5 10:35
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class AddRewardModel {
    /**
     * result : 1
     * body : {"award_log_id":367,"user_id":450000397,"type":1,"oid":184073,"gold_num":20}
     */

    private String result;
    /**
     * award_log_id : 367
     * user_id : 450000397
     * type : 1
     * oid : 184073
     * gold_num : 20
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
        private int award_log_id;
        private long user_id;
        private int type;
        private int oid;
        private int gold_num;

        public int getAward_log_id() {
            return award_log_id;
        }

        public void setAward_log_id(int award_log_id) {
            this.award_log_id = award_log_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public int getGold_num() {
            return gold_num;
        }

        public void setGold_num(int gold_num) {
            this.gold_num = gold_num;
        }
    }
}
