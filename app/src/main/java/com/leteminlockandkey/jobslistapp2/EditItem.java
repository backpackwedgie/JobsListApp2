package com.leteminlockandkey.jobslistapp2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditItem extends AppCompatActivity {
    EditText name;
    EditText job;
    EditText year;
    EditText make;
    EditText model;
    EditText comments;
    EditText phonenumber;
    EditText address;
    EditText requirements;
    EditText etastart;
    EditText etastarttime;
  //  EditText etaend;
    Spinner status;
    EditText quote;
    EditText referred;
    Button button;
    String TempID;
    String TempName;
    String TempJob;
    String TempYear;
    String TempMake;
    String TempModel;
    String TempComments;
    String TempPhonenumber;
    String TempAddress;
    String TempRequirements;
    String TempETAStart;
//    String TempETAEnd;
    String TempStatus;
    String TempQuote;
    String TempReferred;
    private JobItemDetails detailsOfSelectedJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        detailsOfSelectedJob = (getIntent().getExtras().getParcelable("sentJobDetail"));
        name = (EditText) findViewById(R.id.editTextName);
        name.setText(detailsOfSelectedJob.getName());
        job = (EditText) findViewById(R.id.editTextJob);
        job.setText(detailsOfSelectedJob.getJob());
        year = (EditText) findViewById(R.id.editTextYear);
        year.setText(detailsOfSelectedJob.getYear());
        make = (EditText) findViewById(R.id.editTextMake);
        make.setText(detailsOfSelectedJob.getMake());
        model = (EditText) findViewById(R.id.editTextModel);
        model.setText(detailsOfSelectedJob.getModel());
        comments = (EditText) findViewById(R.id.editTextComments);
        comments.setText(detailsOfSelectedJob.getComments());
        phonenumber = (EditText) findViewById(R.id.editTextPhonenumber);
        phonenumber.setText(detailsOfSelectedJob.getPhonenumber());
        address = (EditText) findViewById(R.id.editTextAddress);
        address.setText(detailsOfSelectedJob.getAddress());
        requirements = (EditText) findViewById(R.id.editTextRequirements);
        requirements.setText(detailsOfSelectedJob.getRequirements());
        etastart = (EditText) findViewById(R.id.editTextETAStart);
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = inputFormat.parse(detailsOfSelectedJob.getETAStart());
            String ETAOutput = dateFormat.format(date);
            etastart.setText (ETAOutput);
        }
        catch (ParseException e) {}
        etastart.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(com.leteminlockandkey.jobslistapp2.EditItem.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        String selectedmonthtext = " ";
                        String selecteddaytext = " ";
                        String timeplaceholder = " ";
                        if (selectedmonth < 10){
                            selectedmonthtext = "0"+Integer.toString(selectedmonth);
                        } else selectedmonthtext = Integer.toString(selectedmonth);
                        if (selectedday < 10){
                            selecteddaytext = "0"+Integer.toString(selectedday);
                        } else selecteddaytext = Integer.toString(selectedday);
                        etastart.setText(selectedyear+"-"+selectedmonthtext+"-"+selecteddaytext+timeplaceholder);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
                }
            });
            etastarttime = (EditText) findViewById(R.id.editTextETAStartTime);
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = inputFormat.parse(detailsOfSelectedJob.getETAStart());
            String ETAOutput = dateFormat.format(date);
            etastarttime.setText (ETAOutput);
        }
        catch (ParseException e) {}
            etastarttime.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mHour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
                int mMinute = mcurrentDate.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(com.leteminlockandkey.jobslistapp2.EditItem.this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timepicker, int selectedhour, int selectedminute) {
                        //boolean is24hour = true;
                        etastarttime.setText(selectedhour + ":" + selectedminute + ":00");
                    }
                }, mHour, mMinute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }});
    /*        etaend = (EditText) findViewById(R.id.editTextETAEnd);
            etaend.setText(detailsOfSelectedJob.getETAEnd());
            etaend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(com.leteminlockandkey.jobslistapp2.EditItem.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                /*      Your code   to get date and time    *//*
                        selectedmonth = selectedmonth + 1;
                        String selectedmonthtext = " ";
                        String selecteddaytext = " ";
                        String timeplaceholder = " 00:00:00";
                        if (selectedmonth < 10){
                            selectedmonthtext = "0"+Integer.toString(selectedmonth);
                        } else selectedmonthtext = Integer.toString(selectedmonth);
                        if (selectedday < 10){
                            selecteddaytext = "0"+Integer.toString(selectedday);
                        } else selecteddaytext = Integer.toString(selectedday);
                        etaend.setText(selectedyear+"-"+selectedmonthtext+"-"+selecteddaytext+timeplaceholder);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });*/
        status = (Spinner) findViewById(R.id.spinnerStatus);
        status.getOnItemSelectedListener();
        List<String> statuses = new ArrayList<String>();
        statuses.add("Choose a Status");
        statuses.add("Work Order");
        statuses.add("Quote");
        statuses.add("Complete");
        statuses.add("Canceled");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statuses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(dataAdapter);
        quote = (EditText) findViewById(R.id.editTextQuote);
        quote.setText(detailsOfSelectedJob.getQuote());
        referred = (EditText) findViewById(R.id.editTextReferred);
        referred.setText(detailsOfSelectedJob.getReferred());
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.getSelectedItem().toString().equals("Choose a Status")){
                    Toast.makeText(getApplicationContext(), "Chose a Status", Toast.LENGTH_LONG).show();
                }else {
                    GetData();
                    InsertData(TempID, TempName, TempJob, TempYear, TempMake, TempModel, TempComments,
                            TempPhonenumber, TempAddress, TempRequirements, TempETAStart,
                            TempStatus, TempQuote, TempReferred);
                }
            }
        });
    }

    public void GetData(){
        TempID = detailsOfSelectedJob.getID();
        TempName = name.getText().toString();
        TempJob = job.getText().toString();
        TempYear = year.getText().toString();
        TempMake = make.getText().toString();
        TempModel = model.getText().toString();
        TempComments = comments.getText().toString();
        TempPhonenumber = phonenumber.getText().toString();
        TempAddress = address.getText().toString();
        TempRequirements = requirements.getText().toString();
        TempETAStart = etastart.getText().toString()+etastarttime.getText().toString();
    //    TempETAEnd = etaend.getText().toString();
        TempStatus = status.getSelectedItem().toString();
        TempQuote = quote.getText().toString();
        TempReferred = referred.getText().toString();
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
         @Override
         public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            // startETAtext = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
            // etastart
        }
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new com.leteminlockandkey.jobslistapp2.EditItem.DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
    }
    public void InsertData(final String id, final String name, final String job, final String year, final String make,
                           final String model, final String comments, final String phonenumber,
                           final String address, final String requirements, final String etastart,
                           final String status, final String quote, final String referred){
        class SendPost extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params){
                String idHolder = id;
                String nameHolder = name;
                String jobHolder = job;
                String yearHolder = year;
                String makeHolder = make;
                String modelHolder = model;
                String commentsHolder = comments;
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH)+1;
                String mMonthtext;
                if (mMonth < 10){
                    mMonthtext = "0"+Integer.toString(mMonth);
                }else mMonthtext = Integer.toString(mMonth);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                String mDaytext;
                if (mDay < 10){
                    mDaytext = "0"+Integer.toString(mDay);
                }else mDaytext = Integer.toString(mDay);
                 int mHour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
                String mHourtext;
                if (mHour < 10){
                    mHourtext = "0"+Integer.toString(mHour);
                }else mHourtext = Integer.toString(mHour);
                int mMinute = mcurrentDate.get(Calendar.MINUTE);
                String mMinutetext;
                if (mMinute < 10){
                     mMinutetext = "0"+Integer.toString(mMinute);
                }else mMinutetext = Integer.toString(mMinute);
                int mSecond = mcurrentDate.get(Calendar.SECOND);
                String mSecondtext;
                if (mSecond < 10){
                    mSecondtext = "0"+Integer.toString(mSecond);
                }else mSecondtext = Integer.toString(mSecond);
                String datetimeHolder = mYear+"-"+mMonthtext+"-"+mDaytext+" "+mHourtext+":"+mMinutetext+":"+mSecondtext;
                String phonenumberHolder = phonenumber;
                String addressHolder = address;
                String requirementsHolder = requirements;
                String etastartHolder = etastart;
                //String etaendHolder = etaend;
                //              String starttimemilHolder = starttimemil;
                //            String endtimemilHolder = endtimemil;
                String statusHolder = status;
                String quoteHolder = quote;
                String referredHolder = referred;
