package android.lifeistech.com.pezzoditortatimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.activeandroid.query.Select;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by togane on 2016/03/03.
 */
public class DeleteDialogFragment extends DialogFragment {

    List<WorksInfoDB> worksInfoDB;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final int DBposition = getArguments().getInt("position");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("この作業を削除しますか？")
                .setPositiveButton("削除する", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        worksInfoDB = new Select().from(WorksInfoDB.class).execute();
                        worksInfoDB.get(DBposition).delete();

                        EventBus.getDefault().post(new ClickEvent(true));

                        dismiss();



                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dismiss();

            }
        });


        return builder.create();
    }
}
