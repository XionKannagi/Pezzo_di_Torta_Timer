package android.lifeistech.com.pezzoditortatimer;


import android.app.LoaderManager;
import android.content.ClipData;
import android.content.Loader;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    List<WorksInfoDB> infoDatas;


    private static long nTime = 25000;

    private static long workTime = 25000;

    private static long restTime = 5000;

    private static MyCountDownTimer mCountDownTimer;

    int set = 0;
    int hole = 0;
    int index = 1;

    //分を綺麗に表示するためにものそのうち実装
    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EventBus.getDefault().register(this);

    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    //追加Buttonが押された時のEvent
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ClickEvent clickFlag) {

        Log.d("call EventBus", "onEvent");


        infoDatas = new Select().from(WorksInfoDB.class).execute();
        dbSet();

    }


    void dbSet() {

        try {
            Thread.sleep(30);
        } catch (InterruptedException e){

        }

        work_name.setText(infoDatas.get(index).workname);
    }


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
                nTime = restTime;
                time_text.setText(String.format("%1$02d:%2$02d", nTime / 1000 / 60, nTime / 1000 % 60));
                Toast.makeText(getContext(), "Time to rest", Toast.LENGTH_SHORT).show();
                start_btn.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp);
                //終了したセット数をカウント
                set++;
                piece_text.setText(String.valueOf(set));

            } else {

                btn_flag = true;
                state_flag = true;
                nTime = workTime;
                time_text.setText(Long.toString(nTime / 1000 / 60) + ":" + Long.toString(nTime / 1000 % 60));
                Toast.makeText(getContext(), "Time to Work!!", Toast.LENGTH_SHORT).show();
                start_btn.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp);
                index++;//インデックス更新
                upDate();
            }
        }
    }

    //仕事情報の更新
    public void upDate() {
        if (infoDatas.size() > index) {

            Log.d("call Update", "index = " + index );

            //仕事表示用のTextView
            work_name.setText(infoDatas.get(index).workname);

            //setを一個引く
            infoDatas.get(index).setValue--;
            //残りのセットがなければDBから削除
            if (infoDatas.get(index).setValue == 0) {

                //終わった仕事を削除
                infoDatas.get(index).delete();
                /*
                //終わった仕事にカウント
                hole++;
                hole_text.setText(hole);
                */
            }


        } else {
            index = 0;
            //仕事表示用のTextView
            work_name.setText(infoDatas.get(index).workname);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        infoDatas = new Select().from(WorksInfoDB.class).execute();
        if (index<infoDatas.size()) {
            upDate();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SaveInstanceState) {
        View view = inflater.inflate(R.layout.timer_activity_main, container, false);

        work_name = (TextView) view.findViewById(R.id.work_name);


        //時間表示用のTextView
        time_text = (TextView) view.findViewById(R.id.time_text);
        time_text.setText(String.format("%1$02d:%2$02d", nTime / 1000 / 60, nTime / 1000 % 60));


        piece_text = (TextView) view.findViewById(R.id.piece_text);
        piece_text.setText("0");

        hole_text = (TextView) view.findViewById(R.id.hole_text);
        hole_text.setText("0");


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
