package com.atman.wysq.ui.message;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MessageSessionListAdapter;
import com.atman.wysq.model.bean.ImMessage;
import com.atman.wysq.model.bean.ImSession;
import com.atman.wysq.model.event.YunXinMessageEvent;
import com.atman.wysq.model.greendao.gen.ImMessageDao;
import com.atman.wysq.model.greendao.gen.ImSessionDao;
import com.atman.wysq.ui.MainActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.yunxinfriend.MoFriendsActivity;
import com.atman.wysq.ui.yunxinfriend.P2PChatActivity;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.PromptDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

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
public class MessageFragment extends MyBaseFragment implements AdapterInterface{

    @Bind(R.id.fragment_bar_title_iv)
    ImageView fragmentBarTitleIv;
    @Bind(R.id.fragment_bar_right_iv)
    ImageView fragmentBarRightIv;
    @Bind(R.id.fragment_bar_right_rl)
    RelativeLayout fragmentBarRightRl;
    @Bind(R.id.message_listview)
    ListView messageListview;

    private List<ImSession> mImSession;
    private ImSessionDao mImSessionDao;
    private ImMessageDao mImMessageDao;
    private List<ImMessage> mImMessage;
    private List<ImMessage> mImMessageDelete;
    private ImSession mImSessionDelete;

    private MessageSessionListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        fragmentBarTitleIv.setImageResource(R.mipmap.top_message_ic);
        fragmentBarRightIv.setVisibility(View.VISIBLE);
        fragmentBarRightIv.setImageResource(R.mipmap.message_top_right_ic);

        mImMessageDao = MyBaseApplication.getApplication().getDaoSession().getImMessageDao();

        initListView();
    }

    private void initListView() {
        mAdapter = new MessageSessionListAdapter(getActivity(), this);
        messageListview.setAdapter(mAdapter);
        messageListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImSession mImSession = mImSessionDao.queryBuilder()
                        .where(ImSessionDao.Properties.UserId.eq(mAdapter.getItem(position).getUserId())).build().unique();
                if (mImSession!=null) {
                    mImSession.setUnreadNum(0);
                    mImSessionDao.update(mImSession);
                    mAdapter.clearUnreadNum(position);
                    startActivity(P2PChatActivity.buildIntent(getActivity(), mAdapter.getItem(position).getUserId()
                            , mAdapter.getItem(position).getNickName(), mAdapter.getItem(position).getSex()
                            , mAdapter.getItem(position).getIcon(), mAdapter.getItem(position).getVerify_status()));
                }
            }
        });
        messageListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                PromptDialog.Builder builder = new PromptDialog.Builder(getActivity());
                builder.setMessage("您确定要该好友从列表中删除吗？");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mImMessageDelete = mImMessageDao.queryBuilder().where(ImMessageDao.Properties.ChatId.eq(mAdapter.getItem(position).getUserId()), ImMessageDao.Properties.LoginUserId.eq(
                                String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId()))).build().list();
                        for (ImMessage imMessageDelete : mImMessageDelete) {
                            mImMessageDao.delete(imMessageDelete);
                        }

                        mImSessionDelete = mImSessionDao.queryBuilder().where(ImSessionDao.Properties.UserId.eq(mAdapter.getItem(position).getUserId()), ImSessionDao.Properties.LoginUserId.eq(
                                String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId()))).build().unique();
                        if (mImSessionDelete!=null) {
                            mImSessionDao.delete(mImSessionDelete);
                            mAdapter.deleteItemById(position);
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });
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
        if (isLogin()) {
            setUnreadMessageNum();
        } else {
            if (mAdapter!=null) {
                mAdapter.clearData();
            }
        }
    }

    private void setUnreadMessageNum() {
        mImSessionDao = MyBaseApplication.getApplication().getDaoSession().getImSessionDao();
        mImSession = mImSessionDao.queryBuilder().where(ImSessionDao.Properties.NickName.notEq(""), ImSessionDao.Properties.LoginUserId.eq(
                PreferenceUtil.getPreferences(getActivity(), PreferenceUtil.PARM_USERID))).build().list();
        if (mImSession!=null) {
            mAdapter.addBody(mImSession);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e("setUserVisibleHint");
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onMessageEvent(YunXinMessageEvent event) {//参数必须是ClassEvent类型, 否则不会调用此方法
        if (mAdapter!=null) {
            mAdapter.setChange(true);
        }
        setUnreadMessageNum();
        ((MainActivity)getActivity()).countUnReadNum();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    @Override
    public void onItemClick(View view, int position) {

    }
}
