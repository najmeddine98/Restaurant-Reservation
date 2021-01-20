package com.najmeddine.BestReservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminProfile extends AppCompatActivity {

    TextInputLayout fullName,email,password,phpneNb;
    TextView fullNameLabel;
    TextView userClass;
    static TextView reservationTitle;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        Context activity;
        activity=this;

        fullName=findViewById(R.id.profileFullName);
        password=findViewById(R.id.profiePass);
        email=findViewById(R.id.profileEmail);
        phpneNb=findViewById(R.id.profilePhone);
        fullNameLabel=findViewById(R.id.full_name);
        userClass=findViewById(R.id.userType_Title);
        reservationTitle=findViewById(R.id.rerservation_Title);

        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("appUser");

        showAllUserData();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.miprofile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.miHome:
                        startActivity(new Intent(getApplicationContext(),AdminMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.miclient:
                        startActivity(new Intent(getApplicationContext(),Client.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mireservation:
                        startActivity(new Intent(getApplicationContext(),user_submission.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.minavigation_logout:

                        ClickLogout(activity);
                        return true;
                }
                return false;

            }
        });
    }

    private void showAllUserData() {

        fullNameLabel.setText(preferences.getDataName(getApplicationContext()));
        userClass.setText(preferences.getDataAs(getApplicationContext()));
        reservationTitle.setText(preferences.getDataResnb(getApplicationContext()));
        fullName.getEditText().setText(preferences.getDataName(getApplicationContext()));
        email.getEditText().setText(preferences.getDataEmail(getApplicationContext()));
        phpneNb.getEditText().setText(preferences.getDataPhone(getApplicationContext()));
        password.getEditText().setText(preferences.getDataPass(getApplicationContext()));


    }

    public void update(View view){
        if(isNamechanged()||isPasschanged()||isEmailchanged()||isPhonechanged()){
            Toast.makeText(this, "Datat has been updated", Toast.LENGTH_SHORT).show();}
        else {
            Toast.makeText(this, "Data is same and can not be updated", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isNamechanged() {
        if(!preferences.getDataName(getApplicationContext()).equals(fullName.getEditText().getText().toString())){
            reference.child(preferences.getDataPhone(getApplicationContext())).child("fullName").setValue(fullName.getEditText().getText().toString());
            preferences.setDataName(getApplicationContext(),fullName.getEditText().getText().toString());
            fullName.getEditText().setText(preferences.getDataName(getApplicationContext()));
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isPasschanged() {
        if(!preferences.getDataPass(getApplicationContext()).equals(password.getEditText().getText().toString())){
            reference.child(preferences.getDataPhone(getApplicationContext())).child("password").setValue(password.getEditText().getText().toString());
            preferences.setDataPass(getApplicationContext(),password.getEditText().getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isEmailchanged() {
        if(!preferences.getDataEmail(getApplicationContext()).equals(email.getEditText().getText().toString())){
            reference.child(preferences.getDataPhone(getApplicationContext())).child("email").setValue(email.getEditText().getText().toString());
            preferences.setDataEmail(getApplicationContext(),email.getEditText().getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isPhonechanged() {
        if(!preferences.getDataPhone(getApplicationContext()).equals(phpneNb.getEditText().getText().toString())){
            reference.child(preferences.getDataPhone(getApplicationContext())).child("phoneNo").setValue(phpneNb.getEditText().getText().toString());
            preferences.setDataPhone(getApplicationContext(),phpneNb.getEditText().getText().toString());
            return true;
        }
        else {
            return false;
        }
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
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();

    }
    public  void ClickLogout(Context view){
        logout(this);
    }

    public  void logout(Activity activity) {
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        builder.setTitle("LogOut");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishActivity(0);
                FirebaseAuth.getInstance().signOut();
                preferences.clearData(getApplicationContext());
                startActivity(new Intent(getApplicationContext(),NewLogin.class));
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}