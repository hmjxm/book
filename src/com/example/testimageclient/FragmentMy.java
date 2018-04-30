package com.example.testimageclient;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.cache.Resource;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMy extends Fragment{
	private TextView logintxt;
	private ImageView loginimg;
	private TextView myorder;
	private TextView myupload;
	private TextView mysale;
	private TextView mylocation;
	private TextView updatepwd;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		final View view=inflater.inflate(R.layout.my_index, null);
		logintxt = (TextView) view.findViewById(R.id.my_index_login_text); 
		logintxt.setText(loginActivity.getUserName(this.getContext()));
		System.out.println("存储为："+loginActivity.getUserName(this.getContext()));
        logintxt.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        	if(logintxt.getText().toString().equals("点击登录")){
        		Intent intent=new Intent(getActivity(),loginActivity.class);
           	    startActivityForResult(intent,1);
        	}
        	}
        });  
        
        mylocation = (TextView) view.findViewById(R.id.mylocation); 
        mylocation.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if(!logintxt.getText().toString().equals("点击登录")){
        			Intent intent=new Intent(getActivity(),myLocationActivity.class);
        			intent.putExtra("uname", logintxt.getText().toString());
            		startActivityForResult(intent,33);
            	}
        		else{
        			Toast.makeText(getActivity(), "请先进行登录~", Toast.LENGTH_LONG).show();
        		}
        	}
        });  
        
        
        updatepwd = (TextView) view.findViewById(R.id.updatepwd); 
        updatepwd.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if(!logintxt.getText().toString().equals("点击登录")){
        			Intent intent=new Intent(getActivity(),updatepwdActivity.class);
        			intent.putExtra("uname", logintxt.getText().toString());
            		startActivityForResult(intent,22);
            	}
        		else{
        			Toast.makeText(getActivity(), "请先进行登录~", Toast.LENGTH_LONG).show();
        		}
        	}
        });  
        
        TextView exit=(TextView)view.findViewById(R.id.exit);
        exit.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
    			build.setTitle("系统提示").setMessage("确定退出当前账户吗？");
    			build.setPositiveButton("确定",
    					new DialogInterface.OnClickListener() {
    						@Override
    						public void onClick(DialogInterface dialog, int which) {
    							getContext().getSharedPreferences("MT", Context.MODE_PRIVATE).edit().clear().commit(); 
    							getContext().getSharedPreferences("aa", Context.MODE_PRIVATE).edit().clear().commit();
    							logintxt.setText(loginActivity.getUserName(getContext()));
    							loginimg.setImageResource( R.drawable.icon);
    						}
    					});
    			build.setNegativeButton("取消",
    					new DialogInterface.OnClickListener() {

    						@Override
    						public void onClick(DialogInterface dialog, int which) {
    						}
    					}).show();
        	}
        });
        
        loginimg = (ImageView) view.findViewById(R.id.my_index_login_image);
        String touxiang=loginActivity.getTouxiang(this.getContext());
        byte[] bitmapArray;
		bitmapArray = Base64.decode(touxiang, Base64.DEFAULT);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
		loginimg.setImageBitmap(bitmap);
        loginimg.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if(!logintxt.getText().toString().equals("点击登录")){
        			Intent intent=new Intent(getActivity(),selectPicActivity.class);
            		startActivityForResult(intent,3);
            	}
        		else{
        			Toast.makeText(getActivity(), "请先进行登录~", Toast.LENGTH_LONG).show();
        		}
        	}
        });
        
        myorder = (TextView) view.findViewById(R.id.myorder);
        myorder.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if(!logintxt.getText().toString().equals("点击登录")){
        			Intent intent=new Intent(getActivity(),myorderActivity.class);
        			intent.putExtra("uname", logintxt.getText().toString());
            		startActivity(intent);
            	}
        		else{
        			Toast.makeText(getActivity(), "请先进行登录~", Toast.LENGTH_LONG).show();
        		}
        	}
        });
        
        myupload = (TextView) view.findViewById(R.id.myupload);
        myupload.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if(!logintxt.getText().toString().equals("点击登录")){
        			Intent intent=new Intent(getActivity(),myuploadActivity.class);
        			intent.putExtra("uname", logintxt.getText().toString());
            		startActivity(intent);
            	}
        		else{
        			Toast.makeText(getActivity(), "请先进行登录~", Toast.LENGTH_LONG).show();
        		}
        	}
        });
        
        mysale = (TextView) view.findViewById(R.id.mysale);
        mysale.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if(!logintxt.getText().toString().equals("点击登录")){
        			Intent intent=new Intent(getActivity(),mysaleActivity.class);
        			intent.putExtra("uname", logintxt.getText().toString());
            		startActivity(intent);
            	}
        		else{
        			Toast.makeText(getActivity(), "请先进行登录~", Toast.LENGTH_LONG).show();
        		}
        	}
        });
        
        
        return view;
	}
	
	@Override
	public  void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==1&&requestCode==1){
			logintxt.setText(data.getStringExtra("login_name"));
			String picture=data.getStringExtra("touxiang");
			byte[] bitmapArray;
			bitmapArray = Base64.decode(picture, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray,0,bitmapArray.length);
			loginimg.setImageBitmap(bitmap);
		}
		if(requestCode==3&&resultCode==3){
		    Bitmap bmp=selectPicActivity.photo;
			loginimg.setImageBitmap(bmp);
		}
		if(requestCode==22&&resultCode==22){
		}
		if(requestCode==33&&resultCode==33){
		}
	}
	
}
