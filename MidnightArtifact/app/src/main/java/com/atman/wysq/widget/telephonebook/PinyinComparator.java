package com.atman.wysq.widget.telephonebook;

import java.util.Comparator;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/5/26 16:05
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PinyinComparator implements Comparator<GroupMemberBean> {

    public int compare(GroupMemberBean o1, GroupMemberBean o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
