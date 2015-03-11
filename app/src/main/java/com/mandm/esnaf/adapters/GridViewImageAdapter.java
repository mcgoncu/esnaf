package com.mandm.esnaf.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.mandm.esnaf.Constants;

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

        imageView.setImageResource(Constants.DRAWABLES.get(position));
        return imageView;
    }
}
