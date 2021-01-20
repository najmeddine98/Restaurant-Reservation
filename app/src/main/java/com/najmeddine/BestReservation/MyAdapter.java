package com.najmeddine.BestReservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;

    public MyAdapter(Context context,ArrayList<Model> mList) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Model model=mList.get(position);
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.tabNb.setText(model.getTabNb());
        //Toast.makeText(context, model.getKey(), Toast.LENGTH_SHORT).show();

        holder.floatingactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase rootNode= FirebaseDatabase.getInstance();
                DatabaseReference reference=rootNode.getReference("reservations").child(preferences.getDataPhone(context)).child(model.getKey());
                reference.removeValue();
                mList.remove(model);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date,time,tabNb;
        FloatingActionButton floatingactionbutton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //floatingactionbutton=itemView.findViewById(R.id.delatebtn);
            date=itemView.findViewById(R.id.dateContaint);
            time=itemView.findViewById(R.id.timeContaint);
            tabNb=itemView.findViewById(R.id.tableNumberContaint);
        }
    }
}
