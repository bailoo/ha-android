package com.mobilemerit.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobilemerit.javafiles.ItemsDetails;
import com.mycompany.myinventory.R;

public class CategorySpinnerAdapter extends BaseAdapter{

	private List<ItemsDetails> list;
	private LayoutInflater  inflater;
	public CategorySpinnerAdapter(Activity activity, List<ItemsDetails> list) {
		this.list = list;
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
			view = inflater.inflate(R.layout.spinner_item, root, false);
		}
		TextView categoryName = (TextView) view.findViewById(R.id.text_category_name);
		categoryName.setText(list.get(position).getName());
		return view;
	}

}
