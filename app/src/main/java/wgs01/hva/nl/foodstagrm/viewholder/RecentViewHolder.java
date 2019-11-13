package wgs01.hva.nl.foodstagrm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wgs01.hva.nl.foodstagrm.R;

public class RecentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView photo;

    public RecentViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        photo = itemView.findViewById(R.id.country_photo);
    }

    @Override
    public void onClick(View view) {
//        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
