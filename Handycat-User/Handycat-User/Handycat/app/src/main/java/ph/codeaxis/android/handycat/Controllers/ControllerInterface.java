package ph.codeaxis.android.handycat.Controllers;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.Entity.Transaction;
import ph.codeaxis.android.handycat.Entity.User;

/**
 * Created by Timothy on 10/5/2017.
 */

public interface ControllerInterface {

    //Get user from firebase
    void setUser(User user);
    void  addPost(Post post);
    void setTransaction(ArrayList<Transaction> transaction);
    void setError(int error);
    void setPost(ArrayList<Post> post);
}
