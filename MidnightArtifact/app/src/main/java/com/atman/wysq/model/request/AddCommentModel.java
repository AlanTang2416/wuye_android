package com.atman.wysq.model.request;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/16 11:10
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class AddCommentModel {
    private int blog_id;
    private String content;

    public AddCommentModel(int blog_id, String content) {
        this.blog_id = blog_id;
        this.content = content;
    }
}
