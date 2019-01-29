package com.fingertech.kes.Activity.Search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.fingertech.kes.Activity.Adapter.AdapterBookmark;
import com.fingertech.kes.Activity.Maps.SearchingMAP;
import com.fingertech.kes.Activity.Model.Data;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.BookmarkTabel;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    SearchView searchView;
    AdapterBookmark bookmarkAdapter;
    List<Data> itemList = new ArrayList<Data>();
    BookmarkTabel bookmarkTabel = new BookmarkTabel();
    Data data = new Data();
    Button delete,filter;
    Double latitude,longitude;
    ArrayList<HashMap<String, String>> row;
    Toolbar ToolBarAtas2;
    Button arrof;
    SearchManager searchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet);

        recyclerView           = findViewById(R.id.recycleBookmark);
        delete                 = recyclerView.findViewById(R.id.BookMark);
        searchView             = (SearchView)findViewById(R.id.search_filter);
        layoutManager          = new LinearLayoutManager(this);
        ToolBarAtas2           = (Toolbar)findViewById(R.id.toolbar_back);
        bookmarkAdapter        = new AdapterBookmark(itemList,FilterActivity.this);
        slidingUpPanelLayout   = (SlidingUpPanelLayout)findViewById(R.id.sliding);
        filter                 = (Button)findViewById(R.id.filter);
        arrof                  = (Button)findViewById(R.id.arrowF);
        searchManager          = (SearchManager)getSystemService(Context.SEARCH_SERVICE);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(bookmarkAdapter);

        getAllData();

        setSupportActionBar(ToolBarAtas2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });
        slidingUpPanelLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        });

        arrof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        });

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookmarkAdapter.getFilter(query).filter(query);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookmarkAdapter.getFilter(newText).filter(newText);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }
    private void getAllData(){
       row = bookmarkTabel.getAllData();

        for (int i = 0; i < row.size(); i++) {
            String id       = row.get(i).get(Data.KEY_CourseId);
            String poster   = row.get(i).get(Data.KEY_Name);
            String title    = row.get(i).get(Data.KEY_ALAMAT);
            data = new Data();
            data.setId(id);
            data.setName(poster);
            data.setAddress(title);
            itemList.add(data);
        }
        bookmarkAdapter.notifyDataSetChanged();
        bookmarkAdapter.setOnItemClickListener(new AdapterBookmark.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                latitude     = Double.parseDouble(row.get(position).get(Data.KEY_LATITUDE));
                longitude    = Double.parseDouble(row.get(position).get(Data.KEY_LONGITUDE));
                String poster   = row.get(position).get(Data.KEY_Name);
                String title    = row.get(position).get(Data.KEY_ALAMAT);
                String jenjang  = row.get(position).get(Data.KEY_JENJANG);
                String schoolid = row.get(position).get(Data.KEY_SCHOOLDETAIL);

                Intent intent = new Intent(FilterActivity.this,SearchingMAP.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("jenjang",jenjang);
                intent.putExtra("schoolid",schoolid);
                intent.putExtra("namasekolah",poster);
                intent.putExtra("alamat",title);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
