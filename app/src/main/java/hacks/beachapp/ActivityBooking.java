package hacks.beachapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import Common.Constants;
import Common.Itemtouch;
import adapter.RecyclerImageBookingAdapter;

public class ActivityBooking extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvImages;
    Context context;
    Activity activity;
    LinkedList<HashMap<String, String>> Array_Images_Top;
    ImageView ivgoLeft, ivgoRight, ivbackdetails;
    TextView title, tvPrice, tvCheckIn, tvCheckout, tvReset;
    Calendar calendarcheckin, calendarcheckout;
    String selectedtimecheckin, selectedtimecheckout;
    Date date1;
    Date date2;
    Spinner spRooms, spAdult, spChild;
    String HotelRoomRecordId,HotelRecordId,noofadult,noofchild,noofrooms,Strcancel,StrCheckin,Strcheckout;
    TextView tvRoomType,tvAddress,tvPhone,tvEmail,tvBookNow,tvCancel,tvCheckinInst,tvCheckoutInst,tvtabsdata;
    String Name,Address,PhoneNo,EmailAddress,Rate,DefaultImage,ProfilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = ActivityBooking.this;
        activity = ActivityBooking.this;
        HotelRoomRecordId=getIntent().getStringExtra("HotelRoomRecordId");
        HotelRecordId=getIntent().getStringExtra("HotelRecordId");
        title = (TextView) toolbar.findViewById(R.id.title);
        ivbackdetails = (ImageView) toolbar.findViewById(R.id.ivbackdetails);
        ivbackdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("Book Your Room");
        init();
        calendarcheckin = Calendar.getInstance();
        calendarcheckout = Calendar.getInstance();
        Get_most_recent();

    }

    private void init() {
        rvImages = (RecyclerView) findViewById(R.id.rvImages);
        ivgoLeft = (ImageView) findViewById(R.id.ivgoLeft);
        ivgoRight = (ImageView) findViewById(R.id.ivgoRight);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvCheckIn = (TextView) findViewById(R.id.tvCheckIn);
        tvCheckout = (TextView) findViewById(R.id.tvCheckout);
        tvRoomType=(TextView)findViewById(R.id.tvRoomType);
        spRooms = (Spinner) findViewById(R.id.spRooms);
        spAdult = (Spinner) findViewById(R.id.spAdult);
        spChild = (Spinner) findViewById(R.id.spChild);
        tvReset = (TextView) findViewById(R.id.tvReset);
        tvAddress=(TextView)findViewById(R.id.tvAddress);
        tvPhone=(TextView)findViewById(R.id.tvPhone);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        tvBookNow=(TextView)findViewById(R.id.tvBookNow);
        tvCancel=(TextView)findViewById(R.id.tvCancel);
        tvCheckinInst=(TextView)findViewById(R.id.tvCheckinInst);
        tvCheckoutInst=(TextView)findViewById(R.id.tvCheckoutInst);
        tvtabsdata=(TextView)findViewById(R.id.tvtabsdata);

        addItemsOnrooms();
        addItemsOnAdult();
        addItemsOnChild();
        tvCheckout.setOnClickListener(this);
        tvCheckIn.setOnClickListener(this);
        tvReset.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvCheckinInst.setOnClickListener(this);
        tvCheckoutInst.setOnClickListener(this);

        ivgoLeft.setOnClickListener(this);
        ivgoRight.setOnClickListener(this);
        tvBookNow.setOnClickListener(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        rvImages.setLayoutManager(layoutManager1);
    }

    private void addItemsOnChild() {
        List<String> list = new ArrayList<String>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spChild.setAdapter(dataAdapter);
        spChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                noofchild = parent.getItemAtPosition(position).toString();
                Log.e("noofchild", noofchild);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addItemsOnAdult() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAdult.setAdapter(dataAdapter);

        spAdult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                noofadult = parent.getItemAtPosition(position).toString();
                Log.e("noofadult", noofadult);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void addItemsOnrooms() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRooms.setAdapter(dataAdapter);
        spRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                noofrooms = parent.getItemAtPosition(position).toString();
                Log.e("noofrooms", noofrooms);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void Get_most_recent() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        //dialog.show();

        Array_Images_Top = new LinkedList<HashMap<String, String>>();
        String url = Constants.baseurl+"HomeApi/Room?id="+HotelRoomRecordId;
        Log.e("roomurl",url);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Url", response + "");
                        try {
                            if (!Array_Images_Top.equals(null)) {
                                Array_Images_Top.clear();
                            }

                            JSONObject jobject = new JSONObject(response);
                            String HotelRecordId=jobject.getString("HotelRecordId");
                            String HotelRoomRecordId=jobject.getString("HotelRoomRecordId");
                            String RoomTypeName=jobject.getString("RoomTypeName");
                            String RoomName=jobject.getString("RoomName");
                            String Description=jobject.getString("Description");
                             Rate=jobject.getString("Rate");
                            String MaxGuest=jobject.getString("MaxGuest");
                             DefaultImage=jobject.getString("DefaultImage");

                            tvRoomType.setText(RoomTypeName);
                            tvPrice.setText("$ "+Rate+" Per Night");
                            JSONArray jrr=jobject.getJSONArray("ImageList");

                            for (int i = 0; i < jrr.length(); i++) {

                                String images = jrr.getString(i);
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("ImageList", jrr.getString(i));
                                hm.put("HotelRecordId",HotelRecordId);
                                Log.e("ImageList",jrr.getString(i));
                               Array_Images_Top.add(hm);



                            }

                            JSONObject job=jobject.getJSONObject("HotelRecord");
                            for(int i=0;i<job.length();i++)
                            {
                                Name=job.getString("Name");
                                 Address=job.getString("Address");
                                tvAddress.setText(Address);
                                 PhoneNo=job.getString("PhoneNo");
                                tvPhone.setText(PhoneNo);
                                 EmailAddress=job.getString("EmailAddress");
                                tvEmail.setText(EmailAddress);
                               Strcancel=job.getString("CancellationPolicy");
                                StrCheckin=job.getString("CheckInInstruction");
                                Strcheckout=job.getString("CheckOutInstruction");
                                ProfilePic=job.getString("ProfilePic");
                                if(Strcancel.equals(null))
                                {
                                    Log.e("hello","data is null");
                                    Strcancel="No data available";
                                }
                                tvtabsdata.setText(Strcancel);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerImageBookingAdapter cad = new RecyclerImageBookingAdapter(activity, Array_Images_Top);
                        cad.notifyDataSetChanged();
                        rvImages.setAdapter(cad);
                        if (dialog != null) {
                            if (dialog.isShowing())
                                dialog.dismiss();
                        }
//                        Itemtouch.addTo(rvImages).setOnItemClickListener(new Itemtouch.OnItemClickListener() {
//                            @Override
//                            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
//                                Intent i = new Intent(activity, ImageGalleryActivity.class);
//                                startActivity(i);
//                            }
//                        });


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
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivgoLeft:
                LinearLayoutManager layoutManager = (LinearLayoutManager) rvImages
                        .getLayoutManager();

                rvImages.getLayoutManager().scrollToPosition(layoutManager.findFirstVisibleItemPosition() - 1);
                break;
            case R.id.ivgoRight:
                LinearLayoutManager layoutManager1 = (LinearLayoutManager) rvImages
                        .getLayoutManager();

                rvImages.getLayoutManager().scrollToPosition(layoutManager1.findLastVisibleItemPosition() + 1);
                break;
            case R.id.tvCheckIn:
                new DatePickerDialog(activity, date, calendarcheckin
                        .get(Calendar.YEAR), calendarcheckin.get(Calendar.MONTH),
                        calendarcheckin.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.tvCheckout:
                new DatePickerDialog(activity, dateout, calendarcheckout
                        .get(Calendar.YEAR), calendarcheckout.get(Calendar.MONTH),
                        calendarcheckout.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.tvReset:
                tvCheckIn.setText(null);
                tvCheckout.setText(null);
                break;
            case R.id.tvBookNow:
                booknow();
                break;
            case R.id.tvCancel:
                if(Strcancel.equals(null))
                {
                    Strcancel="No data available";
                    tvtabsdata.setText(Strcancel);
                }
                else {
                    tvtabsdata.setText(Strcancel);
                }

                break;
            case R.id.tvCheckinInst:
                if(StrCheckin.equals(null)|| StrCheckin.isEmpty())
                {
                    StrCheckin="No data available";
                    tvtabsdata.setText(StrCheckin);
                }
                else {
                    tvtabsdata.setText(StrCheckin);
                }

                break;
            case R.id.tvCheckoutInst:
                if(Strcheckout.equals(null))
                {
                    Strcheckout="No data available";
                    tvtabsdata.setText(Strcheckout);
                }
                else {
                    tvtabsdata.setText(Strcheckout);
                }

                break;
        }
    }

    private void booknow() {
      //  Hotel(Guid hid, Guid rid, string checkin, string checkout, int adult, int child, int room)
       // http://ohohotels.com/Hotel/Book?hid=243649dd-80ee-4cb2-b85c-00494ebb51ff
        // &rid=0aebc6be-a84b-49ec-a9ae-189321419be8&checkin=08%2F10%2F2017
        // &checkout=08%2F25%2F2017&adult=1&child=0&room=1
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
            String url = Constants.baseurl+"HomeApi/Hotel?hid="+HotelRecordId+"&rid="+HotelRoomRecordId+"&checkin="+selectedtimecheckin+"&checkout="+selectedtimecheckout+
                    "&adult="+noofadult+"&child="+noofchild+"&room="+noofrooms;
            Log.e("bookurl",url);


            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("bookurl", response + "");
                            try {
                                JSONObject job=new JSONObject(response);
                             //   String name=job.getString("Name");
                                    if (dialog != null) {
                                        if (dialog.isShowing())
                                            dialog.dismiss();
                                    }

                               Intent i=new Intent(ActivityBooking.this,ActivityBookLast.class);
                                i.putExtra("HotelRecordId",HotelRecordId);
                                i.putExtra("HotelRoomRecordId",HotelRoomRecordId);
                                i.putExtra("selectedtimecheckin",selectedtimecheckin);
                                i.putExtra("selectedtimecheckout",selectedtimecheckout);
                                i.putExtra("noofadult",noofadult);
                                i.putExtra("noofchild",noofchild);
                                i.putExtra("noofrooms",noofrooms);
                                i.putExtra("Name",Name);
                                i.putExtra("Address",Address);
                                i.putExtra("PhoneNo",PhoneNo);
                                i.putExtra("EmailAddress",EmailAddress);
                                i.putExtra("Rate",Rate);
                                i.putExtra("ProfilePic",ProfilePic);


                                startActivity(i);
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

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendarcheckin.set(Calendar.YEAR, year);
            calendarcheckin.set(Calendar.MONTH, monthOfYear);
            calendarcheckin.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

        private void updateLabel() {
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            selectedtimecheckin = sdf.format(calendarcheckin.getTime());
            tvCheckIn.setText(selectedtimecheckin);
        }

    };
    DatePickerDialog.OnDateSetListener dateout = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendarcheckout.set(Calendar.YEAR, year);
            calendarcheckout.set(Calendar.MONTH, monthOfYear);
            calendarcheckout.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

        private void updateLabel() {
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            selectedtimecheckout = sdf.format(calendarcheckout.getTime());
            tvCheckout.setText(selectedtimecheckout);


            //Dates to compare
            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");

            //Setting dates
            try {
                date1 = dates.parse(selectedtimecheckin);
                date2 = dates.parse(selectedtimecheckout);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            //Comparing dates
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            //Convert long to String
            String dayDifference = Long.toString(differenceDates);

            Log.e("HERE", "HERE: " + dayDifference);

        }

    };
}
