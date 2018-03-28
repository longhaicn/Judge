package com.judge.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static Date getDate(){

        return new Date();


    }
  public static String getDateStr(){
      SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      return time.format(new Date());
  }
  public static String formatDateStr(Date date){

      SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      return time.format(date);

  }


}
