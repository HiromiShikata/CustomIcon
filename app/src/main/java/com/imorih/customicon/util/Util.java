package com.imorih.customicon.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final String CREATE_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final Pattern PAT_ACTIVITY_LOG = Pattern.compile(
            ".*?ActivityManager.*act=([^ ]*) (?:dat=([^ ]*)* ){0,1}flg=([^ ]*) cmp=([^ /]*)/([^ ]*) ");
    private static final int ICON_SIZE = 256;
    private static final String URL_GOOGLE_PHOTOS = "com.google.android.apps.photos.content";

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


        Bitmap bitmapOrig = BitmapFactory.decodeResource(context.getResources(), iconResource);
        Bitmap bitmap = Bitmap.createScaledBitmap(bitmapOrig, ICON_SIZE, ICON_SIZE, true);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bitmap);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        context.sendBroadcast(intent);

    }

    public static void createShortCut(
            final Context context,
            final Intent appIntent,
            final String title,
            Uri uri) {

        final Intent intent = new Intent(CREATE_SHORTCUT);


        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, appIntent);
        Bitmap bitmapOrig = BitmapFactory.decodeFile(uri.toString());
        Bitmap bitmap = Bitmap.createScaledBitmap(bitmapOrig, ICON_SIZE, ICON_SIZE, true);

        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bitmap);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        context.sendBroadcast(intent);

    }

//    private static Bitmap getImage(Context context, Uri uri) {
//        if (isGooglePhoto(uri)) {
//            //http://mirukerapps.com/program/4_4-saf.html
//        }
//        Bitmap bitmapOrig = null;
//        try {
//            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
//            cursor.moveToFirst();
//            String str = cursor.getString(0);
//            cursor.close();
//
//            bitmapOrig =
//                    BitmapFactory.decodeStream(
//                            context.getContentResolver().openInputStream(uri)
//                    );
//
//
//			bitmapOrig = BitmapFactory.decodeStream(
//					context.openFileInput(uri.toString()));
//			bitmapOrig = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (bitmapOrig == null) {
//            Log.e("", "return !!!!!!");
//            return null;
//        }
//
//    }

    public static Intent getIntentFromActivityLog(
            String log) {
        Matcher mat = PAT_ACTIVITY_LOG.matcher(log);
        if (!mat.find()) {
            return null;
        }
        String act = mat.group(1);
        String dat = mat.group(2);
        String flg = mat.group(3);
        String cmpPackage = mat.group(4);
        String cmpClass = mat.group(5);

//		String act = "android.intent.action.VIEW";
//		String dat = "a";
//		String flg = mat.group(3);
//		String cmpPackage = mat.group(4);
//		String cmpClass = mat.group(5);

        Intent i = new Intent();
        i.setAction(act);
        String uriStr = dat;
        try {
            uriStr = URLEncoder.encode(dat, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        i.setData(Uri.parse(uriStr));
//		i.setFlags(Integer.parseInt(flg,16));
        i.setComponent(new ComponentName(cmpPackage, cmpClass));

        return i;
    }

    public static boolean isGooglePhoto(Uri uri) {
        return URL_GOOGLE_PHOTOS.equals(uri.getAuthority());
    }

    private Util() {
    }
}
