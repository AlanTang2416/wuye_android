package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetAddressListResponseModel;
import com.base.baselibs.iimp.AdapterInterface;

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
public class AddressManageListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetAddressListResponseModel.BodyEntity> shop;
    private AdapterInterface mAdapterInterface;
    private int selectId = 0;

    public AddressManageListAdapter(Context context, List<GetAddressListResponseModel.BodyEntity> shop,
                                    AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        this.shop = shop;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setSelectId(int addressId) {
        for (int i=0;i<shop.size();i++) {
            if (shop.get(i).getAddress_id()==addressId) {
                this.selectId = i;
            }
        }
    }

    public void setSelectPosition(int position) {
        this.selectId = position;
    }

    public int getSelectId() {
        return selectId;
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public GetAddressListResponseModel.BodyEntity getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_addressmanagelist_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (selectId == position) {
            holder.itemAddressmanagelistSelectIv.setVisibility(View.VISIBLE);
        } else {
            holder.itemAddressmanagelistSelectIv.setVisibility(View.INVISIBLE);
        }
        holder.itemAddressmanagelistTagTx.setText("收货地址"+(position+1));
        holder.itemAddressmanagelistNameTx.setText(shop.get(position).getReceiver_name());
        holder.itemAddressmanagelistPhoneTx.setText(shop.get(position).getReceiver_phone());
        holder.itemAddressmanagelistAddressTx.setText(shop.get(position).getReceiver_area_name()+shop.get(position).getReceiver_address());
        holder.itemAddressmanagelistEditTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });
        holder.itemAddressmanagelistDeleteTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_addressmanagelist_select_iv)
        ImageView itemAddressmanagelistSelectIv;
        @Bind(R.id.item_addressmanagelist_tag_tx)
        TextView itemAddressmanagelistTagTx;
        @Bind(R.id.item_addressmanagelist_edit_tx)
        TextView itemAddressmanagelistEditTx;
        @Bind(R.id.item_addressmanagelist_delete_tx)
        TextView itemAddressmanagelistDeleteTx;
        @Bind(R.id.item_addressmanagelist_name_tx)
        TextView itemAddressmanagelistNameTx;
        @Bind(R.id.item_addressmanagelist_phone_tx)
        TextView itemAddressmanagelistPhoneTx;
        @Bind(R.id.item_addressmanagelist_address_tx)
        TextView itemAddressmanagelistAddressTx;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
