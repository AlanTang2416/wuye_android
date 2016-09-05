package com.atman.wysq.model.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.atman.wysq.model.bean.ImMessage;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "IM_MESSAGE".
*/
public class ImMessageDao extends AbstractDao<ImMessage, String> {

    public static final String TABLENAME = "IM_MESSAGE";

    /**
     * Properties of entity ImMessage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Uuid = new Property(0, String.class, "uuid", true, "UUID");
        public final static Property ChatId = new Property(1, String.class, "chatId", false, "CHAT_ID");
        public final static Property UserId = new Property(2, String.class, "userId", false, "USER_ID");
        public final static Property NickName = new Property(3, String.class, "nickName", false, "NICK_NAME");
        public final static Property Icon = new Property(4, String.class, "icon", false, "ICON");
        public final static Property Sex = new Property(5, String.class, "sex", false, "SEX");
        public final static Property Verify_status = new Property(6, int.class, "verify_status", false, "VERIFY_STATUS");
        public final static Property IsSelfSend = new Property(7, boolean.class, "isSelfSend", false, "IS_SELF_SEND");
        public final static Property Time = new Property(8, long.class, "time", false, "TIME");
        public final static Property ContentType = new Property(9, int.class, "contentType", false, "CONTENT_TYPE");
        public final static Property Content = new Property(10, String.class, "content", false, "CONTENT");
        public final static Property ImageFilePath = new Property(11, String.class, "imageFilePath", false, "IMAGE_FILE_PATH");
        public final static Property ImageUrl = new Property(12, String.class, "imageUrl", false, "IMAGE_URL");
        public final static Property ImageThumUrl = new Property(13, String.class, "imageThumUrl", false, "IMAGE_THUM_URL");
        public final static Property ImageSFilePath = new Property(14, String.class, "imageSFilePath", false, "IMAGE_SFILE_PATH");
        public final static Property ImageSUrl = new Property(15, String.class, "imageSUrl", false, "IMAGE_SURL");
        public final static Property ImageSThumUrl = new Property(16, String.class, "imageSThumUrl", false, "IMAGE_STHUM_URL");
        public final static Property AudioFilePath = new Property(17, String.class, "audioFilePath", false, "AUDIO_FILE_PATH");
        public final static Property AudioUrl = new Property(18, String.class, "audioUrl", false, "AUDIO_URL");
        public final static Property AudioDuration = new Property(19, long.class, "audioDuration", false, "AUDIO_DURATION");
        public final static Property FingerValue = new Property(20, int.class, "fingerValue", false, "FINGER_VALUE");
        public final static Property IsGiftMessage = new Property(21, boolean.class, "isGiftMessage", false, "IS_GIFT_MESSAGE");
        public final static Property IsSeedSuccess = new Property(22, int.class, "isSeedSuccess", false, "IS_SEED_SUCCESS");
    }


    public ImMessageDao(DaoConfig config) {
        super(config);
    }
    
    public ImMessageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"IM_MESSAGE\" (" + //
                "\"UUID\" TEXT PRIMARY KEY NOT NULL ," + // 0: uuid
                "\"CHAT_ID\" TEXT," + // 1: chatId
                "\"USER_ID\" TEXT," + // 2: userId
                "\"NICK_NAME\" TEXT," + // 3: nickName
                "\"ICON\" TEXT," + // 4: icon
                "\"SEX\" TEXT," + // 5: sex
                "\"VERIFY_STATUS\" INTEGER NOT NULL ," + // 6: verify_status
                "\"IS_SELF_SEND\" INTEGER NOT NULL ," + // 7: isSelfSend
                "\"TIME\" INTEGER NOT NULL ," + // 8: time
                "\"CONTENT_TYPE\" INTEGER NOT NULL ," + // 9: contentType
                "\"CONTENT\" TEXT," + // 10: content
                "\"IMAGE_FILE_PATH\" TEXT," + // 11: imageFilePath
                "\"IMAGE_URL\" TEXT," + // 12: imageUrl
                "\"IMAGE_THUM_URL\" TEXT," + // 13: imageThumUrl
                "\"IMAGE_SFILE_PATH\" TEXT," + // 14: imageSFilePath
                "\"IMAGE_SURL\" TEXT," + // 15: imageSUrl
                "\"IMAGE_STHUM_URL\" TEXT," + // 16: imageSThumUrl
                "\"AUDIO_FILE_PATH\" TEXT," + // 17: audioFilePath
                "\"AUDIO_URL\" TEXT," + // 18: audioUrl
                "\"AUDIO_DURATION\" INTEGER NOT NULL ," + // 19: audioDuration
                "\"FINGER_VALUE\" INTEGER NOT NULL ," + // 20: fingerValue
                "\"IS_GIFT_MESSAGE\" INTEGER NOT NULL ," + // 21: isGiftMessage
                "\"IS_SEED_SUCCESS\" INTEGER NOT NULL );"); // 22: isSeedSuccess
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"IM_MESSAGE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ImMessage entity) {
        stmt.clearBindings();
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(1, uuid);
        }
 
        String chatId = entity.getChatId();
        if (chatId != null) {
            stmt.bindString(2, chatId);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(4, nickName);
        }
 
        String icon = entity.getIcon();
        if (icon != null) {
            stmt.bindString(5, icon);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(6, sex);
        }
        stmt.bindLong(7, entity.getVerify_status());
        stmt.bindLong(8, entity.getIsSelfSend() ? 1L: 0L);
        stmt.bindLong(9, entity.getTime());
        stmt.bindLong(10, entity.getContentType());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(11, content);
        }
 
        String imageFilePath = entity.getImageFilePath();
        if (imageFilePath != null) {
            stmt.bindString(12, imageFilePath);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(13, imageUrl);
        }
 
        String imageThumUrl = entity.getImageThumUrl();
        if (imageThumUrl != null) {
            stmt.bindString(14, imageThumUrl);
        }
 
        String imageSFilePath = entity.getImageSFilePath();
        if (imageSFilePath != null) {
            stmt.bindString(15, imageSFilePath);
        }
 
        String imageSUrl = entity.getImageSUrl();
        if (imageSUrl != null) {
            stmt.bindString(16, imageSUrl);
        }
 
        String imageSThumUrl = entity.getImageSThumUrl();
        if (imageSThumUrl != null) {
            stmt.bindString(17, imageSThumUrl);
        }
 
        String audioFilePath = entity.getAudioFilePath();
        if (audioFilePath != null) {
            stmt.bindString(18, audioFilePath);
        }
 
        String audioUrl = entity.getAudioUrl();
        if (audioUrl != null) {
            stmt.bindString(19, audioUrl);
        }
        stmt.bindLong(20, entity.getAudioDuration());
        stmt.bindLong(21, entity.getFingerValue());
        stmt.bindLong(22, entity.getIsGiftMessage() ? 1L: 0L);
        stmt.bindLong(23, entity.getIsSeedSuccess());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ImMessage entity) {
        stmt.clearBindings();
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(1, uuid);
        }
 
        String chatId = entity.getChatId();
        if (chatId != null) {
            stmt.bindString(2, chatId);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(4, nickName);
        }
 
        String icon = entity.getIcon();
        if (icon != null) {
            stmt.bindString(5, icon);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(6, sex);
        }
        stmt.bindLong(7, entity.getVerify_status());
        stmt.bindLong(8, entity.getIsSelfSend() ? 1L: 0L);
        stmt.bindLong(9, entity.getTime());
        stmt.bindLong(10, entity.getContentType());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(11, content);
        }
 
        String imageFilePath = entity.getImageFilePath();
        if (imageFilePath != null) {
            stmt.bindString(12, imageFilePath);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(13, imageUrl);
        }
 
        String imageThumUrl = entity.getImageThumUrl();
        if (imageThumUrl != null) {
            stmt.bindString(14, imageThumUrl);
        }
 
        String imageSFilePath = entity.getImageSFilePath();
        if (imageSFilePath != null) {
            stmt.bindString(15, imageSFilePath);
        }
 
        String imageSUrl = entity.getImageSUrl();
        if (imageSUrl != null) {
            stmt.bindString(16, imageSUrl);
        }
 
        String imageSThumUrl = entity.getImageSThumUrl();
        if (imageSThumUrl != null) {
            stmt.bindString(17, imageSThumUrl);
        }
 
        String audioFilePath = entity.getAudioFilePath();
        if (audioFilePath != null) {
            stmt.bindString(18, audioFilePath);
        }
 
        String audioUrl = entity.getAudioUrl();
        if (audioUrl != null) {
            stmt.bindString(19, audioUrl);
        }
        stmt.bindLong(20, entity.getAudioDuration());
        stmt.bindLong(21, entity.getFingerValue());
        stmt.bindLong(22, entity.getIsGiftMessage() ? 1L: 0L);
        stmt.bindLong(23, entity.getIsSeedSuccess());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ImMessage readEntity(Cursor cursor, int offset) {
        ImMessage entity = new ImMessage( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // uuid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // chatId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nickName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // icon
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // sex
            cursor.getInt(offset + 6), // verify_status
            cursor.getShort(offset + 7) != 0, // isSelfSend
            cursor.getLong(offset + 8), // time
            cursor.getInt(offset + 9), // contentType
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // content
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // imageFilePath
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // imageUrl
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // imageThumUrl
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // imageSFilePath
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // imageSUrl
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // imageSThumUrl
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // audioFilePath
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // audioUrl
            cursor.getLong(offset + 19), // audioDuration
            cursor.getInt(offset + 20), // fingerValue
            cursor.getShort(offset + 21) != 0, // isGiftMessage
            cursor.getInt(offset + 22) // isSeedSuccess
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ImMessage entity, int offset) {
        entity.setUuid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setChatId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNickName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIcon(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSex(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setVerify_status(cursor.getInt(offset + 6));
        entity.setIsSelfSend(cursor.getShort(offset + 7) != 0);
        entity.setTime(cursor.getLong(offset + 8));
        entity.setContentType(cursor.getInt(offset + 9));
        entity.setContent(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setImageFilePath(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setImageUrl(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setImageThumUrl(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setImageSFilePath(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setImageSUrl(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setImageSThumUrl(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setAudioFilePath(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setAudioUrl(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setAudioDuration(cursor.getLong(offset + 19));
        entity.setFingerValue(cursor.getInt(offset + 20));
        entity.setIsGiftMessage(cursor.getShort(offset + 21) != 0);
        entity.setIsSeedSuccess(cursor.getInt(offset + 22));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ImMessage entity, long rowId) {
        return entity.getUuid();
    }
    
    @Override
    public String getKey(ImMessage entity) {
        if(entity != null) {
            return entity.getUuid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ImMessage entity) {
        return entity.getUuid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
