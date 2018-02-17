package ph.codeaxis.android.handycat.MainApp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import ph.codeaxis.android.handycat.Controllers.ControllerInterface;
import ph.codeaxis.android.handycat.Controllers.NewsFeedController;
import ph.codeaxis.android.handycat.Controllers.PostController;
import ph.codeaxis.android.handycat.Controllers.TransactionController;
import ph.codeaxis.android.handycat.Controllers.User_Controller;
import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.Entity.Transaction;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.FreshStart.LoadingScreen;
import ph.codeaxis.android.handycat.R;

public class QRScanner extends AppCompatActivity implements ControllerInterface{
    public String transno, postid;
    public String status;
    public TransactionController transactionController;
    public PostController postController;
    public NewsFeedController newsFeedController;
    public User_Controller userController;
    public int error;
    public static ArrayList<Post> post;
    public static ArrayList<Transaction> transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        transno = b.getString("transno");
        postid = b.getString("postid");
        status = b.getString("status");


        transactionController = new TransactionController(this);
        postController = new PostController(this);
        newsFeedController = new NewsFeedController(this);
        userController = new User_Controller(this);


        final Activity activity = this;
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning process", Toast.LENGTH_LONG).show();

            }
            else {
                if(result.getContents().equalsIgnoreCase(transno) && status.equals("reserved")) {

                    status = "paid";
                    transactionController.updateStatus(transno,status);

                    if(error==0) {
                        Toast.makeText(this, "Payment successful", Toast.LENGTH_LONG).show();
                       // userController.updateUserBuy(FirebaseAuth.getInstance().getCurrentUser().getUid(), 2);
                        userController.updateUserUpvotes(FirebaseAuth.getInstance().getCurrentUser().getUid(),2);


                    }else{
                        Toast.makeText(this, "Transaction failed. Please try again!", Toast.LENGTH_LONG).show();
                    }

                }
                else if(result.getContents().equalsIgnoreCase(transno) && status.equals("paid")) {

                    status = "claimed";
                    transactionController.updateStatus(transno, status);

                    if(error == 0) {
                        //userController.updateUserSell(FirebaseAuth.getInstance().getCurrentUser().getUid(), 2);
                        userController.updateUserUpvotes(FirebaseAuth.getInstance().getCurrentUser().getUid(),2);
                        transactionController.deleteTransaction(transno);
                        postController.deletePost(postid);
                        Toast.makeText(this, "Cash-out successful", Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(this, "Transaction failed. Please try again!", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(this, "Invalid Transaction number", Toast.LENGTH_LONG).show();

                }
                newsFeedController.fetchPosts();
                NewsFeedFragment.post = this.post;

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        finish();
    }

    @Override
    public void setTransaction(ArrayList<Transaction> transaction) {
        this.transaction = transaction;
    }

    @Override
    public void setError(int error) {
        this.error = error;
    }


    @Override
    public void setUser(User user) {
    }

    @Override
    public void addPost(Post post) {

    }

    public void setPost(ArrayList<Post> post){
        this.post = post;
    }


}
