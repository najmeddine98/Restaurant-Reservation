package com.najmeddine.BestReservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class LoginTabFragment extends Fragment {

    CountryCodePicker countryCodePicker;
    TextView phoneNB;
    TextView email,password;
    CheckBox checkBox;
    Button loginbtn;
    private FirebaseDatabase rootNode;
    FirebaseAuth fAuth ;
    DatabaseReference reference;
    float v=0;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);

        countryCodePicker=root.findViewById(R.id.countryCodePicker);
        phoneNB=root.findViewById(R.id.phoneNb);
        password=root.findViewById(R.id.pass);
        email=root.findViewById(R.id.email);
        checkBox=root.findViewById(R.id.checkBox);
        loginbtn=root.findViewById(R.id.loginbtn);
        fAuth= FirebaseAuth.getInstance();

        countryCodePicker.setTranslationX(800);
        phoneNB.setTranslationX(800);
        email.setTranslationX(800);
        password.setTranslationX(800);
        checkBox.setTranslationX(800);
        loginbtn.setTranslationX(800);

        countryCodePicker.setAlpha(v);
        phoneNB.setAlpha(v);
        email.setAlpha(v);
        password.setAlpha(v);
        checkBox.setAlpha(v);
        loginbtn.setAlpha(v);

        countryCodePicker.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        phoneNB.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        checkBox.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();
        loginbtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1300).start();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_email =email.getText().toString().trim();
                String m_password = password.getText().toString().trim();
                String m_phone =phoneNB.getText().toString().trim();
                String m_fullPhoneNB ="+216"+m_phone;

                if (m_phone.length()!=8){
                    phoneNB.setError("Phone Number must be =8");
                    return;
                }

                if (TextUtils.isEmpty(m_email)){
                    email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(m_password)){
                    password.setError("Password Required.");
                }

                if (m_password.length()<6){
                    password.setError("Password must be >=6");
                    return;
                }

                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("appUser");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(m_fullPhoneNB).exists()){
                            if (checkBox.isChecked()){

                                if (snapshot.child(m_fullPhoneNB).child("as").getValue(String.class).equals("admin")){
                                    fAuth.signInWithEmailAndPassword(m_email,m_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                preferences.setDataLogin(getActivity().getBaseContext(),true);
                                                preferences.setDataName(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("fullName").getValue(String.class));
                                                preferences.setDataEmail(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("email").getValue(String.class));
                                                preferences.setDataPhone(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("phoneNo").getValue(String.class));
                                                preferences.setDataPass(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("password").getValue(String.class));
                                                preferences.setDataAs(getActivity().getBaseContext(),"admin");
                                               // preferences.setDataResnb(getActivity().getBaseContext(),String.valueOf(Reservation.x));
                                                Toast.makeText(getActivity().getBaseContext(), "Login is done Successfully.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity(),AdminMainActivity.class);
                                                startActivity(intent);
                                                ((Activity) getActivity()).overridePendingTransition(0, 0);
                                            }
                                            else {
                                                Toast.makeText(getActivity().getBaseContext(), "Verify Your Adress and Your Password! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                //progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                                else {
                                    fAuth.signInWithEmailAndPassword(m_email,m_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                preferences.setDataLogin(getActivity().getBaseContext(),true);
                                                preferences.setDataName(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("fullName").getValue(String.class));
                                                preferences.setDataEmail(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("email").getValue(String.class));
                                                preferences.setDataPhone(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("phoneNo").getValue(String.class));
                                                preferences.setDataPass(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("password").getValue(String.class));
                                                preferences.setDataAs(getActivity().getBaseContext(),"user");
                                                //preferences.setDataResnb(getActivity().getBaseContext(),String.valueOf(Reservation.x));
                                                Toast.makeText(getActivity().getBaseContext(), "Login is done Successfully.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity(),MainActivity.class);
                                                startActivity(intent);
                                                ((Activity) getActivity()).overridePendingTransition(0, 0);
                                            }
                                            else {
                                                Toast.makeText(getActivity().getBaseContext(), "Verify Your Adress and Your Password! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                //progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                            }
                            else {

                                if (snapshot.child(m_fullPhoneNB).child("as").getValue(String.class).equals("admin")){
                                    fAuth.signInWithEmailAndPassword(m_email,m_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                preferences.setDataLogin(getActivity().getBaseContext(),false);
                                                preferences.setDataName(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("fullName").getValue(String.class));
                                                preferences.setDataEmail(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("email").getValue(String.class));
                                                preferences.setDataPhone(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("phoneNo").getValue(String.class));
                                                preferences.setDataPass(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("password").getValue(String.class));
                                                preferences.setDataAs(getActivity().getBaseContext(),"admin");
                                                //preferences.setDataResnb(getActivity().getBaseContext(),String.valueOf(Reservation.x));
                                                Toast.makeText(getActivity().getBaseContext(), "Login is done Successfully.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity(),AdminMainActivity.class);
                                                startActivity(intent);
                                                ((Activity) getActivity()).overridePendingTransition(0, 0);
                                            }
                                            else {
                                                Toast.makeText(getActivity().getBaseContext(), "Verify Your Adress and Your Password! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                //progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                                else {
                                    fAuth.signInWithEmailAndPassword(m_email,m_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                preferences.setDataLogin(getActivity().getBaseContext(),false);
                                                preferences.setDataName(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("fullName").getValue(String.class));
                                                preferences.setDataEmail(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("email").getValue(String.class));
                                                preferences.setDataPhone(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("phoneNo").getValue(String.class));
                                                preferences.setDataPass(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("password").getValue(String.class));
                                                //preferences.setDataResnb(getActivity().getBaseContext(),snapshot.child(m_fullPhoneNB).child("password").getValue(String.class));
                                                preferences.setDataAs(getActivity().getBaseContext(),"user");
                                                //preferences.setDataResnb(getActivity().getBaseContext(),String.valueOf(Reservation.x));
                                                Toast.makeText(getActivity().getBaseContext(), "Login is done Successfully.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity(),MainActivity.class);
                                                startActivity(intent);
                                                ((Activity) getActivity()).overridePendingTransition(0, 0);
                                            }
                                            else {
                                                Toast.makeText(getActivity().getBaseContext(), "Verify Your Adress and Your Password! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                //progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                            }
                        }else {
                            Toast.makeText(getActivity().getBaseContext(), "Verify your Number.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });


        return root;





    }
}
