package com.atman.wysq.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by vavid on 2016/9/19.
 */
@Entity
public class TouChuanOtherNotice {

    @Id(autoincrement = true)
    private Long id;
    private int noticeType;
    private String drawingImage;
    private long send_userId;
    private String time;
    private int propTouchY;
    private int propTouchX;
    private int giftId;
    private String giftMessage;
    private String send_avatar;
    private String send_nickName;
    private int changeBackImageIndex;
    private long receive_userId;
    private int propId;
    private int callType;
    private String receive_nickName;
    private String propMessage;
    private int grabType;
    private int addfriendType;
    private String receive_avatar;
    private int isRead;
    private boolean isEmbalmed;
    public boolean getIsEmbalmed() {
        return this.isEmbalmed;
    }
    public void setIsEmbalmed(boolean isEmbalmed) {
        this.isEmbalmed = isEmbalmed;
    }
    public int getIsRead() {
        return this.isRead;
    }
    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
    public String getReceive_avatar() {
        return this.receive_avatar;
    }
    public void setReceive_avatar(String receive_avatar) {
        this.receive_avatar = receive_avatar;
    }
    public int getAddfriendType() {
        return this.addfriendType;
    }
    public void setAddfriendType(int addfriendType) {
        this.addfriendType = addfriendType;
    }
    public int getGrabType() {
        return this.grabType;
    }
    public void setGrabType(int grabType) {
        this.grabType = grabType;
    }
    public String getPropMessage() {
        return this.propMessage;
    }
    public void setPropMessage(String propMessage) {
        this.propMessage = propMessage;
    }
    public String getReceive_nickName() {
        return this.receive_nickName;
    }
    public void setReceive_nickName(String receive_nickName) {
        this.receive_nickName = receive_nickName;
    }
    public int getCallType() {
        return this.callType;
    }
    public void setCallType(int callType) {
        this.callType = callType;
    }
    public int getPropId() {
        return this.propId;
    }
    public void setPropId(int propId) {
        this.propId = propId;
    }
    public long getReceive_userId() {
        return this.receive_userId;
    }
    public void setReceive_userId(long receive_userId) {
        this.receive_userId = receive_userId;
    }
    public int getChangeBackImageIndex() {
        return this.changeBackImageIndex;
    }
    public void setChangeBackImageIndex(int changeBackImageIndex) {
        this.changeBackImageIndex = changeBackImageIndex;
    }
    public String getSend_nickName() {
        return this.send_nickName;
    }
    public void setSend_nickName(String send_nickName) {
        this.send_nickName = send_nickName;
    }
    public String getSend_avatar() {
        return this.send_avatar;
    }
    public void setSend_avatar(String send_avatar) {
        this.send_avatar = send_avatar;
    }
    public String getGiftMessage() {
        return this.giftMessage;
    }
    public void setGiftMessage(String giftMessage) {
        this.giftMessage = giftMessage;
    }
    public int getGiftId() {
        return this.giftId;
    }
    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }
    public int getPropTouchX() {
        return this.propTouchX;
    }
    public void setPropTouchX(int propTouchX) {
        this.propTouchX = propTouchX;
    }
    public int getPropTouchY() {
        return this.propTouchY;
    }
    public void setPropTouchY(int propTouchY) {
        this.propTouchY = propTouchY;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public long getSend_userId() {
        return this.send_userId;
    }
    public void setSend_userId(long send_userId) {
        this.send_userId = send_userId;
    }
    public String getDrawingImage() {
        return this.drawingImage;
    }
    public void setDrawingImage(String drawingImage) {
        this.drawingImage = drawingImage;
    }
    public int getNoticeType() {
        return this.noticeType;
    }
    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 483648170)
    public TouChuanOtherNotice(Long id, int noticeType, String drawingImage,
            long send_userId, String time, int propTouchY, int propTouchX,
            int giftId, String giftMessage, String send_avatar,
            String send_nickName, int changeBackImageIndex, long receive_userId,
            int propId, int callType, String receive_nickName, String propMessage,
            int grabType, int addfriendType, String receive_avatar, int isRead,
            boolean isEmbalmed) {
        this.id = id;
        this.noticeType = noticeType;
        this.drawingImage = drawingImage;
        this.send_userId = send_userId;
        this.time = time;
        this.propTouchY = propTouchY;
        this.propTouchX = propTouchX;
        this.giftId = giftId;
        this.giftMessage = giftMessage;
        this.send_avatar = send_avatar;
        this.send_nickName = send_nickName;
        this.changeBackImageIndex = changeBackImageIndex;
        this.receive_userId = receive_userId;
        this.propId = propId;
        this.callType = callType;
        this.receive_nickName = receive_nickName;
        this.propMessage = propMessage;
        this.grabType = grabType;
        this.addfriendType = addfriendType;
        this.receive_avatar = receive_avatar;
        this.isRead = isRead;
        this.isEmbalmed = isEmbalmed;
    }
    @Generated(hash = 502206853)
    public TouChuanOtherNotice() {
    }
}
