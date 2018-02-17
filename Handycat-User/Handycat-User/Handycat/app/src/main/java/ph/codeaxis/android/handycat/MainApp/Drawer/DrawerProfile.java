package ph.codeaxis.android.handycat.MainApp.Drawer;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import ph.codeaxis.android.handycat.*;
import ph.codeaxis.android.handycat.Controllers.ControllerInterface;
import ph.codeaxis.android.handycat.Controllers.User_Controller;
import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.Entity.Transaction;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.MainApp.MainFragmentHolder;
import ph.codeaxis.android.handycat.MainApp.MainHolderCustomViewPager;
import ph.codeaxis.android.handycat.MainApp.Profile.HistoryFragment;
import ph.codeaxis.android.handycat.MainApp.Profile.InfoFragment;
import ph.codeaxis.android.handycat.MainApp.Profile.StatsFragment;

/**
 * Created by Trisha on 25/09/2017.
 */

public class DrawerProfile extends AppCompatActivity implements ControllerInterface{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MainHolderCustomViewPager viewPager;
    private TextView screenName;
    private TextView upvotes;
    private TextView downvotes;
    private FirebaseAuth firebaseAuth;
    private User_Controller controller;
    public static User user;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private ImageView profilePictue;
    private ImageView profileCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_profile);

        final String[] drawerprofiletabs = {"Stats", "Info", "History"};
        screenName = (TextView)findViewById(R.id.screenName);
        profileCover = (ImageView)findViewById(R.id.profilecover);
        profilePictue = (ImageView)findViewById(R.id.profilepicture);

        upvotes = (TextView) findViewById(R.id.upvotes);
        downvotes = (TextView) findViewById(R.id.downvotes);

        firebaseAuth = FirebaseAuth.getInstance();
        controller = new User_Controller(this);

        controller.getUser(firebaseAuth.getCurrentUser().getUid());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        setProfile();

        //Adding viewpager

        viewPager = (MainHolderCustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open navigation drawer when click navigation back button
                startActivity(new Intent(DrawerProfile.this, MainFragmentHolder.class));
                finish();
            }
        });

        // Setting The Tabs

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(R.color.colorAccent));
        }

        // View Pager

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int tempPos = 0;

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                // Sets Title base on what tab the user is on

                // Change Icon Color if Tab changed
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (position == 0) {
                        tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(R.color.colorAccent));
                        tabLayout.getTabAt(tempPos).getIcon().setTint(Color.parseColor("#727272"));
                        tempPos = position;
                    } else if (position == 1) {
                        tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(R.color.colorAccent));
                        tabLayout.getTabAt(tempPos).getIcon().setTint(Color.parseColor("#727272"));

                        tempPos = position;
                    } else if (position == 2) {
                        tabLayout.getTabAt(tempPos).getIcon().setTint(Color.parseColor("#727272"));
                        tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(R.color.colorAccent));
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

    public void setUser(User user){
        this.user = user;
        screenName.setText(user.firstname + " " + user.lastname );
        upvotes.setText("" + user.getUpvote());
        downvotes.setText("" + user.getDownvote());
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
                Picasso.with(DrawerProfile.this).load(uri).fit().centerCrop().into(profilePictue);
            }
        });
        storageReference.child("Users").child(firebaseAuth.getCurrentUser().getUid() + "/" + "cover").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(DrawerProfile.this).load(uri).fit().centerCrop().into(profileCover);
            }
        });
    }

    public void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_equalizer_black_24dp));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_perm_contact_calendar_black_24dp));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_history_gray_24dp));
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StatsFragment(),"Stats");
        adapter.addFragment(new InfoFragment(), "Info");
        adapter.addFragment(new HistoryFragment(), "History");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
