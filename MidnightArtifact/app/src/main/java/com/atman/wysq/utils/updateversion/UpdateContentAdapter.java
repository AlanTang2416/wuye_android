package com.atman.wysq.utils.updateversion;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atman.wysq.R;

import java.util.ArrayList;
import java.util.List;

/**
 *@author:郁俊耀(Allen)
 *@data:2015-10-21 上午11:08:56
 */
public class UpdateContentAdapter extends BaseAdapter {
	
	private List<String> updateList = new ArrayList<String>();
	private Activity mContext = null;
	
	public UpdateContentAdapter(Activity mActivity, List<String> updateList){
		this.mContext = mActivity;
		this.updateList = updateList;
	}
	
	@Override
	public int getCount() {
		return updateList.size();
	}

	@Override
	public Object getItem(int position) {
		return updateList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_update_version, null);
			viewHolder.contentTv = (TextView) convertView.findViewById(R.id.content);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.contentTv.setText(updateList.get(position));
		return convertView;
	}
	
	private class ViewHolder{
		TextView contentTv = null;
	}
	
}
