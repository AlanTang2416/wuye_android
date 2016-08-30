package com.atman.wysq.ui.yunxinfriend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetFansModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.personal.AddressListInvitationActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.widget.telephonebook.CharacterParser;
import com.atman.wysq.widget.telephonebook.SideBar;
import com.atman.wysq.yunxin.adapter.SortGroupFriendsAdapter;
import com.atman.wysq.yunxin.utils.FriendsPinyinComparator;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/25 17:19
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MoFriendsActivity extends MyBaseActivity implements AdapterInterface{

    @Bind(R.id.mofriend_listview)
    ListView mofriendListview;
    @Bind(R.id.mofriend_no_friends)
    TextView mofriendNoFriends;
    @Bind(R.id.mofriend_title_layout_catalog)
    TextView mofriendTitleLayoutCatalog;
    @Bind(R.id.mofriend_title_layout)
    LinearLayout mofriendTitleLayout;
    @Bind(R.id.mofriend_dialog)
    TextView mofriendDialog;
    @Bind(R.id.mofriend_sidrbar)
    SideBar mofriendSidrbar;

    private Context mContext = MoFriendsActivity.this;
    private GetFansModel mGetFansModel;
    private View headView;
    private LinearLayout headViewRootLl;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<GetFansModel.BodyEntity> SourceDateList;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private FriendsPinyinComparator pinyinComparator;
    private SortGroupFriendsAdapter adapter;
    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mofriends);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("魔友");

        headView = LayoutInflater.from(mContext).inflate(R.layout.part_friends_head, null);
        headViewRootLl = (LinearLayout) headView.findViewById(R.id.part_friends_head_root_ll);
        headViewRootLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    showLogin();
                } else {
                    if (!UiHelper.isTabletDevice(mContext)) {
                        startActivity(new Intent(mContext, AddressListInvitationActivity.class));
                    } else {
                        showToast("你的设备不支持短信");
                    }
                }
            }
        });
        mofriendListview.addHeaderView(headView);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        OkHttpUtils.get().url(Common.Url_Get_Fans)
                .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                .tag(Common.NET_GET_FANS).id(Common.NET_GET_FANS).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_FANS) {
            mGetFansModel = mGson.fromJson(data, GetFansModel.class);
            if (mGetFansModel.getBody().size()>0) {
                mofriendTitleLayout.setVisibility(View.VISIBLE);
            } else {
                mofriendTitleLayout.setVisibility(View.GONE);
            }
            InitLieView();
        }
    }

    private void InitLieView() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new FriendsPinyinComparator();
        mofriendSidrbar.setTextView(mofriendDialog);

        // 设置右侧触摸监听
        mofriendSidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mofriendListview.setSelection(position);
                }

            }
        });

        SourceDateList = filledData(mGetFansModel.getBody());
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);

        adapter = new SortGroupFriendsAdapter(mContext, SourceDateList, this);
        mofriendListview.setAdapter(adapter);
        mofriendListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int section = getSectionForPosition(firstVisibleItem);
                int nextSection = getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = getPositionForSection(+nextSection);

                if (firstVisibleItem==0) {
                    mofriendTitleLayout.setVisibility(View.GONE);
                } else {
                    mofriendTitleLayout.setVisibility(View.VISIBLE);
                }

                if (firstVisibleItem != lastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mofriendTitleLayout
                            .getLayoutParams();
                    params.topMargin = 0;
                    mofriendTitleLayout.setLayoutParams(params);
                    if (SourceDateList.size()>0) {
                        mofriendTitleLayoutCatalog.setText(SourceDateList.get(
                                getPositionForSection(section)).getSortLetters());
                    } else {
                        mofriendTitleLayoutCatalog.setVisibility(View.GONE);
                    }
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = mofriendTitleLayout.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mofriendTitleLayout
                                .getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            mofriendTitleLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                mofriendTitleLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<GetFansModel.BodyEntity> filledData(List<GetFansModel.BodyEntity> date) {
        List<GetFansModel.BodyEntity> mSortList = new ArrayList<>();

        for (int i = 0; i < date.size(); i++) {
            GetFansModel.BodyEntity sortModel = new GetFansModel.BodyEntity();
            sortModel.setId(date.get(i).getId());
            sortModel.setUser_id(date.get(i).getUser_id());
            sortModel.setUserExt(date.get(i).getUserExt());
            sortModel.setSelect(false);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getUserExt().getNick_name());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        try {
            return SourceDateList.get(position).getSortLetters().charAt(0);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < SourceDateList.size(); i++) {
            String sortStr = SourceDateList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_FANS);
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(OtherPersonalActivity.buildIntent(mContext, adapter.getItem(position).getUserExt().getUser_id()));
    }
}
