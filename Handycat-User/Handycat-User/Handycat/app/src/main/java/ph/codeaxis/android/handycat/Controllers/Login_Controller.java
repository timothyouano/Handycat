package ph.codeaxis.android.handycat.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.FreshStart.Login;

/**
 * Created by Maria Himaya on 27/09/2017.
 */
public class Login_Controller {

    public User user;
    private Login view;
    FirebaseAuth firebaseAuth;

    public Login_Controller (Login view){ this.view = view; }

    public void validateLogin(String email, String pass){
        firebaseAuth = FirebaseAuth.getInstance();
        if (!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(pass)) {
           firebaseAuth.signInWithEmailAndPassword(email,pass)
                   .addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               view.setError(0); //successful
                           } else{
                               view.setError(2); // failed to login
                           }
                       }
                   });
        }else{
            view.setError(1); // empty field(s)
        }
    }

    public void registerFb(){ /* register user with his/her fb account */ }

}
