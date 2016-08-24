package com.atman.wysq.model.response;

import java.util.List;
import java.util.Map;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/26 11:30
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetGoldenRoleModel {

    String result;
    Map<String, Fool> body;

    public Map<String, Fool> getBody() {
        return body;
    }

    public void setBody(Map<String, Fool> body) {
        this.body = body;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class Fool {
        private int golden_role_id;
        private String title;
        private int cost_golden;
        private int recycle_num;
        private int status;
        private long create_time;
        private long update_time;

        public int getGolden_role_id() {
            return golden_role_id;
        }

        public void setGolden_role_id(int golden_role_id) {
            this.golden_role_id = golden_role_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCost_golden() {
            return cost_golden;
        }

        public void setCost_golden(int cost_golden) {
            this.cost_golden = cost_golden;
        }

        public int getRecycle_num() {
            return recycle_num;
        }

        public void setRecycle_num(int recycle_num) {
            this.recycle_num = recycle_num;
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

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }
    }
}
