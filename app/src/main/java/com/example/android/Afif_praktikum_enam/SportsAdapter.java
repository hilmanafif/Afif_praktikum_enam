package com.example.android.Afif_praktikum_enam;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.android.Afif_praktikum_enam.R.id.useLogo;

/***
 * The adapter class for the RecyclerView, contains the sports data
 */
class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportsViewHolder>  {

    //Member variables
    private GradientDrawable mGradientDrawable;
    private ArrayList<Sport> mSportsData;
    private Context mContext;

    /**
     * Constructor that passes in the sports data and the context
     * @param sportsData ArrayList containing the sports data
     * @param context Context of the application
     */
    SportsAdapter(Context context, ArrayList<Sport> sportsData) {
        this.mSportsData = sportsData;
        this.mContext = context;

        //Prepare gray placeholder
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext,R.drawable.img_badminton);
        if(drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
        }
    }

    @Override
    public SportsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SportsViewHolder(mContext, LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false), mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(SportsViewHolder holder, int position) {

        //Get current sport
        Sport currentSport = mSportsData.get(position);

        //Bind the data to the view
        holder.bindTo(currentSport);

    }

    @Override
    public int getItemCount() {
        return mSportsData.size();
    }


    static class SportsViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        //Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mSportsimage;
        private Context mContext;
        private Sport mCurrentSport;
        private GradientDrawable mGradientDrawable;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         * @param itemView The rootview of the list_item.xml layout file
         */
        SportsViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views
            mTitleText = (TextView)itemView.findViewById(R.id.title);
            mInfoText = (TextView)itemView.findViewById(R.id.subTitle);
            mSportsimage = (ImageView) itemView.findViewById(R.id.sportsImage);

            mContext = context;
            mGradientDrawable = gradientDrawable;

            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        void bindTo(Sport currentSport){
            //Populate the textviews with data
            mTitleText.setText(currentSport.getTitle());
            mInfoText.setText(currentSport.getInfo());

            //get the current sport
            mCurrentSport = currentSport;

            //LOad rhe images into the ImageView using the Glide Library
            Glide.with(mContext).load(currentSport.getImageResource()).
                    placeholder(mGradientDrawable).into(mSportsimage);
        }

        @Override
        public void onClick(View view) {
        //Set up the detail intent
            Intent detailIntent = Sport.starter(mContext,
                    mCurrentSport.getTitle(),
                    mCurrentSport.getImageResource());

            //Start the detail activity
            mContext.startActivity(detailIntent);
        }
    }
}
