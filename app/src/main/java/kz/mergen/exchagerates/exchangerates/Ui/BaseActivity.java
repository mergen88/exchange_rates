package kz.mergen.exchagerates.exchangerates.Ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;

import java.util.ArrayList;

import kz.mergen.exchagerates.exchangerates.Adapters.RatesAdapter;
import kz.mergen.exchagerates.exchangerates.BaseApp;
import kz.mergen.exchagerates.exchangerates.Models.RatesModel;
import kz.mergen.exchagerates.exchangerates.R;
import kz.mergen.exchagerates.exchangerates.Utils.DataBaseController;
import kz.mergen.exchagerates.exchangerates.Utils.ServerController;
import kz.mergen.exchagerates.exchangerates.Utils.SharedController;

/**
 * Created by arman on 14.09.17.
 */

public abstract class BaseActivity extends AppCompatActivity implements ServerController.ServerConnectionListener{


    private TSnackbar snackbar;
    private ProgressBar progressBar;
    private RecyclerView.LayoutManager manager;
    protected Toolbar toolbar;
    protected BaseApp app;
    protected RecyclerView recycle_view;
    protected abstract int getContentView();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        app = BaseApp.instance;
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recycle_view = (RecyclerView)findViewById(R.id.recycle_view);
        manager = new LinearLayoutManager(this);
        recycle_view.setLayoutManager(manager);
        recycle_view.setHasFixedSize(true);
    }
    protected abstract void loadRates();
    protected void initRates(){
        if(ServerController.isOnline(getApplicationContext())){
            app.serverController.getRatesModels(getApplicationContext(),this);
        } else {
            showSnackBarView();
        }
    }

    protected void hideSnackBarView(){
        if(snackbar!=null){
            if(snackbar.isShown()){
                snackbar.dismiss();
            }
        }
    }
    protected void showSelect(BaseActivity activity, int cx, int cy){
        SelectActivity.cx = cx;
        SelectActivity.cy = cy;
        showActivity(activity, SelectActivity.class);
    }
    protected static void showActivity(BaseActivity activity, Class aClass){
        activity.startActivity(new Intent(activity,aClass));
    }
    protected void showProgressBar(){
        progressBar = (ProgressBar)findViewById(R.id.toolbar_progressbar);
        progressBar.setVisibility(View.VISIBLE);
    }
    protected void hideProgressBar(){
        if(progressBar!=null) {
            progressBar.setVisibility(View.GONE);
        }
    }
    protected void initToolbar(){
        setSupportActionBar(toolbar);
    }
    protected void setTitle(String title){
        toolbar.setTitle(title);
    }
    protected void showSnackBarView(){
        snackbar = TSnackbar.make(findViewById(R.id.snackbar_view),getString(R.string.connection_error), Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.WHITE);
        View view = snackbar.getView();
        Button button = (Button)view.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_action);
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setTextSize(15);
        TextView snackText = (TextView)view.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        snackText.setTextColor(Color.WHITE);
        snackText.setTextSize(15);
        view.setBackground(getResources().getDrawable(R.color.greenWarning));
        snackbar.setAction(getString(R.string.error_action), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRates();
            }
        });
        snackbar.show();
    }
    @Override
    public void onStartConnection() {
        showProgressBar();
    }

    @Override
    public void onErrorConnection() {
        hideProgressBar();
        showSnackBarView();
    }



    @Override
    public void onCompleteConnection(ArrayList<RatesModel> ratesModels) {
        hideProgressBar();
        hideSnackBarView();
        DataBaseController.DataBaseHelper.saveRatesModels(getApplicationContext(),ratesModels);
        loadRates();
    }

}
