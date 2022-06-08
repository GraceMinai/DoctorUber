package com.twinternet.druber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity

{
    public TextView signIn;
    public EditText fullName, email, phoneNumber, password, confirmPassword;
    public Button signUp;

    private  FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = firebaseDatabase.getReference().child("Users");
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();


        mAuth = FirebaseAuth.getInstance();



        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("We are creating your account...");



        signIn = findViewById(R.id.tv_signIn);
        fullName = findViewById(R.id.edt_SignUp_FullName);
        email = findViewById(R.id.edt_SignUp_Email);
        phoneNumber = findViewById(R.id.edt_SignUp_phoneNumber);
        password = findViewById(R.id.edt_SignUp_Password);
        confirmPassword = findViewById(R.id.edt_SignUp_ConfirmPassword);
        signUp = findViewById(R.id.btn_signUp);




        signIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Method when submit button is clicked, it goes to the next activity
                Intent intent = (new Intent(SignUpActivity.this, LoginActivity.class));
                startActivity(intent);
                finish();

            }
        });


        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                registerUser();

            }
        });



    }

    private void registerUser() {

        //Converting the user input into String
        String userFullName = fullName.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPhoneNumber = phoneNumber.getText().toString().trim();
        String firstPassword = password.getText().toString().trim();
        String userPassword = confirmPassword.getText().toString().trim();

        //Validation to ensure the user does not leave any spaces blank
        if (TextUtils.isEmpty(userFullName)
        || TextUtils.isEmpty(userEmail)
        || TextUtils.isEmpty(userPhoneNumber)
        || TextUtils.isEmpty(firstPassword)
        || TextUtils.isEmpty(userPassword))
        {
            //Ask the user to fill in all spaces
            Toast.makeText(this, "Please fill in all spaces", Toast.LENGTH_SHORT).show();

        }
        else
        {
            //Check if passwords match
            if (firstPassword.equals(userPassword))
            {

                //Register user
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful())
                        {
                            //Store the user data in Realtime database
                            UserDetails userDetails = new UserDetails(userFullName, userEmail, userPhoneNumber, userPassword);
                            String userId = task.getResult().getUser().getUid();
                            dataRef.child(userId).setValue(userDetails);

                            //Send user to loginActivity
                            Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            finish();


                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
            else
            {
                Toast.makeText(SignUpActivity.this, "Password don't match", Toast.LENGTH_SHORT).show();
            }

        }
    }






}