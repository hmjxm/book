package com.example.testimageclient;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.example.testimageclient.FragmentCart.shopcartAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class myuploadActivity extends Activity{
	private TextView subtitle;
	private  List<HashMap<String, Object>> listitems;
	
	private List<book> myorderlist;
	private List<book> booklist;
	private ListView listview10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myupload);
		subtitle=(TextView)findViewById(R.id.subtitle);
		listview10=(ListView)findViewById(R.id.listview10);
		subtitle.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
		
		Intent intent=getIntent(); 
		final String uname=intent.getExtras().getString("uname");
		System.out.println("unameÎª£º"+uname);
		
		final Handler myHandler=new Handler(){
			@SuppressWarnings("unchecked")
			public void handleMessage(Message msg){
				listitems=(List<HashMap<String, Object>>) msg.obj;
				System.out.println("123344432222121"+listitems);
				myUploadAdapter myuploadadapter = new myUploadAdapter(myuploadActivity.this,listitems); //´´½¨ÊÊÅäÆ÷  
				listview10.setAdapter(myuploadadapter);
		}
		};
		new Thread(new Runnable(){
			public void run(){
				try {
					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
                    List<book> myuploadlist=getMyUploadParse.doPost(uname);
                	for(int j=0;j<myuploadlist.size();j++)
					{
                		HashMap<String, Object> map = new HashMap<String, Object>(); 
                		int bid=myuploadlist.get(j).getBid();
                		String bname=myuploadlist.get(j).getBname();
                		Double bprice=myuploadlist.get(j).getBprice();
                		String bintro=myuploadlist.get(j).getBintro();
                		String bpic=myuploadlist.get(j).getBpic();
                		String btime=myuploadlist.get(j).getBtime();
                		String bstate=myuploadlist.get(j).getBstate();
                		
                	    map.put("bid", bid);
                	    map.put("bname", bname);
                	    map.put("bprice", bprice);
                	    map.put("bintro", bintro);
                	    map.put("bpic", bpic);
                	    map.put("btime", btime);
                	    map.put("bstate", bstate);
						data.add(map); 
					}   
                	
			        Message msg=new Message();
		            msg.obj=data;
			        myHandler.sendMessage(msg);
				}

			
			 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
	}).start();
		
		
	}//override
}//class activity
