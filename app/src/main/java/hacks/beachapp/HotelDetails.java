package hacks.beachapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
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
import adapter.AmenitiesAdapter;
import adapter.DetailsAdapter;

public class HotelDetails extends AppCompatActivity {
    ListView lvDetailsdata,lvAmenities;
    Context context;
    Activity activity;
    LinkedList<HashMap<String, String>> Array_details;
    LinkedList<HashMap<String, String>> Array_amenities;
    TextView tvDetails;
    ImageView ivbackdetails,IvProfilepic;
    String HotelRecordId;
    TextView tvHotelname,tvStar,tvAddress,tvPhone;
    ScrollView svDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = HotelDetails.this;
        activity = HotelDetails.this;
        HotelRecordId=getIntent().getStringExtra("HotelRecordId");
        ivbackdetails = (ImageView) toolbar.findViewById(R.id.ivbackdetails);
        ivbackdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
        getdetailsrooms();
    }
    private void init() {
        tvHotelname=(TextView)findViewById(R.id.tvHotelname);
        tvStar=(TextView)findViewById(R.id.tvStar);
        IvProfilepic=(ImageView)findViewById(R.id.IvProfilepic);
        lvAmenities=(ListView)findViewById(R.id.lvAmenities);
        lvDetailsdata = (ListView) findViewById(R.id.lvDetailsdata);
       tvDetails = (TextView) findViewById(R.id.tvDetails);
        tvAddress=(TextView)findViewById(R.id.tvAddress);
        tvPhone=(TextView)findViewById(R.id.tvPhone);
        svDetails=(ScrollView)findViewById(R.id.svDetails);

        svDetails.setFocusableInTouchMode(true);
        svDetails.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        svDetails.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lvAmenities.getParent().requestDisallowInterceptTouchEvent(false);
                lvDetailsdata.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

    }

    private void getdetailsrooms() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        Array_details = new LinkedList<HashMap<String, String>>();
        Array_amenities = new LinkedList<HashMap<String, String>>();
        String url = Constants.baseurl+"GetBeachDetail/"+HotelRecordId;
        Log.e("hoteldetailsurl",url);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("detailsurl", response + "");
                        try {
                            JSONObject job=new JSONObject(response);
                             HotelRecordId=job.getString("HotelRecordId");
                        String name=job.getString("Name");
                            String CityName=job.getString("CityName");
                            String address=job.getString("Address");
                            String PhoneNo=job.getString("PhoneNo");
                            String ProfilePic=job.getString("ProfilePic");
                            String Star=job.getString("Star");
                            String Descriptions=job.getString("Descriptions");
                            tvAddress.setText(address);
                            tvPhone.setText(PhoneNo);
                            tvHotelname.setText(name);
                            tvStar.setText(Star+" -star");


                            tvDetails.setText(Html.fromHtml(Descriptions).toString().trim());

                            Picasso.with( context )
                                    .load( "http://images.hacks.com//"+HotelRecordId+"/PROFILE/"+ProfilePic )
                                    .error(R.drawable.noimage )
                                    .placeholder(R.drawable.graylogo )
                                    .into(IvProfilepic );

                            JSONArray jarr =job.getJSONArray("RoomList");
                            for (int i = 0; i < jarr.length(); i++) {

                                JSONObject jobj = jarr.getJSONObject(i);
                                if (dialog != null)
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("HotelRoomRecordId", jobj.getString("HotelRoomRecordId"));
                                hm.put("RoomTypeName",jobj.getString("RoomTypeName"));
                                hm.put("RoomName", jobj.getString("RoomName"));
                                hm.put("Description", jobj.getString("Description"));
                                hm.put("Rate", jobj.getString("Rate"));
                                hm.put("Discount", jobj.getString("Discount"));
                                hm.put("MaxGuest", jobj.getString("MaxGuest"));
                                hm.put("AllowSmoking", jobj.getString("AllowSmoking"));
                                hm.put("DefaultImage", jobj.getString("DefaultImage"));
                                hm.put("Child",jobj.getString("Child"));
                                hm.put("HotelRecordId",HotelRecordId);

                                Array_details.add(hm);

                                if (dialog != null) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                                DetailsAdapter adap = new DetailsAdapter(activity, Array_details);
                                Helper.getListViewSize(lvDetailsdata);
                                lvDetailsdata.setAdapter(adap);


                            }
                            JSONArray jrrr =job.getJSONArray("HotelAmenitieList");
                            for (int i = 0; i < jrrr.length(); i++) {

                                JSONObject jobj = jrrr.getJSONObject(i);
                                if (dialog != null)
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("AmmenitiesGroupTitle", jobj.getString("AmmenitiesGroupTitle"));
                                hm.put("AmmenitiesSubGroupTitle",jobj.getString("AmmenitiesSubGroupTitle"));


                                Array_amenities.add(hm);

                                if (dialog != null) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                                AmenitiesAdapter adap = new AmenitiesAdapter(activity, Array_amenities);
                                Helper.getListViewSize(lvAmenities);
                                lvAmenities.setAdapter(adap);


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
