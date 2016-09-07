package com.base.baselibs.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/6 11:07
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ThumbnailQuery {

    private static final String TAG = ThumbnailQuery.class.getSimpleName();

    public static String queryImageThumbnailByPath(Context context, String path) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[] { MediaStore.Images.Media._ID };
        String selection = MediaStore.Images.Media.DATA + " = ? ";
        String[] selectionArgs = new String[] { path };

        Cursor cursor = query(context, uri, projection, selection,
                selectionArgs);
        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        }
        cursor.close();
        if (id == -1) {
            return null;
        }

        uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        projection = new String[] { MediaStore.Images.Thumbnails.DATA };
        selection = MediaStore.Images.Thumbnails.IMAGE_ID + " = ? ";
        selectionArgs = new String[] { String.valueOf(id) };

        cursor = query(context, uri, projection, selection, selectionArgs);
        String thumbnail = null;
        if (cursor.moveToFirst()) {
            int idxData = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
            thumbnail = cursor.getString(idxData);
        }
        cursor.close();
        return thumbnail;
    }

    public static String queryVideoThumbnailByPath(Context context, String path) {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[] { MediaStore.Video.Media._ID };
        String selection = MediaStore.Video.Media.DATA + " = ? ";
        String[] selectionArgs = new String[] { path };

        Cursor cursor = query(context, uri, projection, selection,
                selectionArgs);
        int mediaId = -1;
        if (cursor.moveToFirst()) {
            int idxId = cursor.getColumnIndex(MediaStore.Video.Media._ID);
            mediaId = cursor.getInt(idxId);
        }
        cursor.close();
        if (mediaId == -1) {
            return null;
        }

        uri = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
        projection = new String[] { MediaStore.Video.Thumbnails.DATA };
        selection = MediaStore.Video.Thumbnails.VIDEO_ID + " =  ? ";
        selectionArgs = new String[] { String.valueOf(mediaId) };

        cursor = query(context, uri, projection, selection, selectionArgs);
        String thumbnail = null;
        if (cursor.moveToFirst()) {
            int idxData = cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA);
            thumbnail = cursor.getString(idxData);
        }
        cursor.close();
        return thumbnail;
    }

    private static Cursor query(Context context, Uri uri, String[] projection,
                                String selection, String[] selectionArgs) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(uri, projection, selection, selectionArgs,
                null);
        return cursor;
    }
}
