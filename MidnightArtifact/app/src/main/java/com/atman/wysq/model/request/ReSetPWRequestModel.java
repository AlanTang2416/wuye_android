package com.atman.wysq.model.request;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/8 11:14
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ReSetPWRequestModel {
    /**
     * old_password : qqqqqq
     * password : qqqqqq
     */

    private String old_password;
    private String password;

    public ReSetPWRequestModel(String password, String old_password) {
        this.password = password;
        this.old_password = old_password;
    }
}
