package com.gokhanbarisaker.osapissample.fragment;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gokhanbarisaker.osapissample.Application;
import com.gokhanbarisaker.osapissample.R;
import com.gokhanbarisaker.osapissample.model.Photo;
import com.squareup.picasso.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShareFragment extends Fragment {
    private static final String ARG_PHOTO = "sharefragment.photo";

    private Photo photo;
    private ImageView imageView;
    private TextView textView;
    private View wrapper;

    public static ShareFragment newInstance(Photo photo)
    {
        ShareFragment fragment = new ShareFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PHOTO, photo);
        fragment.setArguments(args);
        return fragment;
    }

    public ShareFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photo = getArguments().getParcelable(ARG_PHOTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_share, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.share_imageview);
        textView = (TextView) rootView.findViewById(R.id.share_textview);
        wrapper = rootView.findViewById(R.id.share_wrapper);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Application.picasso.load(photo.getImageUrl()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        // Do something with colors...

                        wrapper.setBackgroundColor(palette.getDarkMutedColor(Color.BLACK));
                        textView.setTextColor(palette.getLightVibrantColor(Color.WHITE));
                    }
                });
            }

            @Override
            public void onError() {

            }
        });
        textView.setText(photo.getTitle());
    }

    @Override
    public void onStop() {
        super.onStop();

        Application.picasso.cancelRequest(imageView);
    }

    public Photo getPhoto() {
        return photo;
    }
}
