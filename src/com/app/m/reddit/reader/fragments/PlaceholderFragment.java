package com.app.m.reddit.reader.fragments;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.m.reddit.reader.FrontActivity;
import com.app.m.reddit.reader.R;
import com.app.m.reddit.reader.constants.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private ViewPager mViewPager;
	private SectionsPagerAdapter mSectionsPagerAdapter;

	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String SUBREDDIT = "subreddit";
	private static final String URL = "url";
	private String url;
	private String mSubreddit;
	private String[] categoriesList = new String[]{
		"hot", "new", "rising", "controversial", "top", "gilded"
	};

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlaceholderFragment newInstance(int sectionNumber, String subreddit) {
		PlaceholderFragment fragment = new PlaceholderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putString(SUBREDDIT, subreddit);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_front,
				container, false);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getChildFragmentManager());

		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((FrontActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
		mSubreddit = getArguments().getString(SUBREDDIT, "");
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new MainFragment();
			Bundle args = new Bundle();
			if(mSubreddit.equals("front") || mSubreddit.equals("")){
			  url = Constants.BASE_URL + "/" + categoriesList[position] +".json";
			} else {
			  url = Constants.BASE_URL + "/r/" + mSubreddit + "/" + categoriesList[position] +".json";
			}
      
			args.putInt(ARG_SECTION_NUMBER, position + 1);
			args.putString(URL, url);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return categoriesList.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			return categoriesList[position].toUpperCase(l);
		}
	}
}
