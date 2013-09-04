package nexters.waterheart;

import java.io.FileInputStream;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.nineoldandroids.view.ViewHelper;

public class MainFragment extends SherlockFragment {

	View mainView;
	HeartManager heartManager;
	ViewFlipper tutorialFlipper;
	TutorialManager tutorial;
	CupManager cupManager;
	View main_heart;
	ImageView[] cups;
	ImageView undo;
	ClickManager clickManager;
	Animation toastAni;
	TextView toastText;
	static String yourName;
	static int whichTutorial;
	static int counter; // 첫 실행 시, 즉 사용자정보가
	// 하나도 없을 때에는 counter 가 0이다.
	// counter가 0일때 ~~ 조치를 취한다...

	TextView heartTextPercent;
	TextView heartTextML;

	private static final int TUTORIAL_NUMBER01 = 0, TUTORIAL_NUMBER02 = 1;
	private static final int CUP_ONE = 0, CUP_TWO = 1, CUP_THREE = 2,
			CUP_FOUR = 3;
	private static final int ONCLICK_NUM = 0;
	private static final int FROM_CUPCUSTOM = 10;
	private static final int FROM_CUSTOM = 11;
	private static final String FRAGMENT_TAG_CUPCUSTOM = "CUPCUSTOM";
	private static final String FRAGMENT_TAG_CUSTOM = "CUSTOM";

	static int totalWater = 2500;
	float a; // 제일 작은 조각 한개의 용량
	float b; // 중간 크기 조각 한개의 용량
	float c; // 제일 큰 조각 한개의 용량
	int water;
	float[] originalValue = new float[14];
	float[] currentValue = new float[14];
	float[] eachWater = new float[14];
	ImageView[] heartImg = new ImageView[14];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * setHasOptionsMenu(true)가 지정되어야만 fragment내에서 액션바 메뉴를 설정할 수 있다
		 */
		setHasOptionsMenu(true);
		mainView = inflater.inflate(R.layout.mainview, container, false);
		clickManager = new ClickManager(ONCLICK_NUM, getActivity(),
				fillWaterHandler);
		cupManager = new CupManager(getActivity());
		tutorial = new TutorialManager();
		toastText = (TextView) mainView.findViewById(R.id.toastText);
		toastAni = AnimationUtils.loadAnimation(getSherlockActivity(),
				R.anim.text_show);
		return mainView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		init();

