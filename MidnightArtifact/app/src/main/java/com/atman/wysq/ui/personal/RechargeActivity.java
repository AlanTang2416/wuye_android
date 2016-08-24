package com.atman.wysq.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.RechargeListAdapter;
import com.atman.wysq.model.response.AliPayResponseModel;
import com.atman.wysq.model.response.RechargeAddOrderModel;
import com.atman.wysq.model.response.WeiXinPayResponseModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.pay.PayDialog;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.tbl.okhttputils.OkHttpUtils;
import com.tbl.okhttputils.utils.L;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/13 15:17
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class RechargeActivity extends MyBaseActivity implements AdapterInterface,PayDialog.payItemCallback,PayDialog.payResultCallback {

    @Bind(R.id.recharge_top_iv)
    ImageView rechargeTopIv;
    @Bind(R.id.recharge_surpluscoin_tx)
    TextView rechargeSurpluscoinTx;
    @Bind(R.id.recharge_listview)
    ListView rechargeListview;

    private Context mContext = RechargeActivity.this;
    private int whatPay = 0;

    private RechargeListAdapter mAdapter;
    private PayDialog mPayDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("充值");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getmWidth(),
                getmWidth() * 259 / 640);
        rechargeTopIv.setLayoutParams(params);
        rechargeSurpluscoinTx.setText("剩余金币："+MyBaseApplication.mUserCion);
        initListView();
    }

    private void initListView() {
        mAdapter = new RechargeListAdapter(mContext, MyBaseApplication.mShop, this);
        rechargeListview.setAdapter(mAdapter);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (whatPay==1) {
            MyBaseApplication.getApp().setFilterLock(false);
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_RECHARGE_ADD_ORDER) {
            RechargeAddOrderModel mRechargeAddOrderModel = mGson.fromJson(data, RechargeAddOrderModel.class);
            LogUtils.e("mRechargeAddOrderModel.getBody().getOrder_id():"+ mRechargeAddOrderModel.getBody().getOrder_id());
            mPayDialog = new PayDialog(mContext, mRechargeAddOrderModel.getBody().getOrder_id()+"" ,this);
            mPayDialog.show();
        } else if (id == Common.NET_RECHARGE_ADD_ORDER_ALIPAY) {
            MyBaseApplication.getApp().setFilterLock(true);
            AliPayResponseModel mAliPayResponseOneModel = mGson.fromJson(data, AliPayResponseModel.class);
            String parms = mAliPayResponseOneModel.getBody().getParam().replace("\\\"","\"");
            int start = parms.indexOf("&sign");
            mPayDialog.aliPay(RechargeActivity.this, parms.substring(0, start));
        } else if (id == Common.NET_RECHARGE_ADD_ORDER_WEIXIN) {
            MyBaseApplication.getApp().setFilterLock(true);
            WeiXinPayResponseModel WeiXinPayResponseModelm = mGson.fromJson(data, WeiXinPayResponseModel.class);
            mPayDialog.weixinPay(WeiXinPayResponseModelm);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_RECHARGE_ADD_ORDER);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RECHARGE_ADD_ORDER_ALIPAY);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RECHARGE_ADD_ORDER_WEIXIN);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_reharge_bt:
                OkHttpUtils.postString().url(Common.Url_Recharge_Add_Order)
                        .content("{\"golden_coin\":\""+mAdapter.getItem(position).getName()+"\"}")
                        .mediaType(Common.JSON).addHeader("cookie",MyBaseApplication.getApp().getCookie())
                        .id(Common.NET_RECHARGE_ADD_ORDER).tag(Common.NET_RECHARGE_ADD_ORDER)
                        .build().execute(new MyStringCallback(mContext, this, true));
                break;
        }
    }

    @Override
    public void itemPay(int num, String orderId) {
        if (num==1) {
            whatPay = 0;
            OkHttpUtils.postString().url(Common.Url_Recharge_Add_Order_Alipay + orderId).content("{}")
                    .mediaType(Common.JSON).addHeader("cookie",MyBaseApplication.getApp().getCookie())
                    .id(Common.NET_RECHARGE_ADD_ORDER_ALIPAY).tag(Common.NET_RECHARGE_ADD_ORDER_ALIPAY)
                    .build().execute(new MyStringCallback(mContext, this, true));
        } else {
            whatPay = 1;
            OkHttpUtils.postString().url(Common.Url_Recharge_Add_Order_WeiXin).content("{\"order_id\":\""+orderId+"\"}")
                    .mediaType(Common.JSON).addHeader("cookie",MyBaseApplication.getApp().getCookie())
                    .id(Common.NET_RECHARGE_ADD_ORDER_WEIXIN).tag(Common.NET_RECHARGE_ADD_ORDER_WEIXIN)
                    .build().execute(new MyStringCallback(mContext, this, true));
        }
    }

    @Override
    public void payResult(String str) {
        MyBaseApplication.getApp().setFilterLock(false);
    }
}
