package com.mandm.esnaf;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;


public class FullscreenActivity extends FragmentActivity {

    public static final String CURRENT_FILE = "current_file";

    private String[] mFileNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mFileNames = getAssets().list(Constants.PHOTOS_FOLDER);
        } catch (IOException e) {
            mFileNames = new String[0];
            e.printStackTrace();
        }

        int currentFile = getIntent().getIntExtra(CURRENT_FILE, 0);

        setContentView(R.layout.activity_fullscreen);

        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(currentFile);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.newInstance(mFileNames[position]);
        }

        @Override
        public int getCount() {
            return mFileNames.length;
        }
    }

    public static class ScreenSlidePageFragment extends Fragment {

        private static final String FILE_NAME = "file_name";

        private String mFileName;

        public static ScreenSlidePageFragment newInstance(String fileName) {
            ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
            Bundle bundle = new Bundle();
            bundle.putString(FILE_NAME, fileName);
            fragment.setArguments(bundle);
            return fragment;
        }

        public ScreenSlidePageFragment() {

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mFileName = getArguments().getString(FILE_NAME);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(
                    new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));

            InputStream inputStream = null;
            try {
                inputStream = getActivity().getAssets().open(Constants.PHOTOS_FOLDER + "/" + mFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);

            return imageView;
        }
    }
}
