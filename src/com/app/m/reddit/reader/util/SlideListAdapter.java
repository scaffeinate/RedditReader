package com.app.m.reddit.reader.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.m.reddit.reader.R;

public class SlideListAdapter extends BaseAdapter {

	private String[] list_items;
	private LayoutInflater inflater = null;
	private int position_selected = 0;
	
	public SlideListAdapter(Activity activity, String[] list_items){
		this.list_items = list_items;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_items.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list_items[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public static class ViewHolder {
		public TextView textview_item_name;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View vi = arg1;
		final ViewHolder holder;

		if (arg1 == null) {
			vi = inflater.inflate(R.layout.slide_list_item, null);
			holder = new ViewHolder();
			holder.textview_item_name = (TextView) vi
					.findViewById(R.id.textView_item_name);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		
		if (arg0 == position_selected)
	        vi.setBackgroundResource(R.drawable.list_item_background_selected);
		else
			vi.setBackgroundResource(R.drawable.list_item_background);
		
		try {
			holder.textview_item_name.setText(list_items[arg0]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vi;

	}

	public void setPositionSelected(int mCurrentSelectedPosition) {
		// TODO Auto-generated method stub
		position_selected = mCurrentSelectedPosition;
		
	}
}
