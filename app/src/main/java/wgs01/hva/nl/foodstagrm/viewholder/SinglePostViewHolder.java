package wgs01.hva.nl.foodstagrm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import wgs01.hva.nl.foodstagrm.R;

public class SinglePostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView singlePostItemImage;
    public TextView singlePostItemUsername,singlePostItemLikes, singlePostItemDislikes;

    public SinglePostViewHolder(View itemView) {
        super(itemView);

        singlePostItemImage = itemView.findViewById(R.id.singlepostitem_image);
        singlePostItemUsername = itemView.findViewById(R.id.singlepostitem_username);
        singlePostItemLikes = itemView.findViewById(R.id.singlepostitem_amount_likes);
        singlePostItemDislikes = itemView.findViewById(R.id.singlepostitem_amount_dislikes);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(v.getContext(), "clicked positiion: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
    }
}
