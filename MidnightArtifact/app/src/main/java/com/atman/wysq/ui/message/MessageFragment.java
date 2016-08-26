package com.atman.wysq.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.yunxinfriend.MoFriendsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述 消息
 * 作者 tangbingliang
 * 时间 16/7/1 18:11
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MessageFragment extends MyBaseFragment {

    @Bind(R.id.fragment_bar_title_iv)
    ImageView fragmentBarTitleIv;
    @Bind(R.id.fragment_bar_right_iv)
    ImageView fragmentBarRightIv;
    @Bind(R.id.fragment_bar_right_rl)
    RelativeLayout fragmentBarRightRl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        fragmentBarTitleIv.setImageResource(R.mipmap.top_message_ic);
        fragmentBarRightIv.setVisibility(View.VISIBLE);
        fragmentBarRightIv.setImageResource(R.mipmap.message_top_right_ic);
    }

    @Override
    public void initIntentAndMemData() {
        super.initIntentAndMemData();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.fragment_bar_right_iv, R.id.fragment_bar_right_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_bar_right_iv:
            case R.id.fragment_bar_right_rl:
                if (!isLogin()) {
                    showLogin();
                } else {
                    startActivity(new Intent(getActivity(), MoFriendsActivity.class));
                }
                break;
        }
    }
}
