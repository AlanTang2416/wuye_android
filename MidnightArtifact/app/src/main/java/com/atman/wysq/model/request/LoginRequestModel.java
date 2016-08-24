package com.atman.wysq.model.request;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/6 11:35
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class LoginRequestModel {
    /**
     * userName : 13860000002
     * password : 343b1c4a3ea721b2d640fc8700db0f36
     * idfa : idfa
     * platform : 客户端操作系统
     * language : en
     * deviceToken : token
     * isTestToken : false
     * version : 2.1.0
     * deviceType : 客户端硬件型号
     * channel : 广告来源
     */

    private String userName;
    private String password;
    private String idfa;
    private String platform;
    private String language;
    private String deviceToken;
    private boolean isTestToken;
    private String version;
    private String deviceType;
    private String channel;

    public LoginRequestModel () {}

    public LoginRequestModel (String userName, String password, String idfa, String deviceToken,
                              boolean isTestToken, String version, String deviceType, String channel) {
        this.userName = userName;
        this.password = password;
        this.idfa = idfa;
        this.platform = "Android";
        this.platform = "en";
        this.deviceToken = deviceToken;
        this.isTestToken = isTestToken;
        this.version = version;
        this.deviceType = deviceType;
        this.channel = channel;
    }
}
