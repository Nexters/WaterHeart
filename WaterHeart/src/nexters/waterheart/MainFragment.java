package nexters.waterheart;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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

	TextView heartTextPercent;
	TextView heartTextML;

	private static final int TUTORIAL_NUMBER = 0;
	private static final int CUP_ONE = 0, CUP_TWO = 1, CUP_THREE = 2,
			CUP_FOUR = 3;
	private static final int ONCLICK_NUM = 0;
	private static final int FROM_CUPCUSTOM = 10;
	private static final int FROM_CUSTOM = 11;

	static int totalWater = 2000;
	float valueA = totalWater / 20; //제일 작은 조각 한개의 용량
	float valueB = totalWater / 20;	//중간 크기 조각 한개의 용량
	float valueC = totalWater / 5;	//제일 큰 조각 한개의 용량
	int water;
	float[] originalValue = new float[14];
	float[] currentValue = new float[14];
	float[] eachWater = new float[14];
	ImageView[] heartImg = new ImageView[14];
	//static final int totalWater = 2000;
	//float[] value = new float[15];
	//float[] logicValue = new float[15];
	//private int valueA = totalWater / 2 / 12;
	//private int valueB = totalWater / 10 * 6 / 12;
	//private int valueC = totalWater / 2 * 5 / 12;
	//ArrayList<Integer> numList = new ArrayList<Integer>();
	//Random random = new Random();
	//int scope = 14, index = 0;
	//float tmp;
	//int heartWater;
	//int divisor = 3;
	//int currentWater = 0;

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

			// 이 밑으로는 다 init()에서 희조가 추가한 부분
			// 하트 이미지뷰설정 및 하트 조각별 용량 설정, 그리고 초기 투명도 10퍼로 설정
			for (int i = 0; i < 14; i++) {
				heartImg[i] = (ImageView) getActivity().findViewById(
						R.id.main_heart01 + i);
				/*
				if (i == 0 || i == 1)
					originalValue[i] = valueA; //젤 작은거
				else if (i == 12 || i == 13)
					originalValue[i] = valueC; //젤 큰거
				else
					originalValue[i] = valueB;
					*/
				//logicValue[i] = value[i];
				ViewHelper.setAlpha(heartImg[i], 0.05f);
			}
			//여기는 일단 임시로
			//totalWater 가 2000으로 고정됬을거라고 가정했을 시:
			originalValue[0] = 80; originalValue[1] = 120; originalValue[2] = 60; originalValue[3] = 140;
			originalValue[4] = 75; originalValue[5] = 125; originalValue[6] = 90; originalValue[7] = 110;
			originalValue[8] = 85; originalValue[9] = 115; originalValue[10] = 50; originalValue[11] = 150;
			originalValue[12] = 450; originalValue[13] = 350;
			
			
			
			// 일단 겹치는 것 없이 모든 숫자를 랜덤으로 뽑았음, 일단 init()메소드에 넣었음
			/*
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
			*/
			heartManager = new HeartManager(getActivity());
			heartManager.init();	
		}
		//heartManager = new HeartManager(getActivity());
		//heartManager.init();
		water = heartManager.mainHeartShow();
		heartTextML.setText(String.valueOf(water));
		heartTextPercent.setText(String.valueOf((int) ((float) water
				/ totalWater * 100)));
														//여기서 heartLogic()을 호출해야한다!!
		heartLogic();
		//heartLogic(heartWater);
	}

	public static void expandTouchArea(final View bigView, final View smallView, final int extraPadding) {
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
			} else if (msg.what == FROM_CUSTOM) {
				getActivity().findViewById(R.id.pager_title_strip)
						.setVisibility(View.VISIBLE);
				getActivity().findViewById(R.id.main_undo).setVisibility(
						View.VISIBLE);
			} else {
				cupManager.getAllCupStates();
				Toast.makeText(getSherlockActivity(), "" + msg.arg1, 1000)
						.show();
				/*
				int newWater = 0;
				if (msg.what == CUP_ONE)
					newWater = cupManager.cup_one;
				else if (msg.what == CUP_TWO)
					newWater = cupManager.cup_two;
				else if (msg.what == CUP_THREE)
					newWater = cupManager.cup_three;
				else if (msg.what == CUP_FOUR)
					newWater = cupManager.cup_four;
				else if (msg.what == 5)
					newWater = msg.arg1;
				*/							//이건 필요없을듯?
											//그냥 바로 msg.arg1으로 받은 값으로 처리하면될듯
				water = msg.arg1;
				heartLogic();
				/*
				 * 
				 * int totalWater = 2000;
				 * float valueA = totalWater / 20;
				 * float valueB = totalWater / 5;
				 * float valueC = totalWater / 20;
				 * int water;
				 * float[] originalValue = new float[14]; //맨 위에 정의
				 * float[] currentValue = new float[14]; //맨 위에 정의
				 * float[] eachWater = new float[14]; //맨 위에다가 정의해둔다.
				 * 
				 *	//이 밑의 코드들은
				 *	//어플이 다시 시작되었을때도 다시 불려야할 코드들이다.
				 *	//어플이 시작되면서 water 변수에 지금까지 마신 물의 양을 넘겨주면
				 *	//이 밑에서 다시 알아서 처리
				 *	//따라서 따로 메소드에 정의해놓는다!
				 *
				 *public void heartLogic(){
				 * for(int i = 0; i<14; i++) eachWater[i] = water / 14; //물 마실때마다 호출해야하는 반복문
				 * 							//주의할점: water는 지금 한번 마셨을때 그 컵의 물의 용량이 아니라
				 * 							//지금까지 마신 물의 총량이다!
				 * 
				 * for(int i = 0; i<14; i++){
				 * 	if(currentValue[i] == originalValue[i]){	//0번째 하트가 꽉 찼을 때는 더 채우지말고
				 * 		if(i == 13) break;
				 * 		eachWater[i+1] += eachWater[i];	//1번째 하트에 채울 물에 더해준다. 
				 * 	} else{								//0번째 하트가 꽉 안찼을경우에는
				 * 		currentValue[i] == eachWater[i];	//0번쨰 하트의 물 양을 갱신한다.
				 * 		if(currentValue[i] > originalValue[i]){		//한편 originalValue보다 더 많이 채워졌을경우엔
				 * 			float tmp = currentValue[i] - originalValue[i];
				 * 			currentValue[i] = originalValue[i];		//currentValue는 originalValue값으로 맞춰주고 
				 * 			if(i == 13) break;
				 * 			eachWater[i+1] += tmp;		//여유분을 다음 물 양에 추가해준다.
				 * 		}
				 * 	}
				 * }
				 *	//반복문을 통해 각 하트 조각들의 currentValue들에 물을 채웠으면
				 *	//이제 for문 밖에서 setHeartAlpha()값으로
				 *	//각각 하트 조각들마다의 currentValue값을 참조해서
				 *	//그에 알맞게 opacity를 조절한다. 
				 *
				 *	setHeartAlpha();
				 * } 										//heartLogic() 메소드 끝
				 * 
				 * public void setHeartAlpha(){
				 * float alphaValue = 0.0f;
				 * 	for(int i = 0; i<14; i++){
				 * 		alphaValue = currentValue[i] / originalValue[i];
				 * 		heartImg[i].setAlpha(alphaValue);
				 * 	}
				 * }
				 * 
				 * 
				 */
				
				/*
				if (msg.what == 5) {
					for (int i = 0; i < scope; i++) {
						ViewHelper.setAlpha(heartImg[i], 0.05f);
						if (i == 0 || i == 4)
							value[i] = valueA / divisor;
						else if (i == 10 || i == 13)
							value[i] = valueC / divisor;
						else
							value[i] = valueB / divisor;
					}
					divisor = 3;
					index = 0;
					heartWater = newWater;
					heartTextML.setText(String.valueOf(heartWater));
					heartTextPercent
							.setText(String.valueOf((int) ((float) heartWater
									/ totalWater * 100)));

				} else {
					heartWater += newWater;
					heartTextML.setText(String.valueOf(heartWater));
					heartTextPercent
							.setText(String.valueOf((int) ((float) heartWater
									/ totalWater * 100)));
				}
				heartLogic(newWater);
				*/
			}
			
		}
		
	};
	public void heartLogic(){
		 for(int i = 0; i<14; i++) eachWater[i] = water / 14; //물 마실때마다 호출해야하는 반복문
		 						//주의할점: water는 지금 한번 마셨을때 그 컵의 물의 용량이 아니라
		 						//지금까지 마신 물의 총량이다!
		 
		 for(int i = 0; i<14; i++){
		  		currentValue[i] = eachWater[i];	//0번쨰 하트의 물 양을 갱신한다.
		  		if(currentValue[i] > originalValue[i]){		//한편 originalValue보다 더 많이 채워졌을경우엔
		  			float tmp = currentValue[i] - originalValue[i];
		  			currentValue[i] = originalValue[i];		//currentValue는 originalValue값으로 맞춰주고 
		  			if(i == 13) break;
		  			eachWater[i+1] += tmp;		//여유분을 다음 물 양에 추가해준다.
		  		
		  	}
		  }
		 heartTextML.setText(String.valueOf(water));
			heartTextPercent.setText(String.valueOf((int) ((float) water
					/ totalWater * 100)));
			//반복문을 통해 각 하트 조각들의 currentValue들에 물을 채웠으면
		 	//이제 for문 밖에서 setHeartAlpha()값으로
		 	//각각 하트 조각들마다의 currentValue값을 참조해서
		 	//그에 알맞게 opacity를 조절한다. 
		 
		 	setHeartAlpha();
	} 										//heartLogic() 메소드 끝
	
	public void setHeartAlpha(){
		  float alphaValue = 0.0f;
		  	for(int i = 0; i<14; i++){
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
/*
	void heartLogic(int water) {

		float opacityPercentage = 0;
		currentWater += water;
		
		while (water != 0) {
			
			if (index == 14)
				break;
			else if (tmp <= water) { // tmp는 하트조각의 남은 용량
				ViewHelper.setAlpha(heartImg[numList.get(index)],
						(float) 0.8f / divisor); // 채워지고
				water -= tmp; // 물의 양이 변화
				index++;
				// numList.remove(0);
				if (!(index == 14))
					tmp = value[numList.get(index)]; // 새로운 하트 조각의 용량 받기
			}
			else { // 하트조각의 용량이 입력된 물의 양보다 작으면
				opacityPercentage = (float) water / tmp / divisor;
				if (opacityPercentage <= 0.05)
					opacityPercentage = 0.08f;
				tmp -= water;// 하트조각의 남은 용량이 변화하고
				ViewHelper.setAlpha(heartImg[numList.get(index)],
						opacityPercentage);
				water = 0;
			}
			int w = 0;
			for (int i = 0; i < scope; i++)
				w += value[i];
			if (totalWater / divisor < currentWater && w < currentWater && divisor > 1) { 
				divisor--; //divisor--, value[] += value[] or init()에서 따로 떼던가 해야함
				for (int i = 0; i < scope; i++)
					value[i] += logicValue[i];
				index = 0;
			}
		}

		

	}
	*/

	OnLongClickListener longClick = new OnLongClickListener() {

		@SuppressLint("NewApi")
		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			getActivity().findViewById(R.id.pager_title_strip).setVisibility(
					View.INVISIBLE);
			getActivity().findViewById(R.id.main_undo).setVisibility(
					View.INVISIBLE);

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
			getActivity().findViewById(R.id.pager_title_strip).setVisibility(
					View.INVISIBLE);
			getActivity().findViewById(R.id.main_undo).setVisibility(
					View.INVISIBLE);
			getActivity()
					.getSupportFragmentManager()
					.beginTransaction()
					.add(android.R.id.content,
							new CustomFragment01(fillWaterHandler))
					.addToBackStack(null).commit();
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
