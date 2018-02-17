package ph.codeaxis.android.handycat.MainApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import ph.codeaxis.android.handycat.Controllers.ControllerInterface;
import ph.codeaxis.android.handycat.Controllers.PostController;
import ph.codeaxis.android.handycat.Controllers.TransactionController;
import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.Entity.Transaction;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.R;
import java.util.Calendar;

/**
 * Created by Trisha on 09/10/2017.
 */

public class NewTransaction extends AppCompatActivity implements ControllerInterface {
    public EditText etxtdate, etxtexpirydate, etxtpcs;
    public TextView txttotal, txttransnum;
    public Button btnMakeTransaction;
    public  String transid;
    public String status;
    public int error;
    TransactionController transactionController;
    PostController postController;


    public Long itemPrice;
    public String sellerid, postid, sellername;
    public long total;

    public String a;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        sellerid = b.getString("ownerID");
        postid = b.getString("postID");
        itemPrice = b.getLong("price");
        sellername = b.getString("sellerName");
        setContentView(R.layout.activity_new_transaction);

        transactionController = new TransactionController(this);
        postController = new PostController(this);
        transid = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM / dd / yyyy ");
        String datetoday =  mdformat.format(calendar.getTime());
        calendar.add(calendar.DAY_OF_MONTH,3);
        String expirydate = mdformat.format(calendar.getTime());


        etxtdate = (EditText) findViewById(R.id.etxtdate);
        etxtexpirydate = (EditText) findViewById(R.id.etxtexpirydate);
        etxtpcs = (EditText) findViewById(R.id.etxtpcs);
        txttotal = (TextView) findViewById(R.id.txttotal);
        txttransnum = (TextView) findViewById(R.id.txttransnum);

        txttransnum.setText(transid);
        etxtdate.setText(datetoday);
        etxtexpirydate.setText(expirydate);

        etxtpcs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    txttotal.setText("");
                    txttotal.setHint("PHP 0.00");
                }
                else {
                    total = getTotal();
                    txttotal.setText("PHP "+Long.toString(total) + ".00");
                }
            }
        });


        btnMakeTransaction = (Button) findViewById(R.id.btnMakeTransaction);
        btnMakeTransaction.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Long pcs = Long.parseLong( etxtpcs.getText().toString());
                transactionController.createTransaction( transid,sellerid,postid, sellername, etxtdate.getText().toString().trim(), etxtexpirydate.getText().toString().trim(), itemPrice, pcs, total);
                if(error==0) {
                    postController.UpdateStatus(postid,"reserved");
                    Toast.makeText(getApplicationContext(), "Transaction Registered", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }

    @Override
    public void setTransaction(ArrayList<Transaction> transaction) {

    }

    @Override
    public void setError(int error) {
        this.error = error;
    }

    @Override
    public void setPost(ArrayList<Post> post) {

    }

    public Long getTotal(){
        return (Long.parseLong(etxtpcs.getText().toString())*itemPrice);
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public void addPost(Post post) {

    }


}
