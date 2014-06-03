package com.imorih.android.customicon.fragment;

import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.imorih.android.customicon.R;
import com.imorih.android.customicon.adapter.AppAdapter;
import com.imorih.android.customicon.model.App;

public class AppListFragment extends Fragment
		implements
		OnItemClickListener {
	public static final int FLAG_APPLICATION_INFO = 0;

	public interface OnSelectApp {
		public void onSelectApp(String packageName);
	}

	private ListView mListView;
	private AppAdapter mAdapter;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater
				.inflate(R.layout.fragment_app_list, container, false);

		mListView = (ListView) rootView.findViewById(R.id.fragment_app_list_list);
		mAdapter = new AppAdapter(getActivity());

		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setAppList();
	}

	private void setAppList() {
		PackageManager pm = getActivity().getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> appInfo = pm.queryIntentActivities(intent, 0);
		if (appInfo == null) {
			return;
		}
		for (ResolveInfo ri : appInfo) {
			App app = new App(ri.activityInfo.applicationInfo, pm);
			mAdapter.add(app);

		}

		mAdapter.notifyDataSetChanged();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		App app = mAdapter.getItem(position);
		if (!(getActivity() instanceof OnSelectApp)) {
			return;
		}
		((OnSelectApp) getActivity()).onSelectApp(app.getPackageName());

	}

}
