package ph.codeaxis.android.handycat.FreshStart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ph.codeaxis.android.handycat.Controllers.Login_Controller;
import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.MainApp.MainFragmentHolder;
import ph.codeaxis.android.handycat.R;

/**
 * Created by Timothy on 8/14/2017.
 */

public class Login extends AppCompatActivity{


    private EditText txtemail;
    private TextInputEditText etpass;
    private Button loginBtn;
    private Button loginfbBtn;
    private Button forgotpassBtn;

    private ProgressDialog dialog;

    private int error;

    public static Login_Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        controller = new Login_Controller(this);

        txtemail = (EditText) findViewById(R.id.etxtemail);
        etpass = (TextInputEditText) findViewById(R.id.etxtPassword);

        loginBtn = (Button) findViewById(R.id.loginbtn);
        loginfbBtn = (Button) findViewById(R.id.loginwithfacebookbtn);

        dialog = new ProgressDialog(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Logging in...");
                dialog.show();

                //VALIDATES THE LOGIN PROCESS
                controller.validateLogin( txtemail.getText().toString().trim(), etpass.getText().toString().trim());

            }
        });

        loginfbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginScreen = new Intent(Login.this, MainFragmentHolder.class);
                startActivity(loginScreen);

                // Animation right to left
                overridePendingTransition(R.anim.right_in, R.anim.left_in);
                Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT).show();

            }
        });



    }

    //ERROR TRAPPER
    public void setError(int error){

        switch (error){

            case 0 :{

                Intent loginScreen = new Intent(Login.this, MainFragmentHolder.class);
                startActivity(loginScreen);

                // Animation right to left
                overridePendingTransition(R.anim.right_in, R.anim.left_in);
                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                break;

            }

            case 1 :{

                Toast.makeText(getApplicationContext(), "Don't leave any textfields empty", Toast.LENGTH_SHORT).show();
                break;

            }

            default:{

                Toast.makeText(getApplicationContext(), "Failed to login", Toast.LENGTH_SHORT).show();

            }


        }

    }
}
