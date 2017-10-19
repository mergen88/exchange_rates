package kz.mergen.exchagerates.exchangerates;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import org.junit.Test;

import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import kz.mergen.exchagerates.exchangerates.Models.RatesModel;
import kz.mergen.exchagerates.exchangerates.Ui.HomeActivity;
import kz.mergen.exchagerates.exchangerates.Utils.DataBaseController;
import kz.mergen.exchagerates.exchangerates.Utils.ServerController;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

       @Test
    public void addition_isCorrect() throws Exception {
              List<Integer> list = Arrays.asList(2,3,2);

              HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
              Integer am;
              for (Integer i : list) {
                  am = hm.get(i);
                  hm.put(i, am == null ? 1 : am + 1);
              }

              System.out.println(hm);
       }





}