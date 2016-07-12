package com.mobilemerit.javafiles;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.mobilemerit.database.DatabaseHandler;

public class ExportItemToExcel {
	private Activity mActivity;
	private List<ItemsDetails> listItem;
	private String fileLocation;
	private DatabaseHandler databaseHandler;
	public ExportItemToExcel(Activity a, String fileLocation ){
		this.mActivity = a;
		databaseHandler = new DatabaseHandler(a);
		this.listItem = databaseHandler.getItemsDetails();
		this.fileLocation = fileLocation;
		
	}
	public void exportExcel() {
		String Fnamexls = "Inventory_Items" + ".xls";
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));

		File directory = new File(fileLocation);
		directory.mkdirs();
		File file = new File(directory, Fnamexls);
/*
		Toast.makeText(mActivity.getBaseContext(), "" + file.toString(),
				Toast.LENGTH_SHORT).show();*/
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(file, wbSettings);
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			Label label0 = new Label(0, 0, "ITEM NAME");
			Label label1 = new Label(1, 0, "PRODUCT ID");
			Label label2 = new Label(2, 0, "BRAND");
			Label label3 = new Label(3, 0, "DATE");
			Label label4 = new Label(4, 0, "DETAILS");
			Label label5 = new Label(5, 0, "CATEGORY");
			Label label6 = new Label(6, 0, "COMPANY");
			Label label7 = new Label(7, 0, "PRICE");
			Label label8 = new Label(8, 0, "QUANTITY");
			Label label9 = new Label(9, 0, "LOCATION");
			Label label10 = new Label(10, 0, "IMAGE");
			try {
				sheet.addCell(label0);
				sheet.addCell(label1);
				sheet.addCell(label2);
				sheet.addCell(label3);
				sheet.addCell(label4);
				sheet.addCell(label5);
				sheet.addCell(label6);
				sheet.addCell(label7);
				sheet.addCell(label8);
				sheet.addCell(label9);
				sheet.addCell(label10);
				
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
			int row1 = 1;
			int row2 = 1;
			int row3 = 1;
			int row4 = 1;
			int row5 = 1;			
			int row6 = 1;
			int row7 = 1;
			int row8 = 1;
			int row9 = 1;
			int row10 = 1;
			int row11 = 1;			
			
			for (ItemsDetails item : listItem) {
				Label labelName = new Label(0, row1++, ""+item.getName());
				Label labelProductID = new Label(1, row2++, ""+item.getProductID());
				Label labelBrand = new Label(2, row3++, ""+item.getBrand());
				Label labelDate = new Label(3, row4++, ""+item.getDate());
				Label labelDetails = new Label(4, row5++, ""+item.getDetails());
				Label labelCategory = new Label(5, row6++, ""+databaseHandler.getCategoryFromID(""+item.getCatID()).getName());
				Label labelCompany = new Label(6, row7++, ""+item.getCompany());
				Label labelPrice = new Label(7, row8++, ""+item.getPrice());
				Label labelQuantity = new Label(8, row9++, ""+item.getQuantity());				
				Label labelLocation = new Label(9, row10++, ""+item.getLocation());
								
				try {
					sheet.addCell(labelName);
					sheet.addCell(labelProductID);
					sheet.addCell(labelBrand);
					sheet.addCell(labelDate);
					sheet.addCell(labelDetails);
					sheet.addCell(labelCategory);
					sheet.addCell(labelCompany);
					sheet.addCell(labelPrice);
					sheet.addCell(labelQuantity);
					sheet.addCell(labelLocation);
				} catch (WriteException e) {
					e.printStackTrace();
				}
				byte[] byteArray = item.getImage();
				if (byteArray != null) {
					WritableImage wImg = new WritableImage(10, row11++, 1, 1,
							byteArray);
					sheet.addImage(wImg);
				} else {
					row11++;
				}
			}
			workbook.write();
			excelFileSavedLocationDialog(file.toString());
			try {
				workbook.close();
			} catch (WriteException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
private void excelFileSavedLocationDialog(String path){
	 AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
	 	builder.setTitle("Excel file saved in");
	    builder.setMessage(path);
	    builder.setCancelable(true);
	    builder.setNegativeButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
	    AlertDialog dialog = builder.create();
	    dialog.show();
}

}
