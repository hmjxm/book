
package com.example.testimageclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class parentAdapter extends BaseAdapter implements ListAdapter
{
	private class ListItemView
    {           
		    //自定义控件集合    
		    
		    public TextView orderid; 
		    public ChildLiistView listview8;
		    public TextView allprice;
		    public TextView delorder;
                  
    }   
	private Context context;                        //运行上下文  
    private List<HashMap<String, Object>> listItems;    //商品信息集合  
    private LayoutInflater listContainer;           //视图容器                
    private ListItemView  listItemView;
    private childAdapter childadapter;
    private Thread thread1;
      
    public parentAdapter(Context context, List<HashMap<String, Object>> listitems) {  
        this.context = context;           
        this.listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文  
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


    public View getView( final int position, View convertView, ViewGroup parent) { 
        // TODO Auto-generated method stub  
        //自定义视图  
        if (convertView == null) {  
            listItemView = new ListItemView();   
            //获取list_item布局文件的视图  
            convertView = listContainer.inflate(R.layout.myorder_item, null);  
            //获取控件对象  
            listItemView.orderid= (TextView)convertView.findViewById(R.id.orderid);  
            listItemView.listview8 = (ChildLiistView)convertView.findViewById(R.id.listview8);  
            listItemView.allprice = (TextView)convertView.findViewById(R.id.allprice);
            listItemView.delorder = (TextView)convertView.findViewById(R.id.delorder);
            //设置控件集到convertView  
            convertView.setTag(listItemView);  
        }else {  
            listItemView = (ListItemView)convertView.getTag();  
        }  
 
        listItemView.orderid.setText((String) listItems.get(position)  
                .get("orderid"));
        Double allprice1=(Double) listItems.get(position).get("allprice");
        String allprice=String.valueOf(allprice1);
        listItemView.allprice.setText(allprice);
        List<book> booklist=(List<book>) listItems.get(position).get("booklist");
        childadapter = new childAdapter(context,booklist);
        listItemView.listview8.setAdapter(childadapter);
        listItemView.listview8.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
					System.out.println("订单详情！");
			}
		});
        listItemView.delorder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<book> booklist=(List<book>) listItems.get(position).get("booklist");
				final String orderid=(String) listItems.get(position).get("orderid");
				listItems.remove(position);
				notifyDataSetChanged();
				for(int i=0;i<booklist.size();i++){
					final int bid=booklist.get(i).getBid();
					System.out.println(bid);
					thread1=new Thread(new Runnable(){
	          			public void run(){
						String result = null;
						try {
							result = deleteOrderDetailParse.doPost(bid,orderid);
							System.out.println("aaaa"+result);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	          			}});
					    thread1.start();
				}
				try {
					thread1.sleep(600);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				new Thread(new Runnable(){
          			public void run(){
					String result = null;
					try {
						result = deleteOrderParse.doPost(orderid);
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
		});
        return convertView;  
    }  
    
    
}
