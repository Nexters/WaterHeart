package nexters.waterheart;

import java.util.Random;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	private static Activity activity;
	private static String s;

	public AlarmReceiver() {
		super();
	}

	public AlarmReceiver(Activity activity, String s) {
		this.activity = activity;
		this.s = s;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		HeartManager heartManager = new HeartManager(activity);
		heartManager.heartReset();

		//if (intent.getAction().equals("FAIL")) {
			NotificationManager notifier = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);

			Notification notify = new Notification(R.drawable.actionbar_logo,
					"오늘이 5분 남았어요!!", System.currentTimeMillis());

			Intent intent2 = new Intent(context, MainActivity.class);
			PendingIntent pender = PendingIntent.getActivity(context, 0,
					intent2, 0);

			notify.setLatestEventInfo(context, "Water Heart", "오늘이 5분 남았어요!! 오늘의 달성도를 볼까요?", pender);

			notify.flags |= Notification.FLAG_AUTO_CANCEL;
			notify.vibrate = new long[] { 200, 100, 500, 300 };
			// notify.sound=Uri.parse("file:/");
			notify.number++;

			notifier.notify(1, notify);
		//}
	}

}
