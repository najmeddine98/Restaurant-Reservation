package com.najmeddine.BestReservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class test extends AppCompatActivity {

    private Button datePickBtn;
    private Button timePickBtn;
    private Button addReservationbtn;
    private Button backHome;
    private Button numTabbtn;
    String date,time;


    FirebaseDatabase rootNode;
    DatabaseReference reference,Numreference;


    private TextInputLayout firstName;
    private TextInputLayout lastName;
    private TextInputLayout phoneNumber;
    private TextInputLayout comment;
    static TextView tableNumber;
    private TextView datePickText;
    private TextView timePickText;



    int t1Hour,t1Minute;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        backHome=findViewById(R.id.backHomebtn);
        datePickText=findViewById(R.id.DateText);
        timePickText=findViewById(R.id.TimeText);
        firstName=findViewById(R.id.name1);
        lastName=findViewById(R.id.Last_Name1);
        phoneNumber=findViewById(R.id.phoneNumber);
        comment=findViewById(R.id.comment1);
        numTabbtn=findViewById(R.id.tabNumbtn);
        tableNumber=findViewById(R.id.tabNumText1);

        timePickBtn=findViewById(R.id.TimeButton);
        datePickBtn=findViewById(R.id.DateButton);
        addReservationbtn=findViewById(R.id.submitReservationBtn);





        //autoCompleteTextView.setAdapter(adapter);
        datePickText.setEnabled(false);
        timePickText.setEnabled(false);
        //autoCompleteTextView.setEnabled(false);


        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        datePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (preferences.getDataAs(getApplicationContext()).equals("user")){
                    Intent intent = new Intent(test.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(test.this, AdminMainActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        });


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                datePickText.setText( materialDatePicker.getHeaderText());
                date=materialDatePicker.getHeaderText();
            }
        });

        timePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(
                        test.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                             t1Hour=hourOfDay;
                             t1Minute=minute;
                                Calendar calendar=Calendar.getInstance();
                                calendar.set(0,0,0,t1Hour,t1Minute);
                                timePickText.setText(DateFormat.format("hh:mm:aa",calendar));
                                time= (String) DateFormat.format("hh:mm:aa",calendar);
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t1Hour,t1Minute);
                timePickerDialog.show();
            }

        });

        numTabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mfirstName= firstName.getEditText().getText().toString();
                String mlastName= lastName.getEditText().getText().toString();
                String mphoneNo= phoneNumber.getEditText().getText().toString();

                if (TextUtils.isEmpty(mfirstName)){
                    firstName.setError("Name is requared");
                }

                else if (TextUtils.isEmpty(mphoneNo)){
                    firstName.setError("Phone Number is requared");
                }

                else if (TextUtils.isEmpty(date)){
                    datePickText.setError("Date is requared");
                }
                else if (TextUtils.isEmpty(time)){
                    datePickText.setError("Time is requared");
                }


                else {
                    Intent intent = new Intent(getApplicationContext(), RestaurantPlanning.class);
                    intent.putExtra("dateKey", date);
                    intent.putExtra("timeKey", time);
                    startActivity(intent);
                }


            }
        });

        addReservationbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("reservations");
                Numreference=reference.child(preferences.getDataPhone(getApplicationContext()));
                //referenceNb=rootNode.getReference("reservationNumber");

                String mfirstName= firstName.getEditText().getText().toString();
                String mlastName= lastName.getEditText().getText().toString();
                String mphoneNo= phoneNumber.getEditText().getText().toString();
                String reservationDate= datePickText.getEditableText().toString();
                String reservationTime= timePickText.getEditableText().toString();
                String mtableNo= tableNumber.getEditableText().toString();
                String mcomment= comment.getEditText().getText().toString();

                if (TextUtils.isEmpty(mfirstName)){
                    firstName.setError("Name is requared");
                }

                else if (TextUtils.isEmpty(mphoneNo)){
                    firstName.setError("Phone Number is requared");
                }

                else if (TextUtils.isEmpty(date)){
                    datePickText.setError("Date is requared");
                }
                else if (TextUtils.isEmpty(time)){
                    datePickText.setError("Time is requared");
                }
                else {
                    String userId = Numreference.push().getKey();
                    ReservationsHelperClass reservationsHelperClass = new ReservationsHelperClass(userId,reservationDate,reservationTime,mtableNo);
                    Numreference.child(userId).setValue(reservationsHelperClass);


                    if (preferences.getDataAs(getApplicationContext()).equals("user")){
                        Toast.makeText(test.this, "Regestration Done Succesfully .", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(test.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(test.this, "Regestration Done Succesfully .", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(test.this, AdminMainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }




            }
        });

    }



    @Override
    public void onBackPressed() {

            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(getApplicationContext(), Exit.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                            finish();
                        }
                    }).create().show();
    }

}