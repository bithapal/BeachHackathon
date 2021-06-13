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


}
