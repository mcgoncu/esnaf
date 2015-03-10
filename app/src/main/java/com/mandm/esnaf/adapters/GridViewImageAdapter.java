package com.mandm.esnaf.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.mandm.esnaf.Constants;

import java.lang.ref.WeakReference;

public class GridViewImageAdapter extends BaseAdapter {

    private Context mContext;
    private int mImageWidth;

    public GridViewImageAdapter(Context context, int imageWidth) {
        mContext = context;
        mImageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return Constants.DRAWABLES.size();
    }

    @Override
    public Object getItem(int position) {
        return Constants.DRAWABLES.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(mImageWidth, mImageWidth));
        } else {
            imageView = (ImageView) convertView;
        }

//        WorkerTask task = new WorkerTask(imageView, Constants.DRAWABLES.get(position));
//        task.execute();
        imageView.setImageResource(Constants.DRAWABLES.get(position));

        return imageView;
    }

    class WorkerTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<ImageView> mImageViewReference;
        private int mResourceId;

        public WorkerTask(ImageView imageView, int resourceId) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            mImageViewReference = new WeakReference<>(imageView);
            mResourceId = resourceId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final ImageView imageView = mImageViewReference.get();
            if(imageView != null)
            {
                imageView.setImageResource(mResourceId);
            }
            return null;
        }
    }
}
