package com.woleapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/*import com.soldship.driver.R;
import com.soldship.driver.dto.GeographicalAreaDTO;
import com.soldship.driver.fragments.ProfileFragment;
import com.soldship.driver.utilities.AppSession;*/
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.woleapp.R;
import com.woleapp.model.Color;

import java.util.List;
import java.util.Map;

//import com.taxiapp_customer.R;
//import com.taxiapp_customer.utils.AppSession;

/**
 * Created by deepak on 6/10/16.
 */
public class CustomSpinner extends ArrayAdapter<Color> {

    Context mContext;
    List<Map<String, String>> list;
    List<Color> list1;
    int layoutResourceId;
    View row = null;

    int textColor;
    //String selectedId="";
    int selectedPos = -1,selectedId=0;
    SearchableSpinner searchableSpinner;
    int type = 0;
//    public CustomSpinner(Context mContext, int layoutResourceId,
//                               List<Map<String, String>> list, int textColor) {
//        super(mContext, layoutResourceId, list);
//        this.mContext = mContext;
//        this.list = list;
//        this.layoutResourceId = layoutResourceId;
//        appSession = new AppSession(mContext);
//        this.textColor = textColor;
//    }



    public CustomSpinner(Context mContext, int layoutResourceId,
                         List<Color> list)
    {//, int textColor
        super(mContext, layoutResourceId, list);
        this.mContext = mContext;
        this.list1 = list;
        this.layoutResourceId = layoutResourceId;

        //this.textColor = textColor;

    }

    public CustomSpinner(Context mContext, int layoutResourceId,
                         List<Color> list, int textColor, SearchableSpinner searchableSpinner) {
        super(mContext, layoutResourceId, list);
        this.mContext = mContext;
        this.list1 = list;
        this.layoutResourceId = layoutResourceId;
        this.textColor = textColor;
        this.searchableSpinner = searchableSpinner;
    }

    public CustomSpinner(Context mContext, int layoutResourceId,
                         List<Color> list, int textColor, int type)
    {
        super(mContext, layoutResourceId, list);
        this.mContext = mContext;
        this.list1 = list;
        this.layoutResourceId = layoutResourceId;
        this.textColor = textColor;
        this.type = type;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomDropDownView(position, convertView, parent);
    }

    /*public int getId(int pos)
    {
        return list1.get(pos).getId();
    }*/

    public void clearList()
    {
        list1.clear();
        //notifyDataSetChanged();
    }


    public String getName(int pos)
    {
        return list1.get(pos).getName();
    }
    @Override
    public int getCount() {

        try {
            return (list1.size());
        } catch (Exception e1) {
            e1.printStackTrace();

            return 0;
        } catch (Error e1) {
            e1.printStackTrace();

            return 0;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    public View getCustomView(int position, View convertView, ViewGroup parent) {
        try {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_view_color, parent, false);
            TextView label =  row.findViewById(R.id.text2);
//            if (appSession.getLanguage().equalsIgnoreCase("ar"))
//                label.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//            else label.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

            /*if (position == 0) {
                label.setTextColor(mContext.getResources().getColor(R.color.text_gray));
            }
            else {
                //label.setTextColor(textColor);
                label.setTextColor(mContext.getResources().getColor(R.color.text_gray));
            }*/

            //String id = list.get(position).get("id");
            //int id = list1.get(position).getId();
//            if(id==selectedId)
//            {
//                selectedPos = position;
//                if(searchableSpinner!=null)
//                {
//                    searchableSpinner.setSelection(selectedPos,true);
//                }
//            }
//            label.setText(list.get(position).get("country_name"));//value
            label.setText(list1.get(position).getName());//value
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                label.setCompoundDrawableTintList(ColorStateList.valueOf(android.graphics.Color.parseColor(list1.get(position).getColor_code())));

            }
            else
            {
                Drawable mDrawable=mContext.getResources().getDrawable(R.drawable.bubble2);
                mDrawable.setColorFilter(new PorterDuffColorFilter(android.graphics.Color.parseColor(list1.get(position).getColor_code()), PorterDuff.Mode.SRC_ATOP));//PorterDuff.Mode.MULTIPLY
                label.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mDrawable, null);
            }
            return row;
        } catch (Exception e) {
            e.printStackTrace();
            return row;
        }
    }

    public int getSelectedPosition()
    {
        return selectedPos;
    }

    public void setSelectedId(int id)
    {
        selectedId = id;
    }


    public View getCustomDropDownView(int position, View convertView,
                                      ViewGroup parent) {
        try {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.item_drop_down_color, parent, false);
            TextView label = row.findViewById(R.id.text2);
//            if (appSession.getLanguage().equalsIgnoreCase("ar"))
//                label.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//            else label.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            //label.setText(list.get(position).get("country_name"));//value//"  " + // + "  "`
            label.setText(list1.get(position).getName());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                label.setCompoundDrawableTintList(ColorStateList.valueOf(android.graphics.Color.parseColor(list1.get(position).getColor_code())));

            }
            else
            {
                Drawable mDrawable=mContext.getResources().getDrawable(R.drawable.bubble2);
                mDrawable.setColorFilter(new PorterDuffColorFilter(android.graphics.Color.parseColor(list1.get(position).getColor_code()), PorterDuff.Mode.SRC_ATOP));//PorterDuff.Mode.MULTIPLY
                label.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mDrawable, null);
            }
            /*if (position == 0) {
                label.setBackgroundColor(mContext.getResources().getColor(
                        R.color.white));
                label.setTextColor(mContext.getResources().getColor(
                        R.color.text_gray));
            } else {
                label.setBackgroundColor(mContext.getResources().getColor(
                        R.color.white));
                label.setTextColor(mContext.getResources().getColor(
                        R.color.text_gray));
            }*/
            return row;
        } catch (Exception e) {
            e.printStackTrace();
            return row;
        }
    }
}