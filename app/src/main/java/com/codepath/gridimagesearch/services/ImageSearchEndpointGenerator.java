package com.codepath.gridimagesearch.services;

import com.codepath.gridimagesearch.models.ImageSearchFilters;

/**
 * Created by rcarino on 9/20/14.
 */
public class ImageSearchEndpointGenerator {
    public static final int RESULT_SIZE = 8;
    public static final String RESOURCE = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz="
            + Integer.toString(RESULT_SIZE);

    private static ImageSearchEndpointGenerator instance;
    public ImageSearchFilters filters = new ImageSearchFilters();

    private ImageSearchEndpointGenerator() {

    }

    public static ImageSearchEndpointGenerator getInstance() {
        if (instance == null) {
            instance = new ImageSearchEndpointGenerator();
        }

        return instance;
    }

    public String getEndpoint(String query, int page) {
        String endpoint = RESOURCE + "&q=" + query;

        if (!filters.getImageSize().isEmpty()) {
            endpoint += "&imgsz=" + filters.getImageSize();
        }

        if (!filters.getColorFilter().isEmpty()) {
            endpoint += "&imgcolor=" + filters.getColorFilter();
        }

        if (!filters.getImageType().isEmpty()) {
            endpoint += "&imgtype=" + filters.getImageType();
        }

        if (!filters.getSiteFilter().isEmpty()) {
            endpoint += "&as_sitesearch=" + filters.getSiteFilter();
        }

        if (page > 1) {
            int offset = ((page - 1) * RESULT_SIZE);
            endpoint += "&start=" + Integer.toString(offset);
        }

        return endpoint;
    }
}
