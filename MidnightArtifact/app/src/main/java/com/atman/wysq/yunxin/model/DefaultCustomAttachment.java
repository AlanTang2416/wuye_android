package com.atman.wysq.yunxin.model;

import com.alibaba.fastjson.JSONObject;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/1 20:27
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class DefaultCustomAttachment extends CustomAttachment {

    private String content;

    public DefaultCustomAttachment() {
        super(0);
    }

    @Override
    protected void parseData(JSONObject data) {
        content = data.toJSONString();
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = null;
        try {
            data = JSONObject.parseObject(content);
        } catch (Exception e) {

        }
        return data;
    }

    public String getContent() {
        return content;
    }
}
