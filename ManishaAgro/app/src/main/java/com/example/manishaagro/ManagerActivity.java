package com.example.manishaagro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.manishaagro.utils.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.manishaagro.utils.Constants.EMPLOYEE;
import static com.example.manishaagro.utils.Constants.PROFILE;

public class ManagerActivity extends AppCompatActivity {
    Toolbar managerToolbar;
    ViewPager managerViewPager;
    TabLayout managerTabLayout;
    String tempEmployeeValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mngr);

        managerToolbar = findViewById(R.id.Mngrtoolbar);
        setSupportActionBar(managerToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        managerViewPager = findViewById(R.id.viewpager1);
        SetUpPager(managerViewPager);
        managerTabLayout = findViewById(R.id.tab2);

        managerTabLayout.setupWithViewPager(managerViewPager);
        Intent intent = getIntent();
        tempEmployeeValue = intent.getStringExtra(Constants.LOGIN_MANAGER);
        Log.v("yek", "keyyy" + tempEmployeeValue);
    }

    public Bundle getMgrData() {
        Bundle hm = new Bundle();
        hm.putString("tempval2", tempEmployeeValue);
        return hm;
    }

    private void SetUpPager(ViewPager viewPager) {
        ViewPagerAdapter1 adp = new ViewPagerAdapter1(getSupportFragmentManager());
        adp.addFragment(new MgrProfileFrag(), PROFILE);
        adp.addFragment(new EmployeeFrag(), EMPLOYEE);
        viewPager.setAdapter(adp);
    }

    private static class ViewPagerAdapter1 extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> namesList = new ArrayList<>();

        public ViewPagerAdapter1(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            namesList.add(name);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return namesList.get(position);
        }
    }
}