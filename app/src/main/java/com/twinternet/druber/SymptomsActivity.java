package com.twinternet.druber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SymptomsActivity extends AppCompatActivity  {

    public Button contactDoctor;
    public Dialog success_msg_custom_dialog, amount_popup_dialog;


    ListView listViewData;
    ArrayAdapter<String> adapter;
    String[] arraySymptoms = {"Diarrhoea", "Severe Headache",
            "Asthmatic Attack", "Epileptic Attack", "Child birth", "Seizure",
            "Severe Fever", "Stomach Ache", "Ulcers", "Abdominal Pain", "Hemoptysis",
            "Dyspnea", "Expectoration", "Cough", "Chest Discomfort",
            "Shortness of Breathe", "Severe toothache", "Headache", "General body weakness", "Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);


        contactDoctor = findViewById(R.id.btn_Symptom_contactDoctor_id);


        //Making an adapter to hold the list content
        listViewData = findViewById(R.id.listview_data);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,arraySymptoms);
        listViewData.setAdapter(adapter);
        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {

                contactDoctor.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v)
                    {
                        //Dialog for showing amount
                        amount_popup_dialog = new Dialog(SymptomsActivity.this);
                        amount_popup_dialog.setContentView(R.layout.amount_popup_dialog);
                        amount_popup_dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
                        amount_popup_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        amount_popup_dialog.setCancelable(true);
                        //Adding animation to dialog
                        //amount_popup_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        amount_popup_dialog.show();

                        Button confirm = (Button) amount_popup_dialog.findViewById(R.id.btn_confirm_id);
                        confirm.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {

                                amount_popup_dialog.dismiss();

                                Toast.makeText(SymptomsActivity.this, "Request confirmed", Toast.LENGTH_SHORT).show();
                                //Dialog for success message at the end
                                success_msg_custom_dialog = new Dialog(SymptomsActivity.this);
                                success_msg_custom_dialog.setContentView(R.layout.success_msgcustom_dialog);
                                success_msg_custom_dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
                                success_msg_custom_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                success_msg_custom_dialog.setCancelable(true);
                                success_msg_custom_dialog.show();

                                //Adding animation to our dialog
                                success_msg_custom_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                                Button okay = success_msg_custom_dialog.findViewById(R.id.btn_okay);
                                Button cancel = success_msg_custom_dialog.findViewById(R.id.btn_cancel);

                                okay.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        startActivity(new Intent(SymptomsActivity.this, GoogleMapActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        Toast.makeText(SymptomsActivity.this, "Doctor will arrive soon", Toast.LENGTH_SHORT).show();
                                        success_msg_custom_dialog.dismiss();

                                    }
                                });

                                cancel.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        success_msg_custom_dialog.dismiss();
                                        amount_popup_dialog.dismiss();
                                        finish();
                                    }
                                });



                            }
                        });




                        //showing details on the popup window
                        TextView  txt_popup_symptoms = (TextView) amount_popup_dialog.findViewById(R.id.popup_user_symptoms);
                        TextView  txt_popup_amount = (TextView) amount_popup_dialog.findViewById(R.id.popup_amount);
                        TextView  txt_popup_estimatedDuration = (TextView) amount_popup_dialog.findViewById(R.id.popup_estimatedDuration);

                       //Showing symptoms
                       txt_popup_symptoms.setText(adapterView.getItemAtPosition(position).toString());

                        if (adapterView.getSelectedItemPosition() == 0)
                        {
                            txt_popup_amount.setText("KSH 1800");
                            txt_popup_estimatedDuration.setText("5 minutes");

                        }
                        else if (adapterView.getSelectedItemPosition() == 1)
                        {
                            txt_popup_amount.setText("KSH 1500");
                            txt_popup_estimatedDuration.setText("9 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 2)
                        {
                            txt_popup_amount.setText("KSH 1200");
                            txt_popup_estimatedDuration.setText("3 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 3)
                        {
                            txt_popup_amount.setText("KSH 2100");
                            txt_popup_estimatedDuration.setText("10 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 4)
                        {
                            txt_popup_amount.setText("KSH 5600");
                            txt_popup_estimatedDuration.setText("8 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 5)
                        {
                            txt_popup_amount.setText("KSH 1450");
                            txt_popup_estimatedDuration.setText("7 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 6)
                        {
                            txt_popup_amount.setText("KSH 800");
                            txt_popup_estimatedDuration.setText("12 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 7)
                        {
                            txt_popup_amount.setText("KSH 800");
                            txt_popup_estimatedDuration.setText("6 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 8)
                        {
                            txt_popup_amount.setText("KSH 2600");
                            txt_popup_estimatedDuration.setText("10 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 9)
                        {
                            txt_popup_amount.setText("KSH 3600");
                            txt_popup_estimatedDuration.setText("11 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 10)
                        {
                            txt_popup_amount.setText("KSH 7500");
                            txt_popup_estimatedDuration.setText("8 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 11)
                        {
                            txt_popup_amount.setText("KSH 6800");
                            txt_popup_estimatedDuration.setText("3 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 12)
                        {
                            txt_popup_amount.setText("KSH 8000");
                            txt_popup_estimatedDuration.setText("8 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 13)
                        {
                            txt_popup_amount.setText("KSH 1000");
                            txt_popup_estimatedDuration.setText("15 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 14)
                        {
                            txt_popup_amount.setText("KSH 2500");
                            txt_popup_estimatedDuration.setText("6 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 15)
                        {
                            txt_popup_amount.setText("KSH 2300");
                            txt_popup_estimatedDuration.setText("6 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 16)
                        {
                            txt_popup_amount.setText("KSH 3500");
                            txt_popup_estimatedDuration.setText("20 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 17)
                        {
                            txt_popup_amount.setText("KSH 1100");
                            txt_popup_estimatedDuration.setText("10 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 18)
                        {
                            txt_popup_amount.setText("KSH 2000");
                            txt_popup_estimatedDuration.setText("7 minutes");
                        }
                        else if (adapterView.getSelectedItemPosition() == 19)
                        {
                            txt_popup_amount.setText("Consultation Fee 500/=");
                            txt_popup_estimatedDuration.setText("10 minutes");
                        }




                    }
                });

            }
        });


    }


}