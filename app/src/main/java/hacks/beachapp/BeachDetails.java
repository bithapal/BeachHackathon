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

public class BeachDetails extends AppCompatActivity {
    Activity activity;
    Context context;
    LinkedList<HashMap<String, String>> Array_Images_Top;
    TextView title;
    ImageView ivbackdetails;
//    ListView lvbeachdetails;
public TextView tvBeachName,tvamenities,tvhotels,etrest;
    LinkedList<HashMap<String, String>> Array_details;
    String beachId;
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
        tvBeachName = (TextView) findViewById(R.id.tvBeachName);
        tvamenities = (TextView) findViewById(R.id.tvamenities);
        tvhotels = (TextView) findViewById(R.id.tvhotels);
        etrest = (TextView) findViewById(R.id.etrest);

        ivbackdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        lvbeachdetails = (ListView) findViewById(R.id.lvbeachdetails);
        getdetailsrooms();
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

                        try {
                            JSONObject jobj=new JSONObject(response);
                            tvBeachName.setText(jobj.getString("beachName"));
                            tvamenities.setText(jobj.getString("amenities"));
                            tvhotels.setText(jobj.getString("nearbyHotels"));
                            etrest.setText(jobj.getString("nearbyRestaurants"));


//                                if (dialog != null)
//                                    if (dialog.isShowing())
//                                        dialog.dismiss();
//                                HashMap<String, String> hm = new HashMap<String, String>();
//                                hm.put("beachId", jobj.getString("beachId"));
//                                hm.put("beachName", jobj.getString("beachName"));
//                                hm.put("amenities", jobj.getString("amenities"));
//                                hm.put("nearbyHotels", jobj.getString("nearbyHotels"));
//                                hm.put("nearbyRestaurants", jobj.getString("nearbyRestaurants"));
//
//
//                                Array_details.add(hm);

//                                if (dialog != null) {
//                                    if (dialog.isShowing())
//                                        dialog.dismiss();
//                                }
//                                CitiesNearByHotelsAdapter adap = new CitiesNearByHotelsAdapter(activity, Array_details);
//                                Helper.getListViewSize(lvbeachdetails);
//                                lvbeachdetails.setAdapter(adap);



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
