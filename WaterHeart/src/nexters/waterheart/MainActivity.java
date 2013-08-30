package nexters.waterheart;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity {
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	String s;
	AlarmReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		alarm(MainActivity.this);
	}

	public void alarm(Context context) {
		Random r = new Random();
		int num = r.nextInt(3) + 1;
		//랜덤안됨
		switch (num) {
		case 1:
			s = "내일은 잘 할 수 있죠?";
		case 2:
			s = "오늘은 실패했지만 내일은 성공해요~!";
		case 3:
			s = "물 마시는 습관 만들기가 쉽지 않죠? 그래도 화이팅!";
		}
		mReceiver = new AlarmReceiver(MainActivity.this, s);
		final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent("ALARM");
		final PendingIntent sender = PendingIntent.getBroadcast(
				MainActivity.this, 0, intent, 0);

		Calendar calendar = new GregorianCalendar(Locale.KOREA);
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
	}

//	@Override
//	public void onResume() {
//		super.onResume();
//		Random r = new Random();
//		int num = r.nextInt(3) + 1;
//		switch (num) {
//		case 1:
//			s = "내일은 잘 할 수 있죠?";
//		case 2:
//			s = "오늘은 실패했지만 내일은 성공해요~!";
//		case 3:
//			s = "물 마시는 습관 만들기가 쉽지 않죠? 그래도 화이팅!";
//		}
//	}

//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		unregisterReceiver(mReceiver);
//	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			Fragment fragment = new Fragment();
			switch (position) {
			case 0:
				fragment = new MainFragment();
				break;
			case 1:
				fragment = new HistoryFragment();
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			if (position == 0)
				return "WaterHeart";
			else
				return "History";
		}
	}

}
