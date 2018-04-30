package com.example.testimageclient;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.example.testimageclient.FragmentCart.shopcartAdapter.numOnClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class selectLocationActivity extends Activity{
	private List<location> myList;
	private ListView listview5;
    private TextView subtitle;
    private Button insert_btn;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectlocation);
		listview5=(ListView)findViewById(R.id.listview5);
		subtitle=(TextView)findViewById(R.id.subtitle);
		insert_btn=(Button)findViewById(R.id.insert_btn);
		final Handler myHandler=new Handler(){
			public void handleMessage(Message msg){
				List<HashMap<String, Object>> response=(List<HashMap<String, Object>>) msg.obj;
				System.out.println(response);
				final selectLocationAdapter selectlocationadapter = new selectLocationAdapter(selectLocationActivity.this,response); //����������  
				listview5.setAdapter(selectlocationadapter);
				
				listview5.setOnItemClickListener(new OnItemClickListener(){   	                  	
            		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            		{
            			switch (parent.getId())
            			{
            			case R.id.listview5:
            				expressitemClick(position);//position ����������һ��
            				break;
            			
                         }
                     }
                public void expressitemClick(int postion){
            	Intent intent = new Intent(selectLocationActivity.this,confirmOrderActivity.class);
                //item�ڵ����ݱ�����map��
                @SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>)selectlocationadapter.getItem(postion);	
                System.out.println("dadadadadadad");
               //��Ҫ��������intent��
                int lid1=(Integer) map.get("lid");
                String lid=String.valueOf(lid1);
                intent.putExtra("lid", lid);
                System.out.println(lid);
                intent.putExtra("lper", (String)map.get("lper"));
                System.out.println(map.get("lper"));
                long ltel=(Long) map.get("ltel");
                intent.putExtra("ltel", ltel);
                System.out.println(map.get("ltel"));
                intent.putExtra("loc", (String)map.get("loc"));
                System.out.println(map.get("loc"));
                setResult(11,intent);
				finish();
                }
		        });
				
				
	      }
        };
		new Thread(new Runnable(){
			public void run(){
				List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
				String uname=loginActivity.getUserName(selectLocationActivity.this);
				System.out.println("unameΪ��"+uname);
					try {
						myList=selectLocationParse.doPost(uname);
						for(int j=0;j<myList.size();j++)
						{
		            		HashMap<String, Object> map = new HashMap<String, Object>(); 
							System.out.println(myList.get(j).getLid());
							System.out.println(myList.get(j).getLper());
							System.out.println(myList.get(j).getLtel());
							System.out.println(myList.get(j).getLoc());
							int lid=myList.get(j).getLid();
							String lper=myList.get(j).getLper();
							long ltel=myList.get(j).getLtel();
							String loc=myList.get(j).getLoc();
							map.put("lid", lid);
		            	    map.put("lper", lper);
		            	    map.put("ltel", ltel);
		            	    map.put("loc", loc);
							data.add(map); 
						}  
						System.out.println(data);
				        Message msg=new Message();
			            msg.obj=data;
				        myHandler.sendMessage(msg);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}).start();
		
		insert_btn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent=new Intent(selectLocationActivity.this,insertLocationActivity.class);
        		startActivityForResult(intent,15);
        		
        	}
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
		if(resultCode==15&&requestCode==15){
			final Handler myHandler=new Handler(){
				public void handleMessage(Message msg){
					List<HashMap<String, Object>> response=(List<HashMap<String, Object>>) msg.obj;
					System.out.println(response);
					final selectLocationAdapter selectlocationadapter = new selectLocationAdapter(selectLocationActivity.this,response); //����������  
					listview5.setAdapter(selectlocationadapter);
					
					listview5.setOnItemClickListener(new OnItemClickListener(){   	                  	
	            		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	            		{
	            			switch (parent.getId())
	            			{
	            			case R.id.listview5:
	            				expressitemClick(position);//position ����������һ��
	            				break;
	            			
	                         }
	                     }
	                public void expressitemClick(int postion){
	            	Intent intent = new Intent(selectLocationActivity.this,confirmOrderActivity.class);
	                //item�ڵ����ݱ�����map��
	                @SuppressWarnings("unchecked")
					Map<String, Object> map = (Map<String, Object>)selectlocationadapter.getItem(postion);	
	                System.out.println("dadadadadadad");
	               //��Ҫ��������intent��
	                int lid1=(Integer) map.get("lid");
	                String lid=String.valueOf(lid1);
	                intent.putExtra("lid", lid);
	                System.out.println(lid);
	                intent.putExtra("lper", (String)map.get("lper"));
	                System.out.println(map.get("lper"));
	                long ltel=(Long) map.get("ltel");
	                intent.putExtra("ltel", ltel);
	                System.out.println(map.get("ltel"));
	                intent.putExtra("loc", (String)map.get("loc"));
	                System.out.println(map.get("loc"));
	                setResult(11,intent);
					finish();
	                }
			        });
					
					
		      }
	        };
			new Thread(new Runnable(){
				public void run(){
					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					String uname=loginActivity.getUserName(selectLocationActivity.this);
					System.out.println("unameΪ��"+uname);
						try {
							myList=selectLocationParse.doPost(uname);
							for(int j=0;j<myList.size();j++)
							{
			            		HashMap<String, Object> map = new HashMap<String, Object>(); 
								System.out.println(myList.get(j).getLid());
								System.out.println(myList.get(j).getLper());
								System.out.println(myList.get(j).getLtel());
								System.out.println(myList.get(j).getLoc());
								int lid=myList.get(j).getLid();
								String lper=myList.get(j).getLper();
								long ltel=myList.get(j).getLtel();
								String loc=myList.get(j).getLoc();
								map.put("lid", lid);
			            	    map.put("lper", lper);
			            	    map.put("ltel", ltel);
			            	    map.put("loc", loc);
								data.add(map); 
							}  
							System.out.println(data);
					        Message msg=new Message();
				            msg.obj=data;
					        myHandler.sendMessage(msg);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
		}).start();
		}	
	}
	
	
	public class selectLocationAdapter extends BaseAdapter
	{
		private class ListItemView
	    {                //�Զ���ؼ�����        
	            public TextView lper;    
	            public TextView ltel;   
	            public TextView loc;
	            public TextView delete;   
	    }   
		private Context context;                        //����������  
	    private List<HashMap<String, Object>> listItems;    //��Ʒ��Ϣ����  
	    private LayoutInflater listContainer;           //��ͼ����                
	    private ListItemView  listItemView ;
	    
	    public selectLocationAdapter(Context context, List<HashMap<String, Object>> listitems) {  
	        this.context = context;           
	        listContainer = LayoutInflater.from(context);   //������ͼ����������������  
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
	    
	    private void doDelete(final int lid)
		{
			for (int i = 0; i < listItems.size(); i++)
			{
				final int dataId = (Integer) listItems.get(i).get("lid");
				System.out.println("data"+dataId);
			
					if (dataId == lid)
					{
						listItems.remove(i);
						i--;
						new Thread(new Runnable(){
		          			public void run(){
							String result = null;
							try {
								result = deleteLocationParse.doPost(lid);
								System.out.println("lid"+lid);
								System.out.println("aaaa"+result);
							} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		          			}}).start();
					}
			}
			refreshListView();
		}
	    
	    @Override
	    public View getView( int position, View convertView, ViewGroup parent) {  
	        // TODO Auto-generated method stub  
	        //�Զ�����ͼ  
	        if (convertView == null) {  
	            listItemView = new ListItemView();   
	            //��ȡlist_item�����ļ�����ͼ  
	            convertView = listContainer.inflate(R.layout.selectlocation_item, null);  
	            //��ȡ�ؼ�����  
	            listItemView.lper = (TextView)convertView.findViewById(R.id.lper);  
	            listItemView.ltel = (TextView)convertView.findViewById(R.id.ltel);  
	            listItemView.loc = (TextView)convertView.findViewById(R.id.loc);
	            listItemView.delete = (TextView)convertView.findViewById(R.id.delete);
	            //���ÿؼ�����convertView  
	            convertView.setTag(listItemView);  
	        }else {  
	            listItemView = (ListItemView)convertView.getTag();  
	        }  
            System.out.println("������������������������"+listItems);
	        listItemView.lper.setText((String) listItems.get(position)  
	                .get("lper"));  
	        String ltel=String.valueOf(listItems.get(position).get("ltel"));
	        listItemView.ltel.setText(ltel);  
	        listItemView.loc.setText((String) listItems.get(position)  
	                .get("loc"));  
	        listItemView.delete.setOnClickListener(new numOnClickListener(position));
	        listItemView.lper.setOnClickListener(new numOnClickListener(position));
	        listItemView.ltel.setOnClickListener(new numOnClickListener(position));
	        listItemView.loc.setOnClickListener(new numOnClickListener(position));
	        
	        return convertView;  
	    }
	    
	    class numOnClickListener implements  View.OnClickListener{
	        private int position;
	        numOnClickListener(int pos){
	          position = pos;
	        }
	        @Override
	        //��дonClick��������������ť
	        public void onClick(View v){
	          int vid = v.getId();
	        if(vid==listItemView.delete.getId()){
	        	  int lid = (Integer) listItems.get(position).get("lid");
	        	  System.out.println("idΪ��"+lid);
	        	  doDelete(lid);
	        	  
	        }
	        
	        }
	       
	    }//class click
	}
}
