package com.choicepicture_library.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.choicepicture_library.R;
import com.choicepicture_library.model.SelectImageItem;
import com.choicepicture_library.tools.Bimp;
import com.choicepicture_library.tools.FileUtils;

import java.io.IOException;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/5/3 10:06
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GridAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int selectedPosition = -1;
    private boolean shape;
    private Context context;

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public GridAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void update() {
        loading();
    }

    public int getCount() {
        return (Bimp.drr.size() + 1);
    }

    public Object getItem(int arg0) {

        return null;
    }

    public long getItemId(int arg0) {

        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * ListView Item
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        final int coord = position;
        ViewHolder holder = null;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_published_grida,parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == Bimp.bmp.size()) {
            holder.image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_addpic_unfocused));
            if (position == Bimp.max) {
                holder.image.setVisibility(View.GONE);
            }
        } else if (position < Bimp.drr.size()) {
            if (Bimp.bmp.size()!=0 && Bimp.bmp.get(position)!=null) {
                holder.image.setImageBitmap(Bimp.bmp.get(position));
            } else {
                Bitmap bm = null;
                try {
                    bm = Bimp.revitionImageSize(Bimp.drr.get(position));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bimp.bmp.set(position,bm);
                holder.image.setImageBitmap(bm);
            }
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void loading() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (Bimp.max == Bimp.drr.size()) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        break;
                    } else {
                        try {
                            if (Bimp.drr.size()==0 || Bimp.num>=Bimp.drr.size()) {
                                return;
                            }
                            String path = Bimp.drr.get(Bimp.num);
                            System.out.println(path);
                            Bitmap bm = Bimp.revitionImageSize(path);
                            Bimp.bmp.add(bm);
                            String newStr = path.substring(
                                    path.lastIndexOf("/") + 1,
                                    path.lastIndexOf("."));
                            FileUtils.saveBitmap(bm, "" + newStr);
                            SelectImageItem item = new SelectImageItem();
                            item.setDrr_or(path);
                            item.setDrr_sle("" + newStr);
                            Bimp.drr_or.add(item);
                            Bimp.num += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
