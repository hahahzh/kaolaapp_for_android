package com.winwinapp.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class HTTPBase {

	private static final int TIMEOUT_CONNECTION = 10000; 
	private static final int TIMEOUT_SOCKET = 60000;
	public static final int HTTP_TYPE_POST = 1;
	public static final int HTTP_TYPE_INQUIRE = 2;
	public static final int HTTP_TYPE_DOWNLOAD = 3;
	
	public static DefaultHttpClient getHttpClient(){
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_CONNECTION);
		HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_SOCKET);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		return httpClient;
	}
	
	public static String HTTPSend(String url,String content,int type)
	{
		String page = null;
		DefaultHttpClient httpClient = getHttpClient();
		try {
			HttpResponse response = null;
			StringEntity entity = null;
			HttpPost post = new HttpPost(url);
			entity = new StringEntity(content);
			post.setEntity(entity);
			response = httpClient.execute(post);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				page = EntityUtils.toString(response.getEntity());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			httpClient.getConnectionManager().shutdown();
		}
		
		return page;
	}
	
	public static String HTTPSendGet(String url)
	{
		String page = null;
		DefaultHttpClient httpClient = getHttpClient();
		try {
			HttpResponse response = null;
			HttpGet post = new HttpGet(url);
			response = httpClient.execute(post);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				page = EntityUtils.toString(response.getEntity());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			httpClient.getConnectionManager().shutdown();
		}
		
		return page;
	}
	
}
