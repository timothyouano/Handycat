package ph.codeaxis.android.handycat.FreshStart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import ph.codeaxis.android.handycat.Controllers.ControllerInterface;
import ph.codeaxis.android.handycat.Controllers.NewsFeedController;
import ph.codeaxis.android.handycat.Controllers.TransactionController;
import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.Entity.Transaction;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.MainApp.MainFragmentHolder;
import ph.codeaxis.android.handycat.MainApp.NewsFeedFragment;
import ph.codeaxis.android.handycat.R;

/**
 * Created by Timothy on 9/6/2017.
 */

public class LoadingScreen extends Activity implements ControllerInterface{

    private final int SPLASH_DISPLAY_LENGTH = 500; //set your time here......
    private ImageView logo;
    private View background;
    private FirebaseAuth firebaseAuth;
    private NewsFeedController controller;
    private TransactionController transController;
    public static ArrayList<Post> post;

    public static ArrayList<Transaction> transaction;


    public void setPost(ArrayList<Post> post){
       this.post = post;
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public void addPost(Post post) {

    }

    public void setTransaction(ArrayList<Transaction> transaction) {
        this.transaction = transaction;
    }

    @Override
    public void setError(int error) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loadingscreen);

        logo = (ImageView) findViewById(R.id.logo);
        background = (View) findViewById(R.id.loadingbackground);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                presentActivity(logo);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);
        this.post = new ArrayList<Post>();
        this.transaction = new ArrayList<Transaction>();
        controller = new NewsFeedController(this);
        controller.fetchPosts();
        transController = new TransactionController(this);
        transController.getTransaction();

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){

            Intent intent = new Intent(this, MainFragmentHolder.class);
            intent.putExtra(Landing.EXTRA_CIRCULAR_REVEAL_X, revealX);
            intent.putExtra(Landing.EXTRA_CIRCULAR_REVEAL_Y, revealY);
            intent.putExtra("loadpost",post);
            ActivityCompat.startActivity(this, intent, options.toBundle());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);
        }
        else{

            Intent intent = new Intent(this, Landing.class);
            intent.putExtra(MainFragmentHolder.EXTRA_CIRCULAR_REVEAL_X, revealX);
            intent.putExtra(MainFragmentHolder.EXTRA_CIRCULAR_REVEAL_Y, revealY);
            ActivityCompat.startActivity(this, intent, options.toBundle());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);

        }
    }
}
