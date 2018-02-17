package ph.codeaxis.android.handycat.Controllers;


import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.MainApp.Drawer.DrawerProfile;

/**
 * Created by Maria Himaya on 30/09/2017.
 */

public class User_Controller {

    private DatabaseReference handycat;
    public User user = new User();
    public ArrayList<User> userlist;
    public long downvote;
    public long upvote;

    private ControllerInterface screen;

    public User_Controller(ControllerInterface screen) {
        handycat = FirebaseDatabase.getInstance().getReference();
        this.screen = screen;
    }

    public long getUpvote() {
        return upvote;
    }

    public void setUpvote(long upvote) {
        this.upvote = upvote;
    }

    public void setDownvote(long downvote) {
        this.downvote = downvote;
    }

    public long getDownvote() {
        return downvote;
    }

    public User_Controller(){
        handycat = FirebaseDatabase.getInstance().getReference();
    }

    public void createUser(String id, String email) {

        User newuser = new User();

        newuser.firstname = "set your firstname";
        newuser.lastname = "set your lastname";
        newuser.setEmail(email);
        newuser.course = "set your course";
        newuser.idNum = "set your idnum";
        newuser.address = "set your address";
        newuser.contact = "set your contactnum";
        newuser.sell = 0;
        newuser.buy = 0;
        newuser.rent = 0;
        newuser.donate = 0;
        newuser.trade = 0;
        newuser.upvote = 0;
        newuser.downvote = 0;

        handycat.child("Users").child(id).setValue(newuser);
    }

    public void updateUserFirstName(String id, String firstName) {
        handycat.child("Users").child(id).child("firstname").setValue(firstName);
    }

    public void updateUserLastName(String id, String lastName) {
        handycat.child("Users").child(id).child("lastname").setValue(lastName);
    }

    public void updateUserCourse(String id, String course) {
        handycat.child("Users").child(id).child("course").setValue(course);
    }

    public void updateUserIdNum(String id, String idNum) {
        handycat.child("Users").child(id).child("idNum").setValue(idNum);
    }

    public void updateUserAddress(String id, String address) {
        handycat.child("Users").child(id).child("address").setValue(address);
    }

    public void updateUserContact(String id, String contact) {
        handycat.child("Users").child(id).child("contact").setValue(contact);
    }

    public void updateUserBuy(final String id, final long sell) {
        handycat.child("Users").child(id).child("sell").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long usell = dataSnapshot.getValue(Long.TYPE);
                handycat.child("Users").child(id).child("upvote").setValue(usell+sell);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateUserSell(final String id, final long buy) {
        handycat.child("Users").child(id).child("buy").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long ubuy = dataSnapshot.getValue(Long.TYPE);
                handycat.child("Users").child(id).child("upvote").setValue(ubuy+buy);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateUserUpvotes(final String id, final long vote) {
        handycat.child("Users").child(id).child("upvote").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long uvote = dataSnapshot.getValue(Long.TYPE);
                handycat.child("Users").child(id).child("upvote").setValue(uvote+vote);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateUserDownvotes(final String id, final long vote) {
        handycat.child("Users").child(id).child("downvote").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long dvote = dataSnapshot.getValue(Long.TYPE);
                handycat.child("Users").child(id).child("downvote").setValue(dvote+vote);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getUser(String id) {
        DatabaseReference db = handycat.child("Users").child(id);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                screen.setUser(u);
                //do what you want with the email
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
