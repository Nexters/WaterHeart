package nexters.waterheart;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.nineoldandroids.view.ViewHelper;

@SuppressLint("ValidFragment")
public class CupCustomizingFragment extends SherlockFragment implements
		View.OnClickListener {
	// 이쒸.. 너무 복잡해져서 이건 그냥 클릭리스너 스스로등록함
	Handler mHandler;
	ImageView[] cups_on_main;
	ImageView[] cups_on_customizing;
	EditText amountEdit;
	SeekBar seekBar;
	CupManager cupManager;
	ClickManager clickManager;
	int whichCup; // 메인에서 롱클릭한 컵이 무엇인지를 판단하기위한 변수
	float unselected = 0.1f, selected = 0.9f; // 선택된 컵의 알파값을 조절하기 위한 변수
	private static final int CUP_ONE = 0, CUP_TWO = 1, CUP_THREE = 2,
			CUP_FOUR = 3;
	private static final int FROM_CUPCUSTOM = 10;

	public CupCustomizingFragment(Handler handler, int which) {
		mHandler = handler;
		whichCup = which;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		cupManager = new CupManager(getActivity());
		View view = inflater.inflate(R.layout.cupcustomizing, container, false);
		cups_on_main = new ImageView[] {
				(ImageView) getActivity().findViewById(R.id.main_cup_drop),
				(ImageView) getActivity().findViewById(R.id.main_cup_bottle),
				(ImageView) getActivity().findViewById(R.id.main_cup_cup),
				(ImageView) getActivity().findViewById(R.id.main_cup_coffee) };
		cups_on_customizing = new ImageView[] {
				(ImageView) view.findViewById(R.id.cup_drop_customizing),
				(ImageView) view.findViewById(R.id.cup_bottle_customizing),
				(ImageView) view.findViewById(R.id.cup_cup_customizing),
				(ImageView) view.findViewById(R.id.cup_coffee_customizing) };
		amountEdit = (EditText) view
				.findViewById(R.id.cupcustomizing_amountEdit);
		seekBar = (SeekBar) view.findViewById(R.id.cupcustomizing_seekbar);

		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				progress = progress / 10;
				progress = progress * 10;
				amountEdit.setText("" + progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

		});

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		init();
		cups_on_main[whichCup].setImageResource(R.drawable.cup_selected);
		ViewHelper.setAlpha(cups_on_customizing[whichCup], selected);
		for (int i = 0; i < 4; i++) {
			// cups_on_main[i].setOnClickListener(this);
			cups_on_customizing[i].setOnClickListener(this);
			// cups_on_main[i].setOnLongClickListener(null);
			cups_on_customizing[i].setOnLongClickListener(null);
		}
		switch (whichCup) {
		case CUP_ONE:
			amountEdit.setText("" + cupManager.cup_one);
			seekBar.setProgress(cupManager.cup_one);
			break;
		case CUP_TWO:
			amountEdit.setText("" + cupManager.cup_two);
			seekBar.setProgress(cupManager.cup_two);
			break;
		case CUP_THREE:
			amountEdit.setText("" + cupManager.cup_three);
			seekBar.setProgress(cupManager.cup_three);
			break;
		case CUP_FOUR:
			amountEdit.setText("" + cupManager.cup_four);
			seekBar.setProgress(cupManager.cup_four);
			break;
		}
		super.onResume();
	}

	public void init() {
		cupManager.getAllCupStates();
		cups_on_main[0].setImageResource(cupManager.cup_one_image);
		cups_on_main[1].setImageResource(cupManager.cup_two_image);
		cups_on_main[2].setImageResource(cupManager.cup_three_image);
		cups_on_main[3].setImageResource(cupManager.cup_four_image);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cup_drop_customizing:
		case R.id.cup_bottle_customizing:
		case R.id.cup_cup_customizing:
		case R.id.cup_coffee_customizing:
			ViewHelper.setAlpha(v, selected);
			for (ImageView img : cups_on_customizing) {
				if (img != v)
					ViewHelper.setAlpha(img, unselected);
			}
			break;
		/*
		 * case R.id.main_cup_drop: case R.id.main_cup_bottle: case
		 * R.id.main_cup_cup: case R.id.main_cup_coffee:
		 * cups_on_main[0].setImageResource(R.drawable.cup_drop);
		 * cups_on_main[1].setImageResource(R.drawable.cup_bottle);
		 * cups_on_main[2].setImageResource(R.drawable.cup_cup);
		 * cups_on_main[3].setImageResource(R.drawable.cup_coffee); ImageView
		 * img = (ImageView)v; img.setImageResource(R.drawable.cup_selected);
		 * 
		 * getActivity().getSupportFragmentManager().beginTransaction()
		 * 
		 * .add(android.R.id.content, new
		 * CupCustomizingFragment(fillWaterHandler,CUP_ONE))
		 * .addToBackStack(null).commit(); break;
		 */
		}

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.removeItem(R.id.action_pencil);
		menu.removeItem(R.id.action_question);
		menu.removeItem(R.id.action_question_history);
		menu.add("Check").setIcon(R.drawable.actionbar_logo)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		getSherlockActivity().getSupportActionBar().setTitle("Cup Setting");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int imageId = 0;
		for (int i = 0; i < 4; i++) {
			if (ViewHelper.getAlpha(cups_on_customizing[i]) == selected) {
				imageId = i;
				break;
			}
		}
		switch (imageId) {
		case CUP_ONE:
			cupManager.setCupState(whichCup,
					Integer.parseInt(amountEdit.getText().toString()),
					R.drawable.cup_drop);
			break;
		case CUP_TWO:
			cupManager.setCupState(whichCup,
					Integer.parseInt(amountEdit.getText().toString()),
					R.drawable.cup_bottle);
			break;
		case CUP_THREE:
			cupManager.setCupState(whichCup,
					Integer.parseInt(amountEdit.getText().toString()),
					R.drawable.cup_cup);
			break;
		case CUP_FOUR:
			cupManager.setCupState(whichCup,
					Integer.parseInt(amountEdit.getText().toString()),
					R.drawable.cup_coffee);
			break;
		}
		init();
		Toast.makeText(
				getSherlockActivity(),
				"Done! Image changed! Amount: "
						+ amountEdit.getText().toString() + " ml",
				Toast.LENGTH_LONG).show();
		getActivity().getSupportFragmentManager().beginTransaction()
		.remove(CupCustomizingFragment.this).commit();
		getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		getSherlockActivity().getSupportActionBar().setTitle("WaterHeart");
		mHandler.sendEmptyMessage(FROM_CUPCUSTOM);
		init();
		getActivity().getSupportFragmentManager().beginTransaction()
				.remove(CupCustomizingFragment.this).commit();
		getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		super.onPause();
	}

}
