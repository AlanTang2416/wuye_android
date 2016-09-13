package com.atman.wysq.ui.yunxinfriend;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.P2PChatAdapter;
import com.atman.wysq.model.bean.ImMessage;
import com.atman.wysq.model.bean.ImSession;
import com.atman.wysq.model.event.YunXinMessageEvent;
import com.atman.wysq.model.greendao.gen.ImMessageDao;
import com.atman.wysq.model.greendao.gen.ImSessionDao;
import com.atman.wysq.model.response.ChatAudioModel;
import com.atman.wysq.ui.PictureBrowsingActivity;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.yunxin.model.ContentTypeInter;
import com.atman.wysq.yunxin.model.GuessAttachment;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.media.player.AudioPlayer;
import com.netease.nimlib.sdk.media.player.OnPlayListener;
import com.netease.nimlib.sdk.media.record.AudioRecorder;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/1 14:07
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class P2PChatActivity extends MyBaseActivity implements EditCheckBack, IAudioRecordCallback, P2PChatAdapter.P2PAdapterInter {

    @Bind(R.id.p2pchat_lv)
    PullToRefreshListView p2pChatLv;
    @Bind(R.id.p2pchat_record_iv)
    ImageView p2pchatRecordIv;
    @Bind(R.id.blogdetail_addcomment_et)
    MyCleanEditText blogdetailAddcommentEt;
    @Bind(R.id.p2pchat_record_bt)
    Button p2pchatRecordBt;
    @Bind(R.id.blogdetail_addemol_iv)
    ImageView blogdetailAddemolIv;
    @Bind(R.id.p2pchat_add_iv)
    ImageView p2pchatAddIv;
    @Bind(R.id.p2pchat_send_bt)
    Button p2pchatSendBt;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @Bind(R.id.p2pchat_add_ll)
    LinearLayout p2pchatAddLl;
    @Bind(R.id.timer)
    Chronometer timer;
    @Bind(R.id.timer_tip)
    TextView timerTip;
    @Bind(R.id.layoutPlayAudio)
    FrameLayout layoutPlayAudio;

    private Context mContext = P2PChatActivity.this;
    private String id;
    private String nick;
    private String sex;
    private String icon;
    private int verify_status;

    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private final int maxDuration = 60;
    private Uri imageUri;
    private String path;
    protected AudioRecorder audioMessageHelper;
    private boolean touched = false; // 是否按着
    private boolean started = false;
    private boolean cancelled = false;

    private ImSessionDao mImSessionDao;
    private ImSession mImSession;
    private ImMessageDao mImMessageDao;
    private List<ImMessage> mImMessage;
    private List<ImMessage> mImMessageDelete;
    private ImSession mImSessionDelete;
    private List<ImMessage> mImMessageUpdata;
    private P2PChatAdapter mAdapter;
    private AudioPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2pchat);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static Intent buildIntent(Context context, String id, String nick, String sex
            , String icon, int verify_status) {
        Intent intent = new Intent(context, P2PChatActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("nick", nick);
        intent.putExtra("sex", sex);
        intent.putExtra("icon", icon);
        intent.putExtra("verify_status", verify_status);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        id = getIntent().getStringExtra("id");
        nick = getIntent().getStringExtra("nick");
        sex = getIntent().getStringExtra("sex");
        icon = getIntent().getStringExtra("icon");
        verify_status = getIntent().getIntExtra("verify_status", 0);

        player = new AudioPlayer(mContext);
        setBarTitleTx(nick);
        getBarBackLl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                finish();
            }
        });
        getBarBackIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                finish();
            }
        });
        setBarRightTx("清空").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                PromptDialog.Builder builder = new PromptDialog.Builder(mContext);
                builder.setMessage("您确定要清空与该好友的聊天记录吗？");
                builder.setPositiveButton("清空", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mImMessageDelete = mImMessageDao.queryBuilder().where(ImMessageDao.Properties.ChatId.eq(id), ImMessageDao.Properties.LoginUserId.eq(
                                String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId()))).build().list();
                        for (ImMessage imMessageDelete : mImMessageDelete) {
                            mImMessageDao.delete(imMessageDelete);
                        }
                        mAdapter.clearData();

                        mImSessionDelete = mImSessionDao.queryBuilder().where(ImSessionDao.Properties.UserId.eq(id), ImSessionDao.Properties.LoginUserId.eq(
                                String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId()))).build().unique();
                        if (mImSessionDelete!=null) {
                            LogUtils.e("mImSessionDelete");
                            mImSessionDao.delete(mImSessionDelete);
                            EventBus.getDefault().post(new YunXinMessageEvent());
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
            }
        });

        blogdetailAddcommentEt.addTextChangedListener(new MyTextWatcherTwo(this));

        p2pchatRecordBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touched = true;
                    initAudioRecord();
                    onStartAudioRecord();
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                        || event.getAction() == MotionEvent.ACTION_UP) {
                    touched = false;
                    onEndAudioRecord(isCancelled(v, event));
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    touched = false;
                    cancelAudioRecord(isCancelled(v, event));
                }
                return false;
            }
        });

        ReceiveMessageObserver(true);
        initSeedResult(true);

        initListView();

        mImMessageDao = MyBaseApplication.getApplication().getDaoSession().getImMessageDao();
        mImSessionDao = MyBaseApplication.getApplication().getDaoSession().getImSessionDao();
        LogUtils.e("id："+id);
        mImMessage = mImMessageDao.queryBuilder().where(ImMessageDao.Properties.ChatId.eq(id)).build().list();
        LogUtils.e("mImMessage.size()："+mImMessage.size());
        mAdapter.addImMessageDao(mImMessage);
        p2pChatLv.getRefreshableView().post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
