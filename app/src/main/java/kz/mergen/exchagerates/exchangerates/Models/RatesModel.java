package kz.mergen.exchagerates.exchangerates.Models;

/**
 * Created by arman on 13.09.17.
 */

public class RatesModel {
    public String money;
    public String currency_title;
    public String date;
    public String change;
    public boolean checked;

    public RatesModel(String money, String currency_title, String date, String change, boolean checked){
        this.money = money;
        this.currency_title = currency_title;
        this.date = date;
        this.change = change;
        this.checked = checked;
    }

}
