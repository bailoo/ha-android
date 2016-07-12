package com.mycompany.myinventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mobilemerit.database.DatabaseHandler;
import com.mobilemerit.javafiles.ItemsDetails;

public class ItemDetailActivity extends ActionBarActivity{

	private String itemID;
	private TextView name;
	private TextView company;
	private TextView details;
	private TextView category;
	private TextView price;
	private TextView quantity;
	private TextView productID;
	private TextView brand;
	private TextView date;
	private TextView location;
	private ImageView image;
	private DatabaseHandler handler;
	private Toolbar mToolbar;
	public InterstitialAd interstitial;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_details_activity);
		
		mToolbar = (Toolbar) findViewById(R.id.toolbar); 
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Item Details");
     // ** Admob Code */
     		AdView adView = (AdView) this.findViewById(R.id.adView);
     		AdRequest request = new AdRequest.Builder().build();
     		adView.loadAd(request);
     		interstitial = new InterstitialAd(getBaseContext());
     		interstitial.setAdUnitId(getResources().getString(
     				R.string.interstitial_id));
     		interstitial.loadAd(request);
     		
		itemID = getIntent().getStringExtra("KEY_ID");	
		setResourceView();
		setResults();
	}
	
	private void setResourceView(){
		name = (TextView) findViewById(R.id.text_name);
		productID = (TextView) findViewById(R.id.text_product_id);
		brand = (TextView) findViewById(R.id.text_brand);
		date = (TextView) findViewById(R.id.text_date);
		company = (TextView) findViewById(R.id.text_company);
		category = (TextView) findViewById(R.id.text_category_name);
		details = (TextView) findViewById(R.id.text_detail);
		price = (TextView) findViewById(R.id.text_price);
		quantity = (TextView) findViewById(R.id.text_quantity);
		location = (TextView) findViewById(R.id.text_location);
		image = (ImageView) findViewById(R.id.img_item);
	}
	
	private void setResults(){
		handler = new DatabaseHandler(this);
		ItemsDetails itemsDetails = handler.getItemDetailsFromID(itemID);
		name.setText(""+itemsDetails.getName());
		productID.setText(""+itemsDetails.getProductID());
		brand.setText(""+itemsDetails.getBrand());
		date.setText(""+itemsDetails.getDate());
		company.setText(""+itemsDetails.getCompany());
		details.setText(""+itemsDetails.getDetails());
		category.setText(""+handler.getCategoryFromID(""+itemsDetails.getCatID()).getName());
		price.setText(""+itemsDetails.getPrice());
		quantity.setText(""+itemsDetails.getQuantity());
		location.setText(""+itemsDetails.getLocation());
		byte[] byteArrayImage = itemsDetails.getImage();
		if(byteArrayImage != null){
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayImage , 0, byteArrayImage .length);
		image.setImageBitmap(bitmap);
		}
	}
	@Override
	public void onBackPressed() {
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
		super.onBackPressed();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.view_screen_menu, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {				
		case R.id.action_home:
				Intent intent = new Intent(ItemDetailActivity.this,
					MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			break;
		case R.id.action_edit:
			Intent editIntent = new Intent(ItemDetailActivity.this,
					EditItemActivity.class);
			editIntent.putExtra("KEY_ID", "" + itemID);
			startActivityForResult(editIntent, 100);
			break;
		case R.id.action_delete:
			deleteDialog();
			finish();
			break;
		case R.id.action_search:
			startActivity(new Intent(ItemDetailActivity.this,
					SearchItemActivity.class));
			finish();
			break;			
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}	
	private void deleteDialog(){
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		        	handler.deleteItemsDetailsByID(itemID);
					finish();
		            break;

		        case DialogInterface.BUTTON_NEGATIVE:
		            //No button clicked
		            break;
		        }
		    }
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailActivity.this);
		builder.setMessage("Are you sure?"+"\n"+"You want to delete this item.").setPositiveButton("Yes", dialogClickListener)
		    .setNegativeButton("No", dialogClickListener).show();
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// check if the request code is same as what is passed here it is 2		
			if(requestCode == 100){
				setResults();
			}
	}
}
