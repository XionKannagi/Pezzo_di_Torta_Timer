package android.lifeistech.com.pezzoditortatimer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;


import de.greenrobot.event.EventBus;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;

/**
 * Created by togane on 2016/02/25.
 */
public class AddWorksFragment extends Fragment {

    EditText addWorkName;

    NumberPicker hourPicker;
    NumberPicker minPicker;

    Button add_btn;

    WorksInfoDB worksInfoDB;

    String work_Name;


    /*
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        //EventBusを登録
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy(){
        //登録を解除
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    */



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SaveInstanceState) {

        //入力キーボード
        this.getActivity().getWindow().setSoftInputMode(SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View view = inflater.inflate(R.layout.add_work_activity, container, false);



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
        add_btn = (Button) view.findViewById(R.id.add_button);
        add_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //入力を記録
                        work_Name = addWorkName.getText().toString();

                        worksInfoDB = new WorksInfoDB();

                        if (work_Name != null && hourPicker.getValue() != 0 || minPicker.getValue() != 0) {


                            // DBに情報をinsert
                            worksInfoDB.workname = work_Name;
                            worksInfoDB.setValue = hourPicker.getValue() * 2 + minPicker.getValue() / 30;
                            worksInfoDB.remindValue = minPicker.getValue() % 30;
                            worksInfoDB.save();

                            //ボタンが押されたらediTtext内を初期化
                            addWorkName.getEditableText().clear();

                            //追加時に通知
                            Toast.makeText(getContext(), "Work is Added!", Toast.LENGTH_SHORT).show();

                            //決定のタイミングでイベントをポスト
                            EventBus.getDefault().post(new ClickEvent(true));
                        }

                    }
                }

        );


        return view;
    }







}
