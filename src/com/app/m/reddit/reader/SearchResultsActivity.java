package com.app.m.reddit.reader;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.m.reddit.reader.constants.Constants;
import com.app.m.reddit.reader.fragments.MainFragment;

public class SearchResultsActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle arg0) {
    // TODO Auto-generated method stub
    super.onCreate(arg0);
    setContentView(R.layout.activity_search);
    ActionBar actionBar = getActionBar();
    actionBar.setDisplayShowHomeEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayHomeAsUpEnabled(true);
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

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case android.R.id.home:
      Intent toHome = new Intent(this, FrontActivity.class);
      startActivity(toHome);
    }
    return true;
  }

  @Override
  protected void onPause() {
    // TODO Auto-generated method stub
    super.onPause();
    this.finish();
  }

  @Override
  public void onBackPressed() {
    // TODO Auto-generated method stub
    super.onBackPressed();
    Intent toHome = new Intent(this, FrontActivity.class);
    startActivity(toHome);
  }
  

}
