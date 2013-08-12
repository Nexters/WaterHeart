package nexters.waterheart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class MainFragment extends SherlockFragment{
	ViewFlipper tutorialFlipper;
	TutorialManager tutorial;
	CupManager cupManager;
	View main_heart;
	ImageView[] cups;
	ClickManager clickManager;
	private static final int TUTORIAL_NUMBER = 0;
	private static final int CUP_ONE=0, CUP_TWO=1, CUP_THREE=2, CUP_FOUR=3;
	private static final int ONCLICK_NUM=0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * setHasOptionsMenu(true)가 지정되어야만
		 * fragment내에서 액션바 메뉴를 설정할 수 있다
		 */
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.mainview, container,false);
		clickManager = new ClickManager(ONCLICK_NUM, getActivity(), fillWaterHandler);
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
	
	public void init(){
		if(main_heart==null){
			main_heart=getActivity().findViewById(R.id.main_heart_layout);
			//그 외 imageview들을 다 여기서 객체화
			cups = new ImageView[]{
				(ImageView)getActivity().findViewById(R.id.main_cup_drop),
				(ImageView)getActivity().findViewById(R.id.main_cup_bottle),
				(ImageView)getActivity().findViewById(R.id.main_cup_cup),
				(ImageView)getActivity().findViewById(R.id.main_cup_coffee)};
			
			main_heart.setOnClickListener(clickManager);
			for(int i=0;i<4;i++){
				cups[i].setOnClickListener(clickManager);
			}
			
		}
	}
/*
 * ClickManager에서 db를통해 물의 총량을 받아오면 
 * 그 물의 양을 이 핸들러로 넘겨주고
 * 여기서 하트에 물을 채운다.
 */
	Handler fillWaterHandler = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what==0){
				Toast.makeText(getSherlockActivity(), ""+msg.arg1, 1000).show();
			}
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
		switch(item.getItemId()){
		case R.id.action_pencil:
			Intent intent = new Intent(getActivity(), CustomActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.show_custom, 0);
			return true;
		case R.id.action_question:
			tutorialFlipper = tutorial.getTutorial(TUTORIAL_NUMBER, getActivity());
			tutorialFlipper.setOnTouchListener(mOnTouchListener);
			tutorial.showTutorial();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * 윈도우상의 튜토리얼이 받는 touchlistener
	 */
	public OnTouchListener mOnTouchListener = new OnTouchListener(){

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				if(tutorialFlipper.getCurrentView()==tutorialFlipper.getChildAt(1)){ //튜토리얼 페이지가 2개밖에 없기때문에
					tutorial.finishTutorial();										//지금은 getChildAt(1) 로
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


	public void onDestroy(){
		tutorial.finishTutorial();
		super.onDestroy();
	}
	
}
