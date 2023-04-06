package com.woleapp.adapters;

import android.content.Context;
import android.content.res.Resources;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.woleapp.R;
import com.woleapp.util.Utilities;

import java.util.List;


/**
 * Created by ideallinux16 on 3/24/17.
 */

public class ThemeAdapter extends BaseAdapter
{
    Context mContext;
    List<com.woleapp.model.Color> items;


    public ThemeAdapter(Context mContext, List<com.woleapp.model.Color> items)
    {
        this.mContext = mContext;
        this.items = items;

    }


    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder vholder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_theme, parent, false);
            vholder = new ViewHolder();

            vholder.tvItemName =  convertView.findViewById(R.id.tvColor);
            vholder.cardView =  convertView.findViewById(R.id.cardView);
            vholder.linearCV = convertView.findViewById(R.id.linearCV);

            convertView.setTag(vholder);
        }
        else
        {
            vholder = (ViewHolder) convertView.getTag();
        }


        vholder.tvItemName.setText(items.get(position).getName());
        String color_code = items.get(position).getColor_code();
        vholder.cardView.setCardBackgroundColor(Color.parseColor(color_code));//res.getColor(color)

        return convertView;
    }

    private class ViewHolder
    {
        TextView tvItemName;
        CardView cardView;
        LinearLayout linearCV;
    }
}
