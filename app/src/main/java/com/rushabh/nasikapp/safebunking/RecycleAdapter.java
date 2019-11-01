package com.rushabh.nasikapp.safebunking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Myholder> {

    public static ArrayList<DataModel> dataModelArrayList;
    DatabaseHelper mydb;
    Context context;
    Animation animBlink;
    float t,b;
    int a;
    float percent;
    int limi,safe_bunk;
    public RecycleAdapter(ArrayList<DataModel> dataModelArrayList, Context context) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
    }

    class Myholder extends RecyclerView.ViewHolder {
        ImageView add_bunk,attend;
        public ArrayList<DataModel> dataModelArrayList;
        Context context;
        CardView card;
        public TextView subject_name, total_lectures, bunked_lectures, percentage_attendance, safe_bunk,notes;

        public Myholder(View itemView, Context context, ArrayList<DataModel> dataModelArrayList) {
            super(itemView);
            this.context = context;
            this.dataModelArrayList = dataModelArrayList;

            subject_name = (TextView) itemView.findViewById(R.id.subject_name);
            total_lectures = (TextView) itemView.findViewById(R.id.total_lectures);
            add_bunk = (ImageView) itemView.findViewById(R.id.bunk);
            card = (CardView) itemView.findViewById(R.id.card_view);
            percentage_attendance = (TextView) itemView.findViewById(R.id.percent_attendance);
            safe_bunk = (TextView) itemView.findViewById(R.id.safe_bunk);
            attend = (ImageView) itemView.findViewById(R.id.attend);
            notes=itemView.findViewById(R.id.notes);
        }
    }
    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_adapter, null,false);
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        view.startAnimation(animation);
        return new Myholder(view, context, dataModelArrayList);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final Myholder holder, int position) {
        final DataModel dataModel = dataModelArrayList.get(position);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        limi=sharedPreferences.getInt("limit",75);

        final float b = dataModel.getBunked_lectures();
        final float t = dataModel.getTotal_Lectures();
        final float a =  (t + b);
        DecimalFormat df = new DecimalFormat("#.##");
        df.format(percent);
        df.setMaximumFractionDigits(2);
        float percent = (float) (Math.ceil((double) (10.00f * (100.00f * (t / a)))) / 10.00d);
        df.format(percent);
        df.setMaximumFractionDigits(2);
        int safe_bunk = 0;

        holder.subject_name.setText(dataModel.getSubject_name());
        holder.total_lectures.setText("Lecture attendance:- "+String.valueOf(dataModel.getTotal_Lectures())+"/"+(int)a);
        if(b==0&&t==0)
        {
            percent=100;
        }
        if (percent < limi) {
            safe_bunk = 0;
            holder.total_lectures.setTextColor(Color.parseColor("#f44336"));
            holder.percentage_attendance.setTextColor(Color.parseColor("#f44336"));
            holder.safe_bunk.setTextColor(Color.parseColor("#f44336"));
//            animBlink = AnimationUtils.loadAnimation(context,R.anim.layout_animation_fall_down);
//            holder.total_lectures.startAnimation(animBlink);
//            holder.safe_bunk.startAnimation(animBlink);
//            holder.percentage_attendance.setAnimation(animBlink);

        } else {
            safe_bunk= Integer.parseInt(safebunks(limi,(int)a, (int) b));
            holder.percentage_attendance.setTextColor(Color.parseColor("#008000"));
            holder.safe_bunk.setTextColor(Color.parseColor("#008000"));
            holder.total_lectures.setTextColor(Color.parseColor("#008000"));
        }

        if (dataModel != null) {
            holder.percentage_attendance.setText("Percentage attendance:- "+String.valueOf(percent));
            holder.safe_bunk.setText("Safe bunks available:- "+String.valueOf(safe_bunk));
        }

        holder.add_bunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.attendance.execSQL("update bunk set bunked_lectures='" + ((dataModel.getBunked_lectures()) + 1) + "' where subject_name='" + dataModel.getSubject_name() + "'");
                MainActivity.updateList();
            }
        });
        holder.attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.attendance.execSQL("update bunk set total_lectures='" + ((dataModel.getTotal_Lectures()) + 1) + "' ,bunked_lectures='" + ((dataModel.getBunked_lectures())) + "' where subject_name='" + dataModel.getSubject_name() + "'");
                MainActivity.updateList();
            }
        });
        Typeface sans=Typeface.createFromAsset(context.getAssets(),"font/OpenSans-Regular.ttf");
        Typeface lato=Typeface.createFromAsset(context.getAssets(),"font/Lato-Bold.ttf");
//        Typeface lato_italic=Typeface.createFromAsset(context.getAssets(),"font/Lato-Italic.ttf");
        holder.total_lectures.setTypeface(sans);
        holder.safe_bunk.setTypeface(sans);
        holder.percentage_attendance.setTypeface(sans);
        holder.subject_name.setTypeface(lato);
//        holder.notes.setTypeface(lato);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(context,subject_details.class);
//                i.putExtra("total_lectures",holder.total_lectures.getText());
//                i.putExtra("lectures_bunked",holder.bunked_lectures.getText());
//                context.startActivity(i);
            }
        });
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage("Do you want to delete this subject?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.attendance.execSQL("delete from bunk where subject_name='"+dataModel.getSubject_name()+"'");
                                MainActivity.updateList();
                                MainActivity.rupani(context);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return false;
            }
        });
        final float finalPercent = percent;
        final int finalSafe_bunk = safe_bunk;
        final int finalt= (int) t;

        DatabaseHelper mydb=new DatabaseHelper(context);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(context,Individual.class);
                i.putExtra("l",limi);
                i.putExtra("to",finalt);
                i.putExtra("a",a);
                i.putExtra("b",b);
                i.putExtra("s", finalSafe_bunk);
                i.putExtra("p", finalPercent);
                i.putExtra("notes",dataModel.getNotes());
                i.putExtra("subject_name",dataModel.getSubject_name());
                context.startActivity(i);
            }
        });
    }

    public String safebunks(int mpercent, int total, int bunked) {
        int a = total;
        int b = bunked;
        int count = 0;
        float percentage = (((float) (a - b)) / ((float) a)) * 100.0f;
        while (percentage >= ((float) mpercent)) {
            a++;
            count++;
            b++;
            percentage = (((float) (a - b)) * 100.0f) / ((float) a);
            if (percentage < ((float) mpercent)) {
                return Integer.toString(count - 1);
            }
        }
        return Integer.toString(a - total);
    }
    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }
}

