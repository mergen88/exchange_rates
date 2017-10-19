package kz.mergen.exchagerates.exchangerates.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kz.mergen.exchagerates.exchangerates.Models.RatesModel;
import kz.mergen.exchagerates.exchangerates.R;

/**
 * Created by arman on 13.09.17.
 */

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.ViewHolder> {
    Context context;
    ArrayList<RatesModel> ratesModels;
    public RatesAdapter(ArrayList<RatesModel> ratesModels){
        this.ratesModels = ratesModels;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.kurs_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RatesModel rate = ratesModels.get(position);
        if(rate.change.contains("+")){
            holder.change_view.setTextColor(Color.RED);
        } else {
            holder.change_view.setTextColor(Color.GREEN);
        }
        holder.currency_view.setText(rate.currency_title);
        holder.change_view.setText(rate.change);
        holder.date_view.setText(rate.date);
        holder.sale_value.setText(holder.sale_value.getText().toString().replace("%",rate.money));
    }

    @Override
    public int getItemCount() {
        return ratesModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView currency_view;
        public TextView sale_value;
        public TextView date_view;
        public TextView change_view;
        public ViewHolder(View itemView) {
            super(itemView);
            currency_view = (TextView)itemView.findViewById(R.id.currency_view);
            sale_value = (TextView)itemView.findViewById(R.id.sale_view);
            date_view = (TextView)itemView.findViewById(R.id.date_view);
            change_view = (TextView)itemView.findViewById(R.id.change_view);
        }
    }
}
