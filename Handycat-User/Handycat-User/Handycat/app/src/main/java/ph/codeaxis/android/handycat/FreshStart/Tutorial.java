package ph.codeaxis.android.handycat.FreshStart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import ph.codeaxis.android.handycat.MainApp.MainFragmentHolder;
import ph.codeaxis.android.handycat.R;

/**
 * Created by Timothy on 8/30/2017.
 */

public class Tutorial extends Activity {

    ViewPager viewPager;
    TutorialSwipeAdapter adapter;
    private Button[] navigation;
    private Button continueBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tutorial);
        navigation = new Button[6];
        viewPager = (ViewPager) findViewById(R.id.tutorial_pager);
        continueBtn = (Button) findViewById(R.id.continuebtn);

        adapter = new TutorialSwipeAdapter(this);
        viewPager.setAdapter(adapter);

        navigation[0] = (Button)findViewById(R.id.nav1);
        navigation[1] = (Button)findViewById(R.id.nav2);
        navigation[2] = (Button)findViewById(R.id.nav3);
        navigation[3] = (Button)findViewById(R.id.nav4);
        navigation[4] = (Button)findViewById(R.id.nav5);
        navigation[5] = (Button)findViewById(R.id.nav6);

        for(int i = 0; i < 6; i++){
            navigation[i].setOnClickListener(new navListeners(i));
        }

        // Continue Button hide
        continueBtn.setVisibility(View.INVISIBLE);

        // Button Listeners

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                // Check if this is the page you want.
                changeNavColors();
                navigation[position].setBackgroundResource(R.drawable.currentnavigationcircle);

                if(position == 5){
                    continueBtn.setVisibility(View.VISIBLE);
                }
                else{
                    continueBtn.setVisibility(View.INVISIBLE);
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent setupScreen = new Intent(Tutorial.this,SetupProfile.class);
            finish();
            startActivity(setupScreen);
            // Animation right to left
            overridePendingTransition(R.anim.right_in, R.anim.left_in);
            }
        });


        navigation[0].setBackgroundResource(R.drawable.currentnavigationcircle);

    }

    public class navListeners implements View.OnClickListener{
        int value;

        public navListeners(int value){
            this.value = value;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(value);
            navigation[viewPager.getCurrentItem()].setBackgroundResource(R.drawable.currentnavigationcircle);
        }
    }

    public void changeNavColors(){
        for(int i = 0; i < 6; i++){
            navigation[i].setBackgroundResource(R.drawable.navigationcircles);
        }
    }

}
