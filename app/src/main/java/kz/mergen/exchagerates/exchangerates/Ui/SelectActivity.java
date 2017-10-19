package kz.mergen.exchagerates.exchangerates.Ui;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import kz.mergen.exchagerates.exchangerates.Adapters.SelectAdapter;
import kz.mergen.exchagerates.exchangerates.R;
import kz.mergen.exchagerates.exchangerates.Utils.DataBaseController;

public class SelectActivity extends BaseActivity {

    private LinearLayout select_layout;
    @Override
    protected int getContentView() {
        return R.layout.activity_select;
    }

    public static int cx = 0;
    public static int cy = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        select_layout = (LinearLayout)findViewById(R.id.select_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        initToolbar();
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.select_money));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initRates();
        select_layout.post(new Runnable() {
            @Override
            public void run() {
                circularShowActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {

        circularHideActivity();
    }

    private void circularHideActivity(){
        float finalRadius = Math.max(select_layout.getWidth(), select_layout.getHeight());
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(select_layout, cx, cy, finalRadius, 0);
        circularReveal.setDuration(700);
        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                select_layout.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        circularReveal.start();
    }
    private void circularShowActivity() {
        float finalRadius = Math.max(select_layout.getWidth(), select_layout.getHeight());
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(select_layout, cx, cy, 0, finalRadius);
        circularReveal.setDuration(700);
        circularReveal.start();
    }

    @Override
    protected void loadRates() {
        SelectAdapter ratesAdapter = new SelectAdapter(DataBaseController.DataBaseHelper.getRatesModels(getApplicationContext()));
        recycle_view.setAdapter(ratesAdapter);
    }


}
