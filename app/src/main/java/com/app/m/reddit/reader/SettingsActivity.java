package com.app.m.reddit.reader;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class SettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.activity_settings);
		
		ActionBar actionBar = getActionBar();
    actionBar.setDisplayShowHomeEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle("Settings");
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
  public void onBackPressed() {
    // TODO Auto-generated method stub
    super.onBackPressed();
    Intent toHome = new Intent(this, FrontActivity.class);
    startActivity(toHome);
    this.finish();
  }

  @Override
  protected void onPause() {
    // TODO Auto-generated method stub
    super.onPause();
    this.finish();
  }
  
  

}
