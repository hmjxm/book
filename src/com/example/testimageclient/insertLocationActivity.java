package com.example.testimageclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class insertLocationActivity extends Activity{
	private TextView lefttitle;
    private TextView righttitle;
    private EditText et_lper;
    private EditText et_ltel;
    private EditText et_loc;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insertlocation);
		lefttitle=(TextView)findViewById(R.id.lefttitle);
		righttitle=(TextView)findViewById(R.id.righttitle);
		et_lper=(EditText)findViewById(R.id.et_lper);
		et_ltel=(EditText)findViewById(R.id.et_ltel);
		et_loc=(EditText)findViewById(R.id.et_loc);
		
		righttitle.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		final Handler myHandler=new Handler(){
        			public void handleMessage(Message msg){
        				String response= (String) msg.obj;
        				System.out.println(response);
        				if(response.equals("true")){
        					Intent intent=new Intent(insertLocationActivity.this,selectLocationActivity.class);
        	        		setResult(15,intent);
        	        		finish();
        	        		
        				}
        				}
                };
        		new Thread(new Runnable(){
        			public void run(){
        				String lper=et_lper.getText().toString();
        			    String ltel=et_ltel.getText().toString();
        			    for(int i=0;i<ltel.length();i++){
        			    	if(!(ltel.charAt(i)>='0'&&ltel.charAt(i)<='9'&&ltel.length()==11)){
        			    		Looper.prepare();
        			    		Toast.makeText(insertLocationActivity.this, "联系电话需为11位数字~", Toast.LENGTH_LONG).show();		
								Looper.loop();
        			    		
        			    	}
        			    }
        			    String loc=et_loc.getText().toString();
        				String uname=loginActivity.getUserName(insertLocationActivity.this);
        				System.out.println("uname为："+uname);
        				String data;
        				try {
        						data=insertLocationParse.doPost(uname,lper,ltel,loc);
        						
        						System.out.println(data);
        				        Message msg=new Message();
        			            msg.obj=data;
        				        myHandler.sendMessage(msg);
        					} catch (ClientProtocolException e) {
        						// TODO Auto-generated catch block
        						e.printStackTrace();
        					} catch (IOException e) {
        						// TODO Auto-generated catch block
        						e.printStackTrace();
        					}
        				}
        	}).start();
        	}
        });
		
		

		lefttitle.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
		
	}
}
