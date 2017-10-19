package kz.mergen.exchagerates.exchangerates.Ui;

import android.animation.Animator;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import kz.mergen.exchagerates.exchangerates.Adapters.RatesAdapter;
import kz.mergen.exchagerates.exchangerates.BaseApp;
import kz.mergen.exchagerates.exchangerates.R;
import kz.mergen.exchagerates.exchangerates.Utils.DataBaseController;
import kz.mergen.exchagerates.exchangerates.Utils.ServerController;

public class HomeActivity extends BaseActivity {

    private FloatingActionButton fab;
    private float[] a;
    private boolean isExpandle = true;
    private AppBarLayout appBarLayout;
    private ImageView imageView;
    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        imageView = (ImageView)findViewById(R.id.toolbarImage);

        initRates();
        initToolbar();
        collapsingToolbarLayout.setTitle(getString(R.string.app_name));
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        initAlpha((appBarLayout.getLayoutParams().height-toolbar.getLayoutParams().height)-30);
        initViews();
    }

    

    private void initAlpha(int value){
        a = new float[value];
        float aa = ((float) 1)/value;
        float ab = 0;
        for (int i = 0; i<a.length;i++){
            a[i] = 1-ab;
            ab+=aa;
        }
    }
    private float getAlpha(int alpha){
        int value = Math.abs(alpha);
        if(value<a.length){
            return a[value];
        }
        return 0;
    }
    Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        this.menu = menu;
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        showSelect(HomeActivity.this, getWindow().getWindowManager().getDefaultDisplay().getWidth()-15,toolbar.getHeight()/2);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu!=null) {
            if (isExpandle) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRates();
    }

    @Override
    protected void loadRates() {
        RatesAdapter ratesAdapter = new RatesAdapter(DataBaseController.DataBaseHelper.getCheckedRatesModels(getApplicationContext()));
        recycle_view.setAdapter(ratesAdapter);
    }
    private void initViews(){

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                imageView.setAlpha(getAlpha(verticalOffset));
                if(verticalOffset<-200){
                    fab.hide();
                    if (isExpandle){
                        supportInvalidateOptionsMenu();
                        isExpandle = false;
                    }
                } else {
                    fab.show();
                    if(!isExpandle) {
                        supportInvalidateOptionsMenu();
                        isExpandle = true;
                    }
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect(HomeActivity.this, (int)fab.getX()+(fab.getWidth()/2),(int)fab.getY()+(fab.getHeight()/2));
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(),"Добавление валюты",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


}
