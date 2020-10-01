package com.example.manishaagro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
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

import static com.example.manishaagro.utils.Constants.PROFILE;
import static com.example.manishaagro.utils.Constants.STATUS;

public class EmployeeActivity extends AppCompatActivity {
    //    private ApiInterface apiInterface;
    Toolbar employeeToolbar;
    ViewPager employeeViewPager;
    TabLayout employeeTabLayout;
    String tempEmployeeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp);

        employeeToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(employeeToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
       // Fragment fragment=new MapFragment();
      //  getSupportFragmentManager().beginTransaction().replace(R.id.frameMap,fragment).commit();
        employeeViewPager = findViewById(R.id.viewpager);
        SetUpPager(employeeViewPager);
        employeeTabLayout = findViewById(R.id.tab1);

        employeeTabLayout.setupWithViewPager(employeeViewPager);

        Intent intent = getIntent();
        tempEmployeeValue = intent.getStringExtra(Constants.LOGIN_EMPLOYEE);
        Log.v("yek", "keyyy" + tempEmployeeValue);
    }

    public Bundle getEmpData() {
        Bundle hm = new Bundle();
        hm.putString("tempval1", tempEmployeeValue);
        return hm;
    }

    private void SetUpPager(ViewPager viewPager) {
        ViewPagerAdapter adp = new ViewPagerAdapter(getSupportFragmentManager());
        adp.addFragment(new ProfileFragment(), PROFILE);
        adp.addFragment(new EmployeeStatusFragment(), STATUS);
        viewPager.setAdapter(adp);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> namesList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            namesList.add(name);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return namesList.get(position);
        }
    }



}