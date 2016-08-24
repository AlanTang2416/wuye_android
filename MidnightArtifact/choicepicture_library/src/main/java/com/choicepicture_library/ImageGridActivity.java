package com.choicepicture_library;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.choicepicture_library.adapters.ImageGridAdapter;
import com.choicepicture_library.handlers.AlbumHelper;
import com.choicepicture_library.model.ImageItem;
import com.choicepicture_library.model.SelectImageItem;
import com.choicepicture_library.tools.Bimp;
import com.choicepicture_library.tools.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ImageGridActivity extends Activity {
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	List<ImageItem> dataList;
	GridView gridView;
	TextView imagegrid_right_id;
	ImageGridAdapter adapter;
	AlbumHelper helper;
	Button bt;
	public static Bitmap bimap;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(ImageGridActivity.this, "最多选择"+Bimp.max+"张图片", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_grid);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		dataList = helper.getImagesList(false);
		bimap= BitmapFactory.decodeResource(getResources(),R.mipmap.icon_addpic_unfocused);


		initView();
		bt = (Button) findViewById(R.id.bt);
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();) {
					list.add(it.next());
				}
				for (int i = 0; i < list.size(); i++) {
					if (Bimp.drr.size() <= Bimp.max) {
						String path = list.get(i);
						Bimp.drr.add(path);
						Bitmap bm = null;
						try {
							bm = Bimp.revitionImageSize(path);
						} catch (IOException e) {
							e.printStackTrace();
						}
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
					}
				}
				Intent mIntent = new Intent();
				setResult(RESULT_OK,mIntent);
				finish();
			}

		});
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
			public void onListen(int count) {
				bt.setText("完成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				if (dataList.get(position).isSelected()) {
//					dataList.get(position).setSelected(false);
//				} else {
//					dataList.get(position).setSelected(true);
//				}
				adapter.notifyDataSetChanged();
			}

		});
		imagegrid_right_id = (TextView) findViewById(R.id.imagegrid_right_id);
		imagegrid_right_id.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
