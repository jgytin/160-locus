package com.example.jt.locus_draft;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class SavedAdapter extends RecyclerView.Adapter {

    private RecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<Saved> mSaved;

    public SavedAdapter(Context context, ArrayList<Saved> saved, RecyclerView rv) {
        mContext = context;
        mSaved = saved;
        mRecyclerView = rv;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.saved_cell_layout, parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int index = mRecyclerView.getChildLayoutPosition(view);
//                Saved saved = (Saved) mSaved.get(index);
//                Intent gotoComments = new Intent(mContext, CommentFeedActivity.class);
//                gotoComments.putExtra("index", index);
//                gotoComments.putExtra("name", (String) hm.get("landmark_name"));
//                gotoComments.putExtra("username", username);
//                mContext.startActivity(gotoComments);
//            }
//        });
        return new SavedViewHolder(view);
    }


    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // here, we the comment that should be displayed at index `position` in our recylcer view
        // everytime the recycler view is refreshed, this method is called getItemCount() times (because
        // it needs to recreate every cell).
        Saved saved = (Saved) mSaved.get(position);
        ((SavedViewHolder) holder).bind(saved);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mSaved.size();
    }

}

class SavedViewHolder extends RecyclerView.ViewHolder {

    // each data item is just a string in this case
    public LinearLayout mSavedBubbleLayout;
    public ImageView mImageView;
    public TextView mNameTextView;

    public SavedViewHolder(View itemView) {
        super(itemView);
        mSavedBubbleLayout = itemView.findViewById(R.id.saved_cell_layout);
        mImageView = mSavedBubbleLayout.findViewById(R.id.saved_cell_image);
        mNameTextView = mSavedBubbleLayout.findViewById(R.id.saved_cell_name);
    }

    void bind(Saved saved) {
//        mImageView.setImageResource(R.drawable.strawberry_creek);
        mNameTextView.setText(saved.name);
    }
}