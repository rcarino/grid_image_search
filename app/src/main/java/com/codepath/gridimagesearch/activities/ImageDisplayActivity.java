package com.codepath.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageResult;
import com.codepath.gridimagesearch.services.BitmapUriExtractor;
import com.codepath.gridimagesearch.services.OnSwipeTouchListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageDisplayActivity extends Activity {
    public static final String RESULT_INDEX_INTENT_KEY = "resultIndex";
    public static final String RESULTS_MODEL_INTENT_KEY = "resultsModel";

    private ImageView ivResult;

    private PhotoViewAttacher attacher;

    private List<ImageResult> results;
    private int currentResultsIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        getActionBar().hide();

        findAndSetupIvResult();

        setupShareIcon();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupIvResultSwipeListener(ImageView ivResult) {
        ivResult.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                if (currentResultsIndex < (results.size() - 1)) {
                    currentResultsIndex += 1;
                    displayImage(results.get(currentResultsIndex));
                }
            }

            @Override
            public void onSwipeRight() {
                if (currentResultsIndex > 0) {
                    currentResultsIndex -= 1;
                    displayImage(results.get(currentResultsIndex));
                }
            }
        });
    }

    private void findAndSetupIvResult() {
        ivResult = (ImageView) findViewById(R.id.ivImageResult);
        setupIvResultSwipeListener(ivResult);

        ImageResult result = getResultFromIntent();
        displayImage(result);
    }

    private void displayImage(ImageResult image) {
        ivResult.setImageResource(0);
        Picasso.with(this).load(image.fullUrl).into(ivResult);
    }

    private ImageResult getResultFromIntent() {
        results = (List<ImageResult>) getIntent().getSerializableExtra(RESULTS_MODEL_INTENT_KEY);
        currentResultsIndex = getIntent().getIntExtra(RESULT_INDEX_INTENT_KEY, 0);

        return results.get(currentResultsIndex);
    }

    private void setupShareIcon() {
        ImageView shareIcon = (ImageView) findViewById(R.id.ivShare);
        shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(ivResult);
            }
        });
    }

    private void shareImage(ImageView ivResult) {
        Uri bitmapUri = BitmapUriExtractor.extractFromImageView(ivResult);

        if (bitmapUri != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
            startActivity(Intent.createChooser(shareIntent, "Share image using"));
        } else {
            Toast.makeText(this, "Failed to share Image", Toast.LENGTH_SHORT);
        }
    }
}
