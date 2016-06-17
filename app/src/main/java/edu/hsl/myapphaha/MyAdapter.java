package edu.hsl.myapphaha;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class MyAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    List<JsonBean.Result.Data> data = new ArrayList<>();

    public MyAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public JsonBean.Result.Data getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public void addData(List<JsonBean.Result.Data> data) {
        this.data.addAll(data);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.layout_list_content, null);
            holder.tv_content = (TextView) view.findViewById(R.id.tv_content);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_time.setText(getItem(i).updatetime);
        holder.tv_content.setText(view.getResources().getString(R.string.text_name) + getItem(i)
                .content);
        return view;
    }

    class ViewHolder {
        TextView tv_content;
        TextView tv_time;
    }
}
