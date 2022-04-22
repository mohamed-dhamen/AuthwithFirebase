package com.example.databseconnection.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseHelper {
    private DatabaseReference mDatabase;

    public FireBaseHelper() {



    }
    public void writeData(String userId,String username,String email){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        User user=new User(username,email);

        mDatabase.child("users").child(userId).setValue(user);


    }

}
