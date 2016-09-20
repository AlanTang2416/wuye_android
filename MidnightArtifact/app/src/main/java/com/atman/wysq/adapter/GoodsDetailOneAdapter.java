package com.atman.wysq.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetGoodsCommentResponseModel;
import com.atman.wysq.model.response.GoodsDetailsResponseModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.base.baselibs.iimp.AdapterInterface;
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
public class GoodsDetailOneAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetGoodsCommentResponseModel.BodyEntity> shop;
    private AdapterInterface mAdapterInterface;

    public GoodsDetailOneAdapter(Context context, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.shop = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
        this.mAdapterInterface = mAdapterInterface;
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public GetGoodsCommentResponseModel.BodyEntity getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_image_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(Common.ImageUrl + shop.get(position).getIcon(),
                holder.itemGoodsdetailHeadIv, MyBaseApplication.getApplication().getOptionsNot());
        holder.itemGoodsdetailNameTv.setText(shop.get(position).getUser_name());
        holder.itemGoodsdetailLevelTx.setText("Lv."+shop.get(position).getUserLevel());
        if (shop.get(position).getSex().equals("M")) {
            holder.itemGoodsdetailGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            holder.itemGoodsdetailGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }
        holder.itemGoodsdetailContentTv.setText(shop.get(position).getContent());
        holder.itemGoodsdetailLikeTv.setText(shop.get(position).getLike_count()+"");
        holder.itemGoodsdetailLikeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shop.get(position).getIsLike()==0) {
                    mAdapterInterface.onItemClick(v, position);
                }
            }
        });
        if (shop.get(position).getVip_level()>=4) {
            holder.itemGoodsdetailCommentVipTx.setVisibility(View.GONE);
            holder.itemGoodsdetailCommentSvipIv.setVisibility(View.VISIBLE);
        } else {
            holder.itemGoodsdetailCommentSvipIv.setVisibility(View.GONE);
            if (shop.get(position).getVip_level()==0) {
                holder.itemGoodsdetailCommentVipTx.setVisibility(View.GONE);
            } else {
                holder.itemGoodsdetailCommentVipTx.setText("VIP."+shop.get(position).getVip_level());
                holder.itemGoodsdetailCommentVipTx.setVisibility(View.VISIBLE);
            }
        }
        Drawable drawable = null;
        if (shop.get(position).getIsLike() == 1) {
            drawable = context.getResources().getDrawable(R.mipmap.btn_like_icon_gray);
            holder.itemGoodsdetailLikeTv.setTextColor(context.getResources().getColor(R.color.postingsdetails_like_tx));
        } else {
            drawable = context.getResources().getDrawable(R.mipmap.btn_like_icon);
            holder.itemGoodsdetailLikeTv.setTextColor(context.getResources().getColor(R.color.postingsdetails_like_nomal_tx));
        }
//        if (shop.get(position).getVerify_status() == 1) {
//            holder.itemGoodsdetailVerifyImg.setVisibility(View.VISIBLE);
//            holder.itemGoodsdetailGenderIv.setVisibility(View.GONE);
//        } else {
            holder.itemGoodsdetailVerifyImg.setVisibility(View.GONE);
            holder.itemGoodsdetailGenderIv.setVisibility(View.VISIBLE);
//        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        holder.itemGoodsdetailLikeTv.setCompoundDrawables(null, null, drawable, null);
        holder.itemGoodsdetailTimeTv.setText("第"+(position+1)+"楼 "+ MyTools.convertTimeTwo(shop.get(position).getCreate_time()));

        if (shop.get(position).getVip_level()>=3) {
            holder.itemGoodsdetailNameTv.setTextColor(context.getResources().getColor(R.color.color_red));
            if (shop.get(position).getVip_level()>=4) {
                holder.itemGoodsdetailContentTv.setTextColor(context.getResources().getColor(R.color.color_red));
            } else {
                holder.itemGoodsdetailContentTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
            }
        } else {
            holder.itemGoodsdetailNameTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
        }

        holder.itemGoodsdetailHeadRl.setOnClickListener(new View.OnClickListener() {
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

    public void addBody(List<GetGoodsCommentResponseModel.BodyEntity> body) {
        shop.addAll(body);
        notifyDataSetChanged();
    }

    public void setLikeById(int n) {
        shop.get(n).setIsLike(1);
        shop.get(n).setLike_count(shop.get(n).getLike_count() + 1);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_goodsdetail_head_iv)
        CustomImageView itemGoodsdetailHeadIv;
        @Bind(R.id.item_goodsdetail_verify_img)
        CustomImageView itemGoodsdetailVerifyImg;
        @Bind(R.id.item_goodsdetail_gender_iv)
        ImageView itemGoodsdetailGenderIv;
        @Bind(R.id.item_goodsdetail_comment_svip_iv)
        ImageView itemGoodsdetailCommentSvipIv;
        @Bind(R.id.item_goodsdetail_head_rl)
        RelativeLayout itemGoodsdetailHeadRl;
        @Bind(R.id.item_goodsdetail_name_tv)
        TextView itemGoodsdetailNameTv;
        @Bind(R.id.item_goodsdetail_level_tx)
        TextView itemGoodsdetailLevelTx;
        @Bind(R.id.item_goodsdetail_name_ll)
        LinearLayout itemGoodsdetailNameLl;
        @Bind(R.id.item_goodsdetail_time_tv)
        TextView itemGoodsdetailTimeTv;
        @Bind(R.id.item_goodsdetail_comment_vip_tx)
        TextView itemGoodsdetailCommentVipTx;
        @Bind(R.id.item_goodsdetail_like_tv)
        TextView itemGoodsdetailLikeTv;
        @Bind(R.id.item_goodsdetail_content_tv)
        TextView itemGoodsdetailContentTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
