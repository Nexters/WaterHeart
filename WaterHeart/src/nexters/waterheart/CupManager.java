package nexters.waterheart;

import android.app.Activity;
import android.content.SharedPreferences;

public class CupManager {
	SharedPreferences pref;
	Activity mActivity;
	int cup_one, cup_two, cup_three, cup_four; //private 변수로 할라고했는데.. 다른곳에서 참조하는데 번거로울듯하여
	int cup_one_image,
		cup_two_image,
		cup_three_image,
		cup_four_image;
	/*
	 * 컵의 정보들은 sharedpreferences 에 저장하는걸로~
	 * 4개밖에없으니 속도에는 무리가 없을거같다는 생각..
	 * 8개도..괜찮겠징 ? ㅋ
	 */
	private static final int CUP_ONE = 0, CUP_TWO = 1, CUP_THREE = 2,
			CUP_FOUR = 3;
	
	public CupManager(Activity activity) {
		mActivity = activity;
		pref = mActivity.getSharedPreferences("CupSettings",0);
		getAllCupStates();
	}
	
	public void saveAllCupStates() {
		SharedPreferences.Editor edit = pref.edit();
		edit.putInt("cup_one", cup_one);
		edit.putInt("cup_two", cup_two);
		edit.putInt("cup_three", cup_three);
		edit.putInt("cup_four", cup_four);
		edit.commit();
	}
	
	public void getAllCupStates() {
		cup_one = pref.getInt("cup_one", 100);
		cup_two = pref.getInt("cup_two", 500);
		cup_three = pref.getInt("cup_three", 200);
		cup_four = pref.getInt("cup_four", 240);
		cup_one_image = pref.getInt("cup_one_image", R.drawable.cup_drop);
		cup_two_image = pref.getInt("cup_two_image",R.drawable.cup_bottle);
		cup_three_image = pref.getInt("cup_three_image", R.drawable.cup_cup);
		cup_four_image = pref.getInt("cup_four_image", R.drawable.cup_coffee);
	}
	
	public void setCupState(int which, int amount, int resId) { //which 변수는 0부터 컵의 순서
		SharedPreferences.Editor edit = pref.edit();
		switch (which) {
		case CUP_ONE:
			edit.putInt("cup_one", amount);
			edit.putInt("cup_one_image", resId);
			break;
		case CUP_TWO:
			edit.putInt("cup_two", amount);
			edit.putInt("cup_two_image", resId);
			break;
		case CUP_THREE:
			edit.putInt("cup_three", amount);
			edit.putInt("cup_three_image", resId);
			break;
		case CUP_FOUR:
			edit.putInt("cup_four", amount);
			edit.putInt("cup_four_image", resId);
			break;
		}
		edit.commit();
		//getAllCupStates();
	}
}
