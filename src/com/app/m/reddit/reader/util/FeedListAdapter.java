package com.app.m.reddit.reader.util;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.m.reddit.reader.R;
import com.app.m.reddit.reader.common.Subreddit;

public class FeedListAdapter extends BaseAdapter {

	private LinkedList<Subreddit> feedLinkedList;
	private LayoutInflater inflater;
	
	public FeedListAdapter(Activity activity, LinkedList<Subreddit> feedLinkedList){
		this.feedLinkedList = feedLinkedList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return feedLinkedList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return feedLinkedList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public static class ViewHolder {
		public TextView textView_title;
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
			holder.textView_title = (TextView) vi
					.findViewById(R.id.textView_title);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		
		try {
			holder.textView_title.setText(feedLinkedList.get(arg0).getTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vi;
	}

}
