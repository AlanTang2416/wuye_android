package com.atman.wysq.model.request;

import android.graphics.Bitmap;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/3 16:46
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PostContentModel {
    private int id;
    private String content;
    private String localUrl;
    private String netUrl;
    private Bitmap bitmap;

    public PostContentModel(int id, String localUrl, String netUrl, String content, Bitmap bitmap) {
        this.content = content;
        this.id = id;
        this.localUrl = localUrl;
        this.netUrl = netUrl;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getNetUrl() {
        return netUrl;
    }

    public void setNetUrl(String netUrl) {
        this.netUrl = netUrl;
    }
}
