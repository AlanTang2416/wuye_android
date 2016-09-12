package com.atman.wysq.model.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.atman.wysq.model.bean.ImSession;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "IM_SESSION".
*/
public class ImSessionDao extends AbstractDao<ImSession, String> {

    public static final String TABLENAME = "IM_SESSION";

    /**
     * Properties of entity ImSession.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UserId = new Property(0, String.class, "userId", true, "USER_ID");
        public final static Property LoginUserId = new Property(1, String.class, "loginUserId", false, "LOGIN_USER_ID");
        public final static Property Content = new Property(2, String.class, "content", false, "CONTENT");
        public final static Property NickName = new Property(3, String.class, "nickName", false, "NICK_NAME");
        public final static Property Icon = new Property(4, String.class, "icon", false, "ICON");
        public final static Property Sex = new Property(5, String.class, "sex", false, "SEX");
        public final static Property Verify_status = new Property(6, int.class, "verify_status", false, "VERIFY_STATUS");
        public final static Property Time = new Property(7, long.class, "time", false, "TIME");
        public final static Property UnreadNum = new Property(8, int.class, "unreadNum", false, "UNREAD_NUM");
    }


    public ImSessionDao(DaoConfig config) {
        super(config);
    }
    
    public ImSessionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"IM_SESSION\" (" + //
                "\"USER_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: userId
                "\"LOGIN_USER_ID\" TEXT," + // 1: loginUserId
                "\"CONTENT\" TEXT," + // 2: content
                "\"NICK_NAME\" TEXT," + // 3: nickName
                "\"ICON\" TEXT," + // 4: icon
                "\"SEX\" TEXT," + // 5: sex
                "\"VERIFY_STATUS\" INTEGER NOT NULL ," + // 6: verify_status
                "\"TIME\" INTEGER NOT NULL ," + // 7: time
                "\"UNREAD_NUM\" INTEGER NOT NULL );"); // 8: unreadNum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"IM_SESSION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ImSession entity) {
        stmt.clearBindings();
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(1, userId);
        }
 
        String loginUserId = entity.getLoginUserId();
        if (loginUserId != null) {
            stmt.bindString(2, loginUserId);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
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
        stmt.bindLong(8, entity.getTime());
        stmt.bindLong(9, entity.getUnreadNum());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ImSession entity) {
        stmt.clearBindings();
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(1, userId);
        }
 
        String loginUserId = entity.getLoginUserId();
        if (loginUserId != null) {
            stmt.bindString(2, loginUserId);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
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
        stmt.bindLong(8, entity.getTime());
        stmt.bindLong(9, entity.getUnreadNum());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ImSession readEntity(Cursor cursor, int offset) {
        ImSession entity = new ImSession( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // userId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // loginUserId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // content
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nickName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // icon
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // sex
            cursor.getInt(offset + 6), // verify_status
            cursor.getLong(offset + 7), // time
            cursor.getInt(offset + 8) // unreadNum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ImSession entity, int offset) {
        entity.setUserId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setLoginUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNickName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIcon(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSex(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setVerify_status(cursor.getInt(offset + 6));
        entity.setTime(cursor.getLong(offset + 7));
        entity.setUnreadNum(cursor.getInt(offset + 8));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ImSession entity, long rowId) {
        return entity.getUserId();
    }
    
    @Override
    public String getKey(ImSession entity) {
        if(entity != null) {
            return entity.getUserId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ImSession entity) {
        return entity.getUserId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
