package nexters.waterheart;

import android.view.View;
/*
 * 당황하지마
 * 클릭리스너를 따로 분리하려는것뿐이야
 */
public class ClickListenerClass implements View.OnClickListener{
	int onclick_num;
	
	public ClickListenerClass(int onclick_num){
		/*
		 * onclick_num값으로 클릭을 일으킨 페이지마다 구분할거임~?
		 */
		this.onclick_num=onclick_num;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
