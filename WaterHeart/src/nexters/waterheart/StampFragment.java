package nexters.waterheart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class StampFragment extends SherlockFragment {
	private static final int TUTORIAL_NUMBER = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.results_page, container,false);
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
