package com.atman.wysq.model.request;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/7 11:36
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class NewPassWordRequestModel {
    /**
     * mobile : 13451682880
     * password : 123456
     * valid_code : 123456
     */

    private String mobile;
    private String password;
    private String valid_code;

    public NewPassWordRequestModel(String mobile, String password, String valid_code) {
        this.mobile = mobile;
        this.password = password;
        this.valid_code = valid_code;
    }
}
