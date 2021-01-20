package com.najmeddine.BestReservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class SignTabFragment extends Fragment {

    CountryCodePicker countryCodePicker;
    TextView phoneNB,email,userName;
    TextView password;
    Button signbtn;
    FirebaseAuth fAuth ;
    float v=0;


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.sign_tab_fragment,container,false);

        countryCodePicker=root.findViewById(R.id.countryCodePicker);
        phoneNB=root.findViewById(R.id.phoneNb);
        password=root.findViewById(R.id.pass);;
        signbtn=root.findViewById(R.id.signUPbtn);
        email=root.findViewById(R.id.email);
        userName=root.findViewById(R.id.userName);
        fAuth= FirebaseAuth.getInstance();

        countryCodePicker.setTranslationX(800);
        phoneNB.setTranslationX(800);
        password.setTranslationX(800);
        signbtn.setTranslationX(800);
        email.setTranslationX(800);
        userName.setTranslationX(800);

        phoneNB.setAlpha(v);
        password.setAlpha(v);
        countryCodePicker.setAlpha(v);
        signbtn.setAlpha(v);
        email.setAlpha(v);
        userName.setAlpha(v);

        userName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        countryCodePicker.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        phoneNB.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();
        signbtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1300).start();

        if (fAuth.getCurrentUser() != null){
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0, 0);
        }

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String m_email =email.getText().toString().trim();
                String m_password = password.getText().toString().trim();
                String m_fullName =userName.getText().toString().trim();
                String m_phone =phoneNB.getText().toString().trim();
                String m_fullPhoneNB ="+"+countryCodePicker.getFullNumber()+m_phone;

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

                if (m_phone.length()!=8){
                    phoneNB.setError("Phone Number must be =8");
                    return;
                }

                FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
                DatabaseReference reference=rootNode.getReference("appUser");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.child(m_fullPhoneNB).exists()){
                            Toast.makeText(getContext(), "Number alredy exist", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            fAuth.createUserWithEmailAndPassword(m_email,m_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        String as="user";
                                        String reservationCount="0";
                                        Toast.makeText(getActivity().getBaseContext(), "User Created.", Toast.LENGTH_SHORT).show();
                                        storeNewUserData(m_fullName,m_email,m_password,m_fullPhoneNB,as);
                                        Intent intent = new Intent(getActivity(),MainActivity.class);
                                        startActivity(intent);
                                        ((Activity) getActivity()).overridePendingTransition(0, 0);
                                        preferences.setDataResnb(getContext(),reservationCount);
                                        preferences.setDataLogin(getContext(),true);
                                        preferences.setDataPhone(getContext(),m_fullPhoneNB);
                                        preferences.setDataName(getContext(),m_fullName);
                                        preferences.setDataEmail(getContext(),m_email);
                                        preferences.setDataPass(getContext(),m_password);
                                        preferences.setDataAs(getContext(),as);

                                    }else {
                                        Toast.makeText(getActivity().getBaseContext(), "ERROR!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        //progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

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


    private void storeNewUserData(String fullName, String email, String password, String phoneNo, String as) {
        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        DatabaseReference reference=rootNode.getReference("appUser");
        UserHelperClass addNewUser= new UserHelperClass(fullName,email,password,phoneNo,as);
        reference.child(phoneNo).setValue(addNewUser);
    }

}
