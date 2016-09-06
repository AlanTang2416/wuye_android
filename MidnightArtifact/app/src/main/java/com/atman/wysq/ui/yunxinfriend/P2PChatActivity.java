package com.atman.wysq.ui.yunxinfriend;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import com.atman.wysq.model.greendao.gen.ImMessageDao;
import com.atman.wysq.model.greendao.gen.ImSessionDao;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.yunxin.model.ContentTypeInter;
import com.atman.wysq.yunxin.model.GuessAttachment;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.ThumbnailQuery;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.media.record.AudioRecorder;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

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
public class P2PChatActivity extends MyBaseActivity implements EditCheckBack, IAudioRecordCallback {

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
    @Bind(R.id.ll1)
    LinearLayout ll1;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @Bind(R.id.p2pchat_add_picture_tv)
    TextView p2pchatAddPictureTv;
    @Bind(R.id.p2pchat_add_camera_tv)
    TextView p2pchatAddCameraTv;
    @Bind(R.id.p2pchat_add_gif_tv)
    TextView p2pchatAddGifTv;
    @Bind(R.id.p2pchat_add_finger_tv)
    TextView p2pchatAddFingerTv;
    @Bind(R.id.p2pchat_add_ll)
    LinearLayout p2pchatAddLl;
    @Bind(R.id.timer)
    Chronometer timer;
    @Bind(R.id.timer_tip)
    TextView timerTip;
    @Bind(R.id.timer_tip_container)
    LinearLayout timerTipContainer;
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
    private ImMessage mImMessageUpdata;
    private P2PChatAdapter mAdapter;

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

