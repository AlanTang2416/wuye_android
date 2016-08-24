package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetTaskAllModel;
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
public class MyTaskAllAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetTaskAllModel.BodyEntity> body;
    private ImageLoader mImageLoader;
    private AdapterInterface mAdapterInterface;

    public MyTaskAllAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        body = new ArrayList<>();
        mImageLoader = ImageLoader.getInstance();
    }

    public void setBody(List<GetTaskAllModel.BodyEntity> body, AdapterInterface mAdapterInterface) {
        this.body = body;
        this.mAdapterInterface = mAdapterInterface;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public GetTaskAllModel.BodyEntity getItem(int position) {
        return body.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GetTaskAllModel.BodyEntity mBodyEntity = body.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mytaskall_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

            mImageLoader.displayImage(Common.ImageUrl + "/" + mBodyEntity.getPic(),
                    holder.itemTaskImg, MyBaseApplication.getApp().getOptions());
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemTaskTitleTx.setText(mBodyEntity.getName());
        if (mBodyEntity.getExperience() != 0) {
            holder.itemTaskExperienceTx.setVisibility(View.VISIBLE);
            holder.itemTaskExperienceTx.setText("奖励经验：" + mBodyEntity.getExperience());
        } else {
            holder.itemTaskExperienceTx.setVisibility(View.GONE);
        }
        if (mBodyEntity.getCoin() != 0) {
            holder.itemTaskCoinTx.setVisibility(View.VISIBLE);
            holder.itemTaskCoinTx.setText("奖励金币：" + mBodyEntity.getCoin());
        } else {
            holder.itemTaskCoinTx.setVisibility(View.GONE);
        }

        /**
         * task_type//1:签到 2:认证 3:分享 4:安装APP 5:其它
         * share_type//1:QQ 2:微信 3:QQ 4:新浪微博
         * task_complete//0未完成 1完成
         * rewarded//0未领取 1领取
         * **/

        if (mBodyEntity.getTask_complete() == 1) {//完成
            if (mBodyEntity.getRewarded() == 0) {
                holder.itemTaskStatusBt.setVisibility(View.VISIBLE);
                holder.itemTaskStatusTx.setVisibility(View.GONE);
                holder.itemTaskStatusBt.setText("领取");
            } else {
                holder.itemTaskStatusBt.setVisibility(View.GONE);
                holder.itemTaskStatusTx.setVisibility(View.VISIBLE);
                if (mBodyEntity.getTask_type() == 1) {
                    holder.itemTaskStatusTx.setText("已签到");
                } else {
                    holder.itemTaskStatusTx.setText("已领取");
                }
            }
        } else {//未完成
            if (mBodyEntity.getTask_type() == 1) {
                holder.itemTaskStatusBt.setVisibility(View.VISIBLE);
                holder.itemTaskStatusTx.setVisibility(View.GONE);
                holder.itemTaskStatusBt.setText("签到");
            } else if (mBodyEntity.getTask_type() == 3) {
                holder.itemTaskStatusBt.setVisibility(View.VISIBLE);
                holder.itemTaskStatusTx.setVisibility(View.GONE);
                holder.itemTaskStatusBt.setText("立即分享");
            } else if (mBodyEntity.getTask_type() == 4) {
                holder.itemTaskStatusBt.setVisibility(View.VISIBLE);
                holder.itemTaskStatusTx.setVisibility(View.GONE);
                holder.itemTaskStatusBt.setText("安装");
            } else {
                holder.itemTaskStatusBt.setVisibility(View.GONE);
                holder.itemTaskStatusTx.setVisibility(View.VISIBLE);
            }
        }

        holder.itemTaskStatusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        return convertView;
    }

    public void changeRewardedStatusById(int num){
        body.get(num).setRewarded(1);
        notifyDataSetChanged();
    }

    public void changeFinishStatusById(int num){
        body.get(num).setTask_complete(1);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_task_img)
        ImageView itemTaskImg;
        @Bind(R.id.item_task_title_tx)
        TextView itemTaskTitleTx;
        @Bind(R.id.item_task_experience_tx)
        TextView itemTaskExperienceTx;
        @Bind(R.id.item_task_coin_tx)
        TextView itemTaskCoinTx;
        @Bind(R.id.item_task_status_bt)
        Button itemTaskStatusBt;
        @Bind(R.id.item_task_status_tx)
        TextView itemTaskStatusTx;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
