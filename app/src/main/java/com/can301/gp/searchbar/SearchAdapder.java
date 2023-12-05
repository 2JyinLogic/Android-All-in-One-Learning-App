package com.can301.gp.searchbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.can301.gp.Demonstration;
import com.can301.gp.MainActivity;
import com.can301.gp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Adapter继承基本方法并implements Filter对象 实现filter功能对文本的前缀匹配
 */
public class SearchAdapder extends BaseAdapter implements Filterable {

    private Context context;

    public List<Demonstration> allDemos;
    private List<Demonstration> filteredDemos;


    public SearchAdapder(Context context, List<Demonstration> allDemos) {
        this.context = context;

        this.allDemos = allDemos;
        this.filteredDemos = new ArrayList<Demonstration>(allDemos);
    }

    /**
     * ViewHolder存放已经加载过的信息 不至于每次上下滑动都重新加载
     */
    private class ViewHolder{
        TextView ui_name, ui_description;
    }

    @Override
    public int getCount() {
        return null == allDemos ? 0: allDemos.size();
    }

    @Override
    public Object getItem(int position) {
        return allDemos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater =LayoutInflater.from(context);

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.ui_name = (TextView) convertView.findViewById(R.id.ui_name);
            holder.ui_description = (TextView) convertView.findViewById(R.id.ui_description);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        final Demonstration item = (Demonstration)getItem(position);

        holder.ui_name.setText(item.title);
        holder.ui_name.setCompoundDrawablesWithIntrinsicBounds(item.iconId, 0, 0, 0);
        holder.ui_description.setText(item.description);


        return convertView;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override

            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults resultData = new FilterResults();

                if(constraint != null && constraint.length() > 0){

                    ArrayList<Demonstration> _filterUIS = new ArrayList<>();

                    for (int i = 0; i < filteredDemos.size(); i++){
                        Demonstration filterableCustomer = filteredDemos.get(i);

                        String titleString = context.getString(filterableCustomer.title);
                        if(titleString.toLowerCase()
                                .contains(constraint.toString().toLowerCase())){
                            _filterUIS.add(filterableCustomer);
                        }
                    }
                    resultData.count = _filterUIS.size();
                    resultData.values = _filterUIS;
                }else{
                    resultData.values = filteredDemos;
                    resultData.count = filteredDemos.size();
                }
                return resultData;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                allDemos = (ArrayList<Demonstration>)results.values;
                notifyDataSetChanged();

            }
        };
    }


}