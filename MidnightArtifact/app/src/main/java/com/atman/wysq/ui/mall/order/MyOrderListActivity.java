package com.atman.wysq.ui.mall.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.OrderListAdapter;
import com.atman.wysq.model.response.AliPayResponseModel;
import com.atman.wysq.model.response.GetOrderListModel;
import com.atman.wysq.model.response.WeiXinPayResponseModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.pay.PayDialog;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/25 09:58
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyOrderListActivity extends MyBaseActivity implements AdapterInterface, PayDialog.payItemCallback, PayDialog.payResultCallback {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;

    private Context mContext = MyOrderListActivity.this;

    private View mEmpty;
    private TextView mEmptyTX;
    private OrderListAdapter mAdapter;
    private int mPage = 1;
    private GetOrderListModel mGetOrderListModel;
    private int whatPay = 0;

    private PayDialog mPayDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorderlist);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("我的订单");

        initListView();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        dohttp(true);
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无订单");

        mAdapter = new OrderListAdapter(mContext, this);
        pullToRefreshListView.setEmptyView(mEmpty);
        pullToRefreshListView.setAdapter(mAdapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
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
        if (id==Common.NET_GET_ORDERLIST) {
            mGetOrderListModel = mGson.fromJson(data, GetOrderListModel.class);
            if (mGetOrderListModel.getBody() == null
                    || mGetOrderListModel.getBody().size() == 0) {
                if (mAdapter!=null && mAdapter.getCount()>0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                mAdapter.addBody(mGetOrderListModel.getBody());
            }
        } else if (id == Common.NET_RECHARGE_ADD_ORDER_ALIPAY) {
            MyBaseApplication.getApp().setFilterLock(true);
            AliPayResponseModel mAliPayResponseOneModel = mGson.fromJson(data, AliPayResponseModel.class);
            String parms = mAliPayResponseOneModel.getBody().getParam().replace("\\\"","\"");
            int start = parms.indexOf("&sign");
            mPayDialog.aliPay(MyOrderListActivity.this, parms.substring(0, start));
        } else if (id == Common.NET_RECHARGE_ADD_ORDER_WEIXIN) {
            MyBaseApplication.getApp().setFilterLock(true);
            WeiXinPayResponseModel WeiXinPayResponseModelm = mGson.fromJson(data, WeiXinPayResponseModel.class);
            mPayDialog.weixinPay(WeiXinPayResponseModelm);
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        mPage = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        mPage += 1;
        dohttp(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        mPage = 1;
        mAdapter.clearData();
        dohttp(false);
    }

    private void dohttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_OrderList+mPage).id(Common.NET_GET_ORDERLIST)
                .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                .tag(Common.NET_GET_ORDERLIST).build().execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_ORDERLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RECHARGE_ADD_ORDER_ALIPAY);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RECHARGE_ADD_ORDER_WEIXIN);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_orderlist_root_ll:
                startActivityForResult(OrderDetailActivity.buildIntent(mContext,
                        mAdapter.getItem(position).getOrder_id()
                        , mAdapter.getItem(position).getItems().get(0).getGoods_pic()
                        ,mAdapter.getItem(position).getItems().get(0).getGoods_title()
                        ,mAdapter.getItem(position).getItems().get(0).getAmount()
                        ,mAdapter.getItem(position).getItems().get(0).getPrice()
                        ,mAdapter.getItem(position).getTotal_price()
                        ,mAdapter.getItem(position).getAddress_id()
                        ,mAdapter.getItem(position).getItems().get(0).getGoods_id()), Common.toOrderDetail);
                break;
            case R.id.item_orderlist_bt:
                if (mAdapter.getItem(position).getType()==1) {
                    mPayDialog = new PayDialog(mContext, mAdapter.getItem(position).getOrder_id()+"" ,this);
                    mPayDialog.show();
                } else {
                    MyBaseApplication.getApp().setFilterLock(true);
                    toPhone(mContext, getResources().getString(R.string.personal_service_phone_str));
                }
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
