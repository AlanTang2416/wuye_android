package com.atman.wysq.widget.face;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.atman.wysq.R;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/2 09:49
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class FaceRelativeLayout extends RelativeLayout implements
        AdapterView.OnItemClickListener, View.OnClickListener {

    private Context context;

    /**
     * 表情页的监听事件
     */
    private OnCorpusSelectedListener mListener;

    /**
     * 显示表情页的viewpager
     */
    private ViewPager vp_face;

    /**
     * 表情页界面集合
     */
    private ArrayList<View> pageViews;

    /**
     * 游标显示布局
     */
    private LinearLayout layout_point;

    /**
     * 游标点集合
     */
    private ArrayList<ImageView> pointViews;

    /**
     * 表情集合
     */
    private List<List<ChatEmoji>> emojis;

    /**
     * 表情区域
     */
    private View view;
    private View view2;

    /**
     * 输入框
     */
    private MyCleanEditText et_sendmessage;
    private ImageView blogdetail_addemol_iv;

    /**
     * 表情数据填充器
     */
    private List<FaceAdapter> faceAdapters;

    /**
     * 当前表情页
     */
    private int current = 0;

    public FaceRelativeLayout(Context context) {
        super(context);
        this.context = context;
    }

    public FaceRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public FaceRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void setOnCorpusSelectedListener(OnCorpusSelectedListener listener) {
        mListener = listener;
    }

    /**
     * 表情选择监听
     *
     * @author naibo-liao
     * @时间： 2013-1-15下午04:32:54
     */
    public interface OnCorpusSelectedListener {

        void onCorpusSelected(ChatEmoji emoji);

        void onCorpusDeleted();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        emojis = FaceConversionUtil.getInstace().emojiLists;
        onCreate();
    }

    private void onCreate() {
        Init_View();
        Init_viewPager();
        Init_Point();
        Init_Data();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.blogdetail_addemol_iv:
                if (isIMOpen()) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                }
                // 隐藏表情选择框
                if (view.getVisibility() == View.VISIBLE) {
                    view.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
                if (view2!=null) {
                    view2.setVisibility(View.GONE);
                }
                break;
            case R.id.blogdetail_addcomment_et:
                // 隐藏表情选择框
                if (view.getVisibility() == View.VISIBLE) {
                    view.setVisibility(View.GONE);
                }
                if (view2!=null) {
                    view2.setVisibility(View.GONE);
                }
                break;

        }
    }

    private boolean isIMOpen() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();//isOpen若返回true，则表示输入法打开
    }

    /**
     * 隐藏表情选择框
     */
    public boolean hideFaceView() {
        // 隐藏表情选择框
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    /**
     * 初始化控件
     */
    private void Init_View() {
        vp_face = (ViewPager) findViewById(R.id.vp_contains);
        et_sendmessage = (MyCleanEditText) findViewById(R.id.blogdetail_addcomment_et);
        layout_point = (LinearLayout) findViewById(R.id.iv_image);
        et_sendmessage.setOnClickListener(this);
        view = findViewById(R.id.ll_facechoose);
        view2 = findViewById(R.id.p2pchat_add_ll);
        blogdetail_addemol_iv = (ImageView) findViewById(R.id.blogdetail_addemol_iv);
        blogdetail_addemol_iv.setOnClickListener(this);
    }

    /**
     * 初始化显示表情的viewpager
     */
    private void Init_viewPager() {
        pageViews = new ArrayList<View>();
        // 左侧添加空页
        View nullView1 = new View(context);
        // 设置透明背景
        nullView1.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView1);

        // 中间添加表情页

        faceAdapters = new ArrayList<FaceAdapter>();
        for (int i = 0; i < emojis.size(); i++) {
            GridView view = new GridView(context);
            FaceAdapter adapter = new FaceAdapter(context, emojis.get(i));
            view.setAdapter(adapter);
            faceAdapters.add(adapter);
            view.setOnItemClickListener(this);
            view.setNumColumns(8);
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setHorizontalSpacing(1);
            view.setVerticalSpacing(1);
            view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            view.setCacheColorHint(0);
            view.setPadding(5, 0, 5, 0);
            view.setSelector(new ColorDrawable(Color.TRANSPARENT));
            view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            view.setGravity(Gravity.CENTER);
            pageViews.add(view);
        }

        // 右侧添加空页面
        View nullView2 = new View(context);
        // 设置透明背景
        nullView2.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView2);
    }

    /**
     * 初始化游标
     */
    private void Init_Point() {

        pointViews = new ArrayList<ImageView>();
        ImageView imageView;
        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(context);
            imageView.setBackgroundResource(R.mipmap.d1);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.width = 8;
            layoutParams.height = 8;
            layout_point.addView(imageView, layoutParams);
            if (i == 0 || i == pageViews.size() - 1) {
                imageView.setVisibility(View.GONE);
            }
            if (i == 1) {
                imageView.setBackgroundResource(R.mipmap.d2);
            }
            pointViews.add(imageView);

        }
    }

    /**
     * 填充数据
     */
    private void Init_Data() {
        vp_face.setAdapter(new ViewPagerAdapter(pageViews));

        vp_face.setCurrentItem(1);
        current = 0;
        vp_face.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                current = arg0 - 1;
                // 描绘分页点
                draw_Point(arg0);
                // 如果是第一屏或者是最后一屏禁止滑动，其实这里实现的是如果滑动的是第一屏则跳转至第二屏，如果是最后一屏则跳转到倒数第二屏.
                if (arg0 == pointViews.size() - 1 || arg0 == 0) {
                    if (arg0 == 0) {
                        vp_face.setCurrentItem(arg0 + 1);// 第二屏 会再次实现该回调方法实现跳转.
                        pointViews.get(1).setBackgroundResource(R.mipmap.d2);
                    } else {
                        vp_face.setCurrentItem(arg0 - 1);// 倒数第二屏
                        pointViews.get(arg0 - 1).setBackgroundResource(
                                R.mipmap.d2);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    /**
     * 绘制游标背景
     */
    public void draw_Point(int index) {
        for (int i = 1; i < pointViews.size(); i++) {
            if (index == i) {
                pointViews.get(i).setBackgroundResource(R.mipmap.d2);
            } else {
                pointViews.get(i).setBackgroundResource(R.mipmap.d1);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        LogUtils.e(">>>onItemClick>>>>arg2:"+arg2);
        ChatEmoji emoji = (ChatEmoji) faceAdapters.get(current).getItem(arg2);
        if (emoji.getId() == R.mipmap.face_del) {
            int selection = et_sendmessage.getSelectionStart();
            String text = et_sendmessage.getText().toString();
            if (selection > 0) {
                deleteEditTextSpan(selection);
            }
        }
        if (!TextUtils.isEmpty(emoji.getCharacter())) {
            if (mListener != null) {
                mListener.onCorpusSelected(emoji);
            }
            LogUtils.e(">>>>>>>>>>>>>>>>emoji:"+emoji);
            SpannableString spannableString = FaceConversionUtil.getInstace()
                    .addFace(getContext(), emoji.getId(), emoji.getCharacter());

            int index = et_sendmessage.getSelectionStart();
            String str = et_sendmessage.getText().toString();
            if (index < 0 || index >= str.length()) {
                et_sendmessage.append(spannableString);
            } else {
                Editable edit = et_sendmessage.getEditableText();//获取EditText的文字
                edit.insert(index, spannableString);
            }
        }

    }

    private void deleteEditTextSpan(int selection) {
        Spanned s = et_sendmessage.getEditableText();
        ImageSpan[] imageSpan = s.getSpans(0, selection, ImageSpan.class);
        Editable et = et_sendmessage.getText();
        if (imageSpan.length>0) {
            for (int i = imageSpan.length - 1; i >= 0; i--) {
                if(i == imageSpan.length - 1) {
                    int start = s.getSpanStart(imageSpan[i]);
                    int end = s.getSpanEnd(imageSpan[i]);
                    if (end == selection) {
                        et.delete(start, end);
                    } else {
                        et.delete(selection-1, selection);
                    }
                }
            }
        } else {
            et.delete(selection-1, selection);
        }
        et_sendmessage.invalidate();
    }
}
