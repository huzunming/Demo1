package cn.edu.qtc.activity;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.qtc.main.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MusicMenu extends RelativeLayout {
	private boolean isVisibility = false;
	private ImageView musicMenuBtnSubMenu1;
	private ImageView musicMenuBtnSubMenu2;
	private ImageView musicMenuBtnSubMenu3;
	private ImageView musicMenuBtnSubMenu4;
	private ImageView musicMenuBtn;
	private OnMenuItemClickListener mOnMenuItemClickListener;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == -1) {
				menuClose();
			}
		};
	};
	private Timer timer;
	private TimerTask timerTask;

	public interface OnMenuItemClickListener {
		void onClick(View view, int pos);
	}

	public MusicMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public MusicMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public MusicMenu(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		View view = View.inflate(getContext(), R.layout.music_menu, this);
		musicMenuBtn = (ImageView) view.findViewById(R.id.music_menu_btn);
		musicMenuBtnSubMenu1 = (ImageView) view
				.findViewById(R.id.music_menu_btn_one);
		musicMenuBtnSubMenu2 = (ImageView) view
				.findViewById(R.id.music_menu_btn_two);
		musicMenuBtnSubMenu3 = (ImageView) view
				.findViewById(R.id.music_menu_btn_three);
		musicMenuBtnSubMenu4 = (ImageView) view
				.findViewById(R.id.music_menu_btn_four);
		initOnClick();
		timerTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = -1;
				handler.sendMessage(message);
			}
		};
		timer = new Timer(true);
		timer.schedule(timerTask, 1000, 10000); // 延时1000ms后执行，1000ms执行一次
		// timer.cancel(); //退出计时器
	}
private void menuAutoCancel(){
	timerTask = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			message.what = -1;
			handler.sendMessage(message);
		}
	};
	timer = new Timer(true);
	timer.schedule(timerTask, 5000, 10000); // 延时1000ms后执行，1000ms执行一次
	// timer.cancel(); //退出计时器
}
	public void setOnMenuItemClickListener(
			OnMenuItemClickListener onMenuItemClickListener) {
		this.mOnMenuItemClickListener = onMenuItemClickListener;
	}

	private void initOnClick() {
		musicMenuBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Toast.makeText(getContext(), "is=" + isVisibility, 0).show();
				if (!isVisibility) {

					// menuClose();

					menuOpen();
					// Toast.makeText(getContext(), "open and is=" +
					// isVisibility,
					// 0).show();
				} else {
					menuClose();
					// Toast.makeText(getContext(), "close", 0).show();
				}
			}

		});

		musicMenuBtnSubMenu1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOnMenuItemClickListener.onClick(v, 1);
				timer.cancel();
				menuAutoCancel();
			}
		});
		musicMenuBtnSubMenu2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOnMenuItemClickListener.onClick(v, 2);
				timer.cancel();
				menuAutoCancel();
			}
		});
		musicMenuBtnSubMenu3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOnMenuItemClickListener.onClick(v, 3);
				timer.cancel();
				menuAutoCancel();
			}
		});
		musicMenuBtnSubMenu4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOnMenuItemClickListener.onClick(v, 4);
				timer.cancel();
				menuAutoCancel();
			}
		});
	}

	public void updateIcon(int id, String iconType) {
		switch (id) {
		case 1:

			break;
		case 2:

			break;
		case 3:
			if ("zanting".equals(iconType)) {
				musicMenuBtnSubMenu3.setImageResource(R.drawable.music_pause);
			} else {
				musicMenuBtnSubMenu3.setImageResource(R.drawable.music_play);
			}
			break;
		case 4:
			if ("shunxu".equals(iconType)) {
				musicMenuBtnSubMenu4
						.setImageResource(R.drawable.music_sequence);
			} else if ("danqu".equals(iconType)) {
				musicMenuBtnSubMenu4.setImageResource(R.drawable.music_single);
			} else {
				musicMenuBtnSubMenu4.setImageResource(R.drawable.music_random);
			}

			break;
		default:
			break;
		}
	}

	private void menuClose() {
		// TODO Auto-generated method stub

		TranslateAnimation translateAnimation1 = new TranslateAnimation(0, 0,
				0, 50);
		translateAnimation1.setDuration(500);
		translateAnimation1.setFillAfter(true);
		musicMenuBtnSubMenu1.startAnimation(translateAnimation1);

		TranslateAnimation translateAnimation2 = new TranslateAnimation(0, 0,
				0, 100);
		translateAnimation2.setDuration(500);
		translateAnimation2.setFillAfter(true);
		musicMenuBtnSubMenu2.startAnimation(translateAnimation2);

		TranslateAnimation translateAnimation3 = new TranslateAnimation(0, 0,
				0, 150);
		translateAnimation3.setDuration(500);
		translateAnimation3.setFillAfter(true);
		musicMenuBtnSubMenu3.startAnimation(translateAnimation3);

		TranslateAnimation translateAnimation4 = new TranslateAnimation(0, 0,
				0, 200);

		translateAnimation4.setDuration(500);
		translateAnimation4.setFillAfter(true);
		musicMenuBtnSubMenu4.startAnimation(translateAnimation4);
		musicMenuBtnSubMenu1.setVisibility(View.INVISIBLE);
		musicMenuBtnSubMenu2.setVisibility(View.INVISIBLE);
		musicMenuBtnSubMenu3.setVisibility(View.INVISIBLE);
		musicMenuBtnSubMenu4.setVisibility(View.INVISIBLE);
		musicMenuBtnSubMenu1.clearAnimation();
		musicMenuBtnSubMenu2.clearAnimation();
		musicMenuBtnSubMenu3.clearAnimation();
		musicMenuBtnSubMenu4.clearAnimation();
		isVisibility = false;
	}

	private void menuOpen() {
		musicMenuBtnSubMenu1.setVisibility(VISIBLE);
		musicMenuBtnSubMenu2.setVisibility(VISIBLE);
		musicMenuBtnSubMenu3.setVisibility(VISIBLE);
		musicMenuBtnSubMenu4.setVisibility(VISIBLE);
		isVisibility = true;
		TranslateAnimation translateAnimation1 = new TranslateAnimation(0, 0,
				150, 0);
		translateAnimation1.setDuration(500);
		translateAnimation1.setFillAfter(true);
		musicMenuBtnSubMenu1.startAnimation(translateAnimation1);

		TranslateAnimation translateAnimation2 = new TranslateAnimation(0, 0,
				250, 0);
		translateAnimation2.setDuration(500);
		translateAnimation2.setFillAfter(true);
		musicMenuBtnSubMenu2.startAnimation(translateAnimation2);

		TranslateAnimation translateAnimation3 = new TranslateAnimation(0, 0,
				350, 0);
		translateAnimation3.setDuration(500);
		translateAnimation3.setFillAfter(true);
		musicMenuBtnSubMenu3.startAnimation(translateAnimation3);

		TranslateAnimation translateAnimation4 = new TranslateAnimation(0, 0,
				450, 0);
		translateAnimation4.setDuration(500);
		translateAnimation4.setFillAfter(true);
		musicMenuBtnSubMenu4.startAnimation(translateAnimation4);
	}
}
