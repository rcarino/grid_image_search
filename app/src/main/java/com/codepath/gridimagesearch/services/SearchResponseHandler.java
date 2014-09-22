package com.codepath.gridimagesearch.services;

import android.content.Context;
import android.widget.Toast;

import com.codepath.gridimagesearch.adapters.ImageResultsAdapter;
import com.codepath.gridimagesearch.models.ImageResult;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rcarino on 9/20/14.
 */
public class SearchResponseHandler extends JsonHttpResponseHandler {

    private ImageResultsAdapter imageResultsAdapter;
    private Context context;

    public SearchResponseHandler(ImageResultsAdapter imageResultsAdapter, Context context) {
        this.imageResultsAdapter = imageResultsAdapter;
        this.context = context;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        JSONArray imageResultsJson;
        try {
            imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
            imageResultsAdapter.addAll(ImageResult.fromJSONArray(imageResultsJson));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Toast.makeText(context, "Network currently unavailable.", Toast.LENGTH_LONG).show();
    }
}
