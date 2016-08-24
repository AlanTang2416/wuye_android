package com.atman.wysq.widget.telephonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.atman.wysq.R;
import com.base.baselibs.iimp.AdapterInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/5/26 16:07
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class SortGroupMemberAdapter extends BaseAdapter implements SectionIndexer {
    private List<GroupMemberBean> list = null;
    private Context mContext;
    private AdapterInterface onItemClick;
    private boolean isSelected = false;

    public SortGroupMemberAdapter(Context mContext, List<GroupMemberBean> list,AdapterInterface onItemClick) {
        this.mContext = mContext;
        this.list = list;
        this.onItemClick = onItemClick;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<GroupMemberBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final GroupMemberBean mContent = list.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_telephone_listview, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.itemTelephoneCatalogTv.setVisibility(View.VISIBLE);
            viewHolder.itemTelephoneCatalogTv.setText(mContent.getSortLetters());
        } else {
            viewHolder.itemTelephoneCatalogTv.setVisibility(View.GONE);
        }

        viewHolder.itemTelephoneNameTv.setText(this.list.get(position).getName());

        if (list.get(position).isSelect()) {
//            viewHolder.itemTelephoneSelectIv.setImageResource(R.mipmap.member_item_select);
        } else {
//            viewHolder.itemTelephoneSelectIv.setImageResource(R.mipmap.member_item_unselect);
        }

        viewHolder.itemTelephoneSelectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(v, position);
            }
        });

        return view;

    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        for (int i=0;i<list.size();i++) {
            list.get(i).setSelect(isSelected);
        }
        notifyDataSetChanged();
    }

    public List<GroupMemberBean> getSelectedList(){
        List<GroupMemberBean> listSelect = new ArrayList<>();
        for (int i=0;i<list.size();i++) {
            if (list.get(i).isSelect()) {
                listSelect.add(list.get(i));
            }
        }
        return listSelect;
    }

    public boolean setSelectById(int position) {
        if (list.get(position).isSelect()) {
            list.get(position).setSelect(false);
        } else {
            list.get(position).setSelect(true);
        }
        notifyDataSetChanged();
        return isSelectedAll();
    }

    public boolean isSelectedAll(){
        int num = 0;
        for (int i=0;i<list.size();i++) {
            if (list.get(i).isSelect()) {
                num += 1;
            }
        }
        return num==list.size();
    }

    static class ViewHolder {
        @Bind(R.id.item_telephone_catalog_tv)
        TextView itemTelephoneCatalogTv;
        @Bind(R.id.item_telephone_select_iv)
        ImageView itemTelephoneSelectIv;
        @Bind(R.id.item_telephone_name_tv)
        TextView itemTelephoneNameTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
