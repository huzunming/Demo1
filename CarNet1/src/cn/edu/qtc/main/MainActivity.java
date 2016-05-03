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

	// ********��ͼ���********
	// ��ͼ�������
	private BaiduMap baiduMap;
	private TextureMapView mapView;

	// ��ͼ�ϵİ�ť
	private ImageView locatonImg;
	// ��λ��
	private Location location;
	// ·�߹滮
	private LinearLayout routePlan_start_ll;
	private LinearLayout routePlan_end_ll;

	private TextView routePlan_start_tv;
	private TextView routePlan_end_tv;
	private int START = 1;
	private int END = 2;

	private String nowCity;
	// *******�໬���**********
	private SlidingMenu slidingMenu;
	private MusicMenu musicMenu;
	// ���ֲ������
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
		// ���ص�ͼ
		initMapView();
		// ���ز໬
		initSlidingMeun();
		// ��ʼ�����ַ���
		initMusicPlayer();
	}

	// ��ʼ�����ַ���
	private void initMusicPlayer() {
		shared = MyShared.getShared(MainActivity.this);
		int musicPlayMode = shared.getInt("music_play_mode", 0);

		sc = new MyServiceConnection();
		bindService(new Intent(MainActivity.this, MusicService.class), sc,
				BIND_AUTO_CREATE);
		musicMenu = (MusicMenu) findViewById(R.id.music_menu);
		switch (musicPlayMode) {
		case 0:// ˳��
				// btnMusicPlayMode.setText("˳�򲥷�");
			musicMenu.updateIcon(4, "shunxu");
			break;
		case 1:// ���
				// btnMusicPlayMode.setText("�������");
			musicMenu.updateIcon(4, "suiji");
			break;
		case 2:// ����
				// btnMusicPlayMode.setText("����ѭ��");
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
						// btnPlay.setText("����");
						musicMenu.updateIcon(3, "bofang");
					} else {
						isPlay = mc.play();
						// btnPlay.setText("��ͣ");
						musicMenu.updateIcon(3, "zanting");
					}
					break;
				case 4:
					switch (currentMusicPlayMode) {
					case 0:
						mc.order(1);// 15092257595
						currentMusicPlayMode = 1;
						// btnMusicPlayMode.setText("�������");
						musicMenu.updateIcon(4, "suiji");
						break;
					case 1:
						mc.order(2);// 15092257595
						currentMusicPlayMode = 2;
						// btnMusicPlayMode.setText("��������");
						musicMenu.updateIcon(4, "danqu");
						break;
					case 2:
						mc.order(0);// 15092257595
						currentMusicPlayMode = 0;
						// btnMusicPlayMode.setText("˳��ѭ��");
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
	 * ��ʼ���໬
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
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// �������󻬻����һ���
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// ���ò˵����
		slidingMenu.setFadeDegree(0.35f);// ���õ��뵭���ı���
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);// ��������ģʽ
		// slidingMenu
		// .setShadowDrawable(R.drawable.bitmap_book_read_chapterlist_repeat);//
		// ������˵���ӰͼƬ
		slidingMenu.setFadeEnabled(true);// ���û���ʱ�˵����Ƿ��뵭��
		slidingMenu.setBehindScrollScale(0.65f);// ���û���ʱ��קЧ��

		slidingMenu.setSecondaryMenu(R.layout.menu_frame_right);
		FragmentTransaction rightFragementTransaction = getSupportFragmentManager()
				.beginTransaction();
		Fragment rightFrag = new RightFragment();
		leftFragementTransaction.replace(R.id.menu_frame_two, rightFrag);
		rightFragementTransaction.commit();
	}

	/**
	 * ��ʼ����ͼ
	 */
	private void initMapView() {
		// ----------------------------------������ͼ-------------------------------------
		// ��ʼ��������ͼ
		mapView = (TextureMapView) findViewById(R.id.mTexturemap);
		baiduMap = mapView.getMap();
		// ���ż���
		// ����logo
		View child = mapView.getChildAt(1);
		if (child != null
				&& (child instanceof ImageView || child instanceof ZoomControls)) {
			child.setVisibility(View.INVISIBLE);
		}
		// ��ͼ�ϱ�����
		mapView.showScaleControl(false);
		// �������ſؼ���λ��
		baiduMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {

			@Override
			public void onMapLoaded() {
				// TODO Auto-generated method stub
				mapView.setZoomControlsPosition(new Point(925, 800));
			}
		});

		// ������ͨͼ
		baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// ���ý�ͨͼ
		baiduMap.setTrafficEnabled(true);

		// ----------------------------------������ͼ-----------------------------------

		// ------------------------------------��λ-------------------------------
		// ��ʼ����λ
		location = new Location(mapView, MainActivity.this);
		// ������λ
		location.startClient(true);

		// ------------------------------------��λ-------------------------------
		//
		// //
		// ----------------------------------·�߹滮-------------------------------
		// // ��ȡ·�߹滮�Ŀտؼ�
		// routePlan_end_tv = (TextView) findViewById(R.id.mian_route_end_tv);
		// routePlan_start_tv = (TextView)
		// findViewById(R.id.mian_route_start_tv);

		// ----------------------------------·�߹滮--------------------------------

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// ��໬��ť
		case R.id.title_bar_menu_btn_left:
			slidingMenu.showMenu();

			break;
		// �Ҳ໬��ť
		case R.id.title_bar_menu_btn_right:
			slidingMenu.showSecondaryMenu(true);

			break;
		// ��λ��ť
		case R.id.main_location_img:
			location.startClient(true);
			// overlaySearch.SearchNearBy();

			break;
		// ·�߹滮��ʼLinearLayout��ť
		case R.id.main_routeplan_start_ll:
			// ��ת����ѯ����
			// Intent intentStart = new Intent(MainActivity.this,
			// SearchActivity.class);
			// if (location.nowCity != null) {
			// intentStart.putExtra("city", location.nowCity);
			// intentStart.putExtra("type", START + "");
			// } else {
			// intentStart.putExtra("city", "��λʧ��");
			// intentStart.putExtra("type", START + "");
			// }
			// startActivityForResult(intentStart, START);
			break;
		// ·�߹滮����LinearLayout��ť
		case R.id.main_routeplan_end_ll:
			// ��ת����ѯ����
			// Intent intentEnd = new Intent(MainActivity.this,
			// SearchActivity.class);
			// if (location.nowCity != null) {
			// intentEnd.putExtra("city", location.nowCity);
			// intentEnd.putExtra("type", END + "");
			// } else {
			// intentEnd.putExtra("city", "��λʧ��");
			// intentEnd.putExtra("type", END + "");
			// }
			// startActivityForResult(intentEnd, END);

			break;

		// ��������յ���Ϣ
		case R.id.main_route_change:
			if (routePlan_start_tv.getText().toString().equals("��ǰλ��")
					&& routePlan_end_tv.getText().toString().equals("��Ҫȥ�Ķ�")) {
				// ��������Ϣ

			} else {
				// ������Ϣ
				String endInfo = routePlan_end_tv.getText().toString();
				String startInfo = routePlan_start_tv.getText().toString();

				routePlan_start_tv.setText(endInfo);
				routePlan_end_tv.setText(startInfo);
			}

			break;
		// �滮·��
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
		// �������
		shared.edit().putInt("music_play_mode", currentMusicPlayMode).commit();
		if (sc != null) {
			unbindService(sc);
		}
		// ����Ϊ����������
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
