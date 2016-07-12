package com.mobilemerit.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilemerit.javafiles.SettingPojo;
import com.mycompany.myinventory.R;

public class SettingAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<SettingPojo> list; 
	private Activity activity;
	public SettingAdapter(Activity activity, List<SettingPojo> list) {
		this.activity = activity;
		this.list = list;
		this.inflater =LayoutInflater.from(activity);  
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
	public View getView(int position, View view, ViewGroup group) {
		if(view == null){
			view = inflater.inflate(R.layout.setting_item , group, false);
		}
		ImageView icon = (ImageView)view.findViewById(R.id.img_icon);
		TextView title = (TextView)view.findViewById(R.id.txt_name);
		Typeface type = Typeface.createFromAsset(activity.getAssets(),"font/roboto_regular.ttf"); 
		title.setTypeface(type);
		icon.setBackgroundResource(list.get(position).getResource());
		title.setText(list.get(position).getName()); 
		return view;
	}

}
