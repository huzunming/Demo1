//package cn.edu.qtc.activity;
//
//import java.util.List;
//
//import com.baidu.mapapi.BMapManager;
//import com.baidu.mapapi.MKGeneralListener;
//import com.baidu.mapapi.map.MKEvent;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//import cn.edu.qtc.main.R;
//import cn.edu.qtc.map.FuzzySearch;
//
//public class SearchActivity extends Activity implements OnClickListener {
//
//	private EditText search_edit;
//	private TextView location_tv;
//	private ListView itemList;
//	private ArrayAdapter<String> adapter;
//	private List<String> itemGroup;
//	// 找寻的类型
//	private int START = 1;
//	private int END = 2;
//	private String type;
//	// 模糊查询
//	private BMapManager manager;
//	private String key = "1A4A4ABEFBEECD8C17DEE880C4EA69B9607020B5";
//	private FuzzySearch fuzzySearch;
//
//	private Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			switch (msg.what) {
//			case 0:
//				// 获取数据完成，添加适配器
//				itemGroup = fuzzySearch.getData();
//				adapter = new ArrayAdapter<String>(SearchActivity.this,
//						android.R.layout.simple_list_item_1, itemGroup);
//				itemList.setAdapter(adapter);
//				break;
//
//			default:
//				break;
//			}
//		};
//	};
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		// 获取Manager
//		checkMapKey();
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_search);
//
//		init();
//		listener();
//
//	}
//
//	/**
//	 * 输入框，列表监听
//	 */
//	private void listener() {
//
//		itemList.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1,
//					int location, long arg3) {
//
//				Intent i = new Intent();
//				i.putExtra("value", itemGroup.get(location));
//
//				if (type.equals(START + "")) {
//					setResult(START, i);
//				} else {
//					setResult(END, i);
//				}
//
//				SearchActivity.this.finish();
//
//			}
//		});
//
//	}
//
//	private void init() {
//		search_edit = (EditText) findViewById(R.id.search_topbar_edittext);
//		location_tv = (TextView) findViewById(R.id.search_topbar_location_tv);
//		itemList = (ListView) findViewById(R.id.search_listview);
//		search_edit.addTextChangedListener(new MyEditTextListener());
//		getData();
//
//	}
//
//	/**
//	 * 获取主界面传来的数据
//	 */
//	private void getData() {
//		Intent intent = getIntent();
//		String city = intent.getStringExtra("city");
//		type = intent.getStringExtra("type");
//		location_tv.setText(city);
//
//	}
//
//	@Override
//	public void onClick(View v) {
//
//		switch (v.getId()) {
//		case R.id.search_topbar_back_ll:
//			Intent i = new Intent();
//			i.putExtra("value", "");
//			if (type.equals(START + "")) {
//				setResult(START, i);
//			} else {
//				setResult(END, i);
//			}
//			SearchActivity.this.finish();
//			break;
//		case R.id.search_topbar_location_ll:
//
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//	private void checkMapKey() {
//		manager = new BMapManager(getApplicationContext());
//		manager.init(key, new MKGeneralListener() {
//
//			@Override
//			public void onGetPermissionState(int errorCode) {
//				// key验证失败
//				if (errorCode == MKEvent.ERROR_PERMISSION_DENIED) {
//					Toast.makeText(getApplicationContext(), "Key值验证失败", 0)
//							.show();
//				}
//
//			}
//
//			@Override
//			public void onGetNetworkState(int errorCode) {
//				// 网络异常
//				if (errorCode == MKEvent.ERROR_NETWORK_CONNECT) {
//					Toast.makeText(getApplicationContext(), "网络连接失败", 0).show();
//				}
//
//			}
//		});
//	}
//
//	/**
//	 * 监听输入框的变化
//	 * 
//	 * @author linlin
//	 * 
//	 */
//	private class MyEditTextListener implements TextWatcher {
//
//		@Override
//		public void afterTextChanged(Editable arg0) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//				int arg3) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onTextChanged(CharSequence text, int arg1, int arg2,
//				int arg3) {
//			String value = search_edit.getText().toString();
//			if (value.equals("")) {
//
//			} else {
//				fuzzySearch = new FuzzySearch(manager, SearchActivity.this,
//						handler);
//				fuzzySearch.SearchKey(value, location_tv.getText().toString());
//
//			}
//
//			// 添加适配器
//
//		}
//
//	}
//
//}
