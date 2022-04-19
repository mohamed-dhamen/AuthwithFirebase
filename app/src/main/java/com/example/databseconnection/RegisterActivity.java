package com.example.databseconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EmailPassword";
    EditText inputemail,inputpassword;
    TextView to_login;
    Button register;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputemail=(EditText)findViewById(R.id.inputemail_register);
        inputpassword=(EditText)findViewById(R.id.inputpassword_register);
        to_login=(TextView) findViewById(R.id.textview_tologin);
        register=(Button)findViewById(R.id.btn_register);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();
        register.setOnClickListener(this);
        to_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case  R.id.btn_register:
                createCount();
                break;
            case R.id.textview_tologin:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                break;
        }

    }

    private void createCount() {

        String email=inputemail.getText().toString();
        String password =inputpassword.getText().toString();
        if(email.isEmpty()){
            inputemail.setError("you must be enter your email");
            inputemail.requestFocus();
            return ;
        }
        if (password.isEmpty()){
            inputpassword.setError("you must be enter your password");
            inputpassword.requestFocus();
            return;
        }
        if (password.length()<6){
            inputpassword.setError("plaise give a pass have a letter then 6 caracters");
            inputpassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                           startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            updateUI(null);
                        }
                    }
                });

    }
    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
           reload();
        }
    }

}