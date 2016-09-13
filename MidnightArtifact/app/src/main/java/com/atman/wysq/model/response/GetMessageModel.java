package com.atman.wysq.model.response;

import com.atman.wysq.model.request.SeedMessageModel;

/**
 * Created by vavid on 2016/9/13.
 */
public class GetMessageModel {
    private int contentType;
    private int contentFinger;
    private int fingerValue;
    private String contentImageSUrl;
    private SeedMessageModel sendUser;

    public GetMessageModel(int contentType, int contentFinger, String contentImageSUrl, SeedMessageModel sendUser) {
        this.contentType = contentType;
        this.contentFinger = contentFinger;
        this.fingerValue = contentFinger;
        this.contentImageSUrl = contentImageSUrl;
        this.sendUser = sendUser;
    }

    public int getFingerValue() {
        return fingerValue;
    }

    public void setFingerValue(int fingerValue) {
        this.fingerValue = fingerValue;
    }

    public String getContentImageSUrl() {
        return contentImageSUrl;
    }

    public void setContentImageSUrl(String contentImageSUrl) {
        this.contentImageSUrl = contentImageSUrl;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public SeedMessageModel getSendUser() {
        return sendUser;
    }

    public void setSendUser(SeedMessageModel sendUser) {
        this.sendUser = sendUser;
    }

    public int getContentFinger() {
        return contentFinger;
    }

    public void setContentFinger(int contentFinger) {
        this.contentFinger = contentFinger;
    }
}
