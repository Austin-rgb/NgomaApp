package com.example.ngomaapp;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {
    public static String encode(String[] keys,String[] values){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            try {
                if (i!=0) result.append("&");
            result.append(URLEncoder.encode(keys[i], "UTF-8")).append("=").append(URLEncoder.encode(values[i], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                Log.e("Utils.encode",e.getMessage());
            }
        }
        Log.i("Utils.encode","successfully encoded "+result);
        return result.toString();
    }
    public static String array(String[] strings,String separator){
        StringBuilder result = new StringBuilder();
        for (String s :
                strings) {
            result.append(s);
            result.append(separator);
        }
        return result.toString();
    }
}
