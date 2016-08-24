package com.base.baselibs.net;

/**
 * 描述 访问错误的解析基础类
 * 作者 tangbingliang
 * 时间 16/7/6 10:50
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BaseErrorTwoModel {
    /**
     * result : 0
     * body : {"error":"User or Password error","error_code":"20020","error_description":"用户名或者密码错误。"}
     */

    private String result;
    /**
     * error : User or Password error
     * error_code : 20020
     * error_description : 用户名或者密码错误。
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
        private String error;
        private String error_code;
        private String error_description;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getError_code() {
            return error_code;
        }

        public void setError_code(String error_code) {
            this.error_code = error_code;
        }

        public String getError_description() {
            return error_description;
        }

        public void setError_description(String error_description) {
            this.error_description = error_description;
        }
    }
}
