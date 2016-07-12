package com.mobilemerit.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.mobilemerit.javafiles.HomeItem;
import com.mycompany.myinventory.R;

public class GridAdapter extends BaseAdapter{

	private List<HomeItem> list;
	private LayoutInflater inflater;
	public GridAdapter(Activity activity, List<HomeItem> list) {
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
			view = inflater.inflate(R.layout.gridview_item, root, false);
		}
		TextView titleTextView = (TextView)view.findViewById(R.id.txt_title);
		PorterShapeImageView imageView = (PorterShapeImageView) view.findViewById(R.id.img_icon);
		titleTextView.setText(list.get(position).getTitle());
		imageView.setImageResource(list.get(position).getImage());
		return view;
	}

}
