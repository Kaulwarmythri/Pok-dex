package com.example.pokdex;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokdex.pokemon.pokemon;
import com.example.pokdex.types.types;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterT extends RecyclerView.Adapter<RecyclerViewAdapterT.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdapterT";
    Context mContext;
    private ArrayList<types> mTypeList;
    private ArrayList<types> mTypeListFull;

    public RecyclerViewAdapterT(Context context, ArrayList<types> typeList){
        mContext = context;
        mTypeList = typeList;
        mTypeListFull = new ArrayList<>(mTypeList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext)
               .inflate(R.layout.types_view, parent, false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Calling");
        types currentType = mTypeList.get(position);
        final int lastPosition = -1;
        holder.parentLayout.setAnimation(AnimationUtils.loadAnimation(mContext,
                (position > lastPosition)? R.anim.loading_down_anim : R.anim.loading_up_anim));
        holder.typeName.setText(currentType.getName());
        for (int i=0;i<currentType.getPokemonList().size();i++){
            holder.pokemonListHidden.setText(currentType.getPokemonList().get(i).getPokemon().getName());
        }
        boolean isExpanded = mTypeListFull.get(position).isExpanded();
        holder.expandablelayout.setVisibility((isExpanded)?View.VISIBLE:View.GONE);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                types types = mTypeListFull.get(position);
                types.setExpanded(!types.isExpanded());
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTypeList.size();
    }

    @Override
    public Filter getFilter() {
        return formsFilter;
    }
    private Filter formsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<types> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length()==0){
                filteredList.addAll(mTypeListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (types type : mTypeListFull){
                    if (type.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(type);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mTypeList.clear();
            mTypeListFull.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView typeName;
        CardView parentLayout;
        RelativeLayout expandablelayout;
        TextView pokemonListHidden;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeName = itemView.findViewById(R.id.name_types);
            parentLayout = itemView.findViewById(R.id.parent_layout_2);
            expandablelayout = itemView.findViewById(R.id.expandaqble_layout_2);
            pokemonListHidden = itemView.findViewById(R.id.pokemon_list_text);


        }
    }

}
