package nexters.waterheart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

@SuppressLint("ValidFragment")
public class CupCustomizingFragment extends SherlockFragment{
	private static final int FROM_CUPCUSTOM = 10;
	Handler mHandler;
	
	public CupCustomizingFragment(Handler handler){
		mHandler=handler;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.cupcustomizing, container,false);
		
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.removeItem(R.id.action_pencil);
		menu.removeItem(R.id.action_question);
		getSherlockActivity().getSupportActionBar().setTitle("Cup Setting");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		getSherlockActivity().getSupportActionBar().setTitle("WaterHeart");
		mHandler.sendEmptyMessage(FROM_CUPCUSTOM);
		getActivity().getSupportFragmentManager().beginTransaction()
		.remove(CupCustomizingFragment.this).commit();
		super.onPause();
	}
	
	
}
