package android.lifeistech.com.pezzoditortatimer;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by togane on 2016/02/27.
 */

@Table(name = "WorksInfo_table")
public class WorksInfoDB extends Model {

    @Column(name = "Work_Name")
    public String workname;

    @Column(name = "set_value")
    public int setValue;

    @Column(name = "remind_value")
    public int remindValue;

}
