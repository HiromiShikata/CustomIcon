package com.imorih.android.customicon.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView.SelectionBoundsAdjuster;

import com.imorih.android.customicon.R;
import com.imorih.android.customicon.fragment.AppListFragment;
import com.imorih.android.customicon.fragment.SelectIconFragment;
import com.imorih.android.customicon.util.Util;

public class SelectIconActivity extends ActionBarActivity
		implements
		SelectIconFragment.OnSelectIcon,
		SelectIconFragment.OnPickGalleryImage{
	
	private static final int REQUEST_ACTION_PICK = 1;
	
	private static final String ARGKEY_SHORTCUT_INTENT = "shortcutIntent";
	private static final String ARGKEY_SHORTCUT_LABEL = "shortcutLabel";
	
	private Intent mAppIntent;
	private String mLabel;
	
	public static void startActivity(
			final Context context,
			final String packageName) {
		final PackageManager pm = context.getPackageManager();
		final Intent appIntent = pm.getLaunchIntentForPackage(packageName);
		
		String label = "";
		try {
			final ApplicationInfo ai = pm.getApplicationInfo(packageName,
					AppListFragment.FLAG_APPLICATION_INFO);
			label = ai.loadLabel(pm).toString();
		} catch (final NameNotFoundException e) {
		}
		
		startActivity(context, appIntent, label);
		
	}
	public static void startActivityFromActivityLog(
			final Context context,
			String log) {

		Intent i = Util.getIntentFromActivityLog(log);
		final PackageManager pm = context.getPackageManager();
		String packageName = i.getComponent().getPackageName();
		
		String label = "";
		try {
			final ApplicationInfo ai = pm.getApplicationInfo(packageName,
					AppListFragment.FLAG_APPLICATION_INFO);
			label = ai.loadLabel(pm).toString();
		} catch (final NameNotFoundException e) {
		}
		
		startActivity(context, i, label);

		i.getComponent().getPackageName();
		
	}
	
	public static void startActivity(
			final Context context,
			final Intent appIntent,
			final String label) {
		final Intent intent = new Intent(context, SelectIconActivity.class);
		intent.putExtra(ARGKEY_SHORTCUT_INTENT, appIntent);
		intent.putExtra(ARGKEY_SHORTCUT_LABEL, label);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_icon);
		mAppIntent = getIntent().getParcelableExtra(ARGKEY_SHORTCUT_INTENT);
		mLabel = getIntent().getStringExtra(ARGKEY_SHORTCUT_LABEL);
	}
	
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onSelectIcon(final int resourceId) {
		createShortCut(resourceId);
		moveTaskToBack(true);
		finish();
	}
	
	private void createShortCut(
			final int iconResource) {
		if (mAppIntent == null) {
			return;
		}
		Util.createShortCut(this, mAppIntent, mLabel, iconResource);
	}
	@Override
	public void onClickGalleryBtn() {
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.setType("image/*");
		startActivityForResult(
				Intent.createChooser(i, "画像選択"),
				REQUEST_ACTION_PICK);
		
	}
    protected void onActivityResult(
    		int requestCode, int resultCode, Intent data) {

    	if(requestCode == REQUEST_ACTION_PICK){

    		Util.createShortCut(this, mAppIntent, mLabel, data.getData());
    		
    	}
    }
	
}
