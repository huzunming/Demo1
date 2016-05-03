package cn.edu.qtc.main;

import cn.edu.qtc.map.Location;
import cn.edu.qtc.music.MiddleClass;
import cn.edu.qtc.music.MusicService;
import cn.edu.qtc.music.MyShared;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.TextureMapView;

import cn.edu.qtc.activity.MusicMenu;
import cn.edu.qtc.activity.MusicMenu.OnMenuItemClickListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;
import android.widget.ZoomControls;

public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener {

	// ********地图相关********
	// 地图基本组件
	private BaiduMap baiduMap;
	private TextureMapView mapView;

	// 地图上的按钮
	private ImageView locatonImg;
	// 定位类
	private Location location;
	// 路线规划
	private LinearLayout routePlan_start_ll;
	private LinearLayout routePlan_end_ll;

	private TextView routePlan_start_tv;
	private TextView routePlan_end_tv;
	private int START = 1;
	private int END = 2;

	private String nowCity;
	// *******侧滑相关**********
	private SlidingMenu slidingMenu;
	private MusicMenu musicMenu;
	// 音乐播放相关
	private MyServiceConnection sc;
	private MiddleClass mc;
	private boolean isPlay = false;
	private int currentMusicPlayMode;
	private SharedPreferences shared;

	//

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		// 加载地图
		initMapView();
		// 加载侧滑
		initSlidingMeun();
		// 初始化音乐服务
		initMusicPlayer();
	}

	// 初始化音乐服务
	private void initMusicPlayer() {
		shared = MyShared.getShared(MainActivity.this);
		int musicPlayMode = shared.getInt("music_play_mode", 0);

		sc = new MyServiceConnection();
		bindService(new Intent(MainActivity.this, MusicService.class), sc,
				BIND_AUTO_CREATE);
		musicMenu = (MusicMenu) findViewById(R.id.music_menu);
		switch (musicPlayMode) {
		case 0:// 顺序
				// btnMusicPlayMode.setText("顺序播放");
			musicMenu.updateIcon(4, "shunxu");
			break;
		case 1:// 随机
				// btnMusicPlayMode.setText("随机播放");
			musicMenu.updateIcon(4, "suiji");
			break;
		case 2:// 单曲
				// btnMusicPlayMode.setText("单曲循环");
			musicMenu.updateIcon(4, "danqu");
			break;
		default:
			break;
		}
		musicMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public void onClick(View view, int pos) {
				// TODO Auto-generated method stub
				switch (pos) {
				case 1:
					mc.next();
					isPlay = true;
					musicMenu.updateIcon(3, "zanting");
					break;
				case 2:
					mc.last();
					isPlay = true;
					musicMenu.updateIcon(3, "zanting");
					break;
				case 3:
					if (mc.isPlaying()) {
						isPlay = mc.pause();
						// btnPlay.setText("播放");
						musicMenu.updateIcon(3, "bofang");
					} else {
						isPlay = mc.play();
						// btnPlay.setText("暂停");
						musicMenu.updateIcon(3, "zanting");
					}
					break;
				case 4:
					switch (currentMusicPlayMode) {
					case 0:
						mc.order(1);// 15092257595
						currentMusicPlayMode = 1;
						// btnMusicPlayMode.setText("随机播放");
						musicMenu.updateIcon(4, "suiji");
						break;
					case 1:
						mc.order(2);// 15092257595
						currentMusicPlayMode = 2;
						// btnMusicPlayMode.setText("单曲播放");
						musicMenu.updateIcon(4, "danqu");
						break;
					case 2:
						mc.order(0);// 15092257595
						currentMusicPlayMode = 0;
						// btnMusicPlayMode.setText("顺序循环");
						musicMenu.updateIcon(4, "shunxu");
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * 初始化侧滑
	 */
	private void initSlidingMeun() {
		setBehindContentView(R.layout.menu_frame_left);
		FragmentTransaction leftFragementTransaction = getSupportFragmentManager()
				.beginTransaction();
		Fragment fragmenLeft = new LeftFragment();
		leftFragementTransaction.replace(R.id.menu_frame, fragmenLeft);
		leftFragementTransaction.commit();
		// customize the SlidingMenu
		slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// 设置是左滑还是右滑，
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 设置菜单宽度
		slidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);// 设置手势模式
		// slidingMenu
		// .setShadowDrawable(R.drawable.bitmap_book_read_chapterlist_repeat);//
		// 设置左菜单阴影图片
		slidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		slidingMenu.setBehindScrollScale(0.65f);// 设置滑动时拖拽效果

		slidingMenu.setSecondaryMenu(R.layout.menu_frame_right);
		FragmentTransaction rightFragementTransaction = getSupportFragmentManager()
				.beginTransaction();
		Fragment rightFrag = new RightFragment();
		leftFragementTransaction.replace(R.id.menu_frame_two, rightFrag);
		rightFragementTransaction.commit();
	}

	/**
	 * 初始化地图
	 */
	private void initMapView() {
		// ----------------------------------基础地图-------------------------------------
		// 初始化基础地图
		mapView = (TextureMapView) findViewById(R.id.mTexturemap);
		baiduMap = mapView.getMap();
		// 缩放级别
		// 隐藏logo
		View child = mapView.getChildAt(1);
		if (child != null
				&& (child instanceof ImageView || child instanceof ZoomControls)) {
			child.setVisibility(View.INVISIBLE);
		}
		// 地图上比例尺
		mapView.showScaleControl(false);
		// 设置缩放控件的位置
		baiduMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {

			@Override
			public void onMapLoaded() {
				// TODO Auto-generated method stub
				mapView.setZoomControlsPosition(new Point(925, 800));
			}
		});

		// 设置普通图
		baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 设置交通图
		baiduMap.setTrafficEnabled(true);

		// ----------------------------------基础地图-----------------------------------

		// ------------------------------------定位-------------------------------
		// 初始化定位
		location = new Location(mapView, MainActivity.this);
		// 开启定位
		location.startClient(true);

		// ------------------------------------定位-------------------------------
		//
		// //
		// ----------------------------------路线规划-------------------------------
		// // 获取路线规划的空控件
		// routePlan_end_tv = (TextView) findViewById(R.id.mian_route_end_tv);
		// routePlan_start_tv = (TextView)
		// findViewById(R.id.mian_route_start_tv);

		// ----------------------------------路线规划--------------------------------

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 左侧滑按钮
		case R.id.title_bar_menu_btn_left:
			slidingMenu.showMenu();

			break;
		// 右侧滑按钮
		case R.id.title_bar_menu_btn_right:
			slidingMenu.showSecondaryMenu(true);

			break;
		// 定位按钮
		case R.id.main_location_img:
			location.startClient(true);
			// overlaySearch.SearchNearBy();

			break;
		// 路线规划开始LinearLayout按钮
		case R.id.main_routeplan_start_ll:
			// 跳转到查询界面
			// Intent intentStart = new Intent(MainActivity.this,
			// SearchActivity.class);
			// if (location.nowCity != null) {
			// intentStart.putExtra("city", location.nowCity);
			// intentStart.putExtra("type", START + "");
			// } else {
			// intentStart.putExtra("city", "定位失败");
			// intentStart.putExtra("type", START + "");
			// }
			// startActivityForResult(intentStart, START);
			break;
		// 路线规划结束LinearLayout按钮
		case R.id.main_routeplan_end_ll:
			// 跳转到查询界面
			// Intent intentEnd = new Intent(MainActivity.this,
			// SearchActivity.class);
			// if (location.nowCity != null) {
			// intentEnd.putExtra("city", location.nowCity);
			// intentEnd.putExtra("type", END + "");
			// } else {
			// intentEnd.putExtra("city", "定位失败");
			// intentEnd.putExtra("type", END + "");
			// }
			// startActivityForResult(intentEnd, END);

			break;

		// 交换起点终点信息
		case R.id.main_route_change:
			if (routePlan_start_tv.getText().toString().equals("当前位置")
					&& routePlan_end_tv.getText().toString().equals("你要去哪儿")) {
				// 不交换信息

			} else {
				// 交换信息
				String endInfo = routePlan_end_tv.getText().toString();
				String startInfo = routePlan_start_tv.getText().toString();

				routePlan_start_tv.setText(endInfo);
				routePlan_end_tv.setText(startInfo);
			}

			break;
		// 规划路线
		case R.id.main_route_plan:

			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == START) {
			if (!data.getStringExtra("value").equals("")) {
				routePlan_start_tv.setText(data.getStringExtra("value"));
			}

		}

		else if (requestCode == END) {
			if (!data.getStringExtra("value").equals("")) {
				routePlan_end_tv.setText(data.getStringExtra("value"));
			}

		}

	}

	@Override
	protected void onPause() {
		Log.d("MainActivity", "onPause");

		mapView.onPause();
		location.stopClient();
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.d("MainActivity", "onResume");
		mapView.onResume();
		location.startClient(false);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mapView.onDestroy();
		// 音乐相关
		shared.edit().putInt("music_play_mode", currentMusicPlayMode).commit();
		if (sc != null) {
			unbindService(sc);
		}
		// 以上为音乐相关组件
		super.onDestroy();
	}

	class MyServiceConnection implements ServiceConnection {

		private static final String TAG = "MyServiceConnection";

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub

			mc = (MiddleClass) arg1;
			if (mc == null) {
			}
			if (mc != null) {
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub

		}

	}
}
