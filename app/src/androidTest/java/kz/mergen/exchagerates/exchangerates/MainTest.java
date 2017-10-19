package kz.mergen.exchagerates.exchangerates;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockApplication;
import android.test.mock.MockContext;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import kz.mergen.exchagerates.exchangerates.Models.RatesModel;
import kz.mergen.exchagerates.exchangerates.Ui.HomeActivity;
import kz.mergen.exchagerates.exchangerates.Utils.ServerController;

/**
 * Created by arman on 21.09.17.
 */
@RunWith(AndroidJUnit4.class)
public class MainTest {


    @Test
    public void sayHello(){
        ServerController.getInstance().nconnection(InstrumentationRegistry.getContext());
        Log.d("AAA","sayHello");
    }
    @After
    public void sayEnd(){
        System.out.println("OK");
    }

}
