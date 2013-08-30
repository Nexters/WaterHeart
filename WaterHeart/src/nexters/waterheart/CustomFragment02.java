package nexters.waterheart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

@SuppressLint("ValidFragment")
public class CustomFragment02 extends SherlockFragment implements OnClickListener{
	Handler mHandler;
	private static final int FROM_CUSTOM = 11;
	boolean isClickedOkay;
	ImageButton[] gender = new ImageButton[2];
	ImageView[] navi = new ImageView[2];
	ImageButton[] goal = new ImageButton[6];
	
	public CustomFragment02(Handler handler){
		mHandler = handler;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.customview02, container,false);
		for(int i=0; i<2; i++){
			gender[i] = (ImageButton)view.findViewById(R.id.gender_man+i);
			gender[i].setOnClickListener(this);
			gender[i].setTag("unselected");
			navi[i] = (ImageView)view.findViewById(R.id.navi05_01+i);
			navi[i].setTag("unselected");
		}
		for(int i=0; i<6; i++){
			goal[i] = (ImageButton)view.findViewById(R.id.goal_diet+i);
			goal[i].setOnClickListener(this);
			goal[i].setTag("unselected");
		}
		return view;
	}
	
	public void onResume(){
		isClickedOkay = false; //이거 추가함. 과연?
		init();
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
		int which = 0;
		FileInputStream fis = null;
		try{
			fis = getActivity().openFileInput("gender.txt");
			which = fis.read();
			gender[which].performClick();
			
			fis = getActivity().openFileInput("goal.txt");
			which = fis.read();
			goal[which].performClick();
		}catch(FileNotFoundException e){}catch(Exception e){}
		finally{
			try{
				if(fis != null) fis.close();
			}catch(IOException e){};
		}
		setNaviState();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.gender_man:
			gender[0].setBackgroundResource(R.drawable.man_clicked);
			gender[0].setTag("selected");
			gender[1].setBackgroundResource(R.drawable.woman_unclicked);
			gender[1].setTag("unselected");
			
			break;
		case R.id.gender_woman:
			gender[0].setBackgroundResource(R.drawable.man_unclicked);
			gender[0].setTag("unselected");
			gender[1].setBackgroundResource(R.drawable.woman_clicked);
			gender[1].setTag("selected");
			break;
		case R.id.goal_diet:
			setGoalDefault();
			goal[0].setBackgroundResource(R.drawable.diet_touched);
			goal[0].setTag("selected");
			break;
		case R.id.goal_health:
			setGoalDefault();
			goal[1].setBackgroundResource(R.drawable.health_touched);
			goal[1].setTag("selected");
			break;
		case R.id.goal_diabetes:
			setGoalDefault();
			goal[2].setBackgroundResource(R.drawable.diabetes_touched);
			goal[2].setTag("selected");
			break;
		case R.id.goal_skincare:
			setGoalDefault();
			goal[3].setBackgroundResource(R.drawable.skincare_touched);
			goal[3].setTag("selected");
			break;
		case R.id.goal_exercise:
			setGoalDefault();
			goal[4].setBackgroundResource(R.drawable.exercise_touched);
			goal[4].setTag("selected");
			break;
		case R.id.goal_smoking:
			setGoalDefault();
			goal[5].setBackgroundResource(R.drawable.smoking_touched);
			goal[5].setTag("selected");
			break;
		}
		setNaviState();
	}

	public void setNaviState(){
		if(((String)(gender[0].getTag()) == "selected")
				|| ((String)(gender[1].getTag()) == "selected")){
			navi[0].setImageResource(R.drawable.navi_selected);
			navi[0].setTag("selected");
		}
		for(int i=0; i<6; i++){
			if((String)(goal[i].getTag()) == "selected"){
				navi[1].setImageResource(R.drawable.navi_selected);
				navi[1].setTag("selected");
				break;
			}
		}
	}
	
	public void setGoalDefault(){
		goal[0].setBackgroundResource(R.drawable.diet_untouched);
		goal[1].setBackgroundResource(R.drawable.health_untouched);
		goal[2].setBackgroundResource(R.drawable.diabetes_untouched);
		goal[3].setBackgroundResource(R.drawable.skincare_untouched);
		goal[4].setBackgroundResource(R.drawable.exercise_untouched);
		goal[5].setBackgroundResource(R.drawable.smoking_untouched);
		for(int i=0; i<6; i++) goal[i].setTag("unselected");
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.removeItem(R.id.action_pencil);
		menu.removeItem(R.id.action_question);
		menu.removeItem(R.id.action_question_history);
		menu.add("CheckButton").setIcon(R.drawable.icon_checking)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		//getSherlockActivity().getSupportActionBar().setTitle("Heart Setting");
		ActionBar action = getSherlockActivity().getSupportActionBar();
		action.setDisplayShowTitleEnabled(false);
		
		LayoutInflater inflater02 = LayoutInflater.from(getSherlockActivity());
		View titleView = inflater02.inflate(R.layout.actionbar_title,null);
		
		TextView titleText = (TextView)titleView.findViewById(R.id.actionBar_HeartSetting);
		titleText.setVisibility(View.VISIBLE);
		titleText.setTypeface(Typeface.createFromAsset(getSherlockActivity().getAssets(),"neutratexttfbook.ttf"));
		
		action.setCustomView(titleView);
		action.setDisplayShowCustomEnabled(true);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Save all the data here
		if((String)(navi[1].getTag()) != "selected"){
			Toast.makeText(getActivity(), "입력을 마치세요", 1000).show();
			return true;
		}
		saveAllData();  						//CustomFragment01이랑 메소드 이름이 똑같으면 좀 그런가..?
		isClickedOkay=true;
		android.support.v4.app.FragmentManager manager = getSherlockActivity().getSupportFragmentManager();
		manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.setCustomAnimations(0, 
				R.anim.fragment_exit, 0, 0);
		transaction.remove(CustomFragment02.this).commit();
		/*
		getActivity().getSupportFragmentManager().beginTransaction()
		.remove(CustomFragment02.this).commit();
		getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		*/
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
		 int which = 0;
		 MainFragment.counter++;
		 FileOutputStream fos = null;
		 try{
			 fos = getActivity().openFileOutput("gender.txt", 0);
			 if((String)(gender[0].getTag()) == "selected") which = 0;
			 else which = 1;
			 fos.write(which);
			 
			 fos = getActivity().openFileOutput("goal.txt", 0);
			 for(int i=0; i<6; i++){
				 if((String)(goal[i].getTag()) == "selected"){
					 which = i;
					 break;
				 }
			 }
			 fos.write(which);
			 
			 fos = getActivity().openFileOutput("counter", 0);
			 fos.write(MainFragment.counter);
		 }catch(Exception e){}finally{
				try {
					if(fos != null)
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
	 }

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		getSherlockActivity().getSupportActionBar().setTitle("WaterHeart");
		if(isClickedOkay){
			Message msg = Message.obtain(mHandler, FROM_CUSTOM, 1, 0);
			mHandler.sendMessage(msg);
		}
		getActivity().getSupportFragmentManager().beginTransaction()
		.remove(CustomFragment02.this).commit();
		//getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		super.onPause();
	}
	
	
}
