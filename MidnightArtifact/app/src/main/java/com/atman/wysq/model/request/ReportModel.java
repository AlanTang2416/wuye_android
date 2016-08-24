package com.atman.wysq.model.request;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/16 15:23
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ReportModel {

    private String content;
    private int type;
    private int obj_id;

    public ReportModel(String content, int type, int obj_id) {
        this.content = content;
        this.obj_id = obj_id;
        this.type = type;
    }
}
