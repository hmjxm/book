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

import android.app.Activity;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class confirmOrderActivity extends Activity {
	private TextView subtitle;
	private TextView tv_allprice;
	private TextView tv_submit;
	private ListView listview4;
    private TextView tv_locate;
    private LinearLayout locationlayout;
    private TextView tv_lper;
    private TextView tv_ltel;
    private TextView tv_loc;
    private ImageView image_locate;
    private String RANDOMS="1234567890";
    private int lid;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmorder);
		subtitle=(TextView)findViewById(R.id.subtitle);
		tv_allprice=(TextView)findViewById(R.id.tv_allprice);
		tv_submit=(TextView)findViewById(R.id.tv_submit);
		listview4=(ListView)findViewById(R.id.listview4);
		tv_locate=(TextView)findViewById(R.id.tv_locate);
		locationlayout=(LinearLayout)findViewById(R.id.locationlayout);
		tv_lper=(TextView)findViewById(R.id.tv_lper);
		tv_ltel=(TextView)findViewById(R.id.tv_ltel);
		tv_loc=(TextView)findViewById(R.id.tv_loc);
		image_locate=(ImageView)findViewById(R.id.image_locate);
		Double totalPrice=0.0;
		Intent intent=getIntent(); 
		final List<HashMap<String,Object>> blist = (List<HashMap<String, Object>>)intent.getSerializableExtra("blist");
		System.out.println("blist为          ："+blist);
		final List<HashMap<String,Object>> mylist=new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<blist.size();i++){
			HashMap<String, Object> map=new HashMap<String, Object>();
			int bid=(Integer) blist.get(i).get("bid");
			String bname=(String) blist.get(i).get("bname");
			Double bprice=(Double) blist.get(i).get("bprice");
			totalPrice+=bprice;
			String bpic1=(String) blist.get(i).get("bpic1");
			byte[] bitmapArray;
			bitmapArray = Base64.decode(bpic1, Base64.DEFAULT);
			Bitmap bpic= BitmapFactory.decodeByteArray(bitmapArray,0,bitmapArray.length);
			map.put("bid", bid);
    	    map.put("bname", bname);
    	    map.put("bprice", bprice);
    	    map.put("bpic", bpic);
			mylist.add(map); 
		}
		tv_allprice.setText("￥"+totalPrice);
		
		confirmOrderAdapter confirmOrderadapter = new confirmOrderAdapter(confirmOrderActivity.this,mylist); //创建适配器  
		listview4.setAdapter(confirmOrderadapter);

		image_locate.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent=new Intent(confirmOrderActivity.this,selectLocationActivity.class);
        		startActivityForResult(intent,11);
        	}
        });
		
		tv_submit.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		final String uname=loginActivity.getUserName(confirmOrderActivity.this);
        		String location=tv_locate.getText().toString();
        		if(location.equals("选择收货地址")){
        			Toast.makeText(confirmOrderActivity.this, "请先选择收货地址~", Toast.LENGTH_LONG).show();
        		}
        		else{
        			System.out.println("我的天那");
        			final List<HashMap<String,Object>> salelist=new ArrayList<HashMap<String,Object>>();
        			final List<HashMap<String,Object>> nosalelist=new ArrayList<HashMap<String,Object>>();
        			for(int i=0;i<mylist.size();i++){
        				final HashMap<String, Object> map=new HashMap<String, Object>();
            			final int bid=(Integer) mylist.get(i).get("bid");
            			final String bid1=String.valueOf(bid);
            			Thread thread11=new Thread(new Runnable(){
                			public void run(){
                				try {
    								String bstate=querystateParse.doPost(bid1);
    								System.out.println(bstate);
    								if(bstate.equals("已卖出")){
    									System.out.println(bid+bstate);
    									map.put("bid", bid);
    									map.put("bstate", bstate);
    									salelist.add(map);
    									
    								}
    								else{
    									System.out.println(bid+bstate);
    									map.put("bid", bid);
    									map.put("bstate", bstate);
    									nosalelist.add(map);
    									
    								}
    							} catch (ClientProtocolException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							} catch (IOException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
                			}
            			});
            			thread11.start();
            			try {
							thread11.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            		}
        			System.out.println("salelist:"+salelist);
					System.out.println("nosalelist:"+nosalelist);
        	    
					if((salelist.toString()).equals("[]")){
        				System.out.println("okokok");
        				
        				//跳转到orderdetail页面
                		Date dt=new Date();
        				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        				final String ordertime = sdf.format(dt);
        				System.out.println(ordertime);
                		String lid1=ordertime.substring(5,7).trim();
            			String lid2=ordertime.substring(8,10).trim();
            			String lid3=ordertime.substring(11,13).trim();
            			String lid4=ordertime.substring(14,16).trim();
            			StringBuffer sbf=new StringBuffer();
            			for(int i=0;i<2;i++){
            				int random=(int)(Math.random()*RANDOMS.length());
            				sbf.append(RANDOMS.charAt(random));
            			}
            			String lid5=sbf.toString();
            			final String orderid=lid5+lid1+lid2+lid3+lid4;
            			System.out.println(orderid);
            			final String lper=tv_lper.getText().toString();
            			final String ltel1=tv_ltel.getText().toString();
            			final long ltel=Long.parseLong(ltel1);
            			final String loc=tv_loc.getText().toString();
            			Intent intent=new Intent(confirmOrderActivity.this,orderDetailActivity.class);  
                        intent.putExtra("blist", (Serializable)blist);  
                        intent.putExtra("lper", lper);
                        intent.putExtra("ltel", ltel);
                        intent.putExtra("loc", loc);
                        intent.putExtra("orderid", orderid);
                        intent.putExtra("ordertime", ordertime);
                        startActivityForResult(intent,13);      
                        Toast.makeText(confirmOrderActivity.this, "您已成功提交订单~", Toast.LENGTH_LONG).show();
                        
        				
        				
        			    for(int i=0;i<mylist.size();i++){
            			final int bid=(Integer) mylist.get(i).get("bid");
            			new Thread(new Runnable(){
                			public void run(){
                				try {
    								String result=deleteCartParse.doPost(bid,uname);
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
        			
                    
                    //将信息插入book_orders表中
                    Thread thread1=new Thread(new Runnable(){
            			public void run(){
            				try {
    							String result=ordersParse.doPost(orderid,uname,ordertime,lper,ltel1,loc);
    							System.out.println(result);
            				}catch (IOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
            			}
            		});
            		thread1.start();
            		try {
    					thread1.sleep(500);
    				} catch (InterruptedException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
        		
            		Thread thread2=new Thread(new Runnable(){
            			public void run(){
            				try {
            					for(int i=0;i<blist.size();i++){
            						int bid=(Integer) blist.get(i).get("bid");
            						String bname=(String) blist.get(i).get("bname");
            						Double bprice=(Double) blist.get(i).get("bprice");
            						String bpic=(String) blist.get(i).get("bpic1");
            						String result=orderdetailParse.doPost(bid,orderid,bname,bprice,bpic);
        							System.out.println(result);
            					}
    							
            				}catch (IOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
            			}
            		});
            		thread2.start();
            		
            		Thread thread3=new Thread(new Runnable(){
            			public void run(){
            				try {
            					for(int i=0;i<blist.size();i++){
            						int bid=(Integer) blist.get(i).get("bid");
            						String bid1=String.valueOf(bid);
            						String result=updateBookParse.doPost(bid1);
        							System.out.println(result);
            					}
    							
            				}catch (IOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
            			}
            		});
            		thread3.start();
            		
            		Thread thread4=new Thread(new Runnable(){
            			public void run(){
            				try {
            					for(int i=0;i<blist.size();i++){
            						int bid=(Integer) blist.get(i).get("bid");
            						System.out.println("youyouiyu"+bid);
            						String bid1=String.valueOf(bid);
            						String result=insertsaleinfoParse.doPost(orderid,bid1,ordertime,lper,ltel1,loc);
        							System.out.println(result);
            					}
    							
            				}catch (IOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
            			}
            		});
            		thread4.start();
            		
        			}
        			else{
        				StringBuffer sbf=new StringBuffer();
        				for(int i=0;i<mylist.size();i++){
        					int bid=(Integer) mylist.get(i).get("bid");
        					for(int j=0;j<salelist.size();j++){
        						int bid1=(Integer) salelist.get(j).get("bid");
        						if(bid1==bid){
        							String bname=(String) mylist.get(i).get("bname");
        							sbf.append(bname);
        							sbf.append("、");
        						}
        					}
        				}
        				sbf.deleteCharAt(sbf.length() - 1);  
        				String sb=sbf.toString();
        				Toast.makeText(confirmOrderActivity.this, sb+"这"+salelist.size()+"本书已经售完，请重新选购~", Toast.LENGTH_LONG).show();
        				Intent intent=new Intent(confirmOrderActivity.this,MainActivity.class);
        				setResult(12,intent);
        				finish();
        				for(int i=0;i<salelist.size();i++){
        					final int bid=(Integer) salelist.get(i).get("bid");
        					new Thread(new Runnable(){
                    			public void run(){
                    				try {
        								String result=deleteCartParse.doPost(bid,uname);
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
        			}
        		}//else
        	}//click
        });
		
		subtitle.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
	}
	
	@Override
	public  void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==11&&requestCode==11){
			System.out.println("nisncskjfgjgkhlj;");
			tv_locate.setVisibility(View.GONE);
			tv_locate.setText(null);
			locationlayout.setVisibility(View.VISIBLE);
			String lid1=data.getStringExtra("lid");
			System.out.println(lid1);
			lid=Integer.parseInt(lid1);
			tv_lper.setText(data.getStringExtra("lper"));
			System.out.println(data.getStringExtra("lper"));
			Long ltel=data.getLongExtra("ltel",00000000000);
			System.out.println(ltel);
			tv_ltel.setText(ltel.toString());
			System.out.println(ltel.toString());
			tv_loc.setText(data.getStringExtra("loc"));
			System.out.println(data.getStringExtra("loc"));
		}
		
		if(resultCode==13&&requestCode==13){
			Intent intent=new Intent(confirmOrderActivity.this,MainActivity.class);
			setResult(12,intent);
			finish();
			
		}
	}
	
	public class confirmOrderAdapter extends BaseAdapter
	{
		private class ListItemView
	    {                //自定义控件集合     
	            public ImageView imageview1;    
	            public TextView tv1;    
	            public TextView tv2;   
	    }   
		private Context context;                        //运行上下文  
	    private List<HashMap<String, Object>> listItems;    //商品信息集合  
	    private LayoutInflater listContainer;           //视图容器                
	    private ListItemView  listItemView ;
	    
	    public confirmOrderAdapter(Context context, List<HashMap<String, Object>> listitems) {  
	        this.context = context;           
	        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文  
	        this.listItems = listitems;  
	    }  

		public int getCount() {  
	        // TODO Auto-generated method stub  
	        return listItems.size();  
	    }  

	    public Object getItem(int position) {  
	        // TODO Auto-generated method stub  
	        return listItems.get(position);  
	    }  

	    public long getItemId(int position) {  
	        // TODO Auto-generated method stub  
	        return position;  
	    }  
	    private void refreshListView()
		{
			
				this.notifyDataSetChanged();
		}
	    
	    @Override
	    public View getView( int position, View convertView, ViewGroup parent) {  
	        // TODO Auto-generated method stub  
	        //自定义视图  
	        if (convertView == null) {  
	            listItemView = new ListItemView();   
	            //获取list_item布局文件的视图  
	            convertView = listContainer.inflate(R.layout.confirmorder_item, null);  
	            //获取控件对象  
	            listItemView.imageview1 = (ImageView)convertView.findViewById(R.id.imageview1);  
	            listItemView.tv1 = (TextView)convertView.findViewById(R.id.tv1);  
	            listItemView.tv2 = (TextView)convertView.findViewById(R.id.tv2);
	            //设置控件集到convertView  
	            convertView.setTag(listItemView);  
	        }else {  
	            listItemView = (ListItemView)convertView.getTag();  
	        }  
            System.out.println("我我我我我我我我我我我我"+listItems);
	        listItemView.imageview1.setImageBitmap((Bitmap)listItems.get(  
	                position).get("bpic"));  
	        listItemView.tv1.setText((String) listItems.get(position)  
	                .get("bname"));  
	        listItemView.tv2.setText("￥"+listItems.get(position)  
	                .get("bprice"));  
	        return convertView;  
	    }

	}//class shopcartAdapter
	
}//class activity