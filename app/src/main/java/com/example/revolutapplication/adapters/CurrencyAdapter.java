package com.example.revolutapplication.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.revolutapplication.POJO.Currency;
import com.example.revolutapplication.R;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder>{
    private ArrayList<Currency> currencies;

    private OnItemClickListener onItemClickListener;
    private OnEditTextChangedListener onEditTextChangedListener;

    public interface OnItemClickListener {
        void onItemClick (int position);
    }
    public interface OnEditTextChangedListener {
        void onTextChanged(int position, String charSeq);
    }

    public void setOnItemClickListener(OnItemClickListener listener){ onItemClickListener = listener; }
    public void setOnEditTextChangedListener(OnEditTextChangedListener listener){ onEditTextChangedListener = listener; }

    // RecyclerView recyclerView;
    public CurrencyAdapter(ArrayList<Currency> currencies) {
        this.currencies = currencies;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.currency_viewholder, parent, false);

        ViewHolder viewHolder = new ViewHolder(listItem, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final Currency currency = currencies.get(position);
        holder.flagImageView.setImageResource(currency.getFlagImageReference());
        holder.abbrevNameTextView.setText(currency.getCurrencyAbbreviatedName());
        holder.fullNameTextView.setText(currency.getCurrencyFullName());

        EditText editText = holder.currencyValueEditText;
        // Limit the value to 2 decimal places
        editText.setText(String.format("%.2f", currency.getExchangeRate() * currency.getBaseCurrencyQuantity()));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (position == 0) {
                    onEditTextChangedListener.onTextChanged(position, charSequence.toString());
                }
            }
        });
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