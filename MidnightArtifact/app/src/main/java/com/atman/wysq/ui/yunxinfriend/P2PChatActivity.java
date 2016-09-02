package com.atman.wysq.ui.yunxinfriend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.atman.wysq.model.greendao.gen.ImMessageDao;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.yunxin.model.ContentTypeInter;
import com.atman.wysq.yunxin.model.GuessAttachment;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.media.record.AudioRecorder;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
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

    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private Uri imageUri;
    private String path;
    protected AudioRecorder audioMessageHelper;
    private boolean touched = false; // 是否按着
    private boolean started = false;
    private boolean cancelled = false;

    private ImMessageDao mImMessageDao;
    private List<ImMessage> mImMessage;
    private P2PChatAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2pchat);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static Intent buildIntent(Context context, String id, String nick) {
        Intent intent = new Intent(context, P2PChatActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("nick", nick);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        id = getIntent().getStringExtra("id");
        nick = getIntent().getStringExtra("nick");

        setBarTitleTx(nick);
        setBarRightTx("清空").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("清空");
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
        mImMessage = mImMessageDao.queryBuilder().build().list();
        mAdapter.addImMessageDao(mImMessage);
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.DISABLED, p2pChatLv);
        mAdapter = new P2PChatAdapter(mContext);
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
            if (messages.size() > 0) {
                showToast("收到消息：" + "contentType"+messages.get(0).getRemoteExtension().get("contentType")
                        +",content:"+messages.get(0).getContent());
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
                LogUtils.e("imMessage.getAttachStatus():" + imMessage.getAttachStatus()
                        + ",imMessage.getStatus():" + imMessage.getStatus());
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

        ImMessage temp = new ImMessage(id, String.valueOf(MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserId())
                , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getNickName()
                , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex()
                , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()
                , true, System.currentTimeMillis(), contentType, message.getContent(), "", "", "", "", "", "", "", "", 0, 0, false);
        mAdapter.addImMessageDao(temp);
        mImMessageDao.insert(temp);
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
            LogUtils.e("imageUri:" + imageUri);
            // 创建图片消息
            IMMessage message = MessageBuilder.createImageMessage(id, SessionTypeEnum.P2P,
                    new File(imageUri.getPath()), "");
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
}
