package com.filmetrics.eqrcodeapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.filmetrics.eqrcodeapp.adapters.ProductDetailAdapter;
import com.filmetrics.eqrcodeapp.fragments.DetailsFragment;
import com.filmetrics.eqrcodeapp.fragments.OtherInfoFragment;
import com.filmetrics.eqrcodeapp.fragments.PromosFragment;

public class ProductDetailsActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.prod_dtls_title));
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        toolbar.setTitle(getTitles()[0]);

        for(int cntr = 0;cntr < getTitles().length; cntr++) {
            setTabTitle(cntr);
        }
    }

    // This method will call Adapter for ViewPager
    private void setupViewPager(ViewPager viewPager) {
        ProductDetailAdapter adapter = new ProductDetailAdapter(getSupportFragmentManager());
        adapter.addFragment(new DetailsFragment(), "");
        adapter.addFragment(new OtherInfoFragment(), "");
        adapter.addFragment(new PromosFragment(), "");
        //Set adapter to ViewPager
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        toolbar.setTitle(getTitles()[tab.getPosition()]);
        //When Tab is clicked this line set the viewpager to corresponding fragment
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    private String[] getTitles() {
        String[] FRAGMENT_NAME = {getString(R.string.prod_dtls), getString(R.string.other_info), getString(R.string.promo)};
        return FRAGMENT_NAME;
    }

    private void setTabTitle(int index) {
        tabLayout.getTabAt(index).setText(getTitles()[index]);
    }
}
