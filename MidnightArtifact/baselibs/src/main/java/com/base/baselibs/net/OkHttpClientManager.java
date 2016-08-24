package com.base.baselibs.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.base.baselibs.util.MyAppFileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/30 09:02
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class OkHttpClientManager {
    private static final String TAG = OkHttpClientManager.class.getSimpleName();

    private static OkHttpClientManager mInstance = null;
    private Handler mHandler;

    private OkHttpClient.Builder mOkHttpClient;
    private File cacheDirectory = new MyAppFileUtils().getMyAppHttpCacheFile();
    private long cacheSize = 10 * 1024 * 1024;

    public static synchronized OkHttpClientManager getInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpClientManager();
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            Cache cache = new Cache(cacheDirectory, cacheSize);
            mOkHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .cache(cache);
        }
        mHandler = new Handler(Looper.getMainLooper());
        return mOkHttpClient.build();
    }

    private void request(final int method, String tag) {
        Request request = methodBuilder(method, tag);
        Call call = OkHttpClientManager.getInstance().getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String jsonResponse = null;
                        try {
                            jsonResponse = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }

    private Request methodBuilder(int method, String tag) {

        Request.Builder builder = new Request.Builder();
        builder.url(getUrl(method));
        builder.tag(tag);
//        builder.headers(addHeaders());
//        builder.header("Cache-Control", "only-if-cached");
//        builder.header("Cache-Control", "max-stale=" + maxStale);

        switch (method) {
            case Method.GET:
                builder.get();
                break;
            case Method.POST:
//                builder.post(addMultipartBuilder());
                break;
            case Method.PUT:
//                builder.put(addMultipartBuilder());
                break;
            case Method.DELETE:
//                builder.delete(addMultipartBuilder());
                break;
        }
//        Log.d(TAG, "Method: " + getMethodStr(method) + "  url： " + builder.build().httpUrl().toString());
        return builder.build();

    }

    private String getUrl(int method) {
        return null;
    }

    interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }

    String getMethodStr(int method) {
        switch (method) {
            case Method.GET:
                return "GET";
            case Method.POST:
                return "POST";
            case Method.PUT:
                return "PUT";
            case Method.DELETE:
                return "DELETE";
            default:
                return "-->";
        }
    }

}
