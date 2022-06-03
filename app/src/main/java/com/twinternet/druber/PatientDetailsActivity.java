package com.twinternet.druber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientDetailsActivity extends AppCompatActivity
{


    public TextView patientDetails;
    public EditText fullName, idNumber, placeOfResidence, emailAddress, age, sex, phoneNumber;
    public Button next;
    public RadioGroup radioGroup;
    public RadioButton radioButton;

    //Creating variables for the Database reference
    DatabaseReference myDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        getSupportActionBar().hide();



        patientDetails = findViewById(R.id.tv_patient_details);
        fullName = findViewById(R.id.edt_PatientDetails_FullName);
        idNumber = findViewById(R.id.edt_PatientDetails_IdNumber);
        placeOfResidence = findViewById(R.id.edt_PatientDetails_Residence);
        emailAddress = findViewById(R.id.edt_PatientDetails_EmailAddress);
        age = findViewById(R.id.edt_PatientDetails_Age);
        sex = findViewById(R.id.edt_PatientDetails_Sex);
        phoneNumber = findViewById(R.id.edt_PatientDetails_phoneNumber);
        next = findViewById(R.id.btn_PatientDetails_Next);
        radioGroup = findViewById(R.id.radioGroup);

        //Initialising the database reference
        myDatabaseReference = FirebaseDatabase.getInstance().getReference().child("patientDetails");


        //if above condition is met, run the following methods
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                uploadingInformation();
                next();

            }
        });


    }





    public void checkRadioButton(View view)
    {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

    }

    private void uploadingInformation()
    {
        //Converting the user input into String
        String myFullName = fullName.getText().toString().trim();
        String myIdNumber = idNumber.getText().toString().trim();
        String myPlaceOfResidence = placeOfResidence.getText().toString().trim();
        String myEmailAddress = emailAddress.getText().toString().trim();
        String myAge = age.getText().toString().trim();
        String mySex = sex.getText().toString().toString();
        String myPhoneNumber = phoneNumber.getText().toString().trim();

        //To ensure that user does not leave any blank fields (not empty)
        if(!TextUtils.isEmpty(myFullName)
        && !TextUtils.isEmpty(myIdNumber)
        && !TextUtils.isEmpty(myPlaceOfResidence)
        && !TextUtils.isEmpty(myEmailAddress)
        && !TextUtils.isEmpty(myAge)
        && !TextUtils.isEmpty(mySex)
        && !TextUtils.isEmpty(myPhoneNumber))
        {

            //If user has not left any spaces... Do this...
            //Generating key for each user
            String userKey = myDatabaseReference.push().getKey();

            //Uploading information to the database
            PatientDetails patientDetails = new PatientDetails(myFullName, myIdNumber, myPlaceOfResidence, myEmailAddress, myAge, mySex, myPhoneNumber);

            //Creating a new child to store details of each specific user
            myDatabaseReference.child(userKey).setValue(patientDetails);

            //Clearing the edit texts
            fullName.setText("");
            idNumber.setText("");
            placeOfResidence.setText("");
            emailAddress.setText("");
            age.setText("");
            sex.setText("");
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

    private void next()
    {
        Intent intent = (new Intent(PatientDetailsActivity.this, EmergencyActivity.class));
        startActivity(intent);
        finish();
    }
}