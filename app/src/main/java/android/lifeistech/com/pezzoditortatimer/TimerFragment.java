package android.lifeistech.com.pezzoditortatimer;


import android.content.Context;
import android.content.SharedPreferences;
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

    TextView workNameText;
    TextView timeText;
    TextView pieceText;
    TextView hole_text;

    boolean btnFlag = true;

    boolean stateFlag = true;

    Button startBtn;

    WorksInfoDB worksInfoDB;

    List<WorksInfoDB> infoDatas;


    private static long nTime;

    private static long workTime;

    private static long restTime;

    private static MyCountDownTimer mCountDownTimer;

    int setNumber = 0;
    int hole = 0;
    int index = 0;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.d("TimerFragment", "showing!!!!");
            setTimes();
        } else {
            Log.d("TimerFragment", "hiding!!!!");
        }
    }


    int deleted_Position;

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

    @Subscribe
    public void onEvent(DeleteEvent deleteEvent) {


        Log.d("Delete", "call onEvent");

        if (infoDatas == null && index == deleted_Position) {
            workNameText.setText("Work_Name");
            index = 0;
        }
    }


    void dbSet() {

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {

        }
        workNameText.setText(infoDatas.get(index).workname);
    }


    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisUntilFinished, long countDownInterval) {
            super(millisUntilFinished, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timeText.setText(Long.toString(millisUntilFinished / 1000 / 60) + ":" + Long.toString(millisUntilFinished / 1000 % 60));
            nTime = millisUntilFinished; //現在時刻
        }

        @Override
        public void onFinish() {
            String toastText;

            // toastText = "Time to " + (stateFlag ? "rest" : "Work!!");

            if (stateFlag = !stateFlag) {

                stateFlag = true;
                toastText = "Time to rest";
                nTime = restTime;

            } else {
                stateFlag = false;
                toastText = "Time to Work!!";
                nTime = workTime;
                //終了したセット数をカウント
                updateWorksInfo();
            }

            btnFlag = true;
            startBtn.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp);
            timeText.setText(String.format("%1$02d:%2$02d", nTime / 1000 / 60, nTime / 1000 % 60));
            Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT).show();
        }
    }

    // 仕事情報の更新
    public void updateWorksInfo() {
        pieceText.setText(String.valueOf(++setNumber));

        if (index < infoDatas.size()) {

            Log.d("call Update", "index = " + index);

            WorksInfoDB item = infoDatas.get(index);
            item.setValue -= 1;
            item.save();

            Log.d("setValue", item.workname + "  setValue is " + item.setValue);


            //残りのセットがなければDBから削除
            if (item.setValue == 0 || item.remindValue == 0) {

                //終わった仕事を削除
                item.delete();
                infoDatas.remove(index);


                //終わった仕事にカウント
                hole++;
                hole_text.setText(String.valueOf(hole));

            } else {
                index++;
            }
        }

        if (index >= infoDatas.size()) {
            // 一番最初にもどる
            index = 0;
        }

        if (infoDatas.size() == 0) {
            // すべてのタスクを完了した時

            Toast.makeText(getContext(), "仕事が全部終わりました！！", Toast.LENGTH_SHORT).show();
            workNameText.setText("Work_Name");
            //AddフラグメントのListViewを更新
            EventBus.getDefault().post(new WorkFinishEvent(true));

            return;
        }

        //仕事表示用のTextView
        workNameText.setText(infoDatas.get(index).workname);
        //AddフラグメントのListViewを更新
        EventBus.getDefault().post(new WorkFinishEvent(true));
    }


    @Override
    public void onResume() {
        super.onResume();


        infoDatas = new Select().from(WorksInfoDB.class).execute();
        if (infoDatas.size() != 0) {
            workNameText.setText(infoDatas.get(index).workname);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SaveInstanceState) {
        View view = inflater.inflate(R.layout.timer_activity_main, container, false);

        workNameText = (TextView) view.findViewById(R.id.work_name);


        //時間表示用のTextView
        timeText = (TextView) view.findViewById(R.id.time_text);
        timeText.setText(String.format("%1$02d:%2$02d", nTime / 1000 / 60, nTime / 1000 % 60));


        pieceText = (TextView) view.findViewById(R.id.piece_text);
        pieceText.setText("0");

        hole_text = (TextView) view.findViewById(R.id.hole_text);
        hole_text.setText("0");


        startBtn = (Button) view.findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnFlag) {
                    btnFlag = false;
                    mCountDownTimer = new MyCountDownTimer(nTime, 1000);
                    mCountDownTimer.start();
                    startBtn.setBackgroundResource(R.drawable.ic_pause_circle_outline_white_24dp);


                } else {
                    btnFlag = true;
                    startBtn.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp);
                    mCountDownTimer.cancel();
                }
            }
        });

        return view;
    }

    public void setTimes() {
        SharedPreferences data = getActivity().getSharedPreferences("SaveData", Context.MODE_PRIVATE);
        workTime = (long)data.getInt("workTime", 25) * 1000 * 60;
        restTime = (long)data.getInt("restTime", 5) * 1000 * 60;
        Log.d("call setTimes", "worktime is" + workTime);
        nTime = workTime;

        timeText.setText(String.format("%1$02d:%2$02d", nTime / 1000 / 60, nTime / 1000 % 60));

    }


}
