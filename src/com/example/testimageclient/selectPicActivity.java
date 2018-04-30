package com.example.testimageclient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class selectPicActivity extends Activity {
	private Button btn_take_photo, btn_pick_photo, btn_cancel;
	private LinearLayout layout;
	public static Bitmap photo;
	public String headPicture;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.img);
		btn_take_photo = (Button) this.findViewById(R.id.btn_take_photo);
		btn_pick_photo = (Button) this.findViewById(R.id.btn_pick_photo);
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		Resources res=getResources(); 
		Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.icon);
		layout=(LinearLayout)findViewById(R.id.pop_layout);
		
		//添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
		layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", 
						Toast.LENGTH_SHORT).show();	
			}
		});

		
		
		//添加按钮监听
		btn_cancel.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish(); 
	    }
        });
		
		btn_pick_photo.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(Intent.ACTION_PICK, null);
    			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
    			startActivityForResult(intent, 4);
	    }
        });
		
		btn_take_photo.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    			intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));
    			startActivityForResult(intent, 6);
	    }
        });
	}

	private void startCrop(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");// 璋冪敤Android绯荤粺鑷甫鐨勪竴涓浘鐗囧壀瑁侀〉闈�,
		intent.setDataAndType(uri,"image/*");
		intent.putExtra("crop", "true");// 杩涜淇壀
		// aspectX aspectY 鏄楂樼殑姣斾緥
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 鏄鍓浘鐗囧楂�
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 5);
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==4){
			if (data == null) {
				return;
			}
			startCrop(data.getData());
		}
		
		if(requestCode==5){
			if (data == null) {
				return;
			}
			Bundle extras = data.getExtras();
			if (extras != null) {
				photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream); 
				Intent intent=new Intent(selectPicActivity.this,MainActivity.class);
				setResult(3,intent);
				
				headPicture =Base64.encodeToString(stream.toByteArray(),Base64.DEFAULT);//加密转换成String
				loginActivity.putTouxiang(this,headPicture);
				int n=headPicture.length();
				System.out.println(headPicture);
				System.out.println(n);
				final String uname=loginActivity.getUserName(this);
				System.out.println("用户名为："+uname);
				final Handler orderHandler=new Handler(){
        			public void handleMessage(Message msg){
        				String response=(String)msg.obj;
        				System.out.println(response);
        			}
        		};
        		Thread thread1=new Thread(new Runnable(){
        			public void run(){
        				try {
							String result=updateParse.doPost(uname,headPicture);
							Message msg=new Message();
	        				msg.obj=result;
	        				orderHandler.sendMessage(msg);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			}
        		});
        		thread1.start();
        		
				finish();
               }
	    }
		if(requestCode==6){
			File picture = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
			startCrop(Uri.fromFile(picture));
		}
	}
	
	//实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}	

}
