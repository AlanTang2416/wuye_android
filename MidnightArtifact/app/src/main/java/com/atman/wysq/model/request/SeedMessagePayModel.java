package com.atman.wysq.model.request;

/**
 * Created by vavid on 2016/9/14.
 */
public class SeedMessagePayModel {
    private long receiver_id;
    private String content;

    public SeedMessagePayModel(long receiver_id, String content) {
        this.receiver_id = receiver_id;
        this.content = content;
    }
}
