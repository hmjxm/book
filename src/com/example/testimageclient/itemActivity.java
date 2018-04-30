package com.example.testimageclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.ClientProtocolException;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class itemActivity extends Activity {
	private TextView txtname;
	private TextView txtprice;
	private TextView txtintro;
	private ImageView imgview;
	private Button buy_btn;
	private TextView return_btn;
	String bpic1;
	Bitmap bpic;
	String bintro;
	String bname;
	Double bprice;
	int bid;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);
		imgview=(ImageView)findViewById(R.id.imgview);
		txtname=(TextView)findViewById(R.id.txtname);
		txtprice=(TextView)findViewById(R.id.txtprice);
		txtintro=(TextView)findViewById(R.id.txtintro);
		buy_btn=(Button)findViewById(R.id.buy_btn);
		return_btn=(TextView)findViewById(R.id.subtitle);
		Intent itemintent=getIntent(); 
		bpic1=itemintent.getExtras().getString("bpic1");
		byte[] bitmapArray1;
		bitmapArray1 = Base64.decode(bpic1, Base64.DEFAULT);
		bpic = BitmapFactory.decodeByteArray(bitmapArray1, 0,bitmapArray1.length);
        bname=itemintent.getExtras().getString("bname");
        bprice=itemintent.getExtras().getDouble("bprice");
        bintro=itemintent.getExtras().getString("bintro");
        bid=itemintent.getExtras().getInt("bid");
        final Handler myHandler=new Handler(){
			public void handleMessage(Message msg){
				String response=(String) msg.obj;
				if(response.equals("false")){
					buy_btn.setVisibility(View.GONE);
				}
				else{
					buy_btn.setVisibility(View.VISIBLE);
				}
				
			}
		};
		new Thread(new Runnable(){
			public void run(){
				String bprice1=String.valueOf(bprice);
				System.out.println(bprice1);
				String bid1=String.valueOf(bid);
				String uname=loginActivity.getUserName(itemActivity.this);
				String data;
				try {
					
					data = queryParse.doPost(bid1,uname);
					Message msg=new Message();
		            msg.obj=data;
			        myHandler.sendMessage(msg);
					
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}).start();
		imgview.setImageBitmap(bpic); 
		txtname.setText("名称："+bname);
		txtprice.setText("价格：￥"+bprice);
		txtintro.setText("简介："+bintro);	
		
		buy_btn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		final Handler myHandler=new Handler(){
        			public void handleMessage(Message msg){
        				int response=(Integer) msg.obj;
        				if(response==1){
        					Toast.makeText(itemActivity.this, "添加成功,在购物车等您~", Toast.LENGTH_LONG).show();
        				}
        				
						if(response==0){
							Toast.makeText(itemActivity.this, "此书已在您的购物车里哦~", Toast.LENGTH_LONG).show();
						}
        			}
        		};
        		new Thread(new Runnable(){
        			public void run(){
        				String bprice1=String.valueOf(bprice);
        				System.out.println(bprice1);
        				String bid1=String.valueOf(bid);
        				String uname=loginActivity.getUserName(itemActivity.this);
        				int data;
						try {
							if(uname.equals("点击登录")){
								System.out.println("请先进行登录！");
								Looper.prepare();
								Toast.makeText(itemActivity.this, "请先进行登录！", Toast.LENGTH_LONG).show();	
								Looper.loop();
							}
							else{
								data = insertCartParse.doPost(bid1,uname, bname, bprice1, bpic1);
								Message msg=new Message();
		    		            msg.obj=data;
		    			        myHandler.sendMessage(msg);
							}
							
						} catch (ClientProtocolException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
        			}
        		}).start();
	    }
        });
		
		return_btn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
	}
}