package com.mycompany.myinventory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mobilemerit.adapter.CategorySpinnerAdapter;
import com.mobilemerit.database.DatabaseHandler;
import com.mobilemerit.javafiles.ItemsDetails;

public class EditItemActivity extends ActionBarActivity implements
		OnClickListener, OnItemSelectedListener {

	private EditText editTextItemTitle;
	private EditText editTextItemProductId;
	private EditText editTextItemBrand;
	private EditText editTextItemDate;
	private EditText editTextItemDetails;
	private EditText editTextItemCompany;
	private EditText editTextItemPrice;
	private EditText editTextItemQuality;
	private EditText editTextItemNotifyLimit;
	private EditText editTextItemLocation;
	private EditText editTextItemPic;
	private Spinner spinnerItemCategory;
	private ImageView itemImage;
	private Button btnSave;
	private DatabaseHandler databaseHandler;
	private CategorySpinnerAdapter adapter;
	private int categoryID;
	public static int RESULT_LOAD_IMAGE = 1;
	public static boolean gallery = false;
	public static String cameraImagePath;
	private byte[] imageArray = null;
	private List<ItemsDetails> countryList;
	private String itemID;
	private Toolbar mToolbar;
	private Calendar myCalendar;
	public InterstitialAd interstitial;
	private final CharSequence[] dialogItems = { "Take Photo From Gallery",
			"Take Photo From Camera", "Remove Photo"};
	AlertDialog.Builder builder;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_product);

		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		setTitle("Edit Item");
		// ** Admob Code */
				AdView adView = (AdView) this.findViewById(R.id.adView);
				AdRequest request = new AdRequest.Builder().build();
				adView.loadAd(request);
				interstitial = new InterstitialAd(getBaseContext());
				interstitial.setAdUnitId(getResources().getString(
						R.string.interstitial_id));
				interstitial.loadAd(request);
				
		itemID = getIntent().getStringExtra("KEY_ID");
		initilizeClassVariables();
		setResourcesView();
	}

	private void initilizeClassVariables() {
		builder = new AlertDialog.Builder(this);
		databaseHandler = new DatabaseHandler(this);
		myCalendar = Calendar.getInstance();
	}

	private void setResourcesView() {
		editTextItemTitle = (EditText) findViewById(R.id.edit_title);
		editTextItemProductId = (EditText) findViewById(R.id.edit_product_id);
		editTextItemBrand = (EditText) findViewById(R.id.edit_brand);
		editTextItemDate = (EditText) findViewById(R.id.edit_date);
		editTextItemDetails = (EditText) findViewById(R.id.edit_detail);
		editTextItemCompany = (EditText) findViewById(R.id.edit_company);
		editTextItemPrice = (EditText) findViewById(R.id.edit_price);
		editTextItemQuality = (EditText) findViewById(R.id.edit_quantity);
		editTextItemNotifyLimit = (EditText) findViewById(R.id.edit_mini_limit);
		editTextItemLocation = (EditText) findViewById(R.id.edit_location);
		spinnerItemCategory = (Spinner) findViewById(R.id.spinner_cat);
		editTextItemPic = (EditText) findViewById(R.id.edit_btn_ite_pic);
		itemImage = (ImageView) findViewById(R.id.img_item_pic);
		btnSave = (Button) findViewById(R.id.btn_save);
		countryList = databaseHandler.getCategories();
		adapter = new CategorySpinnerAdapter(this, countryList);
		spinnerItemCategory.setAdapter(adapter);
		spinnerItemCategory.setOnItemSelectedListener(this);
		btnSave.setOnClickListener(this);
		itemImage.setOnClickListener(this);
		editTextItemPic.setOnClickListener(this);
		editTextItemDate.setOnClickListener(this);
		setItemContents();
	}

	private void setItemContents() {
		if (itemID != null) {
			ItemsDetails details = databaseHandler.getItemDetailsFromID(itemID);
			editTextItemTitle.setText("" + details.getName());
			editTextItemProductId.setText("" + details.getProductID());
			editTextItemBrand.setText("" + details.getBrand());
			editTextItemDate.setText("" + details.getDate());
			editTextItemDetails.setText("" + details.getDetails());
			editTextItemCompany.setText("" + details.getCompany());
			editTextItemPrice.setText("" + details.getPrice());
			editTextItemQuality.setText("" + details.getQuantity());
			editTextItemNotifyLimit.setText("" + details.getNotifyLimit());
			editTextItemLocation.setText("" + details.getLocation());
			for (int i = 0; i < countryList.size(); i++) {
				if (details.getCatID() == countryList.get(i).getId()) {
					spinnerItemCategory.setSelection(i);
				}
			}
			byte[] byteArrayImage = details.getImage();
			imageArray = byteArrayImage;
			if (byteArrayImage != null) {
				editTextItemPic.setVisibility(ImageView.GONE);
				itemImage.setVisibility(ImageView.VISIBLE);
				Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayImage,
						0, byteArrayImage.length);
				itemImage.setImageBitmap(bitmap);
			}
		}
	}

	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			updateLabel();
		}

	};

	private void updateLabel() {
		String myFormat = "dd/MM/yyyy"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		editTextItemDate.setText(sdf.format(myCalendar.getTime()));

	}	
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		categoryID = countryList.get(position).getId();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_save:
			checkEntryValidation();
			break;
		case R.id.img_item_pic:
			showDialog();
			break;
		case R.id.edit_btn_ite_pic:
			showDialog();
			break;
		case R.id.edit_date:
			new DatePickerDialog(EditItemActivity.this, date,
					myCalendar.get(Calendar.YEAR),
					myCalendar.get(Calendar.MONTH),
					myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			break;
		default:
			break;
		}
	}

	private void checkEntryValidation() {
		String title = editTextItemTitle.getText().toString();
		String details = editTextItemDetails.getText().toString();
		String company = editTextItemCompany.getText().toString();
		String price = editTextItemPrice.getText().toString();
		String quantity = editTextItemQuality.getText().toString();
		String location = editTextItemLocation.getText().toString();
		String date = editTextItemDate.getText().toString();
		String productID = editTextItemProductId.getText().toString();
		String brand = editTextItemBrand.getText().toString();
		String notifyLimit = editTextItemNotifyLimit.getText().toString();
		if (price == null || price.isEmpty()) {
			price = "0";
		}
		if (quantity == null || quantity.isEmpty()) {
			quantity = "0";
		}
		if (notifyLimit == null || notifyLimit.isEmpty()) {
			notifyLimit = "0";
		}
		
		if (title != null && !title.isEmpty()) {
			boolean flag = databaseHandler.updateItems(
					itemID,
					new ItemsDetails(title, details, company, location,
							categoryID, Integer.parseInt(price), Integer
									.parseInt(quantity), imageArray, date,
							productID, brand,Integer.parseInt(notifyLimit)));
			if (flag) {
				Toast.makeText(getBaseContext(), "Item Updated!",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(getBaseContext(), "Item Not Updated!",
						Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(getApplicationContext(),
					"Please Enater Item's Name!", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("request", requestCode + "  result  " + resultCode + "  intent  "
				+ data);
		super.onActivityResult(requestCode, resultCode, data);
		if (gallery == true) {
			if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
					&& null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				imageArray = imageToByteArray(new File(picturePath));
				setImagePicture(picturePath);
			} else {

			}
		} else if (gallery == false) {
			File file = new File(cameraImagePath);
			if (file.exists()) {
				imageArray = imageToByteArray(file);
				setImagePicture(cameraImagePath);
			} else {

			}

		} else {
			this.finish();
			startActivity(new Intent(this, EditItemActivity.class));
		}
	}

	private byte[] imageToByteArray(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			// create FileInputStream which obtains input bytes from a file in a
			// file system
			// FileInputStream is meant for reading streams of raw bytes such as
			// image data. For reading streams of characters, consider using
			// FileReader.

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];

			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				// Writes to this byte array output stream
				bos.write(buf, 0, readNum);
				System.out.println("read " + readNum + " bytes,");
			}
			byte[] bytes = bos.toByteArray();
			return bytes;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void setImagePicture(String photoPath) {
		editTextItemPic.setVisibility(ImageView.GONE);
		itemImage.setVisibility(ImageView.VISIBLE);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
		itemImage.setImageBitmap(bitmap);
	}
	@Override
	public void onBackPressed() {
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
		super.onBackPressed();
	}
	
	public void showDialog() {

		builder.setTitle("Choose Action!");
		builder.setItems(dialogItems, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				// Do something with the selection
				if (item == 0) {
					openGallery();
				} else if(item == 1) {
					openCamera();
				}else{
					imageArray = null;
					editTextItemPic.setVisibility(ImageView.VISIBLE);
					itemImage.setVisibility(ImageView.GONE);				
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}	
	
	public void openGallery() {
		EditItemActivity.gallery = true;
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, RESULT_LOAD_IMAGE);
	}

	public void openCamera() {
		EditItemActivity.gallery = false;

		File image = new File(appFolderCheckandCreate(), "img" + getTimeStamp()
				+ ".jpg");
		Uri uriSavedImage = Uri.fromFile(image);
		EditItemActivity.cameraImagePath = image.getAbsolutePath();
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
		i.putExtra("return-data", true);
		startActivityForResult(i, RESULT_LOAD_IMAGE);
	}

	private String appFolderCheckandCreate() {

		String appFolderPath = "";
		File externalStorage = Environment.getExternalStorageDirectory();

		if (externalStorage.canWrite()) {
			appFolderPath = externalStorage.getAbsolutePath() + "/InventoryApp";
			File dir = new File(appFolderPath);

			if (!dir.exists()) {
				dir.mkdirs();
			}

		} else {

		}

		return appFolderPath;
	}

	@SuppressLint("SimpleDateFormat") private String getTimeStamp() {

		final long timestamp = new Date().getTime();

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);

		final String timeString = new SimpleDateFormat("HH_mm_ss_SSS")
				.format(cal.getTime());

		return timeString;
	}
}