        setBarTitleTx(nick);
        setBarRightTx("清空").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromptDialog.Builder builder = new PromptDialog.Builder(mContext);
                builder.setMessage("您确定要清空与该好友的聊天记录吗？");
                builder.setPositiveButton("清空", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mImMessageDelete = mImMessageDao.queryBuilder().where(ImMessageDao.Properties.ChatId.eq(id)).build().list();
                        for (ImMessage imMessageDelete : mImMessageDelete) {
                            mImMessageDao.delete(imMessageDelete);
                        }
                        mAdapter.clearData();
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
        mImMessage = mImMessageDao.queryBuilder().where(ImMessageDao.Properties.ChatId.eq(id)).build().list();
        mAdapter.addImMessageDao(mImMessage);
        p2pChatLv.getRefreshableView().smoothScrollToPosition(p2pChatLv.getRefreshableView().getBottom());
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.DISABLED, p2pChatLv);
        mAdapter = new P2PChatAdapter(mContext, p2pChatLv);
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
            audioMessageHelper = new AudioRecorder(mContext, RecordType.AAC, AudioRecorder.DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND, this);
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
    }

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
                ImMessage temp = null;
                if (messages.get(i).getMsgType() == MsgTypeEnum.text) {
                    temp = new ImMessage(messages.get(i).getUuid()
                            , messages.get(i).getSessionId()
                            , messages.get(i).getFromAccount()
                            , messages.get(i).getRemoteExtension().get("nickName").toString()
                            , messages.get(i).getRemoteExtension().get("icon").toString()
                            , messages.get(i).getRemoteExtension().get("sex").toString()
                            , Integer.parseInt(messages.get(i).getRemoteExtension().get("verify_status").toString())
                            , false, System.currentTimeMillis()
                            , Integer.parseInt(messages.get(i).getRemoteExtension().get("contentType").toString())
                            , messages.get(i).getContent(), "", "", "", "", "", "", "", "", 0, 0, false, 1);
                } else if (messages.get(i).getMsgType() == MsgTypeEnum.image) {
//                    LogUtils.e("get>>>>image>>>>getPath:"+((FileAttachment)messages.get(i).getAttachment()).getPath());
//                    LogUtils.e("get>>>>image>>>>getPathForSave:"+((FileAttachment)messages.get(i).getAttachment()).getPathForSave());
//                    LogUtils.e("get>>>>image>>>>getThumbPath:"+((FileAttachment)messages.get(i).getAttachment()).getThumbPath());
//                    LogUtils.e("get>>>>image>>>>getThumbPathForSave:"+((FileAttachment)messages.get(i).getAttachment()).getThumbPathForSave());
//                    LogUtils.e("get>>>>image>>>>getUrl:"+((FileAttachment)messages.get(i).getAttachment()).getUrl());
                    temp = new ImMessage(messages.get(i).getUuid()
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
                }
                mAdapter.addImMessageDao(temp);
                setSession(messages.get(i));
            }
            p2pChatLv.getRefreshableView().smoothScrollToPosition(p2pChatLv.getRefreshableView().getBottom());
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
                mImMessageUpdata = mImMessageDao.queryBuilder().where(ImMessageDao.Properties.Uuid.eq(imMessage.getUuid())).build().unique();
                if (mImMessageUpdata!=null) {
                    if (imMessage.getStatus()==MsgStatusEnum.success) {
                        mImMessageUpdata.setIsSeedSuccess(1);
                        mAdapter.setImMessageStatus(imMessage.getUuid(), 1);
                    } else {
                        mImMessageUpdata.setIsSeedSuccess(2);
                        mAdapter.setImMessageStatus(imMessage.getUuid(), 2);
                    }
                    mImMessageDao.update(mImMessageUpdata);
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
                break;
            case R.id.p2pchat_record_iv:
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
                break;
            case R.id.p2pchat_add_iv:
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
                break;
            case R.id.p2pchat_send_bt:
                // 创建文本消息
                IMMessage message = MessageBuilder.createTextMessage(id, SessionTypeEnum.P2P
                        , blogdetailAddcommentEt.getText().toString());
                seedMessage(message, ContentTypeInter.contentTypeText, "", "");
                blogdetailAddcommentEt.setText("");
                break;
            case R.id.p2pchat_add_picture_tv:
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType("image/*");
                startActivityForResult(getAlbum, CHOOSE_BIG_PICTURE);
                break;
            case R.id.p2pchat_add_camera_tv:
                path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                break;
            case R.id.p2pchat_add_gif_tv:
                break;
            case R.id.p2pchat_add_finger_tv:
                GuessAttachment attachment = new GuessAttachment();
                IMMessage CustomMessage = MessageBuilder.createCustomMessage(id, SessionTypeEnum.P2P, attachment.getValue().getDesc(), attachment);
                seedMessage(CustomMessage, ContentTypeInter.contentTypeFinger, "", attachment.getValue().getDesc());
                break;
        }
    }

    private void seedMessage(IMMessage message, int contentType,String contentImageSUrl, String contentFinger) {
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
            temp= new ImMessage(message.getUuid(), id
                    , String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getNickName()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex()
                    , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()
                    , true, System.currentTimeMillis(), contentType, message.getContent(), "", "", "", "", "", "", "", "", 0, 0, false, 0);
        } else if (contentType==ContentTypeInter.contentTypeImage) {
//            LogUtils.e("p2p>>>>image>>>>getPath:"+((FileAttachment)message.getAttachment()).getPath());
//            LogUtils.e("p2p>>>>image>>>>getPathForSave:"+((FileAttachment)message.getAttachment()).getPathForSave());
//            LogUtils.e("p2p>>>>image>>>>getThumbPath:"+((FileAttachment)message.getAttachment()).getThumbPath());
//            LogUtils.e("p2p>>>>image>>>>getThumbPathForSave:"+((FileAttachment)message.getAttachment()).getThumbPathForSave());
//            LogUtils.e("p2p>>>>image>>>>getUrl:"+((FileAttachment)message.getAttachment()).getUrl());
            temp = new ImMessage(message.getUuid()
                    , message.getSessionId()
                    , message.getFromAccount()
                    , message.getRemoteExtension().get("nickName").toString()
                    , message.getRemoteExtension().get("icon").toString()
                    , message.getRemoteExtension().get("sex").toString()
                    , Integer.parseInt(message.getRemoteExtension().get("verify_status").toString())
                    , true, System.currentTimeMillis()
                    , Integer.parseInt(message.getRemoteExtension().get("contentType").toString())
                    , "［图片］", ((FileAttachment)message.getAttachment()).getPathForSave()
                    , ((FileAttachment)message.getAttachment()).getUrl()
                    , ((FileAttachment)message.getAttachment()).getThumbPathForSave(), "", "", "", "", "", 0, 0, false, 1);
        }
        mAdapter.addImMessageDao(temp);
        mImMessageDao.insert(temp);
        p2pChatLv.getRefreshableView().smoothScrollToPosition(p2pChatLv.getRefreshableView().getBottom());
        setSession(message);
    }

    private void setSession(IMMessage message) {
        mImSession = mImSessionDao.queryBuilder().where(ImSessionDao.Properties.UserId.eq(id)).build().unique();
        if (mImSession==null) {
            ImSession mImSessionTemp = new ImSession(id, message.getContent(), nick, icon, sex, verify_status
                    , System.currentTimeMillis(), 0);
            mImSessionDao.insert(mImSessionTemp);
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
        if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            imageUri = data.getData();
        } else if (requestCode == TAKE_BIG_PICTURE) {
            imageUri = Uri.parse("file:///" + path);
        }
        if (imageUri != null) {
            IMMessage message = null;
//            LogUtils.e("imageUri:" + imageUri);
//            LogUtils.e("imageUri.toString():" + imageUri.toString());
//            LogUtils.e("imageUri>>>>:" + imageUri.toString().contains("content:"));
//            LogUtils.e("imageUri.getPath():" + imageUri.getPath());
//            LogUtils.e("imageUri>>>>:" + imageUri.getPath().toString().contains("content:"));
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

    private void selectLast(){

    }
}