//                p2pChatLv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount() - 1);
                p2pChatLv.getRefreshableView().setSelection(mAdapter.getCount());
            }
        });
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.DISABLED, p2pChatLv);
        mAdapter = new P2PChatAdapter(mContext, getmWidth(), p2pChatLv, this);
        p2pChatLv.setAdapter(mAdapter);
    }

    /**
     * 取消语音录制
     *
     * @param cancel
     */
    private void cancelAudioRecord(boolean cancel) {
        // reject
        if (!started) {
            return;
        }
        // no change
        if (cancelled == cancel) {
            return;
        }

        cancelled = cancel;
        updateTimerTip(cancel);
    }

    /**
     * 初始化AudioRecord
     */
    private void initAudioRecord() {
        if (audioMessageHelper == null) {
            audioMessageHelper = new AudioRecorder(mContext, RecordType.AAC, maxDuration, this);
        }
    }

    /**
     * 开始语音录制
     */
    private void onStartAudioRecord() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        started = audioMessageHelper.startRecord();
        cancelled = false;
        if (started == false) {
            showToast("初始化录音失败");
            return;
        }

        if (!touched) {
            return;
        }

        p2pchatRecordBt.setText("松开  结束");

        updateTimerTip(false); // 初始化语音动画状态
        playAudioRecordAnim();
    }

    /**
     * 开始语音录制动画
     */
    private void playAudioRecordAnim() {
        layoutPlayAudio.setVisibility(View.VISIBLE);
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ("1:00".equals(chronometer.getText().toString())) {
                    Message message = new Message();
                    message.what = 1;
                    hand.sendMessage(message);
                }
            }
        });
    }

    Handler hand = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    touched = false;
                    onEndAudioRecord(false);
                    break;
            }
            super.handleMessage(msg);
        }

    };

    /**
     * 结束语音录制动画
     */
    private void stopAudioRecordAnim() {
        layoutPlayAudio.setVisibility(View.GONE);
        timer.stop();
        timer.setBase(SystemClock.elapsedRealtime());
    }

    /**
     * 结束语音录制
     *
     * @param cancel
     */
    private void onEndAudioRecord(boolean cancel) {
        getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        audioMessageHelper.completeRecord(cancel);
        p2pchatRecordBt.setText("按住 说话");
        stopAudioRecordAnim();
    }

    // 上滑取消录音判断
    private static boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth()
                || event.getRawY() < location[1] - 40) {
            return true;
        }

        return false;
    }

    /**
     * 正在进行语音录制和取消语音录制，界面展示
     *
     * @param cancel
     */
    private void updateTimerTip(boolean cancel) {
        if (cancel) {
            timerTip.setText("松开手指，取消发送");
        } else {
            timerTip.setText("手指上滑，取消发送");
        }
    }

    Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> messages) {
            // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
            for (int i=0;i<messages.size();i++) {
                LogUtils.e("messages.get(i).getMsgType():"+messages.get(i).getMsgType());
                if (messages.get(i).getRemoteExtension()!=null) {
                    ImMessage temp = null;
                    int messageType = Integer.parseInt(messages.get(i).getRemoteExtension().get("contentType").toString());
                    if (messageType == ContentTypeInter.contentTypeText) {
                        temp = new ImMessage(null, messages.get(i).getUuid()
                                , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                                , messages.get(i).getSessionId()
                                , messages.get(i).getFromAccount()
                                , messages.get(i).getRemoteExtension().get("nickName").toString()
                                , messages.get(i).getRemoteExtension().get("icon").toString()
                                , messages.get(i).getRemoteExtension().get("sex").toString()
                                , Integer.parseInt(messages.get(i).getRemoteExtension().get("verify_status").toString())
                                , false, System.currentTimeMillis()
                                , Integer.parseInt(messages.get(i).getRemoteExtension().get("contentType").toString())
                                , messages.get(i).getContent(), "", "", "", "", "", "", "", "", 0, 0, false, 1);
                    } else if (messageType == ContentTypeInter.contentTypeImage) {
                        temp = new ImMessage(null, messages.get(i).getUuid()
                                , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                                , messages.get(i).getSessionId()
                                , messages.get(i).getFromAccount()
                                , messages.get(i).getRemoteExtension().get("nickName").toString()
                                , messages.get(i).getRemoteExtension().get("icon").toString()
                                , messages.get(i).getRemoteExtension().get("sex").toString()
                                , Integer.parseInt(messages.get(i).getRemoteExtension().get("verify_status").toString())
                                , false, System.currentTimeMillis()
                                , Integer.parseInt(messages.get(i).getRemoteExtension().get("contentType").toString())
                                , "", ((FileAttachment)messages.get(i).getAttachment()).getPathForSave()
                                , ((FileAttachment)messages.get(i).getAttachment()).getUrl()
                                , ((FileAttachment)messages.get(i).getAttachment()).getThumbPathForSave(), "", "", "", "", "", 0, 0, false, 1);
                        if (isOriginImageHasDownloaded(messages.get(i))) {
                            AbortableFuture future = NIMClient.getService(MsgService.class).downloadAttachment(messages.get(i), true);
                            future.setCallback(callback);
                        }
                    } else if (messageType == ContentTypeInter.contentTypeFinger) {
                        LogUtils.e("contentFinger:"+messages.get(i).getRemoteExtension().get("contentFinger"));
                        String contentFinger = messages.get(i).getRemoteExtension().get("contentFinger").toString();
                        int fingerValue = Integer.parseInt(messages.get(i).getRemoteExtension().get("contentFinger").toString());
                        String str = "[石头]";
                        if (contentFinger.equals("2")) {
                            str = "[剪刀]";
                            fingerValue = 2;
                        } else if (contentFinger.equals("3")) {
                            str = "[布]";
                            fingerValue = 3;
                        }
                        temp = new ImMessage(null, messages.get(i).getUuid()
                                , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                                , messages.get(i).getSessionId()
                                , messages.get(i).getFromAccount()
                                , messages.get(i).getRemoteExtension().get("nickName").toString()
                                , messages.get(i).getRemoteExtension().get("icon").toString()
                                , messages.get(i).getRemoteExtension().get("sex").toString()
                                , Integer.parseInt(messages.get(i).getRemoteExtension().get("verify_status").toString())
                                , false, System.currentTimeMillis()
                                , Integer.parseInt(messages.get(i).getRemoteExtension().get("contentType").toString())
                                , str, "", "", "", "", "", "", "", "", 0, fingerValue, false, 1);
                    } else if (messageType == ContentTypeInter.contentTypeAudio) {
                        ChatAudioModel mChatAudioModel = new Gson().fromJson(messages.get(i).getAttachment().toJson(true), ChatAudioModel.class);
                        temp = new ImMessage(null, messages.get(i).getUuid()
                                , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                                , messages.get(i).getSessionId()
                                , messages.get(i).getFromAccount(), messages.get(i).getRemoteExtension().get("nickName").toString()
                                , messages.get(i).getRemoteExtension().get("icon").toString(), messages.get(i).getRemoteExtension().get("sex").toString()
                                , Integer.parseInt(messages.get(i).getRemoteExtension().get("verify_status").toString())
                                , false, System.currentTimeMillis(), Integer.parseInt(messages.get(i).getRemoteExtension().get("contentType").toString())
                                , "[语音]", "", "", "", "", "", "",
                                ((FileAttachment)messages.get(i).getAttachment()).getPathForSave()
                                , ((FileAttachment)messages.get(i).getAttachment()).getUrl(), mChatAudioModel.getDur(), 0, false, 1);
                    } else if (messageType == ContentTypeInter.contentTypeImageSmall) {
                        temp = new ImMessage(null, messages.get(i).getUuid()
                                , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                                , messages.get(i).getSessionId()
                                , messages.get(i).getFromAccount()
                                , messages.get(i).getRemoteExtension().get("nickName").toString()
                                , messages.get(i).getRemoteExtension().get("icon").toString()
                                , messages.get(i).getRemoteExtension().get("sex").toString()
                                , Integer.parseInt(messages.get(i).getRemoteExtension().get("verify_status").toString())
                                , false, System.currentTimeMillis()
                                , Integer.parseInt(messages.get(i).getRemoteExtension().get("contentType").toString())
                                , "", "", "", "", ((FileAttachment)messages.get(i).getAttachment()).getPathForSave()
                                , ((FileAttachment)messages.get(i).getAttachment()).getUrl()
                                , ((FileAttachment)messages.get(i).getAttachment()).getThumbPathForSave(), "", "", 0, 0, false, 1);
                        if (isOriginImageHasDownloaded(messages.get(i))) {
                            AbortableFuture future = NIMClient.getService(MsgService.class).downloadAttachment(messages.get(i), true);
                            future.setCallback(callback);
                        }
                    }
                    mAdapter.addImMessageDao(temp);
                    setSession(temp);
                }
            }
        }
    };

    private void ReceiveMessageObserver(boolean b) {
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, b);
    }

    private void initSeedResult(boolean b) {
        // 监听消息发送状态的变化通知
        NIMClient.getService(MsgServiceObserve.class).observeMsgStatus(new Observer<IMMessage>() {
            @Override
            public void onEvent(IMMessage imMessage) {
                mImMessageUpdata = mImMessageDao.queryBuilder().where(ImMessageDao.Properties.Uuid.eq(imMessage.getUuid())).build().list();
                for (int i=0;i<mImMessageUpdata.size();i++) {
                    if (imMessage.getStatus()==MsgStatusEnum.success) {
                        mImMessageUpdata.get(i).setIsSeedSuccess(1);
                        mAdapter.setImMessageStatus(imMessage.getUuid(), 1);
                    } else {
                        mImMessageUpdata.get(i).setIsSeedSuccess(2);
                        mAdapter.setImMessageStatus(imMessage.getUuid(), 2);
                    }
                    mImMessageDao.update(mImMessageUpdata.get(i));
                }
            }
        }, b);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReceiveMessageObserver(false);
        initSeedResult(false);
    }

    @OnClick({R.id.p2pchat_record_iv, R.id.p2pchat_add_iv, R.id.p2pchat_send_bt, R.id.blogdetail_addemol_iv
            , R.id.p2pchat_add_picture_tv, R.id.p2pchat_add_camera_tv, R.id.p2pchat_add_gif_tv, R.id.p2pchat_add_finger_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.blogdetail_addemol_iv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                if (llFacechoose.getVisibility() == View.GONE) {
                    if (isIMOpen()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                    }
                    llFacechoose.setVisibility(View.VISIBLE);
                    blogdetailAddemolIv.setImageResource(R.mipmap.chat_key_ic);
                } else {
                    llFacechoose.setVisibility(View.GONE);
                    blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                }
                blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                p2pchatRecordBt.setVisibility(View.GONE);
                p2pchatAddLl.setVisibility(View.GONE);
                p2pchatRecordIv.setImageResource(R.mipmap.chat_record_ic);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.p2pchat_record_iv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                if (llFacechoose.getVisibility() == View.VISIBLE) {
                    llFacechoose.setVisibility(View.GONE);
                }
                if (blogdetailAddcommentEt.getVisibility() == View.VISIBLE) {
                    if (isIMOpen()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                    }
                    blogdetailAddcommentEt.setVisibility(View.GONE);
                    p2pchatRecordBt.setVisibility(View.VISIBLE);
                    p2pchatRecordIv.setImageResource(R.mipmap.chat_key_ic);
                } else {
                    blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                    p2pchatRecordBt.setVisibility(View.GONE);
                    p2pchatRecordIv.setImageResource(R.mipmap.chat_record_ic);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                }
                blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
                p2pchatAddLl.setVisibility(View.GONE);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.p2pchat_add_iv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                if (p2pchatAddLl.getVisibility() == View.GONE) {
                    if (isIMOpen()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                    }
                    p2pchatAddLl.setVisibility(View.VISIBLE);
                } else {
                    p2pchatAddLl.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                }
                blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                p2pchatRecordBt.setVisibility(View.GONE);
                llFacechoose.setVisibility(View.GONE);
                p2pchatRecordIv.setImageResource(R.mipmap.chat_record_ic);
                blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.p2pchat_send_bt:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                // 创建文本消息
                IMMessage message = MessageBuilder.createTextMessage(id, SessionTypeEnum.P2P
                        , blogdetailAddcommentEt.getText().toString());
                seedMessage(message, ContentTypeInter.contentTypeText, "", "");
                blogdetailAddcommentEt.setText("");
                break;
            case R.id.p2pchat_add_picture_tv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType("image/*");
                startActivityForResult(getAlbum, CHOOSE_BIG_PICTURE);
                break;
            case R.id.p2pchat_add_camera_tv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                break;
            case R.id.p2pchat_add_gif_tv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                startActivityForResult(SelectGiftActivity.buildIntent(mContext, id), Common.toSelectGift);
                p2pchatAddLl.setVisibility(View.GONE);
                break;
            case R.id.p2pchat_add_finger_tv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                GuessAttachment attachment = new GuessAttachment();
                IMMessage CustomMessage = MessageBuilder.createCustomMessage(id, SessionTypeEnum.P2P, attachment.getValue().getDesc(), attachment);
                seedMessage(CustomMessage, ContentTypeInter.contentTypeFinger, "", attachment.getValue().getDesc());
                break;
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(com.base.baselibs.R.anim.activity_bottom_in, com.base.baselibs.R.anim.activity_bottom_out);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
        }
    }

    private void seedMessage(IMMessage message, int contentType, String contentImageSUrl, String contentFinger) {
        Map<String, Object> map = new HashMap<>();
        map.put("contentType", contentType);
        map.put("contentImageSUrl", contentImageSUrl);
        map.put("contentFinger", contentFinger);
        map.put("nickName", MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getNickName());
        map.put("icon", MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon());
        map.put("sex", MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex());
        map.put("verify_status", MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status());
        message.setRemoteExtension(map);
        NIMClient.getService(MsgService.class).sendMessage(message, true);

        ImMessage temp = null;
        if (contentType==ContentTypeInter.contentTypeText) {
            temp= new ImMessage(null, message.getUuid()
                    , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId()), id
                    , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getNickName()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()
                    , true, System.currentTimeMillis(), contentType, message.getContent(), "", "", "", "", "", "", "", "", 0, 0, false, 0);
        } else if (contentType==ContentTypeInter.contentTypeImage) {
            temp = new ImMessage(null, message.getUuid()
                    , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId()), id
                    , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getNickName()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()
                    , true, System.currentTimeMillis(), contentType, "［图片］", ((FileAttachment)message.getAttachment()).getPathForSave()
                    , ((FileAttachment)message.getAttachment()).getUrl()
                    , ((FileAttachment)message.getAttachment()).getThumbPathForSave(), "", "", "", "", "", 0, 0, false, 0);
        } else if (contentType==ContentTypeInter.contentTypeFinger) {
            LogUtils.e("contentFinger:"+contentFinger);
            int fingerValue = 1;
            String str = "[石头]";
            if (contentFinger.equals("2")) {
                str = "[剪刀]";
                fingerValue = 2;
            } else if (contentFinger.equals("3")) {
                str = "[布]";
                fingerValue = 3;
            }
            temp= new ImMessage(null, message.getUuid()
                    , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId()), id
                    , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getNickName()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()
                    , true, System.currentTimeMillis(), contentType, str, "", "", "", "", "", "", "", "", 0, fingerValue, false, 0);
        } else if (contentType==ContentTypeInter.contentTypeAudio) {
            ChatAudioModel mChatAudioModel = new Gson().fromJson(message.getAttachment().toJson(true), ChatAudioModel.class);
            temp = new ImMessage(null, message.getUuid()
                    , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId()), id
                    , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getNickName()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()
                    , true, System.currentTimeMillis(), contentType, "[语音]", "", "", "", "", "", "", ((FileAttachment)message.getAttachment()).getPathForSave()
                    , ((FileAttachment)message.getAttachment()).getUrl(), mChatAudioModel.getDur(), 0, false, 0);
        } else if (contentType==ContentTypeInter.contentTypeImageSmall) {
            temp = new ImMessage(null, message.getUuid()
                    , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId()), id
                    , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getNickName()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()
                    , true, System.currentTimeMillis(), contentType, "［图片］", "", "", "", ((FileAttachment)message.getAttachment()).getPathForSave()
                    , ((FileAttachment)message.getAttachment()).getUrl()
                    , ((FileAttachment)message.getAttachment()).getThumbPathForSave(), "", "", 0, 0, false, 0);
        }
        mAdapter.addImMessageDao(temp);
        mImMessageDao.insertOrReplace(temp);
        setSession(temp);
    }

    private void setSession(ImMessage message) {
        mImSession = mImSessionDao.queryBuilder().where(ImSessionDao.Properties.UserId.eq(id)).build().unique();
        if (mImSession==null) {
            ImSession mImSessionTemp = new ImSession(id, String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                    , message.getContent(), nick, icon, sex, verify_status, System.currentTimeMillis(), 0);
            mImSessionDao.insertOrReplace(mImSessionTemp);
        } else {
            mImSession.setContent(message.getContent());
            mImSession.setTime(System.currentTimeMillis());
            mImSession.setUnreadNum(0);
            mImSessionDao.update(mImSession);
        }
    }


    @Override
    public void isNull() {
        if (TextUtils.isEmpty(blogdetailAddcommentEt.getText().toString())) {
            p2pchatSendBt.setVisibility(View.GONE);
            p2pchatAddIv.setVisibility(View.VISIBLE);
        } else {
            p2pchatSendBt.setVisibility(View.VISIBLE);
            p2pchatAddIv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toSelectGift) {
            String text = data.getStringExtra("text");
            File file = ImageLoader.getInstance().getDiskCache().get(Common.ImageUrl+data.getStringExtra("url"));
            IMMessage message = MessageBuilder.createImageMessage(id, SessionTypeEnum.P2P, file, "");
            seedMessage(message, ContentTypeInter.contentTypeImageSmall, "", "");
            if (!text.isEmpty() && !TextUtils.isEmpty(text)) {
                IMMessage messagetext = MessageBuilder.createTextMessage(id, SessionTypeEnum.P2P
                        , text.toString());
                seedMessage(messagetext, ContentTypeInter.contentTypeText, "", "");
            }
        } else {
            if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
                imageUri = data.getData();
            } else if (requestCode == TAKE_BIG_PICTURE) {
                imageUri = Uri.parse("file:///" + path);
            }
            if (imageUri != null) {
                IMMessage message = null;
                // 创建图片消息
                if (imageUri.toString().contains("content:")){
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(imageUri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);
                    LogUtils.e("path:"+path);
                    message = MessageBuilder.createImageMessage(id, SessionTypeEnum.P2P,
                            new File(path), "");
                } else {
                    LogUtils.e("path:"+path);
                    message = MessageBuilder.createImageMessage(id, SessionTypeEnum.P2P,
                            new File(imageUri.getPath()), "");
                }
                seedMessage(message, ContentTypeInter.contentTypeImage, "", "");
            }
        }
    }

    @Override
    public void onRecordReady() {

    }

    @Override
    public void onRecordStart(File file, RecordType recordType) {

    }

    @Override
    public void onRecordSuccess(File file, long l, RecordType recordType) {
        IMMessage audioMessage = MessageBuilder.createAudioMessage(id, SessionTypeEnum.P2P, file, l);
        seedMessage(audioMessage, ContentTypeInter.contentTypeAudio, "", "");
    }

    @Override
    public void onRecordFail() {

    }

    @Override
    public void onRecordCancel() {

    }

    @Override
    public void onRecordReachedMaxTime(int i) {

    }

    private RequestCallback<List<IMMessage>> callback = new RequestCallback<List<IMMessage>>() {
        @Override
        public void onSuccess(List<IMMessage> imMessages) {
            if (imMessages!=null) {
                for (int i=0;i<imMessages.size();i++) {
//                    LogUtils.e("p2p>>>>image>>>>getPath:"+((FileAttachment)imMessages.get(i).getAttachment()).getPath());
//                    LogUtils.e("p2p>>>>image>>>>getPathForSave:"+((FileAttachment)imMessages.get(i).getAttachment()).getPathForSave());
//                    LogUtils.e("p2p>>>>image>>>>getThumbPath:"+((FileAttachment)imMessages.get(i).getAttachment()).getThumbPath());
//                    LogUtils.e("p2p>>>>image>>>>getThumbPathForSave:"+((FileAttachment)imMessages.get(i).getAttachment()).getThumbPathForSave());
//                    LogUtils.e("p2p>>>>image>>>>getUrl:"+((FileAttachment)imMessages.get(i).getAttachment()).getUrl());
                }
            }
        }

        @Override
        public void onFailed(int i) {

        }

        @Override
        public void onException(Throwable throwable) {

        }
    };

    @Override
    public void onItem(View v, int position) {
        switch (v.getId()) {
            case R.id.item_p2pchat_root_Rl:
                if (isIMOpen()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                }
                blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
                p2pchatRecordIv.setImageResource(R.mipmap.chat_record_ic);
                blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                llFacechoose.setVisibility(View.GONE);
                p2pchatRecordBt.setVisibility(View.GONE);
                p2pchatAddLl.setVisibility(View.GONE);
                break;
            case R.id.item_p2pchat_image_left_iv:
            case R.id.item_p2pchat_image_right_iv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                String imagePath = "";
                if (mAdapter.getItem(position).getImageThumUrl().startsWith("http")) {
                    imagePath = mAdapter.getItem(position).getImageUrl();
                } else {
                    File mFile = new File(mAdapter.getItem(position).getImageFilePath());
                    if (mFile.exists()) {
                        imagePath = "file://"+mAdapter.getItem(position).getImageFilePath();
                    } else {
                        imagePath = mAdapter.getItem(position).getImageUrl();
                    }
                }

                Intent intent = new Intent();
                intent.putExtra("image", imagePath);
                intent.putExtra("num", 0);
                intent.setClass(mContext, PictureBrowsingActivity.class);
                startActivity(intent);
                break;
        }
    }

    private int positionAudio;
    @Override
    public void onItemAudio(View v, int position, final AnimationDrawable animationDrawable) {
        switch (v.getId()) {
            case R.id.item_p2pchat_audio_right_ll:
            case R.id.item_p2pchat_audio_left_ll:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                if ((new File(mAdapter.getItem(position).getAudioFilePath()).exists())) {
                    if (player!=null && positionAudio!=position) {
                        player.stop();
                    }
                    player.setDataSource(mAdapter.getItem(position).getAudioFilePath());
                    player.setOnPlayListener(new OnPlayListener() {
                        @Override
                        public void onPrepared() {

                        }

                        @Override
                        public void onCompletion() {
                            animationDrawable.stop();
                            animationDrawable.selectDrawable(0);
                        }

                        @Override
                        public void onInterrupt() {
                            animationDrawable.stop();
                            animationDrawable.selectDrawable(0);
                        }

                        @Override
                        public void onError(String s) {
                            animationDrawable.stop();
                            animationDrawable.selectDrawable(0);
                        }

                        @Override
                        public void onPlaying(long l) {
                            animationDrawable.start();
                        }
                    });
                    if (animationDrawable.isRunning()) {
                        player.stop();
                    } else {
                        player.start(AudioManager.STREAM_MUSIC);
                    }
                } else {
                    showToast("没有文件");
                }
                positionAudio = position;
                break;
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            p2pChatLv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount());
        }
    };
}
