package android.lifeistech.com.pezzoditortatimer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by togane on 2016/02/25.
 */
public class MyPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    private int[] mactivityList;

    int[] iconImageRes = {
            R.drawable.ic_alarm_add_white_24dp,
            R.drawable.ic_timer_white_24dp,
            R.drawable.ic_insert_chart_white_24dp,
            R.drawable.ic_settings_white_24dp};

    public MyPagerAdapter(FragmentManager fm, int[] activityList) {
        super(fm);
        mactivityList = activityList;
    }

    @Override
    public Fragment getItem(int position) {



        switch (position) {
            case 0:
                AddWorksFragment addWorksFragment = new AddWorksFragment();
                Bundle args1 = new Bundle();
                args1.putInt("addActivity",mactivityList[position]);
                addWorksFragment.setArguments(args1);
                return addWorksFragment;

            case 1:
                TimerFragment timerFragment = new TimerFragment();
                Bundle args = new Bundle();
                args.putInt("timeActivity",mactivityList[position]);
                timerFragment.setArguments(args);
                return timerFragment;


            case 2:
                DisplayResultFragment displayResultFragment = new DisplayResultFragment();
                return displayResultFragment;

            case 3:

                SettingFragment settingFragment = new SettingFragment();
                return settingFragment;


            default:
                return new TimerFragment();
        }


    }


    @Override
    public int getCount() {
        return mactivityList.length;
    }



    @Override
    public int getPageIconResId(int position) {
        return iconImageRes[position];
    }
}
