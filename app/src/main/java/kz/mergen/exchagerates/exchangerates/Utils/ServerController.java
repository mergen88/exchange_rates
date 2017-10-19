package kz.mergen.exchagerates.exchangerates.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import kz.mergen.exchagerates.exchangerates.Constants;
import kz.mergen.exchagerates.exchangerates.Models.RatesModel;
import kz.mergen.exchagerates.exchangerates.Models.ResponceModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by arman on 13.09.17.
 */

public class ServerController {
    private ServerConnectionListener connectionListener;


    public void getRatesModels(Context context, ServerConnectionListener connectionListener){
        this.connectionListener = connectionListener;
        openConnection(context);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnected();
    }

    private void openConnection(final Context context){
        if(isOnline(context)){
            new AsyncTask<String, String, ResponceModel>(){
                @Override
                protected void onPreExecute() {
                    connectionListener.onStartConnection();
                }

                @Override
                protected ResponceModel doInBackground(String... params) {
                    ResponceModel respModel = null;
                    try {
                        OkHttpClient httpClient = new OkHttpClient.Builder()
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .writeTimeout(10, TimeUnit.SECONDS)
                                .readTimeout(10, TimeUnit.SECONDS)
                                .build();
                        Request.Builder request = new Request.Builder();
                        request.url(Constants.RATES_URL).get();

                        Response response = httpClient.newCall(request.build()).execute();
                        Log.i("AAA","responce = "+response.code());
                        respModel = new ResponceModel(response.body().byteStream(),response.code());
                    } catch (Exception e) {
                        Log.e("AAA","Exception "+e.getMessage());
                        e.printStackTrace();
                        connectionListener.onErrorConnection();
                    }
                    return respModel;
                }

                @Override
                protected void onPostExecute(ResponceModel responseModel) {
                    if(responseModel.codeError==200){
                        connectionListener.onCompleteConnection(XmlParser.getInstance().parse(responseModel.inputStream).findTag(Constants.TAG_NAME,new String[]{
                                Constants.CURRENCY,
                                Constants.UPDATE_DATE,
                                Constants.MONEY,
                                Constants.CHANGE}).getRatesModels());

                    } else {
                        connectionListener.onErrorConnection();
                    }

                }
            }.execute();
        } else {

        }
    }
    public interface ServerConnectionListener{
        void onStartConnection();
        void onErrorConnection();
        void onCompleteConnection(ArrayList<RatesModel> ratesModels);
    }
}
