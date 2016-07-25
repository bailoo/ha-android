package com.mycompany.myinventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mobilemerit.adapter.CategoryListAdapter;
import com.mobilemerit.adapter.CategoryListAdapter.OnTextClickListener;
import com.mobilemerit.database.DatabaseHandler;
import com.mobilemerit.javafiles.ItemsDetails;

public class CategoryReportActivity extends ActionBarActivity implements
        OnTextClickListener, OnClickListener {

    private Button btnCategory, btnQuantity;
    private ListView listView;
    private List<ItemsDetails> listItem;
    private CategoryListAdapter adapter;
    private DatabaseHandler databaseHandler;
    private Toolbar mToolbar;
    public InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list_report);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Reports");

        btnCategory = (Button) findViewById(R.id.btn_category);
        btnQuantity = (Button) findViewById(R.id.btn_quantity);
        btnCategory.setOnClickListener(this);
        btnQuantity.setOnClickListener(this);
        btnCategory.setBackgroundColor(getResources().getColor(
                R.color.table_title_color));
        btnQuantity.setBackgroundColor(getResources().getColor(
                R.color.colorPrimary));
        listView = (ListView) findViewById(R.id.listview);

        listItem = new ArrayList<ItemsDetails>();
        databaseHandler = new DatabaseHandler(this);
        listItem.addAll(databaseHandler.getCategories());
        adapter = new CategoryListAdapter(this, listItem);
        listView.setAdapter(adapter);

    }

    @Override
    public void onTextClick(int itemId) {
        Intent intent = new Intent(CategoryReportActivity.this,
                CategoryItemListActivity.class);
        intent.putExtra("KEY_ID", "" + itemId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.report_screen_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(CategoryReportActivity.this,
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.action_add:
                Intent addIntent = new Intent(CategoryReportActivity.this,
                        AddProductActivity.class);
                startActivity(addIntent);
                finish();
                break;
            case R.id.action_edit:
                Intent editItemIntent = new Intent(CategoryReportActivity.this,
                        ItemListActivity.class);
                editItemIntent.setAction("EDIT");
                startActivity(editItemIntent);
                finish();
                break;
            case R.id.action_search:
                startActivity(new Intent(CategoryReportActivity.this,
                        SearchItemActivity.class));
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_category:
                btnCategory.setBackgroundColor(getResources().getColor(
                        R.color.table_title_color));
                btnQuantity.setBackgroundColor(getResources().getColor(
                        R.color.colorPrimary));
                sortListByName();
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_quantity:
                btnCategory.setBackgroundColor(getResources().getColor(
                        R.color.colorPrimary));
                btnQuantity.setBackgroundColor(getResources().getColor(
                        R.color.table_title_color));
                sortListByItemQuantity();
                adapter.notifyDataSetChanged();
                break;

            default:
                break;
        }

    }

    private void sortListByName() {
        Collections.sort(listItem, new Comparator<ItemsDetails>() {
            @Override
            public int compare(ItemsDetails obj1, ItemsDetails obj2) {
                return obj1.getName().compareToIgnoreCase(obj2.getName());
            }
        });
    }

    private void sortListByItemQuantity() {
        Collections.sort(listItem, new Comparator<ItemsDetails>() {
            @Override
            public int compare(ItemsDetails obj1, ItemsDetails obj2) {
                return Integer.compare(
                        databaseHandler.getItemsDetailsFromCategory(
                                "" + obj2.getId()).size(),
                        databaseHandler.getItemsDetailsFromCategory(
                                "" + obj1.getId()).size());

            }
        });
    }
}
