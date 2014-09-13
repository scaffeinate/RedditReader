package com.app.m.reddit.reader.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import com.app.m.reddit.reader.common.Children;
import com.app.m.reddit.reader.common.Data;
import com.app.m.reddit.reader.common.Subreddit;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONParser {
	
	private LinkedList<Children> resultLinkedList;
	
	public LinkedList<Children> parseJSON(String url){
		ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Subreddit subreddit = null;
		try {
			subreddit = mapper.readValue(new URL(url), Subreddit.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Data data = subreddit.getData();
		resultLinkedList = data.getChildren();
		
		return resultLinkedList;
	}
}
