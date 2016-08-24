package com.atman.wysq.model.request;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/4 17:18
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class AddPostModel {
    private int blog_board_id;
    private int anonymity;
    private String content;
    private String title;

    public AddPostModel(int blog_board_id, String content, String title, int anonymity) {
        this.blog_board_id = blog_board_id;
        this.content = content;
        this.title = title;
        this.anonymity = anonymity;
    }
}
