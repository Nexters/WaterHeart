package nexters.waterheart;

import java.util.ArrayList;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.nineoldandroids.view.ViewHelper;

public class MainFragment extends SherlockFragment {

	ViewFlipper tutorialFlipper;
	TutorialManager tutorial;
	CupManager cupManager;
	View main_heart;
	ImageView[] cups;
	ImageView undo;
	ClickManager clickManager;
	/*
	 * 봐봐 여기 밑에 STATIC FINAL 변수들있지? 되도록이면 이것들을 사용하도록해~ 내가 밑에 핸들러에서도 CUP_ONE,
	 * CUP_TWO 같은걸로 다 바꿔놨어~ 이러면 좀 더 보기편하기도하고 혹시나 겹치면 안되니까!
	 */
	private static final int TUTORIAL_NUMBER = 0;
	private static final int CUP_ONE = 0, CUP_TWO = 1, CUP_THREE = 2,
			CUP_FOUR = 3;
	private static final int ONCLICK_NUM = 0;
	private static final int FROM_CUPCUSTOM = 10;

	// 한소리!!! 내가 여기저기 추가했어.
	// 전역으로 변수 몇개 선언했고, 일단 잘되나 확인하려고 init()에서 하트관련 이미지뷰들 추가하고 초기 투명도 설정했어.
	// 하트 가운데 숫자표시되는 투명도 하트는 안건드림.
	// 그리고 fill어쩌고 핸들러에서 하트 로직짬.
	// 근데 undo일 땐 아직 못짬.
	// 이 밑이 내가 추가한 변수들. 좀 지저분하고 임의로 한것들도 있어. 너가 좀 정리 도와줘............

