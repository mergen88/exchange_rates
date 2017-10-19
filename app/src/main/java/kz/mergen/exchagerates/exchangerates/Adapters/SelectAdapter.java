package kz.mergen.exchagerates.exchangerates.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import kz.mergen.exchagerates.exchangerates.Models.RatesModel;
import kz.mergen.exchagerates.exchangerates.R;
import kz.mergen.exchagerates.exchangerates.Utils.DataBaseController;

/**
 * Created by arman on 14.09.17.
 */

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {
    Context context;
    ArrayList<RatesModel> ratesModels;
    public SelectAdapter(ArrayList<RatesModel> ratesModels){
        this.ratesModels = ratesModels;

    }
    @Override
    public SelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.select_item,parent,false);
        return new SelectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SelectAdapter.ViewHolder holder, final int position) {
        final RatesModel rate = ratesModels.get(position);
        if(rate.change.contains("+")){
            holder.change_view.setTextColor(Color.RED);
        } else {
            holder.change_view.setTextColor(Color.GREEN);
        }
        holder.currency_view.setText(rate.currency_title);
        holder.change_view.setText(rate.change);
        holder.date_view.setText(rate.date);
        holder.sale_value.setText(holder.sale_value.getText().toString().replace("%",rate.money));
        holder.check_view.setChecked(rate.checked);
        holder.check_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rate.checked){
                    rate.checked = false;
                    DataBaseController.DataBaseHelper.setCheckedRate(context,rate.currency_title,false);
                    Log.i("AAA","checked "+false+" "+rate.currency_title);
                } else {
                    rate.checked = true;
                    DataBaseController.DataBaseHelper.setCheckedRate(context,rate.currency_title,true);
                    Log.i("AAA","checked "+true+" "+rate.currency_title);
                }



            }
        });


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
        public CheckBox check_view;
        public ViewHolder(View itemView) {
            super(itemView);
            currency_view = (TextView)itemView.findViewById(R.id.currency_view);
            sale_value = (TextView)itemView.findViewById(R.id.sale_view);
            date_view = (TextView)itemView.findViewById(R.id.date_view);
            change_view = (TextView)itemView.findViewById(R.id.change_view);
            check_view = (CheckBox)itemView.findViewById(R.id.select_rate);
        }
    }
}
