package com.codepath.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.adapters.ImageResultsAdapter;
import com.codepath.gridimagesearch.fragments.FilterSearchDialog;
import com.codepath.gridimagesearch.models.ImageResult;
import com.codepath.gridimagesearch.models.ImageSearchFilters;
import com.codepath.gridimagesearch.services.EndlessScrollListener;
import com.codepath.gridimagesearch.services.ImageSearchEndpointGenerator;
import com.codepath.gridimagesearch.services.SearchResponseHandler;
import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;


public class SearchActivity extends Activity {
    AsyncHttpClient client = new AsyncHttpClient();
    SearchResponseHandler searchResponseHandler;
    private SearchView searchView;
    private StaggeredGridView gvResults;
    private String currentQuery;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        setupMenuSearch(menu);

        return super.onCreateOptionsMenu(menu);
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

    public void openFilterDialog(MenuItem mi) {
        FilterSearchDialog filterDialog = new FilterSearchDialog();

        Bundle currentFilters = new Bundle();
        currentFilters.putSerializable(FilterSearchDialog.FILTERS_BUNDLE_KEY,
                ImageSearchEndpointGenerator.getInstance().filters);

        filterDialog.setArguments(currentFilters);
        filterDialog.show(getFragmentManager(), "test");
    }

    private void setupMenuSearch(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                doSearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                aImageResults.clear();
                aImageResults.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void setupViews() {
        gvResults = (StaggeredGridView) findViewById(R.id.gvResults);
        bindGvResultsWithModel(gvResults);

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        setupGvResultsItemClickListener(gvResults);
        setupGvResultsScrollListener(gvResults);
    }

    private void setupGvResultsItemClickListener(StaggeredGridView gvResults) {
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayInFullscreen(i);
            }
        });
    }

    private void displayInFullscreen(int imageIndex) {
        Intent request = new Intent(SearchActivity.this, ImageDisplayActivity.class);
        request.putExtra(ImageDisplayActivity.RESULT_INDEX_INTENT_KEY, imageIndex);
        request.putExtra(ImageDisplayActivity.RESULTS_MODEL_INTENT_KEY, imageResults);
        startActivity(request);
    }

    private void setupGvResultsScrollListener(StaggeredGridView gvResults) {
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                fetchAndBindSearchResults(page);
            }
        });
    }

    private void bindGvResultsWithModel(StaggeredGridView gvResults) {
        imageResults = new ArrayList<ImageResult>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
    }

    private void doSearch(String query) {
        imageResults.clear();
        currentQuery = query;
        fetchAndBindSearchResults(1);
    }

    private void fetchAndBindSearchResults(int page) {
        if (searchResponseHandler == null) {
            searchResponseHandler = new SearchResponseHandler(aImageResults, this);
        }

        String searchUrl = ImageSearchEndpointGenerator.getInstance()
                .getEndpoint(currentQuery, page);

        client.get(searchUrl, searchResponseHandler);
    }

    public void setEndpointGeneratorFilters(ImageSearchFilters filters) {
        ImageSearchEndpointGenerator.getInstance().filters = filters;
    }
}
