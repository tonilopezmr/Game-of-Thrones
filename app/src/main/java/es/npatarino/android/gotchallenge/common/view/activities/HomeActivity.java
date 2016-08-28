package es.npatarino.android.gotchallenge.common.view.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.common.view.adapters.SectionsPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUi();
    }

    public void initUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupTabs();
    }

    private void setupTabs() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setCustomView(getCustomIcon(R.drawable.ned_head_light));
        tabLayout.getTabAt(0).setText("");
        tabLayout.getTabAt(1).setCustomView(getCustomIcon(R.drawable.insignia_light, 100));
        tabLayout.getTabAt(1).setText("");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView imageView = (ImageView) tab.getCustomView();
                imageView.getDrawable().mutate().setAlpha(255);
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView imageView = (ImageView) tab.getCustomView();
                imageView.getDrawable().mutate().setAlpha(100);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private View getCustomIcon(int resId, int alpha) {
        return getCustomIcon(AppCompatDrawableManager.get().getDrawable(tabLayout.getContext(), resId), alpha);
    }

    private View getCustomIcon(int resId) {
        return getCustomIcon(AppCompatDrawableManager.get().getDrawable(tabLayout.getContext(), resId), 255);
    }

    private View getCustomIcon(Drawable icon, int alpha) {
        ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.custom_tab, null);
        imageView.setImageDrawable(icon);
        imageView.getDrawable().mutate().setAlpha(alpha);
        return imageView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GotChallengeApplication app = GotChallengeApplication.get(getApplicationContext());
        app.releaseHouseComponent();
        app.releaseCharacterComponent();
    }
}
