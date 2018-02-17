package ph.codeaxis.android.handycat.MainApp;

/**
 * Created by Trisha on 8/31/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ph.codeaxis.android.handycat.Controllers.ControllerInterface;
import ph.codeaxis.android.handycat.Controllers.TransactionController;

import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.Entity.Transaction;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.FreshStart.LoadingScreen;
import ph.codeaxis.android.handycat.R;

public class TransactionsFragment extends Fragment implements ControllerInterface {

    private LinearLayout[] messageBtn; // Treated as button
    private ArrayList<Transaction> transact = new ArrayList<Transaction>();
    private CircleImageView[] profile;
    private TextView[] message;
    private RecyclerView recyclerView;
    private TransactionRecyclerAdapter mAdapter;
    public TransactionController controller;
    public TransactionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        setTransaction(LoadingScreen.transaction);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new TransactionRecyclerAdapter(transact,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public void addPost(Post post) {

    }

    @Override
    public void setTransaction(ArrayList<Transaction> transaction) {
        this.transact = transaction;
    }

    @Override
    public void setError(int error) {

    }

    @Override
    public void setPost(ArrayList<Post> post) {

    }

}