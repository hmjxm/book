package com.example.testimageclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener{
	private FragmentManager fragmentManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RadioGroup group=(RadioGroup)findViewById(R.id.main_bottom_tabs);
		RadioButton main_home=(RadioButton)findViewById(R.id.main_home);
		RadioButton main_my=(RadioButton)findViewById(R.id.main_my);
		
		
		//在一个activity中使用另一个activity中的控件
		LayoutInflater factors = LayoutInflater.from(MainActivity.this);
		final View otherView = factors.inflate(R.layout.my_index, null);
		TextView logintxt=(TextView)otherView.findViewById(R.id.my_index_login_text);
		
		fragmentManager=getSupportFragmentManager();
		main_home.setChecked(true);
		group.setOnCheckedChangeListener(this);
		changeFragment(new FragmentHome(),false);
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.main_home:
			changeFragment(new FragmentHome(),true);
			break;
		case R.id.main_out:
			changeFragment(new FragmentOut(),true);
			break;
		case R.id.main_cart:
			changeFragment(new FragmentCart(),true);
			break;
		case R.id.main_my:
			changeFragment(new FragmentMy(),true);
			break;
		default:
			break;
		}
		
	}
	
	//切换不同的fragment{
	
	public void changeFragment(Fragment fragment,boolean isInit){
		FragmentTransaction transaction=fragmentManager.beginTransaction();
		transaction.replace(R.id.main_content, fragment);
		if(!isInit){
			transaction.addToBackStack(null);
		}
		transaction.commit();
		
	}
}



