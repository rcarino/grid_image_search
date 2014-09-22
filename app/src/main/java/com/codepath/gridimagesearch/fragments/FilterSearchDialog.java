package com.codepath.gridimagesearch.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.activities.SearchActivity;
import com.codepath.gridimagesearch.models.ImageSearchFilters;

public class FilterSearchDialog extends DialogFragment {
    public static final String FILTERS_BUNDLE_KEY = "filters";

    private Spinner imageSize;
    private Spinner colorFilter;
    private Spinner imagetype;
    private EditText sitesFilter;
    private Button btnSave;
    private Button btnClose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View filterDialog = inflater.inflate(R.layout.fragment_filter_search_dialog, container);

        getDialog().setTitle(R.string.filters_title);
        findAndSetViews(filterDialog);
        prepopulateViewFields();
        setupButtonListeners(btnSave, btnClose);

        return filterDialog;
    }

    private void setupButtonListeners(Button btnSave, Button btnClose) {
        setupSaveFiltersButtonListener(btnSave);
        setupCancelButtonListener(btnClose);
    }

    private void setupCancelButtonListener(Button btnClose) {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void prepopulateViewFields() {
        ImageSearchFilters filters = (ImageSearchFilters) getArguments().getSerializable(FILTERS_BUNDLE_KEY);
        if (filters != null) {
            setSpinnerToValue(imageSize, filters.getImageSize());
            setSpinnerToValue(colorFilter, filters.getColorFilter());
            setSpinnerToValue(imagetype, filters.getImageType());

            sitesFilter.setText(filters.getSiteFilter());
        }
    }

    private void findAndSetViews(View filterDialog) {
        imageSize = (Spinner) filterDialog.findViewById(R.id.spinnerImageSize);
        colorFilter = (Spinner) filterDialog.findViewById(R.id.spinnerColorFilter);
        imagetype = (Spinner) filterDialog.findViewById(R.id.spinnerImageType);
        sitesFilter = (EditText) filterDialog.findViewById(R.id.etSiteFilter);
        btnSave = (Button) filterDialog.findViewById(R.id.btnSave);
        btnClose = (Button) filterDialog.findViewById(R.id.btnClose);
    }

    private void setupSaveFiltersButtonListener(Button btnSave) {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFiltersAndDismiss();
            }
        });
    }

    private void saveFiltersAndDismiss() {
        saveFilters();
        dismiss();
    }

    private void saveFilters() {
        ImageSearchFilters filters = getCurrentFilters();
        ((SearchActivity) getActivity()).setEndpointGeneratorFilters(filters);
    }


    private ImageSearchFilters getCurrentFilters() {
        ImageSearchFilters currentFilters = new ImageSearchFilters();

        currentFilters.setColorFilter(colorFilter.getSelectedItem().toString());
        currentFilters.setImageSize(imageSize.getSelectedItem().toString());
        currentFilters.setImageType(imagetype.getSelectedItem().toString());
        currentFilters.setSiteFilter(sitesFilter.getText().toString());

        return currentFilters;
    }

    private void setSpinnerToValue(Spinner spinner, String value) {
        spinner.setSelection(0);
        if (value.isEmpty()) {
            return;
        }

        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            String normalizedSpinnerValue = adapter.getItem(i).toString().toLowerCase();
            if (normalizedSpinnerValue.equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }

    }
}
