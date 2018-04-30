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

public class orderDetailActivity extends Activity {
	private TextView tv_orderprice;
	private ListView listview6;
	private TextView subtitle;
    private TextView tv_lper;
    private TextView tv_orderid;
    private TextView tv_ordertime;
    private TextView tv_ltel;
    private TextView tv_loc;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderdetail);
		tv_orderprice=(TextView)findViewById(R.id.tv_orderprice);
		listview6=(ListView)findViewById(R.id.listview6);
		tv_lper=(TextView)findViewById(R.id.tv_lper);
		tv_ltel=(TextView)findViewById(R.id.tv_ltel);
		tv_loc=(TextView)findViewById(R.id.tv_loc);
		tv_orderid=(TextView)findViewById(R.id.tv_orderid);
		tv_ordertime=(TextView)findViewById(R.id.tv_ordertime);
		subtitle=(TextView)findViewById(R.id.subtitle);
		Double totalPrice=0.0;
		Intent intent=getIntent(); 
		final List<HashMap<String,Object>> blist = (List<HashMap<String, Object>>)intent.getSerializableExtra("blist");
		System.out.println("blist为          ："+blist);
		for(int i=0;i<blist.size();i++){
			HashMap<String, Object> map=new HashMap<String, Object>();
			Double bprice=(Double) blist.get(i).get("bprice");
			totalPrice+=bprice;
		}
		tv_orderprice.setText("￥"+totalPrice);
		
		String lper=intent.getExtras().getString("lper");
		long ltel1=intent.getExtras().getLong("ltel");
		String ltel=String.valueOf(ltel1);
		String loc=intent.getExtras().getString("loc");
		String orderid=intent.getExtras().getString("orderid");
		String ordertime=intent.getExtras().getString("ordertime");
		
		System.out.println(lper+ltel1+loc+orderid+ordertime);
		tv_lper.setText(lper);
		tv_ltel.setText(ltel);
		tv_loc.setText(loc);
		tv_orderid.setText(orderid);
		tv_ordertime.setText(ordertime);
		
		orderDetailAdapter orderDetailAdapter = new orderDetailAdapter(orderDetailActivity.this,blist); //创建适配器  
		listview6.setAdapter(orderDetailAdapter);

		subtitle.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent=new Intent(orderDetailActivity.this,confirmOrderActivity.class);  
        		setResult(13,intent);
				finish();
        	}
        });
	}
	
	public class orderDetailAdapter extends BaseAdapter
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
	    
	    public orderDetailAdapter(Context context, List<HashMap<String, Object>> listitems) {  
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
	            convertView = listContainer.inflate(R.layout.orderdetail_item, null);  
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
            
            String bpic1=(String) listItems.get(position).get("bpic1");
			byte[] bitmapArray;
			bitmapArray = Base64.decode(bpic1, Base64.DEFAULT);
			Bitmap bpic= BitmapFactory.decodeByteArray(bitmapArray,0,bitmapArray.length);
	        listItemView.imageview1.setImageBitmap(bpic);  
	        listItemView.tv1.setText((String) listItems.get(position)  
	                .get("bname"));  
	        listItemView.tv2.setText("￥"+listItems.get(position)  
	                .get("bprice"));  
	        return convertView;  
	    }

	}//class shopcartAdapter
	
}//class activity