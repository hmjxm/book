package com.example.testimageclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class updatepwdActivity extends Activity{
	private String uname,touxiang;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatepwd);
		final EditText name=(EditText)findViewById(R.id.set_uname);
		final EditText upsd=(EditText)findViewById(R.id.set_upsd);
		final EditText upsd1=(EditText)findViewById(R.id.set_upsd1);
		Button  btn=(Button)findViewById(R.id.setbtn);
		Intent intent=getIntent(); 
		final String uname=intent.getExtras().getString("uname");
		name.setText(uname);
		name.setEnabled(false);
		TextView subtitle =(TextView)findViewById(R.id.subtitle);
		subtitle.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
	    }
        });
		
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
        		final Handler myHandler=new Handler(){
        			public void handleMessage(Message msg){
        				String  response=(String)msg.obj;
        				System.out.println(response);
        				if(response.equals("true")){
        					Intent intent=new Intent(updatepwdActivity.this,MainActivity.class);
        					setResult(22,intent);
        					finish();
        				}
        			}
        		};
        		new Thread(new Runnable(){
        			public void run(){
        				String pwd=upsd.getText().toString();
        				String pwd1=upsd1.getText().toString();
    					String data=null;
    					try {
    					if(!pwd.equals(pwd1)){
							Looper.prepare();
							Toast.makeText(updatepwdActivity.this, "两次密码输入不一致~", Toast.LENGTH_LONG).show();
							Looper.loop();
						}
						else{
							data=updatepwdParse.doPost(uname,pwd);
						}
							System.out.println("data为："+data);
							Message msg=new Message();
	    		            msg.obj=data;
	    			        myHandler.sendMessage(msg);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			}
        		}).start();
        	}
        	
        });
		
	}
}
