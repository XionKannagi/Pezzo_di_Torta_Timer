package android.lifeistech.com.pezzoditortatimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by togane on 2016/02/26.
 */
public class DisplayResultFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SaveInstanceState){

        View view = inflater.inflate(R.layout.display_result_activity, container, false);

        return view;
    }
}
