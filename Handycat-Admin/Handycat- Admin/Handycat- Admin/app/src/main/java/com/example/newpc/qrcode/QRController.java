package com.example.newpc.qrcode;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QRController {

    private QRDirectory view;
    private DatabaseReference handycat;

    public QRController(QRDirectory view){
        this.view = view;
        handycat = FirebaseDatabase.getInstance().getReference();
    }

    public void searchTransID(final String id) {
        DatabaseReference db = handycat.child("Transaction").child(id);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                view.setFlag(dataSnapshot.getValue() != null? true : false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Debug", "iirorrr");
            }
        });
    }

}
