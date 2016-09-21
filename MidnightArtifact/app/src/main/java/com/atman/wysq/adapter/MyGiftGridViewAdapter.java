package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.MyGiftListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.widget.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
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
public class MyGiftGridViewAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<MyGiftListModel.BodyBean.RowsBean> shop;

    public MyGiftGridViewAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.shop = new ArrayList<>();
    }

    public void addShop(List<MyGiftListModel.BodyBean.RowsBean> shop) {
        this.shop.addAll(shop);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public MyGiftListModel.BodyBean.RowsBean getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mygiftgridview_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemMygiftNameTx.setText(shop.get(position).getName());

        if (shop.get(position).getUser_count()==0) {
            holder.itemMygiftNumTx.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+shop.get(position).getGray_pic_url()
                    ,holder.itemMygiftImg, MyBaseApplication.getApplication().getOptionsNot());
        } else {
            holder.itemMygiftNumTx.setVisibility(View.VISIBLE);
            holder.itemMygiftNumTx.setText("x"+shop.get(position).getUser_count());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+shop.get(position).getPic_url()
                    ,holder.itemMygiftImg, MyBaseApplication.getApplication().getOptionsNot());
        }

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_mygift_img)
        ImageView itemMygiftImg;
        @Bind(R.id.item_mygift_name_tx)
        TextView itemMygiftNameTx;
        @Bind(R.id.item_mygift_num_tx)
        TextView itemMygiftNumTx;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
