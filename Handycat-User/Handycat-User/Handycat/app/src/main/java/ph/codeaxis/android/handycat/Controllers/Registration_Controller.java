package ph.codeaxis.android.handycat.Controllers;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import ph.codeaxis.android.handycat.FreshStart.Register;


/**
 * Created by Maria Himaya on 26/09/2017.
 */

public class Registration_Controller {

    private Register view;
    FirebaseAuth firebaseAuth;
    User_Controller controller;

    public Registration_Controller(Register view){
        this.view = view;
    }

    public void validateRegistration(String email, String pass, String conpass, final ProgressDialog dialog) {
        firebaseAuth = FirebaseAuth.getInstance();
        controller = new User_Controller();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(conpass)) {
            if (pass.equals(conpass)) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    view.setError(0); //successful
                                    controller.createUser(firebaseAuth.getCurrentUser().getUid(), firebaseAuth.getCurrentUser().getEmail());
                                    dialog.dismiss();
                                } else {
                                    view.setError(3); //Failed to register
                                    dialog.dismiss();
                                }
                            }

                        });
            } else {
                view.setError(2); //pass and conpass didn't match
                dialog.dismiss();
            }
        } else {
            view.setError(1); ///empty field(s)
            dialog.dismiss();
        }
    }

    public void registerFb() { /* register user with his/her fb account */ }
}