	// 너무어렵... 이건 이제 누나몫이니까 화이팅
	int totalWater = 2000;
	ImageView[] heartImg = new ImageView[15];
	int[] value = new int[15];
	private int valueA = totalWater / 10;
	private int valueB = totalWater / 5;
	private int valueC = totalWater * 3 / 10;
	ArrayList<Integer> numList = new ArrayList<Integer>();
	Random random = new Random();
	int scope = 14, tmp, i = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * setHasOptionsMenu(true)가 지정되어야만 fragment내에서 액션바 메뉴를 설정할 수 있다
		 */
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.mainview, container, false);
		clickManager = new ClickManager(ONCLICK_NUM, getActivity(),
				fillWaterHandler);
		cupManager = new CupManager(getActivity());
		tutorial = new TutorialManager();
		return view;
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
			// 그 외 imageview들을 다 여기서 객체화
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

			// 한소라 여기가 내가 추가한 부분!!!!
			// 이 밑으로는 다 init()에서 희조가 추가한 부분
			// 하트 이미지뷰설정 및 하트 조각별 용량 설정, 그리고 초기 투명도 10퍼로 설정
			for (int i = 0; i < scope; i++) {
				heartImg[i] = (ImageView) getActivity().findViewById(
						R.id.main_heart01 + i);
				if (i == 0 || i == 4)
					value[i] = valueA;
				else if (i == 10 || i == 13)
					value[i] = valueC;
				else
					value[i] = valueB;
				ViewHelper.setAlpha(heartImg[i], 0.05f);
			}

			// 일단 겹치는 것 없이 모든 숫자를 랜덤으로 뽑았음, 일단 init()메소드에 넣었음
			boolean check;
			while (numList.size() != scope) {
				int value = random.nextInt(scope);
				check = true;
				for (int i = 0; i < numList.size(); i++)
					if (numList.get(i).equals(value)) {
						check = false;
						break;
					}
				if (check)
					numList.add(value);
			}

			tmp = value[numList.get(0)];
		}
	}

	/*
	 * ClickManager에서 db를통해 물의 총량을 받아오면 그 물의 양을 이 핸들러로 넘겨주고 여기서 하트에 물을 채운다.
	 */
	Handler fillWaterHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == FROM_CUPCUSTOM) { // 이건 내가 만든거. 컵커스터마이징창에서 돌아올때의
												// 핸들러야!
				getActivity().findViewById(R.id.pager_title_strip)
						.setVisibility(View.VISIBLE);
				getActivity().findViewById(R.id.main_undo).setVisibility(
						View.VISIBLE);
				for (int i = 0; i < 4; i++) {
					cups[i].setOnClickListener(clickManager);
					cups[i].setOnLongClickListener(longClick);
				}
			} else {
				cupManager.getAllCupStates();
				Toast.makeText(getSherlockActivity(), "" + msg.arg1, 1000)
						.show();
				// 한소라 여기가 추가한 부분. 하트 물채워지는 부분임. 아직 미완성...뭐가 문젠지 봐바 ㅠ.ㅠ
				int water = 0; // 사용된 컵의 물 양, 이것 때문에 어쩔수 없이 ClickManager에서
								// msg.what
								// 바꿔버렸는데 바꿔서 밑에 처럼 쓰면 안되는거? what을 딴데 쓸 용도가 있다면
								// what안쓰고
								// 컵용량 받을 수 있게 좀 해줘봐바..그럴만한 메소드가 없는듯

				// 아냐! 잘했네! 喜조누나가 한거처럼 what바꾸면서하면돼~
				if (msg.what == CUP_ONE)
					water = cupManager.cup_one;
				else if (msg.what == CUP_TWO)
					water = cupManager.cup_two;
				else if (msg.what == CUP_THREE)
					water = cupManager.cup_three;
				else if (msg.what == CUP_FOUR)
					water = cupManager.cup_four;
				else if (msg.what == 5)
					water = msg.arg1;

				float opacityPercentage = 0;

				if (msg.what == 5) {
					for (int i = 0; i < scope; i++)
						ViewHelper.setAlpha(heartImg[i], 0.05f);
					i = 0;
				}

				while (water != 0) {
					if (numList.isEmpty())
						break;
					else if (tmp <= water) { // tmp는 하트조각의 남은 용량
						ViewHelper.setAlpha(heartImg[numList.get(i)], 1.0f); // 채워지고
						water -= tmp; // 물의 양이 변화
						i++;
						// numList.remove(0);
						if (!(numList.isEmpty()))
							tmp = value[numList.get(i)]; // 새로운 하트 조각의 용량 받기
					}

					else { // 하트조각의 용량이 입력된 물의 양보다 작으면
						opacityPercentage = (float) water / tmp;
						tmp -= water;// 하트조각의 남은 용량이 변화하고
						ViewHelper.setAlpha(heartImg[numList.get(i)],
								opacityPercentage);
						water = 0;
					}

				}

			}
		}
	};

	OnLongClickListener longClick = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			getActivity().findViewById(R.id.pager_title_strip).setVisibility(
					View.GONE);
			getActivity().findViewById(R.id.main_undo).setVisibility(View.GONE);

			switch (v.getId()) {
			case R.id.main_cup_drop:
				getActivity()
						.getSupportFragmentManager()
						.beginTransaction()
						.add(android.R.id.content,
								new CupCustomizingFragment(fillWaterHandler,
										CUP_ONE)).addToBackStack(null).commit();
				return true;
			case R.id.main_cup_bottle:
				getActivity()
						.getSupportFragmentManager()
						.beginTransaction()
						.add(android.R.id.content,
								new CupCustomizingFragment(fillWaterHandler,
										CUP_TWO)).addToBackStack(null).commit();
				return true;
			case R.id.main_cup_cup:
				getActivity()
						.getSupportFragmentManager()
						.beginTransaction()
						.add(android.R.id.content,
								new CupCustomizingFragment(fillWaterHandler,
										CUP_THREE)).addToBackStack(null)
						.commit();
				return true;
			case R.id.main_cup_coffee:
				getActivity()
						.getSupportFragmentManager()
						.beginTransaction()
						.add(android.R.id.content,
								new CupCustomizingFragment(fillWaterHandler,
										CUP_FOUR)).addToBackStack(null)
						.commit();
				return true;
			}
			return false;
		}

	};

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_pencil:
			Intent intent = new Intent(getActivity(), CustomActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.show_custom, 0);
			return true;
		case R.id.action_question:
			tutorialFlipper = tutorial.getTutorial(TUTORIAL_NUMBER,
					getActivity());
			tutorialFlipper.setOnTouchListener(mOnTouchListener);
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
				if (tutorialFlipper.getCurrentView() == tutorialFlipper
						.getChildAt(1)) { // 튜토리얼 페이지가 2개밖에 없기때문에
					tutorial.finishTutorial(); // 지금은 getChildAt(1) 로
					return true;
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
