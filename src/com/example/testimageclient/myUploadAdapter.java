
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
	private Context context;                        //����������  
    private List<HashMap<String, Object>> listItems;    //��Ʒ��Ϣ����  
    private LayoutInflater listContainer;           //��ͼ����                
    private ListItemView  listItemView ;
      
    public myUploadAdapter(Context context, List<HashMap<String, Object>> listitems) {  
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


    public View getView( final int position, View convertView, ViewGroup parent) { 
        // TODO Auto-generated method stub  
        //�Զ�����ͼ  
        if (convertView == null) {  
            listItemView = new ListItemView();   
            //��ȡlist_item�����ļ�����ͼ  
            convertView = listContainer.inflate(R.layout.myupload_item, null);  
            //��ȡ�ؼ�����  
            listItemView.bname = (TextView)convertView.findViewById(R.id.bname);
            listItemView.bprice = (TextView)convertView.findViewById(R.id.bprice);
            listItemView.bpic= (ImageView)convertView.findViewById(R.id.bpic);
            listItemView.delmyupload = (TextView)convertView.findViewById(R.id.delmyupload);
            //���ÿؼ�����convertView  
            convertView.setTag(listItemView);  
        }else {  
            listItemView = (ListItemView)convertView.getTag();  
        }  
        listItemView.bname.setText((String) listItems.get(position)  
                .get("bname"));  
        String price = String.valueOf(listItems.get(position)  
                .get("bprice"));
        listItemView.bprice.setText("��"+price); 
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