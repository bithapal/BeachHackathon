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
 * Created by User on 8/21/2017.
 */
public class CitiesNearByHotelsAdapter extends ArrayAdapter<HashMap<String, String>> implements Animation.AnimationListener {

    private final Activity context;
    private final LinkedList<HashMap<String, String>> allDataApparels;
    Animation animationslidedown;
    int check=1;

    public CitiesNearByHotelsAdapter(Activity context, LinkedList<HashMap<String, String>> list) {
        super(context, R.layout.beachdetails, list);
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


        viewHolder.tvBeachName = (TextView) view.findViewById(R.id.tvBeachName);
        viewHolder.tvamenities = (TextView) view.findViewById(R.id.tvamenities);
        viewHolder.tvhotels = (TextView) view.findViewById(R.id.tvhotels);
        viewHolder.etrest = (TextView) view.findViewById(R.id.etrest);


    }

    public void fillAll(final ViewHolder holder, final int position) {
        try {

//            String image=
//            Picasso.with( context )
//                    .load( image )
//                    .error(R.drawable.noimage )
//                    .placeholder(R.drawable.graylogo )
//                    .into( holder.ivhotel_home );

            holder.tvBeachName.setText(allDataApparels.get(position).get("beachName"));
            holder.tvamenities.setText(allDataApparels.get(position).get("amenities"));
            holder.tvhotels.setText(allDataApparels.get(position).get("nearbyHotels"));
            holder.etrest.setText(allDataApparels.get(position).get("nearbyRestaurants"));




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
        public TextView tvBeachName,tvamenities,tvhotels,etrest;

//        TextView tvFlat,tvTypeOfRent,tvAddress,tvSize,tvInfo,tvAmount,tvViewDetails,tvViews,tvApplied;
//        ImageView ivItemImage;
    }

}
