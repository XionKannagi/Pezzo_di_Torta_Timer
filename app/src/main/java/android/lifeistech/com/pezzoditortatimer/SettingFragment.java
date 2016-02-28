package android.lifeistech.com.pezzoditortatimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SaveInstanceState){

        View view = inflater.inflate(R.layout.setting_activity, container, false);

        //仕事時間の設定用ぴっかー
        work_TimePicker = (NumberPicker)view.findViewById(R.id.setting_work_time);
        work_TimePicker.setMaxValue(60);
        work_TimePicker.setMinValue(15);
        work_TimePicker.setValue(WORK_TIME_INIT_VALUE);

        //休憩時間の設定用ぴっかー
        rest_TimePicker = (NumberPicker) view.findViewById(R.id.setting_rest_time);
        rest_TimePicker.setMaxValue(30);
        rest_TimePicker.setMinValue(5);
        rest_TimePicker.setValue(REST_TIME_INIT_VALUE);

        return view;
    }
}
