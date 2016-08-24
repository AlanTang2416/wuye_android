package com.atman.wysq.widget.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.alipay.sdk.app.PayTask;
import com.atman.wysq.R;
import com.atman.wysq.model.response.WeiXinPayResponseModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.utils.Tools;
import com.base.baselibs.util.LogUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/23 10:57
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PayDialog {

    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMkJmTrvFwx1MJjbckKU6tOhT1RWQUwICsguPzfYAZwKB9kIN0IFYhdKb8FGTNrPWfXsrlMjNd6OvHzbTyGVOb7W481zkjElQAHN7Z+uoUaGkb1XLX3W8qzg9U4tlm0SHQRK14Zafz6EqeCCEhJgEhlypqi9dF9dfYkOz3mO70v7AgMBAAECgYAhvvu6udGbpLxQnF2UsZytg6FmXcg+NAdjTOgNvrOedsyUcN2dtnudvz/9KIjUHgRS1LT6famYP0uChenFkGANzZ4oj70j/v6TsOBrvkWteTt1mpbTsiFnOZkSqxTVVfe5WjVQ5HzLO7yORIf7xc1XniSfMWLRaKvO7MpBZo0RaQJBAOqN9uVJHSCyqHCNH4aD7nkiQK57ke1QJvyWkPfyRnMuU7a3owsGGkMnFzwEcPYOHcZfZAbo512wC1gG4UiX6f0CQQDbayBlVgvM1DclenTI1b8G69lSR/yxFcA1TF9VEXF2uCjF3JBPCdJOx7C+kN6k4zVThMnAP5aKywKxDgenBRNXAkA9DbT9m9l0IG0N1v4kwoS5jTvu7wVeE2YCi6Kyl6LU6nMp9YUAIpeFcVBv8+v/cE85yqy7y7YlweRCBpBvl/N1AkEAsswMzgbb/sE/Xs72s2FaFK3DnYUDqNnskoH1fUCFkRRoRTOCdKWwNnoK0H8ErqBnJ/Og44PtjJP5UYX5PmSoaQJALMHBIUJ+/yWxk/5AgbvEvoRqBCA4htj400le9yAGY2cbuhLEMGCoo1umd9mEOTre4pG5HmkDspU2aqH5u740KA==";
    private static final int SDK_PAY_FLAG = 1;
    private Context context;
    private String orderId;
    private payResultCallback mpayResultCallback;
    private payItemCallback mpayItemCallback;

    public PayDialog(Context context){
        this.context = context;
    }

    public PayDialog(Context context, payResultCallback mpayResultCallback){
        this.context = context;
        this.mpayResultCallback = mpayResultCallback;
    }

    public PayDialog(Context context, String orderId, payItemCallback mpayItemCallback){
        this.context = context;
        this.orderId = orderId;
        this.mpayItemCallback = mpayItemCallback;
    }

    public void show(){
        //布局文件转换为view对象
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout)inflaterDl.inflate(R.layout.dialog_pay, null );

        //对话框
        final Dialog dialog = new AlertDialog.Builder(context, R.style.dialog_pay).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);

        layout.findViewById(R.id.dialog_pay_cancel_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        layout.findViewById(R.id.dialog_pay_root_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        layout.findViewById(R.id.dialog_pay_zhifubao_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mpayItemCallback.itemPay(1, orderId);
            }
        });

        layout.findViewById(R.id.dialog_pay_weixinzhifu_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IWXAPI api = WXAPIFactory.createWXAPI(context, null);
                if (api.isWXAppInstalled()) {
                    dialog.dismiss();
                    mpayItemCallback.itemPay(2, orderId);
                } else {
                    ((MyBaseActivity) context).showToast("您还没有安装微信客户端");
                }

            }
        });
    }

    public void weixinPay(WeiXinPayResponseModel mWeiXinPayResponseModel){
        IWXAPI api = WXAPIFactory.createWXAPI(context, mWeiXinPayResponseModel.getBody().getAppid());
        PayReq payRequest = new PayReq();
        payRequest.appId = mWeiXinPayResponseModel.getBody().getAppid();//应用ID
        payRequest.partnerId = mWeiXinPayResponseModel.getBody().getMch_id();//商户号
        payRequest.prepayId = mWeiXinPayResponseModel.getBody().getPrepayId();//预支付交易会话ID
        payRequest.packageValue = "WX_ADD_ID:"+mWeiXinPayResponseModel.getBody().getAppid();//扩展字段
        payRequest.nonceStr = mWeiXinPayResponseModel.getBody().getNonce_str();//随机字符串
        payRequest.timeStamp = String.valueOf(genTimeStamp());//时间戳

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", payRequest.appId));
        signParams.add(new BasicNameValuePair("noncestr", payRequest.nonceStr));
        signParams.add(new BasicNameValuePair("package", payRequest.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", payRequest.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", payRequest.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", payRequest.timeStamp));

        payRequest.sign = genAppSign(signParams);

//        payRequest.sign = mWeiXinPayResponseModel.getBody().getSign();//签名
        api.sendReq(payRequest);
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append("WysqAtAtman20150908r8p0WvNYHs1g9");

        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        return appSign;
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void aliPay(final Context context,String orderInfo ) {
        if (!Tools.isPkgInstalled(context, "com.eg.android.AlipayGphone")) {
            new AlertDialog.Builder(context).setTitle("提示").setMessage("您的手机还没有安装支付宝，不能进行支付！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            dialoginterface.dismiss();
                        }
                    }).show();
            return;
        }
        if (TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(context).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            ((Activity)context).finish();
                        }
                    }).show();
            return;
        }

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((MyBaseActivity)context);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    LogUtils.e("payResult:"+payResult);
                    if (mpayResultCallback!=null) {
                        mpayResultCallback.payResult("");
                    }
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    LogUtils.e("resultStatus:"+resultStatus+",context:"+context);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ((MyBaseActivity) context).showToast("支付成功");
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ((MyBaseActivity) context).showToast("支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ((MyBaseActivity) context).showToast("支付失败，"+payResult.getMemo());
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    public interface payItemCallback {
        void itemPay(int num, String orderId);
    }

    public interface payResultCallback {
        void payResult(String str);
    }
}
