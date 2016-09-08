package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GiftListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/8 11:59
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class SelectGiftAdapter extends BaseAdapter {

    private Context context;
    private List<GiftListModel.BodyEntity> data;
    private AdapterInterface mAdapterInterface;
    protected LayoutInflater layoutInflater;
    private ViewHolder holder;
    private LinearLayout.LayoutParams params;
    private int myCion;

    public SelectGiftAdapter(Context context,int mWight, List<GiftListModel.BodyEntity> data, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.data = data;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        int w = (mWight - DensityUtil.dp2px(context,15))/2*2/3;
        params = new LinearLayout.LayoutParams(w, w*272/470);
    }

    public void setMyCion(int myCion) {
        this.myCion = myCion;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (data.size()>=6) {
            return 6;
        } else {
            return data.size();
        }
    }

    @Override
    public GiftListModel.BodyEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_selectgift_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GiftListModel.BodyEntity temp = data.get(position);

        holder.itemSelectgiftCionTv.setText("兑换:"+temp.getPrice()+"金币");
        holder.itemSelectgiftMeiliTv.setText("魅力值:+"+temp.getCharm());
        holder.itemSelectgiftNameTv.setText(temp.getName());
        holder.itemSelectgiftIv.setLayoutParams(params);
        if (temp.getPrice() < myCion) {
            ImageLoader.getInstance().displayImage(Common.ImageUrl+temp.getPic_url(),holder.itemSelectgiftIv
                    , MyBaseApplication.getApplication().getOptionsNot());
        } else {
            ImageLoader.getInstance().displayImage(Common.ImageUrl+temp.getGray_pic_url(),holder.itemSelectgiftIv
                    , MyBaseApplication.getApplication().getOptionsNot());
        }

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_selectgift_iv)
        ImageView itemSelectgiftIv;
        @Bind(R.id.item_selectgift_cion_tv)
        TextView itemSelectgiftCionTv;
        @Bind(R.id.item_selectgift_name_tv)
        TextView itemSelectgiftNameTv;
        @Bind(R.id.item_selectgift_meili_tv)
        TextView itemSelectgiftMeiliTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
