package ph.codeaxis.android.handycat.MainApp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.UUID;

import ph.codeaxis.android.handycat.Controllers.ControllerInterface;
import ph.codeaxis.android.handycat.Controllers.PostController;
import ph.codeaxis.android.handycat.Controllers.User_Controller;
import ph.codeaxis.android.handycat.Entity.Post;
import ph.codeaxis.android.handycat.Entity.Transaction;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.FreshStart.SetupProfile;
import ph.codeaxis.android.handycat.MainApp.Drawer.DrawerProfile;
import ph.codeaxis.android.handycat.R;

import static android.R.attr.bitmap;

/**
 * Created by Timothy on 10/6/2017.
 */

public class WritePost extends AppCompatActivity implements ControllerInterface {

    private Button confirm;
    private EditText description;
    private PostController controller;
    private User_Controller user_controller;
    private String name;
    private User user;
    private TextView screenName;

    private ImageView photo1;
    private ImageView photo2;
    private ImageView photo3;

    private EditText price;

    private FirebaseStorage firebaseStorage;
    private StorageReference storage;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private Button continueBtn;

    private Uri uri;

    private Uri uriphoto1;
    private Uri uriphoto2;
    private Uri uriphoto3;;

    private boolean flag1,flag2,flag3;

    private static final int PHOTO1 = 1;
    private static final int PHOTO2 = 2;
    private static final int PHOTO3 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        confirm = (Button)findViewById(R.id.confirm);
        description = (EditText)findViewById(R.id.input);
        screenName = (TextView)findViewById(R.id.screenName);
        photo1 = (ImageView) findViewById(R.id.photo1);
        photo2 = (ImageView) findViewById(R.id.photo2);
        photo3 = (ImageView) findViewById(R.id.photo3);
        continueBtn = (Button)findViewById(R.id.post);
        price = (EditText)findViewById(R.id.price);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storage = firebaseStorage.getReference();

        progressDialog = new ProgressDialog(this);

        flag1 = false;
        flag2 = false;
        flag3 = false;

        uriphoto1 = null;
        uriphoto1 = null;
        uriphoto1 = null;

        user_controller = new User_Controller(this);
        user_controller.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
        controller = new PostController(this);

        user = MainFragmentHolder.user;

        screenName.setText(user.firstname + " " + user.lastname);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Post to Feed");

        photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder getImageFrom = new AlertDialog.Builder(WritePost.this);
                getImageFrom.setTitle("Choose Method");
                final CharSequence[] opsChars = {"Take a Picture", "Open Gallery"};
                getImageFrom.setItems(opsChars, new android.content.DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, PHOTO1);
                        }else
                        if(which == 1){
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Pick Gallery"), PHOTO1);
                        }
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder getImageFrom = new AlertDialog.Builder(WritePost.this);
                getImageFrom.setTitle("Choose Method");
                final CharSequence[] opsChars = {"Take a Picture", "Open Gallery"};
                getImageFrom.setItems(opsChars, new android.content.DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, PHOTO2);
                        }else
                        if(which == 1){
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Pick Gallery"), PHOTO2);
                        }
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder getImageFrom = new AlertDialog.Builder(WritePost.this);
                getImageFrom.setTitle("Choose Method");
                final CharSequence[] opsChars = {"Take a Picture", "Open Gallery"};
                getImageFrom.setItems(opsChars, new android.content.DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, PHOTO3);
                        }else
                        if(which == 1){
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Pick From Gallery"), PHOTO3);
                        }
                        dialog.dismiss();
                    }
                }).show();
            }
        });

    }

    @Override
    public void setUser(User user) {
        this.user = user;
        name = user.firstname + " " + user.lastname;
    }

    @Override
    public void addPost(Post post) {

    }

    @Override
    public void setTransaction(ArrayList<Transaction> transaction) {

    }

    @Override
    public void setError(int error) {

    }

    @Override
    public void setPost(ArrayList<Post> post) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PHOTO1 && resultCode == RESULT_OK){

            Uri uri = data.getData();

            uriphoto1 = uri;

            Picasso.with(WritePost.this).load(uri).fit().centerCrop().into(photo1);

        }

        if(requestCode == PHOTO2 && resultCode == RESULT_OK){

            Uri uri = data.getData();

            uriphoto2 = uri;

            Picasso.with(WritePost.this).load(uri).fit().centerCrop().into(photo2);

        }
        if(requestCode == PHOTO3 && resultCode == RESULT_OK){

            Uri uri = data.getData();

            uriphoto3 = uri;

            Picasso.with(WritePost.this).load(uri).fit().centerCrop().into(photo3);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post, menu);
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String postID = UUID.randomUUID().toString();

                progressDialog.setMessage("Uploading Images...");
                progressDialog.show();

                if(uriphoto1 != null){
                    StorageReference filePath1 = storage.child("Posts").child(postID + "/" + "photo1");
                    filePath1.putFile(uriphoto1)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            finish();
                        }
                    });
                }
                if(uriphoto2 != null) {
                    StorageReference filePath2 = storage.child("Posts").child(postID + "/" + "photo2");
                    filePath2.putFile(uriphoto2)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            finish();
                        }
                    });
                }
                if(uriphoto3 != null){
                    StorageReference filePath3 = storage.child("Posts").child(postID + "/" + "photo3");
                    filePath3.putFile(uriphoto3)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            finish();
                        }
                    });
                }

                controller.createPost(MainFragmentHolder.userid,postID,user.firstname + " " + user.lastname,description.getText().toString(),Long.parseLong(price.getText().toString()));
                Toast.makeText(getApplicationContext(), "Successfully posted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: finish(); break;
        }
        return super.onOptionsItemSelected(item);
    }
}
