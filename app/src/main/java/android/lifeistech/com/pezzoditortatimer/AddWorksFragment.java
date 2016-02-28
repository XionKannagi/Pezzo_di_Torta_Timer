package android.lifeistech.com.pezzoditortatimer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;


import static android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;

/**
 * Created by togane on 2016/02/25.
 */
public class AddWorksFragment extends Fragment {

    EditText addWorkName;

    NumberPicker hourPicker;
    NumberPicker minPicker;

    ImageButton imageButton;

    WorksInfoDB worksInfoDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SaveInstanceState) {

        //入力キーボード
        this.getActivity().getWindow().setSoftInputMode(SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View view = inflater.inflate(R.layout.add_work_activity, container, false);

        worksInfoDB = new WorksInfoDB();
        worksInfoDB.workname = "hpge";
        worksInfoDB.setValue = 0;
        worksInfoDB.remindValue = 0;
        worksInfoDB.save();

        //仕事の追加Edit_Text
        addWorkName = (EditText) view.findViewById(R.id.edit_work_name);

        //時間設定用ぴっかー
        hourPicker = (NumberPicker) view.findViewById(R.id.hour_picker);
        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);


        //分設定用ぴっかー
        minPicker = (NumberPicker) view.findViewById(R.id.min_picker);
        minPicker.setMaxValue(59);
        minPicker.setMinValue(0);

        //addボタン
        imageButton = (ImageButton) view.findViewById(R.id.add_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //入力を記録
                String work_Name = addWorkName.getText().toString();
                //insert
                worksInfoDB.workname = work_Name;
                worksInfoDB.setValue = hourPicker.getValue() * 2 + minPicker.getValue() / 30;
                worksInfoDB.remindValue = minPicker.getValue() % 30;
                worksInfoDB.save();

            }
        });


        return view;
    }


}
