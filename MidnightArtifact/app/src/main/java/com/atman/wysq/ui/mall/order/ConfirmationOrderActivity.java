package com.atman.wysq.ui.mall.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.request.AddOrderRequestModel;
import com.atman.wysq.model.response.AddOrderResponseModel;
import com.atman.wysq.model.response.AliPayResponseModel;
import com.atman.wysq.model.response.GetAddressListResponseModel;
import com.atman.wysq.model.response.WeiXinPayResponseModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.mall.address.AddAddressActivity;
import com.atman.wysq.ui.mall.address.AddressManageActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.atman.wysq.widget.pay.PayDialog;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/21 11:32
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ConfirmationOrderActivity extends MyBaseActivity implements PayDialog.payResultCallback {

    @Bind(R.id.confirmationorder_name_tx)
    TextView confirmationorderNameTx;
    @Bind(R.id.confirmationorder_phone_tx)
    TextView confirmationorderPhoneTx;
    @Bind(R.id.confirmationorder_addr_tx)
    TextView confirmationorderAddrTx;
    @Bind(R.id.right_arrow_iv)
    ImageView rightArrowIv;
    @Bind(R.id.confirmationorder_empty_tx)
    TextView confirmationorderEmptyTx;
    @Bind(R.id.confirmationorder_alipay_iv)
    ImageView confirmationorderAlipayIv;
    @Bind(R.id.confirmationorder_weixinpay_iv)
    ImageView confirmationorderWeixinpayIv;
    @Bind(R.id.confirmationorder_orderprice_tx)
    TextView confirmationorderOrderpriceTx;
    @Bind(R.id.confirmationorder_play_bt)
    Button confirmationorderPlayBt;
    @Bind(R.id.confirmationorder_goods_iv)
    ImageView confirmationorderGoodsIv;
    @Bind(R.id.confirmationorder_goodsname_tv)
    TextView confirmationorderGoodsnameTv;
    @Bind(R.id.confirmationorder_goodspeice_tv)
    TextView confirmationorderGoodspeiceTv;
    @Bind(R.id.confirmationorder_reduce_bt)
    Button confirmationorderReduceBt;
    @Bind(R.id.confirmationorder_num_bt)
    TextView confirmationorderNumBt;
    @Bind(R.id.confirmationorder_add_bt)
    Button confirmationorderAddBt;

    private Context mContext = ConfirmationOrderActivity.this;
    private GetAddressListResponseModel mGetAddressListResponseModel;
    private AddOrderResponseModel mAddOrderResponseModel;
    private PayDialog mPayDialog;

    private String goodsName;
    private String goodsUrl;
    private String GoodsPrice;
    private int goodsId;
    private int num = 1;
    private int mAddreId = -1;

    private int whatPay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmationorder);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String goodsName, String goodsUrl, String GoodsPrice, int goodsId) {
        Intent intent = new Intent(context, ConfirmationOrderActivity.class);
        intent.putExtra("goodsName", goodsName);
        intent.putExtra("goodsUrl", goodsUrl);
        intent.putExtra("GoodsPrice", GoodsPrice);
        intent.putExtra("goodsId", goodsId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("确认订单");

        mPayDialog = new PayDialog(mContext);

        goodsName = getIntent().getStringExtra("goodsName");
        goodsUrl = getIntent().getStringExtra("goodsUrl");
        GoodsPrice = getIntent().getStringExtra("GoodsPrice");
        goodsId = getIntent().getIntExtra("goodsId", -1);

        confirmationorderReduceBt.setEnabled(false);
        confirmationorderGoodsnameTv.setText(goodsName);
        confirmationorderGoodspeiceTv.setText("¥ "+ MyTools.formatfloat(Float.parseFloat(GoodsPrice)));
        confirmationorderOrderpriceTx.setText("¥ "+ MyTools.formatfloat(Float.parseFloat(GoodsPrice)));
        ImageLoader.getInstance().displayImage(Common.ImageUrl+goodsUrl,confirmationorderGoodsIv,MyBaseApplication.getApp().getOptionsNot());
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        doHttp();
    }

    private void doHttp() {
        OkHttpUtils.get().url(Common.Url_Get_Address_List).addHeader("cookie", MyBaseApplication.getApp().getCookie())
                .tag(Common.NET_GET_ADDRESS_LIST).id(Common.NET_GET_ADDRESS_LIST).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (whatPay ==1) {
            MyBaseApplication.getApp().setFilterLock(false);
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_ADDRESS_LIST) {
            mGetAddressListResponseModel = mGson.fromJson(data, GetAddressListResponseModel.class);
            if (mGetAddressListResponseModel.getBody().size() == 0) {
                confirmationorderEmptyTx.setVisibility(View.VISIBLE);
                mAddreId = -1;
                confirmationorderNameTx.setText("");
                confirmationorderPhoneTx.setText("");
                confirmationorderAddrTx.setText("");
            } else {
                confirmationorderNameTx.setText(mGetAddressListResponseModel.getBody().get(0).getReceiver_name());
                confirmationorderPhoneTx.setText(mGetAddressListResponseModel.getBody().get(0).getReceiver_phone());
                confirmationorderAddrTx.setText(mGetAddressListResponseModel.getBody().get(0).getReceiver_area_name()
                        +mGetAddressListResponseModel.getBody().get(0).getReceiver_address());
                mAddreId = mGetAddressListResponseModel.getBody().get(0).getAddress_id();
                confirmationorderEmptyTx.setVisibility(View.GONE);
            }
        } else if (id == Common.NET_ADD_ORDER) {
            mAddOrderResponseModel = mGson.fromJson(data, AddOrderResponseModel.class);
            if (confirmationorderAlipayIv.getVisibility() == View.VISIBLE) {
                OkHttpUtils.postString().url(Common.Url_Recharge_Add_Order_Alipay + mAddOrderResponseModel.getBody().getOrder_id()).content("{}")
                        .mediaType(Common.JSON).addHeader("cookie",MyBaseApplication.getApp().getCookie())
                        .id(Common.NET_RECHARGE_ADD_ORDER_ALIPAY).tag(Common.NET_RECHARGE_ADD_ORDER_ALIPAY)
                        .build().execute(new MyStringCallback(mContext, this, true));
            } else {
                OkHttpUtils.postString().url(Common.Url_Recharge_Add_Order_WeiXin).content("{\"order_id\":\""+mAddOrderResponseModel.getBody().getOrder_id()+"\"}")
                        .mediaType(Common.JSON).addHeader("cookie",MyBaseApplication.getApp().getCookie())
                        .id(Common.NET_RECHARGE_ADD_ORDER_WEIXIN).tag(Common.NET_RECHARGE_ADD_ORDER_WEIXIN)
                        .build().execute(new MyStringCallback(mContext, this, true));
            }
        } else if (id == Common.NET_RECHARGE_ADD_ORDER_ALIPAY) {
            whatPay = 0;
            MyBaseApplication.getApp().setFilterLock(true);
            AliPayResponseModel mAliPayResponseOneModel = mGson.fromJson(data, AliPayResponseModel.class);
            String parms = mAliPayResponseOneModel.getBody().getParam().replace("\\\"","\"");
            int start = parms.indexOf("&sign");
            LogUtils.e("mContext:"+mContext);
            mPayDialog.aliPay(mContext, parms.substring(0, start));
        } else if (id == Common.NET_RECHARGE_ADD_ORDER_WEIXIN) {
            whatPay = 1;
            MyBaseApplication.getApp().setFilterLock(true);
            WeiXinPayResponseModel WeiXinPayResponseModelm = mGson.fromJson(data, WeiXinPayResponseModel.class);
            mPayDialog.weixinPay(WeiXinPayResponseModelm);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_ADDRESS_LIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_ORDER);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RECHARGE_ADD_ORDER_WEIXIN);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RECHARGE_ADD_ORDER_ALIPAY);
    }

    @OnClick({R.id.confirmationorder_alipay_iv, R.id.confirmationorder_alipay_rl, R.id.confirmationorder_empty_tx,
            R.id.confirmationorder_weixinpay_iv, R.id.confirmationorder_weixinpay_rl, R.id.confirmationorder_play_bt,
            R.id.confirmationorder_reduce_bt, R.id.confirmationorder_add_bt,R.id.confirmationorder_addr_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmationorder_addr_rl:
                startActivityForResult(AddressManageActivity.buildInTent(mContext, mAddreId), Common.toManageAddress);
                break;
            case R.id.confirmationorder_empty_tx:
                startActivityForResult(new Intent(mContext, AddAddressActivity.class), Common.toAddAddress);
                break;
            case R.id.confirmationorder_alipay_iv:
            case R.id.confirmationorder_alipay_rl:
                confirmationorderAlipayIv.setVisibility(View.VISIBLE);
                confirmationorderWeixinpayIv.setVisibility(View.GONE);
                break;
            case R.id.confirmationorder_weixinpay_iv:
            case R.id.confirmationorder_weixinpay_rl:
                confirmationorderAlipayIv.setVisibility(View.GONE);
                confirmationorderWeixinpayIv.setVisibility(View.VISIBLE);
                break;
            case R.id.confirmationorder_play_bt:

                if (mAddreId == -1) {
                    showToast("请设置收获地址");
                    return;
                }
                AddOrderRequestModel.OrderEntity temp = new AddOrderRequestModel.OrderEntity(goodsId, num);
                List<AddOrderRequestModel.OrderEntity> list = new ArrayList<>();
                list.add(temp);
                AddOrderRequestModel mAddOrderRequestModel = new AddOrderRequestModel(mAddreId, list, 1);

                OkHttpUtils.postString().url(Common.Url_Add_Order)
                        .addHeader("cookie", MyBaseApplication.getApp().getCookie()).mediaType(Common.JSON)
                        .content(mGson.toJson(mAddOrderRequestModel)).tag(Common.NET_ADD_ORDER).id(Common.NET_ADD_ORDER)
                        .build().execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.confirmationorder_reduce_bt:
                num -= 1;
                if (num>1) {
                    confirmationorderNumBt.setText(num+"");
                    confirmationorderOrderpriceTx.setText("¥ "+MyTools.formatfloat(Float.parseFloat(GoodsPrice)*num));
                } else {
                    confirmationorderNumBt.setText("1");
                    confirmationorderOrderpriceTx.setText("¥ "+MyTools.formatfloat(Float.parseFloat(GoodsPrice)));
                    confirmationorderReduceBt.setEnabled(false);
                }
                break;
            case R.id.confirmationorder_add_bt:
                LogUtils.e("confirmationorder_add_bt");
                confirmationorderReduceBt.setEnabled(true);
                num += 1;
                confirmationorderNumBt.setText(num+"");
                confirmationorderOrderpriceTx.setText("¥ "+MyTools.formatfloat(Float.parseFloat(GoodsPrice)*num));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toManageAddress) {
            mAddreId = data.getIntExtra("addressId",-1);
            if (mAddreId>0) {
                confirmationorderNameTx.setText(data.getStringExtra("name"));
                confirmationorderPhoneTx.setText(data.getStringExtra("phone"));
                confirmationorderAddrTx.setText(data.getStringExtra("address"));
            } else {
                doHttp();
            }
        } else if (requestCode == Common.toAddAddress) {
            doHttp();
        }
    }

    @Override
    public void payResult(String str) {
        MyBaseApplication.getApp().setFilterLock(false);
    }
}