//                String text = "";
                try {
                    Log.d("did we try?", "try");
                    URL url = new URL ("http://www.leteminlockandkey.com/AppsFolder/edit.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStream os = con.getOutputStream();
                    Log.d("we output the stream?","stream");
                    String data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(idHolder, "UTF-8")+"&"+
                            URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(nameHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Job", "UTF-8")+"="+URLEncoder.encode(jobHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Year","UTF-8")+"="+URLEncoder.encode(yearHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Make","UTF-8")+"="+URLEncoder.encode(makeHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Model","UTF-8")+"="+URLEncoder.encode(modelHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Comments","UTF-8")+"="+URLEncoder.encode(commentsHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Datetime","UTF-8")+"="+URLEncoder.encode(datetimeHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Phonenumber","UTF-8")+"="+URLEncoder.encode(phonenumberHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Address","UTF-8")+"="+URLEncoder.encode(addressHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Requirements","UTF-8")+"="+URLEncoder.encode(requirementsHolder,"UTF-8")+"&"+
                            URLEncoder.encode("ETAStart","UTF-8")+"="+URLEncoder.encode(etastartHolder,"UTF-8")+"&"+
                            //URLEncoder.encode("ETAEnd","UTF-8")+"="+URLEncoder.encode(etaendHolder,"UTF-8")+"&"+
                            //            URLEncoder.encode("Starttimemil","UTF-8")+"="+URLEncoder.encode(starttimemilHolder,"UTF-8")+"&"+
                            //          URLEncoder.encode("Endtimemeil","UTF-8")+"="+URLEncoder.encode(endtimemilHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Status","UTF-8")+"="+URLEncoder.encode(statusHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Quote","UTF-8")+"="+URLEncoder.encode(quoteHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Referred","UTF-8")+"="+URLEncoder.encode(referredHolder,"UTF-8");
                      //      URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(idHolder,"UTF-8");
                    Log.d("degub","data");
                    BufferedWriter bW=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                    bW.write(data);
                    bW.flush();
                    int statusCode = con.getResponseCode();
                    if (statusCode == 200) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null)
                            sb.append(line).append("\n");
                        String text = "";
                        text = sb.toString();
                        bW.close();
                    }
                }catch (Exception e){
                    Log.d("ok", "Error: "+e);}
                return "Success";
            }
            protected void onPostExecute(String result){
                super.onPostExecute(result);
                Toast.makeText(com.leteminlockandkey.jobslistapp2.EditItem.this,"Success",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditItem.this, MainActivity.class);
                startActivity(intent);
            }
        }
        SendPost sendPost = new SendPost();
        sendPost.execute(name);
    }
}