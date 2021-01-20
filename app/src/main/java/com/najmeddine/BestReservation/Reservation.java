package com.najmeddine.BestReservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Reservation extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ListView listbumisson;
    int img = R.drawable.inverse_logo;
    private ArrayList<Model> rec = new ArrayList<>();
    ImageView imageView;
    Context activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        drawerLayout=findViewById(R.id.reservation_layout);
        init();
        activity=this;








    }

    private void init() {
        listbumisson = (ListView) findViewById(R.id.listbumisson);
        String uid = preferences.getDataPhone(getApplication());

        if(!uid.equals(""))
        {
            getDataUser(uid);
        }

    }

    private void getDataUser(String uid) {



        Query query = FirebaseDatabase.getInstance().getReference("reservations")
                .child(uid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model=new Model(dataSnapshot.child("key").getValue(String.class),dataSnapshot.child("reservationDate").getValue(String.class),dataSnapshot.child("reservationTime").getValue(String.class),dataSnapshot.child("tableNo").getValue(String.class));
                    rec.add(model);

                }


                Reservation.Testadapter myadapter = new Reservation.Testadapter(getApplicationContext().getApplicationContext(),rec,img);
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
                                        Reservation.Testadapter myadapter = new Reservation.Testadapter(getApplicationContext().getApplicationContext(),rec,img);
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
            txt.setText("Title: " + tests.get(position).getDate() + "");
            txt2.setText("Time: " + tests.get(position).getTime());
            txt3.setText("Time: " + tests.get(position).getTabNb());





            return row;
        }


    }

    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        MainActivity.redirectActivity(this,MainActivity.class);
    }

    public void ClickProfile(View view){
        MainActivity.redirectActivity(this,Profile.class);
    }

    public void ClickClient(View view){
        MainActivity.redirectActivity(this,Client.class);
    }

    public void ClickStatistique(View view){
        MainActivity.redirectActivity(this,Statistique.class);
    }

    public void ClickReservation(View view){
        recreate();
    }

    public void ClickCalandrier(View view){
        MainActivity.redirectActivity(this,Calandrier.class);
    }

    public void ClickInfo(View view){
        MainActivity.redirectActivity(this,Info.class);
    }

    public  void ClickLogout(View view){
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

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
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
}