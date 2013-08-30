package nexters.waterheart;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

		final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		AlarmReceiver mReceiver = new AlarmReceiver(MainActivity.this);
		Intent intent = new Intent("ALARM");
		final PendingIntent sender = PendingIntent.getBroadcast(
				MainActivity.this, 0, intent, 0);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
	}

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
