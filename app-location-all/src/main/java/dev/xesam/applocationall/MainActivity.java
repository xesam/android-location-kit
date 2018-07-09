package dev.xesam.applocationall;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    public TabLayout vTabs;
    @BindView(R.id.vp)
    public ViewPager vViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        vViewPager.setOffscreenPageLimit(3);
        vViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            private int getType(int position) {
                switch (position) {
                    case 0:
                        return Type.ANDROID;
                    case 1:
                        return Type.BAIDU;
                    default:
                        return Type.GAODE;
                }
            }

            private String getTitle(int position) {
                switch (position) {
                    case 0:
                        return "Android";
                    case 1:
                        return "百度";
                    default:
                        return "高德";
                }
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return AndroidFragment.newInstance(getType(position), getTitle(position));
                    case 1:
                        return BaiduFragment.newInstance(getType(position), getTitle(position));
                    default:
                        return GaodeFragment.newInstance(getType(position), getTitle(position));
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getTitle(position);
            }
        });

        vTabs.setupWithViewPager(vViewPager);
    }
}
