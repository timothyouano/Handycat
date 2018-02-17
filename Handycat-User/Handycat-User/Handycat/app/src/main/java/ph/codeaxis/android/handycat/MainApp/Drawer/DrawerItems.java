package ph.codeaxis.android.handycat.MainApp.Drawer;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import ph.codeaxis.android.handycat.*;
import ph.codeaxis.android.handycat.MainApp.ImageSwipeAdapter;

/**
 * Created by Trisha on 25/09/2017.
 */

public class DrawerItems extends AppCompatActivity{

    ViewPager viewPager, viewPager2, viewPager3;
    ImageSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_items);

        getSupportActionBar().setTitle("My Items");

        /* ----------------- Temporary for UI only ---------------- */

        // View Pager 1
        viewPager = (ViewPager) findViewById(R.id.image_pager);
        adapter = new ImageSwipeAdapter(this);
        viewPager.setAdapter(adapter);

        // View Pager 2

        viewPager2 = (ViewPager) findViewById(R.id.image_pager2);
        adapter = new ImageSwipeAdapter(this);
        viewPager2.setAdapter(adapter);

        // View Pager 3

        viewPager3 = (ViewPager) findViewById(R.id.image_pager3);
        adapter = new ImageSwipeAdapter(this);
        viewPager3.setAdapter(adapter);

        /* -------------------------------------------------------- */
    }

}
