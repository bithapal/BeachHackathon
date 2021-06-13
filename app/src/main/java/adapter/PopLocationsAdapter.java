package adapter;

import android.app.Activity;
import android.text.Html;
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

/**
 * Created by User on 8/22/2017.
 */
public class PopLocationsAdapter extends ArrayAdapter<HashMap<String, String>> {

    private final Activity context;
    private final LinkedList<HashMap<String, String>> allDataApparels;

    public PopLocationsAdapter(Activity context, LinkedList<HashMap<String, String>> list) {
        super(context, R.layout.pop_loc_list_item, list);
        this.context = context;
        this.allDataApparels = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = null;
        if (view == null) {
            LayoutInflater inflator = context.getLayoutInflater();

            view = inflator.inflate(R.layout.pop_loc_list_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            initAll(view, viewHolder);
            view.setTag(viewHolder);

        }
        ViewHolder holder = (ViewHolder) view.getTag();
        fillAll(holder, position);
        return view;
    }

    public void initAll(View view, ViewHolder viewHolder) {
        viewHolder.ivPlaceName = (ImageView) view.findViewById(R.id.ivPlaceName);
        viewHolder.tvNameofPlace = (TextView) view.findViewById(R.id.tvNameofPlace);
       viewHolder.tvDesc=(TextView)view.findViewById(R.id.tvDesc);


    }

    public void fillAll(final ViewHolder holder, final int position) {
        try {
            holder.tvNameofPlace.setText(allDataApparels.get(position).get("Title"));
            holder.tvDesc.setText(Html.fromHtml(allDataApparels.get(position).get("Details")));

            String imageurl = "http://images.hacks.com//CITY/" + allDataApparels.get(position).get("CityRecordId") + "/LOCATION/" + allDataApparels.get(position).get("Photo");
            imageurl = imageurl.replace(" ", "%20");
            Picasso.with(context)
                    .load(imageurl)
                    .error(R.drawable.noimage)
                    .placeholder(R.drawable.graylogo)
                    .into(holder.ivPlaceName);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ViewHolder {
        TextView tvNameofPlace,tvDesc;
        ImageView ivPlaceName;

    }

}
