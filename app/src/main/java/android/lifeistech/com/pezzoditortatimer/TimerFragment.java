package android.lifeistech.com.pezzoditortatimer;

import android.content.Context;
import android.content.SharedPreferences;
import android.lifeistech.com.pezzoditortatimer.R;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by togane on 2016/02/25.
 */
public class TimerFragment extends Fragment {

    TextView work_name;
    TextView time_text;
    TextView piece_text;
    TextView hole_text;

    boolean btn_flag = true;

    boolean state_flag = true;

    Button start_btn;


    //SharedPreferences savedTime = getActivity().getSharedPreferences("timeData", Context.MODE_PRIVATE);


    //private long nTime = savedTime.getLong("timeDataSave", 30000);

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
                time_text.setText(String.format("%1$02d:%2$02d", nTime / 1000 / 60, nTime / 1000 % 60));
                Toast.makeText(getContext(), "Time to rest", Toast.LENGTH_SHORT).show();
                start_btn.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp);
                //終了したセット数をカウント
                set++;
                piece_text.setText(String.valueOf(set));

            } else {

                btn_flag = true;
                state_flag = true;
                nTime = 30000;
                time_text.setText(Long.toString(nTime / 1000 / 60) + ":" + Long.toString(nTime / 1000 % 60));
                Toast.makeText(getContext(), "Time to Work!!", Toast.LENGTH_SHORT).show();
                start_btn.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp);

            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SaveInstanceState) {

        View view = inflater.inflate(R.layout.timer_activity_main, container, false);


        //List<WorksInfoDB> worksInfoDBs = new Select().from(WorksInfoDB.class).execute();

        WorksInfoDB infoData = WorksInfoDB.load(WorksInfoDB.class,0);
        String workname = infoData.workname;
        int setValue = infoData.setValue;
        int remaindValue = infoData.remindValue;

        //仕事表示用のTextView
        work_name = (TextView) view.findViewById(R.id.work_name);
        work_name.setText(workname);

        //時間表示用のTextView
        time_text = (TextView) view.findViewById(R.id.time_text);
        time_text.setText(String.format("%1$02d:%2$02d", nTime / 1000 / 60, nTime / 1000 % 60));


        piece_text = (TextView) view.findViewById(R.id.piece_text);
        piece_text.setText(setValue);

        hole_text = (TextView) view.findViewById(R.id.hole_text);
        hole_text.setText(remaindValue);


        start_btn = (Button) view.findViewById(R.id.start_btn);
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



        return view;
    }

}
