package com.app.m.reddit.reader.util;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.m.reddit.reader.R;
import com.app.m.reddit.reader.common.Children;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FeedListAdapter extends BaseAdapter {

	private LinkedList<Children> feedLinkedList;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public FeedListAdapter(Activity activity, LinkedList<Children> feedLinkedList, ImageLoader imageLoader){
		this.feedLinkedList = feedLinkedList;
		this.imageLoader = imageLoader;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.ic_default_thumbnail)
        .resetViewBeforeLoading(false)
        .delayBeforeLoading(500)
        .build();
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
		public ImageView imageView_thumbnail;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View vi = arg1;
		final ViewHolder holder;

		if (arg1 == null) {
			vi = inflater.inflate(R.layout.feed_list_item, null);
			holder = new ViewHolder();
			holder.textView_title = (TextView) vi
					.findViewById(R.id.textView_title);
			holder.imageView_thumbnail = (ImageView) vi.findViewById(R.id.imageView_thumbnail);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		holder.textView_title.setText(feedLinkedList.get(arg0).getData().getTitle());
		imageLoader.displayImage(feedLinkedList.get(arg0).getData().getThumbnail(), holder.imageView_thumbnail, options);

		return vi;
	}

}
