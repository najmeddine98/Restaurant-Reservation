package com.najmeddine.BestReservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_submission extends AppCompatActivity {

    ListView listbumisson;
    int img = R.drawable.inverse_logo;
    private ArrayList<Model> rec = new ArrayList<>();
    ImageView imageView;
    Context activity;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private TextView datePickText;
    private TextView timePickText;
    private Button datePickBtn;


    TextInputLayout date1;
    String date,time;
    int t1Hour,t1Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_submission);
        //init();


        datePickBtn=findViewById(R.id.dateButton1);

        datePickText=findViewById(R.id.DateText1);


        datePickText.setEnabled(false);
        date1=findViewById(R.id.Date1);


        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("reservations");

        date1.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickText.setText("");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child: snapshot.getChildren()){
                            String key= child.getKey();

                            Query query = FirebaseDatabase.getInstance().getReference("reservations")
                                    .child(key);
                            getDataUser(query);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        datePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }

        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                datePickText.setText( materialDatePicker.getHeaderText());
                date=materialDatePicker.getHeaderText();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child: snapshot.getChildren()){
                            String key= child.getKey();

                            Query query = FirebaseDatabase.getInstance().getReference("reservations")
                                    .child(key)
                                    .orderByChild("reservationDate")
                                    .equalTo(date);
                            getDataUser(query); }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


        });





        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.mireservation);
        activity=this;


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.miprofile:
                        startActivity(new Intent(getApplicationContext(),AdminProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.miclient:
                        startActivity(new Intent(getApplicationContext(),Client.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.miHome:
                        startActivity(new Intent(getApplicationContext(),AdminMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.minavigation_logout:

                        ClickLogout(activity);
                        return true;
                }
                return false;

            }
        });

        listbumisson = (ListView) findViewById(R.id.listbumisson);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()){
                    String key= child.getKey();

                    Query query = FirebaseDatabase.getInstance().getReference("reservations")
                            .child(key);
                    getDataUser(query);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    /*private void init() {
        listbumisson = (ListView) findViewById(R.id.listbumisson);
        String uid = preferences.getDataPhone(getApplication());

        if(!uid.equals(""))
        {
            getDataUser(uid);
        }

    }*/

    private void getDataUser(Query query) {


        rec.clear();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model=new Model(dataSnapshot.child("key").getValue(String.class),dataSnapshot.child("reservationDate").getValue(String.class),dataSnapshot.child("reservationTime").getValue(String.class),dataSnapshot.child("tableNo").getValue(String.class));
                    rec.add(model);

                }

                    Testadapter myadapter = new Testadapter(getApplicationContext().getApplicationContext(),rec,img);
                    listbumisson.setAdapter(myadapter);
                    listbumisson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
                        builder.setTitle("Delate Submission");
                        builder.setMessage("Are you sure you want to delate ?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //activity.finishActivity(0);
                                FirebaseDatabase rootNode= FirebaseDatabase.getInstance();
                                DatabaseReference reference=rootNode.getReference("reservations").child(preferences.getDataPhone(getApplicationContext())).child(rec.get(position).getKey());
                                reference.removeValue();

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        rec.remove(position);
                                        Testadapter myadapter = new Testadapter(getApplicationContext().getApplicationContext(),rec,img);

                                        listbumisson.setAdapter(myadapter);
                                        Toast.makeText(activity, "Delate done.", Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(activity, "don't", Toast.LENGTH_SHORT).show();
                                    }
                                });
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
                });



                preferences.setDataResnb(getApplicationContext(),String.valueOf(listbumisson.getCount()));
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }



        });




    }

    class Testadapter extends BaseAdapter {

        Context context;
        ArrayList<Model> tests;
        int rimg;

        Testadapter(Context c, ArrayList<Model> tests, int img) {
            this.context = c;
            this.tests = tests;
            this.rimg = img;
        }

        @Override
        public int getCount() {
            return tests.size();
        }

        @Override
        public Model getItem(int i) {
            return tests.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.row, parent, false);
            //ImageView images = row.findViewById(R.id.listimage);
            TextView txt = row.findViewById(R.id.dateContaint);
            TextView txt2 = row.findViewById(R.id.timeContaint);
            TextView txt3 = row.findViewById(R.id.tableNumberContaint);
            imageView=row.findViewById(R.id.delatebtn1);

            //images.setImageResource(rimg);
            txt.setText(tests.get(position).getDate());
            txt2.setText(tests.get(position).getTime());
            txt3.setText(tests.get(position).getTabNb());

            return row;
        }


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