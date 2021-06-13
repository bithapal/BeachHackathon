package adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hacks.beachapp.beachapp.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.LinkedList;

import hacks.beachapp.HotelDetails;


/**
 * Created by User on 12/20/2016.
 */
public class AllItemsAdapter extends ArrayAdapter<HashMap<String, String>> implements Animation.AnimationListener {

    private final Activity context;
    private final LinkedList<HashMap<String, String>> allDataApparels;
    Animation animationslidedown;
    int check=1;

    public AllItemsAdapter(Activity context, LinkedList<HashMap<String, String>> list) {
        super(context, R.layout.home_list_item, list);
        this.context = context;
        this.allDataApparels = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = null;
        if (view == null) {
            LayoutInflater inflator = context.getLayoutInflater();

            view = inflator.inflate(R.layout.home_list_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            initAll(view, viewHolder);
            view.setTag(viewHolder);

        }
        ViewHolder holder = (ViewHolder) view.getTag();
        fillAll(holder, position);
        return view;
    }

    public void initAll(View view, ViewHolder viewHolder) {


        viewHolder.tag = (TextView) view.findViewById(R.id.tag);
        viewHolder.ivhotel_home = (ImageView) view.findViewById(R.id.ivhotel_home);
        viewHolder.llview=(LinearLayout)view.findViewById(R.id.llview);
        viewHolder.flgoDown=(FrameLayout)view.findViewById(R.id.flgoDown);
        viewHolder.tvtitle_home=(TextView)view.findViewById(R.id.tvtitle_home);
        viewHolder.tvLocation=(TextView)view.findViewById(R.id.tvLocation);

    }

    public void fillAll(final ViewHolder holder, final int position) {
        try {

            String image=allDataApparels.get(position).get("ImageURL");
            Picasso.with( context )
                    .load( image )
                    .error(R.drawable.noimage )
                    .placeholder(R.drawable.graylogo )
                    .into( holder.ivhotel_home );

                holder.tvtitle_home.setText(allDataApparels.get(position).get("Name"));
            holder.tvLocation.setText(allDataApparels.get(position).get("Location"));

            String acctype=allDataApparels.get(position).get("AccommodationType");

            holder.tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Sample", "Ripple completed");
                    Intent i = new Intent(context, HotelDetails.class);
                    i.putExtra("HotelRecordId",allDataApparels.get(position).get("HotelRecordId"));
                    context.startActivity(i);
                }
            });
            animationslidedown = AnimationUtils.loadAnimation(context, R.anim.translate);
            animationslidedown.setAnimationListener(this);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {


    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public static class ViewHolder {
        public TextView tag,tvtitle_home,tvLocation;
        ImageView ivhotel_home,newtag;
        LinearLayout llview;
        FrameLayout flgoDown;
//        TextView tvFlat,tvTypeOfRent,tvAddress,tvSize,tvInfo,tvAmount,tvViewDetails,tvViews,tvApplied;
//        ImageView ivItemImage;
    }

}
