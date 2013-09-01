package nexters.waterheart;

import java.util.Calendar;
import java.util.Random;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
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
	String s;
	AlarmReceiver mReceiver = null;
	HeartManager heartManager = null;
	private static final String FRAGMENT_TAG_CUPCUSTOM = "CUPCUSTOM";
	private static final String FRAGMENT_TAG_CUSTOM = "CUSTOM";
	private static final String FRAGMENT_TAG_CUSTOM02 = "CUSTOM02";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// heartManager.init();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	public void alarm(Context context) {
		Random r = new Random();
		int num = r.nextInt(3) + 1;
		// 스타틱이라 랜덤안됨
		switch (num) {
		case 1:
			s = "내일은 잘 할 수 있죠?";
		case 2:
			s = "오늘은 실패했지만 내일은 성공해요~!";
		case 3:
			s = "물 마시는 습관 만들기가 쉽지 않죠? 그래도 화이팅!";
		}
		mReceiver = new AlarmReceiver(MainActivity.this, s);
//		IntentFilter filler = new IntentFilter();
//		filler.addAction("ALARM");
//		filler.addAction("FAIL");
//		this.registerReceiver(mReceiver, filler);
		
		final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("ALARM");
//		if (heartManager.mainHeartShow() < MainFragment.totalWater)
//			intent.setAction("FAIL");
		final PendingIntent sender = PendingIntent.getBroadcast(
				MainActivity.this, 0, intent, 0);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 55);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		init();
		// alarmFiller(this);
		// ha();
		alarm(this);
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
		fragment01 = (CupCustomizingFragment)getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_CUPCUSTOM);
		fragment02 = (CustomFragment01)getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_CUSTOM);
		fragment03 = (CustomFragment02)getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_CUSTOM02);
		if((fragment01 == null) && (fragment02 == null) && (fragment03 == null)){
			new AlertDialog.Builder(this)
			.setTitle("정말 종료하시겠습니까?")
			.setMessage("종료하실꺼유? ㅠㅠ")
			.setNegativeButton("아니오", null)
			.setPositiveButton("네", new DialogInterface.OnClickListener(){
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			}).show();
		}else{
			super.onBackPressed();
		
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		//this.unregisterReceiver(mReceiver);
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
