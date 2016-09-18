package com.atman.wysq.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by vavid on 2016/9/18.
 */
@Entity
public class AddFriendRecord {
    @Id(autoincrement = true)
    private Long id;
    private String toRequest;
    private String fromRequest;
    public String getFromRequest() {
        return this.fromRequest;
    }
    public void setFromRequest(String fromRequest) {
        this.fromRequest = fromRequest;
    }
    public String getToRequest() {
        return this.toRequest;
    }
    public void setToRequest(String toRequest) {
        this.toRequest = toRequest;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1385443705)
    public AddFriendRecord(Long id, String toRequest, String fromRequest) {
        this.id = id;
        this.toRequest = toRequest;
        this.fromRequest = fromRequest;
    }
    @Generated(hash = 898879267)
    public AddFriendRecord() {
    }
}
