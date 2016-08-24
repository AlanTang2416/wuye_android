package com.atman.wysq.ui.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.atman.wysq.R;
import com.atman.wysq.model.response.ConfigModel;
import com.atman.wysq.model.response.GetGoldenRoleModel;
import com.atman.wysq.model.response.GetUserInfoModel;
import com.base.baselibs.base.BaseApplication;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PhoneInfo;
import com.base.baselibs.util.PreferenceUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.PlatformConfig;

import java.io.File;
import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/30 14:31
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyBaseApplication extends BaseApplication {

    private static Context mContext = null;
    private static MyBaseApplication mInstance;
    private String mUserName = "";
    private String mPassWord = "";
    private String mUserKey = "";
    private String mUserToken = "";
    private String mUserId = "";

    public static String mDeviceToken = "";
    public static String mVersionName = "";
    public static String mChannel = "";
    public static String mPhoneModel = "";
    public static String mPhoneMac = "";
    public static String mPhoneDeviceId = "";
    public static String mWEB_URL = "";
    public static String mWEB_TYPE = "";
    public static String mWEB_ID = "";
    public static String mDownLoad_URL = "";
    public static String mHEAD_URL = "";
    public static int mUserCion = 0;
    public static List<ConfigModel.ShopEntity> mShop ;
    public static GetGoldenRoleModel mGetGoldenRoleModel ;
    public static GetUserInfoModel mGetUserInfoModel ;
    public boolean isLock = true;
    public boolean isFilterLock = false;
    public static boolean isUnRead = false;
    public boolean isError = false;
    public static String appId = "";

    private DisplayImageOptions options,optionsHead, optionsNot;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        getApp();

        initPhoneInfo();
        initLoginData();

        setConfigLoad();
        InitDownImageConfig();
        initDisplayConfig();

        UMmengInit();
    }

    private void UMmengInit() {
        //微信(朋友圈) appid appsecret
        PlatformConfig.setWeixin("wx142af8d518f19762", "e581275d1a31b0df7d7c29b371cbfdfb");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("79633130", "198c96961ac6c1db2085be3ad229bb5b");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1104488827", "nSXfacXqGsgG7pU2");
    }

    public boolean isFilterLock() {
        return isFilterLock;
    }

    public void setFilterLock(boolean filterLock) {
        isFilterLock = filterLock;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    private void initPhoneInfo() {
        PhoneInfo mPhoneInfo = new PhoneInfo(mContext);
        mVersionName = "v" + getAppInfo().split("-")[0];
        mChannel = getAppMetaData(mContext, "UMENG_CHANNEL");
        mPhoneModel = android.os.Build.MODEL;
        mPhoneMac = mPhoneInfo.getMac();
        mPhoneDeviceId = mPhoneInfo.getIMEI();
    }

    public boolean isLogined() {
        initLoginData();
        return !mUserName.isEmpty() && !mPassWord.isEmpty() &&
                !mUserKey.isEmpty() && !mUserToken.isEmpty() && !mUserId.isEmpty();
    }

    private void initLoginData() {
        mUserName = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_US);
        mPassWord = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_PW);
        mUserKey = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_KEY);
        mUserToken = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_TOKEN);
        mUserId = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USERID);
        mDeviceToken = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_TOKEN);
//        LogUtils.e("mUserName:"+mUserName+",mPassWord:"+mPassWord+",mUserKey:"+mUserKey+",mUserToken:"+mUserToken+",mUserId:"+mUserId);
    }

    public void cleanLoginData() {
        PreferenceUtil.savePreference(mContext, PreferenceUtil.PARM_PW, "");
        PreferenceUtil.savePreference(mContext, PreferenceUtil.PARM_USER_KEY, "");
        PreferenceUtil.savePreference(mContext, PreferenceUtil.PARM_USER_TOKEN, "");
        PreferenceUtil.savePreference(mContext, PreferenceUtil.PARM_USERID, "");
    }

    public String getmUserId() {
        return mUserId;
    }

    public static MyBaseApplication getApp(){
        if (mInstance == null) {
            mInstance = new MyBaseApplication();
        }
        return mInstance;
    }

    public String getCookie() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("USER_KEY=");
        stringBuilder.append(PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_KEY));
        stringBuilder.append(";USER_TOKEN=");
        stringBuilder.append(PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_TOKEN));
        return stringBuilder.toString();
    }

    private String getAppInfo() {
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(pkName, 0).versionName;
            int versionCode = this.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    private void InitDownImageConfig() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(mContext)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
//                .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
//                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(mContext, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    private void initDisplayConfig() {
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.mipmap.ic_launcher) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//                .decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置
//                .delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//                .preProcessor(BitmapProcessor preProcessor)  //设置图片加入缓存前，对bitmap进行设置
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成

        optionsNot = new DisplayImageOptions
                .Builder()
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888)//设置图片的解码类型//
//                .decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置
//                .delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//                .preProcessor(BitmapProcessor preProcessor)  //设置图片加入缓存前，对bitmap进行设置
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成

        optionsHead = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//                .decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置
//                .delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//                .preProcessor(BitmapProcessor preProcessor)  //设置图片加入缓存前，对bitmap进行设置
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
    }

    public DisplayImageOptions getOptions() {
        return options;
    }

    public DisplayImageOptions getOptionsNot() {
        return optionsNot;
    }

    public DisplayImageOptions getOptionsHead() {
        return optionsHead;
    }

    public DisplayImageOptions getMemberHead() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//                .decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置
//                .delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//                .preProcessor(BitmapProcessor preProcessor)  //设置图片加入缓存前，对bitmap进行设置
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(40))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();
    }

    private void setConfigLoad() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(200, 200) // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(6)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY -2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(4 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                .discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this,5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for releaseapp
                .build();//开始构建
        ImageLoader.getInstance().init(config);
    }
}
