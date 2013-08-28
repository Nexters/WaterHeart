package nexters.waterheart;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	Activity activity;
	public AlarmReceiver(Activity activity) {
		this.activity = activity;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//Log.d("activity", String.valueOf(activity));
		HeartManager heartManager = new HeartManager(activity);
		heartManager.mainHeartShow();
		
	}

}
