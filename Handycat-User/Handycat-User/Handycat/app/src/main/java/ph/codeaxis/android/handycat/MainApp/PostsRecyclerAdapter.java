package ph.codeaxis.android.handycat.MainApp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ph.codeaxis.android.handycat.Controllers.PostController;
import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.R;

/**
 * Created by Trisha on 09/10/2017.
 */

public class PostsRecyclerAdapter extends RecyclerView.Adapter<PostsRecyclerAdapter.PostsViewHolder> {
    private ArrayList<Post> post;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    private Context context;
    private ViewPager viewPager;
    public PostController controller;

    public class PostsViewHolder extends RecyclerView.ViewHolder {
        public TextView name, namedesc, price;
        public ImageView profileImage;
        public Button btnTransact;

        public PostsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.postAuthor);
            namedesc = (TextView) view.findViewById(R.id.namedescription);
            price = (TextView) view.findViewById(R.id.price);
            profileImage = (ImageView)view.findViewById(R.id.profile_image);
            viewPager = (ViewPager) view.findViewById(R.id.image_pager);
            btnTransact = (Button) view.findViewById(R.id.transactionbtn);

        }
    }

    public PostsRecyclerAdapter(ArrayList<Post> post, Context context) {
        this.post = post;
        this.context = context;
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_newsfeed_row, parent, false);

        return new PostsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        final Post posted = post.get(position);
        holder.name.setText(posted.ownerName);
        holder.namedesc.setText(posted.description);
        holder.price.setText(posted.price + "");
        ImageSwipeAdapter adapter = new ImageSwipeAdapter(context);
        viewPager.setAdapter(adapter);
        //getProfile(posted.ownerID,holder.profileImage);
        if(posted.ownerID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            holder.btnTransact.setVisibility(View.GONE);
        }
        holder.btnTransact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                    if (posted.status.equals("available")) {

                            Intent intent = new Intent(context, NewTransaction.class);
                            intent.putExtra("postID", posted.postID);
                            intent.putExtra("ownerID", posted.ownerID);
                            intent.putExtra("price", posted.price);
                            intent.putExtra("sellerName", posted.getOwnerName());
                            context.startActivity(intent);

                    } else {
                        Toast.makeText(context.getApplicationContext(), "Item is currently unavailable", Toast.LENGTH_SHORT).show();
                    }


            }
        });

        //getProfile(posted.ownerID,holder.profileImage);
    }

    public void getProfile(String id, final ImageView picture){
        storageReference.child("Users").child(id + "/" + "profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri).fit().resize(10,10).centerCrop().into(picture);
            }
        });
    }

    @Override
    public int getItemCount() {
        return post.size();
    }
}
