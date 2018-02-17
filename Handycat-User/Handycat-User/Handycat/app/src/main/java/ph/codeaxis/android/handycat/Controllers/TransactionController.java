package ph.codeaxis.android.handycat.Controllers;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.Entity.Transaction;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.MainApp.NewTransaction;

/**
 * Created by Timothy on 10/9/2017.
 */

public class TransactionController {

    private ControllerInterface view;

    DatabaseReference handycat;
    FirebaseAuth firebaseAuth;
    ArrayList<Transaction> transactions = new ArrayList<Transaction>();


    public TransactionController(ControllerInterface view){
        this.view = view;
        handycat = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void createTransaction( String transid,String sellerid, String postid, String sellername, String date, String expirydate,long price, long pcs, long total){

        Transaction transaction = new Transaction();

        transaction.setDate(date);
        transaction.setExpirydate(expirydate);
        transaction.setPcs(pcs);
        transaction.setSellerid(sellerid);
        transaction.setBuyerid(firebaseAuth.getCurrentUser().getUid());
        transaction.setPrice(price);
        transaction.setTotal(total);
        transaction.setPostid(postid);
        transaction.setId(transid);
        transaction.setStatus("reserved");
        transaction.setSellername(sellername);

        handycat.child("Transaction").child(transid).setValue(transaction);
        view.setError(0);
    }

    public void getTransaction(){
        DatabaseReference db = handycat.child("Transaction");
        db.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactions.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Transaction p = data.getValue(Transaction.class);
                    Log.e(">>test",firebaseAuth.getCurrentUser().getUid().toString());
                    if( (p.buyerid.equalsIgnoreCase(firebaseAuth.getCurrentUser().getUid().toString()) && !p.status.equalsIgnoreCase("paid") ) | p.sellerid.equalsIgnoreCase(firebaseAuth.getCurrentUser().getUid().toString())){
                        transactions.add(p);
                    }
                }
                view.setTransaction(transactions);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateStatus(String id, String status) {
        handycat.child("Transaction").child(id).child("status").setValue(status);
        view.setError(0);
    }

    public void deleteTransaction(String id){
        handycat.child("Transaction").child(id).removeValue();
        view.setError(0);
    }




}
