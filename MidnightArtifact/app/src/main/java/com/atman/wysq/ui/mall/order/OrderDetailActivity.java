package com.atman.wysq.ui.mall.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.AliPayResponseModel;
import com.atman.wysq.model.response.GetAddressByOrderIdModel;
import com.atman.wysq.model.response.GetLogisticsModel;
import com.atman.wysq.model.response.GetOrderDetailModel;
import com.atman.wysq.model.response.WeiXinPayResponseModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.WebPageActivity;
import com.atman.wysq.ui.mall.GoodsDetailActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.atman.wysq.widget.pay.PayDialog;
import com.base.baselibs.net.MyStringCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/25 15:13
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class OrderDetailActivity extends MyBaseActivity implements PayDialog.payItemCallback, PayDialog.payResultCallback {

    @Bind(R.id.order_detail_orderid_tx)
    TextView orderDetailOrderidTx;
    @Bind(R.id.order_detail_state_tx)
    TextView orderDetailStateTx;
    @Bind(R.id.order_detail_time_tx)
    TextView orderDetailTimeTx;
    @Bind(R.id.order_detail_name_tx)
    TextView orderDetailNameTx;
    @Bind(R.id.order_detail_phone_tx)
    TextView orderDetailPhoneTx;
    @Bind(R.id.order_detail_address_tx)
    TextView orderDetailAddressTx;
    @Bind(R.id.order_detail_head_iv)
    ImageView orderDetailHeadIv;
    @Bind(R.id.orderdetail_title_tx)
    TextView orderDetailTitleTx;
    @Bind(R.id.order_detail_price_tx)
    TextView orderDetailPriceTx;
    @Bind(R.id.order_detail_num_tx)
    TextView orderDetailNumTx;
    @Bind(R.id.order_detail_totalprice_tx)
    TextView orderDetailTotalpriceTx;
    @Bind(R.id.order_detail_bt)
    Button orderDetailBt;
    @Bind(R.id.two_ll)
    LinearLayout twoLl;
    @Bind(R.id.order_detail_logistics_tx)
    TextView orderDetailLogisticsTx;
    @Bind(R.id.four_rl)
    LinearLayout fourRl;

    private Context mContext = OrderDetailActivity.this;

    private String orderId;
    private String pic;
    private String title;
    private int num;
    private float price;
    private float totalPrice;
    private int addressid;
    private GetOrderDetailModel mGetOrderDetailModel;
    private GetAddressByOrderIdModel mGetAddressByOrderIdModel;
    private int goodsId;
    private int whatPay = 0;

    private PayDialog mPayDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String orderId, String pic, String title
            , int num, float price, float totalPrice, int addressid, int goodsId) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("pic", pic);
        intent.putExtra("title", title);
        intent.putExtra("num", num);
        intent.putExtra("price", price);
        intent.putExtra("totalPrice", totalPrice);
        intent.putExtra("addressid", addressid);
        intent.putExtra("goodsId", goodsId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("订单详情");

        orderId = getIntent().getStringExtra("orderId");
        pic = getIntent().getStringExtra("pic");
        title = getIntent().getStringExtra("title");
        num = getIntent().getIntExtra("num", 0);
        price = getIntent().getFloatExtra("price", 0);
        totalPrice = getIntent().getFloatExtra("totalPrice", 0);
        addressid = getIntent().getIntExtra("addressid", 0);
        goodsId = getIntent().getIntExtra("goodsId", 0);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();

        OkHttpUtils.get().url(Common.Url_Get_OrderDetail + orderId).id(Common.NET_GET_ORDERDETAIL)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_ORDERDETAIL).build().execute(new MyStringCallback(mContext, this, true));
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
        if (id == Common.NET_GET_ORDERDETAIL) {
            mGetOrderDetailModel = mGson.fromJson(data, GetOrderDetailModel.class);
            OkHttpUtils.get().url(Common.Url_Get_Address_ByOrderId + orderId)
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_GET_ADDRESS_LIST).id(Common.NET_GET_ADDRESS_LIST).build()
                    .execute(new MyStringCallback(mContext, this, false));
        } else if (id == Common.NET_GET_ADDRESS_LIST) {
            mGetAddressByOrderIdModel = mGson.fromJson(data, GetAddressByOrderIdModel.class);
            updateUI();
        } else if (id == Common.NET_RECHARGE_ADD_ORDER_ALIPAY) {
            MyBaseApplication.getApplication().setFilterLock(true);
            AliPayResponseModel mAliPayResponseOneModel = mGson.fromJson(data, AliPayResponseModel.class);
            String parms = mAliPayResponseOneModel.getBody().getParam().replace("\\\"", "\"");
            int start = parms.indexOf("&sign");
            mPayDialog.aliPay(OrderDetailActivity.this, parms.substring(0, start));
        } else if (id == Common.NET_RECHARGE_ADD_ORDER_WEIXIN) {
            MyBaseApplication.getApplication().setFilterLock(true);
            WeiXinPayResponseModel WeiXinPayResponseModelm = mGson.fromJson(data, WeiXinPayResponseModel.class);
            mPayDialog.weixinPay(WeiXinPayResponseModelm);
        } else if(id == Common.NET_GET_LOGISTICS) {
            GetLogisticsModel mGetLogisticsModel = mGson.fromJson(data, GetLogisticsModel.class);
            if (mGetLogisticsModel.getBody()!=null) {
                startActivity(WebPageActivity.buildIntent(mContext, mGetLogisticsModel.getBody(), "物流跟踪"));
            }
        }
    }

    private void updateUI() {
        orderDetailOrderidTx.setText(mGetOrderDetailModel.getBody().getOrder_id());

        if (mGetOrderDetailModel.getBody().getType() == 1) {
            orderDetailStateTx.setText("未支付");
            orderDetailStateTx.setTextColor(getResources().getColor(R.color.color_red));
        } else if (mGetOrderDetailModel.getBody().getType() == 2) {
            orderDetailStateTx.setText("已支付");
            orderDetailStateTx.setTextColor(getResources().getColor(R.color.color_blue));
        } else if (mGetOrderDetailModel.getBody().getType() == 3) {
            orderDetailStateTx.setText("已发货");
            orderDetailStateTx.setTextColor(getResources().getColor(R.color.color_blue));
        } else if (mGetOrderDetailModel.getBody().getType() == 4) {
            orderDetailStateTx.setText("交易取消");
            orderDetailStateTx.setTextColor(getResources().getColor(R.color.color_red));
        } else if (mGetOrderDetailModel.getBody().getType() == 5) {
            orderDetailStateTx.setText("交易成功");
            orderDetailStateTx.setTextColor(getResources().getColor(R.color.color_blue));
        } else if (mGetOrderDetailModel.getBody().getType() == 5) {
            orderDetailStateTx.setText("申请退换");
            orderDetailStateTx.setTextColor(getResources().getColor(R.color.color_red));
        } else {
            orderDetailStateTx.setText("未知");
            orderDetailStateTx.setTextColor(getResources().getColor(R.color.color_red));
        }

        if (mGetOrderDetailModel.getBody().getType() == 1) {
            orderDetailBt.setText("去付款");
            orderDetailBt.setBackgroundResource(R.drawable.bt_orderdetail_selector);
            orderDetailBt.setTextColor(getResources().getColor(R.color.color_black));
        } else {
            orderDetailBt.setText("联系客服");
            orderDetailBt.setBackgroundResource(R.drawable.bt_order_selector);
            orderDetailBt.setTextColor(getResources().getColor(R.color.orderdetail_text__bt));
        }

        orderDetailTimeTx.setText(MyTools.convertTime(mGetOrderDetailModel.getBody().getCreate_time(), "yyyy.MM.dd HH:mm"));
        orderDetailTitleTx.setText(title);
        orderDetailPriceTx.setText("单价：¥ " + price);
        orderDetailNumTx.setText("数量：" + num);
        orderDetailTotalpriceTx.setText("总金额：¥ " + totalPrice);

        if (mGetAddressByOrderIdModel.getBody() != null) {
            twoLl.setVisibility(View.VISIBLE);
            orderDetailNameTx.setText(mGetAddressByOrderIdModel.getBody().getReceiver_name());
            orderDetailPhoneTx.setText(mGetAddressByOrderIdModel.getBody().getReceiver_phone());
            orderDetailAddressTx.setText(mGetAddressByOrderIdModel.getBody().getReceiver_area_name()
                    + mGetAddressByOrderIdModel.getBody().getReceiver_address());
            ImageLoader.getInstance().displayImage(Common.ImageUrl + pic, orderDetailHeadIv, MyBaseApplication.getApplication().getOptionsNot());
        } else {
            twoLl.setVisibility(View.GONE);
            orderDetailHeadIv.setImageResource(R.mipmap.jinbin);
        }

        if (mGetOrderDetailModel.getBody().getTransport_id()!=null) {
            orderDetailLogisticsTx.setText(mGetOrderDetailModel.getBody().getTransport_id()
                    +"["+mGetOrderDetailModel.getBody().getTransport_company()+"]");
            fourRl.setVisibility(View.VISIBLE);
        } else {
            fourRl.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_ORDERDETAIL);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_ADDRESS_LIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RECHARGE_ADD_ORDER_ALIPAY);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RECHARGE_ADD_ORDER_WEIXIN);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_LOGISTICS);
    }

    @OnClick({R.id.order_detail_bt, R.id.three_rl, R.id.four_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.four_rl:
                OkHttpUtils.get().url(Common.Url_Get_Logistics + orderId)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_GET_LOGISTICS).id(Common.NET_GET_LOGISTICS).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.order_detail_bt:
                if (mGetOrderDetailModel.getBody().getType() == 1) {
                    mPayDialog = new PayDialog(mContext, orderId + "", this);
                    mPayDialog.show();
                } else {
                    MyBaseApplication.getApplication().setFilterLock(true);
                    toPhone(mContext, getResources().getString(R.string.personal_service_phone_str));
                }
                break;
            case R.id.three_rl:
                if (mGetAddressByOrderIdModel.getBody() != null) {
                    startActivity(GoodsDetailActivity.buildIntent(mContext, goodsId));
                }
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
