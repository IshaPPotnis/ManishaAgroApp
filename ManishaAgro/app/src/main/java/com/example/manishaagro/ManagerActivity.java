package com.example.manishaagro;

import android.app.Activity;
import android.app.Dialog;
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

import com.example.manishaagro.utils.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.ACTION_MAIN;
import static com.example.manishaagro.utils.Constants.EMPLOYEE;
import static com.example.manishaagro.utils.Constants.PROFILE;

public class ManagerActivity extends AppCompatActivity {
    Toolbar managerToolbar;
    ViewPager managerViewPager;
    TabLayout managerTabLayout;
    String tempManagerValue="";
    String tempManagerIDValue="";
    AlertDialog.Builder alertDialogBuilder;


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
        tempManagerValue = intent.getStringExtra(Constants.LOGIN_MANAGER);
        tempManagerIDValue = intent.getStringExtra("empi_user");

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

    @Override
    public void onBackPressed()
    {
        alertDialogBuilder = new AlertDialog.Builder(ManagerActivity.this);
        alertDialogBuilder.setTitle("Manisha Agro");
        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ManagerActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

}