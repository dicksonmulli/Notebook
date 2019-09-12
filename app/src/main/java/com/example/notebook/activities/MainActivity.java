package com.example.notebook.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notebook.AppExecutors;
import com.example.notebook.R;
import com.example.notebook.adapters.NoteListAdapter;
import com.example.notebook.data.ItemDataSource;
import com.example.notebook.data.ItemRepository;
import com.example.notebook.data.local.ItemDb;
import com.example.notebook.data.local.LocalDataSource;
import com.example.notebook.data.model.Item;
import com.example.notebook.viewmodel.MainActivityViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // view, adapters and dataset
    private NoteListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Item> mItemList;
    private ItemRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initial configuration and setup
        mRecyclerView = findViewById(R.id.recycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddNoteActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        mRepository = ItemRepository.getInstance(LocalDataSource.getInstance(new AppExecutors(), ItemDb.getInstance(this).itemDao()));

        // if notes list is null, create new list and get the notes
        if (mItemList == null || mItemList.size() == 0) {
            mItemList = new ArrayList<>();
        } else {

            mAdapter.setData(mItemList);

            // notify on data change if adapter is not null
            mAdapter.notifyDataSetChanged();
        }

        getNotes();

        MainActivityViewModel mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        // Observe change on the list
        mViewModel.getItemLiveData().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> itemList) {

                mItemList = itemList;

                setAdapter();
            }
        });
    }

    /**
     * Method to set adapter
     */
    private void setAdapter() {

        // attach adapter
        if (mAdapter == null) {
            mAdapter = new NoteListAdapter(mItemList, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {

            // Set new data
            mAdapter.setData(mItemList);

            // notify on data change if adapter is not null
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to fetch restaurants
     */
    private void getNotes() {

        mRepository.getItems(new ItemDataSource.LoadItemsCallback() {
            @Override
            public void onItemsLoaded(@NotNull List<Item> items) {
                mItemList = items;
                setAdapter();
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(MainActivity.this, "Data not loaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("MainActivity", "******************************** onResume");
        getNotes();;
    }
}
