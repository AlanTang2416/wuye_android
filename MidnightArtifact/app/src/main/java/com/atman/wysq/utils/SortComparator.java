package com.atman.wysq.utils;

import com.atman.wysq.model.response.GiftListModel;

import java.util.Comparator;

/**
 * Created by vavid on 2016/9/13.
 */
public class SortComparator implements Comparator {
    @Override
    public int compare(Object lhs, Object rhs) {
        GiftListModel.BodyEntity a = (GiftListModel.BodyEntity) lhs;
        GiftListModel.BodyEntity b = (GiftListModel.BodyEntity) rhs;

        return (a.getPrice() - b.getPrice());
    }
}
