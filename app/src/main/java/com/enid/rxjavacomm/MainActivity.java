package com.enid.rxjavacomm;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(android.R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int position) {
                if(position % 2 == 0){
                    return new MyFragment1();
                }else{
                    return new MyFragment2();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0:
                        return "page1";
                    case 1:
                        return "page2";
                    default:
                        return "page0";
                }
            }

            @Override
            public float getPageWidth(int position) {
                return super.getPageWidth(position);
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }
}
