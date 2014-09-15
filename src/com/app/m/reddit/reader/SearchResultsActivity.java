package com.app.m.reddit.reader;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.app.m.reddit.reader.constants.Constants;
import com.app.m.reddit.reader.fragments.MainFragment;

public class SearchResultsActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle arg0) {
    // TODO Auto-generated method stub
    super.onCreate(arg0);
    setContentView(R.layout.activity_search);
    handleIntent(getIntent());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    // TODO Auto-generated method stub
    super.onNewIntent(intent);
    setIntent(intent);
    handleIntent(intent);
  }

  private void handleIntent(Intent intent) {
    // TODO Auto-generated method stub
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      String query = intent.getStringExtra(SearchManager.QUERY);
      //use the query to search your data somehow
      Toast.makeText(getApplicationContext(), "Showing results for " + query, Toast.LENGTH_SHORT).show();
      String url = Constants.SEARCH_URL + query;
      Bundle bundle = new Bundle();
      bundle.putString("url", url);
      
      MainFragment mainFragment = new MainFragment();
      mainFragment.setArguments(bundle);
      
      FragmentManager fragmentManager = getSupportFragmentManager();
      fragmentManager
      .beginTransaction()
      .replace(R.id.search_container, mainFragment).commit();

    }
  }

}
