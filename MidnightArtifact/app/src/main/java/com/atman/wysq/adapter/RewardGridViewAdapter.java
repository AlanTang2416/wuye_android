package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetRewardListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.widget.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/12 13:44
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class RewardGridViewAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetRewardListModel.BodyEntity> shop;

    public RewardGridViewAdapter(Context context, List<GetRewardListModel.BodyEntity> shop) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.shop = shop;
    }

    @Override
    public int getCount() {
        if (shop.size() >= 6) {
            return 7;
        } else {
            return shop.size() + 1;
        }
    }

    @Override
    public GetRewardListModel.BodyEntity getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_rewardgridview_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (shop.size() <= 6) {
            if (position == shop.size()) {
                holder.itemRewardlistMoreImg.setVisibility(View.VISIBLE);
                holder.itemRewardlistHeadImg.setVisibility(View.GONE);
            } else {
                holder.itemRewardlistHeadImg.setVisibility(View.VISIBLE);
                holder.itemRewardlistMoreImg.setVisibility(View.GONE);
                ImageLoader.getInstance().displayImage(Common.ImageUrl + shop.get(position).getIcon()
                        , holder.itemRewardlistHeadImg, MyBaseApplication.getApp().getOptionsNot());
            }
        } else {
            if (position < 6) {
                holder.itemRewardlistMoreImg.setVisibility(View.GONE);
                holder.itemRewardlistHeadImg.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(Common.ImageUrl + shop.get(position).getIcon()
                        , holder.itemRewardlistHeadImg, MyBaseApplication.getApp().getOptionsNot());
            } else {
                holder.itemRewardlistMoreImg.setVisibility(View.VISIBLE);
                holder.itemRewardlistHeadImg.setVisibility(View.GONE);
            }
        }


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_rewardlist_head_img)
        RoundImageView itemRewardlistHeadImg;
        @Bind(R.id.item_rewardlist_more_img)
        ImageView itemRewardlistMoreImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
