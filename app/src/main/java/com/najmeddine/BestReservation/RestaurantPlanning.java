package com.najmeddine.BestReservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import at.lukle.clickableareasimage.ClickableArea;
import at.lukle.clickableareasimage.ClickableAreasImage;
import at.lukle.clickableareasimage.OnClickableAreaClickedListener;
import uk.co.senab.photoview.PhotoViewAttacher;

public class RestaurantPlanning extends AppCompatActivity implements OnClickableAreaClickedListener {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ListView listbumisson;
    private ArrayList<Model> rec = new ArrayList<>();
    ImageView imageView;
    String date;
    Context activity;
    String time;
    ArrayList<String> tabelTab;
    boolean test;
    Context activity1;
    int img = R.drawable.inverse_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_planning);
        ImageView image = (ImageView) findViewById(R.id.plann);
        image.setImageResource(R.drawable.plan1);

        Intent intent = getIntent();
        date = intent.getStringExtra("dateKey");
        time = intent.getStringExtra("timeKey");
        activity=this;


        ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(image), this);
        List<ClickableArea> clickableAreas = new ArrayList<>();

        activity=this;

        clickableAreas.add(new ClickableArea(25,328,100,42, new tableNumber("Table numero: 1")));
        clickableAreas.add(new ClickableArea(25,267,100,60, new tableNumber("Table numero: 2")));
        clickableAreas.add(new ClickableArea(25,214,100,42, new tableNumber("Table numero: 3")));
        clickableAreas.add(new ClickableArea(25,153,100,60, new tableNumber("Table numero: 4")));
        clickableAreas.add(new ClickableArea(25,110,100,42, new tableNumber("Table numero: 5")));
        clickableAreas.add(new ClickableArea(126,110,92,92, new tableNumber("Table numero: 6")));
        clickableAreas.add(new ClickableArea(219,110,92,92, new tableNumber("Table numero: 7")));
        clickableAreas.add(new ClickableArea(312,110,92,92, new tableNumber("Table numero: 8")));
        clickableAreas.add(new ClickableArea(126,203,109,70, new tableNumber("Table numero: 9")));
        clickableAreas.add(new ClickableArea(236,203,107,52, new tableNumber("Table numero: 10")));
        clickableAreas.add(new ClickableArea(236,257,107,58, new tableNumber("Table numero: 11")));
        clickableAreas.add(new ClickableArea(345,203,55,109, new tableNumber("Table numero: 12")));
        clickableAreas.add(new ClickableArea(29,0,107,109, new tableNumber("Table numero: 13")));
        clickableAreas.add(new ClickableArea(138,0,94,109, new tableNumber("Table numero: 14")));
        clickableAreas.add(new ClickableArea(232,0,104,99, new tableNumber("Table numero: 15")));
        clickableAreas.add(new ClickableArea(480,0,124,99, new tableNumber("Table numero: 16")));


        // Set your clickable areas to the image
        clickableAreasImage.setClickableAreas(clickableAreas);

        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("reservations");
        listbumisson = (ListView) findViewById(R.id.listbumisson);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()){
                    String key= child.getKey();
                    getDataUser(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Toast.makeText(activity, "we alredy have "+String.valueOf(tabelTab.size())+"reservation on"+date+"at"+time, Toast.LENGTH_SHORT).show();


    }
    @Override
    public void onClickableAreaTouched(Object item) {
        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("reservations");


        if (item instanceof tableNumber ) {

            String text1 = ((tableNumber) item).getTabNum() ;

            if (verifyTab(text1)){
                AlertDialog.Builder builder= new AlertDialog.Builder(activity);
                builder.setTitle("LogOut");
                builder.setMessage("Are you sure you want "+text1+" ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //activity.finishActivity(0);
                        com.najmeddine.BestReservation.test.tableNumber.setText(text1);
                        onBackPressed();
                        Toast.makeText(activity, "Table selected!", Toast.LENGTH_SHORT).show();
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
            else {
                Toast.makeText(activity, "Sorry alredy reserved", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean verifyTab( String text) {
        int nb =0;
        test=true;
        while (nb!=tabelTab.size() && test ){
            if (tabelTab.get(nb).equals(text)){
                test=false;
            }
            nb++;
        }
        return test;
    }


    private void getDataUser(String uid) {







        tabelTab = new ArrayList<String>();
        Query query = FirebaseDatabase.getInstance().getReference("reservations")
                .child(uid)
                .orderByChild("reservationDate")
                .equalTo(date)
                ;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.child("reservationTime").getValue(String.class).equals(time)){
                        Model model=new Model(dataSnapshot.child(uid).getValue(String.class),dataSnapshot.child("reservationDate").getValue(String.class),dataSnapshot.child("reservationTime").getValue(String.class),dataSnapshot.child("tableNo").getValue(String.class));
                        rec.add(model);
                        tabelTab.add(dataSnapshot.child("tableNo").getValue(String.class));
                    }
                }

                RestaurantPlanning.Testadapter myadapter = new RestaurantPlanning.Testadapter(getApplicationContext().getApplicationContext(),rec,img);
                listbumisson.setAdapter(myadapter);

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
            txt3.setText( tests.get(position).getTabNb());





            return row;
        }


    }



}
