package com.atman.wysq.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.atman.wysq.R;
import com.atman.wysq.model.request.PostContentModel;
import com.atman.wysq.widget.face.FaceNotEditViewRelativeLayout;
import com.atman.wysq.widget.face.SmileUtils;
import com.base.baselibs.iimp.AdapterInterface;
import com.choicepicture_library.tools.Bimp;

import java.io.IOException;
import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/12 13:44
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PostContentListAdapter extends BaseAdapter {

    private Context context;
    protected LayoutInflater layoutInflater;
    private List<PostContentModel> shop;
    private AdapterInterface mAdapterInterface;
    private int index = -1;
    private EditText mEditText;
    private ImageView postFaceIv;
    private RelativeLayout llFacechoose;
    private FaceNotEditViewRelativeLayout postFaceRelativeLayout;

    public PostContentListAdapter(Context context, List<PostContentModel> shop, ImageView postFaceIv
            , RelativeLayout llFacechoose, FaceNotEditViewRelativeLayout postFaceRelativeLayout, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.shop = shop;
        this.postFaceIv = postFaceIv;
        this.llFacechoose = llFacechoose;
        this.postFaceRelativeLayout = postFaceRelativeLayout;
    }

    public int getIndex() {
        return index;
    }

    public EditText getmEditText() {
        return mEditText;
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public PostContentModel getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View mView = layoutInflater.inflate(R.layout.item_postcontent_view, null);

        ImageView itemPostcontentDeleteIv = (ImageView) mView.findViewById(R.id.item_postcontent_delete_iv);
        ImageView itemPostcontentHeadIv = (ImageView) mView.findViewById(R.id.item_postcontent_head_iv);
        RelativeLayout itemPostcontentHeadRl = (RelativeLayout) mView.findViewById(R.id.item_postcontent_head_rl);
        final EditText itemPostcontentContentEt = (EditText) mView.findViewById(R.id.item_postcontent_content_et);

        if (shop.size() == 1) {
            itemPostcontentDeleteIv.setVisibility(View.GONE);
        } else {
            itemPostcontentDeleteIv.setVisibility(View.VISIBLE);
        }

        if (shop.get(position).getLocalUrl().isEmpty()) {
            itemPostcontentHeadRl.setVisibility(View.GONE);
        } else {
            itemPostcontentHeadRl.setVisibility(View.VISIBLE);
            if (shop.get(position).getBitmap()!=null) {
                itemPostcontentHeadIv.setImageBitmap(shop.get(position).getBitmap());
            } else {
                Bitmap bm = null;
                try {
                    bm = Bimp.revitionImageSize(shop.get(position).getLocalUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                shop.get(position).setBitmap(bm);
                itemPostcontentHeadIv.setImageBitmap(bm);
            }
        }


        itemPostcontentContentEt.setText(SmileUtils.getEmotionContent(context
                , itemPostcontentContentEt, shop.get(position).getContent()));
        itemPostcontentContentEt.addTextChangedListener(new postTextWatcher(context
                , itemPostcontentContentEt, position));
        itemPostcontentContentEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);//解决listview截获滑动监听
                if(event.getAction() == MotionEvent.ACTION_UP
                        || event.getAction() == MotionEvent.ACTION_MOVE) {
                    index= position;
                    llFacechoose.setVisibility(View.GONE);
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        itemPostcontentContentEt.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEditText = itemPostcontentContentEt;
                    postFaceRelativeLayout.setEt_sendmessage(mEditText);
                    postFaceIv.setVisibility(View.VISIBLE);
                } else {
                    postFaceIv.setVisibility(View.GONE);
                }
            }
        });

        itemPostcontentContentEt.clearFocus();
        if(index!= -1 && index == position) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            itemPostcontentContentEt.requestFocus();
        }

        itemPostcontentDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });
        itemPostcontentHeadRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        return mView;
    }

    public void delateItem (int num) {
        if (num==index) {
            index = -1;
            mEditText = null;
            llFacechoose.setVisibility(View.GONE);
        }
        shop.remove(num);
        notifyDataSetChanged();
    }

    public void setNetUrl(int mPosition, String url) {
        shop.get(mPosition).setBitmap(null);
        shop.get(mPosition).setNetUrl(url);
        notifyDataSetChanged();
    }

    public void setLocalUrl(int mPosition, String url) {
        shop.get(mPosition).setBitmap(null);
        shop.get(mPosition).setLocalUrl(url);
        notifyDataSetChanged();
    }

    public void addItem(PostContentModel mPostContentModel) {
        shop.add(mPostContentModel);
        notifyDataSetChanged();
    }

    public class postTextWatcher implements TextWatcher {
        private EditText myCleanEditText;
        private Context mContext;
        private int num;

        public postTextWatcher(Context mContext, EditText myCleanEditText, int num) {
            this.myCleanEditText = myCleanEditText;
            this.mContext = mContext;
            this.num = num;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String content = s == null ? null : s.toString();
            if (s == null || s.length() == 0) {
                return;
            }
            int size = content.trim().length();
            shop.get(num).setContent(myCleanEditText.getText().toString());
        }
    }
}
