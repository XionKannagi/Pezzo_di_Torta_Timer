package android.lifeistech.com.pezzoditortatimer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Created by togane on 2016/02/25.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private int[] mactivityList;

    public MyPagerAdapter(FragmentManager fm, int[] activityList) {
        super(fm);
        mactivityList = activityList;
    }

    @Override
    public Fragment getItem(int position) {

        TimerFragment timerFragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putInt("timeActivity",mactivityList[position]);
        timerFragment.setArguments(args);

        return timerFragment;

        /*switch (position) {
            case 0:
                TimerFragment timerFragment = new TimerFragment();

                return timerFragment;

            case 1:
                AddWorksFragment addWorksFragment = new AddWorksFragment();

                return addWorksFragment;
        }*/

    }


    @Override
    public int getCount() {
        return mactivityList.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
