package com.atman.wysq.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.bean.ImMessage;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.atman.wysq.widget.face.SmileUtils;
import com.atman.wysq.yunxin.model.ContentTypeInter;
import com.base.baselibs.widget.CustomImageView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/1 16:06
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class P2PChatAdapter extends BaseAdapter {

    private List<ImMessage> mImMessage;

    private Context context;
    protected LayoutInflater layoutInflater;
    private long time = 0;
    private boolean isShowTime = false;
    private PullToRefreshListView p2pChatLv;
    private boolean isBottom = false;

    public P2PChatAdapter(Context context, PullToRefreshListView p2pChatLv) {
        this.context = context;
        this.mImMessage = new ArrayList<>();
        this.p2pChatLv = p2pChatLv;
        layoutInflater = LayoutInflater.from(context);
    }

    public void clearData() {
        this.mImMessage.clear();
        notifyDataSetChanged();
    }

    public void addImMessageDao(List<ImMessage> mImMessageDao) {
        this.mImMessage.addAll(mImMessageDao);
        notifyDataSetChanged();
    }

    public void setImMessageStatus(String id, int status) {
        for (int i = 0; i < mImMessage.size(); i++) {
            if (mImMessage.get(i).getUuid().equals(id)) {
                mImMessage.get(i).setIsSeedSuccess(status);
            }
        }
        notifyDataSetChanged();
    }

    public void addImMessageDao(ImMessage mImMessageDao) {
        this.mImMessage.add(mImMessageDao);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getCount() {
        return mImMessage.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mImMessage.get(position).getContentType();
    }

    @Override
    public Object getItem(int position) {
        return mImMessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolderText holderText = null;

        int type = getItemViewType(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_p2pchat_text_view, parent, false);
            holderText = new viewHolderText(convertView);
            convertView.setTag(holderText);
        } else {
            holderText = (viewHolderText) convertView.getTag();
        }

        ImMessage temp = mImMessage.get(position);

        if (position == 0 || (MyTools.getGapCountM(time, temp.getTime()) >= 5)) {
            time = temp.getTime();
            isShowTime = true;
        } else {
            isShowTime = false;
        }

        holderText.itemP2pchatTextLeftTx.setVisibility(View.GONE);
        holderText.itemP2pchatImageLeftIv.setVisibility(View.GONE);
        holderText.itemP2pchatFingerLeftIv.setVisibility(View.GONE);

        holderText.itemP2pchatTextRightTx.setVisibility(View.GONE);
        holderText.itemP2pchatImageRightIv.setVisibility(View.GONE);
        holderText.itemP2pchatFingerRightIv.setVisibility(View.GONE);
        if (temp.getIsSelfSend()) {
            holderText.itemP2pchatTextHeadrightIv.setVisibility(View.VISIBLE);
            holderText.itemP2pchatTextHeadleftIv.setVisibility(View.GONE);
            if (holderText.itemP2pchatTextHeadrightIv.getDrawable()==null) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl + temp.getIcon()
                        , holderText.itemP2pchatTextHeadrightIv, MyBaseApplication.getApplication().getOptionsNot(), mListener);
            }
        } else {
            holderText.itemP2pchatTextHeadrightIv.setVisibility(View.GONE);
            holderText.itemP2pchatTextHeadleftIv.setVisibility(View.VISIBLE);
            if (holderText.itemP2pchatTextHeadrightIv.getDrawable()==null) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl + temp.getIcon()
                        , holderText.itemP2pchatTextHeadleftIv, MyBaseApplication.getApplication().getOptionsNot(), mListener);
            }
        }
        if (isShowTime) {
            holderText.itemP2pchatTextTimeTx.setVisibility(View.VISIBLE);
            holderText.itemP2pchatTextTimeTx.setText(MyTools.convertTimeS(temp.getTime()));
        } else {
            holderText.itemP2pchatTextTimeTx.setVisibility(View.INVISIBLE);
        }
        if (temp.getIsSeedSuccess() == 0) {
            holderText.itemP2pchatRightProgress.setVisibility(View.VISIBLE);
        } else {
            holderText.itemP2pchatRightProgress.setVisibility(View.GONE);
            if (temp.getIsSeedSuccess() == 2) {
                holderText.itemP2pchatRightAlert.setVisibility(View.VISIBLE);
            } else {
                holderText.itemP2pchatRightAlert.setVisibility(View.GONE);
            }
        }

        switch (type) {
            case ContentTypeInter.contentTypeText:
                if (temp.getIsSelfSend()) {
                    holderText.itemP2pchatTextRightTx.setVisibility(View.VISIBLE);
                    holderText.itemP2pchatTextRightTx.setText(SmileUtils.getEmotionContent(context
                            , holderText.itemP2pchatTextRightTx, temp.getContent()));
                } else {
                    holderText.itemP2pchatTextLeftTx.setVisibility(View.VISIBLE);
                    holderText.itemP2pchatTextLeftTx.setText(SmileUtils.getEmotionContent(context
                            , holderText.itemP2pchatTextRightTx, temp.getContent()));
                    ImageLoader.getInstance().displayImage(Common.ImageUrl + temp.getIcon()
                            , holderText.itemP2pchatTextHeadleftIv, MyBaseApplication.getApplication().getOptionsNot(), mListener);
                }
                break;
            case ContentTypeInter.contentTypeImage:
                if (temp.getIsSelfSend()) {
                    holderText.itemP2pchatImageRightIv.setVisibility(View.VISIBLE);
                    if (temp.getImageThumUrl().startsWith("http")) {
                        ImageLoader.getInstance().displayImage(temp.getImageUrl(), holderText.itemP2pchatImageRightIv
                                , MyBaseApplication.getApplication().getOptionsNot(), mListener);
                    } else {
                        File mFile = new File(temp.getImageFilePath());
                        if (mFile.exists()) {
                            ImageLoader.getInstance().displayImage("file://" + temp.getImageFilePath(), holderText.itemP2pchatImageRightIv);
                        } else {
                            ImageLoader.getInstance().displayImage(temp.getImageUrl(), holderText.itemP2pchatImageRightIv
                                    , MyBaseApplication.getApplication().getOptionsNot(), mListener);
                        }
                    }
                } else {
                    holderText.itemP2pchatImageLeftIv.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(temp.getImageUrl(), holderText.itemP2pchatImageLeftIv, MyBaseApplication.getApplication().getOptionsNot());
                }
                break;
            case ContentTypeInter.contentTypeImageSmall:
                break;
            case ContentTypeInter.contentTypeAudio:
                break;
            case ContentTypeInter.contentTypeFinger:
                if (temp.getIsSelfSend()) {
                    holderText.itemP2pchatFingerRightIv.setVisibility(View.VISIBLE);
                    if (temp.getContent().equals("[石头]")) {
                        holderText.itemP2pchatFingerRightIv.setImageResource(R.mipmap.message_actionfunc_finger_0001);
                    } else if (temp.getContent().equals("[剪刀]")) {
                        holderText.itemP2pchatFingerRightIv.setImageResource(R.mipmap.message_actionfunc_finger_0002);
                    } else if (temp.getContent().equals("[布]")) {
                        holderText.itemP2pchatFingerRightIv.setImageResource(R.mipmap.message_actionfunc_finger_0003);
                    }
                } else {
                    holderText.itemP2pchatFingerLeftIv.setVisibility(View.VISIBLE);
                    if (temp.getContent().equals("[石头]")) {
                        holderText.itemP2pchatFingerLeftIv.setImageResource(R.mipmap.message_actionfunc_finger_0001);
                    } else if (temp.getContent().equals("[剪刀]")) {
                        holderText.itemP2pchatFingerLeftIv.setImageResource(R.mipmap.message_actionfunc_finger_0002);
                    } else if (temp.getContent().equals("[布]")) {
                        holderText.itemP2pchatFingerLeftIv.setImageResource(R.mipmap.message_actionfunc_finger_0003);
                    }
                }
                break;
        }

        return convertView;
    }

    private ImageLoadingListener mListener = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            if (!isBottom) {
                isBottom = true;
                p2pChatLv.getRefreshableView().smoothScrollToPosition(p2pChatLv.getRefreshableView().getBottom());
            }
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    };

    static class viewHolderText {
        @Bind(R.id.item_p2pchat_text_time_tx)
        TextView itemP2pchatTextTimeTx;
        @Bind(R.id.item_p2pchat_text_headleft_iv)
        CustomImageView itemP2pchatTextHeadleftIv;
        @Bind(R.id.item_p2pchat_text_headright_iv)
        CustomImageView itemP2pchatTextHeadrightIv;
        @Bind(R.id.item_p2pchat_text_left_tx)
        TextView itemP2pchatTextLeftTx;
        @Bind(R.id.item_p2pchat_image_left_iv)
        ImageView itemP2pchatImageLeftIv;
        @Bind(R.id.item_p2pchat_finger_left_iv)
        ImageView itemP2pchatFingerLeftIv;
        @Bind(R.id.item_p2pchat_right_progress)
        ProgressBar itemP2pchatRightProgress;
        @Bind(R.id.item_p2pchat_right_alert)
        ImageView itemP2pchatRightAlert;
        @Bind(R.id.item_p2pchat_text_right_tx)
        TextView itemP2pchatTextRightTx;
        @Bind(R.id.item_p2pchat_image_right_iv)
        ImageView itemP2pchatImageRightIv;
        @Bind(R.id.item_p2pchat_finger_right_iv)
        ImageView itemP2pchatFingerRightIv;

        viewHolderText(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //各个布局的控件资源
}
