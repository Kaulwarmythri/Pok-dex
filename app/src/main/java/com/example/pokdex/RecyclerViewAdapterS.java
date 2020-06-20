package com.example.pokdex;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterS extends RecyclerView.Adapter<RecyclerViewAdapterS.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdapterS";
    Context mContext;
    ArrayList<stats> mStatsList;
    ArrayList<stats> mStatsListFull;

    public RecyclerViewAdapterS(Context context, ArrayList<stats> statsList){
        mContext = context;
        mStatsList = statsList;
        mStatsListFull = new ArrayList<>(mStatsList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pokemon_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Calling");
        stats currentStat = mStatsList.get(position);
        holder.statName.setText(currentStat.getStat());

    }

    @Override
    public int getItemCount() {
        return mStatsList.size();
    }

    @Override
    public Filter getFilter() {
        return formsFilter;
    }
    private Filter formsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<stats> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length()==0){
                filteredList.addAll(mStatsListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (stats stat : mStatsListFull){
                    if (stat.getStat().toLowerCase().contains(filterPattern)){
                        filteredList.add(stat);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mStatsList.clear();
            mStatsListFull.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };



    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView statName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statName = itemView.findViewById(R.id.name_types);
        }
    }
}
