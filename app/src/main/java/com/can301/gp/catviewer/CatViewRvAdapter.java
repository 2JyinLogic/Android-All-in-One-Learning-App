package com.can301.gp.catviewer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.can301.gp.Demonstration;
import com.can301.gp.R;

import java.util.ArrayList;
import java.util.List;

//定义了一个适配器，该适配器可以为RecyclerView提供字符串列表中的项目，并在每个项目上显示一个TextView。
public class CatViewRvAdapter extends RecyclerView.Adapter<CatViewRvAdapter.ViewHolder> {
    Context mContext;
    List<Demonstration> demosList;


    public CatViewRvAdapter(Context context) {
        demosList = new ArrayList<>();
        mContext = context;
    }

    public void setDemosList(List<Demonstration> demosList) {
        this.demosList = demosList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.cat_item_next, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Demonstration demo = demosList.get(position);
        holder.tv_text.setText(demo.title);

        holder.itemView.setOnClickListener(v -> {
            demo.goToEffectActivity(mContext);
        });
    }

    @Override
    public int getItemCount() {
        return demosList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_text;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_text = itemView.findViewById(R.id.tv_text);
        }
    }

}
