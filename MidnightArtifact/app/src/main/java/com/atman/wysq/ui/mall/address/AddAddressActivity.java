package com.atman.wysq.ui.mall.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.request.AddAddressRequestModel;
import com.atman.wysq.model.request.UpdateAddressRequestModel;
import com.atman.wysq.model.response.GetDataModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.SelectItemPopwindow;
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
 * 时间 16/7/22 11:15
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class AddAddressActivity extends MyBaseActivity implements SelectItemPopwindow.PopWindowsCallback {

    @Bind(R.id.addaddress_name_et)
    MyCleanEditText addaddressNameEt;
    @Bind(R.id.addaddress_phone_et)
    MyCleanEditText addaddressPhoneEt;
    @Bind(R.id.addaddress_province_et)
    TextView addaddressProvinceEt;
    @Bind(R.id.addaddress_city_et)
    TextView addaddressCityEt;
    @Bind(R.id.addaddress_county_et)
    TextView addaddressCountyEt;
    @Bind(R.id.addaddress_address_et)
    MyCleanEditText addaddressAddressEt;

    private Context mContext = AddAddressActivity.this;
    private GetDataModel mGetProvince;
    private GetDataModel mGetCity;
    private GetDataModel mGetDistrict;
    private SelectItemPopwindow mSelectItemPopwindow;

    private int mProvinceTag = 1;
    private int mCityTag = 2;
    private int mConntryTag = 3;
    private View mView;
    private int mCityTempId = -1;
    private int mCountyTempId = -1;

    private String receiverAddress;
    private String receiverAreaName;
    private String receiverName;
    private String receiverPhone;
    private String receiverArea;

    private int addressId;
    private int useCount;
    private int status;

    private ImageView mRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, int address_id, String receiver_name, String receiver_phone
            , String receiver_area_name, String receiver_address, String receiver_area, int status, int use_count){
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra("address_id", address_id);
        intent.putExtra("receiver_name", receiver_name);
        intent.putExtra("receiver_phone", receiver_phone);
        intent.putExtra("receiver_area_name", receiver_area_name);
        intent.putExtra("receiver_address", receiver_address);
        intent.putExtra("receiver_area", receiver_area);
        intent.putExtra("status", status);
        intent.putExtra("use_count", use_count);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        getIntentData();

        mRight = setBarRightIv(R.mipmap.bt_create_ok);
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiverName = addaddressNameEt.getText().toString().trim();
                receiverPhone = addaddressPhoneEt.getText().toString().trim();
                receiverAddress = addaddressAddressEt.getText().toString().trim();
                receiverAreaName = addaddressProvinceEt.getText().toString().trim()
                        + addaddressCityEt.getText().toString().trim()
                        + addaddressCountyEt.getText().toString().trim();
                if (receiverName.isEmpty()) {
                    showToast("请输入收货人姓名");
                    return;
                }
                if (receiverPhone.isEmpty()) {
                    showToast("请输入手机号码");
                    return;
                }
                if (!StringUtils.isPhone(receiverPhone)) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (addaddressProvinceEt.getText().toString().trim().isEmpty()) {
                    showToast("请选择省(直辖市)");
                    return;
                }
                if (addaddressCityEt.getText().toString().trim().isEmpty()) {
                    showToast("请选择地区(市)");
                    return;
                }
                if (addaddressCountyEt.getText().toString().trim().isEmpty()) {
                    showToast("请选择区(县)");
                    return;
                }
                if (receiverAddress.isEmpty()) {
                    showToast("请输入详细地址");
                    return;
                }
                String parmStr = "";
                if (addressId == -1) {
                    AddAddressRequestModel mAdd = new AddAddressRequestModel(receiverAddress
                            , receiverAreaName, receiverName, receiverPhone, receiverArea);
                    parmStr = mGson.toJson(mAdd);
                } else {
                    UpdateAddressRequestModel mUpdate = new UpdateAddressRequestModel(addressId
                            ,receiverAddress,receiverArea,receiverAreaName, receiverName, receiverPhone
                            , status, System.currentTimeMillis(), useCount);
                    parmStr = mGson.toJson(mUpdate);
                }

                OkHttpUtils.postString().url(Common.Url_Add_Address).content(parmStr)
                        .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApp().getCookie())
                        .id(Common.NET_ADD_ADDRESS).tag(Common.NET_ADD_ADDRESS).build()
                        .execute(new MyStringCallback(mContext, AddAddressActivity.this, true));
            }
        });
    }

    private void getIntentData() {
        addressId = getIntent().getIntExtra("address_id", -1);
        if (addressId==-1){
            setBarTitleTx("新增地址");
            return;
        } else {
            setBarTitleTx("编辑地址");
        }
        status = getIntent().getIntExtra("status", -1);
        useCount = getIntent().getIntExtra("use_count", -1);
        receiverName = getIntent().getStringExtra("receiver_name");
        addaddressNameEt.setText(receiverName);
        receiverPhone = getIntent().getStringExtra("receiver_phone");
        addaddressPhoneEt.setText(receiverPhone);
        String str = getIntent().getStringExtra("receiver_area_name");
        for (int i=0;i<str.split(" ").length;i++) {
            if (i==0) {
                addaddressProvinceEt.setText(str.split(" ")[0]);
            } else if (i==1) {
                addaddressCityEt.setText(str.split(" ")[1]);
            } else if (i==2) {
                addaddressCountyEt.setText(str.split(" ")[2]);
            }
        }
        receiverAddress = getIntent().getStringExtra("receiver_address");
        addaddressAddressEt.setText(receiverAddress);
        receiverArea = getIntent().getStringExtra("receiver_area");
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
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_PROVINCE) {
            mGetProvince = mGson.fromJson(data, GetDataModel.class);
            List<String> provinceList = new ArrayList<>();
            for (int i=0;i<mGetProvince.getBody().size();i++) {
                provinceList.add(mGetProvince.getBody().get(i).getCity_name());
            }
            mSelectItemPopwindow = new SelectItemPopwindow(mContext, mProvinceTag, provinceList, this);
            mSelectItemPopwindow.showTypePopupWindow(mView);
        } else if (id == Common.NET_GET_CITY) {
            mGetCity = mGson.fromJson(data, GetDataModel.class);
            List<String> cityList = new ArrayList<>();
            for (int i=0;i<mGetCity.getBody().size();i++) {
                cityList.add(mGetCity.getBody().get(i).getCity_name());
            }
            mSelectItemPopwindow = new SelectItemPopwindow(mContext, mCityTag, cityList, this);
            mSelectItemPopwindow.showTypePopupWindow(mView);
        } else if (id == Common.NET_GET_DISTRICT) {
            mGetDistrict = mGson.fromJson(data, GetDataModel.class);
            List<String> districtList = new ArrayList<>();
            for (int i=0;i<mGetDistrict.getBody().size();i++) {
                districtList.add(mGetDistrict.getBody().get(i).getCity_name());
            }
            mSelectItemPopwindow = new SelectItemPopwindow(mContext, mConntryTag, districtList, this);
            mSelectItemPopwindow.showTypePopupWindow(mView);
        } else if (id == Common.NET_ADD_ADDRESS) {
            if (addressId == -1) {
                showToast("新增收货地址成功");
            } else {
                showToast("修改收货地址成功");
            }
            Intent mIntent = new Intent();
            setResult(RESULT_OK,mIntent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_PROVINCE);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_CITY);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_DISTRICT);
    }

    @OnClick({R.id.addaddress_province_et, R.id.addaddress_city_et, R.id.addaddress_county_et})
    public void onClick(View view) {
        mView = view;
        switch (view.getId()) {
            case R.id.addaddress_province_et:
                OkHttpUtils.get().url(Common.Url_Get_Province)
                        .addHeader("cookie",MyBaseApplication.getApp().getCookie())
                        .tag(Common.NET_GET_PROVINCE).id(Common.NET_GET_PROVINCE).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.addaddress_city_et:
                if (mCityTempId==-1) {
                    showToast("请选择省(直辖市)");
                    return;
                }
                OkHttpUtils.get().url(Common.Url_Get_City+mCityTempId)
                        .addHeader("cookie",MyBaseApplication.getApp().getCookie())
                        .tag(Common.NET_GET_CITY).id(Common.NET_GET_CITY).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.addaddress_county_et:
                if (mCountyTempId==-1) {
                    showToast("请选择地区(市)");
                    return;
                }
                OkHttpUtils.get().url(Common.Url_Get_District+mCountyTempId)
                        .addHeader("cookie",MyBaseApplication.getApp().getCookie())
                        .tag(Common.NET_GET_DISTRICT).id(Common.NET_GET_DISTRICT).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
        }
    }

    @Override
    public void selectItem(int id, String str, int tag) {
        if (tag == mProvinceTag) {
            mCityTempId = mGetProvince.getBody().get(id).getCity_id();
            mCountyTempId = -1;
            addaddressCityEt.setText("");
            addaddressCountyEt.setText("");
            addaddressProvinceEt.setText(str);
        } else if (tag == mCityTag) {
            mCountyTempId = mGetCity.getBody().get(id).getCity_id();
            addaddressCountyEt.setText("");
            addaddressCityEt.setText(str);
        } else if (tag == mConntryTag) {
            receiverArea = String.valueOf(mGetDistrict.getBody().get(id).getCity_id());
            addaddressCountyEt.setText(str);
        }
    }
}
