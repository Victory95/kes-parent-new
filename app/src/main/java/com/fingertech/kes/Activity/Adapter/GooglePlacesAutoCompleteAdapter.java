package com.fingertech.kes.Activity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.R;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class GooglePlacesAutoCompleteAdapter extends ArrayAdapter implements Filterable {
    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyAjaodN8RLeVCP2fewb-fQe8y47I2I_Pwc";
    private ArrayList<AutocompletePrediction> resultList;
    private Context context = null;
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.NORMAL);

    private GeoDataClient mGeoDataClient;

    /**
     * The bounds used for Places Geo Data autocomplete API requests.
     */
    private LatLngBounds mBounds;

    /**
     * The autocomplete filter used to restrict queries to a specific set of place types.
     */
    private AutocompleteFilter mPlaceFilter;

    public GooglePlacesAutoCompleteAdapter(Context context, GeoDataClient geoDataClient,
                                           LatLngBounds bounds, AutocompleteFilter filter) {
        super(context, R.layout.layout_map, R.id.textViewName);
        this.context = context;
        mGeoDataClient = geoDataClient;
        mBounds = bounds;
        mPlaceFilter = filter;
    }


    @Override
    public int getCount() {
        if(resultList != null)
            return resultList.size();
        else
            return 0;
    }

    @Override
    public AutocompletePrediction getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        // Sets the primary and secondary text for a row.
        // Note that getPrimaryText() and getSecondaryText() return a CharSequence that may contain
        // styling based on the given CharacterStyle.

        AutocompletePrediction item = getItem(position);

        TextView textView1 = (TextView) row.findViewById(R.id.textViewName);
        textView1.setText(item.getFullText(STYLE_BOLD));
        return row;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                // We need a separate list to store the results, since
                // this is run asynchronously.
                ArrayList<AutocompletePrediction> filterData = new ArrayList<>();

                // Skip the autocomplete query if no constraints are given.
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    filterData = getAutocomplete(constraint);
                }

                results.values = filterData;
                if (filterData != null) {
                    results.count = filterData.size();
                } else {
                    results.count = 0;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    resultList = (ArrayList<AutocompletePrediction>) results.values;
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                // Override this method to display a readable result in the AutocompleteTextView
                // when clicked.
                if (resultValue instanceof AutocompletePrediction) {
                    return ((AutocompletePrediction) resultValue).getFullText(null);
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }

    private ArrayList<AutocompletePrediction> getAutocomplete(CharSequence constraint) {
        Log.i("KES", "Starting autocomplete query for: " + constraint);

        // Submit the query to the autocomplete API and retrieve a PendingResult that will
        // contain the results when the query completes.
        Task<AutocompletePredictionBufferResponse> results =
                mGeoDataClient.getAutocompletePredictions(constraint.toString(), mBounds,
                        mPlaceFilter);

        // This method should have been called off the main UI thread. Block and wait for at most
        // 60s for a result from the API.
        try {
            Tasks.await(results, 60, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            AutocompletePredictionBufferResponse autocompletePredictions = results.getResult();

            Log.i("KES", "Query completed. Received " + autocompletePredictions.getCount()
                    + " predictions.");

            // Freeze the results immutable representation that can be stored safely.
            return DataBufferUtils.freezeAndClose(autocompletePredictions);
        } catch (RuntimeExecutionException e) {
            // If the query did not complete successfully return null
            Toast.makeText(getContext(), "Error contacting API: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
            Log.e("KES", "Error getting autocomplete prediction API call", e);
            return null;
        }
    }
}