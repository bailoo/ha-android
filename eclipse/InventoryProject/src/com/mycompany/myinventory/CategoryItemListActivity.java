package com.mycompany.myinventory;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mobilemerit.adapter.ItemListAdapter;
import com.mobilemerit.adapter.ItemListAdapter.OnTextClickListener;
import com.mobilemerit.database.DatabaseHandler;
import com.mobilemerit.javafiles.ItemsDetails;

public class CategoryItemListActivity extends ActionBarActivity implements OnTextClickListener{

	private ListView listView;
	private List<ItemsDetails> listItem;
	private ItemListAdapter adapter;
	private DatabaseHandler databaseHandler;
	private String categoryID;
	private Toolbar mToolbar;
	public InterstitialAd interstitial;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		
		 mToolbar = (Toolbar) findViewById(R.id.toolbar);		 
	     setSupportActionBar(mToolbar);
	     getSupportActionBar().setDisplayShowHomeEnabled(true);
	     setTitle("Category Items");
	  // ** Admob Code */
			AdView adView = (AdView) this.findViewById(R.id.adView);
			AdRequest request = new AdRequest.Builder().build();
			adView.loadAd(request);
			interstitial = new InterstitialAd(getBaseContext());
			interstitial.setAdUnitId(getResources().getString(
					R.string.interstitial_id));
			interstitial.loadAd(request);
		categoryID = getIntent().getStringExtra("KEY_ID");
		if(categoryID != null&& !categoryID.isEmpty()){
		listView = (ListView) findViewById(R.id.listview);		
		listItem = new ArrayList<ItemsDetails>();
		databaseHandler = new DatabaseHandler(this);
		listItem = databaseHandler.getItemsDetailsFromCategory(categoryID);
		adapter = new ItemListAdapter(this, listItem,"VIEW");
		listView.setAdapter(adapter);
		}
	}
	@Override
	public void onTextClick(int itemId, int viewID) {	
			Intent detailIntent = new Intent(CategoryItemListActivity.this, ItemDetailActivity.class);
			detailIntent.putExtra("KEY_ID", ""+itemId);
			startActivity(detailIntent);		
	}
	@Override
	public void onBackPressed() {
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
		super.onBackPressed();
	}
}
