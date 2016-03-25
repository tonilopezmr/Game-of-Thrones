package es.npatarino.android.gotchallenge.view.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.view.adapters.SectionsPagerAdapter;

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
        final ViewPager  viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_word_list);
        tabLayout.getTabAt(0).setText("");
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_group_list);
        tabLayout.getTabAt(1).setText("");
        tabLayout.getTabAt(1).getIcon().mutate().setAlpha(125);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().mutate().setAlpha(255);
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().mutate().setAlpha(125);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
