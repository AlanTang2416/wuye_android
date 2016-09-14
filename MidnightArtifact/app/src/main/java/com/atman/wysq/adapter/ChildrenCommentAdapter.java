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
import com.atman.wysq.model.response.GetChildrenCommentModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.atman.wysq.widget.face.SmileUtils;
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
public class ChildrenCommentAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetChildrenCommentModel.BodyEntity> shop;
    private AdapterInterface mAdapterInterface;
    private long blogUserId;
    private boolean isAnonymity;
    private String anonymityImg;

    public ChildrenCommentAdapter(Context context, long blogUserId, boolean isAnonymity, String anonymityImg
            , AdapterInterface mAdapterInterface) {
        this.context = context;
        this.shop = new ArrayList<>();
        this.blogUserId = blogUserId;
        this.isAnonymity = isAnonymity;
        this.anonymityImg = anonymityImg;
        layoutInflater = LayoutInflater.from(context);
        this.mAdapterInterface = mAdapterInterface;
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public GetChildrenCommentModel.BodyEntity getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_children_comment_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            holder.itemChildrenToplineLl.setVisibility(View.VISIBLE);
        } else {
            holder.itemChildrenToplineLl.setVisibility(View.GONE);
        }

        holder.itemChildrenCommentNameTv.setText(shop.get(position).getUser_name());
        holder.itemChildrenCommentLevelTx.setText("Lv." + shop.get(position).getUserLevel());

        if (shop.get(position).getVip_level()>=4) {
            holder.itemChildrenCommentVipTx.setVisibility(View.GONE);
            holder.itemChildrenCommentSvipIv.setVisibility(View.VISIBLE);
        } else {
            holder.itemChildrenCommentSvipIv.setVisibility(View.GONE);
            if (shop.get(position).getVip_level()==0) {
                holder.itemChildrenCommentVipTx.setVisibility(View.GONE);
            } else {
                holder.itemChildrenCommentVipTx.setText("VIP."+shop.get(position).getVip_level());
                holder.itemChildrenCommentVipTx.setVisibility(View.VISIBLE);
            }
        }
        if (shop.get(position).getSex().equals("M")) {
            holder.itemChildrenCommentGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            holder.itemChildrenCommentGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }
        holder.itemChildrenCommentContentTv.setText(SmileUtils.getEmotionContent(context
                , holder.itemChildrenCommentContentTv, shop.get(position).getContent()));

        if (shop.get(position).getVerify_status() == 1) {
            holder.itemChildrenCommentVerifyImg.setVisibility(View.VISIBLE);
            holder.itemChildrenCommentGenderIv.setVisibility(View.GONE);
        } else {
            holder.itemChildrenCommentVerifyImg.setVisibility(View.GONE);
            holder.itemChildrenCommentGenderIv.setVisibility(View.VISIBLE);
        }
        holder.itemChildrenCommentTimeTv.setText(MyTools.convertTime(shop.get(position).getCreate_time(), "yyyy.MM.dd HH:mm"));

        if (shop.get(position).getUser_id() == blogUserId) {
            holder.itemChildrenHostTx.setVisibility(View.VISIBLE);
        } else {
            ImageLoader.getInstance().displayImage(Common.ImageUrl + shop.get(position).getIcon(),
                    holder.itemChildrenCommentHeadIv, MyBaseApplication.getApplication().getOptionsNot());
            holder.itemChildrenHostTx.setVisibility(View.INVISIBLE);
        }

        holder.itemChildrenCommentHeadRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(shop.get(position).getUser_id() == blogUserId && isAnonymity)) {
                    mAdapterInterface.onItemClick(v, position);
                }
            }
        });
        if (isAnonymity) {
            holder.itemChildrenCommentNameTv.setText("匿名用户");
            holder.itemChildrenCommentGenderIv.setVisibility(View.GONE);
            holder.itemChildrenCommentVerifyImg.setVisibility(View.GONE);
            holder.itemChildrenCommentVipTx.setVisibility(View.GONE);
            holder.itemChildrenCommentSvipIv.setVisibility(View.GONE);
            holder.itemChildrenCommentLevelTx.setVisibility(View.GONE);
            holder.itemChildrenCommentContentTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
            holder.itemChildrenCommentNameTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
            ImageLoader.getInstance().displayImage(anonymityImg,
                    holder.itemChildrenCommentHeadIv, MyBaseApplication.getApplication().getOptionsNot());
        } else {
            if (shop.get(position).getVip_level()>=3) {
                holder.itemChildrenCommentNameTv.setTextColor(context.getResources().getColor(R.color.color_red));
                if (shop.get(position).getVip_level()>=4) {
                    holder.itemChildrenCommentContentTv.setTextColor(context.getResources().getColor(R.color.color_red));
                } else {
                    holder.itemChildrenCommentContentTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
                }
            } else {
                holder.itemChildrenCommentNameTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
            }
            ImageLoader.getInstance().displayImage(Common.ImageUrl + shop.get(position).getIcon(),
                    holder.itemChildrenCommentHeadIv, MyBaseApplication.getApplication().getOptionsNot());
        }

        return convertView;
    }

    public void clearData() {
        shop.clear();
        notifyDataSetChanged();
    }

    public void addBody(List<GetChildrenCommentModel.BodyEntity> body) {
        for (int i = 0; i < shop.size(); i++) {
            for (int j = 0; j < body.size(); j++) {
                if (shop.get(i).getBlog_comment_id() == body.get(j).getBlog_comment_id()) {
                    shop.remove(i);
                    i -= 1;
                }
            }
        }
        shop.addAll(body);
        notifyDataSetChanged();
    }

    public void addBody(GetChildrenCommentModel.BodyEntity body) {
        shop.add(body);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_children_topline_ll)
        LinearLayout itemChildrenToplineLl;
        @Bind(R.id.item_children_host_tx)
        TextView itemChildrenHostTx;
        @Bind(R.id.item_children_comment_head_iv)
        CustomImageView itemChildrenCommentHeadIv;
        @Bind(R.id.item_children_comment_gender_iv)
        ImageView itemChildrenCommentGenderIv;
        @Bind(R.id.item_children_comment_verify_img)
        CustomImageView itemChildrenCommentVerifyImg;
        @Bind(R.id.item_children_comment_head_rl)
        RelativeLayout itemChildrenCommentHeadRl;
        @Bind(R.id.item_children_comment_name_tv)
        TextView itemChildrenCommentNameTv;
        @Bind(R.id.item_children_comment_level_tx)
        TextView itemChildrenCommentLevelTx;
        @Bind(R.id.item_children_comment_vip_tx)
        TextView itemChildrenCommentVipTx;
        @Bind(R.id.item_children_comment_svip_iv)
        ImageView itemChildrenCommentSvipIv;
        @Bind(R.id.item_children_comment_name_ll)
        LinearLayout itemChildrenCommentNameLl;
        @Bind(R.id.item_children_comment_time_tv)
        TextView itemChildrenCommentTimeTv;
        @Bind(R.id.item_children_comment_content_tv)
        TextView itemChildrenCommentContentTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
