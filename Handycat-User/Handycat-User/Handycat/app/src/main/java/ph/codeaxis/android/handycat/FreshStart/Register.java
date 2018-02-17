package ph.codeaxis.android.handycat.FreshStart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ph.codeaxis.android.handycat.Controllers.Registration_Controller;
import ph.codeaxis.android.handycat.R;


public class Register extends AppCompatActivity {

    private EditText etxtemail;
    private TextInputEditText etpass,etconpass;

    private Button registerBtn, registerfbBtn;
    private String conpass;
    private ProgressDialog dialog;

    private int error;

    public static Registration_Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        controller = new Registration_Controller(this);
        etxtemail = (EditText) findViewById(R.id.editTextEmail);
        etpass = (TextInputEditText) findViewById(R.id.etPassword);
        etconpass = (TextInputEditText) findViewById(R.id.etConPassword);
        registerBtn = (Button) findViewById(R.id.registerbtn);
        registerfbBtn = (Button) findViewById(R.id.registerwithfacebookbtn);
        dialog = new ProgressDialog(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conpass = etconpass.getText().toString().trim();
                dialog.setMessage("Registering...");
                dialog.show();

                //VALIDATES REGISTER PROCESS
                controller.validateRegistration( etxtemail.getText().toString().trim(),etpass.getText().toString().trim(),conpass,dialog);
            }
        });

        registerfbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Successfully Registered", Toast.LENGTH_SHORT).show();
                Intent loginScreen = new Intent(Register.this,Tutorial.class);
                startActivity(loginScreen);
                finish();

                // Animation right to left
                overridePendingTransition(R.anim.right_in, R.anim.left_in);
            }
        });

    }

    //ERROR TRAPPER
    public void setError(int error){
        switch(error){
            case 0 : {

                Toast.makeText(getApplicationContext(),"Successfully Registered", Toast.LENGTH_SHORT).show();
                Intent loginScreen = new Intent(Register.this,Tutorial.class);
                startActivity(loginScreen);
                finish();

                // Animation right to left
                overridePendingTransition(R.anim.right_in, R.anim.left_in);

                break;
            }

            case 1 :{

                Toast.makeText(getApplicationContext(),"Don't leave any textfields empty", Toast.LENGTH_SHORT).show();
                break;

            }

            case 2 :{

                Toast.makeText(getApplicationContext(),"Password and Confirm password didn't match", Toast.LENGTH_SHORT).show();
                break;

            }

            default :{

                Toast.makeText(getApplicationContext(),"Failed to register" + error , Toast.LENGTH_SHORT).show();

            }

        }

    }

}
