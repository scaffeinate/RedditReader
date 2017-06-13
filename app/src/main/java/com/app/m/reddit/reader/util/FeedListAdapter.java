package com.app.m.reddit.reader.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import com.app.m.reddit.reader.common.Children.Data;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FeedListAdapter extends BaseAdapter {

	private List<Children> feedLinkedList;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private DateUtil dateUtil;

	public FeedListAdapter(Activity activity, List<Children> feedLinkedList, ImageLoader imageLoader) {
		this.feedLinkedList = feedLinkedList;
		this.imageLoader = imageLoader;
		dateUtil = new DateUtil();
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_default_thumbnail)
				.showImageForEmptyUri(R.drawable.ic_no_img_thumbnail)
				.showImageOnFail(R.drawable.ic_no_img_thumbnail)
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
		public TextView textView_title, textView_author,
				textView_created, textView_score, textView_comments;
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
			holder.textView_author = (TextView) vi.findViewById(R.id.textView_author);
			holder.textView_created = (TextView) vi.findViewById(R.id.textView_created);
			holder.textView_score = (TextView) vi.findViewById(R.id.textView_score);
			holder.textView_comments = (TextView) vi.findViewById(R.id.textView_comments);
			holder.imageView_thumbnail = (ImageView) vi.findViewById(R.id.imageView_thumbnail);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		Data data = feedLinkedList.get(arg0).getData();

		holder.textView_title.setText(data.getTitle() + " (" + data.getDomain() + ")");
		holder.textView_author.setText(data.getAuthor());
		holder.textView_created.setText(dateUtil.adjustTime(data.getCreated_utc()));
		holder.textView_score.setText(data.getScore() + " points");
		holder.textView_comments.setText(data.getNum_comments() + " comments");
		imageLoader.displayImage(feedLinkedList.get(arg0).getData().getThumbnail(), holder.imageView_thumbnail, options);

		return vi;
	}
}
