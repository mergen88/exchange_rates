package kz.mergen.exchagerates.exchangerates;

import android.app.Application;

import kz.mergen.exchagerates.exchangerates.Utils.DataBaseController;
import kz.mergen.exchagerates.exchangerates.Utils.ServerController;
import kz.mergen.exchagerates.exchangerates.Utils.SharedController;

/**
 * Created by arman on 19.10.17.
 */

public class BaseApp extends Application {

    public static BaseApp instance;
    public SharedController sharedController;
    public ServerController serverController;
    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        sharedController = new SharedController();
        serverController = new ServerController();
    }
}
