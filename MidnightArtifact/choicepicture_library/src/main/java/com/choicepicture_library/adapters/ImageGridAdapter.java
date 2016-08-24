package com.choicepicture_library.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.choicepicture_library.R;
import com.choicepicture_library.model.ImageItem;
import com.choicepicture_library.tools.Bimp;
import com.choicepicture_library.tools.BitmapCache;
import com.choicepicture_library.tools.FileUtils;

import java.util.LinkedHashMap;
import java.util.List;

public class ImageGridAdapter extends BaseAdapter {

	private TextCallback textcallback = null;
	final String TAG = getClass().getSimpleName();
	Activity act;
	List<ImageItem> dataList;
	public LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	BitmapCache cache;
	private Handler mHandler;
	private int selectTotal = 0;
	BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,
							  Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "callback, bmp not match");
				}
			} else {
				Log.e(TAG, "callback, bmp null");
			}
		}
	};

	public static interface TextCallback {
		public void onListen(int count);
	}

	public void setTextCallback(TextCallback listener) {
		textcallback = listener;
	}

	public ImageGridAdapter(Activity act, List<ImageItem> list, Handler mHandler) {
		this.act = act;
		dataList = list;
		cache = new BitmapCache();
		selectTotal = Bimp.drr.size();
		this.mHandler = mHandler;
		if (Bimp.drr.size()==0) {
			for (int j=0;j<dataList.size();j++) {
				dataList.get(j).isSelected = false;
			}
		} else {
			for (int j=0;j<dataList.size();j++) {
				dataList.get(j).isSelected = false;
				for(int i=0;i<Bimp.drr.size();i++){
					if(dataList.get(j).imagePath.equals(Bimp.drr.get(i))){
						dataList.get(j).isSelected = true;
					}
				}
			}
		}
	}

	@Override
	public int getCount() {
		int count = 0;
		if (dataList != null) {
			count = dataList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class Holder {
		private ImageView iv;
		private ImageView selected;
		private TextView text;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		
		if (textcallback != null)
			textcallback.onListen(selectTotal);
		
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(act, R.layout.item_image_grid, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.image);
			holder.selected = (ImageView) convertView.findViewById(R.id.isselected);
			holder.text = (TextView) convertView.findViewById(R.id.item_image_grid_text);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final ImageItem item = dataList.get(position);

		holder.iv.setTag(item.imagePath);
		cache.displayBmp(holder.iv, item.thumbnailPath, item.imagePath,callback);

		if (item.isSelected) {
			holder.selected.setImageResource(R.mipmap.icon_data_select);
			holder.text.setBackgroundResource(R.drawable.bgd_relatly_line);
		} else {
			holder.selected.setImageResource(-1);
			holder.text.setBackgroundColor(0x00000000);
		}

		holder.iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String path = dataList.get(position).imagePath;

				if (selectTotal < Bimp.max) {
					item.isSelected = !item.isSelected;
					if (item.isSelected) {//未选中
						holder.selected.setImageResource(R.mipmap.icon_data_select);
						holder.text.setBackgroundResource(R.drawable.bgd_relatly_line);
						selectTotal++;
						if (textcallback != null)
							textcallback.onListen(selectTotal);
						map.put(path, path);

					} else if (!item.isSelected) {//已选择
						holder.selected.setImageResource(-1);
						holder.text.setBackgroundColor(0x00000000);
						selectTotal--;
						if (textcallback != null)
							textcallback.onListen(selectTotal);
						map.remove(path);
						System.out.println("path:"+path);
						for(int i=0;i<Bimp.drr.size();i++){
							String newStr = path.substring(path.lastIndexOf("/") + 1,path.lastIndexOf("."));
							if(newStr.equals(Bimp.drr_or.get(i).getDrr_sle())){
								FileUtils.delFile(Bimp.drr_or.get(i).getDrr_or());
								Bimp.drr_or.remove(i);
								Bimp.bmp.remove(i);
								Bimp.drr.remove(i);
								Bimp.num--;
							}
						}
					}
				} else if (selectTotal >= Bimp.max) {
					if (item.isSelected == true) {
						item.isSelected = !item.isSelected;
						holder.selected.setImageResource(-1);

						selectTotal--;
						map.remove(path);
						for(int i=0;i<Bimp.drr.size();i++){
							String newStr = path.substring(path.lastIndexOf("/") + 1,path.lastIndexOf("."));
							if(newStr.equals(Bimp.drr_or.get(i).getDrr_sle())){
								FileUtils.delFile(Bimp.drr_or.get(i).getDrr_or());
								Bimp.drr_or.remove(i);
								Bimp.bmp.remove(i);
								Bimp.drr.remove(i);
								Bimp.num--;
							}
						}
					} else {
						Message message = Message.obtain(mHandler, 0);
						message.sendToTarget();
					}
				}
			}

		});

		return convertView;
	}
}
