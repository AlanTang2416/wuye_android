package com.atman.wysq.ui.personal;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.widget.telephonebook.CharacterParser;
import com.atman.wysq.widget.telephonebook.ContactBean;
import com.atman.wysq.widget.telephonebook.GroupMemberBean;
import com.atman.wysq.widget.telephonebook.PinyinComparator;
import com.atman.wysq.widget.telephonebook.SideBar;
import com.atman.wysq.widget.telephonebook.SortGroupMemberAdapter;
import com.base.baselibs.iimp.AdapterInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/12 10:46
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class AddressListInvitationActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.country_lvcountry)
    ListView countryLvcountry;
    @Bind(R.id.title_layout_no_friends)
    TextView titleLayoutNoFriends;
    @Bind(R.id.title_layout_catalog)
    TextView titleLayoutCatalog;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.dialog)
    TextView dialog;
    @Bind(R.id.sidrbar)
    SideBar sidrbar;

    private Context mContext = AddressListInvitationActivity.this;

    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<GroupMemberBean> SourceDateList;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private SortGroupMemberAdapter adapter;
    private Map<Integer, ContactBean> contactIdMap = null;
    private List<ContactBean> list;
    private List<GroupMemberBean> mSelectList = new ArrayList<>();
    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresslistinvitation);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("通讯录邀请");
        // 实例化
        asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyBaseApplication.getApplication().setFilterLock(false);
    }

    /**
     * 初始化数据库查询参数
     */
    private void initData() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
        // 查询的字段
        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY};
        // 按照sort_key升序查詢
        asyncQueryHandler.startQuery(0, null, uri, projection, null, null,
                "sort_key COLLATE LOCALIZED asc");

    }

    private void initViews() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sidrbar.setTextView(dialog);

        // 设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    countryLvcountry.setSelection(position);
                }

            }
        });

        countryLvcountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                sendSMS(((GroupMemberBean) adapter.getItem(position)).getMobile());
            }
        });

        SourceDateList = filledData(list);
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);

        adapter = new SortGroupMemberAdapter(mContext, SourceDateList, this);
        countryLvcountry.setAdapter(adapter);
        countryLvcountry.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int section = getSectionForPosition(firstVisibleItem);
                int nextSection = getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = getPositionForSection(+nextSection);
                if (firstVisibleItem != lastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
                            .getLayoutParams();
                    params.topMargin = 0;
                    titleLayout.setLayoutParams(params);
                    titleLayoutCatalog.setText(SourceDateList.get(
                            getPositionForSection(section)).getSortLetters());
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = titleLayout.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
                                .getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            titleLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                titleLayout.setLayoutParams(params);
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
    private List<GroupMemberBean> filledData(List<ContactBean> date) {
        List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();

        for (int i = 0; i < date.size(); i++) {
            GroupMemberBean sortModel = new GroupMemberBean();
            sortModel.setName(date.get(i).getDesplayName());
            sortModel.setMobile(date.get(i).getPhoneNum());
            sortModel.setSex(false);
            sortModel.setSelect(false);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getDesplayName());
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
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    /**
     * @author Administrator
     */
    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                contactIdMap = new HashMap<Integer, ContactBean>();
                list = new ArrayList<ContactBean>();
                cursor.moveToFirst(); // 游标移动到第一项
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(1);
                    String number = cursor.getString(2);
                    String sortKey = cursor.getString(3);
                    int contactId = cursor.getInt(4);
                    Long photoId = cursor.getLong(5);
                    String lookUpKey = cursor.getString(6);

                    if (contactIdMap.containsKey(contactId)) {
                        // 无操作
                    } else {
                        // 创建联系人对象
                        ContactBean contact = new ContactBean();
                        contact.setDesplayName(name);
                        contact.setPhoneNum(number);
                        contact.setSortKey(sortKey);
                        contact.setPhotoId(photoId);
                        contact.setLookUpKey(lookUpKey);
                        list.add(contact);

                        contactIdMap.put(contactId, contact);
                    }
                }
                if (list.size() > 0) {
                    initViews();
                }
            }

            super.onQueryComplete(token, cookie, cursor);
        }

    }

    private void sendSMS(String phoneNumbeer) {
        MyBaseApplication.getApplication().setFilterLock(true);
        Uri smsToUri = Uri.parse("smsto:"+phoneNumbeer);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", "发现一个挺好玩的APP，快来看看吧！"+MyBaseApplication.mDownLoad_URL);
        startActivity(intent);
    }
}
