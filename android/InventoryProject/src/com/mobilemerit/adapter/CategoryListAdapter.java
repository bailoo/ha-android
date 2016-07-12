package com.mobilemerit.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobilemerit.database.DatabaseHandler;
import com.mobilemerit.javafiles.ItemsDetails;
import com.mycompany.myinventory.R;

public class CategoryListAdapter extends BaseAdapter{

	private Activity activity;
	private List<ItemsDetails> list;
	private LayoutInflater inflater;
	private DatabaseHandler handler;
	public CategoryListAdapter(Activity activity, List<ItemsDetails> list) {
		this.list = list;
		this.activity = activity;
		handler = new DatabaseHandler(activity);
		this.inflater = LayoutInflater.from(activity);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup root) {
			if(view == null){
				view = inflater.inflate(R.layout.category_details, root, false);
			}
			LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
			
			TextView count = (TextView) view.findViewById(R.id.text_count);
			TextView name = (TextView) view.findViewById(R.id.text_title);
			TextView quantity = (TextView) view.findViewById(R.id.text_quantity);
			TextView detail = (TextView) view.findViewById(R.id.text_detail);
			if(position == 0){
				mainLayout.setBackgroundColor(Color.parseColor("#ffffff"));
			}else if(position % 2 ==1){
				mainLayout.setBackgroundColor(Color.parseColor("#f1f1f1"));
			}else{
				mainLayout.setBackgroundColor(Color.parseColor("#ffffff"));
			}
			count.setText(""+(position+1));
			name.setText(list.get(position).getName());
			//quantity.setText(""+list.get(position).getId());		
			List<ItemsDetails> items  = handler.getItemsDetailsFromCategory(""+list.get(position).getId());
			if(items == null){
				quantity.setText("0");
			}else{
			quantity.setText(""+items.size());						
			}		
			detail.setOnClickListener(new TextClickHandler(list.get(position).getId()));
			
		return view;
	}
	class TextClickHandler implements OnClickListener{		
		int itemId;		
		OnTextClickListener listener;
		public TextClickHandler(int itemId) {
			
			this.itemId = itemId;
			listener=(OnTextClickListener)activity;
		}
		
		@Override
		public void onClick(View v) {			
				notifyParentActivity();				
		}
		
		private void notifyParentActivity(){			
			listener.onTextClick( itemId);
		}		
	}
	
	public interface OnTextClickListener{
		public void onTextClick(int itemId);
	}	
	
}
