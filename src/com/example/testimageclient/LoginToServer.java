package com.example.testimageclient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class LoginToServer {
	private static String url="http://10.169.162.122:8080/cbb+book/loginServlet";
	public static List<logininfo> getuser() throws Exception {  
        List<logininfo> mlists = new ArrayList<logininfo>();  
        byte[] data = readParse(url);  
        JSONArray array = new JSONArray(new String(data));  
        for (int i = 0; i < array.length(); i++) {  
        	JSONObject item = array.getJSONObject(i);  
            String uname = item.getString("uname");  
            String pwd = item.getString("pwd");
            String touxiang = item.getString("touxiang");
            mlists.add(new logininfo(uname,pwd,touxiang));   
        }  
        return mlists;  
    }  

    public static byte[] readParse(String urlPath) throws Exception {  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] data = new byte[1024*32];  
        int len = 0;  
        URL url = new URL(urlPath);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        InputStream inStream = conn.getInputStream();  
        while ((len = inStream.read(data)) != -1) {  
            outStream.write(data, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
  
    }  
}
