package kz.mergen.exchagerates.exchangerates.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import kz.mergen.exchagerates.exchangerates.BaseApp;
import kz.mergen.exchagerates.exchangerates.Constants;
import kz.mergen.exchagerates.exchangerates.Models.RatesModel;

/**
 * Created by arman on 14.09.17.
 */

public class DataBaseController extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "main_db";
    public static int DATABASE_VERSION = 1;
    public static String CR_TB = "CREATE TABLE ";
    public static String IPKA = " (_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
    public static String CREATE_RATES_TB = CR_TB+ Constants.RATES_TABLE+IPKA
            + Constants.MONEY_COLUMN+" TEXT, "
            + Constants.CHANGE_COLUMN+" TEXT, "
            + Constants.CURRENCY_COLUMN+" TEXT, "
            + Constants.CHECKED_COLUMN+" TEXT, "
            +Constants.DATE_COLUMN+" TEXT);";
    public static String SELECT_RATES = "SELECT * FROM "+ Constants.RATES_TABLE+";";
    public DataBaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RATES_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS + "+Constants.RATES_TABLE+";");
        onCreate(db);
    }

    private static SQLiteDatabase db;
    private static SQLiteDatabase getDatabaseController(Context context){
        if(db==null){
            db = new DataBaseController(context).getWritableDatabase();
        }
        return db;
    }

    public static class DataBaseHelper{


        public static ArrayList<RatesModel> getRatesModels(Context context){
            Cursor cursor = DataBaseController.getDatabaseController(context).query(Constants.RATES_TABLE,null,null,null,null,null,null);
            ArrayList<RatesModel> ratesModels = new ArrayList<>();
            while (cursor.moveToNext()){
                String CHANGE = cursor.getString(cursor.getColumnIndex(Constants.CHANGE_COLUMN));
                String CURRENCY = cursor.getString(cursor.getColumnIndex(Constants.CURRENCY_COLUMN));
                String MONEY = cursor.getString(cursor.getColumnIndex(Constants.MONEY_COLUMN));
                String DATE = cursor.getString(cursor.getColumnIndex(Constants.DATE_COLUMN));
                int CHECK = cursor.getInt(cursor.getColumnIndex(Constants.CHECKED_COLUMN));

                ratesModels.add(new RatesModel(MONEY,CURRENCY,DATE,CHANGE,getChecked(CHECK)));
            }
            cursor.close();
            return ratesModels;
        }
        public static void clearRatesModels(Context context){
            DataBaseController.getDatabaseController(context).execSQL("DELETE FROM "+Constants.RATES_TABLE+";");
        }
        public static ArrayList<RatesModel> getCheckedRatesModels(Context context){
            Cursor cursor = DataBaseController.getDatabaseController(context).rawQuery("SELECT * FROM "+Constants.RATES_TABLE +
                    " WHERE "+Constants.CHECKED_COLUMN+" = "+1+";",null);
            ArrayList<RatesModel> ratesModels = new ArrayList<>();
            while (cursor.moveToNext()){
                String CHANGE = cursor.getString(cursor.getColumnIndex(Constants.CHANGE_COLUMN));
                String CURRENCY = cursor.getString(cursor.getColumnIndex(Constants.CURRENCY_COLUMN));
                String MONEY = cursor.getString(cursor.getColumnIndex(Constants.MONEY_COLUMN));
                String DATE = cursor.getString(cursor.getColumnIndex(Constants.DATE_COLUMN));
                int CHECK = cursor.getInt(cursor.getColumnIndex(Constants.CHECKED_COLUMN));

                ratesModels.add(new RatesModel(MONEY,CURRENCY,DATE,CHANGE,getChecked(CHECK)));
            }
            cursor.close();
            return ratesModels;
        }
        public static Boolean getChecked(int checked){
            if(checked!=1){
                return false;
            }
            return true;
        }
        public static void setCheckedRate(Context context, String currency, Boolean checked){
            SQLiteDatabase db = DataBaseController.getDatabaseController(context);
            if(checked){
                db.execSQL("UPDATE "+Constants.RATES_TABLE+
                        " SET "+Constants.CHECKED_COLUMN+" = "+1+
                        " WHERE "+Constants.CURRENCY_COLUMN +" = '"+currency+"';");
            } else {
                db.execSQL("UPDATE "+Constants.RATES_TABLE+
                        " SET "+Constants.CHECKED_COLUMN+" = "+0+
                        " WHERE "+Constants.CURRENCY_COLUMN +" = '"+currency+"';");
            }

        }
        public static String getMoney(Context context, String currency){
            String money = new String();
            Cursor cursor = DataBaseController.getDatabaseController(context).rawQuery("SELECT * FROM "+Constants.RATES_TABLE +
                    " WHERE "+Constants.CURRENCY_COLUMN+" = '"+currency+"';",null);
            ArrayList<RatesModel> ratesModels = new ArrayList<>();
            while (cursor.moveToNext()){
                String MONEY = cursor.getString(cursor.getColumnIndex(Constants.MONEY_COLUMN));
                money = MONEY;
            }
            cursor.close();
            return money;
        }
        public static String[] getCurrency(Context context){
            Cursor cursor = DataBaseController.getDatabaseController(context).query(Constants.RATES_TABLE,null,null,null,null,null,null);
            String[] data = new String[cursor.getCount()];
            int index = 0;
            while (cursor.moveToNext()){
                String CURRENCY = cursor.getString(cursor.getColumnIndex(Constants.CURRENCY_COLUMN));
                data[index] = CURRENCY;
                index++;
            }
            cursor.close();
            return data;
        }
        public static void saveRatesModels(Context context, ArrayList<RatesModel> ratesModels){
            ContentValues values = new ContentValues();
            for(RatesModel ratesModel : ratesModels){
                values.put(Constants.MONEY_COLUMN, ratesModel.money);
                values.put(Constants.CURRENCY_COLUMN, ratesModel.currency_title);
                values.put(Constants.CHANGE_COLUMN, ratesModel.change);
                values.put(Constants.DATE_COLUMN, ratesModel.date);
                if(BaseApp.instance.sharedController.isFirstRun(context)){
                    if(ratesModel.currency_title.equals("USD")||
                            ratesModel.currency_title.equals("EUR")||
                            ratesModel.currency_title.equals("RUB")){
                        values.put(Constants.CHECKED_COLUMN,ratesModel.checked);
                    } else {
                        values.put(Constants.CHECKED_COLUMN,false);
                    }
                    DataBaseController.getDatabaseController(context).insert(Constants.RATES_TABLE,null,values);
                } else {
                    DataBaseController.getDatabaseController(context).update(Constants.RATES_TABLE,values,Constants.CURRENCY_COLUMN+" = ?",new String[]{ratesModel.currency_title});
                }
                values.clear();
            }
            BaseApp.instance.sharedController.setRunned(context);
        }
    }
}
