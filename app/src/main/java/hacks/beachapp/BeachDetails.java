package hacks.beachapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hacks.beachapp.beachapp.R;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.LinkedList;
import Common.Constants;
import Common.Helper;
import adapter.CitiesNearByHotelsAdapter;

public class BeachDetails extends AppCompatActivity implements View.OnClickListener {
    Activity activity;
    Context context;
    LinkedList<HashMap<String, String>> Array_Images_Top;
    TextView title, tvOverview,tvLearnMore;
    ImageView ivbackdetails,ivMainCity;
    ListView lvNearHotels;
    LinkedList<HashMap<String, String>> Array_details;
    LinkedList<HashMap<String, String>> Array_city_hotels;
    String beachId;
    ScrollView svcitydetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity = BeachDetails.this;
        context = BeachDetails.this;
        beachId=getIntent().getStringExtra("beachId");
        title = (TextView) toolbar.findViewById(R.id.title);
        ivbackdetails = (ImageView) toolbar.findViewById(R.id.ivbackdetails);
        svcitydetails=(ScrollView)findViewById(R.id.svcitydetails);
        svcitydetails.setFocusableInTouchMode(true);
        svcitydetails.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        ivbackdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvNearHotels = (ListView) findViewById(R.id.lvNearHotels);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvLearnMore=(TextView)findViewById(R.id.tvLearnMore);
        tvLearnMore.setOnClickListener(this);
        ivMainCity=(ImageView)findViewById(R.id.ivMainCity);
        getdetailsrooms();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLearnMore:
                Intent i=new Intent(activity,MoreAboutLocation.class);
                i.putExtra("CityRecordId",beachId);
                startActivity(i);
                break;
        }
    }

    private void getdetailsrooms() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        String url = Constants.baseurl+"GetBeachDetail/beachId"+beachId;
        Log.e("citydetailsurl",url);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("citydetailsurl", response + "");
                        try {
                            JSONObject job=new JSONObject(response);
                            String CityRecordId=job.getString("CityRecordId");
                            String CityName=job.getString("CityName");
                            String Details=job.getString("Details");
                            String Photo=job.getString("Photo");
                            title.setText("Welcome to "+CityName);
                            tvOverview.setText(Html.fromHtml(Details));
                            String img= "http://images.hacks.com//CITY/"+CityRecordId+"/"+Photo;
                            Log.e("image",img);
                            Picasso.with( context )
                                    .load( "http://images.hacks.com//CITY/"+CityRecordId+"/"+Photo)
                                    .error(R.drawable.noimage )
                                    .placeholder(R.drawable.graylogo )
                                    .into(ivMainCity );

                            JSONArray jarr =job.getJSONArray("PopularLocationList");
                            for (int i = 0; i < jarr.length(); i++) {

                                JSONObject jobj = jarr.getJSONObject(i);
                                if (dialog != null)
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("CityRecordId", jobj.getString("CityRecordId"));
                                hm.put("CityName",jobj.getString("CityName"));
                                hm.put("NearByLocationId", jobj.getString("NearByLocationId"));
                                hm.put("Title", jobj.getString("Title"));
                                hm.put("Details", jobj.getString("Details"));
                                hm.put("Photo", jobj.getString("Photo"));
                                hm.put("Latitude", jobj.getString("Latitude"));
                                hm.put("Longitude", jobj.getString("Longitude"));


                                Array_details.add(hm);

                                if (dialog != null) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
//                                DetailsAdapter adap = new DetailsAdapter(activity, Array_details);
//                                Helper.getListViewSize(lvDetailsdata);
//                                lvDetailsdata.setAdapter(adap);
                            }
                            JSONArray jarray =job.getJSONArray("NearByHotelList");
                            for (int i = 0; i < jarray.length(); i++) {

                                JSONObject jobj = jarray.getJSONObject(i);
                                if (dialog != null)
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("HotelRecordId", jobj.getString("HotelRecordId"));
                                hm.put("AccommodationTypeTitle", jobj.getString("AccommodationTypeTitle"));
                                hm.put("Name", jobj.getString("Name"));
                                hm.put("CityName", jobj.getString("CityName"));
                                hm.put("Address", jobj.getString("Address"));
                                hm.put("PhoneNo", jobj.getString("PhoneNo"));
                                hm.put("EmailAddress", jobj.getString("EmailAddress"));
                                hm.put("Logo", jobj.getString("Logo"));
                                hm.put("ProfilePic", jobj.getString("ProfilePic"));
                                hm.put("Star", jobj.getString("Star"));

                                Array_city_hotels.add(hm);

                                if (dialog != null) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                                CitiesNearByHotelsAdapter adap = new CitiesNearByHotelsAdapter(activity, Array_city_hotels);
                                Helper.getListViewSize(lvNearHotels);
                                lvNearHotels.setAdapter(adap);


                            }
                            if (dialog != null) {
                                if (dialog.isShowing())
                                    dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Url", error.toString() + "");
                        if (dialog != null)
                            if (dialog.isShowing())
                                dialog.dismiss();

                    }
                }) {

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
