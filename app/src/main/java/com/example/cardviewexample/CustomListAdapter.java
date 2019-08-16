package com.example.cardviewexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

class CustomListAdapter extends ArrayAdapter<Cards> {

    public static final String TAG = "CustomListAdapter";

    private Context mContext;
    private int mRessource;
    private int lastPosition = -1;

    private static class ViewHolder{
        TextView title;
        ImageView image;
    }

    public CustomListAdapter(Context context, int ressource, ArrayList<Cards> objects){
        super(context,ressource,objects);
        mContext = context;
        mRessource = ressource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //setup the image loader library
        setupImageLoader();

        //get the person's information
        String title = getItem(position).getTitle();
        String imgURL = getItem(position).getImgURL();

        try {
            //create the view result for showing the animation
            final View result;

            //ViewHolder object
            ViewHolder holder;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mRessource, parent, false);
                holder = new ViewHolder();
                holder.title = convertView.findViewById(R.id.cardTitle);
                holder.image = convertView.findViewById(R.id.cardImage);

                result = convertView;

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
            result.startAnimation(animation);
            lastPosition = position;

            holder.title.setText(title);

            //create the imageloader object
            ImageLoader imageLoader = ImageLoader.getInstance();

            int defaultImage = mContext.getResources().getIdentifier("@mipmap/ic_launcher", null, mContext.getPackageName());

            //create display options
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisk(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(defaultImage)
                    .showImageOnFail(defaultImage)
                    .showImageOnLoading(defaultImage).build();

            //download and display image from url
            imageLoader.displayImage(imgURL, holder.image, options);

            return convertView;
        }catch (IllegalArgumentException e){
            Log.e(TAG,"getView: IllegalArgumentException: " + e.getMessage());
            return convertView;
        }
    }

    private void setupImageLoader(){
        //UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }
}
