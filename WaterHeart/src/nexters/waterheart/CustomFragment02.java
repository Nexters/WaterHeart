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
public class CustomFragment02 extends SherlockFragment{
	Handler mHandler;
	private static final int FROM_CUSTOM = 11;
	boolean isClickedOkay;

	public CustomFragment02(Handler handler){
		mHandler = handler;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.customview02, container,false);
		return view;
	}
	
	public void onResume(){
		//init();
		super.onResume();
	}
	
	public void init(){
		/*
		 * onResume() 에서 호출될 메소드
		 * CustomFragment01 에서 아이콘을 눌렀을 때 호출된다.
		 * 파일에서 gender, goal에 대한 정보를 가져와서 state_focused 값을 조정한다.
		 * 아무 정보도 없을 경우에는 아무 조치도 취하지않음
		 * 그리고 gender, goal 에 대한 값을 통해 마지막 2개 남은 navigation의 값을 조정한다.
		 */
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.removeItem(R.id.action_pencil);
		menu.removeItem(R.id.action_question);
		menu.removeItem(R.id.action_question_history);
		menu.add("CheckButton").setIcon(R.drawable.actionbar_logo)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		getSherlockActivity().getSupportActionBar().setTitle("Heart Setting");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Save all the data here
		//saveAllData();  						//CustomFragment01이랑 메소드 이름이 똑같으면 좀 그런가..?
		isClickedOkay=true;
		getActivity().getSupportFragmentManager().beginTransaction()
		.remove(CustomFragment02.this).commit();
		getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		return super.onOptionsItemSelected(item);
	}

	 public void saveAllData(){
		 /*
		  * optionsItemSelected에서 호출될 메소드
		  * gender, goal의 state_focused값을 받아와서 true인것을 파일에 저장한다.
		  * gender와 goal을 각각 ImageBUtton 배열에 넣을것이기때문에
		  * 정수로 저장하면 될듯하다.
		  * 
		  */
	 }

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		getSherlockActivity().getSupportActionBar().setTitle("WaterHeart");
		if(isClickedOkay)
		mHandler.sendEmptyMessage(FROM_CUSTOM);
		getActivity().getSupportFragmentManager().beginTransaction()
		.remove(CustomFragment02.this).commit();
		//getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		super.onPause();
	}

	
}
