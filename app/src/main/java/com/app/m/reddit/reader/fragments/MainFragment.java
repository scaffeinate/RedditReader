package com.app.m.reddit.reader.fragments;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.m.reddit.reader.R;
import com.app.m.reddit.reader.WebViewActivity;
import com.app.m.reddit.reader.common.Children;
import com.app.m.reddit.reader.constants.Constants;
import com.app.m.reddit.reader.util.FeedListAdapter;
import com.app.m.reddit.reader.util.JSONParser;
import com.app.m.reddit.reader.util.NetworkUtil;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainFragment extends Fragment implements OnItemClickListener {
	private ListView feedList;
	private TextView textView_noResults;
	private ProgressBar progressBarLoading;
	private FeedListAdapter listAdapter;
	private NetworkUtil networkUtil;
	private JSONParser jsonParser;
	private List<Children> feedLinkedList;
	private ImageLoader imageLoader;
	private Button buttonLoadMore;

	private String url;

	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_tab,
				container, false);
		feedList = (ListView) rootView.findViewById(R.id.feedListView);
		textView_noResults = (TextView) rootView.findViewById(R.id.textView_noItems);
		progressBarLoading = (ProgressBar) rootView.findViewById(R.id.progressBar);

		url = getArguments().getString("url");

		feedList.setOnItemClickListener(this);

		buttonLoadMore = new Button(getActivity().getApplicationContext());
		buttonLoadMore.setBackgroundResource(R.drawable.button_background);
		buttonLoadMore.setText("Load More");
		networkUtil = new NetworkUtil(getActivity());
		jsonParser = new JSONParser();

		buttonLoadMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new LoadMoreTask().execute(url + "?count=25&after=" + jsonParser.getAfter());
			}
		});

		setUpImageLoader();

		if (networkUtil.isInternetWorking()) {
			new GetFeedTask().execute(url);
		} else {
			Toast.makeText(getActivity(), "No network connection available.", Toast.LENGTH_SHORT).show();
		}

		return rootView;
	}

	private void setUpImageLoader() {
		// TODO Auto-generated method stub
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext())
				.diskCacheExtraOptions(480, 800, null)
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.diskCacheSize(50 * 1024 * 1024)
				.diskCacheFileCount(100)
				.build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	private class GetFeedTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				feedLinkedList = jsonParser.parseJSON(params[0]);
				return "";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "Unable to retrieve data. URL may be invalid.";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (feedLinkedList != null && feedLinkedList.size() > 0) {
				listAdapter = new FeedListAdapter(getActivity(), feedLinkedList, imageLoader);
				feedList.setAdapter(listAdapter);
				feedList.addFooterView(buttonLoadMore);
				textView_noResults.setVisibility(View.GONE);
			} else {
				textView_noResults.setVisibility(View.VISIBLE);
				Toast.makeText(getActivity(), "No Results Found", Toast.LENGTH_SHORT).show();
			}
			progressBarLoading.setVisibility(View.INVISIBLE);
		}
	}

	private class LoadMoreTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				feedLinkedList.addAll(jsonParser.parseJSON(params[0]));
			} catch (Exception e) {
				Log.e(Constants.EXCEPTION, "Unable to retrieve data. URL may be invalid.");
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			listAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
			case R.id.feedListView:
				String webUrl = feedLinkedList.get(arg2).getData().getUrl();
				if (webUrl != null && !webUrl.equals("")) {
					Bundle bundle = new Bundle();
					bundle.putString(Constants.WEBURL, webUrl);
					Intent toWebView = new Intent(this.getActivity(), WebViewActivity.class);
					toWebView.putExtras(bundle);
					startActivity(toWebView);
				}
				break;
		}
	}
}
