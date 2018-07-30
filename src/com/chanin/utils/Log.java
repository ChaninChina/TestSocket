package com.chanin.utils;

public class Log {


    public static void d(String tag,String message){
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(System.currentTimeMillis()) + " " + tag+" : " +message);
    }
}
