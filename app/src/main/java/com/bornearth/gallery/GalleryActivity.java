package com.bornearth.gallery;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bornearth.core.BornearthImage;
import com.bornearth.dao.BornearthImageDAO;
import com.bornearth.utils.BornearthConst;
import com.bornearth.utils.BornearthUtils;
import com.example.kevin.bornearth.R;

import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private int numPage;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private List<BornearthImage> bornearthImages;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "GalleryActivity";

    private BornearthImageDAO imageDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Actionbar
        setupActionBar();

        // Image DAO
        imageDAO = new BornearthImageDAO(GalleryActivity.this);
        imageDAO.open();
        numPage = imageDAO.getNbrTotalImg();
        //imageDAO.supprimerToutesLesImages();
        bornearthImages = imageDAO.selectionner();

        // Back button
        ImageView backButton = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.bornearth_back_btn_action_menu);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Add pic button
        ImageView addPicButton = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.borneath_add_photo_to_gallery_btn_action_menu);
        addPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        // Delete pic button
        ImageView deletePicButton = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.bornearth_delete_btn_action_menu);
        deletePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bornearthImages.size() > 0) {
                    deleteImage(viewPager.getCurrentItem());
                }
            }
        });

        // Instantiate a ViewPager and a PagerAdapter.
        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar mActionBar = getSupportActionBar();
            assert mActionBar != null;
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.borneath_photo_gallery_custom_action_bar, null);
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    BornearthImage bi = BornearthUtils.buildBornearthImageFromUri(selectedImageUri, BornearthConst.PAS_DE_DESC, "");
                    boolean imageAdded = imageDAO.ajouter(bi);
                    if (imageAdded) {
                        bornearthImages.add(bi);
                        numPage++;
                        pagerAdapter.notifyDataSetChanged();
                        viewPager.setCurrentItem(numPage - 1);
                    }
                }
            }
        }
    }

    /**
     * A simple pager adapter that represents n ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String cheminReel = "";
            if (bornearthImages.size() > 0) {
                cheminReel = bornearthImages.get(position).getNomImgReel();
            }
            return ScreenSlidePageFragment.newInstance(cheminReel, bornearthImages.get(position).getDateImage());
        }

        @Override
        public int getCount() {
            return numPage;
        }
    }

    /**
     *  Choose an image from Gallery
     */
    private void openImageChooser() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    /**
     * Supprimer l'image a la position donnee
     * @param position la position de l'image a supprimer
     */
    private void deleteImage(int position) {
        if (bornearthImages.size() > position) {
            if (bornearthImages.size() == 1) {
                return;
            } else {
                if (position == 0) {
                    // On affiche l'element suivant
                    viewPager.setCurrentItem(position + 1);
                } else {
                    // On affiche l'element precedent
                    viewPager.setCurrentItem(position - 1);
                }
            }
            String nomImgReel = bornearthImages.get(position).getNomImgReel();
            imageDAO.supprimer(nomImgReel);
            bornearthImages.remove(position);
            numPage--;
            pagerAdapter.notifyDataSetChanged();
        }
    }
}
