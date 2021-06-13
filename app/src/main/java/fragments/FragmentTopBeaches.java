package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import adapter.TopCitiesAdapter;

/**
 * Created by User on 3/2/2017.
 */
public class FragmentTopBeaches extends Fragment {
    View view;
    ListView lvTopBeaches;
    Context context;
    LinkedList<HashMap<String, String>> Array_items_cities;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topbeaches, container, false);
        context=getContext();
        lvTopBeaches=(ListView)view.findViewById(R.id.lvTopBeaches);
        getgeneralitems();
        return view;
    }

    private void getgeneralitems() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        Array_items_cities = new LinkedList<HashMap<String, String>>();
        String url = Constants.baseurl+"HomeApi/CityList";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("lvTopCities", response + "");
                        try {
                            JSONArray jarr = new JSONArray(response);
                            for (int i = 0; i < jarr.length(); i++) {

                                JSONObject jobj = jarr.getJSONObject(i);
                                if (dialog != null)
                                    if (dialog.isShowing())
                                        dialog.dismiss();

                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("ImagePath", jobj.getString("ImagePath"));
                                hm.put("CityName", jobj.getString("CityName"));
                                hm.put("CityRecordId", jobj.getString("CityRecordId"));
                                hm.put("TotalHotel", jobj.getString("TotalHotel"));

                                Array_items_cities.add(hm);

                                if (dialog != null) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                                TopCitiesAdapter adap = new TopCitiesAdapter(getActivity(), Array_items_cities);
                               // Helper.getListViewSize(lvTopCities);
                                lvTopBeaches.setAdapter(adap);


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
