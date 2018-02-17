package ph.codeaxis.android.handycat.Controllers;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.FreshStart.LoadingScreen;
import ph.codeaxis.android.handycat.MainApp.NewsFeedFragment;
import ph.codeaxis.android.handycat.MainApp.QRScanner;

/**
 * Created by Timothy on 10/6/2017.
 */

public class NewsFeedController {

    private ControllerInterface view;
    DatabaseReference handycat;
    ArrayList<String> posts;
    ArrayList<Post> postsss;

    public NewsFeedController(ControllerInterface view){
        this.view = view;
        handycat = FirebaseDatabase.getInstance().getReference();
        postsss = new ArrayList<>();
        posts = new ArrayList<>();
    }




    public void fetchPosts(){
        DatabaseReference db = handycat.child("Post");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postsss.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Post post = data.getValue(Post.class);
                    postsss.add(post);
                }
                view.setPost(postsss);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
