package com.rayanandishehnasr.hmi.Adapters;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rayanandishehnasr.hmi.DataHolder.SearchResultDataHolder;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.Utils.JalaliConverter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsListAdapter extends RecyclerView.Adapter<SearchResultsListAdapter.ViewHolder> {

    Context context;
    private List<SearchResultDataHolder> results;
    private ItemClickListener listener;
    int treatyType;


    public SearchResultsListAdapter(Context context, ItemClickListener listener, int treatyType) {
        this.context = context;
        this.listener = listener;
        this.treatyType = treatyType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.addressTxt.setText("منطقه : " + results.get(position).getStrMunZoneComment() + " ، محله : " + results.get(position).getStrZoneName());

        if (treatyType == Consts.TREATY_TYPE_SELL){
            String pricePerMeter = NumberFormat.getInstance(Locale.US).format(results.get(position).getICostPerMeter());
            holder.pricePerMeterTxt.setText(changeTextColor("قمیت هر متر : " + pricePerMeter + " تومان", pricePerMeter), TextView.BufferType.SPANNABLE);
        } else {
            String pricePerMeter = NumberFormat.getInstance(Locale.US).format(results.get(position).getiFinalRent());
            holder.pricePerMeterTxt.setText(changeTextColor("مبلغ اجاره : " + pricePerMeter + " تومان", pricePerMeter), TextView.BufferType.SPANNABLE);
        }


        int currentYear = Integer.parseInt(JalaliConverter.getCurrentShamsidate().split("/")[0]);
        int calculateAge = currentYear - results.get(position).getSiYearBuild();
        holder.ageTxt.setText(changeTextColor("عمر ساختمان : " + calculateAge + " سال", String.valueOf(calculateAge)), TextView.BufferType.SPANNABLE);

        holder.dateTxt.setText("تاریخ قرارداد : " + results.get(position).getStrTreatyDate());

        if (treatyType == Consts.TREATY_TYPE_SELL){
            String price = NumberFormat.getInstance(Locale.US).format(results.get(position).getICost());
            holder.priceTxt.setText(changeTextColor("قیمت معامله شده : " + price + " تومان", price), TextView.BufferType.SPANNABLE);
        } else {
            String price = NumberFormat.getInstance(Locale.US).format(results.get(position).getiTrust());
            holder.priceTxt.setText(changeTextColor("مبلغ رهن : " + price + " تومان", price), TextView.BufferType.SPANNABLE);
        }

        holder.bind(position, listener);
    }

    private Spannable changeTextColor(String content, String target){
        Spannable spannable = new SpannableString(content);
        int indexOfTarget = content.indexOf(target);
        spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorNavy)), indexOfTarget, indexOfTarget + target.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    @Override
    public int getItemCount() {
        if (results != null){
            return results.size();
        }
        return 0;
    }

    public void setData(List<SearchResultDataHolder> results){
        this.results = results;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.result_item_address)TextView addressTxt;
        @BindView(R.id.result_item_price_per_meter_txt)TextView pricePerMeterTxt;
        @BindView(R.id.result_item_building_age)TextView ageTxt;
        @BindView(R.id.result_item_date)TextView dateTxt;
        @BindView(R.id.result_item_price)TextView priceTxt;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }

        public void bind (int position, ItemClickListener listener){
            itemView.setOnClickListener(v -> listener.onItemClick(position));
        }
    }
}
