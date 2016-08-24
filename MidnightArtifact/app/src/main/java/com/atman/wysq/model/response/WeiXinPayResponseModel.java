package com.atman.wysq.model.response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/14 17:14
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class WeiXinPayResponseModel {
    /**
     * result : 1
     * body : {"order_id":2016071400000122,"appid":"wx142af8d518f19762","mch_id":"1268963601","nonce_str":"bcc6f84e9dd946f2bb98bba6ea3df1f1","sign":"EF0B741369B8E35B92F85A223446E8C9","body":"12金币","out_trade_no":"2016071400000122A0714172118","total_fee":600,"spbill_create_ip":"192.168.255.155","notify_url":"http://api.5ys7.com/wechatcallback","trade_type":"APP","prepayId":"wx20160714171329726c26dc560834276335","resultCode":"SUCCESS","returnCode":"SUCCESS"}
     */

    private String result;
    /**
     * order_id : 2016071400000122
     * appid : wx142af8d518f19762
     * mch_id : 1268963601
     * nonce_str : bcc6f84e9dd946f2bb98bba6ea3df1f1
     * sign : EF0B741369B8E35B92F85A223446E8C9
     * body : 12金币
     * out_trade_no : 2016071400000122A0714172118
     * total_fee : 600
     * spbill_create_ip : 192.168.255.155
     * notify_url : http://api.5ys7.com/wechatcallback
     * trade_type : APP
     * prepayId : wx20160714171329726c26dc560834276335
     * resultCode : SUCCESS
     * returnCode : SUCCESS
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
        private long order_id;
        private String appid;
        private String mch_id;
        private String nonce_str;
        private String sign;
        private String body;
        private String out_trade_no;
        private int total_fee;
        private String spbill_create_ip;
        private String notify_url;
        private String trade_type;
        private String prepayId;
        private String resultCode;
        private String returnCode;

        public long getOrder_id() {
            return order_id;
        }

        public void setOrder_id(long order_id) {
            this.order_id = order_id;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public int getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(int total_fee) {
            this.total_fee = total_fee;
        }

        public String getSpbill_create_ip() {
            return spbill_create_ip;
        }

        public void setSpbill_create_ip(String spbill_create_ip) {
            this.spbill_create_ip = spbill_create_ip;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }
    }
}
