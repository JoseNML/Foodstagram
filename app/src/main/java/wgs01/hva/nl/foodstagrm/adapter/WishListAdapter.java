package wgs01.hva.nl.foodstagrm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.activity.SinglePostActivity;
import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.util.Constants;
import wgs01.hva.nl.foodstagrm.viewholder.WishViewHolder;

public class WishListAdapter extends RecyclerView.Adapter<WishViewHolder>{

    private List<Recipe> recipes;
    private Context context;

    public WishListAdapter(Context pContext, List<Recipe> pRecipes) {
        this.context = pContext;
        this.recipes = pRecipes;
    }

    @NonNull
    @Override
    public WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_wishlist_item, parent, false);
        WishViewHolder wishViewHolder = new WishViewHolder(view);

        return wishViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishViewHolder holder, int position) {
        final ImageView img_popupMenu = holder.popupmenu_wishlist;
        final int p = position;

        Picasso.get()
                .load(recipes.get(position).getImageLink())
                .into(holder.wishlistitem_image);

        holder.wishlistitem_username.setText(recipes.get(position).getFull_name());
        holder.wishlistitem_description.setText(recipes.get(position).getDescription());

        // TODO: CLEAN
        holder.popupmenu_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, img_popupMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_wishlist, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_add_agenda:
                                addWishToAgenda();
                                return true;
                            case R.id.menu_share_wish:
                                shareWish();
                                return true;
                            case R.id.menu_delete_wish:
                                deleteWish(p);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open another activity on item click
                Intent intent = new Intent(context, SinglePostActivity.class);

                intent.putExtra(Constants.IMG_ID, recipes.get(p).getImageId());
                intent.putExtra(Constants.IMG_PARAM, recipes.get(p).getImageLink());
                intent.putExtra(Constants.TITLE_PARAM, recipes.get(p).getDescription());
                intent.putExtra(Constants.IMG_USER_ID, recipes.get(p).getUserId());
                intent.putExtra("selected_username", recipes.get(p).getFull_name());
                intent.putExtra(Constants.RECIPE_URL_PARAM, "");

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    private void addWishToAgenda(){

    }

    private void shareWish(){

    }

    private void deleteWish(int pPosition){
        final String unique_wishlist_child = BaseActivity.getUser().getId() + "_" + recipes.get(pPosition).getImageId();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage("Weet je zeker dat je dit gerecht wilt verwijderen?")
                .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Ja",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Constants.foodstagramDB
                                .child(Constants.CHILD_WISHLIST)
                                .child(unique_wishlist_child).removeValue();
                    }
                }).show();
    }
}
