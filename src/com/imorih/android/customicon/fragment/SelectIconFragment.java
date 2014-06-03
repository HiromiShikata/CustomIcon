package com.imorih.android.customicon.fragment;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.imorih.android.customicon.R;
import com.imorih.android.customicon.adapter.IconAdapter;

public class SelectIconFragment extends Fragment
		implements
		OnItemClickListener {

	public interface OnSelectIcon {
		void onSelectIcon(int resourceId);
	}

	private static final String ICON_PREFIX = "icon_";
	private GridView mGridView;
	private IconAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater
				.inflate(R.layout.fragment_select_icon, container, false);

		mGridView = (GridView) rootView.findViewById(R.id.select_icon_list);
		mAdapter = new IconAdapter(getActivity());

		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(this);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setIconList();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (!(getActivity() instanceof OnSelectIcon)) {
			return;
		}
		int resourceId = mAdapter.getItem(position);
		((OnSelectIcon) getActivity()).onSelectIcon(resourceId);

	}

	private void setIconList() {
		Field[] fields = R.drawable.class.getFields();
		for (Field field : fields) {
			String name = field.getName();
			if (!name.startsWith(ICON_PREFIX)) {
				continue;
			}
			try {
				int resId = (Integer) field.get(name);
				mAdapter.add(resId);
			} catch (IllegalAccessException | IllegalArgumentException e) {
			}
		}
		mAdapter.notifyDataSetChanged();

	}

}
