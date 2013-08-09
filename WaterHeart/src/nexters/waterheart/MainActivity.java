package nexters.waterheart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity {
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        
        mViewPager=(ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }
   

public class SectionsPagerAdapter extends FragmentPagerAdapter{

	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		Fragment fragment = new Fragment();
		switch(position){
		case 0: fragment = new MainFragment(); break;
		case 1: fragment = new HistoryFragment(); break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		if(position==0)return "WaterHeart";
		else return "History";
	}
	
	
}

    
}
