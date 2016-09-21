package com.atman.wysq.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.RechargeListAdapter;
import com.atman.wysq.model.response.AliPayResponseModel;
import com.atman.wysq.model.response.RechargeAddOrderModel;
import com.atman.wysq.model.response.WeiXinPayResponseModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.login.CoinAgreementActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.pay.PayDialog;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.tbl.okhttputils.OkHttpUtils;

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
public class RechargeActivity extends MyBaseActivity implements AdapterInterface, PayDialog.payItemCallback
        , PayDialog.payResultCallback {

    @Bind(R.id.recharge_listview)
    ListView rechargeListview;
    @Bind(R.id.recharge_totalcoin_tv)
    TextView rechargeTotalcoinTv;
    @Bind(R.id.recharge_canoutcoin_tv)
    TextView rechargeCanoutcoinTv;

    private Context mContext = RechargeActivity.this;
    private int whatPay = 0;

    private RechargeListAdapter mAdapter;
    private PayDialog mPayDialog;

    private int goldCoin = 0;
    private int convertCoin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, int goldCoin, int convertCoin) {
        Intent intent = new Intent(context, RechargeActivity.class);
        intent.putExtra("goldCoin", goldCoin);
        intent.putExtra("convertCoin", convertCoin);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("我的金币");
        setBarRightIv(R.mipmap.mycion_bar_right_ic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CoinAgreementActivity.class));
            }
        });
        goldCoin = getIntent().getIntExtra("goldCoin", 0);
        convertCoin = getIntent().getIntExtra("convertCoin", 0);

        rechargeTotalcoinTv.setText(""+goldCoin);
        rechargeCanoutcoinTv.setText(""+convertCoin);

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
        if (whatPay == 1) {
            MyBaseApplication.getApplication().setFilterLock(false);
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_RECHARGE_ADD_ORDER) {
            RechargeAddOrderModel mRechargeAddOrderModel = mGson.fromJson(data, RechargeAddOrderModel.class);
            LogUtils.e("mRechargeAddOrderModel.getBody().getOrder_id():" + mRechargeAddOrderModel.getBody().getOrder_id());
            mPayDialog = new PayDialog(mContext, mRechargeAddOrderModel.getBody().getOrder_id() + "", this);
            mPayDialog.show();
        } else if (id == Common.NET_RECHARGE_ADD_ORDER_ALIPAY) {
            MyBaseApplication.getApplication().setFilterLock(true);
            AliPayResponseModel mAliPayResponseOneModel = mGson.fromJson(data, AliPayResponseModel.class);
            String parms = mAliPayResponseOneModel.getBody().getParam().replace("\\\"", "\"");
            int start = parms.indexOf("&sign");
            mPayDialog.aliPay(RechargeActivity.this, parms.substring(0, start));
        } else if (id == Common.NET_RECHARGE_ADD_ORDER_WEIXIN) {
            MyBaseApplication.getApplication().setFilterLock(true);
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
                        .content("{\"golden_coin\":\"" + mAdapter.getItem(position).getName() + "\"}")
                        .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .id(Common.NET_RECHARGE_ADD_ORDER).tag(Common.NET_RECHARGE_ADD_ORDER)
                        .build().execute(new MyStringCallback(mContext, this, true));
                break;
        }
    }

    @Override
    public void itemPay(int num, String orderId) {
        if (num == 1) {
            whatPay = 0;
            OkHttpUtils.postString().url(Common.Url_Recharge_Add_Order_Alipay + orderId).content("{}")
                    .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .id(Common.NET_RECHARGE_ADD_ORDER_ALIPAY).tag(Common.NET_RECHARGE_ADD_ORDER_ALIPAY)
                    .build().execute(new MyStringCallback(mContext, this, true));
        } else {
            whatPay = 1;
            OkHttpUtils.postString().url(Common.Url_Recharge_Add_Order_WeiXin).content("{\"order_id\":\"" + orderId + "\"}")
                    .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .id(Common.NET_RECHARGE_ADD_ORDER_WEIXIN).tag(Common.NET_RECHARGE_ADD_ORDER_WEIXIN)
                    .build().execute(new MyStringCallback(mContext, this, true));
        }
    }

    @Override
    public void payResult(String str) {
        MyBaseApplication.getApplication().setFilterLock(false);
    }
}
