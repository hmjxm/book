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
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentHome extends Fragment{	
	public static List<book> cp;
	private  List<HashMap<String, Object>> listitems;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view=inflater.inflate(R.layout.home_index, null);	
		final ListView mListView = (ListView) view.findViewById(R.id.listview2);  
		RadioGroup rg=(RadioGroup)view.findViewById(R.id.rg);
		final RadioButton radio3=(RadioButton)view.findViewById(R.id.radio3);
		final RadioButton radio4=(RadioButton)view.findViewById(R.id.radio4);
		final EditText et_bkey=(EditText)view.findViewById(R.id.et_bkey);
		Button search_btn=(Button)view.findViewById(R.id.search_btn);
		search_btn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		System.out.println("radio3:"+radio3.isChecked());
        		System.out.println("radio4:"+radio4.isChecked());
        		radio3.setChecked(false);
        		radio4.setChecked(false);
        		System.out.println("radio3:"+radio3.isChecked());
        		System.out.println("radio4:"+radio4.isChecked());
        		final Handler myHandler=new Handler(){
    				@SuppressWarnings("unchecked")
    				public void handleMessage(Message msg){
    					listitems=(List<HashMap<String, Object>>) msg.obj;
    					System.out.println(listitems);
    					final MyAdapter myadapter = new MyAdapter(view.getContext(),listitems); //创建适配器  
    				    mListView.setAdapter(myadapter); 
    				    mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
    	            		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	            		{
    	            			switch (parent.getId())
    	            			{
    	            			case R.id.listview2:
    	            				expressitemClick(position);//position 代表你点的哪一个
    	            				break;
    	                         }
    	                     }
    	                public void expressitemClick(int postion){
    	            	Intent itemintent = new Intent(getActivity(),itemActivity.class);
    	                //item内的数据保存在map中
    	                @SuppressWarnings("unchecked")
    					Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
    	                
    	                //需要的数据在intent中
    	                itemintent.putExtra("bid", (Integer)map.get("bid"));
    	                itemintent.putExtra("bname", (String)map.get("bname"));
    	                itemintent.putExtra("bprice", (Double)map.get("bprice"));
    	                System.out.println("111"+map.get("bprice"));
    	                itemintent.putExtra("bintro", (String)map.get("bintro"));
    	                itemintent.putExtra("bpic1",(String)map.get("bpic1") );
    	                //跳转页面
    	                startActivity(itemintent);  
    	            }});
    				}
    			};
    			new Thread(new Runnable(){
    				public void run(){
    					try {
    						    String bkey=et_bkey.getText().toString();
    						    List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
    	                        cp=cpnameParse1.getcpname(bkey);
    	                    	for(int j=0;j<cp.size();j++)
    	    					{
    	                    		HashMap<String, Object> map = new HashMap<String, Object>(); 
    	    						System.out.println(cp.get(j).getUname());
    	    						System.out.println(cp.get(j).getTouxiang());
    	    						System.out.println(cp.get(j).getBname());
    	    						System.out.println(cp.get(j).getBprice());
    	    						System.out.println(cp.get(j).getBintro());
    	    						System.out.println(cp.get(j).getBpic());
    	    						System.out.println(cp.get(j).getBid());
    	    						System.out.println(cp.get(j).getBstate());
    	    						System.out.println("wodetiannananananan"+cp.get(j).getBtime());
    	    						String uname=cp.get(j).getUname();
    	    						String touxiang1=cp.get(j).getTouxiang();
    	    						String bname=cp.get(j).getBname();
    	    						Double bprice=cp.get(j).getBprice();
    	    						String bintro=cp.get(j).getBintro();
    	    						String bpic1=cp.get(j).getBpic();
    	    						String btime1=cp.get(j).getBtime();
    	    	        			int bid=cp.get(j).getBid();
    	    	        			String bstate=cp.get(j).getBstate();
    	    	        			if(bid==0&&bname.equals("0")&&bprice==0&&bpic1.equals("0")){
    									for(int i=0;i<MyAdapter.listItems.size();i++){
    										MyAdapter.listItems.remove(i);
    									}
    	    	        			}
    	    	        			else{
    	    	        				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	    						Date date = sdf.parse(btime1);
        	    						String btime2=sdf.format(date);
        	    						String lid1=btime2.substring(5,7).trim();
        	    	        			String lid2=btime2.substring(8,10).trim();
        	    	        			String lid3=btime2.substring(11,13).trim();
        	    	        			String lid4=btime2.substring(14,16).trim();
        	    	        			String btime=lid1+"-"+lid2+" "+lid3+":"+lid4;
    	    	        				byte[] bitmapArray;
        	    						bitmapArray = Base64.decode(touxiang1, Base64.DEFAULT);
        	    						Bitmap touxiang = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
        	    						byte[] bitmapArray1;
        	    						bitmapArray1 = Base64.decode(bpic1, Base64.DEFAULT);
        	    						Bitmap bpic = BitmapFactory.decodeByteArray(bitmapArray1, 0,bitmapArray1.length);
	    	                    	    map.put("uname", uname);
	    	                    	    map.put("touxiang", touxiang);
	    	                    	    map.put("bname", bname);
	    	                    	    map.put("bprice", bprice);
	    	                    	    map.put("bintro", bintro);
	    	                    	    map.put("bpic", bpic);
	    	                    	    map.put("bpic1", bpic1);
	    	                    	    map.put("btime", btime);
	    	                    	    map.put("bid", bid);
	    	                    	    map.put("bstate", bstate);
	    	    						data.add(map); 
    	    	        			}
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
        	}
        });  
		
		
		if(radio3.isChecked()==true){
			System.out.println("首先啊啊啊啊啊啊啊");
			final String btype=radio3.getText().toString();
			final Handler myHandler=new Handler(){
				@SuppressWarnings("unchecked")
				public void handleMessage(Message msg){
					listitems=(List<HashMap<String, Object>>) msg.obj;
					System.out.println(listitems);
					final MyAdapter myadapter = new MyAdapter(view.getContext(),listitems); //创建适配器  
				    mListView.setAdapter(myadapter); 
				    mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
	            		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	            		{
	            			switch (parent.getId())
	            			{
	            			case R.id.listview2:
	            				expressitemClick(position);//position 代表你点的哪一个
	            				break;
	                         }
	                     }
	                public void expressitemClick(int postion){
	            	Intent itemintent = new Intent(getActivity(),itemActivity.class);
	                //item内的数据保存在map中
	                @SuppressWarnings("unchecked")
					Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
	                
	                //需要的数据在intent中
	                itemintent.putExtra("bid", (Integer)map.get("bid"));
	                itemintent.putExtra("bname", (String)map.get("bname"));
	                itemintent.putExtra("bprice", (Double)map.get("bprice"));
	                System.out.println("111"+map.get("bprice"));
	                itemintent.putExtra("bintro", (String)map.get("bintro"));
	                itemintent.putExtra("bpic1",(String)map.get("bpic1") );
	                //跳转页面
	                startActivity(itemintent);  
	            }});
				    
				}
			};
			new Thread(new Runnable(){
				public void run(){
					try {
						    List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
	                        cp=cpnameParse.getcpname(btype);
	                    	for(int j=0;j<cp.size();j++)
	    					{
	                    		HashMap<String, Object> map = new HashMap<String, Object>(); 
	    						System.out.println(cp.get(j).getUname());
	    						System.out.println(cp.get(j).getTouxiang());
	    						System.out.println(cp.get(j).getBname());
	    						System.out.println(cp.get(j).getBprice());
	    						System.out.println(cp.get(j).getBintro());
	    						System.out.println(cp.get(j).getBpic());
	    						System.out.println(cp.get(j).getBid());
	    						System.out.println(cp.get(j).getBstate());
	    						System.out.println("wodetiannananananan"+cp.get(j).getBtime());
	    						String uname=cp.get(j).getUname();
	    						String touxiang1=cp.get(j).getTouxiang();
	    						byte[] bitmapArray;
	    						bitmapArray = Base64.decode(touxiang1, Base64.DEFAULT);
	    						Bitmap touxiang = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
	    						String bname=cp.get(j).getBname();
	    						Double bprice=cp.get(j).getBprice();
	    						String bintro=cp.get(j).getBintro();
	    						String bpic1=cp.get(j).getBpic();
	    						byte[] bitmapArray1;
	    						bitmapArray1 = Base64.decode(bpic1, Base64.DEFAULT);
	    						Bitmap bpic = BitmapFactory.decodeByteArray(bitmapArray1, 0,bitmapArray1.length);
	    						String btime1=cp.get(j).getBtime();
	    						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    						Date date = sdf.parse(btime1);
	    						String btime2=sdf.format(date);
	    						String lid1=btime2.substring(5,7).trim();
	    	        			String lid2=btime2.substring(8,10).trim();
	    	        			String lid3=btime2.substring(11,13).trim();
	    	        			String lid4=btime2.substring(14,16).trim();
	    	        			String btime=lid1+"-"+lid2+" "+lid3+":"+lid4;
	    	        			int bid=cp.get(j).getBid();
	    	        			String bstate=cp.get(j).getBstate();
	                    	    map.put("uname", uname);
	                    	    map.put("touxiang", touxiang);
	                    	    map.put("bname", bname);
	                    	    map.put("bprice", bprice);
	                    	    map.put("bintro", bintro);
	                    	    map.put("bpic", bpic);
	                    	    map.put("bpic1", bpic1);
	                    	    map.put("btime", btime);
	                    	    map.put("bid", bid);
	                    	    map.put("bstate", bstate);
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
		}
		
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { 
            @Override 
            public void onCheckedChanged(RadioGroup group, int checkedId) { 
                // TODO Auto-generated method stub 
                if(checkedId==radio3.getId()&&radio3.isChecked()==true){ 
                	et_bkey.setText(null);
                	System.out.println(radio3.isChecked());
                	System.out.println("撞到radio3");
                	final String btype=radio3.getText().toString();
        			final Handler myHandler=new Handler(){
        				@SuppressWarnings("unchecked")
        				public void handleMessage(Message msg){
        					listitems=(List<HashMap<String, Object>>) msg.obj;
        					System.out.println(listitems);
        					final MyAdapter myadapter = new MyAdapter(view.getContext(),listitems); //创建适配器  
        				    mListView.setAdapter(myadapter); 
        				    mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
        	            		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        	            		{
        	            			switch (parent.getId())
        	            			{
        	            			case R.id.listview2:
        	            				expressitemClick(position);//position 代表你点的哪一个
        	            				break;
        	                         }
        	                     }
        	                public void expressitemClick(int postion){
        	            	Intent itemintent = new Intent(getActivity(),itemActivity.class);
        	                //item内的数据保存在map中
        	                @SuppressWarnings("unchecked")
        					Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
        	                
        	                //需要的数据在intent中
        	                itemintent.putExtra("bid", (Integer)map.get("bid"));
        	                itemintent.putExtra("bname", (String)map.get("bname"));
        	                itemintent.putExtra("bprice", (Double)map.get("bprice"));
        	                System.out.println("111"+map.get("bprice"));
        	                itemintent.putExtra("bintro", (String)map.get("bintro"));
        	                itemintent.putExtra("bpic1",(String)map.get("bpic1") );
        	                //跳转页面
        	                startActivity(itemintent);  
        	            }});
        				    
        				}
        			};
        			new Thread(new Runnable(){
        				public void run(){
        					try {
        						    List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
        	                        cp=cpnameParse.getcpname(btype);
        	                    	for(int j=0;j<cp.size();j++)
        	    					{
        	                    		HashMap<String, Object> map = new HashMap<String, Object>(); 
        	    						System.out.println(cp.get(j).getUname());
        	    						System.out.println(cp.get(j).getTouxiang());
        	    						System.out.println(cp.get(j).getBname());
        	    						System.out.println(cp.get(j).getBprice());
        	    						System.out.println(cp.get(j).getBintro());
        	    						System.out.println(cp.get(j).getBpic());
        	    						System.out.println(cp.get(j).getBid());
        	    						System.out.println(cp.get(j).getBstate());
        	    						System.out.println("wodetiannananananan"+cp.get(j).getBtime());
        	    						String uname=cp.get(j).getUname();
        	    						String touxiang1=cp.get(j).getTouxiang();
        	    						byte[] bitmapArray;
        	    						bitmapArray = Base64.decode(touxiang1, Base64.DEFAULT);
        	    						Bitmap touxiang = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
        	    						String bname=cp.get(j).getBname();
        	    						Double bprice=cp.get(j).getBprice();
        	    						String bintro=cp.get(j).getBintro();
        	    						String bpic1=cp.get(j).getBpic();
        	    						byte[] bitmapArray1;
        	    						bitmapArray1 = Base64.decode(bpic1, Base64.DEFAULT);
        	    						Bitmap bpic = BitmapFactory.decodeByteArray(bitmapArray1, 0,bitmapArray1.length);
        	    						String btime1=cp.get(j).getBtime();
        	    						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	    						Date date = sdf.parse(btime1);
        	    						String btime2=sdf.format(date);
        	    						String lid1=btime2.substring(5,7).trim();
        	    	        			String lid2=btime2.substring(8,10).trim();
        	    	        			String lid3=btime2.substring(11,13).trim();
        	    	        			String lid4=btime2.substring(14,16).trim();
        	    	        			String btime=lid1+"-"+lid2+" "+lid3+":"+lid4;
        	    	        			int bid=cp.get(j).getBid();
        	    	        			String bstate=cp.get(j).getBstate();
        	                    	    map.put("uname", uname);
        	                    	    map.put("touxiang", touxiang);
        	                    	    map.put("bname", bname);
        	                    	    map.put("bprice", bprice);
        	                    	    map.put("bintro", bintro);
        	                    	    map.put("bpic", bpic);
        	                    	    map.put("bpic1", bpic1);
        	                    	    map.put("btime", btime);
        	                    	    map.put("bid", bid);
        	                    	    map.put("bstate", bstate);
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
                }
                if(checkedId==radio4.getId()&&radio4.isChecked()==true){ 
                	et_bkey.setText(null);
                	System.out.println(radio4.isChecked());
                	System.out.println("撞到radio4");
                	final String btype=radio4.getText().toString();
        			System.out.println(btype);
        			final Handler myHandler=new Handler(){
        				@SuppressWarnings("unchecked")
        				public void handleMessage(Message msg){
        					listitems=(List<HashMap<String, Object>>) msg.obj;
        					System.out.println(listitems);
        					final MyAdapter myadapter = new MyAdapter(view.getContext(),listitems); //创建适配器  
        				    mListView.setAdapter(myadapter); 
        				    mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
        	            		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        	            		{
        	            			switch (parent.getId())
        	            			{
        	            			case R.id.listview2:
        	            				expressitemClick(position);//position 代表你点的哪一个
        	            				break;
        	                         }
        	                     }
        	                public void expressitemClick(int postion){
        	            	Intent itemintent = new Intent(getActivity(),itemActivity.class);
        	                //item内的数据保存在map中
        	                @SuppressWarnings("unchecked")
        					Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
        	                
        	                //需要的数据在intent中
        	                itemintent.putExtra("bid", (Integer)map.get("bid"));
        	                itemintent.putExtra("bname", (String)map.get("bname"));
        	                itemintent.putExtra("bprice", (Double)map.get("bprice"));
        	                System.out.println("111"+map.get("bprice"));
        	                itemintent.putExtra("bintro", (String)map.get("bintro"));
        	                itemintent.putExtra("bpic1",(String)map.get("bpic1") );
        	                //跳转页面
        	                startActivity(itemintent);  
        	            }});
        				    
        				}
        			};
        			new Thread(new Runnable(){
        				public void run(){
        					try {
        						    List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
        	                        cp=cpnameParse.getcpname(btype);
        	                    	for(int j=0;j<cp.size();j++)
        	    					{
        	                    		HashMap<String, Object> map = new HashMap<String, Object>(); 
        	    						System.out.println(cp.get(j).getUname());
        	    						System.out.println(cp.get(j).getTouxiang());
        	    						System.out.println(cp.get(j).getBname());
        	    						System.out.println(cp.get(j).getBprice());
        	    						System.out.println(cp.get(j).getBintro());
        	    						System.out.println(cp.get(j).getBpic());
        	    						System.out.println(cp.get(j).getBid());
        	    						System.out.println(cp.get(j).getBstate());
        	    						System.out.println("wodetiannananananan"+cp.get(j).getBtime());
        	    						String uname=cp.get(j).getUname();
        	    						String touxiang1=cp.get(j).getTouxiang();
        	    						byte[] bitmapArray;
        	    						bitmapArray = Base64.decode(touxiang1, Base64.DEFAULT);
        	    						Bitmap touxiang = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
        	    						String bname=cp.get(j).getBname();
        	    						Double bprice=cp.get(j).getBprice();
        	    						String bintro=cp.get(j).getBintro();
        	    						String bpic1=cp.get(j).getBpic();
        	    						byte[] bitmapArray1;
        	    						bitmapArray1 = Base64.decode(bpic1, Base64.DEFAULT);
        	    						Bitmap bpic = BitmapFactory.decodeByteArray(bitmapArray1, 0,bitmapArray1.length);
        	    						String btime1=cp.get(j).getBtime();
        	    						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	    						Date date = sdf.parse(btime1);
        	    						String btime2=sdf.format(date);
        	    						String lid1=btime2.substring(5,7).trim();
        	    	        			String lid2=btime2.substring(8,10).trim();
        	    	        			String lid3=btime2.substring(11,13).trim();
        	    	        			String lid4=btime2.substring(14,16).trim();
        	    	        			String btime=lid1+"-"+lid2+" "+lid3+":"+lid4;
        	    	        			int bid=cp.get(j).getBid();
        	    	        			String bstate=cp.get(j).getBstate();
        	                    	    map.put("uname", uname);
        	                    	    map.put("touxiang", touxiang);
        	                    	    map.put("bname", bname);
        	                    	    map.put("bprice", bprice);
        	                    	    map.put("bintro", bintro);
        	                    	    map.put("bpic", bpic);
        	                    	    map.put("bpic1", bpic1);
        	                    	    map.put("btime", btime);
        	                    	    map.put("bid", bid);
        	                    	    map.put("bstate", bstate);
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
                }
                
                
            }
		});
		
		
		
		return view;
	}
}
