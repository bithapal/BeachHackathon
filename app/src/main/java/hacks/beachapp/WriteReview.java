package hacks.beachapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hacks.beachapp.beachapp.R;
public class WriteReview extends AppCompatActivity {
    LinearLayout clickreview;
    ImageView ivbackdetails;
    TextView title;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = WriteReview.this;
//        rq = Volley.newRequestQueue(WriteReview.this);  // rq != null
        ivbackdetails = (ImageView) toolbar.findViewById(R.id.ivbackdetails);
        title = (TextView) toolbar.findViewById(R.id.title);
        title.setText("Select a place");
        ivbackdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        clickreview = (LinearLayout) findViewById(R.id.submitreview);
        clickreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

//    private void getgeneralitems() {
//        final ProgressDialog dialog = new ProgressDialog(context);
//        dialog.setMessage("Please wait...");
//        dialog.setCancelable(false);
//        dialog.show();
//
//        String url = "http://192.168.100.159/studentdemo/Login/login";
//
//        Map<String, String> postParam = new HashMap<String, String>();
//        postParam.put("username", "bipana");
//        postParam.put("password", "123456");
////        ll.username="Bipana";
////        ll.password="123455";
//        JsonObjectRequest jobjectrequest = new JsonObjectRequest(Request.Method.POST, url
//                , new JSONObject(postParam),
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        if (dialog != null)
//                            if (dialog.isShowing())
//                                dialog.dismiss();
//
//                        Log.e("response", response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Url", error.toString() + "");
//                        if (dialog != null)
//                            if (dialog.isShowing())
//                                dialog.dismiss();
//
//                    }
//                }) {
//
//
//            /**
//             * Passing some request headers
//             * */
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }
//
//        };
//
//        rq.add(jobjectrequest);
//        //   AppController.getInstance().addToRequestQueue(jobjectrequest);
//
//    }

}
