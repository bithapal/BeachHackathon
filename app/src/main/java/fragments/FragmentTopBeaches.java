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
import android.widget.AdapterView;
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
import Common.Helper;
import adapter.AllItemsAdapter;
import adapter.TopCitiesAdapter;
import hacks.beachapp.HotelDetails;

/**
 * Created by User on 3/2/2017.
 */
public class FragmentTopBeaches extends Fragment {
    View view;
    ListView lvTopBeaches;
    Context context;
    String beachId;
    LinkedList<HashMap<String, String>> Array_items_general;
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
                                Helper.getListViewSize(lvTopBeaches);
                                lvTopBeaches.setAdapter(adap);

                                beachId=jobj.getString("beachId");


                            }

                            lvTopBeaches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent u=new Intent(context,HotelDetails.class);
                                    u.putExtra("beachId",Array_items_general.get(position).get("beachId"));
                                    Log.e("hotelrec",Array_items_general.get(position).get("beachId"));
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
