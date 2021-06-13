package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import adapter.AllItemsAdapter;
import adapter.EventsAdapter;

/**
 * Created by User on 8/8/2017.
 */
public class FragmentEvents extends Fragment implements AbsListView.OnScrollListener {
    View view;
    Context context;
    ListView lvFeatured;
    LinkedList<HashMap<String, String>> Array_items_general;
    int i=10;
    private int preLast;
    int j=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_featured, container, false);
        context = getContext();
        lvFeatured=(ListView)view.findViewById(R.id.lvFeatured);
        getgeneralitems();
        lvFeatured.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.e("last pos",""+lvFeatured.getLastVisiblePosition());
//                Log.e("int i",""+i);

                if (lvFeatured.getLastVisiblePosition()+1 == i) {
                    i = i + 10;
                    j=j+10;
                    getgeneralitems();


                }
            }
        });

        return view;
    }
    private void getgeneralitems() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        Array_items_general = new LinkedList<HashMap<String, String>>();
        String url = Constants.baseurl+"HomeApi/get?page="+i;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("homefeaturedurl", response + "");
                        try {
                            if (!Array_items_general.equals(null)) {
                                Array_items_general.clear();
                            }
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
                                int currentPosition = lvFeatured.getSelectedItemPosition()-lvFeatured.getFirstVisiblePosition();
                                EventsAdapter adap = new EventsAdapter(getActivity(), Array_items_general);
                                adap.notifyDataSetChanged();
                                lvFeatured.invalidateViews();
                                lvFeatured.setAdapter(adap);
                                Log.e("currentposition",""+currentPosition);

                                lvFeatured.setSelection(currentPosition+j);
//                                if (i == 10) {
//                                    lvFeatured.setSelection(currentPosition);
//                                    Log.e("if condition","whn i is 10");
//
//                                } else {
//                                    lvFeatured.setSelection(currentPosition);
//                                    Log.e("else condition","whn i is more than 10");
//                                }



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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

//                int lastItem = firstVisibleItem + visibleItemCount;
//
//                if(lastItem == totalItemCount)
//                {
//                    if(preLast!=lastItem)
//                    {
//                        //to avoid multiple calls for last item
//                        Log.e("Last", "Last");
//                        preLast = lastItem;
//                        i=i+1;
//                        getgeneralitems();
//                    }
//
//        }

    }
}
