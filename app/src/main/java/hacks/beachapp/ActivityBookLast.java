package hacks.beachapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hacks.beachapp.beachapp.R;
import com.squareup.picasso.Picasso;

public class ActivityBookLast extends AppCompatActivity {
String HotelRecordId,HotelRoomRecordId,selectedtimecheckin,selectedtimecheckout,noofadult,noofchild,noofrooms;
    String Name,Address,PhoneNo,EmailAddress,Rate,ProfilePic;
    EditText etName,etEmail,etAddress,etMobileNumber;
    TextView tvName,tvAddress,tvCheckinDate,tvCheckoutDate,tvPerNight;
    TextView title;
    ImageView ivbackdetails,ivBookImg;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_book_last);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=ActivityBookLast.this;
        title=(TextView)toolbar.findViewById(R.id.title);
        title.setText("Booking Details");
        ivbackdetails=(ImageView)toolbar.findViewById(R.id.ivbackdetails);
        ivbackdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        HotelRecordId=getIntent().getStringExtra("HotelRecordId");
        HotelRoomRecordId=getIntent().getStringExtra("HotelRoomRecordId");
        selectedtimecheckin=getIntent().getStringExtra("selectedtimecheckin");
        selectedtimecheckout=getIntent().getStringExtra("selectedtimecheckout");
        noofadult=getIntent().getStringExtra("noofadult");
        noofchild=getIntent().getStringExtra("noofchild");
        noofrooms=getIntent().getStringExtra("noofrooms");
        Name=getIntent().getStringExtra("Name");
        Address=getIntent().getStringExtra("Address");
        PhoneNo=getIntent().getStringExtra("PhoneNo");
        EmailAddress=getIntent().getStringExtra("EmailAddress");
        Rate=getIntent().getStringExtra("Rate");
        ProfilePic=getIntent().getStringExtra("ProfilePic");
        init();
        String img="http://images.hacks.com/"+HotelRecordId+"/PROFILE/"+ProfilePic;
        Log.e("iage",img);
        Picasso.with(context)
                .load(img)
                .error(R.drawable.noimage)
                .placeholder(R.drawable.graylogo)
                .into(ivBookImg);

        etName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etName.setFocusable(true);
                etName.setFocusableInTouchMode(true);
                return false;
            }
        });

        etAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etAddress.setFocusable(true);
                etAddress.setFocusableInTouchMode(true);
                return false;
            }
        });

        etMobileNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etMobileNumber.setFocusable(true);
                etMobileNumber.setFocusableInTouchMode(true);
                return false;
            }
        });

        etEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etEmail.setFocusable(true);
                etEmail.setFocusableInTouchMode(true);
                return false;
            }
        });
    }

    private void init() {
        etName=(EditText)findViewById(R.id.etName);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etAddress=(EditText)findViewById(R.id.etAddress);
        etMobileNumber=(EditText)findViewById(R.id.etMobileNumber);
        tvName=(TextView)findViewById(R.id.tvName);
        tvAddress=(TextView)findViewById(R.id.tvAddress);
        tvCheckinDate=(TextView)findViewById(R.id.tvCheckinDate);
        tvCheckoutDate=(TextView)findViewById(R.id.tvCheckoutDate);
        tvPerNight=(TextView)findViewById(R.id.tvPerNight);
        ivBookImg=(ImageView)findViewById(R.id.ivBookImg);


        tvName.setText(Name);
        tvAddress.setText(Address);
        tvCheckinDate.setText(selectedtimecheckin);
        tvCheckoutDate.setText(selectedtimecheckout);




    }

}
