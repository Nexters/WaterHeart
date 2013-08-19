package nexters.waterheart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.nineoldandroids.view.ViewHelper;

public class HistoryFragment extends SherlockFragment {
	private static final int TUTORIAL_NUMBER = 2;
	private static final int ONCLICK_NUM = 1;
	ImageView[] heart = new ImageView[6];
	float[] percent = new float[6];
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
		if (heart[0] == null) {
			for (int i = 0; i < 6; i++) {
				heart[i] = (ImageView) getActivity().findViewById(
						R.id.history_heart_01 + i);		
				//heart[i].setOnClickListener(clickManager);

				//setImage(heart[i], percent[i]);

		
			}
		}
		//heart[0] = (ImageView)getActivity().findViewById(R.id.history_heart_1);
		//heart[1] = (ImageView)getActivity().findViewById(R.id.history_heart_2);
	}

	public void setImage(ImageView img, float percent) {
		/*
		 * 처음에는 모든 이미지들이 invisible 상태일것이다 아마도..DB에서 날짜정보같은걸 가져와서.. 조건을 만족하면
		 * VISIBLE로 바꾸고..할듯
		 */
		if (percent <= 10)
			ViewHelper.setAlpha(img, 0.1f);
		else if (percent <= 20)
			ViewHelper.setAlpha(img, 0.2f);
		else if (percent <= 30)
			ViewHelper.setAlpha(img, 0.3f);
		else if (percent <= 40)
			ViewHelper.setAlpha(img, 0.4f);
		else if (percent <= 50)
			ViewHelper.setAlpha(img, 0.5f);
		else if (percent <= 60)
			ViewHelper.setAlpha(img, 0.6f);
		else if (percent <= 70)
			ViewHelper.setAlpha(img, 0.7f);
		else if (percent <= 80)
			ViewHelper.setAlpha(img, 0.8f);
		else if (percent <= 90)
			ViewHelper.setAlpha(img, 0.9f);
		else if (percent <= 100)
			ViewHelper.setAlpha(img, 1.0f);
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
