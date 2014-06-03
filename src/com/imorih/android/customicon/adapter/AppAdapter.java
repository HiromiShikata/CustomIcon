package com.imorih.android.customicon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imorih.android.customicon.R;
import com.imorih.android.customicon.model.App;

public class AppAdapter extends ArrayAdapter<App> {
	private LayoutInflater mInflator;

	public AppAdapter(Context context) {
		super(context, R.layout.row_app);
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
		App app = getItem(position);
		holder.mTitle.setText(app.getName());
		holder.mIcon.setImageDrawable(app.getIcon());

		return view;

	}

	private class ViewHolder {
		TextView mTitle;
		ImageView mIcon;

		public ViewHolder(
				View root) {
			mTitle = (TextView) root.findViewById(R.id.row_app_title);
			mIcon = (ImageView) root.findViewById(R.id.row_app_icon);

		}

		public TextView getmTitle() {
			return mTitle;
		}

		public ImageView getmIcon() {
			return mIcon;
		}

	}

}
