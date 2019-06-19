package com.example.filesharing_up.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.filesharing_up.R;
import com.example.filesharing_up.activities.MaterialetActivity;
import com.example.filesharing_up.models.Afatet;

import java.util.ArrayList;

public class AfatetAdapter extends RecyclerView.Adapter<AfatetAdapter.ViewHolder>{
    private ArrayList<Afatet> afatets;

    public AfatetAdapter(){
        afatets = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup.getContext(),afatets,LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_afatet,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Afatet afatet = afatets.get(i);
        viewHolder.afatet.setText(afatet.getTitle());
        viewHolder.materialet.setText(afatet.getMaterials().size() + " materiale");
    }


    @Override
    public int getItemCount() {
        return afatets.size();
    }

    public void addAfat(Afatet afat) {
        this.afatets.add(afat);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView afatet, materialet;
        private ArrayList<Afatet> mAfates;
        private Context mContext;

        public ViewHolder(final Context context, ArrayList<Afatet> afates, View itemView) {
            super(itemView);

            afatet = itemView.findViewById(R.id.afat);
            materialet = itemView.findViewById(R.id.materiale);
            this.mContext = context;
            this.mAfates = afates;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent materialetIntent = new Intent(mContext, MaterialetActivity.class);
                    materialetIntent.putExtra("MATERIAL", mAfates.get(getAdapterPosition()));
                    mContext.startActivity(materialetIntent);
                }
            });
        }
    }
}
