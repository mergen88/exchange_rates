package kz.mergen.exchagerates.exchangerates;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import kz.mergen.exchagerates.exchangerates.Models.RatesModel;
import kz.mergen.exchagerates.exchangerates.Utils.DataBaseController;
import kz.mergen.exchagerates.exchangerates.Utils.ServerController;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test(timeout = 10000)
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Log.d("AAA","test started");
        ServerController.getInstance().getRatesModels(appContext, new ServerController.ServerConnectionListener() {
            @Override
            public void onStartConnection() {
                Log.d("AAA","onStartConnection");
            }

            @Override
            public void onErrorConnection() {
                Log.d("AAA","onErrorConnection");
            }

            @Override
            public void onCompleteConnection(ArrayList<RatesModel> ratesModels) {
                Log.d("AAA","onCompleteConnection");
                for(RatesModel re : ratesModels){

                    Log.d("AAA","rate "+re.currency_title +" "+re.money);
                }
            }
        });
        assertEquals("kz.mergen.exchagerates.exchangerates", appContext.getPackageName());

    }


}
