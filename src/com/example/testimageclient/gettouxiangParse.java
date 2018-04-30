package com.example.testimageclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class gettouxiangParse {
	private static final String urlPath = "http://10.169.162.122:8080/cbb+book/gettouxiangServlet";  
	public static String gettouxiang() throws Exception {  
		String touxiang = null; 
        byte[] data = readParse(urlPath);  
        JSONArray array = new JSONArray(new String(data));  
        for (int i = 0; i < array.length(); i++) {  
        	JSONObject item = array.getJSONObject(i);  
            touxiang = item.getString("touxiang");
            System.out.println(touxiang);
        }  
        return touxiang;  
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


