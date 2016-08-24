package com.atman.wysq.model.response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/6 10:54
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class LoginResultModel  {
    /**
     * result : 1
     * body : 450214604
     */

    private String result;
    private long body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getBody() {
        return body;
    }

    public void setBody(long body) {
        this.body = body;
    }
}