		super.onResume();
	}

	public void init() {
		if (main_heart == null) {
			main_heart = getActivity().findViewById(R.id.main_heart_layout);
			undo = (ImageView) getActivity().findViewById(R.id.main_undo);
			expandTouchArea(mainView, undo, 50);
			// 그 외 imageview들을 다 여기서 객체화
			heartTextPercent = (TextView) getActivity().findViewById(
					R.id.main_heart_percent);
			heartTextML = (TextView) getActivity().findViewById(
					R.id.main_heart_ml);
			cups = new ImageView[] {
					(ImageView) getActivity().findViewById(R.id.main_cup_drop),
					(ImageView) getActivity()
							.findViewById(R.id.main_cup_bottle),
					(ImageView) getActivity().findViewById(R.id.main_cup_cup),
					(ImageView) getActivity()
							.findViewById(R.id.main_cup_coffee) };
			cups[0].setImageResource(cupManager.cup_one_image);
			cups[1].setImageResource(cupManager.cup_two_image);
			cups[2].setImageResource(cupManager.cup_three_image);
			cups[3].setImageResource(cupManager.cup_four_image);
			main_heart.setOnClickListener(clickManager);
			undo.setOnClickListener(clickManager);
			for (int i = 0; i < 4; i++) {
				cups[i].setOnClickListener(clickManager);
				cups[i].setOnLongClickListener(longClick);
			}
			/*
			 * try { FileInputStream fis =
			 * getActivity().openFileInput("name.txt"); byte[] data = new
			 * byte[fis.available()]; while (fis.read(data) != -1) { ; }
			 * yourName = new String(data);
			 * 
			 * fis = getActivity().openFileInput("counter"); counter =
			 * fis.read(); fis.close(); } catch (Exception e) { yourName = "";
			 * counter = 0; }
			 */
			// 이 밑으로는 다 init()에서 희조가 추가한 부분
			// 하트 이미지뷰설정 및 하트 조각별 용량 설정, 그리고 초기 투명도 10퍼로 설정
			for (int i = 0; i < 14; i++) {
				heartImg[i] = (ImageView) getActivity().findViewById(
						R.id.main_heart01 + i);

				ViewHelper.setAlpha(heartImg[i], 0.05f);
			}

			heartManager = new HeartManager(getActivity());
			// heartManager.init();
		}

		try {
			FileInputStream fis = getActivity().openFileInput("name.txt");
			byte[] data = new byte[fis.available()];
			while (fis.read(data) != -1) {
				;
			}
			yourName = new String(data);

			fis = getActivity().openFileInput("counter");
			counter = fis.read();
			fis.close();
		} catch (Exception e) {
			yourName = "";
			counter = 0;
		}

		water = heartManager.mainHeartShow();
		heartTextML.setText(String.valueOf(water));
		heartTextPercent.setText(String.valueOf((int) ((float) water
				/ totalWater * 100)));
		// init()에서 if문 밖에 valueA, B, C를 재정의해야한다
		// 왜냐하면 실행 도중에 사용자 정보를 바꿀 시 init()을 다시 호출할것이기때문!
		// 그에 이어 각 하트 조각의 용량도 여기서 재정의한다.
		// 아래의 로직은 totalWater가 최소한 2000 이상일때라 가정한다.

		// 근데 생각보다 효과가 별로... 다른걸해봐야하나
		a = totalWater / 20; // 제일 작은 조각 한개의 용량
		b = totalWater / 20; // 중간 크기 조각 한개의 용량
		c = totalWater / 5; // 제일 큰 조각 한개의 용량
		float[] array = new float[] { a, a, b, b, b, b, b, b, b, b, b, b, c, c };
		Time time = new Time();
		time.setToNow();
		int forRandom = time.weekDay;
		for (int i = 0; i < 14; i++) {
			if ((i % 2) == 0) {
				originalValue[i] = array[i] - (forRandom * i);
			} else {
				originalValue[i] = array[i] + (forRandom * (i - 1));
			}
		}
		// 여기서 heartLogic()을 호출해야한다!!
		heartLogic();
		// heartLogic(heartWater);
	}

	public static void expandTouchArea(final View bigView,
			final View smallView, final int extraPadding) {
		bigView.post(new Runnable() {
			@Override
			public void run() {
				Rect rect = new Rect();
				smallView.getHitRect(rect);
				rect.top -= extraPadding;
				rect.left -= extraPadding;
				rect.right += extraPadding;
				rect.bottom += extraPadding;
				bigView.setTouchDelegate(new TouchDelegate(rect, smallView));
			}
		});
	}

	/*
	 * ClickManager에서 db를통해 물의 총량을 받아오면 그 물의 양을 이 핸들러로 넘겨주고 여기서 하트에 물을 채운다.
	 */
	Handler fillWaterHandler = new Handler() {
		String toastString = "";

		public void handleMessage(Message msg) {

			if (msg.what == FROM_CUPCUSTOM) {
				getActivity().findViewById(R.id.pager_title_strip)
						.setVisibility(View.VISIBLE);
				getActivity().findViewById(R.id.main_undo).setVisibility(
						View.VISIBLE);
				for (int i = 0; i < 4; i++) {
					cups[i].setOnClickListener(clickManager);
					cups[i].setOnLongClickListener(longClick);
				}
				if (msg.arg2 == 1) {
					toastText.setText("컵의 용량이 " + msg.arg1 + " ml로 변경되었습니다!");
					toastText.startAnimation(toastAni);
				}
			} else if (msg.what == FROM_CUSTOM) {
				init();
				getActivity().findViewById(R.id.pager_title_strip)
						.setVisibility(View.VISIBLE);
				getActivity().findViewById(R.id.main_undo).setVisibility(
						View.VISIBLE);
				if ((msg.arg1 == 1) && (counter != 2)) {
					toastText.setText("목표가 " + totalWater + " ml 로 변경되었어요!");
					toastText.startAnimation(toastAni);
					/*
					 * try { FileInputStream fis = getActivity().openFileInput(
					 * "name.txt"); byte[] data = new byte[fis.available()];
					 * while (fis.read(data) != -1) { ; } yourName = new
					 * String(data); fis.close(); } catch (Exception e) {
					 * yourName = ""; }
					 */
				} else if ((msg.arg1 == 1) && (counter == 2)) {
					toastText.setText("안녕하세요 " + yourName + "님!");
					toastText.startAnimation(toastAni);
					toastText.postDelayed(new Runnable() {
						public void run() {
							toastText.setText("매일 " + totalWater
									+ " ml 마실 수 있죠?");
							toastText.startAnimation(toastAni);
							toastText.postDelayed(new Runnable() {
								public void run() {
									tutorialFlipper = tutorial.getTutorial(
											TUTORIAL_NUMBER02, getActivity());
									tutorialFlipper
											.setOnTouchListener(mOnTouchListener);
									whichTutorial = TUTORIAL_NUMBER02;
									tutorial.showTutorial();
								}
							}, 1600);
						}
					}, 1600);
				}

			} else {
				cupManager.getAllCupStates();
				// Toast.makeText(getSherlockActivity(), "" + msg.arg1, 1000)
				// .show();
				if ((msg.what >= 0) && (msg.what < 4)) {
					switch (msg.what) {
					case CUP_ONE:
						toastString = "" + cupManager.cup_one;
						toastText.setText(toastString + " ml 마셨어요!");
						break;
					case CUP_TWO:
						toastString = "" + cupManager.cup_two;
						toastText.setText(toastString + " ml 마셨어요!");
						break;
					case CUP_THREE:
						toastString = "" + cupManager.cup_three;
						toastText.setText(toastString + " ml 마셨어요!");
						break;
					case CUP_FOUR:
						toastString = "" + cupManager.cup_four;
						toastText.setText(toastString + " ml 마셨어요!");
						break;
					}
					toastText.startAnimation(toastAni);
				}

				if (msg.what == 5) {
					toastText.setText(yourName + "님 분발하세요~");
					if (msg.arg1 == 0)
						toastText.setText("더이상 취소할 수 없어요~");
					toastText.startAnimation(toastAni);
				} else if (msg.what == 6) {
					if (counter == 0) {
						tutorialFlipper = tutorial.getTutorial(
								TUTORIAL_NUMBER01, getActivity());
						tutorialFlipper.setOnTouchListener(mOnTouchListener);
						whichTutorial = TUTORIAL_NUMBER01;
						tutorial.showTutorial();
						counter++;
					} else if (counter == 1) {
						toastText.setText("좌측 상단의 연필아이콘을 이용해 개인정보를 입력해 주세요!");
						toastText.startAnimation(toastAni);
					}
				}
				water = msg.arg1;
				heartLogic();
			}

		}

	};

	public void heartLogic() {
		for (int i = 0; i < 14; i++)
			eachWater[i] = water / 14; // 물 마실때마다 호출해야하는 반복문
		// 주의할점: water는 지금 한번 마셨을때 그 컵의 물의 용량이 아니라
		// 지금까지 마신 물의 총량이다!

		for (int i = 0; i < 14; i++) {
			currentValue[i] = eachWater[i]; // 0번쨰 하트의 물 양을 갱신한다.
			if (currentValue[i] > originalValue[i]) { // 한편 originalValue보다 더 많이
														// 채워졌을경우엔
				float tmp = currentValue[i] - originalValue[i];
				currentValue[i] = originalValue[i]; // currentValue는
													// originalValue값으로 맞춰주고
				if (i == 13)
					break;
				eachWater[i + 1] += tmp; // 여유분을 다음 물 양에 추가해준다.

			}
		}
		heartTextML.setText(String.valueOf(water));
		heartTextPercent.setText(String.valueOf((int) ((float) water
				/ totalWater * 100)));
		// 반복문을 통해 각 하트 조각들의 currentValue들에 물을 채웠으면
		// 이제 for문 밖에서 setHeartAlpha()값으로
		// 각각 하트 조각들마다의 currentValue값을 참조해서
		// 그에 알맞게 opacity를 조절한다.

		setHeartAlpha();
	} // heartLogic() 메소드 끝

	public void setHeartAlpha() {
		float alphaValue = 0.0f;
		for (int i = 0; i < 14; i++) {
			alphaValue = currentValue[i] / originalValue[i];
			if (alphaValue >= 0 && alphaValue < 0.1)
				ViewHelper.setAlpha(heartImg[i], 0.1f);
			else if (alphaValue >= 0.1 && alphaValue < 0.2)
				ViewHelper.setAlpha(heartImg[i], 0.2f);
			else if (alphaValue >= 0.2 && alphaValue < 0.3)
				ViewHelper.setAlpha(heartImg[i], 0.3f);
			else if (alphaValue >= 0.3 && alphaValue < 0.4)
				ViewHelper.setAlpha(heartImg[i], 0.4f);
			else if (alphaValue >= 0.4 && alphaValue < 0.5)
				ViewHelper.setAlpha(heartImg[i], 0.5f);
			else if (alphaValue >= 0.5 && alphaValue < 0.6)
				ViewHelper.setAlpha(heartImg[i], 0.55f);
			else if (alphaValue >= 0.6 && alphaValue < 0.7)
				ViewHelper.setAlpha(heartImg[i], 0.6f);
			else if (alphaValue >= 0.7 && alphaValue < 0.8)
				ViewHelper.setAlpha(heartImg[i], 0.65f);
			else if (alphaValue >= 0.8 && alphaValue < 0.9)
				ViewHelper.setAlpha(heartImg[i], 0.7f);
			else if (alphaValue >= 0.9 && alphaValue < 0.95)
				ViewHelper.setAlpha(heartImg[i], 0.75f);
			else if (alphaValue >= 0.95)
				ViewHelper.setAlpha(heartImg[i], 0.8f);
		}
	}

	OnLongClickListener longClick = new OnLongClickListener() {

		@SuppressLint("NewApi")
		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			getActivity().findViewById(R.id.pager_title_strip).setVisibility(
					View.INVISIBLE);
			getActivity().findViewById(R.id.main_undo).setVisibility(
					View.INVISIBLE);
			FragmentTransaction transaction = getSherlockActivity()
					.getSupportFragmentManager().beginTransaction();
			transaction.setCustomAnimations(R.anim.fragment_enter,
					R.anim.fragment_exit, R.anim.fragment_enter,
					R.anim.fragment_exit);

			Fragment fragment;
			// transaction.add(android.R.id.content, fragment);
			// transaction.addToBackStack(null).commit();
			switch (v.getId()) {
			case R.id.main_cup_drop:
				fragment = new CupCustomizingFragment(fillWaterHandler, CUP_ONE);
				transaction.add(android.R.id.content, fragment, FRAGMENT_TAG_CUPCUSTOM)
						.addToBackStack(null).commit();
				/*
				 * getActivity() .getSupportFragmentManager()
				 * .beginTransaction() .add(android.R.id.content, new
				 * CupCustomizingFragment(fillWaterHandler,
				 * CUP_ONE)).addToBackStack(null).commit();
				 */
				return true;
			case R.id.main_cup_bottle:
				fragment = new CupCustomizingFragment(fillWaterHandler, CUP_TWO);
				transaction.add(android.R.id.content, fragment, FRAGMENT_TAG_CUPCUSTOM)
						.addToBackStack(null).commit();
				/*
				 * getActivity() .getSupportFragmentManager()
				 * .beginTransaction() .add(android.R.id.content, new
				 * CupCustomizingFragment(fillWaterHandler,
				 * CUP_TWO)).addToBackStack(null).commit();
				 */
				return true;
			case R.id.main_cup_cup:
				fragment = new CupCustomizingFragment(fillWaterHandler,
						CUP_THREE);
				transaction.add(android.R.id.content, fragment, FRAGMENT_TAG_CUPCUSTOM)
						.addToBackStack(null).commit();
				/*
				 * getActivity() .getSupportFragmentManager()
				 * .beginTransaction() .add(android.R.id.content, new
				 * CupCustomizingFragment(fillWaterHandler,
				 * CUP_THREE)).addToBackStack(null) .commit();
				 */
				return true;
			case R.id.main_cup_coffee:
				fragment = new CupCustomizingFragment(fillWaterHandler,
						CUP_FOUR);
				transaction.add(android.R.id.content, fragment, FRAGMENT_TAG_CUPCUSTOM)
						.addToBackStack(null).commit();
				/*
				 * getActivity() .getSupportFragmentManager()
				 * .beginTransaction() .add(android.R.id.content, new
				 * CupCustomizingFragment(fillWaterHandler,
				 * CUP_FOUR)).addToBackStack(null) .commit();
				 */
				return true;
			}
			return false;
		}

	};

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.main, menu);
		ActionBar action = getSherlockActivity().getSupportActionBar();
		action.setDisplayShowTitleEnabled(false);

		LayoutInflater inflater02 = LayoutInflater.from(getSherlockActivity());
		View titleView = inflater02.inflate(R.layout.actionbar_title, null);

		TextView titleText = (TextView) titleView
				.findViewById(R.id.actionBar_WaterHeart);
		titleText.setVisibility(View.VISIBLE);
		titleText.setTypeface(Typeface.createFromAsset(getSherlockActivity()
				.getAssets(), "neutratexttfbook.ttf"));

		action.setCustomView(titleView);
		action.setDisplayShowCustomEnabled(true);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_pencil:
			getActivity().findViewById(R.id.pager_title_strip).setVisibility(
					View.INVISIBLE);
			getActivity().findViewById(R.id.main_undo).setVisibility(
					View.INVISIBLE);
			FragmentTransaction transaction = getSherlockActivity()
					.getSupportFragmentManager().beginTransaction();
			transaction.setCustomAnimations(R.anim.fragment_enter,
					R.anim.fragment_exit, R.anim.fragment_enter,
					R.anim.fragment_exit);

			CustomFragment01 fragment = new CustomFragment01(fillWaterHandler);
			transaction.add(android.R.id.content, fragment, FRAGMENT_TAG_CUSTOM);
			transaction.addToBackStack(null).commit();
			/*
			 * getActivity() .getSupportFragmentManager() .beginTransaction()
			 * .add(android.R.id.content, new
			 * CustomFragment01(fillWaterHandler))
			 * .addToBackStack(null).commit();
			 */
			return true;
		case R.id.action_question:
			tutorialFlipper = tutorial.getTutorial(TUTORIAL_NUMBER02,
					getActivity());
			tutorialFlipper.setOnTouchListener(mOnTouchListener);
			whichTutorial = TUTORIAL_NUMBER02;
			tutorial.showTutorial();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * 윈도우상의 튜토리얼이 받는 touchlistener
	 */
	public OnTouchListener mOnTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				switch (whichTutorial) {
				case 0:
					if (tutorialFlipper.getCurrentView() == tutorialFlipper
							.getChildAt(0)) { // 튜토리얼 페이지가 2개밖에 없기때문에
						tutorial.finishTutorial(); // 지금은 getChildAt(1) 로
						return true;
					}
				case 1:
					if (tutorialFlipper.getCurrentView() == tutorialFlipper
							.getChildAt(4)) { // 튜토리얼 페이지가 2개밖에 없기때문에
						tutorial.finishTutorial(); // 지금은 getChildAt(1) 로
						return true;
					}
				}
				tutorial.showNext();
			}
			return true;
		}

	};

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		/*
		 * saveFragmentBasicState nullpointerexception 에러 방지용....
		 */
		outState.putString("Don't crash", "Please");
		super.onSaveInstanceState(outState);
	}

	public void onDestroy() {
		tutorial.finishTutorial();
		super.onDestroy();
	}
}