package ph.codeaxis.android.handycat.FreshStart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ph.codeaxis.android.handycat.MainApp.MainFragmentHolder;
import ph.codeaxis.android.handycat.R;

/**
 * Created by Timothy on 8/30/2017.
 */

public class TutorialSwipeAdapter extends PagerAdapter {
    private Context ctx;
    private LayoutInflater layoutInflater;
    private final String[] descriptions = {"Welcome to HandyCat!", "This is the newsfeed, it is where you view latest posts of users like you.", "This is the transaction, it is where transactions are done.", "This is the notifications, this is where you view latest news and updates about your account.", "This is the app drawer. It contains your Profile, Items, Gistory, My Wishlist, and Logout Button.", "Congratulations! You have completed the getting started tutorial. You can now add your personal informations."};
    private final int[] image_icons = {0, R.drawable.ic_web_black_24dp, R.drawable.ic_shopping_basket_black_24dp, R.drawable.ic_public_black_24dp, R.drawable.ic_reorder_black_24dp,0};

    public TutorialSwipeAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return descriptions.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(ConstraintLayout)object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position){
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView = (ImageView)item_view.findViewById(R.id.icon);
        imageView.setImageResource(image_icons[position]);
        TextView textView = (TextView) item_view.findViewById(R.id.description);
        Button skipBtn = (Button) item_view.findViewById(R.id.skipbutton);

        textView.setText("" + descriptions[position]);
        container.addView(item_view);

        // Hide Skip in Congratulations part
        if(position == 5){
            skipBtn.setVisibility(View.INVISIBLE);
        }

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((ConstraintLayout)object);
    }

    public void directProfile(Button skip){}
}
