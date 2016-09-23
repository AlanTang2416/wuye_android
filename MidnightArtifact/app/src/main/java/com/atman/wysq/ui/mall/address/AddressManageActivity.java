package com.atman.wysq.ui.mall.address;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.AddressManageListAdapter;
import com.atman.wysq.model.response.GetAddressListResponseModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.mall.order.ConfirmationOrderActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.PromptDialog;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/21 17:53
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class AddressManageActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.addressmanage_listview)
    ListView addressmanageListview;

    private Context mContext = AddressManageActivity.this;
    private GetAddressListResponseModel mGetAddressListResponseModel;
    private AddressManageListAdapter mAdapter;

    private ImageView mRight;
    private int addressId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressmanage);
        ButterKnife.bind(this);
    }

    public static Intent buildInTent(Context context, int addrId) {
        Intent intent = new Intent(context, AddressManageActivity.class);
        intent.putExtra("addrId", addrId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("收货地址");

        addressId = getIntent().getIntExtra("addrId", -1);

        getBarBackLl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        getBarBackIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        mRight = setBarRightIv(R.mipmap.bt_addnewaddress);
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, AddAddressActivity.class), Common.toAddAddress);
            }
        });
        addressmanageListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent();
                mIntent.putExtra("addressId", mAdapter.getItem(position).getAddress_id());
                mIntent.putExtra("name", mAdapter.getItem(position).getReceiver_name());
                mIntent.putExtra("phone", mAdapter.getItem(position).getReceiver_phone());
                mIntent.putExtra("address", mAdapter.getItem(position).getReceiver_area_name() + mAdapter.getItem(position).getReceiver_address());
                setResult(RESULT_OK,mIntent);
                finish();
            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_Address_List).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_ADDRESS_LIST).id(Common.NET_GET_ADDRESS_LIST).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_ADDRESS_LIST) {
            mGetAddressListResponseModel = mGson.fromJson(data, GetAddressListResponseModel.class);

            mAdapter = new AddressManageListAdapter(mContext, mGetAddressListResponseModel.getBody(), this);
            addressmanageListview.setAdapter(mAdapter);
//            mAdapter.setSelectId(addressId);
        } else if (id == Common.NET_DELETE_ADDRESS) {
            doInitBaseHttp();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_ADDRESS_LIST);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_addressmanagelist_edit_tx:
                startActivityForResult(EditAddressActivity.buildIntent(mContext
                        , mGetAddressListResponseModel.getBody().get(position).getAddress_id()
                        , mGetAddressListResponseModel.getBody().get(position).getReceiver_name()
                        , mGetAddressListResponseModel.getBody().get(position).getReceiver_phone()
                        , mGetAddressListResponseModel.getBody().get(position).getReceiver_area_name()
                        , mGetAddressListResponseModel.getBody().get(position).getReceiver_address()
                        , mGetAddressListResponseModel.getBody().get(position).getReceiver_area()
                        , mGetAddressListResponseModel.getBody().get(position).getStatus()
                        , mGetAddressListResponseModel.getBody().get(position).getUse_count()
                ), Common.toAddAddress);
                break;
            case R.id.item_addressmanagelist_delete_tx:
                showWarn(position);
                break;
        }
    }

    public void showWarn(final int position) {
        PromptDialog.Builder builder = new PromptDialog.Builder(this);
        builder.setMessage("确定要删除收货地址！");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OkHttpUtils.delete().url(Common.Url_Delete_Address+mGetAddressListResponseModel.getBody().get(position).getAddress_id())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .id(Common.NET_DELETE_ADDRESS).tag(Common.NET_DELETE_ADDRESS)
                        .build().execute(new MyStringCallback(mContext, AddressManageActivity.this, true));
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toAddAddress) {
            mAdapter.setSelectPosition(0);
            doInitBaseHttp();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回键
            LogUtils.e("onKeyDown>>>>");
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        if (mAdapter.getCount()==0) {
            Intent mIntent = new Intent(AddressManageActivity.this, ConfirmationOrderActivity.class);
            mIntent.putExtra("addressId", -2);
            setResult(RESULT_OK,mIntent);
        } else {
            int n = mAdapter.getSelectId();
            Intent mIntent = new Intent(AddressManageActivity.this, ConfirmationOrderActivity.class);
            mIntent.putExtra("addressId", mAdapter.getItem(n).getAddress_id());
            mIntent.putExtra("name", mAdapter.getItem(n).getReceiver_name());
            mIntent.putExtra("phone", mAdapter.getItem(n).getReceiver_phone());
            mIntent.putExtra("address", mAdapter.getItem(n).getReceiver_area_name() + mAdapter.getItem(n).getReceiver_address());
            setResult(RESULT_OK,mIntent);
        }
        finish();
    }


}
