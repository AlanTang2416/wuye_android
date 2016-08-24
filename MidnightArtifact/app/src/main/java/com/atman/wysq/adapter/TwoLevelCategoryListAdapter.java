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
import com.atman.wysq.model.response.TwoLevelCategoryListResponseModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
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
public class TwoLevelCategoryListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<TwoLevelCategoryListResponseModel.BodyEntity> body;
    private AdapterInterface mAdapterInterface;
    private int wight;
    private LinearLayout.LayoutParams params;
    private ImageLoader mImageLoader;

    public TwoLevelCategoryListAdapter(Context context, int wight, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.wight = (wight - DensityUtil.dp2px(context, 6)) / 2;
        this.body = new ArrayList<>();
        params = new LinearLayout.LayoutParams(this.wight,
                this.wight);
        mImageLoader = ImageLoader.getInstance();
    }

    public void addBody(List<TwoLevelCategoryListResponseModel.BodyEntity> body) {
        this.body.addAll(body);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public TwoLevelCategoryListResponseModel.BodyEntity getItem(int position) {
        return body.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_twocategory_gridview_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.twoCategoryIv.setLayoutParams(params);
        holder.twoCategoryIv.setImageResource(R.color.color_white);
        mImageLoader.displayImage(Common.ImageUrl + body.get(position).getPic_img(),
                holder.twoCategoryIv, MyBaseApplication.getApp().getOptionsNot());
        holder.twoCategoryNameTx.setText(body.get(position).getTitle());
        holder.twoCategoryPriceTx.setText("¥ " + body.get(position).getDiscount_price());
        holder.twoCategorySalesvolumeTx.setText("月销量：" + body.get(position).getSales());
        holder.twoCategoryExperienceTx.setText("赠送经验：  " + body.get(position).getIntegral());
        holder.twoCategoryCoinTx.setText("赠送金币：  " + body.get(position).getGold_coin());

        holder.twoCategoryToolIv.setVisibility(View.GONE);
        holder.twoCategoryToolEmptyTx.setVisibility(View.GONE);
//        if (body.get(position).getIcon()==null) {
//            holder.twoCategoryToolIv.setVisibility(View.GONE);
//            holder.twoCategoryToolEmptyTx.setVisibility(View.VISIBLE);
//        } else {
//            holder.twoCategoryToolIv.setVisibility(View.VISIBLE);
//            holder.twoCategoryToolEmptyTx.setVisibility(View.GONE);
//            mImageLoader.displayImage(Common.ImageUrl + body.get(position).getIcon(),
//                    holder.twoCategoryToolIv, MyBaseApplication.getApp().getOptionsNot());
//        }

        return convertView;
    }

    public void clearData() {
        body.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.two_category_iv)
        ImageView twoCategoryIv;
        @Bind(R.id.two_category_name_tx)
        TextView twoCategoryNameTx;
        @Bind(R.id.two_category_price_tx)
        TextView twoCategoryPriceTx;
        @Bind(R.id.two_category_salesvolume_tx)
        TextView twoCategorySalesvolumeTx;
        @Bind(R.id.two_category_experience_tx)
        TextView twoCategoryExperienceTx;
        @Bind(R.id.two_category_coin_tx)
        TextView twoCategoryCoinTx;
        @Bind(R.id.two_category_tool_iv)
        ImageView twoCategoryToolIv;
        @Bind(R.id.two_category_tool_empty_tx)
        TextView twoCategoryToolEmptyTx;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
