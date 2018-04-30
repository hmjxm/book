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

public class uploadParse {
	private static String url="http://10.169.162.122:8080/cbb+book/uploadServlet";
	public static String doPost(String uname,String bname,String btype,String bprice,String bintro,String bpic,String btime,String bstate) throws ClientProtocolException, IOException
	{
		HttpClient hc=new DefaultHttpClient();
		HttpPost hp=new HttpPost(url);
		NameValuePair param1=new BasicNameValuePair("uname",uname);
		NameValuePair param2=new BasicNameValuePair("bname",bname);
		NameValuePair param3=new BasicNameValuePair("bprice",bprice);
		NameValuePair param4=new BasicNameValuePair("bintro",bintro);
		NameValuePair param5=new BasicNameValuePair("bpic",bpic);
		NameValuePair param6=new BasicNameValuePair("btime",btime);
		NameValuePair param7=new BasicNameValuePair("bstate",bstate);
		NameValuePair param8=new BasicNameValuePair("btype",btype);
		System.out.println(param1);
		System.out.println(param2);
		System.out.println(param3);
		System.out.println(param4);
		System.out.println(param5);
		System.out.println(param6);
		System.out.println(param7);
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(param1);
		params.add(param2);
		params.add(param3);
		params.add(param4);
		params.add(param5);
		params.add(param6);
		params.add(param7);
		params.add(param8);
		HttpEntity he;
		String result="";
		try {
			he=new UrlEncodedFormEntity(params,"GB2312");
			hp.setEntity(he);
			HttpResponse response=hc.execute(hp);
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
				is.close();
			}
			else{
				result="error";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result="exception";
		}
		return result;
		
	}
}
