package ph.codeaxis.android.handycat.MainApp.Drawer;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ph.codeaxis.android.handycat.*;
import ph.codeaxis.android.handycat.MainApp.ImageSwipeAdapter;

/**
 * Created by Trisha on 25/09/2017.
 */

public class DrawerHistory extends AppCompatActivity{

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_history);

        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        //getSupportActionBar().setTitle("History");
    }
}
