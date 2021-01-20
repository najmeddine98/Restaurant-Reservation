package com.najmeddine.BestReservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Client extends AppCompatActivity {
    DrawerLayout drawerLayout;

    ListView listbumisson;
    int img = R.drawable.inverse_logo;
    private ArrayList<UserHelperClass> rec = new ArrayList<>();
    ImageView imageView;
    Context activity;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Context activity;
        activity=this;

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.miclient);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.miprofile:
                        startActivity(new Intent(getApplicationContext(),AdminProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.miHome:
                        startActivity(new Intent(getApplicationContext(),AdminMainActivity.class));
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

        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("appUser");
        listbumisson = (ListView) findViewById(R.id.listbumisson);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()){
                    if(child.child("as").getValue(String.class).equals("user")){
                        //Toast.makeText(activity,  child.child("fullName").getValue(String.class), Toast.LENGTH_SHORT).show();
                        UserHelperClass userHelperClass=child.getValue(UserHelperClass.class);
                        //Toast.makeText(activity, userHelperClass.getFullName(), Toast.LENGTH_SHORT).show();
                        //getDataUser(key);
                        //UserHelperClass moddel=new UserHelperClass(userHelperClass.getFullName(),userHelperClass.getEmail(),userHelperClass.getPhoneNo());
                        //Toast.makeText(activity,userHelperClass.getFullName(), Toast.LENGTH_SHORT).show();
                        rec.add(userHelperClass);
                    }
                }
                //Toast.makeText(activity, rec.size()+"", Toast.LENGTH_SHORT).show();
                Client.Testadapter1 myadapter = new Client.Testadapter1(getApplicationContext().getApplicationContext(),rec,img);
                listbumisson.setAdapter(myadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


       //


    }



  /*  private void getDataUser(String uid) {



        DatabaseReference query = FirebaseDatabase.getInstance().getReference("appUser").child(uid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass model=new UserHelperClass(snapshot.child("fullName").getValue(String.class),snapshot.child("email").getValue(String.class),snapshot.child("phoneNo").getValue(String.class));
                Toast.makeText(activity,snapshot.child("fullName").getValue(String.class) , Toast.LENGTH_SHORT).show();
                rec.add(model);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

*/


    class Testadapter1 extends BaseAdapter {

        Context context;
        ArrayList<UserHelperClass> tests;
        int rimg;

        Testadapter1(Context c, ArrayList<UserHelperClass> tests, int img) {
            this.context = c;
            this.tests = tests;
            this.rimg = img;
        }

        @Override
        public int getCount() {
            return tests.size();
        }

        @Override
        public UserHelperClass getItem(int i) {
            return tests.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.client_item, parent, false);
            //ImageView images = row.findViewById(R.id.listimage);
            TextView txt = row.findViewById(R.id.dateContaint);
            TextView txt2 = row.findViewById(R.id.timeContaint);
            TextView txt3 = row.findViewById(R.id.tableNumberContaint);
            imageView=row.findViewById(R.id.delatebtn1);

            //images.setImageResource(rimg);
            txt.setText( tests.get(position).getFullName());
            txt2.setText(tests.get(position).getEmail());
            txt3.setText(tests.get(position).getPhoneNo());





            return row;
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