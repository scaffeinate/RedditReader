package com.app.m.reddit.reader.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;

public class DateUtil {

  String[] date_parse;
  String temp;
  SimpleDateFormat dateFormat;
  java.util.Date created,today;
  Long duration;
  int second;
  int minute,hour,day;

  @SuppressLint("SimpleDateFormat")
  public String adjustTime(String created_at) {

    today = new java.util.Date();
    created=new java.util.Date();

    created.setTime(Long.parseLong(created_at.replaceAll("\\.0*$", "")) * 1000);
    duration = today.getTime() - created.getTime();

    second = 1000;
    minute = second * 60;
    hour = minute * 60;
    day = hour * 24;

    if (duration < second * 7) 
      return "now ";

    else if (duration <= minute) {
      int n = (int) Math.floor(duration / second);
      return n + "s ago";
    }

    else if (duration <= minute * 2) 
      return "1m ago";

    else if (duration <= hour) {
      int n = (int) Math.floor(duration / minute);
      return n + "m ago";
    }

    else if (duration <= hour * 2) 
      return "1hr ago";

    else if (duration < day) {
      int n = (int) Math.floor(duration / hour);
      return n + "hrs ago";
    }

    else if (duration > day && duration < day * 2) 
      return "1d ago";

    else if (duration > day * 2 && duration < day * 3) 
      return "2d ago";

    else 
    {
      SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
      date_parse=df.format(created).split(" ");
      return date_parse[0]+"-"+date_parse[1]+"-"+date_parse[2];
    }

  }

}
