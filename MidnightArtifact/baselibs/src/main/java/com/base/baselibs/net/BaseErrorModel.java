package com.base.baselibs.net;

/**
 * 描述 访问错误的解析基础类
 * 作者 tangbingliang
 * 时间 16/7/6 10:50
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BaseErrorModel {

    /**
     * error : no enough goldcoin
     * error_code : 30041
     * error_description : 账户余额不足，请先检查账号余额或充值后再试!
     */

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
