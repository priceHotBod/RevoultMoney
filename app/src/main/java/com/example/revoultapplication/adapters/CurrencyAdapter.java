package com.example.revoultapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.revoultapplication.POJO.Currency;
import com.example.revoultapplication.R;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder>{
    private ArrayList<Currency> currencies;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick (int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    // RecyclerView recyclerView;
    public CurrencyAdapter(ArrayList<Currency> currencies) {
        this.currencies = currencies;


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.currency_viewholder, parent, false);

        ViewHolder viewHolder = new ViewHolder(listItem, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Currency currency = currencies.get(position);
        holder.flagImageView.setImageResource(currency.getFlagImageReference());
        holder.abbrevNameTextView.setText(currency.getCurrencyAbbreviatedName());
        holder.fullNameTextView.setText(currency.getCurrencyFullName());
        if (!holder.currencyValueEditText.hasFocus()){
            holder.currencyValueEditText.setText(currency.getExchangeRate() + "");
        }

    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parentLayout;
        ImageView flagImageView;
        TextView abbrevNameTextView;
        TextView fullNameTextView;
        EditText currencyValueEditText;
        ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            this.parentLayout = itemView.findViewById(R.id.currency_viewholder_parent_layout);
            this.flagImageView = itemView.findViewById(R.id.currency_viewholder_flag_imageview);
            this.abbrevNameTextView = itemView.findViewById(R.id.currency_viewholder_abbrev_name_textview);
            this.fullNameTextView = itemView.findViewById(R.id.currency_viewholder_full_name_textview);
            this.currencyValueEditText = itemView.findViewById(R.id.currency_viewholder_value_edittext);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}