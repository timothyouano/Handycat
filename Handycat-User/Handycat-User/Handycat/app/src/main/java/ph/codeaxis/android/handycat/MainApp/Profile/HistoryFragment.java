package ph.codeaxis.android.handycat.MainApp.Profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import ph.codeaxis.android.handycat.R;

/**
 * Created by Trisha on 27/09/2017.
 */

public class HistoryFragment extends Fragment {

    private ScrollView scrollView;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_drawer_profile_history, container,false);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        scrollView.bringToFront();


        return view;
    }
}
