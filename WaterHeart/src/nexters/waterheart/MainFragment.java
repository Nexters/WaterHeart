package nexters.waterheart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class MainFragment extends SherlockFragment{
	ViewFlipper tutorialFlipper;
	TutorialManager tutorial;
	CupManager cupManager;
	private static final int TUTORIAL_NUMBER = 0;
	private static final int CUP_ONE=0, CUP_TWO=1, CUP_THREE=2, CUP_FOUR=3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * setHasOptionsMenu(true)가 지정되어야만
		 * fragment내에서 액션바 메뉴를 설정할 수 있다
		 */
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.mainview, container,false);
		cupManager = new CupManager(getActivity());
		tutorial = new TutorialManager();
		return view;
	}
	
	
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
