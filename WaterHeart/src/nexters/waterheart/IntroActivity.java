package nexters.waterheart;

import android.app.*;
import android.content.*;
import android.os.*;

public class IntroActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.intro);
	    
	    new Handler().postDelayed(new Thread(){
	    	public void run(){
	    		Intent intent = new Intent(IntroActivity.this, MainActivity.class);
	    	    startActivity(intent);
	    	    finish();
	    	    overridePendingTransition(R.anim.main_fadein, R.anim.intro_fadeout);
	    	}
	    }, 1000);
	    
	}

}
