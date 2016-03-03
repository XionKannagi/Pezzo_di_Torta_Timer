package android.lifeistech.com.pezzoditortatimer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

/**
 * Created by togane on 2016/02/26.
 */
public class SettingFragment extends Fragment {

    private static final int WORK_TIME_INIT_VALUE = 25;

    private static final int REST_TIME_INIT_VALUE = 5;

    NumberPicker work_TimePicker;

    NumberPicker rest_TimePicker;

    int workTime,restTime;



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            Log.d("SettingFragment", "showing!!!!");
        }else{
            Log.d("SettingFragment", "hiding!!!!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SaveInstanceState){

        View view = inflater.inflate(R.layout.setting_activity, container, false);

        //仕事時間の設定用ぴっかー
        work_TimePicker = (NumberPicker)view.findViewById(R.id.setting_work_time);
        work_TimePicker.setMaxValue(60);
        work_TimePicker.setMinValue(15);
        work_TimePicker.setValue(WORK_TIME_INIT_VALUE);
        work_TimePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (oldVal != newVal) {
                    workTime = work_TimePicker.getValue();
                    SharedPreferences data = getActivity().getSharedPreferences("SaveDate", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = data.edit();
                    editor.putInt("workTime", workTime);
                    editor.apply();

                    Log.d("onValue","change Val" + workTime );
                }
            }
        });

        //休憩時間の設定用ぴっかー
        rest_TimePicker = (NumberPicker) view.findViewById(R.id.setting_rest_time);
        rest_TimePicker.setMaxValue(30);
        rest_TimePicker.setMinValue(5);
        rest_TimePicker.setValue(REST_TIME_INIT_VALUE);
        rest_TimePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                restTime = rest_TimePicker.getValue();
                SharedPreferences data = getActivity().getSharedPreferences("SaveDate", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = data.edit();
                editor.putInt("restTime",restTime);
                editor.apply();
            }
        });

        return view;
    }



}
