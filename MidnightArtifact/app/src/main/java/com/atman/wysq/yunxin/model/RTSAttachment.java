package com.atman.wysq.yunxin.model;

import com.alibaba.fastjson.JSONObject;
import com.atman.wysq.R;
import com.atman.wysq.yunxin.DemoCache;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/1 20:24
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class RTSAttachment extends CustomAttachment {

    private byte flag;

    public RTSAttachment() {
        super(CustomAttachmentType.RTS);
    }

    public RTSAttachment(byte flag) {
        this();
        this.flag = flag;
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("flag", flag);
        return data;
    }

    @Override
    protected void parseData(JSONObject data) {
        flag = data.getByte("flag");
    }

    public byte getFlag() {
        return flag;
    }

    public String getContent() {
        return DemoCache.getContext().getString(getFlag() == 0 ? R.string.start_session_record : R.string
                .session_end_record);
    }
}
