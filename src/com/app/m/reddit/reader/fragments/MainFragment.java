package com.app.m.reddit.reader.fragments;

import java.util.LinkedList;

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
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainFragment extends Fragment {
	private ListView feedList;
	private ProgressBar progressBarLoading;
	private FeedListAdapter listAdapter;
	private NetworkUtil networkUtil;
	private JSONParser jsonParser;
	private LinkedList<Children> feedLinkedList;
	private ImageLoader imageLoader;
	private Button buttonLoadMore;

	private static final String CATEGORY = "category";
	private String url;
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_front,
				container, false);
		feedList = (ListView) rootView.findViewById(R.id.feedListView);
		progressBarLoading = (ProgressBar) rootView.findViewById(R.id.progressBar);

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
}
