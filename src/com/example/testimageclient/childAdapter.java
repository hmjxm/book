
package com.example.testimageclient;

import java.util.List;

import com.eegets.peter.enclosure.network.bitmap.abitmap.AWonderBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class childAdapter extends BaseAdapter
{
	private class ListItemView
    {           
		    //自定义控件集合    
		    public TextView bname; 
		    public TextView bprice;
		    public ImageView bpic;
                  
    }   
	private Context context;                        //运行上下文  
    private List<book> listItems;    //商品信息集合  
    private LayoutInflater listContainer;           //视图容器                
    private ListItemView  listItemView ;

    public childAdapter(Context context,List<book> list) {
		super();
		this.listItems=list;
		this.context = context;
		this.listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文  	
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
        //自定义视图  
        if (convertView == null) {  
            listItemView = new ListItemView();   
            //获取list_item布局文件的视图  
            convertView = listContainer.inflate(R.layout.myorderbook_item, null);  
            //获取控件对象  
            listItemView.bname= (TextView)convertView.findViewById(R.id.bname);  
            listItemView.bprice = (TextView)convertView.findViewById(R.id.bprice);  
            listItemView.bpic = (ImageView)convertView.findViewById(R.id.bpic);
            //设置控件集到convertView  
            convertView.setTag(listItemView);  
        }else {  
            listItemView = (ListItemView)convertView.getTag();  
        }  
        
        listItemView.bname.setText((String) listItems.get(position).getBname());
        Double bprice1=listItems.get(position).getBprice();
        String bprice=String.valueOf(bprice1);
        listItemView.bprice.setText("￥"+bprice);
        String bpic1=listItems.get(position).getBpic();
        byte[] bitmapArray;
		bitmapArray = Base64.decode(bpic1, Base64.DEFAULT);
		Bitmap bpic = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
        listItemView.bpic.setImageBitmap(bpic);      
        return convertView;  
    }  
 
}