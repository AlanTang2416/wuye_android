package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.bean.ImSession;
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
public class MessageSessionListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<ImSession> dataList;
    private AdapterInterface mAdapterInterface;

    public MessageSessionListAdapter(Context context, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = new ArrayList<>();
    }

    public void addBody(List<ImSession> data) {
        this.dataList.clear();
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearUnreadNum(int p){
        this.dataList.get(p).setUnreadNum(0);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public ImSession getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_session_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (holder.itemSessionHeadIv.getDrawable()==null) {
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(position).getIcon(), holder.itemSessionHeadIv
                    , MyBaseApplication.getApplication().getOptionsNot());
        }
        if (dataList.get(position).getSex().equals("F")) {
            holder.itemSessionGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        } else {
            holder.itemSessionGenderIv.setImageResource(R.mipmap.personal_man_ic);
        }

        if (dataList.get(position).getVerify_status()==1) {
            holder.itemSessionVerifyImg.setVisibility(View.VISIBLE);
            holder.itemSessionGenderIv.setVisibility(View.GONE);
        } else {
            holder.itemSessionVerifyImg.setVisibility(View.VISIBLE);
            holder.itemSessionGenderIv.setVisibility(View.GONE);
        }

        holder.itemSessionNickTx.setText(dataList.get(position).getNickName());
        holder.itemSessionTimeTx.setText(MyTools.convertTimeS(dataList.get(position).getTime()));

        if (dataList.get(position).getUnreadNum()>0) {
            holder.itemSessionUnreadTx.setVisibility(View.VISIBLE);
            if (dataList.get(position).getUnreadNum()>99) {
                holder.itemSessionUnreadTx.setText("99+");
            } else {
                holder.itemSessionUnreadTx.setText(dataList.get(position).getUnreadNum()+"");
            }
        } else {
            holder.itemSessionUnreadTx.setVisibility(View.INVISIBLE);
        }

        if (dataList.get(position).getContent()!=null) {
            holder.itemSessionContentTx.setText(SmileUtils.getEmotionContent(context
                    , holder.itemSessionContentTx, dataList.get(position).getContent()));
        }

        return convertView;
    }

    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_session_head_iv)
        CustomImageView itemSessionHeadIv;
        @Bind(R.id.item_session_gender_iv)
        ImageView itemSessionGenderIv;
        @Bind(R.id.item_session_verify_img)
        CustomImageView itemSessionVerifyImg;
        @Bind(R.id.item_session_head_rl)
        RelativeLayout itemSessionHeadRl;
        @Bind(R.id.item_session_unread_tx)
        TextView itemSessionUnreadTx;
        @Bind(R.id.item_session_nick_tx)
        TextView itemSessionNickTx;
        @Bind(R.id.item_session_time_tx)
        TextView itemSessionTimeTx;
        @Bind(R.id.item_session_content_tx)
        TextView itemSessionContentTx;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
