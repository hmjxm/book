package com.example.testimageclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentOut extends Fragment{	
	private ImageView outimg;
	private Button outbtn;
	private Bitmap bmp=null;
	private String data;
	private String uname;
	private String bname=null;
	private String bprice=null;
	private String bintro=null;
	private String bpic;
	private String btime;
	private RadioButton radio1; 
	private RadioButton radio2; 
	private String btype;
	// Fragment管理对象
    private FragmentManager manager;
    private FragmentTransaction ft;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = getFragmentManager();
		final View view=inflater.inflate(R.layout.out_index, null);	
		final EditText et_bname=(EditText)view.findViewById(R.id.et_bname);
		final EditText et_bprice=(EditText)view.findViewById(R.id.et_bprice);
		final EditText et_bintro=(EditText)view.findViewById(R.id.et_bintro);
		radio1=(RadioButton)view.findViewById(R.id.radio1);
		radio2=(RadioButton)view.findViewById(R.id.radio2);
		outimg = (ImageView) view.findViewById(R.id.upload_image);
        outimg.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        			Intent intent=new Intent(getActivity(),selectBookActivity.class);
            		startActivityForResult(intent,9);
        	}
        });
        radio1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		et_bprice.setText(" ");
    			et_bprice.setEnabled(true);
        	}
        });
        radio2.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		et_bprice.setText("0");
    			et_bprice.setEnabled(false);
        	}
        });
        outbtn = (Button) view.findViewById(R.id.out_btn);
        outbtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) { 
        		System.out.println(radio1.isChecked());
        		if(radio1.isChecked()==true){
        			btype="转让书籍";
        		}
        		else if(radio2.isChecked()==true){
        			btype="赠送书籍";
        		}
        		else{
        			btype=null;
        		}
        		final Handler myHandler=new Handler(){
    				@SuppressWarnings("unchecked")
    				public void handleMessage(Message msg){
    					String response= (String) msg.obj;
    					System.out.println("结果："+response);
    					if(response.equals("yes")){
    						RadioButton outbutton=(RadioButton) getActivity().findViewById(R.id.main_out);
							outbutton.setChecked(false);
							RadioButton homebutton=(RadioButton) getActivity().findViewById(R.id.main_home);
							homebutton.setChecked(true);
    					}
    				}
        		};
        		Thread outThread=new Thread(new Runnable(){
        			@SuppressLint({ "NewApi", "ResourceAsColor" })
					public void run(){
        				try{
							if(bmp==null){
								bpic=null;
							}
							else{
								ByteArrayOutputStream stream = new ByteArrayOutputStream();
								bmp.compress(Bitmap.CompressFormat.JPEG, 75, stream); 
								bpic =Base64.encodeToString(stream.toByteArray(),Base64.DEFAULT);//加密转换成String
							}
							Date dt=new Date();
		    				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    				btime= sdf.format(dt);
		    				String bstate="未卖出";
							
		    				uname=loginActivity.getUserName(getContext());
							bname=et_bname.getText().toString();
							bintro=et_bintro.getText().toString();
							bprice=et_bprice.getText().toString();
							
							if(uname.equals("点击登录")){
								System.out.println("请先进行登录！");
								Looper.prepare();
								Toast.makeText(getActivity(), "请先进行登录~", Toast.LENGTH_LONG).show();	
								Looper.loop();
							}
							else if(btype==null){
								Looper.prepare();
								Toast.makeText(getActivity(), "请选择书籍类型~",Toast.LENGTH_LONG).show();	
								Looper.loop();
							}
							else if(bname.equals("")){
								Looper.prepare();
								Toast.makeText(getActivity(), "请输入书名~",Toast.LENGTH_LONG).show();	
								Looper.loop();
							}
							else if(bprice.equals(" ")){
								Looper.prepare();
								Toast.makeText(getActivity(), "请输入价格~",Toast.LENGTH_LONG).show();	
								Looper.loop();
							}
							
							else if(bpic==null){
								Looper.prepare();
								Toast.makeText(getActivity(), "请选择图片~",Toast.LENGTH_LONG).show();	
								Looper.loop();
							}
							else{
								data=uploadParse.doPost(uname, bname,btype, bprice,bintro, bpic,btime,bstate);	
							    Message msg=new Message();
					            msg.obj=data;
						        myHandler.sendMessage(msg);
							}
	    			        }
	    			        catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
        		}});
        		outThread.start();
        	}
        });
		return view;
	}
	
	@Override
	public  void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==9){
			System.out.println("1234567");
		    bmp=selectBookActivity.photo;
		    System.out.println(bmp);
			outimg.setImageBitmap(bmp);
		}
	}
}
