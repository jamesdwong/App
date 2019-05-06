package uk.ac.livjm.cms.cmpjwong.routefinder;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;

public class SuggestionAdapter extends ArrayAdapter<String> {

    protected static final String TAG = "SuggestionAdapter";
    private List<SuggestGetSet> suggestions;
    public SuggestionAdapter(Activity context, String nameFilter) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        suggestions = new ArrayList<SuggestGetSet>();
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public String getItem(int index) {
        return suggestions.get(index).getName();
    }

    public SuggestGetSet getStation(int index) {
        return suggestions.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                stationHelper jp=new stationHelper();
                if (constraint != null) {
                    List<SuggestGetSet> new_suggestions =jp.getParseJsonWCF(constraint.toString(),getContext());
                    suggestions.clear();
                    for (int i=0;i<new_suggestions.size();i++) {
                        suggestions.add(new_suggestions.get(i));

                    }
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

}