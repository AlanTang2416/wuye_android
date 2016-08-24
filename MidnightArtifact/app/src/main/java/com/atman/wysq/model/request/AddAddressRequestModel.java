package com.atman.wysq.model.request;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/22 15:32
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class AddAddressRequestModel {
    /**
     * address_id : 15
     * receiver_name : jison
     * receiver_address : 和出版界国家和环境将近jfkckbhkvjkn
     * receiver_area : 321092
     * receiver_phone : 15236523652
     * status : 1
     * use_count : 2
     * update_time : 1420523344000
     * receiver_area_name : 江苏省扬州市经济开发区
     */

    private String receiver_name;
    private String receiver_address;
    private String receiver_area;
    private String receiver_phone;
    private String receiver_area_name;

    public AddAddressRequestModel(String receiver_address, String receiver_area_name, String receiver_name
            , String receiver_phone, String receiver_area) {
        this.receiver_address = receiver_address;
        this.receiver_area_name = receiver_area_name;
        this.receiver_name = receiver_name;
        this.receiver_area = receiver_area;
        this.receiver_phone = receiver_phone;
    }
}
