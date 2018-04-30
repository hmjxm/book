package com.example.testimageclient;

import java.io.BufferedReader;
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

public class insertCartParse {
	private static String url="http://10.169.162.122:8080/cbb+book/insertCartServlet";
	
	public static int doPost(String bid,String uname,String bname,String bprice,String bpic) throws ClientProtocolException, IOException
	{
		HttpClient hc=new DefaultHttpClient();
		HttpPost hp=new HttpPost(url);
		NameValuePair param1=new BasicNameValuePair("uname",uname);
		NameValuePair param2=new BasicNameValuePair("bname",bname);
		NameValuePair param3=new BasicNameValuePair("bprice",bprice);
		NameValuePair param4=new BasicNameValuePair("bpic",bpic);
		NameValuePair param5=new BasicNameValuePair("bid",bid);
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		String result="";
		int result1 = 000;
		params.add(param1);
		params.add(param2);
		params.add(param3);
		params.add(param4);
		params.add(param5);
		System.out.println(params);
		HttpEntity he;
		try {
			he=new UrlEncodedFormEntity(params,"GB2312");
			hp.setEntity(he);
			HttpResponse response=hc.execute(hp);
			System.out.println(response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
			{
				HttpEntity het=response.getEntity();
				InputStream is=het.getContent();
				BufferedReader br=new BufferedReader(new InputStreamReader(is));
				String readLine=null;
				while((readLine=br.readLine())!=null)
				{
					result=result+readLine;
					URLDecoder.decode(result,"GB2312");
				}
				result1=Integer.parseInt(result);
				br.close();
				is.close();
			}
			else{
				result1=000;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result1=000;
		}
		System.out.println(result1);
		return result1;
	}
}
