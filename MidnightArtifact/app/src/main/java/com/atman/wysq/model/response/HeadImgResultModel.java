package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/12 15:08
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class HeadImgResultModel {
    /**
     * name : small_4329570790540159940.jpg
     * size : 0
     * successful : false
     */

    private List<FilesEntity> files;

    public List<FilesEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FilesEntity> files) {
        this.files = files;
    }

    public static class FilesEntity {
        private String name;
        private int size;
        private boolean successful;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public boolean isSuccessful() {
            return successful;
        }

        public void setSuccessful(boolean successful) {
            this.successful = successful;
        }
    }
}
