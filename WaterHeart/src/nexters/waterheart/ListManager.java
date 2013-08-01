package nexters.waterheart;

import android.app.Activity;
import android.widget.ListView;

public class ListManager {
	/*
	 * 이 클래스에서 각종 리스트뷰들을 관리한다!! 얼마나쓸지는모르겠찌만 일단 만들어둠....
	 */

	public ListView newInstance(int which, Activity mActivity){
		/*
		 * which 변수를 받아 리턴할 리스트뷰를 ~
		 */
		return new ListView(mActivity);
	}
	
}
