package com.example.zidingyi;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		View view =View.inflate(MainActivity.this, R.layout.actionbar_port_layout, null);
		actionBar.setCustomView(view);
//		setActionBarLayout( R.layout.actionbar_port_layout );
//44
	}
	public void onClick( View v ){
		switch( v.getId( ) ){
			case R.id.menuBtnId:{
				showToast( this, "menuBtn" );
			}
			break;
			case R.id.noteBtnId:{
				showToast( this, "noteBtn" );
			}
			break;
			case R.id.downloadBtnId:{
				showToast( this, "downloadBtn" );
			}
			break;
			case R.id.editBtnId:{
				showToast( this, "editBtn" );
			}
			break;
			default:{
				
			}
			break;
		}
	}
	
	/**
	 * 设置ActionBar的布局
	 * @param layoutId 布局Id
	 * 
	 * */
	public void setActionBarLayout( int layoutId ){
		ActionBar actionBar = getActionBar( );
		if( null != actionBar ){
			actionBar.setDisplayShowHomeEnabled( false );
			actionBar.setDisplayShowCustomEnabled(true);
			LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(layoutId, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			actionBar.setCustomView(v,layout);
//			actionBar.setCustomView(v);
		}
	}
	
	/**
	 * 显示提示信息
	 * @param context
	 * @param msg 提示信息
	 * 
	 * */
	private void showToast( Context context, String msg ){
		if( null == context || TextUtils.isEmpty( msg ) ){
			return;
		}
		
		Toast.makeText( context, msg, Toast.LENGTH_LONG ).show( );
	}
}
