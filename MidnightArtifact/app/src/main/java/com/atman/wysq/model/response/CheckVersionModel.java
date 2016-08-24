package com.atman.wysq.model.response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/26 14:14
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class CheckVersionModel {
    /**
     * result : 1
     * body : {"warn":"发现新版本,是否立即升级? \n ","force":"0","url":"http://www.5ys7.com/market/ground/self.html"}
     */

    private String result;
    /**
     * warn : 发现新版本,是否立即升级?

     * force : 0
     * url : http://www.5ys7.com/market/ground/self.html
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
        private String warn;
        private String force;
        private String url;
        private String error;
        private String error_code;

        public String getError_description() {
            return error_description;
        }

        public void setError_description(String error_description) {
            this.error_description = error_description;
        }

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

        private String error_description;

        public String getWarn() {
            return warn;
        }

        public void setWarn(String warn) {
            this.warn = warn;
        }

        public String getForce() {
            return force;
        }

        public void setForce(String force) {
            this.force = force;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
