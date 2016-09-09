package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetUserBrowseModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.widget.CustomImageView;
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
public class GetHisVisitorAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetUserBrowseModel.BodyEntity.DataListEntity> shop;
    private AdapterInterface mAdapterInterface;
    private RelativeLayout.LayoutParams params;
    private int typeId;

    public GetHisVisitorAdapter(Context context, int widht, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.shop = new ArrayList<>();
        params = new RelativeLayout.LayoutParams(widht - DensityUtil.dp2px(context, 30),
                (widht - DensityUtil.dp2px(context, 30)) * 200 / 320);
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void addBody(List<GetUserBrowseModel.BodyEntity.DataListEntity> shop) {
        this.shop.addAll(shop);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public GetUserBrowseModel.BodyEntity.DataListEntity getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_hisvisitor_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetUserBrowseModel.BodyEntity.DataListEntity mBodyEntity = shop.get(position);

        if (mBodyEntity.getSex().equals("M")) {
            holder.itemBrowseGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            holder.itemBrowseGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }

        if (mBodyEntity.getVerify_status() == 1) {
            holder.itemBrowseVerifyImg.setVisibility(View.VISIBLE);
            holder.itemBrowseGenderIv.setVisibility(View.GONE);
        } else {
            holder.itemBrowseVerifyImg.setVisibility(View.GONE);
            holder.itemBrowseGenderIv.setVisibility(View.VISIBLE);
        }

        ImageLoader.getInstance().displayImage(Common.ImageUrl + mBodyEntity.getIcon(),
                holder.itemBrowseHeadIv, MyBaseApplication.getApplication().getOptionsNot());

        holder.itemBrowseNameTx.setText(mBodyEntity.getNick_name());
        holder.itemBrowseLevelTx.setText("LV." + mBodyEntity.getUserLevel());
        if (mBodyEntity.getVip_level() == 0) {
            holder.itemBrowseVipTx.setVisibility(View.GONE);
            holder.itemBrowseSvipIv.setVisibility(View.GONE);
        } else {
            holder.itemBrowseVipTx.setVisibility(View.VISIBLE);
            if (mBodyEntity.getVip_level()>=4) {
                holder.itemBrowseVipTx.setVisibility(View.GONE);
                holder.itemBrowseSvipIv.setVisibility(View.VISIBLE);
            } else {
                holder.itemBrowseSvipIv.setVisibility(View.GONE);
                holder.itemBrowseVipTx.setVisibility(View.VISIBLE);
                holder.itemBrowseVipTx.setText("VIP." + mBodyEntity.getVip_level());
            }
        }
        holder.itemBrowseTimeTx.setText("到访时间：" + MyTools.convertTimeS(mBodyEntity.getCreate_time()));

        holder.itemBrowseRootRl.setOnClickListener(new View.OnClickListener() {
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
        @Bind(R.id.item_browse_head_iv)
        CustomImageView itemBrowseHeadIv;
        @Bind(R.id.item_browse_gender_iv)
        ImageView itemBrowseGenderIv;
        @Bind(R.id.item_browse_svip_iv)
        ImageView itemBrowseSvipIv;
        @Bind(R.id.item_browse_verify_img)
        CustomImageView itemBrowseVerifyImg;
        @Bind(R.id.item_browse_head_rl)
        RelativeLayout itemBrowseHeadRl;
        @Bind(R.id.item_browse_name_tx)
        TextView itemBrowseNameTx;
        @Bind(R.id.item_browse_level_tx)
        TextView itemBrowseLevelTx;
        @Bind(R.id.item_browse_vip_tx)
        TextView itemBrowseVipTx;
        @Bind(R.id.item_browse_ll1)
        LinearLayout itemBrowseLl1;
        @Bind(R.id.item_browse_time_tx)
        TextView itemBrowseTimeTx;
        @Bind(R.id.item_browse_root_rl)
        RelativeLayout itemBrowseRootRl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
