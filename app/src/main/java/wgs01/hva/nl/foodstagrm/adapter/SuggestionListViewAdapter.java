package wgs01.hva.nl.foodstagrm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import wgs01.hva.nl.foodstagrm.R;
import wgs01.hva.nl.foodstagrm.network.instagramObject.Data;

public class SuggestionListViewAdapter extends ArrayAdapter<Data> {

    private ArrayList<Data> data;
    private PopupMenu popup;

    public SuggestionListViewAdapter(Context context, int textViewResourceId, ArrayList<Data> objects) {
        super(context, textViewResourceId, objects);
        this.data = objects;
    }

    // TODO: CLEAN CODE
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View curView = convertView;

        if (curView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curView = vi.inflate(R.layout.listview_suggestion_item, null);
        }

        final ImageView imageView_suggestion = curView.findViewById(R.id.imageView_suggestion);
        final ImageView popupMenu = curView.findViewById(R.id.popup_menu);

        popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getContext(), popupMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_suggestion, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        TextView tv_user_fullname = curView.findViewById(R.id.profile_name);
        TextView tv_user_des = curView.findViewById(R.id.message_description);

        tv_user_fullname.setText(data.get(position).getUser().getFull_name());
        tv_user_des.setText(data.get(position).getCaption().getText());

        Picasso.get()
                .load(data.get(position).getImages().getStandard_resolution().getUrl())
                .into(imageView_suggestion);

        return curView;
    }
}
