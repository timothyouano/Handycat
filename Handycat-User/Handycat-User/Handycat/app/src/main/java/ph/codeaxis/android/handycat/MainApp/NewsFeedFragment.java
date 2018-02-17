package ph.codeaxis.android.handycat.MainApp;

/**
 * Created by Timothy on 8/31/2017.
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ph.codeaxis.android.handycat.Controllers.ControllerInterface;
import ph.codeaxis.android.handycat.Controllers.User_Controller;
import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.Entity.Transaction;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.FreshStart.LoadingScreen;
import ph.codeaxis.android.handycat.MainApp.Drawer.DrawerProfile;
import ph.codeaxis.android.handycat.R;

public class NewsFeedFragment extends Fragment implements ControllerInterface{

    private View writePost;
    private FirebaseAuth firebaseAuth;

    private RecyclerView recyclerView;
    private PostsRecyclerAdapter mAdapter;
    private Button btnTransact;


    public static ArrayList<Post> post;

    public void setPost(ArrayList<Post> post) {
        this.post = post;
    }

    public NewsFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Log.e("GETGET",post.get(0).postID);
        this.setPost(LoadingScreen.post);
        firebaseAuth = FirebaseAuth.getInstance();
        String notBoldText = "";
        String boldText = "";
        String text = "";

        if(!post.isEmpty()){
            notBoldText = " " + post.get(0).description;
            boldText = post.get(0).ownerName;
            text = boldText + notBoldText;
        }

        final View view = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        /* ----------------- Temporary for UI only ---------------- */

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new PostsRecyclerAdapter(post,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        writePost = (View)view.findViewById(R.id.writepost);
        writePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WritePost.class);
                intent.putExtra("fullname",MainFragmentHolder.user.firstname + " " + MainFragmentHolder.user.lastname);
                startActivity(intent);
            }
        });


        /*// View Pager 1
        viewPager = (ViewPager) view.findViewById(R.id.image_pager);
        namedescription = (TextView) view.findViewById(R.id.namedescription);
        adapter = new ImageSwipeAdapter(this.getActivity());
        viewPager.setAdapter(adapter);

        // View Pager 2

        viewPager2 = (ViewPager) view.findViewById(R.id.image_pager2);
        namedescription2 = (TextView) view.findViewById(R.id.namedescription2);
        adapter = new ImageSwipeAdapter(this.getActivity());
        viewPager2.setAdapter(adapter);

        // View Pager 3

        viewPager3 = (ViewPager) view.findViewById(R.id.image_pager3);
        namedescription3 = (TextView) view.findViewById(R.id.namedescription3);
        adapter = new ImageSwipeAdapter(this.getActivity());
        viewPager3.setAdapter(adapter);


        boldPartOfText(namedescription, text, 0, boldText.length());
        boldPartOfText(namedescription2, text, 0, boldText.length());
        boldPartOfText(namedescription3, text, 0, boldText.length());*/

        /* -------------------------------------------------------- */

        return view;
    }

    public static void boldPartOfText(View mView, String contentData, int startIndex, int endIndex){
        if(!contentData.isEmpty() && contentData.length() > endIndex) {
            final SpannableStringBuilder sb = new SpannableStringBuilder(contentData);

            final StyleSpan bss = new StyleSpan(Typeface.BOLD); // Span to make text bold
            final StyleSpan iss = new StyleSpan(Typeface.NORMAL); //Span to make text normal
            sb.setSpan(iss, 0, startIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            sb.setSpan(bss, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
            sb.setSpan(iss,endIndex, contentData.length()-1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            if(mView instanceof TextView)
                ((TextView) mView).setText(sb);
            else if(mView instanceof EditText)
                ((EditText) mView).setText(sb);

        }
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public void addPost(Post post) {
        this.post.add(post);
    }

    @Override
    public void setTransaction(ArrayList<Transaction> transaction) {

    }

    @Override
    public void setError(int error) {

    }


}