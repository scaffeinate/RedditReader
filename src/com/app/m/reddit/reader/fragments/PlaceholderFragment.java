package com.app.m.reddit.reader.fragments;

import java.util.LinkedList;
import java.util.Locale;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.m.reddit.reader.FrontActivity;
import com.app.m.reddit.reader.R;
import com.app.m.reddit.reader.common.Children;
import com.app.m.reddit.reader.constants.Constants;
import com.app.m.reddit.reader.util.FeedListAdapter;
import com.app.m.reddit.reader.util.JSONParser;
import com.app.m.reddit.reader.util.NetworkUtil;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
	
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private ListView feedList;
	private ViewPager mViewPager;
	private ProgressBar progressBarLoading;
	private FeedListAdapter listAdapter;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private NetworkUtil networkUtil;
	private JSONParser jsonParser;
	private LinkedList<Children> feedLinkedList;
	private ImageLoader imageLoader;
	private Button buttonLoadMore;
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String CATEGORY = "category";
	private String url;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlaceholderFragment newInstance(int sectionNumber, String mCategory) {
		PlaceholderFragment fragment = new PlaceholderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putString(CATEGORY, mCategory);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_front,
				container, false);
		feedList = (ListView) rootView.findViewById(R.id.feedListView);
		progressBarLoading = (ProgressBar) rootView.findViewById(R.id.progressBar);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getChildFragmentManager());
		
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		buttonLoadMore = new Button(getActivity().getApplicationContext());
		buttonLoadMore.setBackgroundResource(R.drawable.button_background);
		buttonLoadMore.setText("Load More");
		buttonLoadMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				new LoadMoreTask().execute(url + "?count=25&after=" + jsonParser.getAfter());
			}
		});
		networkUtil = new NetworkUtil(getActivity());
		jsonParser = new JSONParser();
	
		setUpImageLoader();
		
		/*if(networkUtil.isInternetWorking()){
			new GetFeedTask().execute(url);
		} else {
			Toast.makeText(getActivity(), "No network connection available.", Toast.LENGTH_SHORT).show();
		}*/
		
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

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((FrontActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
		url = constructUrl(getArguments().getString(CATEGORY));
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
	
	private String constructUrl(String category){
		String url = "";
		switch (category) {
		case "HOT":
			url = Constants.BASE_URL + Constants.HOT;
			break;
		case "RISING":
			url = Constants.BASE_URL + Constants.RISING;
			break;
		case "NEW":
			url = Constants.BASE_URL + Constants.NEW;
			break;
		case "CONTROVERSIAL":
			url = Constants.BASE_URL + Constants.CONTROVERSIAL;
			break;
		case "TOP":
			url = Constants.BASE_URL + Constants.TOP;
			break;
		case "GILDED":
			url = Constants.BASE_URL + Constants.GILDED;
			break;
		case "PROMOTED":
			url = Constants.BASE_URL + Constants.PROMOTED;
			break;
		default:
			url = Constants.BASE_URL + Constants.HOT;
			break;
		}
		return url;
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new FeedFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}
	
	public static class FeedFragment extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";

		public FeedFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_tab,
					container, false);
			return rootView;
		}
	}
}
