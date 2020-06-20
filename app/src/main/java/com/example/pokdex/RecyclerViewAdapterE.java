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

import com.example.pokdex.evolutionChain.Chain.Chain;
import com.example.pokdex.evolutionChain.evolutionChain;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterE extends RecyclerView.Adapter<RecyclerViewAdapterE.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdapterE";
    Context mContext;
    ArrayList<evolutionChain> mChainList;
    ArrayList<evolutionChain> mChainListFull;


    public RecyclerViewAdapterE(Context context, ArrayList<evolutionChain> chainList){
        mContext = context;
        mChainList = chainList;
        mChainListFull = new ArrayList<>(mChainList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.evolution_chain_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Calling");
        int lastPosition = -1;
        String evolutionChain;
        String temp = "";
        evolutionChain currentChainSeq = mChainList.get(position);
        temp += currentChainSeq.getChain().getSpecies() + "\n";
        for (int i=0;i<mChainList.size();i++){
        temp += currentChainSeq.getChain().getEvolves_to().get(i).getSpecies() + "\n";
        }
        for (int i=0;i<mChainList.size();i++){
            for (int j=0;j<mChainList.size();j++){
                temp += currentChainSeq.getChain().getEvolves_to().get(i)
                        .getEvolvesTo().get(j).getSpecies().getName() + "\n";
            }
        }
        holder.pokemonForm1.setText(temp);
        holder.parentLayout.setAnimation(AnimationUtils.loadAnimation(mContext,
                (position > lastPosition)? R.anim.loading_down_anim : R.anim.loading_up_anim));
    }

    @Override
    public int getItemCount() {
        return mChainList.size();
    }
        @Override
        public Filter getFilter() {
            return formsFilter;
        }
        private Filter formsFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<evolutionChain> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length()==0){
                    filteredList.addAll(mChainListFull);
                }else{
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (evolutionChain form : mChainListFull){
                        if (form.getChain().getSpecies().getName().toLowerCase().contains(filterPattern)){
                            filteredList.add(form);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values=filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mChainList.clear();
                mChainListFull.addAll((List) results.values);
                notifyDataSetChanged();

            }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView pokemonForm1;
        CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonForm1 = itemView.findViewById(R.id.babyForm);
            parentLayout = itemView.findViewById(R.id.parent_layout_3);

        }
    }
}
