package nexters.waterheart;

import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	private String s = null;
	boolean success = true;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			Log.e("WTFDUDE", "Boot completed");
			alarmThis(context);
		}
		else{
			letsDoThis(context, intent);
		}
	}
	
	public void alarmThis(Context context){
		Random r = new Random();
		int num = r.nextInt(3) + 1;
		switch (num) {
		case 1:
			s = "내일은 잘 할 수 있죠?";
			break;
		case 2:
			s = "오늘은 실패했지만 내일은 성공해요~!";
			break;
		case 3:
			s = "물 마시는 습관 만들기가 쉽지 않죠? 그래도 화이팅!";
			break;
		}
		
		final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("ALARM");
		intent.putExtra("s", s);
		
		final PendingIntent sender = PendingIntent
				.getBroadcast(context, 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
	}
	
	public void letsDoThis(Context context, Intent intent){
		HeartManager heartManager = new HeartManager(context);
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
