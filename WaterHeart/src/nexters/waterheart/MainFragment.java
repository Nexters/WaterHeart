package nexters.waterheart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.actionbarsherlock.app.SherlockFragment;

public class MainFragment extends SherlockFragment{
	ViewFlipper tutorialFlipper;
	WindowManager wm;
	TutorialManager tutorial;
	private static final int TUTORIAL_NUMBER = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mainview, container,false);
		Button btn = (Button)view.findViewById(R.id.button);
		btn.setOnClickListener(mOnClickListener);
		tutorial = new TutorialManager();
		tutorialFlipper = tutorial.getTutorial(TUTORIAL_NUMBER, getActivity());
		tutorialFlipper.setOnTouchListener(mOnTouchListener);
		return view;
	}

	public View.OnClickListener mOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			tutorial.showTutorial();
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
	
	public void onDestroy(){
		tutorial.finishTutorial();
		super.onDestroy();
	}
}
