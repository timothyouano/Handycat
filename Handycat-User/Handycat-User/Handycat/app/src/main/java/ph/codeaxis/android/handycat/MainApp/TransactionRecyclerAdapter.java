package ph.codeaxis.android.handycat.MainApp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

/**
 * Created by Trisha on 09/10/2017.
 */

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.TransactionRowHolder> implements ControllerInterface {

    private ArrayList<Transaction> TransactionList;
    public TransactionController transactionController;
    public User_Controller user_controller;
    private TransactionsFragment fragment;
    public PostController postController;
    public NewsFeedController newsFeedController;
    public static ArrayList<Post> post;
    public static ArrayList<Transaction> transaction;
    public int error;
    public String id;
    public boolean flag = false;

    @Override
    public void setUser(User user) {

    }

    @Override
    public void addPost(Post post) {

    }

    @Override
    public void setTransaction(ArrayList<Transaction> transaction) {

    }

    public void setPost(ArrayList<Post> post){
        this.post = post;
    }

    @Override
    public void setError(int error) {
        this.error = error;
    }



    public class TransactionRowHolder extends RecyclerView.ViewHolder{
        public TextView product, seller, expdate, price, transno;
        public Button confirmbtn, cancelbtn;
        public String buyerid, postid, status,sellerid;

        public TransactionRowHolder(final View view) {
            super(view);
            newsFeedController = new NewsFeedController(fragment);
            transactionController = new TransactionController(fragment);
            postController = new PostController(fragment);
            user_controller = new User_Controller();
            product = (TextView) view.findViewById(R.id.product);
            price = (TextView) view.findViewById(R.id.price);
            seller = (TextView) view.findViewById(R.id.seller);
            expdate = (TextView) view.findViewById(R.id.expdate);
            transno = (TextView) view.findViewById(R.id.transno);

            confirmbtn = (Button) view.findViewById(R.id.confirmbtn);
            cancelbtn = (Button) view.findViewById(R.id.cancelbtn);

            confirmbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(),QRScanner.class);
                    intent.putExtra("transno", transno.getText().toString());
                    intent.putExtra("postid", postid);
                    intent.putExtra("status", status);
                    view.getContext().startActivity(intent);

                }
            });

            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    transactionController.deleteTransaction(transno.getText().toString());

                    if(error == 0) {
                        Toast.makeText(view.getContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                        user_controller.updateUserDownvotes(buyerid,2);
                        postController.UpdateStatus(postid,"available");
                    }
                    else Toast.makeText(view.getContext(), "Failed to delete transaction", Toast.LENGTH_SHORT).show();
                    newsFeedController.fetchPosts();
                    NewsFeedFragment.post = post;
                }
            });
        }
    }


    public TransactionRecyclerAdapter(ArrayList<Transaction> TransactionList, TransactionsFragment fragment) {
        this.TransactionList = TransactionList;
        this.fragment = fragment;
    }

    public void setList(ArrayList<Transaction> TransactionList){
        this.TransactionList = TransactionList;
    }

    @Override
    public TransactionRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transactions_row, parent, false);

        return new TransactionRowHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionRowHolder holder, int position) {
        Transaction transact = TransactionList.get(position);
        //holder.product.setText(transact.);
        holder.price.setText(Long.toString(transact.getPrice()));
        holder.seller.setText(transact.getSellername());
        holder.expdate.setText(transact.getExpirydate());
        holder.transno.setText(transact.getId());
        holder.buyerid = transact.buyerid;
        holder.postid = transact.postid;
        holder.status = transact.status;
        holder.sellerid = transact.sellerid;

        if(transact.sellerid.equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())){

            if(transact.status.equalsIgnoreCase("reserved")){
                holder.confirmbtn.setText("Pending Payment");
                holder.confirmbtn.setEnabled(false);
            }
            else if(transact.status.equalsIgnoreCase("paid")){
                holder.confirmbtn.setText("Confirm Cashout");
                holder.confirmbtn.setEnabled(true);
            }

            holder.confirmbtn.setBackgroundColor(Color.parseColor("#4cb5ab"));
            holder.cancelbtn.setEnabled(false);
            holder.cancelbtn.setVisibility(View.GONE);


        }

    }

    @Override
    public int getItemCount() {
        return TransactionList.size();
    }
}