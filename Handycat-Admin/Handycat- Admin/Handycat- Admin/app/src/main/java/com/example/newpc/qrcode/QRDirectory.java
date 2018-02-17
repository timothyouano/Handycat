package com.example.newpc.qrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRDirectory extends AppCompatActivity {

    public EditText text,textQR;
    public Button gen_btn, validate_btn;
    public ImageView imgQRCode;
    public String text2Qr;

    public boolean flag;
    public DatabaseReference handycat;
    public QRController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrdirectory);
        handycat = FirebaseDatabase.getInstance().getReference();
        controller = new QRController(this);
        text = (EditText) findViewById(R.id.editText1);
        textQR = (EditText) findViewById(R.id.txtQR);
        gen_btn = (Button) findViewById(R.id.gen_btn);
        validate_btn = (Button) findViewById(R.id.btnSearch);
        imgQRCode = (ImageView) findViewById(R.id.imgQRCode);

        gen_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                text2Qr = textQR.getText().toString().trim();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imgQRCode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        validate_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.searchTransID(text.getText().toString());
            }
        });
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
        if(flag){
            Toast.makeText(getApplicationContext(), "Proceed to QR Code Generation", Toast.LENGTH_SHORT).show();
            gen_btn.setEnabled(true);
            textQR.setText(text.getText().toString());
        }
        else {
            text.setText("");
            textQR.setText("");
            textQR.setEnabled(false);
            gen_btn.setEnabled(false);
            Toast.makeText(getApplicationContext(), "No transaction found.", Toast.LENGTH_SHORT).show();
        }
    }

}
