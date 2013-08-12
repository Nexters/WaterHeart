package nexters.waterheart;

import android.app.Activity;
import android.content.SharedPreferences;

public class CupManager {
	SharedPreferences pref;
	Activity mActivity;
	int cup_one, cup_two, cup_three, cup_four; //private 변수로 할라고했는데.. 다른곳에서 참조하는데 번거로울듯하여
	/*
	 * 컵의 정보들은 sharedpreferences 에 저장하는걸로~
	 * 4개밖에없으니 속도에는 무리가 없을거같다는 생각..
	 */
	
	public CupManager(Activity activity){
		mActivity=activity;
		pref = mActivity.getSharedPreferences("CupSettings",0);
		getAllCupStates();
	}
	
	public void saveAllCupStates(){
		SharedPreferences.Editor edit = pref.edit();
		edit.putInt("cup_one", cup_one);
		edit.putInt("cup_two", cup_two);
		edit.putInt("cup_three", cup_three);
		edit.putInt("cup_four", cup_four);
		edit.commit();
	}
	
	public void getAllCupStates(){
		cup_one = pref.getInt("cup_one", 100);
		cup_two = pref.getInt("cup_two", 500);
		cup_three = pref.getInt("cup_three", 200);
		cup_four = pref.getInt("cup_four", 240);
	}
	
	public void setCupState(int which, int amount){ //which 변수는 0부터 컵의 순서
		SharedPreferences.Editor edit = pref.edit();
		switch(which){
		case 0:
			edit.putInt("cup_one", amount);
			break;
		case 1:
			edit.putInt("cup_two", amount);
			break;
		case 2:
			edit.putInt("cup_three", amount);
			break;
		case 3:
			edit.putInt("cup_four", amount);
			break;
		}
		edit.commit();
	}
}
