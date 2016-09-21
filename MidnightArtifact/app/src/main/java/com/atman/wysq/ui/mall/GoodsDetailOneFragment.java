package com.atman.wysq.ui.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atman.wysq.R;
import com.atman.wysq.adapter.GoodsDetailOneAdapter;
import com.atman.wysq.model.response.GoodsDetailsResponseModel;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.base.baselibs.util.LogUtils;

import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/20 10:43
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GoodsDetailOneFragment extends MyBaseFragment {

//    @Bind(R.id.goodsdetail_onefragment_lv)
//    MyListView goodsdetailOnefragmentLv;

    private GoodsDetailsResponseModel.BodyEntity mModel;
    private GoodsDetailOneAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goodsdetailone, null);
        ButterKnife.bind(this, view);

        Bundle b = getArguments();
        mModel = b.getParcelable("images");
        LogUtils.e(">>>>:" + mModel.getGoodsDetailImgs().size());

//        mAdapter = new GoodsDetailOneAdapter(getActivity(), mModel.getGoodsDetailImgs());
//        goodsdetailOnefragmentLv.setAdapter(mAdapter);
//
//        int totalHeight = 0;
//        LogUtils.e(">>>>mAdapter.getCount():" + mAdapter.getCount());
//        for (int i = 0; i < mAdapter.getCount(); i++) {
//            View listItem = mAdapter.getView(i, null, goodsdetailOnefragmentLv);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        LogUtils.e(">>>>totalHeight:" + totalHeight);
//        ViewGroup.LayoutParams params = goodsdetailOnefragmentLv.getLayoutParams();
//        params.height = totalHeight + (goodsdetailOnefragmentLv.getDividerHeight() * (mAdapter.getCount() - 1));
//        goodsdetailOnefragmentLv.setLayoutParams(params);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
