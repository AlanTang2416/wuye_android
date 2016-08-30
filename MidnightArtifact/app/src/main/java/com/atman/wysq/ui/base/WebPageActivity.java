package com.atman.wysq.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.base.baselibs.util.NetworkUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 展示本地/服务器网页共通界面
 *
 * @author vftour.com
 * @version 1.0
 */
public class WebPageActivity extends MyBaseActivity {
    private static final String TAG = WebPageActivity.class.getSimpleName();

    /**
     * 打开的网址KEY
     */
    public final static String LINK_URL = "linkURL";

    /**
     * 默认网页标题KEY
     */
    public final static String TITLE = "title";

    /**
     * 是否允许缩放
     */
    public final static String IS_CAN_ZOOM = "isCanZoom";

    /**
     * 网页地址
     */
    protected String linkURL = "http://www.atman.com";

    /**
     * 网页标题
     */
    protected String mCurrentTile = "";

    /**
     * 是否允许缩放
     */
    private boolean isCanZoom = true;

    /**
     * 网页加载是否发生错误
     */
    private boolean isError = false;

    @Bind(R.id.tv_error_msg)
    TextView tv_error_msg;
    @Bind(R.id.pb_web_load_progress)
    ProgressBar pb_web_load_progress;
    @Bind(R.id.rl_webview)
    RelativeLayout rl_webview;
    @Bind(R.id.webview)
    WebView mWebView;
    @Bind(R.id.ll_reload)
    LinearLayout ll_reload;
    private String host;
    private String Data;
    private boolean isSplash = false;
    public final static String IS_SPLASH = "liisSplashnkURL";
    private LinearLayout mLiftLl;

