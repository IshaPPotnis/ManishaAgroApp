package com.example.manishaagro;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.manishaagro.utils.Constants.EMPI_USER;
import static com.example.manishaagro.utils.Constants.EMPLOYEE;
import static com.example.manishaagro.utils.Constants.LOGIN_MANAGER;
import static com.example.manishaagro.utils.Constants.PROFILE;

public class ManagerActivity extends AppCompatActivity {
    Toolbar managerToolbar;
    ViewPager managerViewPager;
    TabLayout managerTabLayout;
    String tempManagerValue = "";
    String tempManagerIDValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mngr);

        managerToolbar = findViewById(R.id.Mngrtoolbar);
        setSupportActionBar(managerToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        managerViewPager = findViewById(R.id.viewpager1);
        SetUpPager(managerViewPager);
        managerTabLayout = findViewById(R.id.tab2);

        managerTabLayout.setupWithViewPager(managerViewPager);
        Intent intent = getIntent();
        tempManagerValue = intent.getStringExtra(LOGIN_MANAGER);
        tempManagerIDValue = intent.getStringExtra(EMPI_USER);

        Log.v("yek", "keyyy" + tempManagerValue);
        Log.v("ddd", "ddd" + tempManagerIDValue);
    }

    public Bundle getMgrData() {
        Bundle hm = new Bundle();
        hm.putString("tempval2", tempManagerValue);
        hm.putString("tempManagerIDval2", tempManagerIDValue);
        return hm;
    }

    private void SetUpPager(ViewPager viewPager) {
        ViewPagerAdapter1 adp = new ViewPagerAdapter1(getSupportFragmentManager());
        adp.addFragment(new ManagerProfileFragment(), PROFILE);
        adp.addFragment(new EmployeeFragment(), EMPLOYEE);
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

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return namesList.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            namesList.add(name);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManagerActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}