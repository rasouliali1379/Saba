package com.rayanandishehnasr.hmi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rayanandishehnasr.hmi.DataHolder.SelectorDialogDataHolder;
import com.rayanandishehnasr.hmi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectorDialogAdapter extends RecyclerView.Adapter<SelectorDialogAdapter.ViewHolder> {

    Context context;
    List<SelectorDialogDataHolder> locations;
    private ItemClickListener listener;

    public SelectorDialogAdapter(Context context, List<SelectorDialogDataHolder> locations, ItemClickListener listener) {
        this.context = context;
        this.locations = locations;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selector_dialog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.content.setText(locations.get(position).getLabel());
        if (position == locations.size() - 1){
            holder.indicator.setVisibility(View.INVISIBLE);
        } else {
            holder.indicator.setVisibility(View.VISIBLE);
        }
        holder.bind(locations.get(position).getId(), listener);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public interface ItemClickListener {
        void onItemClick(int id);
    }

    public void setData (List<SelectorDialogDataHolder> locations){
        this.locations = locations;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.selector_dialog_item_content_txt)TextView content;
        @BindView(R.id.selector_dialog_item_indicator)LinearLayout indicator;
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
        public void bind (int id, ItemClickListener listener){
            itemView.setOnClickListener(v -> listener.onItemClick(id));
        }
    }
}