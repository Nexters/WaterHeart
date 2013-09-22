package nexters.waterheart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorInflater;
import com.nineoldandroids.animation.AnimatorSet;

public class IntroActivity extends Activity {
	TextView text;
	AnimatorSet anim; 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.intro);
	    
	    text = (TextView)findViewById(R.id.introText);
	    
	    new Handler().postDelayed(new Thread(){
	    	public void run(){
	    		Intent intent = new Intent(IntroActivity.this, MainActivity.class);
	    	    startActivity(intent);
	    	    finish();
	    	    overridePendingTransition(R.anim.main_fadein, R.anim.intro_fadeout);
	    	}
	    }, 1000);
	    
	}
	public void onResume(){
		
		anim = (AnimatorSet)AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.testing);
	   // Animation ani = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.testing);
	    anim.setTarget(text);
	    new Handler().postDelayed(new Runnable(){
	    	public void run(){
	    		anim.start();
	    	}
	    }, 100);
	   // anim.start();
	   // text.startAnimation(ani);
	    super.onResume();
	}

}
