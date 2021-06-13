package adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hacks.beachapp.beachapp.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.LinkedList;

import hacks.beachapp.ActivityBooking;


public class DetailsAdapter extends ArrayAdapter<HashMap<String, String>> {

    private final Activity context;
    private final LinkedList<HashMap<String, String>> allDataApparels;

    public DetailsAdapter(Activity context, LinkedList<HashMap<String, String>> list) {
        super(context, R.layout.detail_list_item, list);
        this.context = context;
        this.allDataApparels = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = null;
        if (view == null) {
            LayoutInflater inflator = context.getLayoutInflater();

            view = inflator.inflate(R.layout.detail_list_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            initAll(view, viewHolder);
            view.setTag(viewHolder);

        }
        ViewHolder holder = (ViewHolder) view.getTag();
        fillAll(holder, position);
        return view;
    }

    public void initAll(View view, ViewHolder viewHolder) {
        viewHolder.tvBookNow = (TextView) view.findViewById(R.id.tvBookNow);
        viewHolder.ivhotel_home = (ImageView) view.findViewById(R.id.ivhotel_home);
        viewHolder.tvtitle_home = (TextView) view.findViewById(R.id.tvtitle_home);
        viewHolder.tvRate = (TextView) view.findViewById(R.id.tvRate);
        viewHolder.tvMaxGuest = (TextView) view.findViewById(R.id.tvMaxGuest);
        viewHolder.tvKids = (TextView) view.findViewById(R.id.tvKids);
        viewHolder.tvSmoking = (TextView) view.findViewById(R.id.tvSmoking);


    }

    public void fillAll(final ViewHolder holder, final int position) {
        try {
            holder.tvtitle_home.setText(allDataApparels.get(position).get("RoomTypeName"));
            holder.tvRate.setText("$ " + allDataApparels.get(position).get("Rate") + " Night");
            holder.tvMaxGuest.setText(allDataApparels.get(position).get("MaxGuest"));
            holder.tvSmoking.setText(allDataApparels.get(position).get("AllowSmoking"));
            holder.tvKids.setText(allDataApparels.get(position).get("Child"));

            String imagedata=allDataApparels.get(position).get("DefaultImage");
            imagedata= imagedata.replaceAll(" ","%20");
            String imageurl = "http://images.hacks.com//" + allDataApparels.get(position).get("HotelRecordId") + "/ROOM/" + imagedata;
            Picasso.with(context)
                    .load(imageurl)
                    .error(R.drawable.noimage)
                    .placeholder(R.drawable.graylogo)
                    .into(holder.ivhotel_home);
            holder.tvBookNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ActivityBooking.class);
                    i.putExtra("HotelRecordId",allDataApparels.get(position).get("HotelRecordId"));
                    i.putExtra("HotelRoomRecordId",allDataApparels.get(position).get("HotelRoomRecordId"));
                    context.startActivity(i);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ViewHolder {
        TextView tvBookNow, tvtitle_home, tvRate, tvMaxGuest, tvKids, tvSmoking;
        ImageView ivhotel_home;

    }

}
