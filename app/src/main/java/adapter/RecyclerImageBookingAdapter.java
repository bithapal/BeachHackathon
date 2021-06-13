package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hacks.beachapp.beachapp.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by User on 8/25/2017.
 */
public class RecyclerImageBookingAdapter extends RecyclerView.Adapter<RecyclerImageBookingAdapter.ViewHolder> {


    Context context;
    private final LinkedList<HashMap<String, String>> allData;
    boolean isImageFitToScreen;

    public RecyclerImageBookingAdapter(Context context, LinkedList<HashMap<String, String>> list) {
        this.context = context;
        this.allData = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_recylceritem, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        try {
            String dataimg=allData.get(position).get("ImageList");
            dataimg=dataimg.replaceAll(" ","%20");
            final String img = "http://images.hacks.com//"+allData.get(position).get("HotelRecordId")+"/ROOM/"+dataimg;
            Log.e("imagerecycle",img);
            Picasso.with(context)
                    .load(img)
                    .error(R.drawable.noimage)
                    .placeholder(R.drawable.graylogo)
                    .into(holder.horizontal_item_view_image);
//            holder.horizontal_item_view_image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(context, ImageGalleryActivity.class);
//
//                    context.startActivity(i);
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return allData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        ImageView horizontal_item_view_image;


        public ViewHolder(View itemView) {
            super(itemView);


            horizontal_item_view_image = (ImageView) itemView.findViewById(R.id.horizontal_item_view_image);
        }
    }


}

