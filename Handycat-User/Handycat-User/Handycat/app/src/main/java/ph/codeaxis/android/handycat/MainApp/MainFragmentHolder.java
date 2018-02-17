package ph.codeaxis.android.handycat.MainApp;

/**
 * Created by Timothy on 8/31/2017.
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ph.codeaxis.android.handycat.Controllers.ControllerInterface;
import ph.codeaxis.android.handycat.Controllers.NewsFeedController;
import ph.codeaxis.android.handycat.Controllers.PostController;
import ph.codeaxis.android.handycat.Controllers.TransactionController;
import ph.codeaxis.android.handycat.Controllers.User_Controller;
import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.Entity.Transaction;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.FreshStart.Landing;
import ph.codeaxis.android.handycat.FreshStart.SetupProfile;
import ph.codeaxis.android.handycat.MainApp.Drawer.DrawerHistory;
import ph.codeaxis.android.handycat.MainApp.Drawer.DrawerItems;
import ph.codeaxis.android.handycat.MainApp.Drawer.DrawerProfile;
import ph.codeaxis.android.handycat.MainApp.Drawer.DrawerWishlist;
import ph.codeaxis.android.handycat.R;

public class MainFragmentHolder extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ControllerInterface{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MainHolderCustomViewPager viewPager;
    private Animation fadeout;
    private View fab;
    private View actionB;
    private FrameLayout frameLayout;
    private User_Controller userController;
    private NewsFeedController newsFeedController;
    private TransactionController transactionController;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private TextView screenName;
    private TextView email;
    private View profilePictue;
    private View profileCover;
    public static User user;
    public static String userid;

    View hView;

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfragmentholder);

        // Animation

        fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        // Titles

        final String[] tabsTitles = {"Feed", "Wishlist", "Transactions", "Notifications"};

        // Adding the Toolbar

        firebaseAuth = FirebaseAuth.getInstance();
        userController = new User_Controller(this);
        newsFeedController = new NewsFeedController(this);
        transactionController = new TransactionController(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        newsFeedController.fetchPosts();
        transactionController.getTransaction();


        userid = firebaseAuth.getCurrentUser().getUid();
        userController.getUser(userid);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (MainHolderCustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feed");

        // Navigation Drawer

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        hView =  navigationView.getHeaderView(0);
        screenName = (TextView)hView.findViewById(R.id.screenName);
        email = (TextView)hView.findViewById(R.id.email);
        profilePictue = (View) hView.findViewById(R.id.profilepicture);
        profileCover = (View) hView.findViewById(R.id.profilecoverphoto);
        setProfile();

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_reorder_black_24dp));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                navigationView.bringToFront();
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Setting The Tabs

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(R.color.colorAccent));
        }

        // Getting Floating Action Button - fab

        actionB = findViewById(R.id.action_b);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        frameLayout.setVisibility(View.GONE);

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);

        menuMultipleActions.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                frameLayout.setVisibility(View.VISIBLE);
                viewPager.setPagingEnabled(false);

            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.setVisibility(View.GONE);
                viewPager.setPagingEnabled(true);
            }
        });

        final com.getbase.floatingactionbutton.FloatingActionButton actionA = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionA.setTitle("Action A clicked");
            }
        });

        final com.getbase.floatingactionbutton.FloatingActionButton actionC = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_c);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionC.setTitle("clicked");
            }
        });

        // View Pager

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int tempPos = 0;

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                // Sets Title base on what tab the user is on
                getSupportActionBar().setTitle(tabsTitles[position]);

                // Change Icon Color if Tab changed
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (position == 0) {
                        tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(R.color.colorAccent));
                        tabLayout.getTabAt(tempPos).getIcon().setTint(Color.parseColor("#ffffff"));
                        tempPos = position;
                    } else if (position == 1) {
                        tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(R.color.colorAccent));
                        tabLayout.getTabAt(tempPos).getIcon().setTint(Color.parseColor("#ffffff"));
                        tempPos = position;
                    } else if (position == 2) {
                        tabLayout.getTabAt(tempPos).getIcon().setTint(Color.parseColor("#ffffff"));
                        tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(R.color.colorAccent));
                        tempPos = position;
                    } else if (position == 3) {
                        tabLayout.getTabAt(tempPos).getIcon().setTint(Color.parseColor("#ffffff"));
                        tabLayout.getTabAt(3).getIcon().setTint(getResources().getColor(R.color.colorAccent));
                        tempPos = position;
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int pos) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_web_black_24dp));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_star_black_24dp));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_shopping_basket_black_24dp));
        tabLayout.getTabAt(3).setIcon(getResources().getDrawable(R.drawable.ic_public_black_24dp));
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFeedFragment(), getResources().getDrawable(R.drawable.ic_web_black_24dp));
        adapter.addFragment(new WishlistFragment(), getResources().getDrawable(R.drawable.ic_star_black_24dp));
        adapter.addFragment(new TransactionsFragment(), getResources().getDrawable(R.drawable.ic_shopping_basket_black_24dp));
        adapter.addFragment(new NotificationsFragment(), getResources().getDrawable(R.drawable.ic_public_black_24dp));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        screenName.setText(user.firstname + " " + user.lastname);
        email.setText(firebaseAuth.getCurrentUser().getEmail());
    }

    @Override
    public void addPost(Post post) {

    }

    @Override
    public void setTransaction(ArrayList<Transaction> transaction) {

    }

    @Override
    public void setError(int error) {

    }

    @Override
    public void setPost(ArrayList<Post> post) {

    }


    public void setProfile(){
        storageReference.child("Users").child(firebaseAuth.getCurrentUser().getUid() + "/" + "profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(hView.getContext()).load(uri).fit().centerCrop().into((ImageView)profilePictue);
            }
        });
        storageReference.child("Users").child(firebaseAuth.getCurrentUser().getUid() + "/" + "cover").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(hView.getContext()).load(uri).fit().centerCrop().into((ImageView)profileCover);
            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<Drawable> mFragmentIconList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, Drawable icon) {
            mFragmentList.add(fragment);
            mFragmentIconList.add(icon);
        }

        public CharSequence getPageTitle(int position) {
            return "";
        }

        public Drawable getDrawable(int position){
            return mFragmentIconList.get(position);
        }
    }

    // Adding Search Button - UI is in the res/menu/menu.xml

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent i = new Intent(MainFragmentHolder.this,DrawerProfile.class);
            startActivity(i);
        } else if (id == R.id.nav_item) {
            Intent i = new Intent(MainFragmentHolder.this,DrawerItems.class);
            startActivity(i);
        } else if (id == R.id.nav_mywishlist) {
            Intent i = new Intent(MainFragmentHolder.this,DrawerWishlist.class);
            startActivity(i);
        } else if (id == R.id.nav_history) {
            Intent i = new Intent(MainFragmentHolder.this,DrawerHistory.class);
            startActivity(i);
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            Intent i = new Intent(MainFragmentHolder.this,Landing.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}