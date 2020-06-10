package com.example.gleaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.gleaming.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(AllFragment.newInstance());
        fragments.add(UserProfileActivity.newInstance());
        fragments.add(UserProfileActivity.newInstance());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), fragments);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        BottomNavigationView bnvBottomMenu = findViewById(R.id.bnv_menu);

        setupViewPagerListener(bnvBottomMenu, viewPager);
        setupBottomNavViewListener(bnvBottomMenu, viewPager);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments;

        SectionsPagerAdapter(MainActivity mainActivity, FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);

            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    private void setupBottomNavViewListener(BottomNavigationView bnvBottomMenu, final ViewPager viewPager) {
        bnvBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_like:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_perfil:
                        viewPager.setCurrentItem(2);
                        break;
                }

                return false;
            }
        });
    }

    private void setupViewPagerListener(final BottomNavigationView bnvBottomMenu, ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bnvBottomMenu.getMenu().getItem(0).setChecked(true);
                }

                bnvBottomMenu.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bnvBottomMenu.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    /*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

     @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*switch (item.getItemId()){

            case R.id.logout:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

   } */
}