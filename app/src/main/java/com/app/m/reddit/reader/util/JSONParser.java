package com.app.m.reddit.reader.util;

import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.app.m.reddit.reader.common.Children;
import com.app.m.reddit.reader.common.Data;
import com.app.m.reddit.reader.common.Subreddit;
import com.app.m.reddit.reader.constants.Constants;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONParser {

	private List<Children> resultList = new ArrayList<Children>();
	private String after;

	public List<Children> parseJSON(String url) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Subreddit subreddit = null;
		try {
			subreddit = mapper.readValue(new URL(url), Subreddit.class);
		} catch (JsonParseException e) {
			Log.e(Constants.EXCEPTION, e.getMessage());
		} catch (JsonMappingException e) {
			Log.e(Constants.EXCEPTION, e.getMessage());
		} catch (MalformedURLException e) {
			Log.e(Constants.EXCEPTION, e.getMessage());
		} catch (IOException e) {
			Log.e(Constants.EXCEPTION, e.getMessage());
		}

		if (subreddit != null && subreddit.getData() != null) {
			setAfter(subreddit.getData().getAfter());
			resultList = subreddit.getData().getChildren();
		}

		return resultList;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}
}
