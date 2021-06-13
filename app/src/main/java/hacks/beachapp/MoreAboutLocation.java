package hacks.beachapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.LinkedList;
import Common.Constants;
import Common.Helper;
import adapter.PopLocationsAdapter;
import adapter.ThingsToDoAdapter;

public class MoreAboutLocation extends AppCompatActivity {
    ImageView ivbackdetails;
    ListView lvThingstodo,lvPopLocation;
    String CityRecordId;
    Context context;
    LinkedList<HashMap<String, String>> Array_activities;
    LinkedList<HashMap<String, String>> Array_Poplocations;
    Activity activity;
    TextView tvPopLocText,tvThingstodoText;
    ScrollView svMoreAbtLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_about_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=MoreAboutLocation.this;
        activity=MoreAboutLocation.this;
        svMoreAbtLoc=(ScrollView)findViewById(R.id.svMoreAbtLoc);
        svMoreAbtLoc.setFocusableInTouchMode(true);
        svMoreAbtLoc.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        ivbackdetails=(ImageView)toolbar.findViewById(R.id.ivbackdetails);
        ivbackdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvThingstodo=(ListView)findViewById(R.id.lvThingstodo);
        lvPopLocation=(ListView)findViewById(R.id.lvPopLocation);
        tvPopLocText=(TextView)findViewById(R.id.tvPopLocText);
        tvThingstodoText=(TextView)findViewById(R.id.tvThingstodoText);
        CityRecordId=getIntent().getStringExtra("CityRecordId");
        getdetailsrooms();

    }
    private void getdetailsrooms() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        Array_activities= new LinkedList<HashMap<String, String>>();
        Array_Poplocations= new LinkedList<HashMap<String, String>>();

        String url = Constants.baseurl+"HomeApi/BeachDetails?id="+CityRecordId;
        Log.e("citydetailsurl",url);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("citydetailsurl", response + "");
                        try {
                            JSONObject job=new JSONObject(response);
                            String CityName=job.getString("CityName");
                            tvPopLocText.setText("Popular Locations in "+CityName);
                            tvThingstodoText.setText("Things to do in "+CityName);
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


                                Array_Poplocations.add(hm);

                                if (dialog != null) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                                PopLocationsAdapter adap = new PopLocationsAdapter(activity, Array_Poplocations);
                                Helper.getListViewSize(lvPopLocation);
                                lvPopLocation.setAdapter(adap);
                            }

                            JSONArray jrr =job.getJSONArray("CityActivityList");
                            for (int i = 0; i < jrr.length(); i++) {

                                JSONObject jobj = jrr.getJSONObject(i);
                                if (dialog != null)
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("CityActivityRecordId",jobj.getString("CityActivityRecordId"));
                                hm.put("CityRecordId", jobj.getString("CityRecordId"));
                                hm.put("Title", jobj.getString("Title"));
                                hm.put("Photo", jobj.getString("Photo"));
                                hm.put("Latitude", jobj.getString("Latitude"));
                                hm.put("Longitude", jobj.getString("Longitude"));

                                Array_activities.add(hm);

                                if (dialog != null) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                                ThingsToDoAdapter adap = new ThingsToDoAdapter(activity, Array_Poplocations);
                                Helper.getListViewSize(lvThingstodo);
                                lvThingstodo.setAdapter(adap);
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
