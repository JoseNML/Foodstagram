package wgs01.hva.nl.foodstagrm.viewholder;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.model.Recipe;

public class WishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public CardView wishlist_item;
    public ImageView wishlistitem_image, popupmenu_wishlist;
    public TextView wishlistitem_username, wishlistitem_description;


    public WishViewHolder(View itemView) {
        super(itemView);

        wishlist_item = itemView.findViewById(R.id.wishist_item);

        wishlistitem_image = itemView.findViewById(R.id.wishlistitem_image);
        popupmenu_wishlist = itemView.findViewById(R.id.popupmenu_wishlist);

        wishlistitem_username = itemView.findViewById(R.id.wishlistitem_username);
        wishlistitem_description = itemView.findViewById(R.id.wishlistitem_description);

        itemView.setOnClickListener(this);
        popupmenu_wishlist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
