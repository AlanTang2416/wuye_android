package com.atman.wysq.ui.yunxinfriend;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.SelectGiftAdapter;
import com.atman.wysq.model.response.GiftListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.PromptDialog;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/8 11:21
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class SelectGiftFragment extends MyBaseFragment implements AdapterInterface {

    @Bind(R.id.select_gift_gv)
    GridView selectGiftGv;

    private int page = 0;
    private GiftListModel mGiftListModel = new GiftListModel();
    private List<GiftListModel.BodyEntity> data = new ArrayList<>();
    private SelectGiftAdapter mAdapter;

    private int cionNum;
    private int myPosition;
    private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selectgift, null);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        page = b.getInt("page");
        id = b.getString("id");
        mGiftListModel = (GiftListModel) b.getSerializable("data");
        for (int i=(page*6);i<mGiftListModel.getBody().size();i++) {
            data.add(mGiftListModel.getBody().get(i));
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        mAdapter = new SelectGiftAdapter(getActivity(), getmWidth(), data, this);
        selectGiftGv.setAdapter(mAdapter);
        selectGiftGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (((SelectGiftActivity)getActivity()).isFaceLl()) {
                    ((SelectGiftActivity)getActivity()).isInput(view);
                    if (cionNum >= mAdapter.getItem(position).getCharm()) {
                        showPayDialog(position);
                    } else {
                        showToast("金币余额不足");
                    }
                }
            }
        });
    }

    private void showPayDialog(final int po) {
        myPosition = po;
        String str = "确定花费"+mAdapter.getItem(po).getCharm()
                +"金币赠送对方礼物["+mAdapter.getItem(po).getName()+"]吗？";
        PromptDialog.Builder builder = new PromptDialog.Builder(getActivity());
        builder.setMessage(str);
        builder.setPositiveButton("不送了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("赠送", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OkHttpUtils.postString().url(Common.Url_Pay_GiftList+mAdapter.getItem(po).getGift_id()+"/"+id).content("")
                        .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_PAY_GIFTLIST).id(Common.NET_PAY_GIFTLIST).build()
                        .execute(new MyStringCallback(getActivity(), SelectGiftFragment.this, true));
            }
        });
        builder.show();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_PAY_GIFTLIST) {
            ((SelectGiftActivity)getActivity()).backResuilt(mAdapter.getItem(myPosition).getPic_url());
        }
    }

    @Override
    public void initIntentAndMemData() {
        super.initIntentAndMemData();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onResume() {
        super.onResume();
        cionNum = MyBaseApplication.getApplication().mGetUserIndexModel
                .getBody().getUserDetailBean().getUserExt().getGold_coin();
        if (mAdapter!=null) {
            mAdapter.setMyCion(cionNum);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_PAY_GIFTLIST);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
