package com.leteminlockandkey.jobslistapp2;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.AdapterView;

/**
 * Created by Jeff on 3/3/2018
 */

public class JobItemDetails implements Parcelable {
    private String ID;
    private String Name;
    private String Job;
    private String Year;
    private String Make;
    private String Model;
    private String Comments;
    private String Phonenumber;
    private String Address;
    private String Requirements;
    private String ETAStart;
//    private String ETAEnd;
    private String Status;
    private String Quote;
    private String Referred;
    public void setID (String ID){
        this.ID = ID;
    }
    public String getID (){
        return ID;
    }
    public void setName (String Name){
        this.Name = Name;
    }
    public String getName (){
        return Name;
    }
    public void setJob (String Job){
        this.Job = Job;
    }
    public String getJob (){
        return Job;
    }
    public void setYear (String Year){
        this.Year = Year;
    }
    public String getYear (){
        return Year;
    }
    public void setMake (String Make){
        this.Make = Make;
    }
    public String getMake (){
        return Make;
    }
    public void setModel (String Model){ this.Model = Model; }
    public String getModel (){
        return Model;
    }
    public void setComments (String Comments){
        this.Comments = Comments;
    }
    public String getComments (){
        return Comments;
    }
    public void setPhonenumber (String Phonenumber){
        this.Phonenumber = Phonenumber;
    }
    public String getPhonenumber (){
        return Phonenumber;
    }
    public void setAddress (String Address){
        this.Address = Address;
    }
    public String getAddress (){
        return Address;
    }
    public void setRequirements (String Requirements){
        this.Requirements = Requirements;
    }
    public String getRequirements (){
        return Requirements;
    }
    public void setETAStart (String ETAStart){this.ETAStart = ETAStart;}
    public String getETAStart() {return ETAStart;}
//    public void setETAEnd (String ETAEnd){this.ETAEnd = ETAEnd;}
//    public String getETAEnd() {return ETAEnd;}
    public void setStatus (String Status){
        this.Status = Status;
    }
    public String getStatus (){
        return Status;
    }
    public void setQuote (String Quote){
        this.Quote = Quote;
    }
    public String getQuote (){return Quote;}
    public void setReferred (String Referred){
        this.Referred = Referred;
    }
    public String getReferred (){
        return Referred;
    }
    public JobItemDetails (){
//        this.ID = ID;
//        this.Name = Name;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(ID);
        out.writeString(Name);
        out.writeString(Job);
        out.writeString(Year);
        out.writeString(Make);
        out.writeString(Model);
        out.writeString(Comments);
        out.writeString(Phonenumber);
        out.writeString(Address);
        out.writeString(Requirements);
        out.writeString(ETAStart);
//        out.writeString(ETAEnd);
        out.writeString(Status);
        out.writeString(Quote);
        out.writeString(Referred);
    }
    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<JobItemDetails> CREATOR = new Parcelable.Creator<JobItemDetails>() {
        public JobItemDetails createFromParcel(Parcel in) {
            return new JobItemDetails(in);
        }
        public JobItemDetails[] newArray(int size) {
            return new JobItemDetails[size];
        }
    };
    // example constructor that takes a Parcel and gives you an object populated with it's values
    private JobItemDetails(Parcel in) {
        ID = in.readString();
        Name = in.readString();
        Job = in.readString();
        Year = in.readString();
        Make = in.readString();
        Model = in.readString();
        Comments = in.readString();
        Phonenumber = in.readString();
        Address = in.readString();
        Requirements = in.readString();
        ETAStart = in.readString();
//        ETAEnd = in.readString();
        Status = in.readString();
        Quote = in.readString();
        Referred = in.readString();
    }
}