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
import android.widget.TextView;

import com.fingertech.kes.Activity.Adapter.BookmarkAdapter;
import com.fingertech.kes.Activity.Maps.SearchingMAP;
import com.fingertech.kes.Activity.Model.Data;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.BookmarkTabel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    BookmarkAdapter bookmarkAdapter;
    List<Data> itemList = new ArrayList<Data>();
    BookmarkTabel bookmarkTabel = new BookmarkTabel();
    Data data = new Data();
    Button delete;
    Double latitude,longitude;
    ArrayList<HashMap<String, String>> row;
    Toolbar ToolBarAtas2;
    Button arrof;
    SearchManager searchManager;
    TextView hint_bookmark;
    MaterialSearchView materialSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        recyclerView           = findViewById(R.id.recycleBookmark);
        delete                 = recyclerView.findViewById(R.id.BookMark);
        layoutManager          = new LinearLayoutManager(this);
        ToolBarAtas2           = findViewById(R.id.toolbar_back);
        bookmarkAdapter        = new BookmarkAdapter(itemList,FilterActivity.this);
        hint_bookmark          = findViewById(R.id.hint_bookmark);
        materialSearchView      = findViewById(R.id.search_view);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(bookmarkAdapter);

        getAllData();

        setSupportActionBar(ToolBarAtas2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookmarkAdapter.getFilter(query).filter(query);
                recyclerView.setVisibility(View.VISIBLE);
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookmarkAdapter.getFilter(newText).filter(newText);
                recyclerView.setVisibility(View.VISIBLE);
                //Do some magic
                return false;
            }
        });
    }
    private void getAllData(){
       row = bookmarkTabel.getAllData();

       if (row.size() == 0){
           hint_bookmark.setVisibility(View.VISIBLE);
           recyclerView.setVisibility(View.GONE);
       }else {
           hint_bookmark.setVisibility(View.GONE);
           recyclerView.setVisibility(View.VISIBLE);
           for (int i = 0; i < row.size(); i++) {
               String id = row.get(i).get(Data.KEY_CourseId);
               String poster = row.get(i).get(Data.KEY_Name);
               String title = row.get(i).get(Data.KEY_ALAMAT);
               data = new Data();
               data.setId(id);
               data.setName(poster);
               data.setAddress(title);
               itemList.add(data);
           }
           bookmarkAdapter.notifyDataSetChanged();
           bookmarkAdapter.setOnItemClickListener((view, position) -> {
               latitude = Double.parseDouble(row.get(position).get(Data.KEY_LATITUDE));
               longitude = Double.parseDouble(row.get(position).get(Data.KEY_LONGITUDE));
               String poster = row.get(position).get(Data.KEY_Name);
               String title = row.get(position).get(Data.KEY_ALAMAT);
               String jenjang = row.get(position).get(Data.KEY_JENJANG);
               String schoolid = row.get(position).get(Data.KEY_SCHOOLDETAIL);

               Intent intent = new Intent(FilterActivity.this, SearchingMAP.class);
               intent.putExtra("latitude", latitude);
               intent.putExtra("longitude", longitude);
               intent.putExtra("jenjang", jenjang);
               intent.putExtra("schoolid", schoolid);
               intent.putExtra("namasekolah", poster);
               intent.putExtra("alamat", title);
               setResult(RESULT_OK, intent);
               finish();
           });
       }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);

        return true;
    }
}
