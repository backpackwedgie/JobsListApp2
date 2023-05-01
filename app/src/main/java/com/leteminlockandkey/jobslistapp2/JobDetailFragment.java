package com.leteminlockandkey.jobslistapp2;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;

/**
 * A fragment representing a single Job detail screen.
 * This fragment is either contained in a {@link MainActivity}
 * in two-pane mode (on tablets) or a {@link JobDetailActivity}
 * on handsets.
 */
public class JobDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private JobItemDetails detailsOfSelectedJob;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JobDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("ListViewClickedValue")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            detailsOfSelectedJob = (getArguments().getParcelable("sentJobDetail"));
            String Name = "null";
            if (detailsOfSelectedJob != null) {
                Name = detailsOfSelectedJob.getName();
            }
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(Name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.job_detail, container, false);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById (R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), EditItem.class);
                myIntent.putExtra("sentJobDetail",detailsOfSelectedJob);
                startActivity(myIntent);
            }
        });
        if (detailsOfSelectedJob != null) {
            ((TextView) rootView.findViewById(R.id.job_detail)).setText(detailsOfSelectedJob.getJob());
            ((TextView) rootView.findViewById(R.id.year_detail)).setText(detailsOfSelectedJob.getYear()+" ");
            ((TextView) rootView.findViewById(R.id.make_detail)).setText(detailsOfSelectedJob.getMake()+" ");
            ((TextView) rootView.findViewById(R.id.model_detail)).setText(detailsOfSelectedJob.getModel());
            try {
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d yyyy, hh:mm aaa");
                Date date = inputFormat.parse(detailsOfSelectedJob.getETAStart());
                String ETAOutput = dateFormat.format(date);
                ((TextView) rootView.findViewById(R.id.starteta_detail)).setText(ETAOutput);
            }
            catch (ParseException e) {}
//            ((TextView) rootView.findViewById(R.id.endeta_detail)).setText(detailsOfSelectedJob.getETAEnd());
            ((TextView) rootView.findViewById(R.id.quote_detail)).setText(detailsOfSelectedJob.getQuote());
            ((TextView) rootView.findViewById(R.id.comments_detail)).setText(detailsOfSelectedJob.getComments());
            ((TextView) rootView.findViewById(R.id.phonenumber_detail)).setText(detailsOfSelectedJob.getPhonenumber());
            rootView.findViewById(R.id.phonenumber_detail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri urip = Uri.parse("tel:"+detailsOfSelectedJob.getPhonenumber());
                    Intent intentp = new Intent(Intent.ACTION_DIAL);
                    intentp.setData(urip);
                    startActivity(intentp);
                }
            });
            ((TextView) rootView.findViewById(R.id.address_detail)).setText(detailsOfSelectedJob.getAddress());
            rootView.findViewById(R.id.address_detail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("google.navigation:q="+detailsOfSelectedJob.getAddress());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            ((TextView) rootView.findViewById(R.id.requirements_detail)).setText(detailsOfSelectedJob.getRequirements());
            ((TextView) rootView.findViewById(R.id.referred_detail)).setText(detailsOfSelectedJob.getReferred());
            ((TextView) rootView.findViewById(R.id.status_detail)).setText(detailsOfSelectedJob.getStatus());
        }
        return rootView;
    }
}
