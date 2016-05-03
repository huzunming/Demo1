package cn.edu.qtc.music;

import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
	private static final String TAG = "MusicService";
	private MediaPlayer mediaPlayer;
	private List<MusicInfo> musicList;
	private int currentMusicId = 0;
	private int musicPlayMode;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				musicList = (List<MusicInfo>) msg.obj;
				initPlay();
			}
		};
	};

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new MyBinder();

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		GetMusicListThread getMusicListThread = new GetMusicListThread(handler,
				MusicService.this);
		getMusicListThread.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (mediaPlayer != null) {
			mediaPlayer.release();
		}
		super.onDestroy();
	}


	public String retuMusic() {
		String url = null;
		switch (musicPlayMode) {
		case 0:// Ë³Ðò
			MusicInfo musicInfo = musicList.get(currentMusicId);
			url = musicInfo.getUrl();

			break;
		case 1:// Ëæ»ú
			Random random = new Random();
			int randomInt = random.nextInt(musicList.size());
			currentMusicId = randomInt;
			MusicInfo musicInfo1 = musicList.get(currentMusicId);
			url = musicInfo1.getUrl();
			break;
		case 2:// µ¥Çú
			MusicInfo musicInfo2 = musicList.get(currentMusicId);
			url = musicInfo2.getUrl();
			break;
		default:
			break;
		}
		return url;
	}

	public void initPlay() {

		String url = retuMusic();
		if (mediaPlayer != null) {
			mediaPlayer.release();
		}
		try {
			mediaPlayer = MediaPlayer.create(MusicService.this, Uri.parse(url));
			mediaPlayer.start();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	class MyBinder extends Binder implements MiddleClass {

		@Override
		public boolean play() {
			if (mediaPlayer.isPlaying()) {

			} else {
				mediaPlayer.start();
			}
			if (mediaPlayer.isPlaying()) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean pause() {
			// TODO Auto-generated method stub
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
			} else {

			}
			if (mediaPlayer.isPlaying()) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean last() {
			// TODO Auto-generated method stub
			int temp = currentMusicId;
			temp--;
			if (temp < 0) {
				return false;
			} else {
				if (musicPlayMode == 2) {
					initPlay();
					return false;
				}
				currentMusicId = temp;
				initPlay();
				return false;
			}

		}

		@Override
		public boolean next() {
			// TODO Auto-generated method stub
			int temp = currentMusicId;
			temp++;
			if (currentMusicId > musicList.size()) {
				return false;
			} else {
				if (musicPlayMode == 2) {
					initPlay();
					return false;
				} else {
					currentMusicId = temp;
					initPlay();
					return false;
				}

			}

		}

		@Override
		public boolean order(int id) {
			// TODO Auto-generated method stub
			musicPlayMode = id;
			return false;
		}

		@Override
		public boolean isPlaying() {
			// TODO Auto-generated method stub
			if (mediaPlayer != null) {
				return mediaPlayer.isPlaying();
			} else {
				return false;
			}
		}

	}

}
