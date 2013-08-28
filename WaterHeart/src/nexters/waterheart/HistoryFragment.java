package nexters.waterheart;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import nexters.waterheart.dto.Write;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.nineoldandroids.view.ViewHelper;

public class HistoryFragment extends SherlockFragment {
	private static final int TUTORIAL_NUMBER = 2;
	private static final int ONCLICK_NUM = 1;
	ImageView[] heart = new ImageView[6];
	TextView[] text01 = new TextView[6];
	TextView[] text02 = new TextView[6];
	ImageView[] ml = new ImageView[6];
	ClickManager clickManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * setHasOptionsMenu(true)가 지정되어야만 fragment내에서 액션바 메뉴를 설정할 수 있다
		 */
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.historyview, container, false);
		clickManager = new ClickManager(ONCLICK_NUM, getActivity(), null);
		/*
		 * 나중에 할 것: 위 percent* 변수들에 DB의 값을 불러와서 대입한다.
		 */
		return view;
	}

	public void onResume() {
		init();
		super.onResume();
	}

	public void init() {
		// int id1 = R.id.resultspage_text_1_02;
		// int id2 = R.id.resultspage_text_2_02;
		// int id3 = R.id.resultspage_text_3_02;
		// int id4 = R.id.resultspage_text_4_02;
		// int id5 = R.id.resultspage_text_5_02;
		// int id6 = R.id.resultspage_text_6_02;
		// String log = String.valueOf(id1) + " " + String.valueOf(id2) + " "
		// + String.valueOf(id3) + " " + String.valueOf(id4) + " "
		// + String.valueOf(id5) + " " + String.valueOf(id6);
		// Log.d("log", log);
		// 2131034196 2131034191 2131034186 2131034181 2131034176 2131034171

		HeartManager heartManager = new HeartManager(getActivity());
		List<Write> writes = heartManager.onHistoryPage();

		if (heart[0] == null) {
			for (int i = 0; i < 6; i++) {
				heart[i] = (ImageView) getActivity().findViewById(
						R.id.history_heart_01 - i * 5);
				text01[i] = (TextView) getActivity().findViewById(
						R.id.resultspage_text_1_01 - i * 5);
				text02[i] = (TextView) getActivity().findViewById(
						R.id.resultspage_text_1_02 - i * 5);

				heart[i].setVisibility(android.view.View.INVISIBLE);
				text01[i].setVisibility(android.view.View.INVISIBLE);
				text02[i].setVisibility(android.view.View.INVISIBLE);

				heart[i].setOnClickListener(clickManager);
			}
		}
		for (Write cn : writes) {
			String log = "No: " + cn.getNo() + " ,water: " + cn.getWater()
					+ ", date: " + cn.getDate() + ", complete: "
					+ cn.getComplete();
			Log.d("Writes: ", log);
		}
		showHeart(writes);
	}

	public void showHeart(List<Write> writes) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar(Locale.KOREA);

		int total = 0;
		int index = 5;
		float percent = 0;
		calendar.add(Calendar.DATE, -1);
		date = calendar.getTime();

		for (Write w : writes) {
			heart[index].setVisibility(android.view.View.VISIBLE);
			text01[index].setVisibility(android.view.View.VISIBLE);
			text02[index].setVisibility(android.view.View.VISIBLE);

			total = Integer.parseInt(w.getWater());
			percent = (float) total / MainFragment.totalWater;
			
			text01[index].setText(String.valueOf((int) (percent * 100)));
			text02[index].setText(String.valueOf(total));

			if (percent >= 0 && percent < 0.1)
				ViewHelper.setAlpha(heart[index], 0.2f);	
			else if (percent >= 0.1 && percent < 0.2)
				ViewHelper.setAlpha(heart[index], 0.25f);	
			else if (percent >= 0.2 && percent < 0.3)
				ViewHelper.setAlpha(heart[index], 0.3f);	
			else if (percent >= 0.3 && percent < 0.4)
				ViewHelper.setAlpha(heart[index], 0.35f);	
			else if (percent >= 0.4 && percent < 0.5)
				ViewHelper.setAlpha(heart[index], 0.4f);	
			else if (percent >= 0.5 && percent < 0.6)
				ViewHelper.setAlpha(heart[index], 0.45f);	
			else if (percent >= 0.6 && percent < 0.7)
				ViewHelper.setAlpha(heart[index], 0.5f);	
			else if (percent >= 0.7 && percent < 0.8)
				ViewHelper.setAlpha(heart[index], 0.55f);	
			else if (percent >= 0.8 && percent < 0.9)
				ViewHelper.setAlpha(heart[index], 0.6f);	
			else if (percent >= 0.9 && percent < 1)
				ViewHelper.setAlpha(heart[index], 0.65f);	
			else if (percent == 1)
				ViewHelper.setAlpha(heart[index], 0.7f);	
			date = calendar.getTime();
			index--;

			if (index == -1)
				break;
			calendar.add(Calendar.DATE, -1);

		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		/*
		 * saveFragmentBasicState nullpointerexception 에러 방지용....
		 */
		outState.putString("Don't crash", "Please");
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.history, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		return super.onOptionsItemSelected(item);
	}

}
