package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.appyvet.rangebar.RangeBar;
import com.hacks.beachapp.beachapp.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import Common.Constants;
import Common.Helper;
import adapter.AllItemsAdapter;
import hacks.beachapp.HotelDetails;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by User on 2/16/2017.
 */
public class FragmentHome extends Fragment {
    View view;
    String start,end;
    ListView lvHomedata;
    Context context;
    LinkedList<HashMap<String, String>> Array_items_general;
    TextView tvSearch,tvPrice;
    ScrollView svHome;
    int i=10;
    String beachId;
    EditText spCityArea;
    Spinner spStar;
    String star;
    String selectstar="No Star";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        lvHomedata=(ListView)view.findViewById(R.id.lvHomedata);
        // ripplesearch=(RippleView)view.findViewById(R.id.ripplesearch);
        svHome=(ScrollView)view.findViewById(R.id.svHome);
        spCityArea=(EditText)view.findViewById(R.id.spCityArea);
        spStar=(Spinner)view.findViewById(R.id.spStar);
        context=getContext();
        tvSearch=(TextView)view.findViewById(R.id.tvSearch);
        tvPrice=(TextView)view.findViewById(R.id.tvPrice);

        List<String> list = new ArrayList<String>();
        list.add("No Star");
        list.add("1 Star");
        list.add("2 Star");
        list.add("3 Star");
        list.add("4 Star");
        list.add("5 Star");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStar.setAdapter(dataAdapter);

        spStar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectstar=parent.getItemAtPosition(position).toString();
                Log.e("selectstar",selectstar);
                if(selectstar.equals("No Star"))
                {
                    star="0";
                }
                else if(selectstar.equals("1 Star"))
                {
                    star="1";
                }
                else if(selectstar.equals("2 Star"))
                {
                    star="2";
                }
                else if(selectstar.equals("3 Star"))
                {
                    star="3";
                }
                else if(selectstar.equals("4 Star"))
                {
                    star="4";
                } else if(selectstar.equals("5 Star"))
                {
                    star="5";
                }
                else
                {
                    star="0";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getgeneralitems();

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeybaord(v);
                getgeneralitems();
            }
        });


        return view;
    }
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
    @Override
    public void onResume() {
        tvSearch.setBackgroundColor(context.getResources().getColor(R.color.yellow));
        super.onResume();
    }
    private void getgeneralitems() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        Array_items_general = new LinkedList<HashMap<String, String>>();
        String url = Constants.baseurl+"GetAllBeaches";
        Log.e("homefeaturedurl", url + "");


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("homefeaturedurl", response + "");
                        try {
                            JSONArray jarr = new JSONArray(response);

                            for (int i = 0; i < jarr.length(); i++) {

                                JSONObject jobj = jarr.getJSONObject(i);
                                if (dialog != null)
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                HashMap<String, String> hm = new HashMap<String, String>();
                                //  hm.put("title", jobj.getString("title"));
                                hm.put("beachId", jobj.getString("beachId"));
                                hm.put("beachName", jobj.getString("beachName"));
                                hm.put("beachLocation", jobj.getString("beachLocation"));
                                hm.put("image", jobj.getString("image"));


                                Array_items_general.add(hm);

                                if (dialog != null) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                                AllItemsAdapter adap = new AllItemsAdapter(getActivity(), Array_items_general);
                                Helper.getListViewSize(lvHomedata);
                                lvHomedata.setAdapter(adap);

                                beachId=jobj.getString("beachId");


                            }

                            lvHomedata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent u=new Intent(context,HotelDetails.class);
                                    u.putExtra("beachId",Array_items_general.get(position).get("beachId"));
                                    Log.e("beachId",Array_items_general.get(position).get("beachId"));
                                    startActivity(u);
                                }
                            });
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
