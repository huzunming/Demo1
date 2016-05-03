package cn.edu.qtc.music;

import java.util.List;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GetMusicListThread extends Thread {
	private static final String TAG = "GetMusicListThread";
	private Context context;
	private List<MusicInfo> musicList;
	private Handler handler;

	public GetMusicListThread(Handler handler, Context context) {
		this.context = context;
		this.handler = handler;
	}
//
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		MusicLoader musicLoader = new MusicLoader();
		musicList = musicLoader.getMusicList(context);
		Message message =new Message();
		message.what=1;
		message.obj=musicList;
		handler.sendMessage(message);
		Log.i(TAG, musicList.toString());
	}

//10.42
}
