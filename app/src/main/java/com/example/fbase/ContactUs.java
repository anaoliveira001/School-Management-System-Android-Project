package com.example.fbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    EditText etSource, etDestination;
    Button btTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        etSource = findViewById(R.id.et_source);
        etDestination = findViewById(R.id.et_destination);
        btTrack = findViewById(R.id.bt_track);

        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sSource = etSource.getText().toString().trim();
                String sDestination = etDestination.getText().toString().trim();


                if (sSource.equals("") && sDestination.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter both Location", Toast.LENGTH_SHORT).show();
                    DisplayTrack(sSource, sDestination);

                }

            }
        });
    }

    private void DisplayTrack(String sSource, String sDestination) {
        //is the device does not have a map instelled, then redirect it to playStore
        try {
            //When google maps is installed
            //Initialize uri
            Uri uri = Uri.parse("https://www.google.com.pt/maps/dir" + sSource + "/" + sDestination);
            //Inicialize intent with action view
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //set package
            intent.setPackage("com.google.android.apps.maps");
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Star activity
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            // when google maps is not installed
            //initialize uri
            Uri uri = Uri.parse("https://play.google.com/store/apps/datails?id=com.google.android.apps.maps");
            //Initialize intent with action view
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //star activity
            startActivity(intent);
        }
    }
}