    public static Intent buildIntent(Context context, String url, String title) {
        Intent intent = new Intent(context, WebPageActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(LINK_URL, url);
        return intent;
    }

    public static Intent buildIntent(Context context, String url, String title, boolean isSplash) {
        Intent intent = new Intent(context, WebPageActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(LINK_URL, url);
        intent.putExtra(IS_SPLASH, isSplash);
        return intent;
    }

    public static Intent buildDataIntent(Context context, String Data, String title, String url) {
        Intent intent = new Intent(context, WebPageActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra("Data", Data);
        intent.putExtra(LINK_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_common_webpage);
        ButterKnife.bind(this);

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        mLiftLl = getBarBackLl();
        mLiftLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        isSplash = getIntent().getBooleanExtra(IS_SPLASH,false);
    }

    @Override
    public void initIntentAndMemData() {
        super.initIntentAndMemData();
        Bundle parms = getIntent().getExtras();
        linkURL = parms.getString(LINK_URL, linkURL);
        mCurrentTile = parms.getString(TITLE, mCurrentTile);
        isCanZoom = parms.getBoolean(IS_CAN_ZOOM, true);
        setBarTitleTx(mCurrentTile);
        initView();
        initCookie();
    }

    private void initCookie() {
        CookieManager instance = CookieManager.getInstance();
        instance.setAcceptCookie(true);
        if (linkURL == null || TextUtils.isEmpty(linkURL)) {
            return;
        }
        Uri uri = Uri.parse(linkURL);
        host = uri.getHost();

//        if (!TextUtils.isEmpty(host) && Urls.RWH_HOST.contains(host)) {
//            MemberAccountModel model = CacheManager.getInstance().getMemberAccountModel();
//            String token = "";
//            if (model != null && model != null) {
//                String source = "member_id=" + model.getMember_id();
//                token = Api.encryptParams(source);
//            }
//
//            instance.setCookie(host, "i=" + token);
//            instance.setCookie(host, "path=/");
//            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//            instance.setCookie(host, "width=" + displayMetrics.widthPixels);
//
//            instance.setCookie(host, "height=" + displayMetrics.heightPixels);
//            instance.setCookie(host, "device=android");
//            instance.setCookie(host, "version=1");
//            //TODO change version to new
//        }
    }

    public void initView() {
        tv_error_msg = (TextView) findViewById(R.id.tv_error_msg);
        // 加载进度
        pb_web_load_progress = (ProgressBar) findViewById(R.id.pb_web_load_progress);

        // webview、重新加载
        rl_webview = (RelativeLayout) findViewById(R.id.rl_webview);
        mWebView = (WebView) findViewById(R.id.webview);
        ll_reload = (LinearLayout) findViewById(R.id.ll_reload);
        isError = false;
        initWebview();
    }

    public void initWebview() {
        // 初始化Webview配置
        WebSettings settings = mWebView.getSettings();
        // 设置是否支持Javascript
        settings.setJavaScriptEnabled(true);
        // 是否支持缩放
        settings.setSupportZoom(isCanZoom);
        // settings.setBuiltInZoomControls(true);
        // if (Build.VERSION.SDK_INT >= 3.0)
        // settings.setDisplayZoomControls(false);
        // 是否显示缩放按钮
        // settings.setDisplayZoomControls(false);
        // 提高渲染优先级
        settings.setRenderPriority(RenderPriority.HIGH);
        // 设置页面自适应手机屏幕
        settings.setUseWideViewPort(true);
        // WebView自适应屏幕大小
        settings.setLoadWithOverviewMode(true);
        // 加载url前，设置不加载图片WebViewClient-->onPageFinished加载图片
        // settings.setBlockNetworkImage(true);
        // 设置网页编码
        settings.setDefaultTextEncodingName("UTF-8");

        mWebView.setWebChromeClient(new UIWebChromeClient());
        mWebView.setWebViewClient(new UIWebViewClient());
        mWebView.setDownloadListener(listener);

        if (linkURL.equals("-1")) {
            String html = getIntent().getStringExtra("Data");
            mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        } else {
            mWebView.loadUrl(linkURL);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.stopLoading();
        // 先移除附属关系
        rl_webview.removeView(mWebView);
        mWebView.removeAllViews();
        mWebView.destroy();

        CookieManager.getInstance().removeAllCookie();
    }

    @OnClick({R.id.ll_reload})
    public void widgetClick(View v) {
        int switchId = v.getId();
        if (R.id.ll_reload == switchId) {
            mWebView.reload();
            isError = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if (isSplash) {
                back();
            } else {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    DownloadListener listener = new DownloadListener() {

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype,
                                    long contentLength) {
            Log.e(TAG, "onDownloadStart-->url:" + url);
            Log.e(TAG, "onDownloadStart-->userAgent:" + userAgent);
            Log.e(TAG, "onDownloadStart-->contentDisposition:"
                    + contentDisposition);
            Log.e(TAG, "onDownloadStart-->mimetype:" + mimetype);
            Log.e(TAG, "onDownloadStart-->contentLength:" + contentLength);

            // 调用系统浏览器下载
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    };

    class UIWebChromeClient extends WebChromeClient {
        /***
         * 页面加载进度
         **/
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            //不管正常还是发生错误都会进入，需要isError标志位区分
            if (newProgress == 100) {
                pb_web_load_progress.setVisibility(View.GONE);

                //如果发生错误显示错误提示
                ll_reload.setVisibility(isError ? View.VISIBLE : View.GONE);
                mWebView.setVisibility(isError ? View.GONE : View.VISIBLE);

                //设置提示信息
                if (isError) {

                    if (!NetworkUtils.isNetworkAvailable(WebPageActivity.this)) {
                        tv_error_msg.setText("亲，网络木有打开哦~");
                    } else {
                        tv_error_msg.setText("点击刷新");
                    }
                }
            } else {
                pb_web_load_progress.setVisibility(View.VISIBLE);
                pb_web_load_progress.setProgress(newProgress);
            }
        }

        /**
         * 获取到网页标题回调函数
         */
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mCurrentTile.equals("")) {
                mCurrentTile = title;
                setBarTitleTx(title);
            }
        }
    }

    private void back() {
//        if (!linkURL.equals("-1")) {
//            startActivity(MainActivity.buildIntent(WebPageActivity.this, false));
//        }
        finish();
    }

    class UIWebViewClient extends WebViewClient {

        /**
         * 控制网页的链接跳转打开方式（拦截URL，当前界面打开网页所有连接）
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            linkURL = url;
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.i(TAG, "onPageStarted--->url=" + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // 加载完毕后，开始加载图片
            // view.getSettings().setBlockNetworkImage(false);
            Log.i(TAG, "onPageFinished--->url=" + url);
        }

        /***
         * 让浏览器支持访问https请求
         */
        @SuppressLint("NewApi")
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
            Log.i(TAG, "onReceivedSslError--->" + "加载数据失败，错误码：" + error.getUrl());
            isError = true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.i(TAG, "onReceivedError--->" + "加载数据失败，错误码：" + errorCode
                    + "\n 原因描述：" + description);
            isError = true;
        }
    }

    /**
     * 横竖屏切换更改配置
     */
    @Override
    public void onConfigurationChanged(Configuration config) {
        try {
            super.onConfigurationChanged(config);

            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // 横屏时要处理的代码，
                // 这里的代码是当屏幕横屏时当成竖屏显示
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                // 竖屏时要处理的代码
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } catch (Exception ex) {
            Log.e(TAG, "onConfigurationChanged-->exception:" + ex.getMessage());
        }
    }
}
