package com.example.pokdex;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterI extends RecyclerView.Adapter<RecyclerViewAdapterI.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdapterI";
    Context mContext;
    private ArrayList<items> mItemList;
    private ArrayList<items> mItemListFull;
    public RecyclerViewAdapterI(Context context, ArrayList<items> itemList){
        mContext = context;
        mItemList = itemList;
        mItemListFull = new ArrayList<>(mItemList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.types_view, parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Calling");
        items  currentItem = mItemList.get(position);
        final int lastPosition = -1;
        holder.itemName.setText(currentItem.getItem());
        holder.parentLayout.setAnimation(AnimationUtils.loadAnimation(mContext,
                (position > lastPosition)? R.anim.loading_down_anim : R.anim.loading_up_anim));


    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public Filter getFilter() {
        return formsFilter;
    }
    private Filter formsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<items> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length()==0){
                filteredList.addAll(mItemListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (items item : mItemListFull){
                    if (item.getItem().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mItemList.clear();
           mItemListFull.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        CircleImageView itemImage;
        CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.name_pokemon);
            itemImage = itemView.findViewById(R.id.image_pokemon);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
