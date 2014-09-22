package com.codepath.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rcarino on 9/18/14.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageInfo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }

        ViewHolder viewHolder = getViewHolder(convertView);

        viewHolder.image.setImageResource(0);
        Picasso.with(getContext()).load(imageInfo.thumbUrl).into(viewHolder.image);

        viewHolder.title.setText(Html.fromHtml(imageInfo.title));

        return convertView;
    }

    private ViewHolder getViewHolder(View convertView) {
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        if (viewHolder != null) {
            return viewHolder;
        } else {
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.ivImage);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
            return viewHolder;
        }
    }

    private static class ViewHolder {
        private ImageView image;
        private TextView title;
    }
}
