package ph.codeaxis.android.handycat.Controllers;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.MainApp.NewsFeedFragment;

/**
 * Created by Timothy on 10/6/2017.
 */

public class PostController {
    private DatabaseReference handycat;
    private ControllerInterface screen;

    public PostController(ControllerInterface screen){
        this.screen = screen;
        handycat = FirebaseDatabase.getInstance().getReference();
    }

    public void createPost(String id, String postID, String name, String description,long price) {

        Post newpost = new Post();

        newpost.ownerName = name;
        newpost.description = description;
        newpost.likes = 0;
        newpost.postID = postID;
        newpost.price = price;
        newpost.ownerID = id;
        newpost.setStatus("available");

        handycat.child("Post").child(postID).setValue(newpost);
    }


    public void getPost(String id){
        DatabaseReference db = handycat.child("Post").child(id);
        db.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                Post p = dataSnapshot.getValue(Post.class);
                screen.addPost(p);
                //do what you want with the email
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void UpdateStatus(String id, String status){
        handycat.child("Post").child(id).child("status").setValue(status);
    }

    public void deletePost(String id){
        handycat.child("Post").child(id).removeValue();
        screen.setError(0);
    }

}
