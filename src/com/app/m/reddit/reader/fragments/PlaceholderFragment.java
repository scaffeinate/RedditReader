package com.app.m.reddit.reader.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.m.reddit.reader.FrontActivity;
import com.app.m.reddit.reader.R;
import com.app.m.reddit.reader.constants.Constants;
import com.app.m.reddit.reader.util.FeedListAdapter;
import com.app.m.reddit.reader.util.NetworkUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
	
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private ListView feedList;
	private ProgressBar progressBarLoading;
	private FeedListAdapter listAdapter;
	private NetworkUtil networkUtil;
	
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlaceholderFragment newInstance(int sectionNumber) {
		PlaceholderFragment fragment = new PlaceholderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public PlaceholderFragment() {
		networkUtil = new NetworkUtil(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_front,
				container, false);
		feedList = (ListView) rootView.findViewById(R.id.feedListView);
		progressBarLoading = (ProgressBar) rootView.findViewById(R.id.progressBar);
		initialise();
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((FrontActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}
	
	private void initialise(){
		if(networkUtil.isInternetWorking()){
			String frontPageHot = Constants.BASE_URL + Constants.HOT;
			new GetFeedTask().execute(frontPageHot);
		} else {
			Toast.makeText(getActivity(), "No Network Access!", Toast.LENGTH_SHORT).show();
		}
	}
	
	private class GetFeedTask extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}
}
