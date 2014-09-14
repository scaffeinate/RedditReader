package com.app.m.reddit.reader.fragments;

import java.util.LinkedList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.m.reddit.reader.R;
import com.app.m.reddit.reader.common.Children;
import com.app.m.reddit.reader.util.FeedListAdapter;
import com.app.m.reddit.reader.util.JSONParser;
import com.app.m.reddit.reader.util.NetworkUtil;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainFragment extends Fragment {
	private ListView feedList;
	private ProgressBar progressBarLoading;
	private FeedListAdapter listAdapter;
	private NetworkUtil networkUtil;
	private JSONParser jsonParser;
	private LinkedList<Children> feedLinkedList;
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
		progressBarLoading = (ProgressBar) rootView.findViewById(R.id.progressBar);

		url = getArguments().getString("url");
		
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

		if(networkUtil.isInternetWorking()){
			new GetFeedTask().execute(url);
		} else {
			Toast.makeText(getActivity(), "No network connection available.", Toast.LENGTH_SHORT).show();
		}

		return rootView;
	}

	private void setUpImageLoader() {
		// TODO Auto-generated method stub
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext())
		.memoryCacheExtraOptions(480, 800) // default = device screen dimensions
		.diskCacheExtraOptions(480, 800, null)
		.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
		.memoryCacheSize(2 * 1024 * 1024)
		.diskCacheSize(50 * 1024 * 1024)
		.diskCacheFileCount(100)
		.writeDebugLogs()
		.build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	private class GetFeedTask extends AsyncTask<String, Integer, String>{

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
			listAdapter = new FeedListAdapter(getActivity(), feedLinkedList, imageLoader);
			feedList.setAdapter(listAdapter);
			feedList.addFooterView(buttonLoadMore);
			progressBarLoading.setVisibility(View.INVISIBLE);
		}
	}

	private class LoadMoreTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				for(Children child:jsonParser.parseJSON(params[0])){
					feedLinkedList.add(child);
				}
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
			listAdapter.notifyDataSetChanged();
		}

	}
}
