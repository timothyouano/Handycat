package ph.codeaxis.android.handycat.MainApp.Profile;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import ph.codeaxis.android.handycat.Controllers.User_Controller;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.MainApp.MainFragmentHolder;
import ph.codeaxis.android.handycat.R;

/**
 * Created by Trisha on 27/09/2017.
 */

public class InfoFragment extends Fragment {

    private TextView contact;
    private TextView idnum;
    private TextView course;
    private View view;

    private User user;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_drawer_profile_info, container, false);
        contact = (TextView) view.findViewById(R.id.contact);
        idnum = (TextView) view.findViewById(R.id.idnum);
        course = (TextView) view.findViewById(R.id.course);

        setInfo(user);

        // Inflate the layout for this fragment
        return view;
    }

    public void setInfo(User user){
        user = MainFragmentHolder.user;
        this.user = user;
        contact.setText("" + user.getContact());
        idnum.setText("" + user.getIdNum());
        course.setText("" + user.getCourse());
    }
}
