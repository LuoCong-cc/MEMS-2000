package mems.jiaochuan.com.mems_2000.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import mems.jiaochuan.com.mems_2000.R;
import mems.jiaochuan.com.mems_2000.config.Config;

public class HorizontalScrollViewAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private String[] mStations;

    public HorizontalScrollViewAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mStations = Config.stations;
    }

    public int getCount() {
        return mStations.length;
    }

    public Object getItem(int position) {
        return mStations[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.horizontalscrollview_item, parent, false);
            viewHolder.mImg = (ImageView) convertView
                    .findViewById(R.id.id_index_gallery_item_image);
            viewHolder.mText = (TextView) convertView
                    .findViewById(R.id.id_index_gallery_item_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if ("春熙路站".equals(mStations[position]) || "省体育馆站".equals(mStations[position])) {
            viewHolder.mImg.setImageResource(Config.trasferStation);
        } else {
            viewHolder.mImg.setImageResource(Config.commonDatas.get(new Random().nextInt(4)));
        }
        viewHolder.mText.setText(mStations[position]);

        return convertView;
    }

    private class ViewHolder {
        ImageView mImg;
        TextView mText;
    }
}
