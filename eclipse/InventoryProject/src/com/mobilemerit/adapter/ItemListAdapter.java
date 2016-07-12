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

import com.mobilemerit.javafiles.ItemsDetails;
import com.mycompany.myinventory.R;

public class ItemListAdapter extends BaseAdapter{

	private Activity activity;
	private List<ItemsDetails> list;
	private LayoutInflater inflater;
	private String ACTION;
	public ItemListAdapter(Activity activity, List<ItemsDetails> list, String action) {
		this.list = list;
		this.ACTION =action;
		this.activity = activity;
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
				view = inflater.inflate(R.layout.listview_item, root, false);
			}
			LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
			
			TextView count = (TextView) view.findViewById(R.id.text_count);
			TextView name = (TextView) view.findViewById(R.id.text_title);
			TextView quantity = (TextView) view.findViewById(R.id.text_quantity);
			TextView price = (TextView) view.findViewById(R.id.text_price);
			TextView value = (TextView) view.findViewById(R.id.text_value);
			TextView detail = (TextView) view.findViewById(R.id.text_detail);
			TextView edit = (TextView) view.findViewById(R.id.text_edit);
			if(position == 0){
				mainLayout.setBackgroundColor(Color.parseColor("#ffffff"));
			}else if(position % 2 ==1){
				mainLayout.setBackgroundColor(Color.parseColor("#f1f1f1"));
			}else{
				mainLayout.setBackgroundColor(Color.parseColor("#ffffff"));
			}
			
			count.setText(""+(position+1));
			name.setText(list.get(position).getName());
			quantity.setText(""+list.get(position).getQuantity());
			price.setText(""+list.get(position).getPrice());
			value.setText(""+(list.get(position).getQuantity()*list.get(position).getPrice()));
			if(ACTION.equals("VIEW")){
				detail.setVisibility(TextView.VISIBLE);
				detail.setOnClickListener(new TextClickHandler(list.get(position).getId(), R.id.text_detail));
			}else if(ACTION.equals("EDIT")){
				edit.setVisibility(TextView.VISIBLE);
				edit.setOnClickListener(new TextClickHandler(list.get(position).getId(), R.id.text_edit));
			}
			
		return view;
	}
	class TextClickHandler implements OnClickListener{		
		int itemId;
		int viewID;
		OnTextClickListener listener;
		public TextClickHandler(int itemId, int viewID) {
			this.viewID = viewID;
			this.itemId = itemId;
			listener=(OnTextClickListener)activity;
		}
		
		@Override
		public void onClick(View v) {			
				notifyParentActivity();				
		}
		
		private void notifyParentActivity(){			
			listener.onTextClick( itemId, viewID);
		}		
	}
	
	public interface OnTextClickListener{
		public void onTextClick(int itemId, int viewID);
	}	
	
}
