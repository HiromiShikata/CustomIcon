package com.imorih.android.customicon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.imorih.android.customicon.R;

public class IconAdapter extends ArrayAdapter<Integer> {
	private LayoutInflater mInflator;

	public IconAdapter(Context context) {
		super(context, R.layout.row_icon);
		mInflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		ViewHolder holder = null;
		if (view == null) {
			view = mInflator.inflate(R.layout.row_app, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		int resId = getItem(position);
		holder.mIcon.setImageResource(resId);

		return view;

	}

	private class ViewHolder {
		ImageView mIcon;

		public ViewHolder(
				View root) {
			mIcon = (ImageView) root.findViewById(R.id.row_app_icon);

		}

	}

}
