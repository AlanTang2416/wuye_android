package com.atman.wysq.yunxin.utils;

import com.atman.wysq.model.response.GetFansModel;

import java.util.Comparator;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/26 09:42
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class FriendsPinyinComparator implements Comparator<GetFansModel.BodyEntity> {

    public int compare(GetFansModel.BodyEntity o1, GetFansModel.BodyEntity o2) {
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
