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

public class mysaleActivity extends Activity{
	private TextView subtitle;
	private  List<HashMap<String, Object>> listitems;
	
	private List<book> myorderlist;
	private List<book> booklist;
	private ListView listview11;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mysale);
		subtitle=(TextView)findViewById(R.id.subtitle);
		listview11=(ListView)findViewById(R.id.listview11);
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
				final mySaleAdapter mysaleadapter = new mySaleAdapter(mysaleActivity.this,listitems); //创建适配器  
				listview11.setAdapter(mysaleadapter);
				listview11.setOnItemClickListener(new OnItemClickListener(){   	                  	               
            		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            		{
            			switch (parent.getId())
            			{
            			case R.id.listview11:
            				expressitemClick(position);//position 代表你点的哪一个
            				break;
                         }
                     }
                public void expressitemClick(int postion){
                	listview11.setSelected(true);
                	Intent intent=new Intent(mysaleActivity.this,orderDetailActivity1.class);
                	Map<String, Object> map = (Map<String, Object>)mysaleadapter.getItem(postion);
                	intent.putExtra("bname", (String)map.get("bname"));
                	intent.putExtra("bprice", (Double)map.get("bprice"));
                	intent.putExtra("bpic", (String)map.get("bpic"));
                    intent.putExtra("lper", (String)map.get("lper"));
                    long ltel= (Long) map.get("ltel");
                    intent.putExtra("ltel", ltel);
                    intent.putExtra("loc",(String)map.get("loc"));
                    intent.putExtra("orderid",(String)map.get("orderid"));
                    intent.putExtra("ordertime",(String)map.get("stime"));
                    startActivity(intent);
                }
			});
		}
		};
		new Thread(new Runnable(){
			public void run(){
				try {
					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
                    List<book> mysalelist=getMySaleParse.doPost(uname);
                    System.out.println("天哪"+mysalelist);
                    List<HashMap<String, Object>> booklist2= new ArrayList<HashMap<String, Object>>();
                	for(int j=0;j<mysalelist.size();j++)
					{
                		HashMap<String, Object> map = new HashMap<String, Object>(); 
                		int bid=mysalelist.get(j).getBid();
                		String bid1=String .valueOf(bid);
                		System.out.println("书的编号："+bid);
                		List<book> mysalelist1=getMySaleParse1.doPost(bid1);
                		System.out.println("具体信息"+mysalelist1);
                		String orderid=mysalelist1.get(0).getOrderid();
                		String stime1=mysalelist1.get(0).getStime();
                		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date1 = sdf1.parse(stime1);
						String stime=sdf1.format(date1);
						
                		String lper=mysalelist1.get(0).getLper();
                		long ltel=mysalelist1.get(0).getLtel();
                		String loc=mysalelist1.get(0).getLoc();
                		
                		String bname=mysalelist.get(j).getBname();
                		Double bprice=mysalelist.get(j).getBprice();
                		String bintro=mysalelist.get(j).getBintro();
                		String bpic=mysalelist.get(j).getBpic();
                		String btime1=mysalelist.get(j).getBtime();
                		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = sdf.parse(btime1);
						String btime=sdf.format(date);
                		String bstate=mysalelist.get(j).getBstate();
                		
                	    map.put("bid", bid);
                	    map.put("bname", bname);
                	    map.put("bprice", bprice);
                	    map.put("bintro", bintro);
                	    map.put("bpic", bpic);
                	    map.put("btime", btime);
                	    map.put("bstate", bstate);
                	    map.put("orderid", orderid);
                	    map.put("stime",stime);
                	    map.put("lper", lper);
                	    map.put("ltel", ltel);
                	    map.put("loc", loc);
                	    map.put("booklist2", booklist2);
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
