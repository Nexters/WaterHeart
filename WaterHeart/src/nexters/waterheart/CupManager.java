package nexters.waterheart;

import android.app.Activity;
import android.content.SharedPreferences;

public class CupManager {
	SharedPreferences pref;
	Activity mActivity;
	private int cup_one, cup_two, cup_three, cup_four;
	
	
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
		cup_one = pref.getInt("cup_one", 180);
		cup_two = pref.getInt("cup_two", 230);
		cup_three = pref.getInt("cup_three", 280);
		cup_four = pref.getInt("cup_four", 400);
	}
	
	public void setCupState(int which, int amount){
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
	}
}
