package nexters.waterheart;

import android.app.Activity;
import android.view.View;
/*
 * 당황하지마
 * 클릭리스너를 따로 분리하려는것뿐이야
 */
public class ClickManager implements View.OnClickListener{
	int onclick_num;
	Activity mActivity;
	
	public ClickManager(int onclick_num, Activity activity){
		/*
		 * onclick_num값으로 클릭을 일으킨 페이지마다 구분할거임~?
		 */
		this.onclick_num=onclick_num;
		mActivity=activity;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(onclick_num==1){
			historySwap(v.getId());
			
		}
	}
	
	public void historySwap(int resId){
		switch(resId){
		case R.id.history_heart_6:
			if(mActivity.findViewById(R.id.resultspage_change_6_01).getVisibility()==View.VISIBLE){
				mActivity.findViewById(R.id.resultspage_change_6_02).setVisibility(View.VISIBLE);
				mActivity.findViewById(R.id.resultspage_change_6_01).setVisibility(View.GONE);
			}else{
				mActivity.findViewById(R.id.resultspage_change_6_02).setVisibility(View.GONE);
				mActivity.findViewById(R.id.resultspage_change_6_01).setVisibility(View.VISIBLE);
			}
		}
	}

}
