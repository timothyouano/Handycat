package ph.codeaxis.android.handycat.MainApp;

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

import ph.codeaxis.android.handycat.R;

/**
 * Created by Timothy on 8/30/2017.
 */

public class ImageSwipeAdapter extends PagerAdapter {
    private Context ctx;
    private LayoutInflater layoutInflater;
    private int[] images = {R.drawable.dummy1,R.drawable.dummy2,R.drawable.dummy3};
    public ImageSwipeAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(ConstraintLayout)object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position){
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.image_swipe_layout,container,false);
        ImageView imageView = (ImageView)item_view.findViewById(R.id.image_container);
        imageView.setImageResource(images[position]);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((ConstraintLayout)object);
    }
}
