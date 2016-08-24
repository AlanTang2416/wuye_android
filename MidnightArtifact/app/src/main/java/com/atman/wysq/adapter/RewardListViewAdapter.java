package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetRewardListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.base.baselibs.widget.CustomImageView;
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
public class RewardListViewAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetRewardListModel.BodyEntity> shop;

    public RewardListViewAdapter(Context context, List<GetRewardListModel.BodyEntity> shop) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.shop = shop;
    }

    @Override
    public int getCount() {
        return shop.size();
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
            convertView = layoutInflater.inflate(R.layout.item_rewardlistview_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(Common.ImageUrl+shop.get(position).getIcon()
                , holder.itemRewardlistviewHeadImg, MyBaseApplication.getApp().getOptionsNot());
        holder.itemRewardlistviewNameTv.setText(shop.get(position).getNick_name());
        holder.itemRewardlistviewLevelTx.setText("Lv "+shop.get(position).getUserLevel());
        holder.itemRewardlistviewTimeTv.setText(MyTools.convertTimeS(shop.get(position).getCreate_time()));
        holder.itemRewardlistviewNumTv.setText("  "+shop.get(position).getUser_award_gold_num());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_rewardlistview_head_img)
        CustomImageView itemRewardlistviewHeadImg;
        @Bind(R.id.item_rewardlistview_name_tv)
        TextView itemRewardlistviewNameTv;
        @Bind(R.id.item_rewardlistview_level_tx)
        TextView itemRewardlistviewLevelTx;
        @Bind(R.id.item_rewardlistview_name_ll)
        LinearLayout itemRewardlistviewNameLl;
        @Bind(R.id.item_rewardlistview_time_tv)
        TextView itemRewardlistviewTimeTv;
        @Bind(R.id.item_rewardlistview_num_tv)
        TextView itemRewardlistviewNumTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
