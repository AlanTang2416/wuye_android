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
import com.atman.wysq.model.response.GetPostingsDetailsCommentListModel;
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
public class PostingsDetailsCommentAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetPostingsDetailsCommentListModel.BodyEntity> shop;
    private AdapterInterface mAdapterInterface;
    private long blogUserId;
    private boolean isAnonymity = false;
    private String anonymityImg;

    public PostingsDetailsCommentAdapter(Context context, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.shop = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
        this.mAdapterInterface = mAdapterInterface;
    }

    public void setAnonymityImg(String anonymityImg) {
        this.anonymityImg = anonymityImg;
    }

    public void setAnonymity(boolean anonymity) {
        isAnonymity = anonymity;
    }

    public void setBlogUserId(long blogUserId) {
        this.blogUserId = blogUserId;
    }

    @Override
    public int getCount() {
        if (shop.size() == 0) {
            return 1;
        } else {
            return shop.size();
        }
    }

    @Override
    public GetPostingsDetailsCommentListModel.BodyEntity getItem(int position) {
        return shop.get(position);
    }

    public void setLikeById(int n) {
        shop.get(n).setIsLike(1);
        shop.get(n).setLike_count(shop.get(n).getLike_count() + 1);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_postingsdetail_comment_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (shop.size() == 0) {

            holder.notnullRl.setVisibility(View.GONE);
            holder.nullTx.setVisibility(View.VISIBLE);
        } else {
            holder.notnullRl.setVisibility(View.VISIBLE);
            holder.nullTx.setVisibility(View.GONE);
            holder.itemPostingsdetailCommentNameTv.setText(shop.get(position).getUser_name());
            holder.itemPostingsdetailCommentLevelTx.setText("Lv." + shop.get(position).getUserLevel());

            if (shop.get(position).getVip_level()>=4) {
                holder.itemPostingsdetailCommentVipTx.setVisibility(View.GONE);
                holder.itemPostingsdetailCommentSvipIv.setVisibility(View.VISIBLE);
            } else {
                holder.itemPostingsdetailCommentSvipIv.setVisibility(View.GONE);
                if (shop.get(position).getVip_level()==0) {
                    holder.itemPostingsdetailCommentVipTx.setVisibility(View.GONE);
                } else {
                    holder.itemPostingsdetailCommentVipTx.setText("VIP."+shop.get(position).getVip_level());
                    holder.itemPostingsdetailCommentVipTx.setVisibility(View.VISIBLE);
                }
            }

            if (shop.get(position).getSex().equals("M")) {
                holder.itemPostingsdetailCommentGenderIv.setImageResource(R.mipmap.personal_man_ic);
            } else {
                holder.itemPostingsdetailCommentGenderIv.setImageResource(R.mipmap.personal_weman_ic);
            }
            holder.itemPostingsdetailCommentContentTv.setText(SmileUtils.getEmotionContent(context
                    , holder.itemPostingsdetailCommentContentTv, shop.get(position).getContent()));
            holder.itemPostingsdetailCommentLikeTv.setText(shop.get(position).getLike_count() + "");
            holder.itemPostingsdetailCommentLikeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shop.get(position).getIsLike() == 0
                            && shop.get(position).getIsLike() == 0) {
                        mAdapterInterface.onItemClick(v, position);
                    }
                }
            });
            if (shop.get(position).getVerify_status() == 1) {
                holder.itemPostingsdetailCommentVerifyImg.setVisibility(View.VISIBLE);
                holder.itemPostingsdetailCommentGenderIv.setVisibility(View.GONE);
            } else {
                holder.itemPostingsdetailCommentVerifyImg.setVisibility(View.GONE);
                holder.itemPostingsdetailCommentGenderIv.setVisibility(View.VISIBLE);
            }
            Drawable drawable = null;
            if (shop.get(position).getIsLike() == 1) {
                drawable = context.getResources().getDrawable(R.mipmap.btn_like_icon_gray);
                holder.itemPostingsdetailCommentLikeTv.setTextColor(context.getResources().getColor(R.color.postingsdetails_like_tx));
            } else {
                drawable = context.getResources().getDrawable(R.mipmap.btn_like_icon);
                holder.itemPostingsdetailCommentLikeTv.setTextColor(context.getResources().getColor(R.color.postingsdetails_like_nomal_tx));
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.itemPostingsdetailCommentLikeTv.setCompoundDrawables(null, null, drawable, null);
            holder.itemPostingsdetailCommentTimeTv.setText("第" + (position + 1) + "楼 " + MyTools.convertTime(shop.get(position).getCreate_time(), "yyyy.MM.dd HH:mm"));

            if (shop.get(position).getBlogCommentList() != null && shop.get(position).getBlogCommentList().size() > 0) {
                holder.itemPostingsdetailCommentChildrenRl.setVisibility(View.VISIBLE);
                holder.itemPostingsdetailCommentChildrenNameTx.setText(shop.get(position).getBlogCommentList().get(0).getUser_name() + ":");
                holder.itemPostingsdetailCommentChildrenContentTv.setText(SmileUtils.getEmotionContent(context
                        , holder.itemPostingsdetailCommentChildrenContentTv, shop.get(position).getBlogCommentList().get(0).getContent()));
                holder.itemPostingsdetailCommentChildrenTimeTv.setText(MyTools.convertTime(shop.get(position).getBlogCommentList().get(0).getCreate_time(), "yyyy.MM.dd HH:mm"));
                if (shop.get(position).getSub_count() > 1) {
                    holder.itemPostingsdetailCommentChildrenNumTv.setVisibility(View.VISIBLE);
                    holder.itemPostingsdetailCommentChildrenNumTv.setText("查看全部" + shop.get(position).getSub_count() + "条评论");
                } else {
                    holder.itemPostingsdetailCommentChildrenNumTv.setVisibility(View.GONE);
                }
            } else {
                holder.itemPostingsdetailCommentChildrenRl.setVisibility(View.GONE);
            }

            if (shop.get(position).getUser_id() == blogUserId) {
                holder.itemPostingsdetailHostTx.setVisibility(View.VISIBLE);
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl + shop.get(position).getIcon(),
                        holder.itemPostingsdetailCommentHeadIv, MyBaseApplication.getApplication().getOptionsNot());
                holder.itemPostingsdetailHostTx.setVisibility(View.INVISIBLE);
            }

            if (isAnonymity && shop.get(position).getUser_id() == blogUserId) {
                ImageLoader.getInstance().displayImage(anonymityImg,
                        holder.itemPostingsdetailCommentHeadIv, MyBaseApplication.getApplication().getOptionsNot());
                holder.itemPostingsdetailCommentLevelTx.setVisibility(View.GONE);
                holder.itemPostingsdetailCommentNameTv.setText("匿名用户");
                holder.itemPostingsdetailCommentGenderIv.setVisibility(View.GONE);
                holder.itemPostingsdetailCommentVerifyImg.setVisibility(View.GONE);
                holder.itemPostingsdetailCommentVipTx.setVisibility(View.GONE);
                holder.itemPostingsdetailCommentSvipIv.setVisibility(View.GONE);
                holder.itemPostingsdetailCommentNameTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
                holder.itemPostingsdetailCommentContentTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl + shop.get(position).getIcon(),
                        holder.itemPostingsdetailCommentHeadIv, MyBaseApplication.getApplication().getOptionsNot());
                holder.itemPostingsdetailCommentLevelTx.setVisibility(View.VISIBLE);
                holder.itemPostingsdetailCommentNameTv.setText(shop.get(position).getUser_name());
                if (shop.get(position).getVerify_status() == 1) {
                    holder.itemPostingsdetailCommentVerifyImg.setVisibility(View.VISIBLE);
                    holder.itemPostingsdetailCommentGenderIv.setVisibility(View.GONE);
                } else {
                    holder.itemPostingsdetailCommentVerifyImg.setVisibility(View.GONE);
                    holder.itemPostingsdetailCommentGenderIv.setVisibility(View.VISIBLE);
                }
                if (shop.get(position).getVip_level()>=3) {
                    holder.itemPostingsdetailCommentNameTv.setTextColor(context.getResources().getColor(R.color.color_red));
                    if (shop.get(position).getVip_level()>=4) {
                        holder.itemPostingsdetailCommentContentTv.setTextColor(context.getResources().getColor(R.color.color_red));
                    } else {
                        holder.itemPostingsdetailCommentContentTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
                    }
                } else {
                    holder.itemPostingsdetailCommentNameTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
                }
            }

            if (shop.get(position).getBlogCommentList() != null
                    && shop.get(position).getBlogCommentList().size() > 0
                    && shop.get(position).getBlogCommentList().get(0).getUser_id() == blogUserId && isAnonymity) {
                holder.itemPostingsdetailCommentChildrenNameTx.setText("匿名用户:");
                holder.itemPostingsdetailCommentChildrenContentTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
                holder.itemPostingsdetailCommentChildrenNameTx.setTextColor(context.getResources().getColor(R.color.color_DF9E37));
            } else {
                if (shop.get(position).getBlogCommentList() != null
                        && shop.get(position).getBlogCommentList().size() > 0
                        && shop.get(position).getBlogCommentList().get(0).getVip_level()>=3) {
                    holder.itemPostingsdetailCommentChildrenNameTx.setTextColor(context.getResources().getColor(R.color.color_red));
                    if (shop.get(position).getBlogCommentList().get(0).getVip_level()>=4) {
                        holder.itemPostingsdetailCommentChildrenContentTv.setTextColor(context.getResources().getColor(R.color.color_red));
                    } else {
                        holder.itemPostingsdetailCommentChildrenContentTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
                    }
                } else {
                    holder.itemPostingsdetailCommentChildrenNameTx.setTextColor(context.getResources().getColor(R.color.color_DF9E37));
                    holder.itemPostingsdetailCommentChildrenContentTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
                }
            }
        }

        holder.itemPostingsdetailCommentHeadRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(shop.get(position).getUser_id() == blogUserId && isAnonymity)) {
                    mAdapterInterface.onItemClick(v, position);
                }
            }
        });


        return convertView;
    }

    public void clearData() {
        shop.clear();
        notifyDataSetChanged();
    }

    public void addBody(List<GetPostingsDetailsCommentListModel.BodyEntity> body) {
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

    public void addBody(GetPostingsDetailsCommentListModel.BodyEntity body) {
        shop.add(body);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_postingsdetail_comment_head_iv)
        CustomImageView itemPostingsdetailCommentHeadIv;
        @Bind(R.id.item_postingsdetail_comment_gender_iv)
        ImageView itemPostingsdetailCommentGenderIv;
        @Bind(R.id.item_postingsdetail_comment_verify_img)
        CustomImageView itemPostingsdetailCommentVerifyImg;
        @Bind(R.id.item_postingsdetail_comment_head_rl)
        RelativeLayout itemPostingsdetailCommentHeadRl;
        @Bind(R.id.item_postingsdetail_comment_name_tv)
        TextView itemPostingsdetailCommentNameTv;
        @Bind(R.id.item_postingsdetail_comment_level_tx)
        TextView itemPostingsdetailCommentLevelTx;
        @Bind(R.id.item_postingsdetail_comment_vip_tx)
        TextView itemPostingsdetailCommentVipTx;
        @Bind(R.id.item_postingsdetail_comment_svip_iv)
        ImageView itemPostingsdetailCommentSvipIv;
        @Bind(R.id.item_postingsdetail_comment_name_ll)
        LinearLayout itemPostingsdetailCommentNameLl;
        @Bind(R.id.item_postingsdetail_comment_time_tv)
        TextView itemPostingsdetailCommentTimeTv;
        @Bind(R.id.item_postingsdetail_comment_like_tv)
        TextView itemPostingsdetailCommentLikeTv;
        @Bind(R.id.item_postingsdetail_host_tx)
        TextView itemPostingsdetailHostTx;
        @Bind(R.id.item_postingsdetail_comment_content_tv)
        TextView itemPostingsdetailCommentContentTv;
        @Bind(R.id.item_postingsdetail_comment_children_name_tx)
        TextView itemPostingsdetailCommentChildrenNameTx;
        @Bind(R.id.item_postingsdetail_comment_children_content_tv)
        TextView itemPostingsdetailCommentChildrenContentTv;
        @Bind(R.id.item_postingsdetail_comment_children_time_tv)
        TextView itemPostingsdetailCommentChildrenTimeTv;
        @Bind(R.id.item_postingsdetail_comment_children_num_tv)
        TextView itemPostingsdetailCommentChildrenNumTv;
        @Bind(R.id.item_postingsdetail_comment_children_rl)
        RelativeLayout itemPostingsdetailCommentChildrenRl;
        @Bind(R.id.notnull_rl)
        RelativeLayout notnullRl;
        @Bind(R.id.null_tx)
        TextView nullTx;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
