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

public class setActivity extends Activity{
	private String uname,touxiang;
	private List<logininfo> mlist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		final EditText name=(EditText)findViewById(R.id.set_uname);
		final EditText upsd=(EditText)findViewById(R.id.set_upsd);
		final EditText upsd1=(EditText)findViewById(R.id.set_upsd1);
		Button  btn=(Button)findViewById(R.id.setbtn);
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
        				String touxiang = null;
        				if(response.equals("true")){
        					loginActivity.putUserName(setActivity.this,uname);
							for(int i=0;i<mlist.size();i++){
								if(uname.equals(mlist.get(i).getUname())){
									touxiang=mlist.get(i).getTouxiang();
									System.out.println("头像："+touxiang);
								}
							}
        					loginActivity.putTouxiang(setActivity.this,touxiang);
        					System.out.println(loginActivity.getUserName(setActivity.this));
        					Intent intent=new Intent(setActivity.this,loginActivity.class);
        					intent.putExtra("login_name",uname);
        					intent.putExtra("touxiang", touxiang);
        					setResult(9,intent);
        					finish();
        				}
        				else{
        					Toast.makeText(setActivity.this, "此用户名不存在~", Toast.LENGTH_LONG).show();	
        				}
        			}
        		};
        		new Thread(new Runnable(){
        			public void run(){
        				uname=name.getText().toString();
        				String pwd=upsd.getText().toString();
        				String pwd1=upsd1.getText().toString();
    					String data=null;
    					try {
    					if(!pwd.equals(pwd1)){
							Looper.prepare();
							Toast.makeText(setActivity.this, "两次密码输入不一致~", Toast.LENGTH_LONG).show();
							Looper.loop();
						}
						else{
							data=setParse.doPost(uname,pwd);
						}
							System.out.println("data为："+data);
							Message msg=new Message();
	    		            msg.obj=data;
	    		            if(data.equals("true")){
	    		            	mlist=LoginToServer.getuser();
	    		            }
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
