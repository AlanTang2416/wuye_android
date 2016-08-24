package com.atman.wysq.model.response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/14 14:19
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class AliPayResponseModel {
    /**
     * result : 1
     * body : {"param":"partner=\"2088511497398971\"&seller_id=\"2088511497398971\"&out_trade_no=\"2016071400000058\"&subject=\"1\"&body=\"12金币\"&total_fee=\"6.00\"&notify_url=\"http://203.80.144.160:8080/rest/alipayCallback/call\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&show_url=\"http://img.5ys7.com:8080/com.atman.wysq.coin.1\"&sign=\"aXx54YeQnPph6llGIAGGSkae2C8ztU2DyFG1VjptDOeO2VJv21Jm6TXGRtkAaBtpwXEPtBJ4h9QKrb42KTn5lQCdDnw4%2B3FKVCOL%2BE0Hldvdb4LIwMDyQHlZsbGZ7IovxdYJgPQdtxW6CkQYpTSpXQHzIpC%2FYDOhxUqRN3QMDb0%3D\"&sign_type=\"RSA\""}
     */

    private String result;
    /**
     * param : partner="2088511497398971"&seller_id="2088511497398971"&out_trade_no="2016071400000058"&subject="1"&body="12金币"&total_fee="6.00"&notify_url="http://203.80.144.160:8080/rest/alipayCallback/call"&service="mobile.securitypay.pay"&payment_type="1"&_input_charset="utf-8"&it_b_pay="30m"&show_url="http://img.5ys7.com:8080/com.atman.wysq.coin.1"&sign="aXx54YeQnPph6llGIAGGSkae2C8ztU2DyFG1VjptDOeO2VJv21Jm6TXGRtkAaBtpwXEPtBJ4h9QKrb42KTn5lQCdDnw4%2B3FKVCOL%2BE0Hldvdb4LIwMDyQHlZsbGZ7IovxdYJgPQdtxW6CkQYpTSpXQHzIpC%2FYDOhxUqRN3QMDb0%3D"&sign_type="RSA"
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
        private String param;

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }
    }
}
