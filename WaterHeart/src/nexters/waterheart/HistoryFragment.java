package nexters.waterheart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.actionbarsherlock.app.SherlockFragment;

public class HistoryFragment extends SherlockFragment {
	private static final int TUTORIAL_NUMBER = 2;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.historyview, container,false);
		return view;
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

	
}
