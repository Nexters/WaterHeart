package nexters.waterheart;

import java.util.Calendar;
import java.util.Random;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity {
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	String s;
	AlarmReceiver mReceiver = null;
	HeartManager heartManager = null;
	Fragment fragment;
	boolean isFinishing;
	private static MainActivity mainActivity;
	private static final String FRAGMENT_TAG_CUPCUSTOM = "CUPCUSTOM";
	private static final String FRAGMENT_TAG_CUSTOM = "CUSTOM";
	private static final String FRAGMENT_TAG_CUSTOM02 = "CUSTOM02";

	public MainActivity() {
		super();
	}

	public static MainActivity getThisFuckinActivity() {
		return mainActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
	}

	OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			HistoryFragment frag = (HistoryFragment) fragment;
			if (arg0 == 1) {
				frag.showAnimation();
			} else {
				for (int j = 0; j < 6; j++) {
					frag.heart[j].setVisibility(View.INVISIBLE);
					frag.text01[j].setVisibility(View.INVISIBLE);
				}
			}
		}

	};

	public void alarm() {
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

		final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("ALARM");
		intent.putExtra("s", s);

		final PendingIntent sender = PendingIntent
				.getBroadcast(MainActivity.this, 0, intent,
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

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		init();
		mainActivity = this;
		super.onResume();
	}

	public void init() {
		heartManager = new HeartManager(this);
		heartManager.init();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		CupCustomizingFragment fragment01 = null;
		CustomFragment01 fragment02 = null;
		CustomFragment02 fragment03 = null;
		fragment01 = (CupCustomizingFragment) getSupportFragmentManager()
				.findFragmentByTag(FRAGMENT_TAG_CUPCUSTOM);
		fragment02 = (CustomFragment01) getSupportFragmentManager()
				.findFragmentByTag(FRAGMENT_TAG_CUSTOM);
		fragment03 = (CustomFragment02) getSupportFragmentManager()
				.findFragmentByTag(FRAGMENT_TAG_CUSTOM02);
		if ((fragment01 == null) && (fragment02 == null)
				&& (fragment03 == null)) {
			if (isFinishing == false) {
				isFinishing = true;
				Toast.makeText(getApplicationContext(), "'뒤로'버튼을 한번 더 누르면 종료됩니다.",
						Toast.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable() {
					public void run() {
						isFinishing = false;
					}
				}, 2000);
			} else if (isFinishing == true)
				finish();

		} else {
			super.onBackPressed();

		}
	}

	@Override
	public void onPause() {
		alarm();
		mainActivity = null;
		super.onPause();
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			// fragment = new Fragment();
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
