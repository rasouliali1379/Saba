package com.rayanandishehnasr.hmi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rayanandishehnasr.hmi.DataHolder.SelectorDialogDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.XnYDataHolder;
import com.rayanandishehnasr.hmi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListStaticsAdapter extends RecyclerView.Adapter<ListStaticsAdapter.ViewHolder> {

    Context context;
    List<XnYDataHolder> entries;

    public ListStaticsAdapter(Context context, List<XnYDataHolder> entries) {
        this.context = context;
        this.entries = entries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_statics_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(entries.get(position).getX());
        holder.number.setText(entries.get(position).getY());
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_statics_item_name)TextView name;
        @BindView(R.id.list_statics_item_number)TextView number;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}