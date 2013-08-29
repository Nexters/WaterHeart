package nexters.waterheart;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	private static Activity activity;

	public AlarmReceiver() {
		super();
	}

	public AlarmReceiver(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		HeartManager heartManager = new HeartManager(activity);
		heartManager.heartReset();
	}

}
