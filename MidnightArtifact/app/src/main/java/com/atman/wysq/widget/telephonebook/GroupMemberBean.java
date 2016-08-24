package com.atman.wysq.widget.telephonebook;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/5/26 16:03
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GroupMemberBean {

    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母
    private boolean isSelect;
    private String mobile;
    private boolean sex;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
