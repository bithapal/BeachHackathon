package hacks.beachapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import Common.Constants;
import Common.Helper;
import adapter.AllItemsAdapter;
import fragments.FragmentEvents;
import fragments.FragmentHome;
import fragments.FragmentSignUp;
import fragments.FragmentTopBeaches;
import fragments.FragmentLogin;

public class LandingActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private FragmentDrawer drawerFragment;
    FrameLayout container_body;
    Fragment fragment;
    Context context;
    ListView lvBeaches;
    ArrayList<String> mylistadd;
    ArrayList<String> myidadd;
    LinkedList<HashMap<String, String>> Array_items_general;
    TextView titlee;
    String HotelRecordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        context = LandingActivity.this;
        container_body = (FrameLayout) findViewById(R.id.container_body);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titlee = (TextView) toolbar.findViewById(R.id.title);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);
        lvBeaches = (ListView) findViewById(R.id.lvBeaches);
        // Default Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_body, new FragmentHome())
                .commit();
        //getbeachnames();

    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:

                fragment = new FragmentHome();
                title = getString(R.string.home);
                break;

            case 1:
                fragment = new FragmentTopBeaches();
                title = getString(R.string.topbeaches);
                break;

            case 2:
                fragment = new FragmentEvents();
                title = getString(R.string.featured);
                break;

            case 3:
                fragment = new FragmentLogin();
                title = getString(R.string.login);
                break;
            case 4:
                fragment = new FragmentSignUp();
                title = getString(R.string.signup);
                break;
            case 5:
                new AlertDialog.Builder(context)

                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setTitle("Exit")

                        .setMessage("Are you sure you want to logout?")

                        .setNegativeButton(android.R.string.cancel, null)

                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {


                                    }

                                })

                        .show();
                break;
            default:
                break;
        }

        titlee.setText(title);
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment, "mytag");
            fragmentTransaction.commit();
        }
    }

    public void getbeachnames() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        Array_items_general = new LinkedList<HashMap<String, String>>();
        String url = Constants.baseurl+"HomeApi/get?page=";
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
                                hm.put("HotelRecordId", jobj.getString("HotelRecordId"));
                                hm.put("AccommodationType", jobj.getString("AccommodationType"));
                                hm.put("Name", jobj.getString("Name"));
                                hm.put("ImageURL", jobj.getString("ImageURL"));
                                hm.put("Location", jobj.getString("Location"));
                                hm.put("Star", jobj.getString("Star"));
                                hm.put("StartingPrice", jobj.getString("StartingPrice"));

                                Array_items_general.add(hm);

                                if (dialog != null) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                                AllItemsAdapter adap = new AllItemsAdapter(LandingActivity.this, Array_items_general);
                                Helper.getListViewSize(lvBeaches);
                                lvBeaches.setAdapter(adap);

                                HotelRecordId=jobj.getString("HotelRecordId");


                            }

                            lvBeaches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent u=new Intent(context,BeachDetails.class);
                                    u.putExtra("HotelRecordId",Array_items_general.get(position).get("HotelRecordId"));
                                    Log.e("hotelrec",Array_items_general.get(position).get("HotelRecordId"));
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
