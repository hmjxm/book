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

public class regActivity extends Activity{
	private String uname,touxiang;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg);
		final EditText name=(EditText)findViewById(R.id.reg_uname);
		final EditText upsd=(EditText)findViewById(R.id.reg_upsd);
		final EditText upsd1=(EditText)findViewById(R.id.reg_upsd1);
		Button  btn=(Button)findViewById(R.id.regbtn);
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
        					loginActivity.putUserName(regActivity.this,uname);
        					loginActivity.putTouxiang(regActivity.this,touxiang);
        					System.out.println(loginActivity.getUserName(regActivity.this));
        					Intent intent=new Intent(regActivity.this,loginActivity.class);
        					intent.putExtra("login_name",uname);
        					intent.putExtra("touxiang", touxiang);
        					setResult(8,intent);
        					finish();
        				}
        				else{
        					Toast.makeText(regActivity.this, "此用户名已存在~", Toast.LENGTH_LONG).show();	
        				}
        			}
        		};
        		new Thread(new Runnable(){
        			public void run(){
        				uname=name.getText().toString();
        				String pwd=upsd.getText().toString();
        				String pwd1=upsd1.getText().toString();
        				System.out.println(uname);
        				System.out.println(pwd);
        				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
    					ByteArrayOutputStream stream = new ByteArrayOutputStream();
    					bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream); 
    					touxiang =Base64.encodeToString(stream.toByteArray(),Base64.DEFAULT);
    					String data = null;
    					try {
    						if(!pwd.equals(pwd1)){
    							Looper.prepare();
    							Toast.makeText(regActivity.this, "两次密码输入不一致~", Toast.LENGTH_LONG).show();
    							Looper.loop();
    						}
    						else{
    							data=insertParse.doPost(uname,pwd,touxiang);
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
						}
        			}
        		}).start();
        	}
        	
        });
		
	}
}
