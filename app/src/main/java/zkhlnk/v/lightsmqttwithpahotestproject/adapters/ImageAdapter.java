package zkhlnk.v.lightsmqttwithpahotestproject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import zkhlnk.v.lightsmqttwithpahotestproject.R;

/**
 * Created by Valerie on 26.01.2015.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] mThumbIds = {
            R.drawable.lightbulb_off, R.drawable.lightbulb_off,
            R.drawable.lightbulb_off, R.drawable.lightbulb_off,
            R.drawable.lightbulb_off, R.drawable.lightbulb_off,
            R.drawable.lightbulb_off, R.drawable.lightbulb_off,
            R.drawable.lightbulb_off, R.drawable.lightbulb_off,
            R.drawable.lightbulb_off, R.drawable.lightbulb_off
    };

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public ImageAdapter(Context c, int number, int recourse) {
        mThumbIds = new Integer[number];
        for (int i = 0; i < mThumbIds.length; i++) {
            setResource(i, recourse);
        }
        mContext = c;
    }

    public ImageAdapter(Context c, int number, int recourseOn, int recourseOff, boolean[] on) {
        mThumbIds = new Integer[number];
        for (int i = 0; i < mThumbIds.length; i++) {
            if (on[i]) {
                setResource(i, recourseOn);
            } else {
                setResource(i, recourseOff);
            }
        }
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 90));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    public void setResource(int position, int resource) {
        mThumbIds[position] = resource;
    }
}

