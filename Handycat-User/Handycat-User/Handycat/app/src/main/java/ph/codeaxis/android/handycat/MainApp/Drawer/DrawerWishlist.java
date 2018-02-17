package ph.codeaxis.android.handycat.MainApp.Drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import ph.codeaxis.android.handycat.*;
import ph.codeaxis.android.handycat.MainApp.ImageSwipeAdapter;

/**
 * Created by Trisha on 25/09/2017.
 */

public class DrawerWishlist extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_my_wishlist);

        getSupportActionBar().setTitle("My Wishlist");
    }
}
