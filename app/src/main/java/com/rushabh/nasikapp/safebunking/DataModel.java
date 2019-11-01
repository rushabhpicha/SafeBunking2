package com.rushabh.nasikapp.safebunking;

public class DataModel {
    public String subject_name;
    public int total_lectures;
    public int bunked_lectures;
    public String notes;


    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getTotal_Lectures() {
        return total_lectures;
    }

    public void setTotal_Lectures(int total_lectures) {
        this.total_lectures = total_lectures;
    }

    public int getBunked_lectures() {
        return bunked_lectures;
    }

    public void setBunked_lectures(int bunked_lectures) {
        this.bunked_lectures = bunked_lectures;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }


    public DataModel(String subject_name,int bunked_lectures,int total_lectures,String notes)
    {
        this.total_lectures=total_lectures;
        this.subject_name=subject_name;
        this.bunked_lectures=bunked_lectures;
        this.notes=notes;
    }
    public DataModel(){

    }
}
