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
import org.json.JSONException;

import com.example.testimageclient.FragmentCart.shopcartAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class myorderActivity extends Activity{
	private TextView subtitle;
	private  List<HashMap<String, Object>> listitems;
	
	private List<book> myorderlist;
	private List<book> booklist;
	private ListView listview7;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myorder);
		subtitle=(TextView)findViewById(R.id.subtitle);
		listview7=(ListView)findViewById(R.id.listview7);
		subtitle.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
		
		Intent intent=getIntent(); 
		final String uname=intent.getExtras().getString("uname");
		System.out.println("uname为："+uname);
		
		final Handler myHandler=new Handler(){
			@SuppressWarnings("unchecked")
			public void handleMessage(Message msg){
				listitems=(List<HashMap<String, Object>>) msg.obj;
				System.out.println("123344432222121"+listitems);
				final parentAdapter parentadapter = new parentAdapter(myorderActivity.this,listitems); //创建适配器  
				listview7.setAdapter(parentadapter);
			    listview7.setOnItemClickListener(new OnItemClickListener(){   	                  	               
            		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            		{
            			switch (parent.getId())
            			{
            			case R.id.listview7:
            				expressitemClick(position);//position 代表你点的哪一个
            				break;
                         }
                     }
                public void expressitemClick(int postion){
                	System.out.println("qwertytrmhrgmkfg!");
                	Intent intent=new Intent(myorderActivity.this,orderDetailActivity.class);
                	Map<String, Object> map = (Map<String, Object>)parentadapter.getItem(postion);
                    intent.putExtra("blist", (Serializable)map.get("booklist2"));  
                    intent.putExtra("lper", (String)map.get("lper"));
                    long ltel= (Long) map.get("ltel");
                    intent.putExtra("ltel", ltel);
                    intent.putExtra("loc",(String)map.get("loc"));
                    intent.putExtra("orderid",(String)map.get("orderid"));
                    intent.putExtra("ordertime",(String)map.get("ordertime"));
                    startActivity(intent);
                }
			});
		}
		};
		new Thread(new Runnable(){
			public void run(){
				try {
					    List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
                        myorderlist=getMyOrderParse.doPost(uname);
                    	for(int j=0;j<myorderlist.size();j++)
    					{
                    		Double allprice=0.0;
                    		HashMap<String, Object> map = new HashMap<String, Object>(); 
    						System.out.println(myorderlist.get(j).getOrderid());
    						System.out.println(myorderlist.get(j).getOrdertime());
    						System.out.println(myorderlist.get(j).getLper());
    						System.out.println(myorderlist.get(j).getLtel());
    						System.out.println(myorderlist.get(j).getLoc());
    						
    						final String orderid=myorderlist.get(j).getOrderid();    						
							booklist=getMyOrderParse1.doPost(orderid);	
							List<HashMap<String, Object>> booklist2= new ArrayList<HashMap<String, Object>>();
		                	for(int i=0;i<booklist.size();i++){
		                		allprice+=booklist.get(i).getBprice();
		                		HashMap<String, Object> map1 = new HashMap<String, Object>();
		                		map1.put("bname", booklist.get(i).getBname());
		                		map1.put("bprice", booklist.get(i).getBprice());
		                		map1.put("bpic1", booklist.get(i).getBpic());
		                		booklist2.add(map1);
		                	}
		                	
    						String ordertime1=myorderlist.get(j).getOrdertime();
    						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    						Date date = sdf.parse(ordertime1);
    						String ordertime=sdf.format(date);
    						String lper=myorderlist.get(j).getLper();
    						long ltel=myorderlist.get(j).getLtel();
    						String loc=myorderlist.get(j).getLoc();
    						
                    	    map.put("orderid", orderid);
                    	    map.put("ordertime",ordertime);
                    	    map.put("lper", lper);
                    	    map.put("ltel", ltel);
                    	    map.put("loc", loc);
                    	    map.put("booklist", booklist);
                    	    map.put("booklist2", booklist2);
                    	    map.put("allprice", allprice);
    						data.add(map); 
    					}                      	
				        Message msg=new Message();
    		            msg.obj=data;
    			        myHandler.sendMessage(msg);
					}

				
				 catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}
		}).start();
		
		
	}//override
}//class activity
