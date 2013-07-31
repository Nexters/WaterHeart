package nexters.waterheart;

import java.util.*;

import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;

import com.actionbarsherlock.app.*;

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
		
		return new MainFragment();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		
		return ""+position;
	}
	
	
}

    
}
