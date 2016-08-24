package com.atman.wysq.widget.face;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.atman.wysq.R;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/2 09:52
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class FaceAdapter extends BaseAdapter {

    private List<ChatEmoji> data;

    private LayoutInflater inflater;

    private int size = 0;
    private ViewHolder viewHolder;

    public FaceAdapter(Context context, List<ChatEmoji> list) {
        this.inflater = LayoutInflater.from(context);
        this.data = list;
        this.size = list.size();
    }

    @Override
    public int getCount() {
        return this.size;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatEmoji emoji = data.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_face, null);
            viewHolder.iv_face = (ImageView) convertView.findViewById(R.id.item_iv_face);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (emoji.getId() == R.mipmap.face_del) {
            convertView.setBackgroundDrawable(null);
            viewHolder.iv_face.setImageResource(emoji.getId());
        } else if (TextUtils.isEmpty(emoji.getCharacter())) {
            convertView.setBackgroundDrawable(null);
            viewHolder.iv_face.setImageDrawable(null);
        } else {
            viewHolder.iv_face.setTag(emoji);
            viewHolder.iv_face.setImageResource(emoji.getId());
        }

        return convertView;
    }

    class ViewHolder {
        public ImageView iv_face;
    }
}
