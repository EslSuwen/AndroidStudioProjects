package com.example.cameratest.adapter;

/**
 * Created by lzh on 2019/3/29.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cameratest.R;

public class HorizontalListViewAdapter extends BaseAdapter {
    private int[] mIconIDs;
    private String[] mTitles;
    private Context mContext;
    private LayoutInflater mInflater;
    private int selectIndex = -1;

    public HorizontalListViewAdapter(Context context, String[] titles, int[] ids) {
        this.mContext = context;
        this.mIconIDs = ids;
        this.mTitles = titles;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
    }

    public HorizontalListViewAdapter(Context context, String[] titles) {
        this.mContext = context;
        this.mTitles = titles;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (mIconIDs == null)
            return mTitles.length;
        return mIconIDs.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.horizontal_list_item, null);
            holder.mImage = (ImageView) convertView.findViewById(R.id.img_list_item);
            holder.mTitle = (TextView) convertView.findViewById(R.id.text_list_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == selectIndex) {
            convertView.setSelected(true);
            holder.mImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.badge_bg_red));
        } else {
            convertView.setSelected(false);
            holder.mImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.badge_bg));
        }

        holder.mTitle.setText(mTitles[position]);
        //holder.mImage.setImageResource(R.drawable.snapshot_disabled);

        return convertView;
    }

    private static class ViewHolder {
        private TextView mTitle;
        private ImageView mImage;
    }

    public void setSelectIndex(int i) {
        selectIndex = i;
    }
}