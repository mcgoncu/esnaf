package com.mandm.esnaf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mandm.esnaf.adapters.GridViewImageAdapter;

public class PhotosFragment extends Fragment {

    public static PhotosFragment newInstance() {
        return new PhotosFragment();
    }

    public PhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        final GridView mGridView = (GridView) view.findViewById(R.id.grid_view);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Constants.GRID_PADDING, getResources().getDisplayMetrics());
        final int columnWidth = ((getScreenWidth() - ((Constants.NUMBER_OF_COLUMNS_IN_GALLERY + 1) * padding)) / Constants.NUMBER_OF_COLUMNS_IN_GALLERY);

        mGridView.setNumColumns(Constants.NUMBER_OF_COLUMNS_IN_GALLERY);
        mGridView.setColumnWidth(columnWidth);
        mGridView.setStretchMode(GridView.NO_STRETCH);
        mGridView.setPadding(padding, padding, padding, padding);
        mGridView.setHorizontalSpacing(padding);
        mGridView.setVerticalSpacing(padding);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                intent.putExtra(FullscreenActivity.CURRENT_FILE, i);
                startActivity(intent);
            }
        });
        mGridView.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        GridViewImageAdapter mGridViewImageAdapter = new GridViewImageAdapter(getActivity(), columnWidth);
                        mGridView.setAdapter(mGridViewImageAdapter);
                    }
                }, 0);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_section1);
        ((MainActivity) getActivity()).restoreActionBar();
    }

    private int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
}
