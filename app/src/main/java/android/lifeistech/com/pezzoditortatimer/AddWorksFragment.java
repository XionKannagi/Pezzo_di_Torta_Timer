package android.lifeistech.com.pezzoditortatimer;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker
        ;
import android.widget.Toast;


import com.activeandroid.query.Select;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

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

    ListView workList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SaveInstanceState) {

        //入力キーボード
        this.getActivity().getWindow().setSoftInputMode(SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View view = inflater.inflate(R.layout.add_work_activity, container, false);


        //仕事の追加Edit_Text
        addWorkName = (EditText) view.findViewById(R.id.edit_work_name);

        workList = (ListView) view.findViewById(R.id.works_ListView);
        workList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorksInfoDB data = (WorksInfoDB) parent.getItemAtPosition(position);


                //ダイアログを表示する
                DialogFragment dialogFragment = new DeleteDialogFragment();
                Bundle args = new Bundle();
                args.putInt("position", position);
                dialogFragment.setArguments(args);
                dialogFragment.show(getFragmentManager(), "Workの削除");


            }
        });


        //時間設定用ぴっかー
        hourPicker = (NumberPicker) view.findViewById(R.id.hour_picker);
        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);


        //分設定用ぴっかー
        minPicker = (NumberPicker) view.findViewById(R.id.min_picker);
        minPicker.setMaxValue(59);
        minPicker.setMinValue(0);

        //setDividerColor(minPicker);


        //addボタン
        add_btn = (Button) view.findViewById(R.id.add_button);
        add_btn.setOnClickListener(
                new View.OnClickListener()

                {
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

                            //ボタンが押されたらEditText内を初期化
                            addWorkName.getEditableText().clear();

                            //追加時に通知
                            Toast.makeText(getContext(), "Work is Added!", Toast.LENGTH_SHORT).show();

                            setWorksList();

                            //決定のタイミングでイベントをポスト（イベントの発火）
                            EventBus.getDefault().post(new ClickEvent(true));
                        }

                    }
                }

        );


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(ClickEvent clickEvent){
        setWorksList();
    }


    //登録した仕事をListViewで表示
    void setWorksList() {
        List<WorksInfoDB> worksInfoList = new Select().from(WorksInfoDB.class).execute();
        ArrayAdapter<WorksInfoDB> adapter = new ArrayAdapter<>(getContext().getApplicationContext(), R.layout.works_list_row, worksInfoList);

        workList.setAdapter(adapter);
    }

    //pickerの色を変更したい
    /*
    private void setDividerColor (NumberPicker myPicker) {
        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(myPicker, getResources().getColor(R.color.colorAccent));
                    Log.v("test", "here");
                    ColorDrawable colorDrawable =new ColorDrawable(getResources().getColor(R.color.colorAccent));
                    pf.set(myPicker,colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }*/


}
