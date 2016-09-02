package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetOrderListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
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
public class OrderListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetOrderListModel.BodyEntity> shop;
    private AdapterInterface mAdapterInterface;

    public OrderListAdapter(Context context, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.shop = new ArrayList<>();
    }

    public void addBody(List<GetOrderListModel.BodyEntity> shop) {
        this.shop.addAll(shop);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public GetOrderListModel.BodyEntity getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_orederlist_listview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemOrderlistOrderidTx.setText(shop.get(position).getOrder_id() + "");
        if (shop.get(position).getType() == 1) {
            holder.itemOrderlistStateTx.setText("未支付");
            holder.itemOrderlistStateTx.setTextColor(context.getResources().getColor(R.color.color_red));
            holder.itemOrderlistBt.setText("付款");
        } else if (shop.get(position).getType() == 2) {
            holder.itemOrderlistStateTx.setText("已支付");
            holder.itemOrderlistStateTx.setTextColor(context.getResources().getColor(R.color.color_blue));
            holder.itemOrderlistBt.setText("联系客服");
        } else if (shop.get(position).getType() == 3) {
            holder.itemOrderlistStateTx.setText("已发货");
            holder.itemOrderlistStateTx.setTextColor(context.getResources().getColor(R.color.color_blue));
            holder.itemOrderlistBt.setText("联系客服");
        } else if (shop.get(position).getType() == 4) {
            holder.itemOrderlistStateTx.setText("交易取消");
            holder.itemOrderlistStateTx.setTextColor(context.getResources().getColor(R.color.color_red));
            holder.itemOrderlistBt.setText("联系客服");
        } else if (shop.get(position).getType() == 5) {
            holder.itemOrderlistStateTx.setText("交易成功");
            holder.itemOrderlistStateTx.setTextColor(context.getResources().getColor(R.color.color_blue));
            holder.itemOrderlistBt.setText("联系客服");
        } else if (shop.get(position).getType() == 5) {
            holder.itemOrderlistStateTx.setText("申请退换");
            holder.itemOrderlistStateTx.setTextColor(context.getResources().getColor(R.color.color_red));
            holder.itemOrderlistBt.setText("联系客服");
        } else {
            holder.itemOrderlistStateTx.setText("未知");
            holder.itemOrderlistStateTx.setTextColor(context.getResources().getColor(R.color.color_red));
            holder.itemOrderlistBt.setText("联系客服");
        }

        if (shop.get(position).getItems().size() > 0) {
            if (shop.get(position).getOrder_type()==51) {
                holder.itemOrderlistHeadIv.setImageResource(R.mipmap.jinbin);
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl + shop.get(position).getItems().get(0).getGoods_pic()
                        , holder.itemOrderlistHeadIv, MyBaseApplication.getApplication().getOptionsNot());
            }
            holder.itemOrderlistNameTx.setText(shop.get(position).getItems().get(0).getGoods_title());
            holder.itemOrderlistNumTx.setText("数量：" + shop.get(position).getItems().get(0).getAmount());
            holder.itemOrderlistPriceTx.setText("单价：¥ " + shop.get(position).getItems().get(0).getPrice());
        }
        holder.itemOrderlistTotalpriceTx.setText("总金额：¥ " + shop.get(position).getTotal_price());

        if (shop.get(position).getOrder_type()==51) {
            holder.itemOrderlistGoldIv.setVisibility(View.VISIBLE);
        } else {
            holder.itemOrderlistGoldIv.setVisibility(View.INVISIBLE);
        }

        holder.itemOrderlistBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        holder.itemOrderlistRootLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        return convertView;
    }

    public void clearData() {
        shop.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_orderlist_orderid_tx)
        TextView itemOrderlistOrderidTx;
        @Bind(R.id.item_orderlist_gold_iv)
        ImageView itemOrderlistGoldIv;
        @Bind(R.id.item_orderlist_state_tx)
        TextView itemOrderlistStateTx;
        @Bind(R.id.item_orderlist_head_iv)
        ImageView itemOrderlistHeadIv;
        @Bind(R.id.item_orderlist_name_tx)
        TextView itemOrderlistNameTx;
        @Bind(R.id.item_orderlist_price_tx)
        TextView itemOrderlistPriceTx;
        @Bind(R.id.item_orderlist_num_tx)
        TextView itemOrderlistNumTx;
        @Bind(R.id.item_orderlist_totalprice_tx)
        TextView itemOrderlistTotalpriceTx;
        @Bind(R.id.item_orderlist_bt)
        Button itemOrderlistBt;
        @Bind(R.id.item_orderlist_root_ll)
        LinearLayout itemOrderlistRootLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
