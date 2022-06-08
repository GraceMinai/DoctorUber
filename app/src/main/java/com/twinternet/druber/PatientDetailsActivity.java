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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientDetailsActivity extends AppCompatActivity
{


    public TextView gender;
    public EditText et_fullName, et_placeOfResidence, et_mailAddress, et_age, et_phoneNumber;
    public Button next;
    public RadioGroup radioGroup;
    public RadioButton radioButton;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final String userid = mAuth.getUid();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dataRef = firebaseDatabase.getReference().child("Patient_Records").child(userid);
    String patientGender = "";
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        getSupportActionBar().hide();


        et_fullName = findViewById(R.id.edt_PatientDetails_FullName);
        et_placeOfResidence = findViewById(R.id.edt_PatientDetails_Residence);
        et_mailAddress = findViewById(R.id.edt_PatientDetails_EmailAddress);
        et_age = findViewById(R.id.edt_PatientDetails_Age);
        gender = findViewById(R.id.tv_patientDetails_gender);
        et_phoneNumber = findViewById(R.id.edt_PatientDetails_phoneNumber);
        next = findViewById(R.id.btn_PatientDetails_Next);
        radioGroup = findViewById(R.id.radioGroup);
        progressDialog = new ProgressDialog(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radio_btn_male:
                        patientGender = "Male";
                        break;
                    case R.id.radio_btn_female:
                        patientGender = "Female";
                        break;
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                uploadPatientData();

            }
        });


    }

    public void checkRadioButton(View view)
    {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

    }

    //Uploading the data
    private void uploadPatientData() {



        //Obtaining patient's input
        String fullName = et_fullName.getText().toString().trim();
        String placeOfResidence = et_placeOfResidence.getText().toString().trim();
        String email = et_mailAddress.getText().toString().trim();
        String age = et_age.getText().toString().trim();
        String phoneNumber = et_phoneNumber.getText().toString().trim();
        String gender = patientGender;


        //Checking that the user has filled all spaces
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(placeOfResidence)||
            TextUtils.isEmpty(email) || TextUtils.isEmpty(age) || TextUtils.isEmpty(phoneNumber))
        {
            //Ask user to provide all details
            Toast.makeText(this, "Please provide all details", Toast.LENGTH_SHORT).show();

        }
        else
        {

            progressDialog.setTitle("Uploading");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            //Store information in database
            PatientData patientData = new PatientData(fullName, placeOfResidence, email, age, gender, phoneNumber);
            dataRef.setValue(patientData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    progressDialog.dismiss();

                    et_fullName.setText("");
                    et_placeOfResidence.setText("");
                    et_mailAddress.setText("");
                    et_age.setText("");
                    et_phoneNumber.setText("");


                    startActivity(new Intent(PatientDetailsActivity.this, SymptomsActivity.class));
                    Toast.makeText(PatientDetailsActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    //Print the error message
                    Toast.makeText(PatientDetailsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }




    }





    
}