package kz.mergen.exchagerates.exchangerates;

/**
 * Created by arman on 13.09.17.
 */

public class Constants {
//    server
    public static String RATES_URL = "http://www.nationalbank.kz/rss/rates_all.xml";
    public static String TAG_NAME = "item";
    public static String CURRENCY = "title";
    public static String UPDATE_DATE = "pubDate";
    public static String CHANGE = "change";
    public static String MONEY = "description";

    public static String KURS_URL = "https://kurs.kz/index.php?mode=almaty";

//    database
    public static String RATES_TABLE = "rates_tb";
    public static String DATABASE_NAME = "rates.db";
    public static String CURRENCY_COLUMN = "currency_col";
    public static String CHANGE_COLUMN = "change_col";
    public static String DATE_COLUMN = "date_col";
    public static String MONEY_COLUMN = "money_col";
    public static String CHECKED_COLUMN = "checked_col";

//    settings
    public static String SHARED_SETTINGS = "excange_settings";
    public static String FIRST_RUN = "firstRun";
}
