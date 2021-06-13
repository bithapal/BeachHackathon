package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hacks.beachapp.beachapp.R;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import Common.Constants;


public class FragmentSignUp extends Fragment {
    View view;
    EditText etEmail, etpassword, etFirstName, etlastname;
    Context context;
    TextView tvregister;
    private RequestQueue rq;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_signup, container, false);
        context = getContext();
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etpassword = (EditText) view.findViewById(R.id.etpassword);
        tvregister=(TextView)view.findViewById(R.id.tvregister);
        etFirstName = (EditText)view.findViewById(R.id.etFirstName);
        etlastname = (EditText)view.findViewById(R.id.etlastname);
        rq = Volley.newRequestQueue(context);  // rq != null
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getgeneralitems();
            }
        });
        return view;
    }

    private void getgeneralitems() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        String url = Constants.baseurl + "Token/SignUp";
        userlogin ul=new userlogin();

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("FirstName",etFirstName.getText().toString());
        postParam.put("LastName",etFirstName.getText().toString());
        postParam.put("Email", etEmail.getText().toString());
        postParam.put("Password", etpassword.getText().toString());
        JsonObjectRequest jobjectrequest = new JsonObjectRequest(Request.Method.POST, url
                    ,  new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            if (dialog != null)
                                if (dialog.isShowing())
                                    dialog.dismiss();

                            Log.e("response", response.toString());
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


                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }


            };

            jobjectrequest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jobjectrequest);


        rq.add(jobjectrequest);
          // AppController.getInstance().addToRequestQueue(jobjectrequest);

    }

}
 class userlogin
{
    public String username ;
    public String password ;

}
