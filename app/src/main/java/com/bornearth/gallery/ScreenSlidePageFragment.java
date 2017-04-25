/*
 * Copyright - All Rights Reserved
 */
package com.bornearth.gallery;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bornearth.utils.BornearthConst;
import com.bornearth.utils.BornearthUtils;
import com.example.kevin.bornearth.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * @author Marcellin RWEGO
 * @since 1.0.0 [12/02/2017]
 */
public class ScreenSlidePageFragment extends Fragment {

    private static final String ASSET_PATH_PRE = "file:///android_asset/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        String imgUriStr = getArguments().getString(BornearthConst.IMAGE_URI_STR);
        String imgDescStr = getArguments().getString(BornearthConst.IMAGE_DESC);
        if (imgUriStr.isEmpty()) {
            return rootView;
        }
        Uri imgUri = Uri.parse(imgUriStr);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
            ImageView currImgView = (ImageView) rootView.findViewById(R.id.image_a_afficher);
            currImgView.setImageBitmap(bitmap);

            TextView imgDate = (TextView) rootView.findViewById(R.id.image_date_charg);
            imgDate.setText(imgDescStr);
        } catch (IOException e) {
            // TODO To be deleted
            e.printStackTrace();
        }
        return rootView;
    }

    // TODO Dans les params passes on ajoutera la description et la date de l'image
    public static ScreenSlidePageFragment newInstance(String imgUriAtPos, String imgDate) {
        ScreenSlidePageFragment sspf = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString(BornearthConst.IMAGE_URI_STR, imgUriAtPos);
        args.putString(BornearthConst.IMAGE_DESC, imgDate);
        sspf.setArguments(args);
        return sspf;
    }
}
