package ph.codeaxis.android.handycat.FreshStart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import ph.codeaxis.android.handycat.Controllers.User_Controller;
import ph.codeaxis.android.handycat.MainApp.Drawer.DrawerProfile;
import ph.codeaxis.android.handycat.MainApp.MainFragmentHolder;
import ph.codeaxis.android.handycat.R;

/**
 * Created by Timothy on 10/5/2017.
 */

public class SetupProfile extends Activity {

    private FirebaseAuth firebaseAuth;
    private EditText firstName;
    private EditText lastName;
    private EditText course;
    private EditText idnum;
    private EditText address;
    private EditText contact;
    private Button confirmBtn;
    private Button profilePictureBtn;
    private Button profileCoverBtn;
    private User_Controller controller;
    private ImageView profilePicture;
    private ImageView profileCover;
    private ProgressDialog progressDialog;

    private Uri profileURI;
    private Uri coverURI;

    private static final int PROFILE_INTENT = 1;
    private static final int COVER_INTENT = 2;

    boolean flag1 = false,flag2 = false;

    private int error;
    private int value = 10;
    private boolean profBol = false, coverBol = false;

    private FirebaseStorage firebaseStorage;
    private StorageReference storage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        course = (EditText)findViewById(R.id.course);
        idnum = (EditText)findViewById(R.id.idnum);
        address = (EditText)findViewById(R.id.address);
        contact = (EditText)findViewById(R.id.contact);
        confirmBtn = (Button)findViewById(R.id.confirmBtn);
        profilePictureBtn = (Button)findViewById(R.id.profilepicturebtn);
        profileCoverBtn = (Button)findViewById(R.id.profilecoverbtn);
        profilePicture = (ImageView)findViewById(R.id.profilepicture);
        profileCover = (ImageView)findViewById(R.id.profilecover);
        controller = new User_Controller();
        firebaseStorage = FirebaseStorage.getInstance();
        storage = firebaseStorage.getReference();

        progressDialog = new ProgressDialog(this);

        profilePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PROFILE_INTENT);
            }
        });

        profileCoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, COVER_INTENT);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            firebaseAuth = FirebaseAuth.getInstance();
            if (firebaseAuth.getCurrentUser() != null) {
                if(!TextUtils.isEmpty(firstName.getText().toString().trim()) && !TextUtils.isEmpty(lastName.getText().toString().trim()) && !TextUtils.isEmpty(address.getText().toString().trim()) && !TextUtils.isEmpty(contact.getText().toString().trim()) && !TextUtils.isEmpty(course.getText().toString().trim()) && !TextUtils.isEmpty(idnum.getText().toString().trim())) {
                    if(profBol && coverBol) {
                        progressDialog.setMessage("Uploading Images...");
                        progressDialog.show();

                        controller.updateUserFirstName(firebaseAuth.getCurrentUser().getUid(), firstName.getText().toString());
                        controller.updateUserLastName(firebaseAuth.getCurrentUser().getUid(), lastName.getText().toString());
                        controller.updateUserAddress(firebaseAuth.getCurrentUser().getUid(), address.getText().toString());
                        controller.updateUserContact(firebaseAuth.getCurrentUser().getUid(), contact.getText().toString());
                        controller.updateUserCourse(firebaseAuth.getCurrentUser().getUid(), course.getText().toString());
                        controller.updateUserIdNum(firebaseAuth.getCurrentUser().getUid(), idnum.getText().toString());

                        final StorageReference profilePath = storage.child("Users").child(firebaseAuth.getCurrentUser().getUid() + "/" + "profile");
                        profilePath.putFile(profileURI)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                flag1 = true;

                                if (flag1 && flag2) {
                                    progressDialog.dismiss();
                                    setError(0);

                                    controller.updateUserUpvotes(firebaseAuth.getCurrentUser().getUid(), 10);
                                }
                            }
                        });

                        final StorageReference coverPath = storage.child("Users").child(firebaseAuth.getCurrentUser().getUid() + "/" + "cover");
                        coverPath.putFile(coverURI)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                flag2 = true;

                                if (flag1 && flag2) {
                                    progressDialog.dismiss();
                                    setError(0);

                                    controller.updateUserUpvotes(firebaseAuth.getCurrentUser().getUid(), 10);
                                }
                            }
                        });
                    }
                    else if(!profBol && !coverBol){
                        setError(2);
                    }
                    else if(!profBol && coverBol){
                        setError(3);
                    }
                    else if(profBol && !coverBol){
                        setError(4);
                    }
                } else {
                   setError(1); // case 1 if textfield is empty
                }
            } else {
                setError(5);
            }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PROFILE_INTENT && resultCode == RESULT_OK){
            Uri uri = data.getData();
            profileURI = uri;
            Picasso.with(SetupProfile.this).load(uri).fit().centerCrop().into(profilePicture);
            profBol = true;
        }

        if(requestCode == COVER_INTENT && resultCode == RESULT_OK){
            Uri uri = data.getData();
            coverURI = uri;
            Picasso.with(SetupProfile.this).load(uri).fit().centerCrop().into(profileCover);
            coverBol = true;
        }
        if(requestCode == PROFILE_INTENT && resultCode == RESULT_CANCELED){
            profBol = false;
        }
        if(requestCode == COVER_INTENT && resultCode == RESULT_CANCELED){
            coverBol = false;
        }
    }

    public void setError(int error){
        switch (error){
            case 0: {
                Toast.makeText(getApplicationContext(),"Successfully added your info. You earned 10 upvotes for that!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(SetupProfile.this, MainFragmentHolder.class);
                finish();
                startActivity(i);
                break;
            }
            case 1: {
                Toast.makeText(getApplicationContext(),"Don't leave any textfields empty", Toast.LENGTH_SHORT).show();
                break;
            }
            case 2: {
                Toast.makeText(getApplicationContext(),"Please add profile and cover photo", Toast.LENGTH_SHORT).show();
                break;
            }
            case 3: {
                Toast.makeText(getApplicationContext(),"Please add profile photo", Toast.LENGTH_SHORT).show();
                break;
            }
            case 4: {
                Toast.makeText(getApplicationContext(),"Please add cover photo", Toast.LENGTH_SHORT).show();
                break;
            }
            default: {
                Toast.makeText(getApplicationContext(),"Failed to add informations" + error , Toast.LENGTH_SHORT).show();
            }
        }
    }
}
