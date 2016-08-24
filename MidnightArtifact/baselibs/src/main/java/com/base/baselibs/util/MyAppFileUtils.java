package com.base.baselibs.util;

import java.io.File;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/30 11:29
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyAppFileUtils {
    public final String fileName = "MidnightArtifact";
    public final String fileHttpCacheName = "okHttpCache";

    public String getMyAppFilePath(){
        File file = new File(new SDcardUtils().getAbsolutePath() + File.separator + fileName);
        if (!file.exists()) {
            // 创建文件的所有完整路径
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public String getMyAppHttpCacheFilePath(){
        File file = new File(new SDcardUtils().getAbsolutePath() + File.separator + fileName + File.separator + fileHttpCacheName);
        if (!file.exists()) {
            // 创建文件的所有完整路径
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public File getMyAppHttpCacheFile(){
        File file = new File(new SDcardUtils().getAbsolutePath() + File.separator + fileName + File.separator + fileHttpCacheName);
        if (!file.exists()) {
            // 创建文件的所有完整路径
            file.mkdirs();
        }
        return file;
    }
}
