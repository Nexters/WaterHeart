package nexters.waterheart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

@SuppressLint("ValidFragment")
public class CustomFragment01 extends SherlockFragment {
	float unfocused, focused;
	Handler mHandler;
	private static final int FROM_CUSTOM=11;
	boolean isClickedOkay;
	EditText[] edit = new EditText[4]; //name, age, height, weight 순서
	ImageView[] navi = new ImageView[4];
	
	public CustomFragment01(Handler handler){
		mHandler=handler;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.customview01, container, false);
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"nanumgothic.ttf");
		for(int i = 0; i<4; i++){
			navi[i] = (ImageView)view.findViewById(R.id.navi01+i);
			navi[i].setTag("unselected");
			edit[i] = (EditText)view.findViewById(R.id.custom_name+i);
			edit[i].setTypeface(tf);
			edit[i].setHintTextColor(0x88ffffff);
			edit[i].addTextChangedListener(mTextWatcher);
			edit[i].setOnFocusChangeListener(focusChange);
		}
		return view;
	}

	public void onResume(){
		isClickedOkay = false;
		init();
		super.onResume();
	}
	
	public void init(){
		/*
		 * onResume()에서 호출될 메소드
		 * 메인에서 버튼을 누르거나 CustomFragment02 에서 돌아올 때 호출된다
		 * 파일에서 name, age, height, weight에 정보를 가져와서 대입하고
		 * 정보가 없을 경우에는 아무것도 입력하지않는다.
		 * 그리고 정보가 얼마나 입력되었는지에 따라 navigation의 상태를 여기서 조정한다.
		 */
		FileInputStream fis = null;
		try{
			fis = getActivity().openFileInput("name.txt");
			byte[] data = new byte[fis.available()];
			while(fis.read(data)!=-1){;}
			edit[0].setText(new String(data));
			
			fis = getActivity().openFileInput("age.txt");
			data = new byte[fis.available()];
			while(fis.read(data)!=-1){}
			edit[1].setText(new String(data));
			
			fis = getActivity().openFileInput("height.txt");
			data = new byte[fis.available()];
			while(fis.read(data)!=-1){}
			edit[2].setText(new String(data));
			
			fis = getActivity().openFileInput("weight.txt");
			data = new byte[fis.available()];
			while(fis.read(data)!=-1){}
			edit[3].setText(new String(data));
		} catch(FileNotFoundException e){
			for(int i=0; i<4; i++) edit[i].setText("");
		} catch(Exception e){
			for(int i=0; i<4; i++) edit[i].setText("");
		}finally{
			try {
				if(fis!=null)
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int howMany=0;
		for(int j=0; j<4; j++){
			if(!edit[j].getText().toString().equals("")) howMany++;
		}
		for(int i=0; i<howMany; i++){
			navi[i].setImageResource(R.drawable.navi_selected);
			navi[i].setTag("selected");
		}
	}
	
	TextWatcher mTextWatcher = new TextWatcher(){
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if(s.toString().equals("")){
				for(int i=3; i>=0; i--){
					if((String)(navi[i].getTag()) == "selected"){
						navi[i].setImageResource(R.drawable.navi_unselected);
						navi[i].setTag("unselected");
						break;
					}
				}
			}
		}
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {}
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {}		
	};
	
	OnFocusChangeListener focusChange = new OnFocusChangeListener(){

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(!hasFocus){
				if(!((EditText)v).getText().toString().equals("")){
					for(int i=0; i<4; i++){
						if((String)(navi[i].getTag()) == "unselected"){
							navi[i].setImageResource(R.drawable.navi_selected);
							navi[i].setTag("selected");
							break;
							}
						}
				}
			}
		}
		
	};

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.removeItem(R.id.action_pencil);
		menu.removeItem(R.id.action_question);
		menu.removeItem(R.id.action_question_history);
		menu.add("Check").setIcon(R.drawable.icon_checking)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		getSherlockActivity().getSupportActionBar().setTitle("Heart Setting");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if((String)(navi[3].getTag()) != "selected"){
			Toast.makeText(getActivity(), "입력을 마치세요", 1000).show();
			return true;
		} else {
			for(int i = 0; i<4; i++){
				if(edit[i].getText().toString().equals("")) {
					Toast.makeText(getActivity(), "입력을 마치세요", 1000).show();
					return true;
				}
			}
		}
		saveAllData();
		isClickedOkay=true;
		getSherlockActivity().getSupportFragmentManager()
		.beginTransaction().replace(android.R.id.content, new CustomFragment02(mHandler))
		.addToBackStack(null).commit();
		return super.onOptionsItemSelected(item);
	}
	
	public void saveAllData(){
		/*
		 * optionsItemSelected에서 호출되는 메소드
		 * name, age, height, weight에 입력된 값들을 모두 파일에 저장한다.
		 * 만약 이 넷중에 하나라도 입력된 값이 없으면 다음페이지로 못넘어가게 할꺼니까
		 * 그건 상관없을듯
		 * 
		 */
		String nameStr = edit[0].getText().toString();
		String ageStr = edit[1].getText().toString();
		String heightStr = edit[2].getText().toString();
		String weightStr = edit[3].getText().toString();
		FileOutputStream fos = null;
		try{
			fos = getActivity().openFileOutput("name.txt", 0);
			fos.write(nameStr.getBytes());
			fos = getActivity().openFileOutput("age.txt", 0);
			fos.write(ageStr.getBytes());
			fos = getActivity().openFileOutput("height.txt", 0);
			fos.write(heightStr.getBytes());
			fos = getActivity().openFileOutput("weight.txt", 0);
			fos.write(weightStr.getBytes());
		}catch(Exception e){}finally{
			try {
				if(fos!=null)
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 
	}
	
	public void onPause(){
		getSherlockActivity().getSupportActionBar().setTitle("WaterHeart");
		if(!isClickedOkay)
		mHandler.sendEmptyMessage(FROM_CUSTOM);
		getActivity().getSupportFragmentManager().beginTransaction()
		.remove(CustomFragment01.this).commit();
		//getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		super.onPause();
	}
}
