package com.atman.wysq.yunxin.model;

import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;

import org.json.JSONObject;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/1 20:05
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public abstract class CustomAttachment implements MsgAttachment {

    protected int type;

    CustomAttachment(int type) {
        this.type = type;
    }

    public void fromJson(com.alibaba.fastjson.JSONObject data) {
        if (data != null) {
            parseData(data);
        }
    }

    @Override
    public String toJson(boolean send) {
        return CustomAttachParser.packData(type, packData());
    }

    public int getType() {
        return type;
    }

    protected abstract void parseData(com.alibaba.fastjson.JSONObject data);
    protected abstract com.alibaba.fastjson.JSONObject packData();
}
