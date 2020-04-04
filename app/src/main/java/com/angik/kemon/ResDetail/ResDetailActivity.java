package com.angik.kemon.ResDetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.ResDetail.Fragments.DemoCollectionPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

public class ResDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = "ResDetailActivity";

    private DemoCollectionPagerAdapter demoCollectionPagerAdapter;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;


    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private ViewPager viewPager;
    private ProgressBar progressBar;

    private AppBarLayout appbar;
    private CollapsingToolbarLayout collapsing;
    private ImageView coverImage;
    private FrameLayout framelayoutTitle;
    private LinearLayout linearlayoutTitle;
    private Toolbar toolbar;
    private TextView textviewTitle;
    private TextView resName;
    private TextView resAddress;
    private TextView resRate;
    private TextView resOffers;
    private TextView resMenu;
    private TextView resReviews;

    private TabLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_detail);

        findViews();

        toolbar.setTitle("");
        appbar.addOnOffsetChangedListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        startAlphaAnimation(textviewTitle, 0, View.INVISIBLE);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(i) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(textviewTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(textviewTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(linearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;

                toolbar.setVisibility(View.VISIBLE);

                layout.setPadding(0, toolbar.getHeight(), 0, 0);
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(linearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;

                toolbar.setVisibility(View.GONE);

                layout.setPadding(0, 0, 0, 0);
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void findViews() {

        progressBar = findViewById(R.id.progressBar);

        viewPager = findViewById(R.id.pager);

        layout = findViewById(R.id.layout);

        resOffers = findViewById(R.id.resOffers);
        resMenu = findViewById(R.id.resMenu);
        resReviews = findViewById(R.id.resReviews);

        Intent intent = getIntent();

        appbar = findViewById(R.id.appbar);
        collapsing = findViewById(R.id.collapsing);
        coverImage = findViewById(R.id.imageview_placeholder);
        framelayoutTitle = findViewById(R.id.framelayout_title);
        linearlayoutTitle = findViewById(R.id.linearlayout_title);
        toolbar = findViewById(R.id.toolbar);
        textviewTitle = findViewById(R.id.textview_title);

        textviewTitle.setText(intent.getStringExtra("name"));

        resName = findViewById(R.id.resName);

        resName.setText(intent.getStringExtra("name"));

        resAddress = findViewById(R.id.resAddress);

        resAddress.setText(intent.getStringExtra("address"));

        resRate = findViewById(R.id.resRate);

        resRate.setText(intent.getStringExtra("rate"));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();

        String resName = intent.getStringExtra("name");

        demoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager(), this, resName);

        viewPager.setAdapter(demoCollectionPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        layout.setupWithViewPager(viewPager);

        resMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        resOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < layout.getTabCount(); i++) {
                    if (layout.getTabAt(i).getText().toString().trim().equals("Offer")) {
                        viewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });

        resReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < layout.getTabCount(); i++) {
                    if (layout.getTabAt(i).getText().toString().trim().equals("Reviews")) {
                        viewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        viewPager.setAdapter(null);

        layout.setupWithViewPager(null);
    }
}
