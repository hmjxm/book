
package com.example.testimageclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class myUploadAdapter extends BaseAdapter
{
	private class ListItemView
    {            
            public TextView bname;    
            public TextView bprice;
            public ImageView bpic;       
            public TextView delmyupload;
    }   
	private Context context;                        //运行上下文  
    private List<HashMap<String, Object>> listItems;    //商品信息集合  
    private LayoutInflater listContainer;           //视图容器                
    private ListItemView  listItemView ;
      
    public myUploadAdapter(Context context, List<HashMap<String, Object>> listitems) {  
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


    public View getView( final int position, View convertView, ViewGroup parent) { 
        // TODO Auto-generated method stub  
        //自定义视图  
        if (convertView == null) {  
            listItemView = new ListItemView();   
            //获取list_item布局文件的视图  
            convertView = listContainer.inflate(R.layout.myupload_item, null);  
            //获取控件对象  
            listItemView.bname = (TextView)convertView.findViewById(R.id.bname);
            listItemView.bprice = (TextView)convertView.findViewById(R.id.bprice);
            listItemView.bpic= (ImageView)convertView.findViewById(R.id.bpic);
            listItemView.delmyupload = (TextView)convertView.findViewById(R.id.delmyupload);
            //设置控件集到convertView  
            convertView.setTag(listItemView);  
        }else {  
            listItemView = (ListItemView)convertView.getTag();  
        }  
        listItemView.bname.setText((String) listItems.get(position)  
                .get("bname"));  
        String price = String.valueOf(listItems.get(position)  
                .get("bprice"));
        listItemView.bprice.setText("￥"+price); 
        String bpic1=(String) listItems.get(position).get("bpic");
        byte[] bitmapArray1;
		bitmapArray1 = Base64.decode(bpic1, Base64.DEFAULT);
		Bitmap bpic = BitmapFactory.decodeByteArray(bitmapArray1, 0,bitmapArray1.length);
        listItemView.bpic.setImageBitmap(bpic); 
        
        listItemView.delmyupload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final int bid=(Integer) listItems.get(position).get("bid");
				final String bid1=String.valueOf(bid);
				listItems.remove(position);
				notifyDataSetChanged();
				new Thread(new Runnable(){
          			public void run(){
					String result = null;
					try {
						result = deleteMyUploadParse.doPost(bid1);
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