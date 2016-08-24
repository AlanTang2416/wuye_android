package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetBlogBoardModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.widget.CustomImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

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
public class CommunityBlogBoardAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetBlogBoardModel.BodyEntity> body;
    private ImageLoader mImageLoader;
    private AdapterInterface mAdapterInterface;

    public CommunityBlogBoardAdapter(Context context, List<GetBlogBoardModel.BodyEntity> body, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.body = body;
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public GetBlogBoardModel.BodyEntity getItem(int position) {
        return body.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GetBlogBoardModel.BodyEntity mBodyEntity = body.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_community_blogboard_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

            mImageLoader.displayImage(Common.ImageUrl + "/" + mBodyEntity.getPic_url(),
                    holder.itemCommunityBlogboardImg, MyBaseApplication.getApp().getOptions());
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemCommunityBlogboardTitleTx.setText(mBodyEntity.getTitle());
        holder.itemCommunityBlogboardDescriptionTx.setText(mBodyEntity.getDescription());
        holder.itemCommunityBlogboardBlogcountTx.setText(mBodyEntity.getCommentCount() + " ");
        holder.itemCommunityBlogboardRootLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_community_blogboard_img)
        CustomImageView itemCommunityBlogboardImg;
        @Bind(R.id.item_community_blogboard_title_tx)
        TextView itemCommunityBlogboardTitleTx;
        @Bind(R.id.item_community_blogboard_description_tx)
        TextView itemCommunityBlogboardDescriptionTx;
        @Bind(R.id.item_community_blogboard_blogcount_tx)
        TextView itemCommunityBlogboardBlogcountTx;
        @Bind(R.id.item_community_blogboard_root_ll)
        LinearLayout itemCommunityBlogboardRootLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
