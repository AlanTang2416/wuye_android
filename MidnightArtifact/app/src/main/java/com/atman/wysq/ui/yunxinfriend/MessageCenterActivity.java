package com.atman.wysq.ui.yunxinfriend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atman.wysq.R;
import com.atman.wysq.model.bean.ImSession;
import com.atman.wysq.model.greendao.gen.ImSessionDao;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.base.baselibs.util.PreferenceUtil;

/**
 * Created by vavid on 2016/9/19.
 */
public class MessageCenterActivity extends MyBaseActivity {

    private Context mContext = MessageCenterActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_messagecenter);
    }

    public static Intent buildIntent(Context context){
        Intent intent = new Intent(context, MessageCenterActivity.class);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("通知中心").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearView();
            }
        });
        setBarRightTx("清空");
        getBarRightRl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearView();
            }
        });
    }

    private void clearView() {
        MyBaseApplication.getApplication().getDaoSession().getTouChuanOtherNoticeDao().deleteAll();
        MyBaseApplication.getApplication().getDaoSession().getTouChuanGiftNoticeDao().deleteAll();
        ImSession temp = MyBaseApplication.getApplication().getDaoSession().getImSessionDao().queryBuilder()
                .where(ImSessionDao.Properties.NickName.eq("通知中心"), ImSessionDao.Properties.LoginUserId.eq(
                PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USERID))).build().unique();
        if (temp!=null) {
            temp.setContent("暂时还没有通知");
            temp.setUnreadNum(0);
            MyBaseApplication.getApplication().getDaoSession().getImSessionDao().update(temp);
        }
    }
}
