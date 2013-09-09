package nexters.waterheart;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	private static Activity activity;
	private String s = null;
	boolean success = true;
	
	public AlarmReceiver() {
		super();
	}

	public AlarmReceiver(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			Log.e("WTFDUDE", "Boot completed");
		}
		if(activity == null){
			//MainActivity main = new MainActivity();
			//activity = main.getThisFuckinActivity();
			//main.alarm();
		}else{
		HeartManager heartManager = new HeartManager(activity);
		if (heartManager.mainHeartShow() < MainFragment.totalWater)
			success = false;
		else
			success = true;
		heartManager.heartReset();
		s = intent.getStringExtra("s");
		
		
		if (success == false) {
			NotificationManager notifier = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);

			Notification notify = new Notification(R.drawable.actionbar_logo,
					"목표 달성 실패~", System.currentTimeMillis());

			Intent intent2 = new Intent(context, MainActivity.class);
			PendingIntent pender = PendingIntent.getActivity(context, 0,
					intent2, 0);

			notify.setLatestEventInfo(context, "Water Heart",
					s, pender);
			notify.flags |= Notification.FLAG_AUTO_CANCEL;
			notify.vibrate = new long[] { 200, 100, 500, 300 };
			// notify.sound=Uri.parse("file:/");
			notify.number++;

			notifier.notify(1, notify);
		}
		}

	}

}
