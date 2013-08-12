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
	private static final int ONCLICK_NUMBER = 1;
	ImageView heart01; int percent01=30;
	ImageView heart02; int percent02=44;
	ImageView heart03; int percent03=78;
	ImageView heart04; int percent04=80;
	ImageView heart05; int percent05=100;
	ImageView heart06; int percent06=55;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * setHasOptionsMenu(true)가 지정되어야만
		 * fragment내에서 액션바 메뉴를 설정할 수 있다
		 */
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.historyview, container,false);
		/*
		 * 나중에 할 것:
		 * 위 percent* 변수들에 DB의 값을 불러와서 대입한다.
		 */
		return view;
	}
	public void onStart(){
		init();
		super.onStart();
	}
	
	public void init(){
		if(heart01==null){
		heart01 = (ImageView)getActivity().findViewById(R.id.history_heart_1);
		heart02 = (ImageView)getActivity().findViewById(R.id.history_heart_2);
		heart03 = (ImageView)getActivity().findViewById(R.id.history_heart_3);
		heart04 = (ImageView)getActivity().findViewById(R.id.history_heart_4);
		heart05 = (ImageView)getActivity().findViewById(R.id.history_heart_5);
		heart06 = (ImageView)getActivity().findViewById(R.id.history_heart_6);
		
		
		setImage(heart01, percent01);
		setImage(heart02, percent02);
		setImage(heart03, percent03);
		setImage(heart04, percent04);
		setImage(heart05, percent05);
		setImage(heart06, percent06);
		}
	}
	
	public void setImage(ImageView img, int percentage){
		/*
		 * 처음에는 모든 이미지들이 invisible 상태일것이다
		 * 아마도..DB에서 날짜정보같은걸 가져와서.. 조건을 만족하면 VISIBLE로 바꾸고..할듯
		 */
		if(percentage<=10)ViewHelper.setAlpha(img, 0.1f);
		else if(percentage<=20)ViewHelper.setAlpha(img, 0.2f);
		else if(percentage<=30)ViewHelper.setAlpha(img, 0.3f);
		else if(percentage<=40)ViewHelper.setAlpha(img, 0.4f);
		else if(percentage<=50)ViewHelper.setAlpha(img, 0.5f);
		else if(percentage<=60)ViewHelper.setAlpha(img, 0.6f);
		else if(percentage<=70)ViewHelper.setAlpha(img, 0.7f);
		else if(percentage<=80)ViewHelper.setAlpha(img, 0.8f);
		else if(percentage<=90)ViewHelper.setAlpha(img, 0.9f);
		else if(percentage<=100)ViewHelper.setAlpha(img, 1.0f);
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
