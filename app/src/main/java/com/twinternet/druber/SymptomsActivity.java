package com.twinternet.druber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class EmergencyActivity extends AppCompatActivity
{

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
        setContentView(R.layout.activity_emergency);

        getSupportActionBar().hide();

        //Dialog for showing amount
        amount_popup_dialog = new Dialog(EmergencyActivity.this);
        amount_popup_dialog.setContentView(R.layout.amount_popup_dialog);
        amount_popup_dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        amount_popup_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        amount_popup_dialog.setCancelable(true);
        //Adding animation to dialog
        amount_popup_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button confirm = amount_popup_dialog.findViewById(R.id.btn_confirm_id);
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                success_msg_custom_dialog.show();
                Toast.makeText(EmergencyActivity.this, "Request confirmed", Toast.LENGTH_SHORT).show();

            }
        });





        //Dialog for success message at the end
        success_msg_custom_dialog = new Dialog(EmergencyActivity.this);
        success_msg_custom_dialog.setContentView(R.layout.success_msgcustom_dialog);
        success_msg_custom_dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        success_msg_custom_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        success_msg_custom_dialog.setCancelable(false);

        //Adding animation to our dialog
        success_msg_custom_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button okay = success_msg_custom_dialog.findViewById(R.id.btn_okay);
        Button cancel = success_msg_custom_dialog.findViewById(R.id.btn_cancel);

        okay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(EmergencyActivity.this, "Doctor will arrive in 5 minutes", Toast.LENGTH_SHORT).show();
                success_msg_custom_dialog.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(EmergencyActivity.this, "Doctor will arrive in 5 minutes", Toast.LENGTH_SHORT).show();
                success_msg_custom_dialog.dismiss();
            }
        });

        contactDoctor = findViewById(R.id.Emergency_contactDoctor_id);

        contactDoctor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                // I want to show the amount_popup here
                amount_popup_dialog.show();


            }
        });

        listViewData = findViewById(R.id.listview_data);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice,arraySymptoms);
        listViewData.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
        /*
        return super.onCreateOptionsMenu(menu); */
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.item_done)
        {
            String itemSelected = "Selected items: \n";
            for (int i=0; i<listViewData.getCount();i++)
            {
                if(listViewData.isItemChecked(i))
                {
                    itemSelected += listViewData.getItemAtPosition(i) + "\n";

                }
            }
            Toast.makeText(this, "Items Selected", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }


}