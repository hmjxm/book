package com.example.testimageclient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getMyOrderParse1 {
	private static String url="http://10.169.162.122:8080/cbb+book/getMyOrderServlet1";
	
	public static List<book> doPost(String orderid) throws ClientProtocolException, IOException, JSONException
	{
		HttpClient hc=new DefaultHttpClient();
		HttpPost hp=new HttpPost(url);
		NameValuePair param1=new BasicNameValuePair("orderid",orderid);
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(param1);
		System.out.println(params);
		HttpEntity he;
		List<book> mlists = new ArrayList<book>();  
		try {
			he=new UrlEncodedFormEntity(params,"GB2312");
			hp.setEntity(he);
			HttpResponse response=hc.execute(hp);
			System.out.println(response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
			{
				HttpEntity het=response.getEntity();
				InputStream is=het.getContent();
				int len=0;
				byte[] data = new byte[1024*32];  
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				while ((len = is.read(data)) != -1) {  
		            outStream.write(data, 0, len);  
		        }  
		        is.close();  
		        data=outStream.toByteArray();  
		        JSONArray array = new JSONArray(new String(data)); 
		        for (int i = 0; i < array.length(); i++) {  
		        	JSONObject item = array.getJSONObject(i); 
		        	int bid = item.getInt("bid");
		        	String bname = item.getString("bname");
		        	Double bprice=item.getDouble("bprice");
		        	String bpic = item.getString("bpic");
		            mlists.add(new book(bid,bname,bprice,bpic));   
		        }
				
		        System.out.println("hhhhjhh"+mlists);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mlists;
	}
}
