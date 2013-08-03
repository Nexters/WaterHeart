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

public class MainFragment extends SherlockFragment{
	Button btn01;
	Button btn02;
	ViewFlipper tutorialFlipper;
	TutorialManager tutorial;
	CupManager cupManager;
	private static final int TUTORIAL_NUMBER = 0;
	private static final int CUP_ONE=0, CUP_TWO=1, CUP_THREE=2, CUP_FOUR=3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mainview, container,false);
		btn01 = (Button)view.findViewById(R.id.button);
		btn01.setOnClickListener(mOnClickListener);
		btn02 = (Button)view.findViewById(R.id.toCustom); btn02.setOnClickListener(mOnClickListener);
		cupManager = new CupManager(getActivity());
		tutorial = new TutorialManager();
		tutorialFlipper = tutorial.getTutorial(TUTORIAL_NUMBER, getActivity());
		tutorialFlipper.setOnTouchListener(mOnTouchListener);
		return view;
	}

	public View.OnClickListener mOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.button:
				tutorial.showTutorial();
				break;
			case R.id.toCustom:
				Intent intent = new Intent(getActivity(), CustomActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.show_custom, 0);
			}
			
		}
	};
	/*
	 * 윈도우상의 튜토리얼이 받는 touchlistener
	 */
	public OnTouchListener mOnTouchListener = new OnTouchListener(){

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				if(tutorialFlipper.getCurrentView()==tutorialFlipper.getChildAt(1)){
					tutorial.finishTutorial();
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
