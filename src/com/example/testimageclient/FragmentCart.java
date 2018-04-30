package com.example.testimageclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentCart extends Fragment{	
	public TextView tv_allprice; 
    public TextView tv_selectnum; 
    public TextView subtitle; 
    public TextView tv_submit;
    public CheckBox check_box;
    private ListView Listview3;
    public static Double totalPrice=0.0;
    public  static  int len=0;
    private List<book> myList;
    public static List<HashMap<String, Object>> listItems;     
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view=inflater.inflate(R.layout.shopcart, null);	
    	tv_allprice=(TextView)view.findViewById(R.id.tv_allprice);
    	tv_selectnum=(TextView)view.findViewById(R.id.tv_selectnum);
    	tv_submit=(TextView)view.findViewById(R.id.tv_submit);
    	check_box=(CheckBox)view.findViewById(R.id.check_box);
        subtitle=(TextView)view.findViewById(R.id.subtitle);
        Listview3=(ListView)view.findViewById(R.id.listview3);
    	check_box.setChecked(false);

		final Handler myHandler=new Handler(){
			public void handleMessage(Message msg){
				List<HashMap<String, Object>> response=(List<HashMap<String, Object>>) msg.obj;
				System.out.println(response);
				shopcartAdapter shopadapter = new shopcartAdapter(getActivity(),response); //创建适配器  
				Listview3.setAdapter(shopadapter);
				}
        };
		new Thread(new Runnable(){
			public void run(){
				List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
				String uname=loginActivity.getUserName(getContext());
				if(!uname.equals("点击登录")){
					try {
						myList=getCartParse.doPost(uname);
						for(int j=0;j<myList.size();j++)
						{
		            		HashMap<String, Object> map = new HashMap<String, Object>(); 
							System.out.println(myList.get(j).getBname());
							System.out.println(myList.get(j).getBprice());
							System.out.println(myList.get(j).getBpic());
							String bname=myList.get(j).getBname();
							Double bprice=myList.get(j).getBprice();
							String bpic1=myList.get(j).getBpic();
							int bid=myList.get(j).getBid();
							byte[] bitmapArray1;
							bitmapArray1 = Base64.decode(bpic1, Base64.DEFAULT);
							Bitmap bpic = BitmapFactory.decodeByteArray(bitmapArray1, 0,bitmapArray1.length);
							map.put("bid", bid);
		            	    map.put("bname", bname);
		            	    map.put("bprice", bprice);
		            	    map.put("bpic", bpic);
		            	    map.put("bpic1", bpic1);
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
                
            	
	}}).start();
		return view;
	}
	@Override
	public  void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==12&&requestCode==12){
			check_box.setChecked(false);
			totalPrice=0.0;
			len=0;
			tv_allprice.setText("￥" + totalPrice+ "");
		    tv_selectnum.setText("已选" + len + "类书籍");
		    
			final Handler myHandler=new Handler(){
				public void handleMessage(Message msg){
					List<HashMap<String, Object>> response=(List<HashMap<String, Object>>) msg.obj;
					System.out.println(response);
					shopcartAdapter shopadapter = new shopcartAdapter(getActivity(),response); //创建适配器  
					Listview3.setAdapter(shopadapter);
					}
	        };
			new Thread(new Runnable(){
				public void run(){
					List<book> list;
					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					String uname=loginActivity.getUserName(getContext());
					System.out.println(listItems);
					if(!uname.equals("点击登录")){
						try {
							list=getCartParse1.doPost(uname);
							System.out.println("mylistlalallalaalallal为："+list);
							for(int j=0;j<list.size();j++)
							{
			            		HashMap<String, Object> map = new HashMap<String, Object>(); 
								System.out.println(list.get(j).getBname());
								System.out.println(list.get(j).getBprice());
								System.out.println(list.get(j).getBpic());
								String bname=list.get(j).getBname();
								Double bprice=list.get(j).getBprice();
								String bpic1=list.get(j).getBpic();
								int bid=list.get(j).getBid();
								if(bid==0&&bname.equals("0")&&bprice==0&&bpic1.equals("0")){
									for(int i=0;i<listItems.size();i++){
										listItems.remove(i);
									}
								}
								else{
									byte[] bitmapArray1;
									bitmapArray1 = Base64.decode(bpic1, Base64.DEFAULT);
									Bitmap bpic = BitmapFactory.decodeByteArray(bitmapArray1, 0,bitmapArray1.length);
									map.put("bid", bid);
				            	    map.put("bname", bname);
				            	    map.put("bprice", bprice);
				            	    map.put("bpic", bpic);
				            	    map.put("bpic1", bpic1);
									data.add(map);
								}
								 
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
	                
	            	
		}}).start();
			
		}
	}

public class shopcartAdapter extends BaseAdapter
{
	private class ListItemView
    {                //自定义控件集合    
		    public CheckBox  cb1; 
            public ImageView imageview1;    
            public TextView tv1;    
            public TextView tv2;   
    }   
	private Context context;                        //运行上下文  
    private LayoutInflater listContainer;           //视图容器                
    private ListItemView  listItemView ;
    private SparseArray<Boolean> selectstate = new SparseArray<Boolean>(); 
    private boolean isBatchModel;// 是否可删除模式
    
    public shopcartAdapter(Context context, List<HashMap<String, Object>> listitems) {  
        this.context = context;           
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文  
        listItems = listitems;  
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
    
    private void doDelete(List<Integer> ids)
	{
    	final String uname=loginActivity.getUserName(getContext());
		for (int i = 0; i < listItems.size(); i++)
		{
			final Integer dataId = (Integer) listItems.get(i).get("bid");
			System.out.println("data"+dataId);
			Double bprice=(Double) listItems.get(i).get("bprice");
			for (int j = 0; j < ids.size(); j++)
			{
				int deleteId = ids.get(j);
				System.out.println("delete"+deleteId);
				if (dataId == deleteId)
				{
					listItems.remove(i);
					i--;
					ids.remove(j);
					j--;
					len--;
					totalPrice -= bprice;
            		tv_allprice.setText("￥" + totalPrice + "");
      		        tv_selectnum.setText("已选" + len + "类书籍");
      		        
      		        new Thread(new Runnable(){
          			public void run(){
					String result = null;
					try {
						result = deleteCartParse.doPost(dataId,uname);
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
		}
		if(listItems.size()==0)
		{
			check_box.setChecked(false);
		}
		refreshListView();
	}
		
		private List<HashMap<String,Object>> doSubmit(List<Integer> ids)
		{
			List<HashMap<String, Object>> blist=new ArrayList<HashMap<String,Object>>();
			for (int i = 0; i < listItems.size(); i++)
			{
				final Integer dataId = (Integer) listItems.get(i).get("bid");
				Double bprice=(Double) listItems.get(i).get("bprice");
				String bname=(String) listItems.get(i).get("bname");
				String bpic1=(String) listItems.get(i).get("bpic1");
				
				for (int j = 0; j < ids.size(); j++)
				{
					int submitId = ids.get(j);
					if (dataId == submitId)
					{
						HashMap<String, Object> map=new HashMap<String, Object>();
						map.put("bid", dataId);
		        	    map.put("bname", bname);
		        	    map.put("bprice", bprice);
		        	    map.put("bpic1", bpic1);
						blist.add(map); 
					}
				}
			}
		
		return blist;
	}

	private final List<Integer> getSelectedIds()
	{
		ArrayList<Integer> selectedIds = new ArrayList<Integer>();
		for (int index = 0; index < selectstate.size(); index++)
		{
			if (selectstate.valueAt(index))
			{
				selectedIds.add(selectstate.keyAt(index));
			}
		}
		return selectedIds;
	}
    
    public View getView( int position, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  
        //自定义视图  
        if (convertView == null) {  
            listItemView = new ListItemView();   
            //获取list_item布局文件的视图  
            convertView = listContainer.inflate(R.layout.cartlist_item, null);  
            //获取控件对象  
            listItemView.cb1 = (CheckBox)convertView.findViewById(R.id.cb1);  
            listItemView.imageview1 = (ImageView)convertView.findViewById(R.id.imageview1);  
            listItemView.tv1 = (TextView)convertView.findViewById(R.id.tv1);  
            listItemView.tv2 = (TextView)convertView.findViewById(R.id.tv2);
            //设置控件集到convertView  
            convertView.setTag(listItemView);  
        }else {  
            listItemView = (ListItemView)convertView.getTag();  
        }  

        listItemView.imageview1.setImageBitmap((Bitmap)listItems.get(  
                position).get("bpic"));  
        listItemView.tv1.setText((String) listItems.get(position)  
                .get("bname"));  
        listItemView.tv2.setText("￥"+listItems.get(position)  
                .get("bprice"));  
        int bid=(Integer) listItems.get(position).get("bid");  
        boolean selected = selectstate.get(bid,false);
        listItemView.cb1.setChecked(selected);
        
        listItemView.cb1.setOnClickListener(new numOnClickListener(position));
        check_box.setOnClickListener(new numOnClickListener(position));
        subtitle.setOnClickListener(new numOnClickListener(position));
        tv_submit.setOnClickListener(new numOnClickListener(position));
        return convertView;  
    }
 
    class numOnClickListener implements  View.OnClickListener{
        private int position;
        numOnClickListener(int pos){
          position = pos;
        }
        @Override
        //复写onClick方法用来监听按钮
        public void onClick(View v){
          int vid = v.getId();
        if(vid==listItemView.cb1.getId()){
        	  int bid = (Integer) listItems.get(position).get("bid");
        	  System.out.println("id为："+bid);
        	  boolean selected = !selectstate.get(bid, false);
        	  System.out.println(selected);
        	  if (selected)
    			{
        		    selectstate.put(bid,true);
    				Double bprice=(Double) listItems.get(position).get("bprice");
    				totalPrice += bprice;
    				len++;
    			} else
    			{
    				selectstate.delete(bid);
    				Double bprice=(Double) listItems.get(position).get("bprice");
    				totalPrice -= bprice;
    				len--;
    			}
        	  tv_selectnum.setText("已选" + len + "类书籍");
        	  tv_allprice.setText("￥" + totalPrice + "");
        	  
        	  if (len== listItems.size())
  			{
  				check_box.setChecked(true);
  			} else
  			{
  				check_box.setChecked(false);
  			}
        	  
          }
       if(vid==check_box.getId()){
			if (check_box.isChecked())
			{
				totalPrice=0.0;
				int size = listItems.size();
				for (int i = 0; i <size; i++)
				{
					int bid =(Integer) listItems.get(i).get("bid");
					selectstate.put(bid, true);
					Double bprice=(Double) listItems.get(i).get("bprice");
    				totalPrice += bprice;
    				len=listItems.size();
				}
				 tv_selectnum.setText("已选" + listItems.size() + "类书籍");
	        	  tv_allprice.setText("￥" + totalPrice + "");
			}
			else
			{
				int size = listItems.size();
				for (int i = 0; i < size; i++)
				{
					int bid =(Integer) listItems.get(i).get("bid");
				    selectstate.put(bid, false);
				}
					totalPrice = 0.0;
					len=0;
					tv_selectnum.setText("已选" + len + "类书籍");
		        	 tv_allprice.setText("￥" + totalPrice + "");
			}
			refreshListView();
          }
       if(vid==subtitle.getId()){
    	   isBatchModel=!isBatchModel;
    	   if (isBatchModel)
			{
    		   tv_submit.setText("删除");
    		   subtitle.setText("完成");
			}
    	   else{
    		   tv_submit.setText("结算");
    		   subtitle.setText("编辑");
    	   }
       }
       
       if(vid==tv_submit.getId()){
    	   if (isBatchModel)
			{
				List<Integer> ids = getSelectedIds();
				System.out.println(ids);
				doDelete(ids);
				
			} else
			{
				List<Integer> ids = getSelectedIds();
				System.out.println(ids);
				if(ids.toString().equals("[]")){
					Toast.makeText(getActivity(), "请先选择所需购买的书籍~", Toast.LENGTH_LONG).show();
				}
				else{
					List<HashMap<String, Object>> blist=doSubmit(ids);
					System.out.println("lalalalal"+blist);
					Intent intent=new Intent(getActivity(),confirmOrderActivity.class);
					intent.putExtra("blist", (Serializable)blist);  
					startActivityForResult(intent,12);
				}
			}
       }
        }
    }//class click
}//class shopcartAdapter

}//class fragmentcart


