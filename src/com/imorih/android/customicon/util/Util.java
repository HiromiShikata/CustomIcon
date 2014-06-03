package com.imorih.android.customicon.util;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

public class Util {
	private static final String CREATE_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
	
	private static void createShortCut(
			final Context context,
			final String packageName,
			final String title,
			final int iconResource) {
		final Intent appIntent =
				context.getPackageManager().getLaunchIntentForPackage(
						packageName);
		createShortCut(context, appIntent, title, iconResource);
		
	}
	
	public static void createShortCut(
			final Context context,
			final Intent appIntent,
			final String title,
			final int iconResource) {
		
		final Intent intent = new Intent(CREATE_SHORTCUT);
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, appIntent);
		
		final Parcelable icon = Intent.ShortcutIconResource.
				fromContext(context, iconResource);
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		context.sendBroadcast(intent);
		
	}
	
	private Util() {
	}
}
