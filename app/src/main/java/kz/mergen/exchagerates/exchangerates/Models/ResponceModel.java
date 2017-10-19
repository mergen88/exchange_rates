package kz.mergen.exchagerates.exchangerates.Models;

import java.io.InputStream;

/**
 * Created by arman on 13.09.17.
 */

public class ResponceModel {
    public InputStream inputStream;
    public int codeError;

    public ResponceModel(InputStream inputStream, int codeError){
        this.inputStream = inputStream;
        this.codeError = codeError;
    }
}
