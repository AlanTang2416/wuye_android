package com.atman.wysq.ui.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atman.wysq.R;
import com.atman.wysq.ui.MainActivity;
import com.atman.wysq.ui.SplashActivity;
import com.atman.wysq.ui.community.CommentChildrenListActivity;
import com.atman.wysq.ui.community.PostActivity;
import com.atman.wysq.ui.community.PostingsDetailActivity;
import com.atman.wysq.ui.login.LoginActivity;
import com.atman.wysq.ui.mall.order.ConfirmationOrderActivity;
import com.atman.wysq.ui.yunxinfriend.P2PChatActivity;
import com.atman.wysq.ui.yunxinfriend.SelectGiftActivity;
import com.atman.wysq.utils.ScreenObserver;
import com.base.baselibs.base.BaseAppCompatActivity;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.PromptDialog;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/28 16:52
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyBaseActivity extends BaseAppCompatActivity {

    @Bind(R.id.bar_title_tx)
    TextView barTitleTx;
    @Bind(R.id.bar_title_rl)
    RelativeLayout barTitleRl;
    @Bind(R.id.bar_back_iv)
    ImageView barBackIv;
    @Bind(R.id.bar_back_ll)
    LinearLayout barBackLl;
    @Bind(R.id.bar_right_tx)
    TextView barRightTx;
    @Bind(R.id.bar_right_iv)
    ImageView barRightIv;
    @Bind(R.id.bar_right_rl)
    RelativeLayout barRightRl;
    @Bind(R.id.root_bar_rl)
    RelativeLayout rootBarRl;
    @Bind(R.id.root_content_ll)
    LinearLayout rootContentLl;
    @Bind(R.id.bar_title_iv)
    ImageView barTitleIv;

    private boolean mShouldLogin = true;
    private static long lastClickTime = 0;
    protected Activity mAty;
    private Display display;
    protected Gson mGson;
    private ScreenObserver mScreenObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_mybase);
        mAty = this;
        display = getWindowManager().getDefaultDisplay();
        mGson = new Gson();

        mScreenObserver = new ScreenObserver(this);
        mScreenObserver.requestScreenStateUpdate(new ScreenObserver.ScreenStateListener() {
            @Override
            public void onScreenOn() {

            }

            @Override
            public void onScreenOff() {
                if (!MyBaseApplication.getApplication().isFilterLock()) {
                    MyBaseApplication.getApplication().setLock(true);
                }
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {

        rootBarRl = (RelativeLayout) findViewById(R.id.root_bar_rl);
        barTitleRl = (RelativeLayout) findViewById(R.id.bar_title_rl);

        barTitleTx = (TextView) findViewById(R.id.bar_title_tx);
        barTitleIv = (ImageView) findViewById(R.id.bar_title_iv);

        barBackLl = (LinearLayout) findViewById(R.id.bar_back_ll);
        barBackLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        barBackIv = (ImageView) findViewById(R.id.bar_back_iv);
        barBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        barRightTx = (TextView) findViewById(R.id.bar_right_tx);
        barRightIv = (ImageView) findViewById(R.id.bar_right_iv);
        barRightRl = (RelativeLayout) findViewById(R.id.bar_right_rl);

        rootContentLl = (LinearLayout) findViewById(R.id.root_content_ll);
        rootContentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelIM(v);
            }
        });

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(layoutResID, null);
        v.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        rootContentLl.addView(v);
    }

    public LinearLayout getRootContentLl() {
        return rootContentLl;
    }

    public LinearLayout getBarBackLl() {
        return barBackLl;
    }

    public ImageView getBarBackIv() {
        return barBackIv;
    }

    /**
     * 影藏头部
     */
    protected void hideTitleBar() {
        LogUtils.e("rootBarRl:"+rootBarRl);
        if (rootBarRl != null) {
            rootBarRl.setVisibility(View.GONE);
        }
    }

    /**
     * 显示头部
     */
    protected void showTitleBar() {
        if (rootBarRl != null) {
            rootBarRl.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示标题文字，隐藏bar图片
     */
    public TextView setBarTitleTx(String str) {
        barTitleTx.setVisibility(View.VISIBLE);
        barTitleIv.setVisibility(View.GONE);
        barTitleTx.setText(str);
        return barTitleTx;
    }

    /**
     * 显示标题文字，隐藏bar图片
     */
    public TextView setBarTitleTx(int id) {
        barTitleTx.setVisibility(View.VISIBLE);
        barTitleIv.setVisibility(View.GONE);
        barTitleTx.setText(getResources().getString(id));
        return barTitleTx;
    }

    /**
     * 显示标题图片，隐藏标题文字
     */
    public ImageView setBarTitleIv(int id) {
        barTitleTx.setVisibility(View.GONE);
        barTitleIv.setVisibility(View.VISIBLE);
        barTitleIv.setImageResource(id);
        return barTitleIv;
    }

    /**
     * 显示标题右边文字，隐藏标题右边图片
     */
    protected ImageView setBarRightIv() {
        barRightIv.setVisibility(View.VISIBLE);
        barRightTx.setVisibility(View.GONE);
        return barRightIv;
    }

    /**
     * 显示标题右边文字，隐藏标题右边图片
     */
    protected ImageView setBarRightIv(int id) {
        barRightIv.setVisibility(View.VISIBLE);
        barRightTx.setVisibility(View.GONE);
        barRightIv.setBackgroundResource(id);
        return barRightIv;
    }

    /**
     * 显示标题右边
     */
    protected RelativeLayout getBarRightRl() {
        barRightRl.setVisibility(View.VISIBLE);
        return barRightRl;
    }

    /**
     * 显示标题右边图片，隐藏标题右边文字
     */
    protected TextView setBarRightTx(String txt) {
        barRightTx.setVisibility(View.VISIBLE);
        barRightIv.setVisibility(View.GONE);
        barRightTx.setText(txt);
        return barRightTx;
    }

    /**
     * 显示标题右边图片，隐藏标题右边文字
     */
    protected TextView setBarRightTx(int id) {
        barRightTx.setVisibility(View.VISIBLE);
        barRightIv.setVisibility(View.GONE);
        barRightTx.setText(getResources().getString(id));
        return barRightTx;
    }

    public void cancelIM(View v) {
        if (isIMOpen()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    public boolean isIMOpen() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();//isOpen若返回true，则表示输入法打开
    }

    public int getmHight() {
        return display.getHeight();
    }

    public int getmWidth() {
        return display.getWidth();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceUtil.getIntPreferences(this, PreferenceUtil.PARM_GESTURE_ERROR)<=0
                && PreferenceUtil.getBoolPreferences(this, PreferenceUtil.PARM_ISOPEN_GESTURE)
                && mShouldLogin && isLogin()) {
            clearData();
            finish();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (mShouldLogin) {
            if (!isLogin()) {
                if (MyBaseApplication.getApplication().isError) {
                    mAty.finish();
                } else {
                    //需要登陆状态，跳转到登陆界面
                    startActivity(LoginActivity.createIntent(this, getIntent()));
                }
                return;
            }
        }
        boolean isOpen = PreferenceUtil.getBoolPreferences(this, PreferenceUtil.PARM_ISOPEN_GESTURE);
        if (!(mAty instanceof BaseGestureLockActivity) && !(mAty instanceof SplashActivity)
                && MyBaseApplication.getApplication().isLock() && isOpen && isLogin()) {
            startActivity(new Intent(this, BaseGestureLockActivity.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止监听screen状态
        mScreenObserver.stopScreenStateUpdate();
    }

    @Override
    public void clearData() {
        super.clearData();
        MyBaseApplication.getApplication().cleanLoginData();
        Intent mIntent = new Intent();
        setResult(RESULT_OK,mIntent);
        mAty.finish();
    }

    /**
     * 关闭登陆检查
     */
    public void disableLoginCheck() {
        mShouldLogin = false;
    }

    /**
     * 判断用户是否登陆
     *
     * @return
     */
    public boolean isLogin() {
        return MyBaseApplication.getApplication().isLogined();
    }

    /**
     * 防止快速点击,启动多个同样的界面
     *
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (!(mAty instanceof ConfirmationOrderActivity)
                    && !(mAty instanceof CommentChildrenListActivity)
                    && !(mAty instanceof PostActivity)
                    && !(mAty instanceof PostingsDetailActivity)
                    && !(mAty instanceof SelectGiftActivity)
                    && !(mAty instanceof P2PChatActivity)
                    && isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 防止快速点击,启动多个同样的界面
     *
     * @return
     */
    public boolean isFastDoubleClick() {
        long now = System.currentTimeMillis();
        long timeD = now - lastClickTime;
        lastClickTime = now;
        return timeD <= 500;
    }

    public void toPhone(Context context, String phoneNumber) {
        if (!phoneNumber.startsWith("tel:")){
            phoneNumber = "tel:" + phoneNumber;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse(phoneNumber);
        intent.setData(data);
        context.startActivity(intent);
    }

    @Override
    protected void onStop() {
        if (!isAppOnFreground() && !MyBaseApplication.getApplication().isFilterLock()) {
            MyBaseApplication.getApplication().setLock(true);
        }
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && ((this instanceof MainActivity) || (this instanceof BaseGestureLockActivity))) {// 返回键
            exitBy2Click();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            MyBaseApplication.getApplication().initObserver(false);
            MyBaseApplication.getApplication().getDaoSession().getAddFriendRecordDao().deleteAll();
            finish();
            System.exit(0);
        }
    }

    /**
     * 是否在后台
     * @return
     */
    public boolean isAppOnFreground(){
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        String curPackageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> app = am.getRunningAppProcesses();
        if(app==null){
            return false;
        }
        for(ActivityManager.RunningAppProcessInfo a:app){
            if(a.processName.equals(curPackageName)&&
                    a.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                return true;
            }
        }
        return false;
    }

    public void showLogin() {
        PromptDialog.Builder builder = new PromptDialog.Builder(this);
        builder.setMessage("请登录午夜神器以继续使用！");
        builder.setPositiveButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(mAty, LoginActivity.class));
            }
        });
        builder.show();
    }

    public void showWraning(String str) {
        PromptDialog.Builder builder = new PromptDialog.Builder(this);
        builder.setMessage(str);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public File createFile(String versionName){
        File updateDir = null;
        File updateFile = null;
        if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {

            updateDir = new File(Environment.getExternalStorageDirectory()+ "/wysq/downLoad/");
            updateFile = new File(updateDir + "/" + versionName + ".apk");

            if (!updateDir.exists()) {
                updateDir.mkdirs();
            }
            if (!updateFile.exists()) {
                try {
                    updateFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return updateFile;
    }

    public boolean isOriginImageHasDownloaded(final IMMessage message) {
        if (message.getAttachStatus() == AttachStatusEnum.transferred &&
                !TextUtils.isEmpty(((FileAttachment) message.getAttachment()).getPath())) {
            LogUtils.e("imagePath:"+((FileAttachment) message.getAttachment()).getPath());
            return true;
        }
        return false;
    }
}
