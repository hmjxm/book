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
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		final EditText name=(EditText)findViewById(R.id.login_uname);
		final EditText upsd=(EditText)findViewById(R.id.login_upsd);
		final EditText num=(EditText)findViewById(R.id.login_check_num);
		final Button  random=(Button)findViewById(R.id.login_check_random);
		final TextView forget=(TextView)findViewById(R.id.forget);
		forget.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        	Intent intent=new Intent(loginActivity.this,setActivity.class);
        	startActivityForResult(intent,9);	
	    }
        });
		
		Button  btn=(Button)findViewById(R.id.login_btn);
		TextView reg_btn=(TextView)findViewById(R.id.righttitle);
		TextView lefttitle=(TextView)findViewById(R.id.lefttitle);
		lefttitle.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
	    }
        });
		reg_btn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent=new Intent(loginActivity.this,regActivity.class);
        		startActivityForResult(intent,8);
        		
	    }
        });
		setRandomView(random);
		random.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setRandomView(random);	 
	    }
        });
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
        		final Handler myHandler=new Handler(){
        			public void handleMessage(Message msg){
        				List<HashMap<String, Object>> response=(List<HashMap<String, Object>>)msg.obj;
        				System.out.println(response);
        				String a=response.toString();
        				System.out.println(a);
        				if(a!="[]"&&num.getText().toString().equals(random.getText().toString())){
        					String touxiang=(String) response.get(0).get("touxiang");
        					putUserName(loginActivity.this,name.getText().toString());
        					putTouxiang(loginActivity.this,touxiang);
        					Intent intent=new Intent(loginActivity.this,MainActivity.class);
        					intent.putExtra("login_name", name.getText().toString());
        					intent.putExtra("touxiang", touxiang);
        					setResult(1,intent);
        					finish();
        				}
        				else if(a!="[]"&&!num.getText().toString().equals(random.getText().toString())){
        					Toast.makeText(loginActivity.this, "验证码输入不正确！", Toast.LENGTH_LONG).show();	
        				}
        				else{
        					Toast.makeText(loginActivity.this, "用户名和密码不匹配！", Toast.LENGTH_LONG).show();	
        				}
        			}
        		};
        		new Thread(new Runnable(){
        			public void run(){
        				LoginToServer login =new LoginToServer();
        				try {
							List<logininfo> mlist=login.getuser();
							List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
	                    	for(int j=0;j<mlist.size();j++)
	    					{
	                    		HashMap<String, Object> map = new HashMap<String, Object>(); 
	                    		String uname=mlist.get(j).getUname();
	                    		String pwd=mlist.get(j).getPwd();
	                    		String touxiang=mlist.get(j).getTouxiang();
	                    		if(name.getText().toString().equals(uname)&&upsd.getText().toString().equals(pwd)){
		    						map.put("uname", uname);
		                   		    map.put("pwd",pwd);  
			    	                map.put("touxiang", touxiang);
			    	                data.add(map); 
	                    		}
	    						
	    					}      
					        Message msg=new Message();
	    		            msg.obj=data;
	    			        myHandler.sendMessage(msg);
						}catch (IOException e) {
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
	
	public void setRandomView(TextView textView){
		String randomtext=getRandom(4);
		textView.setText(randomtext);
	}
	private String RANDOMS="1234567890qwertyuiopasdfghjklzxcvbnm";
	public String getRandom(int index){
		StringBuffer sbf=new StringBuffer();
		for(int i=0;i<index;i++){
			int random=(int)(Math.random()*RANDOMS.length());
			sbf.append(RANDOMS.charAt(random));
		}
		return sbf.toString();
	}
	public static void putUserName(Context context,String userName){
		Editor editor=context.getSharedPreferences("MT", Context.MODE_PRIVATE).edit();
        editor.putString("userName",userName);
		editor.commit();
	}
	
	public static String getUserName(Context context){
		return context.getSharedPreferences("MT", Context.MODE_PRIVATE).getString("userName","点击登录");
	}

	public static void putTouxiang(Context context,String touxiang){
		Editor editor=context.getSharedPreferences("aa", Context.MODE_PRIVATE).edit();
        editor.putString("touxiang",touxiang);
		editor.commit();
	}
	
	public static String getTouxiang(Context context){
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream); 
		String headPicture =Base64.encodeToString(stream.toByteArray(),Base64.DEFAULT);//加密转换成String
		return context.getSharedPreferences("aa", Context.MODE_PRIVATE).getString("touxiang", headPicture);
	}
	@Override
	public  void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==8&&requestCode==8){
			System.out.println("我们我们我们沃恩"+data.getStringExtra("login_name"));
			String picture=data.getStringExtra("touxiang");
			Intent intent=new Intent(loginActivity.this,MainActivity.class);
			intent.putExtra("login_name", data.getStringExtra("login_name"));
			intent.putExtra("touxiang", picture);
			setResult(1,intent);
			finish();
		}
		if(resultCode==9&&requestCode==9){
			System.out.println("我们我们我们沃恩"+data.getStringExtra("login_name"));
			String picture=data.getStringExtra("touxiang");
			Intent intent=new Intent(loginActivity.this,MainActivity.class);
			intent.putExtra("login_name", data.getStringExtra("login_name"));
			intent.putExtra("touxiang", picture);
			setResult(1,intent);
			finish();
		}
	}
}