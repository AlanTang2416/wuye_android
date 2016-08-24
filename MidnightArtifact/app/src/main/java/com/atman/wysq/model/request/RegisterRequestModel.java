package com.atman.wysq.model.request;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/6 11:35
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class RegisterRequestModel {

    /**
     * mobile : 13451682880
     * password : 123456
     * valid_code : 123456
     * name : locker9
     * iosinfo : {"mac":"28-D2-44-BB-A9-F8","idfa":"fe80::31f6:bce:d0f7:bc76","idfv":"fe80fe80fe80fe80fe80fe80fe80","version":"v1.0.0"}
     */

    private String mobile;
    private String password;
    private String valid_code;
    private String name;
    private String sex;//m：男，f：nv
    /**
     * mac : 28-D2-44-BB-A9-F8
     * idfa : fe80::31f6:bce:d0f7:bc76
     * idfv : fe80fe80fe80fe80fe80fe80fe80
     * version : v1.0.0
     */

    private IosinfoEntity iosinfo;

    public RegisterRequestModel() {}

    public RegisterRequestModel(String mobile, String password, String valid_code, String name,
                                String sex, String mac, String idfa, String idfv, String version) {
        this.mobile = mobile;
        this.password = password;
        this.valid_code = valid_code;
        this.name = name;
        this.sex = sex;
        this.iosinfo = new IosinfoEntity(mac, idfa, idfv, version);
    }

    public static class IosinfoEntity {
        private String mac;
        private String idfa;
        private String idfv;
        private String version;

        public IosinfoEntity(String mac, String idfa, String idfv, String version){
            this.mac = mac;
            this.idfa = idfa;
            this.idfv = idfv;
            this.version = version;
        }
    }
}
