package com.example.filesharing_up.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.filesharing_up.R;
import com.example.filesharing_up.models.Material;

import java.util.ArrayList;

public class MaterialetAdapter extends RecyclerView.Adapter<MaterialetAdapter.ViewHolder> {
    private ArrayList<Material> materials;
    private Context mContext;

    public MaterialetAdapter() {
        materials = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_materialet, viewGroup, false), materials, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Material material = materials.get(i);

        viewHolder.material.setText(material.getTitle());
        viewHolder.type.setText(material.getType());
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }


    public void addMaterials(ArrayList<Material> materials) {
        this.materials.addAll(materials);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView material, type;
        private ArrayList<Material> materialArrayList;
        private Context context;

        public ViewHolder(@NonNull View itemView, ArrayList<Material> materials, Context mContext) {
            super(itemView);

            material = itemView.findViewById(R.id.material);
            type = itemView.findViewById(R.id.type);
            this.context = mContext;
            this.materialArrayList = materials;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = materialArrayList.get(getAdapterPosition()).getUrl();
                    if (url.startsWith("http")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        context.startActivity(browserIntent);
                    }
                }
            });
        }
    }
}
