package android.lifeistech.com.pezzoditortatimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {

    TextView work_name;
    TextView time_text;
    TextView piece_text;
    TextView hole_text;

    boolean btn_flag = true;

    boolean state_flag = true;

    Button start_btn;

    private static long nTime = 30000;

    private static MyCountDownTimer mCountDownTimer;

    int set = 0;

    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisUntilFinished, long countDownInterval) {
            super(millisUntilFinished, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            time_text.setText(Long.toString(millisUntilFinished / 1000 / 60) + ":" + Long.toString(millisUntilFinished / 1000 % 60));
            nTime = millisUntilFinished; //現在時刻
        }

        @Override
        public void onFinish() {

            if (state_flag) {

                btn_flag = true;
                state_flag = false;
                nTime = 35000;
                time_text.setText(String.format("%1$02d:%2$02d",nTime / 1000 / 60,nTime / 1000 % 60));
                Toast.makeText(getApplicationContext(), "Time to rest", Toast.LENGTH_SHORT).show();
                start_btn.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp);
                //終了したセット数をカウント
                set++;
                piece_text.setText(String.valueOf(set));

            } else {

                btn_flag = true;
                state_flag = true;
                nTime = 30000;
                time_text.setText(Long.toString(nTime / 1000 / 60) + ":" + Long.toString(nTime / 1000 % 60));
                Toast.makeText(getApplicationContext(), "Time to Work!!", Toast.LENGTH_SHORT).show();
                start_btn.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp);

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //仕事表示用のTextView
        work_name = (TextView) findViewById(R.id.work_name);

        //時間表示用のTextView
        time_text = (TextView) findViewById(R.id.time_text);
        time_text.setText(String.format("%1$02d:%2$02d",nTime / 1000 / 60,nTime / 1000 % 60));


        piece_text = (TextView) findViewById(R.id.piece_text);
        piece_text.setText("0");

        hole_text = (TextView) findViewById(R.id.hole_text);
        hole_text.setText("0");


        start_btn = (Button) findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn_flag) {
                    btn_flag = false;
                    mCountDownTimer = new MyCountDownTimer(nTime, 1000);
                    mCountDownTimer.start();
                    start_btn.setBackgroundResource(R.drawable.ic_pause_circle_outline_white_24dp);


                } else {
                    btn_flag = true;
                    start_btn.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp);
                    mCountDownTimer.cancel();
                }
            }
        });

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
