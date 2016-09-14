package com.atman.wysq.model.response;

import com.atman.wysq.model.request.SeedMessageModel;

/**
 * Created by vavid on 2016/9/13.
 */
public class GetMessageTwoModel {
    private int contentType;
    private String contentFinger;
    private String fingerValue;
    private String contentImageSUrl;
    private SeedMessageModel sendUser;

    public GetMessageTwoModel(int contentType, String contentFinger, String contentImageSUrl, SeedMessageModel sendUser) {
        this.contentType = contentType;
        this.contentFinger = contentFinger;
        this.fingerValue = contentFinger;
        this.contentImageSUrl = contentImageSUrl;
        this.sendUser = sendUser;
    }

    public String getFingerValue() {
        return fingerValue;
    }

    public void setFingerValue(String fingerValue) {
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

    public String getContentFinger() {
        return contentFinger;
    }

    public void setContentFinger(String contentFinger) {
        this.contentFinger = contentFinger;
    }
}
