package nexters.waterheart;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

@SuppressLint("ValidFragment")
public class CustomFragment01 extends SherlockFragment {
	float unfocused, focused;
	Handler mHandler;
	private static final int FROM_CUSTOM=11;
	boolean isClickedOkay;
	
	public CustomFragment01(Handler handler){
		mHandler=handler;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.customview01, container, false);
		return view;
	}

	public void onResume(){
		isClickedOkay = false;
		//init();
		super.onResume();
	}
	
	public void init(){
		/*
		 * onResume()에서 호출될 메소드
		 * 메인에서 버튼을 누르거나 CustomFragment02 에서 돌아올 때 호출된다
		 * 파일에서 name, age, height, weight에 정보를 가져와서 대입하고
		 * 정보가 없을 경우에는 아무것도 입력하지않는다.
		 * 그리고 정보가 얼마나 입력되었는지에 따라 navigation의 상태를 여기서 조정한다.
		 */
	}
	public void oneChecked(){
		
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.removeItem(R.id.action_pencil);
		menu.removeItem(R.id.action_question);
		menu.removeItem(R.id.action_question_history);
		menu.add("Check").setIcon(R.drawable.actionbar_logo)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		getSherlockActivity().getSupportActionBar().setTitle("Heart Setting");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Save all data here
		//saveAllData();
		isClickedOkay=true;
		getSherlockActivity().getSupportFragmentManager()
		.beginTransaction().replace(android.R.id.content, new CustomFragment02(mHandler))
		.addToBackStack(null).commit();
		return super.onOptionsItemSelected(item);
	}
	
	public void saveAllData(){
		/*
		 * optionsItemSelected에서 호출되는 메소드
		 * name, age, height, weight에 입력된 값들을 모두 파일에 저장한다.
		 * 만약 이 넷중에 하나라도 입력된 값이 없으면 다음페이지로 못넘어가게 할꺼니까
		 * 그건 상관없을듯
		 * 
		 */
	}
	
	public void onPause(){
		getSherlockActivity().getSupportActionBar().setTitle("WaterHeart");
		if(!isClickedOkay)
		mHandler.sendEmptyMessage(FROM_CUSTOM);
		getActivity().getSupportFragmentManager().beginTransaction()
		.remove(CustomFragment01.this).commit();
		//getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		super.onPause();
	}
}
