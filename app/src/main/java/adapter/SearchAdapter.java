package adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hacks.beachapp.beachapp.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.LinkedList;

import hacks.beachapp.HotelDetails;
import hacks.beachapp.MapsActivity;

/**
 * Created by User on 3/7/2017.
 */
public class SearchAdapter extends ArrayAdapter<HashMap<String, String>> implements Animation.AnimationListener {

    private final Activity context;
    private final LinkedList<HashMap<String, String>> allDataApparels;

    public SearchAdapter(Activity context, LinkedList<HashMap<String, String>> list) {
        super(context, R.layout.search_item, list);
        this.context = context;
        this.allDataApparels = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = null;
        if (view == null) {
            LayoutInflater inflator = context.getLayoutInflater();

            view = inflator.inflate(R.layout.search_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            initAll(view, viewHolder);
            view.setTag(viewHolder);

        }
        ViewHolder holder = (ViewHolder) view.getTag();
        fillAll(holder, position);
        return view;
    }

    public void initAll(View view, ViewHolder viewHolder) {
        viewHolder.tvViewOnMap = (TextView) view.findViewById(R.id.tvViewOnMap);
        viewHolder.tvSelectSearch=(TextView)view.findViewById(R.id.tvSelectSearch);
        viewHolder.tvHotel=(TextView)view.findViewById(R.id.tvHotel);
        viewHolder.tvAddress=(TextView)view.findViewById(R.id.tvAddress);
        viewHolder.tvAmount=(TextView)view.findViewById(R.id.tvAmount);
        viewHolder.tvDescription=(TextView)view.findViewById(R.id.tvDescription);
        viewHolder.tvType=(TextView)view.findViewById(R.id.tvType);
viewHolder.ivItemImage=(ImageView)view.findViewById(R.id.ivItemImage);
    }

    public void fillAll(final ViewHolder holder, final int position) {
        try {
holder.tvHotel.setText(allDataApparels.get(position).get("Name"));
          holder.tvAddress.setText(allDataApparels.get(position).get("Location"));
            holder.tvAmount.setText("$ "+allDataApparels.get(position).get("StartingPrice"));
            holder.tvDescription.setText(allDataApparels.get(position).get("Star")+" Stars");
            holder.tvType.setText(allDataApparels.get(position).get("AccommodationType"));
            String image=allDataApparels.get(position).get("ImageURL");
            Picasso.with( context )
                    .load( image )
                    .error(R.drawable.noimage )
                    .placeholder(R.drawable.graylogo )
                    .into( holder.ivItemImage );

            holder.tvViewOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MapsActivity.class);
                    context.startActivity(i);
                }
            });
            holder.tvSelectSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, HotelDetails.class);
                    i.putExtra("HotelRecordId",allDataApparels.get(position).get("HotelRecordId"));
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
        TextView tvViewOnMap,tvSelectSearch;
        TextView tvHotel,tvAddress,tvAmount,tvDescription,tvType;
        ImageView ivItemImage;
    }

}
