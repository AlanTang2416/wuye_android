package com.atman.wysq.model.response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/7 17:46
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ChatAudioModel {
    /**
     * md5 : 30be1572f06dcc6d7d249103c3cfdf06
     * url : http://nim.nos.netease.com/MTAyMTUyMQ==/bmltYV81ODQ0NzAyNl8xNDczMjM5NzUyNTA3X2ZkMGJlZjMyLWE2NjktNDcwNy04ODlhLWU3YThiZDJjY2Y0OA==
     * size : 14669
     * ext : aac
     * dur : 4272
     */

    private String md5;
    private String url;
    private int size;
    private String ext;
    private int dur;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public int getDur() {
        return dur/1000;
    }

    public void setDur(int dur) {
        this.dur = dur;
    }
}
