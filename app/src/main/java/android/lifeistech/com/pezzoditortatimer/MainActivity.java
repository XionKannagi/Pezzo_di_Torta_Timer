package android.lifeistech.com.pezzoditortatimer;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //ViewPagerを紐ずけ
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);


        //PagerSlidingTabStrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setIndicatorColor(Color.GRAY);


        int[] activityList = {
                R.layout.timer_activity_main,
                R.layout.add_work_activity,
                R.layout.display_result_activity,
                R.layout.setting_activity};

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),activityList);

        viewPager.setAdapter(myPagerAdapter);

        tabStrip.setViewPager(viewPager);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_timer) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
