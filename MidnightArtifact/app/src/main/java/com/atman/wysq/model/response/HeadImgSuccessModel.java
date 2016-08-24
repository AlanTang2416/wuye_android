package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/13 16:56
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class HeadImgSuccessModel {
    /**
     * name : small_5494724513635041485.jpg
     * size : 0
     * successful : true
     * url : /imageServer/E9D0eefabd8e1cb34f8f80cbd3b8ede2dfaa.jpg
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
        private String url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
