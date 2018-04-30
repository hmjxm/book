package com.example.testimageclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class cpnameParse1 {
	private static final String urlPath = "http://10.169.162.122:8080/cbb+book/searchbookServlet";  
	public static List<book> getcpname(String bkey) throws Exception {  
		HttpClient hc=new DefaultHttpClient();
		HttpPost hp=new HttpPost(urlPath);
		NameValuePair param1=new BasicNameValuePair("bkey",bkey);
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
			        String uname = item.getString("uname");  
			        String touxiang = item.getString("touxiang");
			        String bname = item.getString("bname");
			        Double bprice = item.getDouble("bprice");
			        String bintro=item.getString("bintro");
			        String bpic=item.getString("bpic");
			        String btime=item.getString("btime");
			        int bid=item.getInt("bid");
			        String bstate=item.getString("bstate");
			        mlists.add(new book(uname,touxiang,bname,bprice,bintro,bpic,btime,bid,bstate));   
			    }  
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mlists;
	}
        
    }  
