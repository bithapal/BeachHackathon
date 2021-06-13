package hacks.beachapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hacks.beachapp.beachapp.R;

public class TermsAndConditions extends AppCompatActivity {
    TextView tvTermsAndCond, title;
    ImageView ivbackdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = (TextView) toolbar.findViewById(R.id.title);
        title.setText("Terms and Conditions! Add more text here!!");
        ivbackdetails = (ImageView) toolbar.findViewById(R.id.ivbackdetails);

        ivbackdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTermsAndCond = (TextView) findViewById(R.id.tvTermsAndCond);
        tvTermsAndCond.setText("terms and conditions");

    }

}
