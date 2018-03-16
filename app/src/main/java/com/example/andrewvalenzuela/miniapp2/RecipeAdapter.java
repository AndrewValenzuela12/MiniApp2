package com.example.andrewvalenzuela.miniapp2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by AndrewValenzuela on 3/10/18.
 */

public class RecipeAdapter extends BaseAdapter {
    // anytime you use BaseAdapter you have to Override these methods
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private LayoutInflater mInflater;

    //constructor
    public RecipeAdapter(Context mContext, ArrayList<Recipe> mRecipeList) {

        //initialize instance variables
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
        mInflater =(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    //methods
    // a list of methods we need to override

    //gives you the number of recipes in the data source
    @Override
    public int getCount(){
        return mRecipeList.size();
    }

    //returns the item at a specific position in the data source
    @Override
    public Object getItem(int position) {
        return mRecipeList.get(position);
    }

    //returns the row ID associated with the specific position in the list
    @Override
    public long getItemId(int position){
        return position;
    }

    //returns a view
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        //check if the view already exists
        //if yes, you don't need to inflate and findViewbyId again

        if (convertView == null) {
            //inflate
            convertView = mInflater.inflate(R.layout.result_activity, parent, false);

            //add the views to the holder
            holder = new ViewHolder();

            //views
            holder.titleTextView = convertView.findViewById(R.id.recipe_list_title);
            holder.servingTextView = convertView.findViewById(R.id.recipe_list_serving);
            holder.thumbnailImageView = convertView.findViewById(R.id.recipe_list_thumbnail);
            holder.preptimeTextView = convertView.findViewById(R.id.recipe_preptime);
            holder.cookingImageButton = convertView.findViewById(R.id.wantTocook_button);
            //add the holder to the view
            convertView.setTag(holder);
        }

        else{
            //get the view holder from the convertview
            holder = (ViewHolder) convertView.getTag();
        }

        // get relevant subview of the row view
        TextView titleTextView = holder.titleTextView;
        TextView servingTextView = holder.servingTextView;
        TextView preptimeTextView = holder.preptimeTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;
        ImageButton cookingImageButton = holder.cookingImageButton;
        // get corresponding recipe for each row
       final Recipe recipe = (Recipe)getItem(position);

        // update the row view's textviews and imageview to display the information

        //titletextView
        titleTextView.setText(recipe.title);
        titleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        titleTextView.setTextSize(18);

        //servingTextView
        servingTextView.setText(recipe.servings + " Servings");
        servingTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));

        //preptimeTextView
        preptimeTextView.setText(recipe.prepTime);
        preptimeTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));

        // imageView
        // use Picasso library to load image from the image url
        Picasso.with(mContext).load(recipe.imageURL).into(thumbnailImageView);

        cookingImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(recipe.title, recipe.URL);
            }
        });

        return convertView;

    }

    // viewHolder
    // is used to customize what you want to put into the view
    // it depends on the layout design of your row
    // this will be a private static class you have to define
    private static class ViewHolder {
        public TextView titleTextView;
        public TextView servingTextView;
        public TextView preptimeTextView;
        public ImageView thumbnailImageView;
        public ImageButton cookingImageButton;

    }

    // intent is used to pass information between activities
    // intent -> package
    // sender, receiver

    private void launchActivity(String title, String url){
        // package intent
        // start activity

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "default");

        String content = "The instruction for " + title + " can be found here!";

        //set icons
        builder.setSmallIcon(android.R.drawable.btn_star);
        builder.setStyle(new NotificationCompat.BigTextStyle(builder).bigText(content));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // create a pending intent for the notification with the intent I created
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,0, intent, 0);
        builder.setContentIntent(pendingIntent);

        //set the title and content of the notification
        builder.setContentTitle("Cooking Instruction");
        builder.setContentText(content);

        // get the system service to display this notification
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        //notify
        notificationManager.notify(1, builder.build());


    }

}
