package adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hacks.beachapp.beachapp.R;

import java.util.Collections;
import java.util.List;

import hacks.beachapp.NavDrawerItem;

/**
 * Created by User on 2/16/2017.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    Boolean bool = true;


    private int mSelectedItem = 0;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public int getSelectedItem() {
        return mSelectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.mSelectedItem = selectedItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.tvtitle.setText(current.getTitle());

        switch (position) {
            case 0:

                if (position == mSelectedItem) {
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#00a63f")));
                    holder.ivIcons.setImageResource(R.drawable.homeselect);
                } else {
                    holder.ivIcons.setImageResource(R.drawable.homeunselect);
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
                }

                break;

            case 1:

                if (position == mSelectedItem) {
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#00a63f")));
                  holder.ivIcons.setImageResource(R.drawable.cityselect);
                } else {
                   holder.ivIcons.setImageResource(R.drawable.cityunselect);
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
                }

                break;
            case 2:

                if (position == mSelectedItem) {
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#00a63f")));
                holder.ivIcons.setImageResource(R.drawable.featuredselect);
                } else {
                   holder.ivIcons.setImageResource(R.drawable.featuredunselect);
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
                }
                break;

            case 3:
                if(position==mSelectedItem) {
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#00a63f")));
                      holder.ivIcons.setImageResource(R.drawable.loginselect);
                }
                else {
                    holder.ivIcons.setImageResource(R.drawable.loginunselect);
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
                }
                break;
            case 4:
                if(position==mSelectedItem) {
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#00a63f")));
                     holder.ivIcons.setImageResource(R.drawable.signupselect);
                }
                else {
               holder.ivIcons.setImageResource(R.drawable.signupunselect);
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
                }
                break;
            case 5:
                if(position==mSelectedItem) {
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#00a63f")));
                    holder.ivIcons.setImageResource(R.drawable.logoutselect);
                }
                else {
                    holder.ivIcons.setImageResource(R.drawable.logoutunselect);
                    holder.tvtitle.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
                }
                break;

        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvtitle;
        ImageView ivIcons;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvtitle = (TextView) itemView.findViewById(R.id.title);
            ivIcons = (ImageView) itemView.findViewById(R.id.ivicon);
        }
    }
}
