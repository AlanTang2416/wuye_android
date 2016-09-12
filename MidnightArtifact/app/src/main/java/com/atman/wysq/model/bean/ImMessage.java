package com.atman.wysq.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/2 13:25
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
@Entity
public class ImMessage {
    @Id(autoincrement = true)
    private Long id;
    private String uuid;
    private String loginUserId;
    private String chatId;
    private String userId;
    private String nickName;
    private String icon;
    private String sex;
    private int verify_status;
    private boolean isSelfSend;
    private long time;
    private int contentType;
    private String content;
    private String imageFilePath;
    private String imageUrl;
    private String imageThumUrl;
    private String imageSFilePath;
    private String imageSUrl;
    private String imageSThumUrl;
    private String audioFilePath;
    private String audioUrl;
    private long audioDuration;
    private int fingerValue;
    private boolean isGiftMessage;
    private int isSeedSuccess; //0:发送中，1：成功，2:失败
    public int getIsSeedSuccess() {
        return this.isSeedSuccess;
    }
    public void setIsSeedSuccess(int isSeedSuccess) {
        this.isSeedSuccess = isSeedSuccess;
    }
    public boolean getIsGiftMessage() {
        return this.isGiftMessage;
    }
    public void setIsGiftMessage(boolean isGiftMessage) {
        this.isGiftMessage = isGiftMessage;
    }
    public int getFingerValue() {
        return this.fingerValue;
    }
    public void setFingerValue(int fingerValue) {
        this.fingerValue = fingerValue;
    }
    public long getAudioDuration() {
        return this.audioDuration;
    }
    public void setAudioDuration(long audioDuration) {
        this.audioDuration = audioDuration;
    }
    public String getAudioUrl() {
        return this.audioUrl;
    }
    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
    public String getAudioFilePath() {
        return this.audioFilePath;
    }
    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }
    public String getImageSThumUrl() {
        return this.imageSThumUrl;
    }
    public void setImageSThumUrl(String imageSThumUrl) {
        this.imageSThumUrl = imageSThumUrl;
    }
    public String getImageSUrl() {
        return this.imageSUrl;
    }
    public void setImageSUrl(String imageSUrl) {
        this.imageSUrl = imageSUrl;
    }
    public String getImageSFilePath() {
        return this.imageSFilePath;
    }
    public void setImageSFilePath(String imageSFilePath) {
        this.imageSFilePath = imageSFilePath;
    }
    public String getImageThumUrl() {
        return this.imageThumUrl;
    }
    public void setImageThumUrl(String imageThumUrl) {
        this.imageThumUrl = imageThumUrl;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageFilePath() {
        return this.imageFilePath;
    }
    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getContentType() {
        return this.contentType;
    }
    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public boolean getIsSelfSend() {
        return this.isSelfSend;
    }
    public void setIsSelfSend(boolean isSelfSend) {
        this.isSelfSend = isSelfSend;
    }
    public int getVerify_status() {
        return this.verify_status;
    }
    public void setVerify_status(int verify_status) {
        this.verify_status = verify_status;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getChatId() {
        return this.chatId;
    }
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
    public String getLoginUserId() {
        return this.loginUserId;
    }
    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 469320086)
    public ImMessage(Long id, String uuid, String loginUserId, String chatId,
            String userId, String nickName, String icon, String sex,
            int verify_status, boolean isSelfSend, long time, int contentType,
            String content, String imageFilePath, String imageUrl,
            String imageThumUrl, String imageSFilePath, String imageSUrl,
            String imageSThumUrl, String audioFilePath, String audioUrl,
            long audioDuration, int fingerValue, boolean isGiftMessage,
            int isSeedSuccess) {
        this.id = id;
        this.uuid = uuid;
        this.loginUserId = loginUserId;
        this.chatId = chatId;
        this.userId = userId;
        this.nickName = nickName;
        this.icon = icon;
        this.sex = sex;
        this.verify_status = verify_status;
        this.isSelfSend = isSelfSend;
        this.time = time;
        this.contentType = contentType;
        this.content = content;
        this.imageFilePath = imageFilePath;
        this.imageUrl = imageUrl;
        this.imageThumUrl = imageThumUrl;
        this.imageSFilePath = imageSFilePath;
        this.imageSUrl = imageSUrl;
        this.imageSThumUrl = imageSThumUrl;
        this.audioFilePath = audioFilePath;
        this.audioUrl = audioUrl;
        this.audioDuration = audioDuration;
        this.fingerValue = fingerValue;
        this.isGiftMessage = isGiftMessage;
        this.isSeedSuccess = isSeedSuccess;
    }
    @Generated(hash = 1511957917)
    public ImMessage() {
    }
}
