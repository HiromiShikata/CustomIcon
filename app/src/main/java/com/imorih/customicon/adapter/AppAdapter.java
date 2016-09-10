package com.imorih.customicon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imorih.customicon.R;
import com.imorih.customicon.model.App;

import java.util.Comparator;


public class AppAdapter extends ArrayAdapter<App> {
    private static final Comparator<App> COMPARATOR_APPNAME = new Comparator<App>() {
        @Override
        public int compare(App lhs, App rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    };

    private LayoutInflater mInflator;

    public AppAdapter(Context context) {
        super(context, R.layout.row_app);
        mInflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void sortByName() {
        super.sort(COMPARATOR_APPNAME);
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
