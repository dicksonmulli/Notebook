package com.example.notebook.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notebook.R;
import com.example.notebook.activities.DetailActivity;
import com.example.notebook.data.model.Item;

import java.util.List;

/**
 * Created by dickson-incentro on 3/10/18.
 */

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private List<Item> mDataset;
    private Activity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View root;
        private TextView title;
        private TextView tag;
        private TextView description;


        public ViewHolder(View view) {
            super(view);
            root = view;
            title = view.findViewById(R.id.title);
            tag = view.findViewById(R.id.tag);
            description = view.findViewById(R.id.description);
        }
    }

    /**
     * Constructor
     *
     * @param dataset mDataset
     */
    public NoteListAdapter(List<Item> dataset, Activity activity) {
        mActivity = activity;
        mDataset = dataset;
    }

    /**
     * Create new view
     *
     * @param parent   parent
     * @param viewType viewType
     * @return new view holder
     */
    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note, parent, false);
        return new NoteListAdapter.ViewHolder(itemView);
    }

    /**
     * Replace the contents of the view
     *
     * @param holder   holder
     * @param position position
     */
    @Override
    public void onBindViewHolder(NoteListAdapter.ViewHolder holder, int position) {
        final Item item = mDataset.get(position);

        holder.title.setText(item.getTitle());
        holder.description.setText(String.valueOf(item.getDescription()));
        holder.tag.setText(item.getTag());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, DetailActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("item", item);
                intent.putExtras(b);
                mActivity.startActivity(intent);
            }
        });
    }

    /**
     * Method to set new data to the adapter
     * @param itemList
     */
    public void setData(List<Item> itemList) {
        mDataset = itemList;
    }

    /**
     * Return the size of the dataset
     *
     * @return int representing the dataset
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

