package com.imorih.android.customicon.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.imorih.android.customicon.R;
import com.imorih.android.customicon.fragment.AppListFragment;

public class MainActivity extends ActionBarActivity
		implements
		AppListFragment.OnSelectApp {
	
	public static final int REQUEST_PICK_SHORTCUT = 0x100;
	public static final int REQUEST_CREATE_SHORTCUT = 0x200;
	
	private static final int MENU_ID_SHORTCUT = Menu.FIRST + 1;
	private static final int MENU_ID_FROMLOG = Menu.FIRST + 2;
	
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.container, new AppListFragment())
					.commit();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(
				Menu.NONE,
				MENU_ID_SHORTCUT,
				Menu.NONE,
				"Shortcut")
//				.setIcon(R.drawable.ic_action_labels)
				;
		menu.add(
				Menu.NONE,
				MENU_ID_FROMLOG,
				Menu.NONE,
				"FromLog")
//				.setIcon(R.drawable.ic_action_labels)
				;
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final int id = item.getItemId();
		switch (id) {
		case R.id.action_settings:
			return true;

		case MENU_ID_SHORTCUT:
			showPickShortcut();
			return true;

		case MENU_ID_FROMLOG:
			showPickLog();
			return true;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void showPickShortcut() {
		final Intent intent = new Intent(Intent.ACTION_PICK_ACTIVITY);
		intent.putExtra(Intent.EXTRA_INTENT, new Intent(
				Intent.ACTION_CREATE_SHORTCUT));
		intent.putExtra(Intent.EXTRA_TITLE, "Shortcuts");
		startActivityForResult(intent, REQUEST_PICK_SHORTCUT);
	}
	private void showPickLog(){
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_info)
			.setTitle("")
			.setView(editText)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					SelectIconActivity.startActivityFromActivityLog(
							MainActivity.this,
							editText.getText().toString());
				}
			})
			.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			})
			.show();
	}
	
	@Override
	public void onSelectApp(final String packageName) {
		showSelectIcon(packageName);
	}
	
	@Override
	protected void onActivityResult(
			final int requestCode, final int resultCode, final Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		final int fragmentReqCode = requestCode & 0xffff;
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (fragmentReqCode) {
		
			case REQUEST_PICK_SHORTCUT:
				startActivityForResult(data, REQUEST_CREATE_SHORTCUT);
				break;
			
			case REQUEST_CREATE_SHORTCUT:
				completeAddShortcut(data);
				break;
			
			default:
				break;
		}
		
	}
	
	private void completeAddShortcut(final Intent data) {
		final String name = data.getStringExtra(Intent.EXTRA_SHORTCUT_NAME);
		final Intent intent = data.getParcelableExtra(Intent.EXTRA_SHORTCUT_INTENT);
		SelectIconActivity.startActivity(this, intent, name);
		
	}
	
	private void showSelectIcon(
			final String packageName) {
		SelectIconActivity.startActivity(this, packageName);
	}

}
