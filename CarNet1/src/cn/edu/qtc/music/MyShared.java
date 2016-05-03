package cn.edu.qtc.music;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MyShared {
	public static SharedPreferences getShared(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"seting", Activity.MODE_PRIVATE);
		return sharedPreferences;

	}

}
