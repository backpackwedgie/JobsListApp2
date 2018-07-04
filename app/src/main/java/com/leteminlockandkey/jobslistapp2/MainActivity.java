package com.leteminlockandkey.jobslistapp2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    private boolean mTwoPane;
    ListView listView;
    ArrayList<HashMap<String, String>> JobItems = new ArrayList<HashMap<String, String>>();
    ArrayList<JobItemDetails> thisJobDetail = new ArrayList<JobItemDetails>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Snackbar.make(view, "More to do.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        listView = findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new ArrayList<String>()));
        PullXMLData theData = new PullXMLData();
        theData.execute();
        if (findViewById(R.id.job_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id){
                Log.d("READ THIS", String.valueOf(id));
                Log.d("Also this", String.valueOf(mTwoPane));
                String TempListViewClickedValue = thisJobDetail.get((int)id).getName();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(JobDetailFragment.ARG_ITEM_ID, TempListViewClickedValue);
                    JobDetailFragment fragment = new JobDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.job_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(MainActivity.this, JobDetailActivity.class);
                    // Sending value to another activity using intent.
                    intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
                    intent.putExtra("sentJobDetail", thisJobDetail.get((int) id));
                    startActivity(intent);
                }
            }
        });
    }
    class PullXMLData extends AsyncTask<String, Void, String>{
        ArrayAdapter<String> adapter;
        @Override
        protected void onPreExecute(){
            adapter = (ArrayAdapter<String>)listView.getAdapter();
        }
        @Override
        protected String doInBackground(String... params){
            String xml = null;
            try {
                URL url = new URL("http://www.leteminlockandkey.com/AppsFolder/mobilesearch.php");
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                int statusCode = con.getResponseCode();
                if (statusCode ==  200) {
                    InputStream inputStream = new BufferedInputStream(con.getInputStream());
                    xml = readStream(inputStream);
                }
            }catch (Exception e){
                Log.d("Connection Exception","Something happon");
            }
            return xml;
        }
        @Override
        protected void onPostExecute(String results){
            Document doc = getDomElement(results);
            NodeList nl = doc.getElementsByTagName("markers");
            for (int i = 0; i < nl.getLength(); i++) {
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nl.item(i);
                // adding each child node to HashMap key => value
                if (getValue(e, "Status").equals("Work Order")) {
                    map.put("ID", getValue(e, "ID"));
                    map.put("Name", getValue(e, "Name"));
                    JobItemDetails newJob = new JobItemDetails();
                    newJob.setID(getValue(e, "ID"));
                    newJob.setName(getValue(e, "Name"));
                    newJob.setJob(getValue(e, "Job"));
                    newJob.setYear(getValue(e, "Year"));
                    newJob.setMake(getValue(e, "Make"));
                    newJob.setModel(getValue(e, "Model"));
                    newJob.setETAStart(getValue(e, "ETAStart"));
                    newJob.setETAEnd(getValue(e,"ETAEnd"));
                    newJob.setComments(getValue(e, "Comments"));
                    newJob.setPhonenumber(getValue(e, "Phonenumber"));
                    newJob.setAddress(getValue(e, "Address"));
                    newJob.setRequirements(getValue(e, "Requirements"));
                    newJob.setStatus(getValue(e, "Status"));
                    newJob.setQuote(getValue(e, "Quote"));
                    newJob.setReferred(getValue(e, "Referred"));
                    thisJobDetail.add(newJob);
                    // adding HashList to ArrayList
                    JobItems.add(map);
                    adapter.add(map.get("Name"));
                }
            }
        }
    }
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
    public Document getDomElement(String xml){
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }
    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }
    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}