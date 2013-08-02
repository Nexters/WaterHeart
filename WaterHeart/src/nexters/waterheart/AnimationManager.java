package nexters.waterheart;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationManager {
/*
 * 각종 애니메이션들을 관리하는 클래스
 */
	Activity mActivity;
	
	public AnimationManager(Activity activity){
		mActivity = activity;
	}
	/*
	 * 이 뒤로는 getFadeoutAnimation(), getCupCustomizerAnimation() 등등 넣을계획?
	 */
	public Animation getShowCustomAnimation(){
		return AnimationUtils.loadAnimation(mActivity, R.anim.show_custom);
	}
}
