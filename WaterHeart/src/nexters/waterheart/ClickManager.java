package nexters.waterheart;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

public class ClickManager implements View.OnClickListener{
	int onclick_num;
	Activity mActivity;
	CupManager cupManager;
	HeartManager heartManager;
	Handler mHandler;
	private static final int MAIN_CLICK=0, HISTORY_CLICK=1;
	private static final int CUP_ONE = 0, CUP_TWO = 1, CUP_THREE = 2,
			CUP_FOUR = 3;
	
	public ClickManager(int onclick_num, Activity activity, Handler handler){
		/*
		 * onclick_num값으로 클릭을 일으킨 페이지마다 구분할거임~?
		 */
		this.onclick_num=onclick_num;
		mActivity=activity;
		cupManager = new CupManager(mActivity);
		heartManager = new HeartManager(activity);
		heartManager.init();
		mHandler = handler;
	}
	
	@Override
	public void onClick(View v) {
		int water;
		Message msg;
		//onclick_num==1 은 히스토리페이지에서 클릭이벤트를 받을 때
		if(onclick_num==HISTORY_CLICK){
			historySwap(v.getId());
		}else if(onclick_num==MAIN_CLICK){
		//이 밑은 메인페이지에서 클릭이벤트를 받을 때
			cupManager.getAllCupStates();
		switch(v.getId()){
		case R.id.main_heart_layout:
			if(mActivity.findViewById(R.id.main_change_01).getVisibility()==View.VISIBLE){
				mActivity.findViewById(R.id.main_change_01).setVisibility(View.GONE);
				mActivity.findViewById(R.id.main_change_02).setVisibility(View.VISIBLE);
			}else{
				mActivity.findViewById(R.id.main_change_01).setVisibility(View.VISIBLE);
				mActivity.findViewById(R.id.main_change_02).setVisibility(View.GONE);
			}
			break;
		case R.id.main_cup_drop:
			water = heartManager.mainOnCupClicked(cupManager.cup_one);
			msg = Message.obtain(mHandler, CUP_ONE, water, 0);
			mHandler.sendMessage(msg);
			break;
		case R.id.main_cup_bottle:
			water = heartManager.mainOnCupClicked(cupManager.cup_two);
			msg = Message.obtain(mHandler,CUP_TWO,water,0);
			mHandler.sendMessage(msg);
			break;
		case R.id.main_cup_cup:
			water = heartManager.mainOnCupClicked(cupManager.cup_three);
			msg = Message.obtain(mHandler,CUP_THREE,water,0);
			mHandler.sendMessage(msg);
			break;
		case R.id.main_cup_coffee:
			water = heartManager.mainOnCupClicked(cupManager.cup_four);
			msg = Message.obtain(mHandler,CUP_FOUR,water,0);
			mHandler.sendMessage(msg);
			break;
		case R.id.main_undo:
			water = heartManager.mainOnBackClicked();
			msg = Message.obtain(mHandler,5,water,0);
			mHandler.sendMessage(msg);
			break;
		}
		}
		
		
	}
	
	public void historySwap(int resId){
		switch(resId){
		case R.id.history_heart_06:
			if(mActivity.findViewById(R.id.resultspage_change_6_01).getVisibility()==View.VISIBLE){
				mActivity.findViewById(R.id.resultspage_change_6_02).setVisibility(View.VISIBLE);
				mActivity.findViewById(R.id.resultspage_change_6_01).setVisibility(View.GONE);
			}else{
				mActivity.findViewById(R.id.resultspage_change_6_02).setVisibility(View.GONE);
				mActivity.findViewById(R.id.resultspage_change_6_01).setVisibility(View.VISIBLE);
			}
			break;
		case R.id.history_heart_05:
			if(mActivity.findViewById(R.id.resultspage_change_5_01).getVisibility()==View.VISIBLE){
				mActivity.findViewById(R.id.resultspage_change_5_02).setVisibility(View.VISIBLE);
				mActivity.findViewById(R.id.resultspage_change_5_01).setVisibility(View.GONE);
			}else{
				mActivity.findViewById(R.id.resultspage_change_5_02).setVisibility(View.GONE);
				mActivity.findViewById(R.id.resultspage_change_5_01).setVisibility(View.VISIBLE);
			}
			break;
		case R.id.history_heart_04:
			if(mActivity.findViewById(R.id.resultspage_change_4_01).getVisibility()==View.VISIBLE){
				mActivity.findViewById(R.id.resultspage_change_4_02).setVisibility(View.VISIBLE);
				mActivity.findViewById(R.id.resultspage_change_4_01).setVisibility(View.GONE);
			}else{
				mActivity.findViewById(R.id.resultspage_change_4_02).setVisibility(View.GONE);
				mActivity.findViewById(R.id.resultspage_change_4_01).setVisibility(View.VISIBLE);
			}
			break;
		case R.id.history_heart_03:
			if(mActivity.findViewById(R.id.resultspage_change_3_01).getVisibility()==View.VISIBLE){
				mActivity.findViewById(R.id.resultspage_change_3_02).setVisibility(View.VISIBLE);
				mActivity.findViewById(R.id.resultspage_change_3_01).setVisibility(View.GONE);
			}else{
				mActivity.findViewById(R.id.resultspage_change_3_02).setVisibility(View.GONE);
				mActivity.findViewById(R.id.resultspage_change_3_01).setVisibility(View.VISIBLE);
			}
			break;
		case R.id.history_heart_02:
			if(mActivity.findViewById(R.id.resultspage_change_2_01).getVisibility()==View.VISIBLE){
				mActivity.findViewById(R.id.resultspage_change_2_02).setVisibility(View.VISIBLE);
				mActivity.findViewById(R.id.resultspage_change_2_01).setVisibility(View.GONE);
			}else{
				mActivity.findViewById(R.id.resultspage_change_2_02).setVisibility(View.GONE);
				mActivity.findViewById(R.id.resultspage_change_2_01).setVisibility(View.VISIBLE);
			}
			break;
		case R.id.history_heart_01:
			if(mActivity.findViewById(R.id.resultspage_change_1_01).getVisibility()==View.VISIBLE){
				mActivity.findViewById(R.id.resultspage_change_1_02).setVisibility(View.VISIBLE);
				mActivity.findViewById(R.id.resultspage_change_1_01).setVisibility(View.GONE);
			}else{
				mActivity.findViewById(R.id.resultspage_change_1_02).setVisibility(View.GONE);
				mActivity.findViewById(R.id.resultspage_change_1_01).setVisibility(View.VISIBLE);
			}
			break;
		}
	}

}
