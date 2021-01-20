package com.najmeddine.BestReservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class Regester extends AppCompatActivity {

    TextView btntologin ;
    Button btncreateaccount;
    EditText mFullName, mEmail, mPassword,mPhone ;
    CountryCodePicker mcountryCodePicker;
    FirebaseAuth fAuth ;
    ProgressBar progressBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        mFullName= findViewById(R.id.FullText);
        mEmail= findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mPhone= findViewById(R.id.Phone);
        mcountryCodePicker=findViewById(R.id.countryCodePicker);
        fAuth= FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        btncreateaccount=(Button) findViewById(R.id.createAccount);
        btntologin= (TextView) findViewById(R.id.btnToLogin);



        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        btncreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password Required.");
                }

                if (password.length()<6){
                    mPassword.setError("Password must be >=6");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String fullName =mFullName.getText().toString().trim();
                            String phone =mPhone.getText().toString().trim();
                            String phoneNo ="+"+mcountryCodePicker.getFullNumber()+phone;
                            String as="User";
                            Toast.makeText(Regester.this, "User Created.", Toast.LENGTH_SHORT).show();
                            storeNewUserData(fullName,email,password,phoneNo,as);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Regester.this, "ERROR!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        /*btncreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Regester.this,Login.class);
                startActivity(intent);
            }
        });*/



        btntologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Regester.this, Login.class );
                startActivity(intent);
            }
        });

    }

    private void storeNewUserData(String fullName, String email, String password, String phoneNo, String as) {
        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        DatabaseReference reference=rootNode.getReference("user");
        UserHelperClass addNewUser= new UserHelperClass(fullName,email,password,phoneNo,as);
        reference.child(phoneNo).setValue(addNewUser);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        System.exit(0);
                    }
                }).create().show();
    }
}