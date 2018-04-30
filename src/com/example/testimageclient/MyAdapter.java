
package com.example.testimageclient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter
{
	private class ListItemView
    {           
		    //�Զ���ؼ�����    
		    public TextView uname; 
		    public TextView btime;
            public ImageView touxiang;   
            public TextView bname;    
            public TextView bprice;
            public ImageView bpic;           
    }   
	private Context context;                        //����������  
    public static  List<HashMap<String, Object>> listItems;    //��Ʒ��Ϣ����  
    private LayoutInflater listContainer;           //��ͼ����                
    private ListItemView  listItemView ;
      
    public MyAdapter(Context context, List<HashMap<String, Object>> listitems) {  
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


    public View getView( int position, View convertView, ViewGroup parent) { 
        // TODO Auto-generated method stub  
        //�Զ�����ͼ  
        if (convertView == null) {  
            listItemView = new ListItemView();   
            //��ȡlist_item�����ļ�����ͼ  
            convertView = listContainer.inflate(R.layout.list_item, null);  
            //��ȡ�ؼ�����  
            listItemView.touxiang= (ImageView)convertView.findViewById(R.id.touxiang);  
            listItemView.uname = (TextView)convertView.findViewById(R.id.uname);  
            listItemView.btime = (TextView)convertView.findViewById(R.id.btime);
            listItemView.bname = (TextView)convertView.findViewById(R.id.bname);
            listItemView.bprice = (TextView)convertView.findViewById(R.id.bprice);
            listItemView.bpic= (ImageView)convertView.findViewById(R.id.bpic);
            //���ÿؼ�����convertView  
            convertView.setTag(listItemView);  
        }else {  
            listItemView = (ListItemView)convertView.getTag();  
        }  

        listItemView.touxiang.setImageBitmap((Bitmap)listItems.get(  
                position).get("touxiang"));  
        listItemView.uname.setText((String) listItems.get(position)  
                .get("uname"));
        listItemView.btime.setText((String) listItems.get(position)  
                .get("btime"));
        listItemView.bname.setText((String) listItems.get(position)  
                .get("bname"));  
        String price = String.valueOf(listItems.get(position)  
                .get("bprice"));
        listItemView.bprice.setText("��"+price); 
        listItemView.bpic.setImageBitmap((Bitmap)listItems.get(  
                position).get("bpic"));  
        

        return convertView;  
    }  
    
    
}