package com.example.databseconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
        TextView toregister;
        TextView inputemail,inputpassword;
        Button button_login;
        FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toregister=(TextView)findViewById(R.id.textview_toregister);
        inputemail=(TextView)findViewById(R.id.inputemail_login);
        inputpassword=(TextView)findViewById(R.id.inputpassword_login);
        button_login=(Button)findViewById(R.id.btn_login);
        mAuth=FirebaseAuth.getInstance();
        toregister.setOnClickListener(this);
        button_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.textview_toregister:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.btn_login:
                signIn();
            case R.id.textview_forgetpassword:
                break;
        }

    }

    private void signIn() {

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
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            // Sign in success, update UI with the signed-in user's information
                            Log.i("message", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }else{

                            // If sign in fails, display a message to the user.
                            Log.i("message", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
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