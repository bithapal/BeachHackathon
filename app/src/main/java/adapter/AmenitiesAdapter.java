package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hacks.beachapp.beachapp.R;

import java.util.HashMap;
import java.util.LinkedList;

public class AmenitiesAdapter extends ArrayAdapter<HashMap<String, String>> {

private final Activity context;
private final LinkedList<HashMap<String, String>> allDataApparels;


public AmenitiesAdapter(Activity context, LinkedList<HashMap<String, String>> list) {
        super(context, R.layout.amenities_item, list);
        this.context = context;
        this.allDataApparels = list;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = null;
        if (view == null) {
        LayoutInflater inflator = context.getLayoutInflater();

        view = inflator.inflate(R.layout.amenities_item, null);
final ViewHolder viewHolder = new ViewHolder();
        initAll(view, viewHolder);
        view.setTag(viewHolder);

        }
        ViewHolder holder = (ViewHolder) view.getTag();
        fillAll(holder, position);
        return view;
        }

public void initAll(View view, ViewHolder viewHolder) {
    viewHolder.tvAmenity = (TextView) view.findViewById(R.id.tvAmenity);
}

public void fillAll(final ViewHolder holder, final int position) {
        try {
        holder.tvAmenity.setText(allDataApparels.get(position).get("AmmenitiesSubGroupTitle"));

        } catch (Exception e) {
        e.printStackTrace();
        }
        }


public static class ViewHolder {
 TextView tvAmenity;
}

}