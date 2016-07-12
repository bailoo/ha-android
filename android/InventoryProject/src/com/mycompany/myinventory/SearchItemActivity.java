package com.mycompany.myinventory;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mobilemerit.adapter.ItemListAdapter;
import com.mobilemerit.adapter.ItemListAdapter.OnTextClickListener;
import com.mobilemerit.database.DatabaseHandler;
import com.mobilemerit.javafiles.ItemsDetails;

public class SearchItemActivity extends ActionBarActivity implements
		OnTextClickListener, OnClickListener {

	private LinearLayout searchListLayout;
	private LinearLayout searchLayout;
	private boolean searchFlag;
	private EditText  searchText;
	private Button btnSearch;
	private ListView listView;
	private List<ItemsDetails> listItem;
	private ItemListAdapter adapter;
	private DatabaseHandler databaseHandler;
	private Toolbar mToolbar;
	public InterstitialAd interstitial;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		setTitle("Search");
		// ** Admob Code */
		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest request = new AdRequest.Builder().build();
		adView.loadAd(request);
		interstitial = new InterstitialAd(getBaseContext());
		interstitial.setAdUnitId(getResources().getString(
				R.string.interstitial_id));
		interstitial.loadAd(request);
		searchListLayout = (LinearLayout) findViewById(R.id.search_list_layout);
		searchLayout = (LinearLayout) findViewById(R.id.search_layout);
		searchText = (EditText) findViewById(R.id.edit_search_text);
		btnSearch = (Button) findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.listview);
		listItem = new ArrayList<ItemsDetails>();
		databaseHandler = new DatabaseHandler(this);		
		listItem = databaseHandler.getItemsDetails();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			Intent addIntent = new Intent(SearchItemActivity.this,
					AddProductActivity.class);
			startActivity(addIntent);
			finish();
			break;
		case R.id.action_edit:
			Intent editItemIntent = new Intent(SearchItemActivity.this,
					ItemListActivity.class);
			editItemIntent.setAction("EDIT");
			startActivity(editItemIntent);
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setSearchNews(String resultText) {
		List<ItemsDetails> searchItem = new ArrayList<ItemsDetails>();
		if (listItem != null) {
			for (ItemsDetails search : listItem) {
				if ((search.getName().toLowerCase()).indexOf(resultText
						.toLowerCase()) != -1) {
					searchItem.add(search);
				}
			}
		}
		if (searchItem.size()>0) {
			searchFlag = true;
			searchLayout.setVisibility(LinearLayout.GONE);
			searchListLayout.setVisibility(LinearLayout.VISIBLE);
			adapter = new ItemListAdapter(this, searchItem, "VIEW");
			listView.setAdapter(adapter);	
		}else{
			Toast.makeText(getBaseContext(), "No Item Found!", Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public void onTextClick(int itemId, int viewID) {
		Intent detailIntent = new Intent(SearchItemActivity.this,
				ItemDetailActivity.class);
		detailIntent.putExtra("KEY_ID", "" + itemId);
		startActivity(detailIntent);
	}

	@Override
	public void onBackPressed() {
		if (searchFlag) {
			searchFlag = false;
			searchLayout.setVisibility(LinearLayout.VISIBLE);
			searchListLayout.setVisibility(LinearLayout.GONE);
		}else{
			if (interstitial.isLoaded()) {
				interstitial.show();
			}
			super.onBackPressed();
		}	
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:
				String query = searchText.getText().toString();
				if(query != null && ! query.isEmpty()){					
					setSearchNews(query);
				}else{
					Toast.makeText(getBaseContext(), "Please enter your query", Toast.LENGTH_SHORT).show();
				}
			break;
		default:
			break;
		}		
	}	
}
