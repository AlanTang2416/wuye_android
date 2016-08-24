package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.ConfigModel;
import com.atman.wysq.model.response.GetTaskAllModel;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.LogUtils;
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
public class RechargeListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<ConfigModel.ShopEntity> shop;
    private AdapterInterface mAdapterInterface;

    public RechargeListAdapter(Context context, List<ConfigModel.ShopEntity> shop, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.shop = shop;
    }

    @Override
    public int getCount() {
        if (shop==null) {
            return 0;
        } else {
            return shop.size();
        }
    }

    @Override
    public ConfigModel.ShopEntity getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_recharge_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemRehargeNumTx.setText(shop.get(position).getTitle());
        holder.itemRehargeMoreTx.setText(shop.get(position).getDescription().replace("é\u0080\u0081", "送"));
        holder.itemRehargeBt.setText(shop.get(position).getPrice().replace("Â",""));
        holder.itemRehargeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_reharge_num_tx)
        TextView itemRehargeNumTx;
        @Bind(R.id.item_reharge_more_tx)
        TextView itemRehargeMoreTx;
        @Bind(R.id.item_reharge_bt)
        Button itemRehargeBt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
