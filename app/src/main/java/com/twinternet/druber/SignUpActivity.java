package com.twinternet.druber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity

{
    public TextView signIn;
    public EditText fullName, email, phoneNumber, password, confirmPassword;
    public Button signUp;

    public FirebaseAuth mAuth;

    //Creating variables for the Database reference
    DatabaseReference myDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        signIn = findViewById(R.id.tv_signIn);
        fullName = findViewById(R.id.edt_SignUp_FullName);
        email = findViewById(R.id.edt_SignUp_Email);
        phoneNumber = findViewById(R.id.edt_SignUp_phoneNumber);
        password = findViewById(R.id.edt_SignUp_Password);
        confirmPassword = findViewById(R.id.edt_SignUp_ConfirmPassword);
        signUp = findViewById(R.id.btn_signUp);

        //Initialising the database reference
        myDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");



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


        //if above condition is met, run the following methods
        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                uploadInformation();
                signUp();

            }
        });



    }


    private void uploadInformation()
    {
        //Converting the user input into String
        String myFullName = fullName.getText().toString().trim();
        String myEmail = email.getText().toString().trim();
        String myPhoneNumber = phoneNumber.getText().toString().trim();

        //To ensure that user does not leave any blank fields (not empty)
        if(!TextUtils.isEmpty(myFullName)
        && !TextUtils.isEmpty(myEmail)
        && !TextUtils.isEmpty(myPhoneNumber))

        {
            //If user has not left any spaces... Do this...
            //Generating key for each user
            String userKey = myDatabaseReference.push().getKey();


            //Uploading information to the database
            UserDetails userDetails = new UserDetails(myFullName, myEmail, myPhoneNumber);

            //Creating a new child to store details of each specific user
            myDatabaseReference.child(userKey).setValue(userDetails);

            //Clearing the edit texts
            fullName.setText("");
            email.setText("");
            phoneNumber.setText("");

            //For successful upload
            Toast.makeText(this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();




        }

        else
        {
            //if any field is left empty.. display the toast below
            Toast.makeText(this, "Ensure all fields are filled then try again", Toast.LENGTH_SHORT).show();
        }

    }

    private void signUp()
    {
        Intent intent = (new Intent(SignUpActivity.this, LoginActivity.class));
        startActivity(intent);
        finish();
    }



}