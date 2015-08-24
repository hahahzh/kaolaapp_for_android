package com.winwinapp.network;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

public class HTTPGet {

	private static final String BAIDU_GEO_URL = "http://api.map.baidu.com/geocoder?output=json&location=%s,%s&key=0BNeEgEHRwKxnVejDNB3emwi";
	public static String RequestCurrentCity(float lat,float lon){
		String city = null;
		String url = String.format(Locale.ENGLISH, BAIDU_GEO_URL,lat,lon);
		String back = HTTPBase.HTTPSendGet(url);
		if(!TextUtils.isEmpty(back)){
			city = JsonHandler.parseGeo(back);
		}
		return city;
	}
}
