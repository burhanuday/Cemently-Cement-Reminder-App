package com.burhanuday.cubetestreminder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.model.Location;
import com.burhanuday.cubetestreminder.util.DateUtils;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by burhanuday on 12-12-2018.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    private static final int TYPE_LOCATION = 0;
    private static final int TYPE_TITLE = 1;
    private static final String TAG = "RecyclerAdapter";
    private List<Location> locationList;
    private ListItemClickListener listItemClickListener;
    private String headerText = "TODAY";
    
    public RecyclerAdapter(List<Location> locationList, ListItemClickListener listItemClickListener){
        this.locationList = locationList;
        this.listItemClickListener = listItemClickListener;
    }
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (i){
            case TYPE_LOCATION:
                view = inflater.inflate(R.layout.row_recyclerview, viewGroup, false);
                return new LocationViewHolder(view);
            case TYPE_TITLE:
                view = inflater.inflate(R.layout.row_title, viewGroup, false);
                return new TitleViewHolder(view);
        }
        
        throw new IllegalArgumentException("Invalid view type: " + i);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof LocationViewHolder){
            LocationViewHolder locationViewHolder = (LocationViewHolder) viewHolder;
            locationViewHolder.bind(locationList.get(i-1));
        }else {
            TitleViewHolder titleViewHolder = (TitleViewHolder) viewHolder;
            titleViewHolder.bind(i);
        }
    }

    @Override
    public int getItemCount() {
        return locationList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_TITLE;
        }else {
            return TYPE_LOCATION;
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder{
        
        @BindView(R.id.tv_title_rec)
        TextView title;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        
        void bind(int position){
            if (position == 0){
                title.setText(headerText);
            }
        }
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.tv_location)
        TextView location;
        
        @BindView(R.id.tv_days_passed)
        TextView daysPassed;
        
        @BindView(R.id.tv_date_created)
        TextView dateCreated;

        @BindView(R.id.iv_delete_location)
        ImageView delete;
        
        private Location loc;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        private void bind(Location location){
            this.location.setText(location.getName());
            dateCreated.setText(DateUtils.convertDateToString(location.getDate()));
            daysPassed.setText(DateUtils.getDaysDifference(DateUtils.convertDateToString(location.getDate())));
            loc = location;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_delete_location){
                listItemClickListener.deleteLocation(loc);
            }else {
                listItemClickListener.onItemClicked(loc);
            }
        }
    }

    public void setHeaderText(String text){
        headerText = text;
    }
    
    public interface ListItemClickListener{
        void onItemClicked(Location location);
        void deleteLocation(Location location);
    }

}
