package com.atman.wysq.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MyGiftGridViewAdapter;
import com.atman.wysq.model.response.MyGiftListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.widget.MyGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by vavid on 2016/9/21.
 */
public class MyGiftActivity extends MyBaseActivity {

    @Bind(R.id.mygift_num_tx)
    TextView mygiftNumTx;
    @Bind(R.id.mygift_charm_tx)
    TextView mygiftCharmTx;
    @Bind(R.id.mygift_list_gv)
    MyGridView mygiftListGv;
    @Bind(R.id.item_mygift_one_img)
    ImageView itemMygiftOneImg;
    @Bind(R.id.item_mygift_name_one_tx)
    TextView itemMygiftNameOneTx;
    @Bind(R.id.item_mygift_num_one_tx)
    TextView itemMygiftNumOneTx;
    @Bind(R.id.item_mygift_two_img)
    ImageView itemMygiftTwoImg;
    @Bind(R.id.item_mygift_name_two_tx)
    TextView itemMygiftNameTwoTx;
    @Bind(R.id.item_mygift_num_two_tx)
    TextView itemMygiftNumTwoTx;
    @Bind(R.id.item_mygift_three_img)
    ImageView itemMygiftThreeImg;
    @Bind(R.id.item_mygift_name_three_tx)
    TextView itemMygiftNameThreeTx;
    @Bind(R.id.item_mygift_num_three_tx)
    TextView itemMygiftNumThreeTx;

    private Context mContext = MyGiftActivity.this;
    private MyGiftListModel mMyGiftListModel;
    private List<MyGiftListModel.BodyBean.RowsBean> listGV = new ArrayList<>();
    private List<MyGiftListModel.BodyBean.RowsBean> listGVBottom = new ArrayList<>();

    private MyGiftGridViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygift);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, MyGiftActivity.class);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("我的礼物");
        setBarRightIv(R.mipmap.mygift_bar_right_ic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    showLogin();
                } else {
                    startActivity(RechargeActivity.buildIntent(mContext
                            , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getGold_coin()
                            , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getConvert_coin()));
                }
            }
        });

        initGridView();
    }

    private void initGridView() {
        mAdapter = new MyGiftGridViewAdapter(mContext);
        mygiftListGv.setAdapter(mAdapter);
        mygiftListGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toGiftDetailed(mAdapter.getItem(position).getGift_id());
            }
        });
    }

    private void toGiftDetailed(int gift_id) {
        startActivity(GiftDetailedListActivity.buildIntent(mContext, gift_id));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_MyGiftList)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_MYGIFTLIST).id(Common.NET_GET_MYGIFTLIST).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_MYGIFTLIST) {
            mMyGiftListModel = mGson.fromJson(data, MyGiftListModel.class);
            updateView();
        }
    }

    private void updateView() {

        int num = mMyGiftListModel.getBody().getRows().size();
        for (int i = 0; i < num; i++) {
            if (i < (num - 3)) {
                listGV.add(mMyGiftListModel.getBody().getRows().get(i));
            } else {
                listGVBottom.add(mMyGiftListModel.getBody().getRows().get(i));
            }
        }

        RelativeLayout.LayoutParams paramsOne = (RelativeLayout.LayoutParams) itemMygiftNameOneTx.getLayoutParams();
        paramsOne.setMargins(0, 0, getmWidth() / 10, 0);
        itemMygiftNameOneTx.setLayoutParams(paramsOne);
        RelativeLayout.LayoutParams paramsTwo = (RelativeLayout.LayoutParams) itemMygiftNumTwoTx.getLayoutParams();
        paramsTwo.setMargins(0, 0, getmWidth() / 10, 0);
        itemMygiftNumTwoTx.setLayoutParams(paramsTwo);
        RelativeLayout.LayoutParams paramsThree = (RelativeLayout.LayoutParams) itemMygiftNumThreeTx.getLayoutParams();
        paramsThree.setMargins(0, 0, getmWidth() / 3, 0);
        itemMygiftNumThreeTx.setLayoutParams(paramsThree);

        for (int i = 0; i < listGVBottom.size(); i++) {
            switch (i) {
                case 0:
                    itemMygiftNameOneTx.setText(listGVBottom.get(i).getName());
                    if (listGVBottom.get(i).getUser_count() == 0) {
                        itemMygiftNumOneTx.setVisibility(View.GONE);
                        ImageLoader.getInstance().displayImage(Common.ImageUrl + listGVBottom.get(i).getGray_pic_url()
                                , itemMygiftOneImg, MyBaseApplication.getApplication().getOptionsNot());
                    } else {
                        itemMygiftNumOneTx.setVisibility(View.VISIBLE);
                        itemMygiftNumOneTx.setText("x" + listGVBottom.get(i).getUser_count());
                        ImageLoader.getInstance().displayImage(Common.ImageUrl + listGVBottom.get(i).getPic_url()
                                , itemMygiftOneImg, MyBaseApplication.getApplication().getOptionsNot());
                    }
                    break;
                case 1:
                    itemMygiftNameTwoTx.setText(listGVBottom.get(i).getName());
                    if (listGVBottom.get(i).getUser_count() == 0) {
                        itemMygiftNumTwoTx.setVisibility(View.GONE);
                        ImageLoader.getInstance().displayImage(Common.ImageUrl + listGVBottom.get(i).getGray_pic_url()
                                , itemMygiftTwoImg, MyBaseApplication.getApplication().getOptionsNot());
                    } else {
                        itemMygiftNumTwoTx.setVisibility(View.VISIBLE);
                        itemMygiftNumTwoTx.setText("x" + listGVBottom.get(i).getUser_count());
                        ImageLoader.getInstance().displayImage(Common.ImageUrl + listGVBottom.get(i).getPic_url()
                                , itemMygiftTwoImg, MyBaseApplication.getApplication().getOptionsNot());
                    }
                    break;
                case 2:
                    itemMygiftNameThreeTx.setText(listGVBottom.get(i).getName());
                    if (listGVBottom.get(i).getUser_count() == 0) {
                        itemMygiftNumThreeTx.setVisibility(View.GONE);
                        ImageLoader.getInstance().displayImage(Common.ImageUrl + listGVBottom.get(i).getGray_pic_url()
                                , itemMygiftThreeImg, MyBaseApplication.getApplication().getOptionsNot());
                    } else {
                        itemMygiftNumThreeTx.setVisibility(View.VISIBLE);
                        itemMygiftNumThreeTx.setText("x" + listGVBottom.get(i).getUser_count());
                        ImageLoader.getInstance().displayImage(Common.ImageUrl + listGVBottom.get(i).getPic_url()
                                , itemMygiftThreeImg, MyBaseApplication.getApplication().getOptionsNot());
                    }
                    break;
            }
        }

        mAdapter.addShop(listGV);

        mygiftNumTx.setText("收到礼物" + mMyGiftListModel.getBody().getTotal() + "件");
        mygiftCharmTx.setText("魅力值:" + mMyGiftListModel.getBody().getCharm());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_MYGIFTLIST);
    }

    @OnClick({R.id.item_mygift_one_rl, R.id.item_mygift_two_rl, R.id.item_mygift_three_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_mygift_one_rl:
                toGiftDetailed(listGVBottom.get(0).getGift_id());
                break;
            case R.id.item_mygift_two_rl:
                toGiftDetailed(listGVBottom.get(1).getGift_id());
                break;
            case R.id.item_mygift_three_rl:
                toGiftDetailed(listGVBottom.get(2).getGift_id());
                break;
        }
    }
}
