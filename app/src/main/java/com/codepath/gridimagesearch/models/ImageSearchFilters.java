package com.codepath.gridimagesearch.models;

import java.io.Serializable;

/**
 * Created by rcarino on 9/20/14.
 *
 * Data container that ensures properties are not null, spinner accessed properties don't store the
 * "any" string, and normalizes all strings to lowercase.
 */
public class ImageSearchFilters implements Serializable {
    private String imageSize = "";
    private String colorFilter = "";
    private String imageType = "";
    private String siteFilter = "";

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter.toLowerCase();
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        if (!isStringNullOrAny(imageType)) {
            this.imageType = imageType.toLowerCase();
        }
    }

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        if (!isStringNullOrAny(colorFilter)) {
            this.colorFilter = colorFilter.toLowerCase();
        }
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        if (!isStringNullOrAny(imageSize)) {
            this.imageSize = imageSize.toLowerCase();
        }
    }

    private boolean isStringNullOrAny(String val) {
        if (val == null) {
            return false;
        }
        return val.toLowerCase().equals("any");
    }
}
