package adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hacks.beachapp.beachapp.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.LinkedList;

import hacks.beachapp.BeachDetails;
import hacks.beachapp.MoreAboutLocation;

/**
 * Created by Bipana 06/12/2021.
 */
public class TopCitiesAdapter extends ArrayAdapter<HashMap<String, String>> implements Animation.AnimationListener {

    private final Activity context;
    private final LinkedList<HashMap<String, String>> allDataApparels;
    Animation animationslidedown;

    public TopCitiesAdapter(Activity context, LinkedList<HashMap<String, String>> list) {
        super(context, R.layout.cities_item, list);
        this.context = context;
        this.allDataApparels = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = null;
        if (view == null) {
            LayoutInflater inflator = context.getLayoutInflater();

            view = inflator.inflate(R.layout.cities_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            initAll(view, viewHolder);
            view.setTag(viewHolder);

        }
        ViewHolder holder = (ViewHolder) view.getTag();
        fillAll(holder, position);
        return view;
    }

    public void initAll(View view, ViewHolder viewHolder) {


        viewHolder.ivhotel_home = (ImageView) view.findViewById(R.id.ivhotel_home);
        viewHolder.rlMore = (RelativeLayout) view.findViewById(R.id.rlMore);
        viewHolder.flgoDown = (FrameLayout) view.findViewById(R.id.flgoDown);
        viewHolder.tvNoOfHotels = (TextView) view.findViewById(R.id.tvNoOfHotels);
        viewHolder.llAbout=(LinearLayout) view.findViewById(R.id.llAbout);

        viewHolder.tvtitle_home=(TextView)view.findViewById(R.id.tvtitle_home);
        viewHolder.tvAbt=(TextView)view.findViewById(R.id.tvAbt);
        viewHolder.tvThings=(TextView)view.findViewById(R.id.tvThings);
        viewHolder.tvPopLocation=(TextView)view.findViewById(R.id.tvPopLocation);
        viewHolder.flThingsToDo=(FrameLayout)view.findViewById(R.id.flThingsToDo);
        viewHolder.llthingstodo=(LinearLayout)view.findViewById(R.id.llthingstodo);
        viewHolder.llPopLoc=(LinearLayout)view.findViewById(R.id.llPopLoc);



    }

    public void fillAll(final ViewHolder holder, final int position) {
        try {
            holder.tvtitle_home.setText(allDataApparels.get(position).get("CityName"));
            holder.tvNoOfHotels.setText(allDataApparels.get(position).get("TotalHotel")+ " Hotels");

            Picasso.with( context )
                    .load( allDataApparels.get(position).get("ImagePath") )
                    .error(R.drawable.noimage )
                    .placeholder(R.drawable.graylogo )
                    .into(holder.ivhotel_home );
            animationslidedown = AnimationUtils.loadAnimation(context, R.anim.translate);
            animationslidedown.setAnimationListener(this);
            holder.ivhotel_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.rlMore.clearAnimation();
                    if (holder.rlMore.getVisibility() == View.VISIBLE) {
                        holder.rlMore.setVisibility(View.GONE);
                        holder.flgoDown.setVisibility(View.VISIBLE);

                    } else {

                        holder.rlMore.setVisibility(View.VISIBLE);
                        holder.flgoDown.setVisibility(View.GONE);
                        holder.rlMore.startAnimation(animationslidedown);
                        holder.tvAbt.setText("About "+allDataApparels.get(position).get("CityName"));
                        holder.tvThings.setText("Things to do in "+allDataApparels.get(position).get("CityName"));
                        holder.tvPopLocation.setText("Popular locations in "+allDataApparels.get(position).get("CityName"));

                    }

                }
            });
            holder.llAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, BeachDetails.class);
                    i.putExtra("CityRecordId",allDataApparels.get(position).get("CityRecordId"));
                    context.startActivity(i);
                }
            });

            holder.flThingsToDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(context,MoreAboutLocation.class);
                    i.putExtra("CityRecordId",allDataApparels.get(position).get("CityRecordId"));
                    context.startActivity(i);
                }
            });

            holder.llthingstodo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(context,MoreAboutLocation.class);
                    i.putExtra("CityRecordId",allDataApparels.get(position).get("CityRecordId"));
                    context.startActivity(i);
                }
            });

            holder.llPopLoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(context,MoreAboutLocation.class);
                    i.putExtra("CityRecordId",allDataApparels.get(position).get("CityRecordId"));
                    context.startActivity(i);
                }
            });

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
        ImageView ivhotel_home;
        RelativeLayout rlMore;
        FrameLayout flgoDown,flThingsToDo;
        TextView tvNoOfHotels;
        LinearLayout llAbout,llthingstodo,llPopLoc;

        TextView tvtitle_home,tvAbt,tvThings,tvPopLocation;

    }

}
