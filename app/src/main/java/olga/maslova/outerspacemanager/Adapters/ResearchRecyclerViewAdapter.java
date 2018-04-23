package olga.maslova.outerspacemanager.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import olga.maslova.outerspacemanager.R;
import olga.maslova.outerspacemanager.Research;


public class ResearchRecyclerViewAdapter extends RecyclerView.Adapter<ResearchRecyclerViewAdapter.ViewHolder> {
    private List<Research> mResearches;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ResearchRecyclerViewAdapter(List<Research> myDataset) {
        mResearches = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ResearchRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_research, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView researchName = (TextView) holder.mView.findViewById(R.id.research_title);
        TextView description = (TextView) holder.mView.findViewById(R.id.research_desc);
        ImageView researchImage = (ImageView) holder.mView.findViewById(R.id.image_research);
        researchName.setText(mResearches.get(position).getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mResearches.size();
    }
}
